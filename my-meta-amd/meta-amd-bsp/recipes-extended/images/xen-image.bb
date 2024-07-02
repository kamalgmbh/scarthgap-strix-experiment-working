IMAGE_FEATURES += "splash package-management x11-base x11-sato ssh-server-dropbear hwcodecs"

inherit core-image

TOOLCHAIN_HOST_TASK:append = " nativesdk-intltool nativesdk-glib-2.0"
TOOLCHAIN_HOST_TASK:remove:task-populate-sdk-ext = " nativesdk-intltool nativesdk-glib-2.0"

QB_MEM = '${@bb.utils.contains("DISTRO_FEATURES", "opengl", "-m 512", "-m 256", d)}'
QB_MEM:qemuarmv5 = "-m 256"
QB_MEM:qemumips = "-m 256"


CORE_IMAGE_EXTRA_INSTALL += " kernel-modules"
IMAGE_INSTALL:append = " kernel-modules nvme-cli amdgpu-firmware spitools linux-firmware spidev-test rpm opkg lshw mokutil autoconf stress-ng hwloc numactl packagegroup-core-buildessential"

# Conditionally add packages based on machine type
do_siena[prefunc] = "siena_prefunc"
siena_prefunc() {
 # Only add these packages for the 'siena' machine
    if test "${MACHINE}" = "siena"; then
        IMAGE_INSTALL:append = " lscpuinfo git"
    fi
}

PREFERRED_PROVIDER_virtual/xserver ?= "xserver-xorg"
XSERVER ?= "xserver-xorg \
          xf86-video-modesetting \
      "
DISTRO_FEATURES:append = " ptest pthread tpm systemd posix-testsuite"
VIRTUAL-RUNTIME_init_manager = "systemd"

DESCRIPTION = "A minimal xen image"

INITRD_IMAGE = "core-image-minimal-initramfs"
#IMAGE_INSTALL:append:x86    = " kernel-module-tun"
#IMAGE_INSTALL:append:x86-64 = " kernel-module-tun"
#RRECOMMENDS:${PN} += "kernel-module-tun"

XEN_KERNEL_MODULES = "kernel-module-xen-blkback kernel-module-xen-gntalloc \
                       kernel-module-xen-gntdev kernel-module-xen-netback kernel-module-xen-wdt \
                       ${@bb.utils.contains('MACHINE_FEATURES', 'pci', "${XEN_PCIBACK_MODULE}", '', d)} \
                       ${@bb.utils.contains('MACHINE_FEATURES', 'acpi', '${XEN_ACPI_PROCESSOR_MODULE}', '', d)} \
                      "

IMAGE_INSTALL:append = " \
    packagegroup-core-boot \
    packagegroup-core-ssh-openssh \
    ${XEN_KERNEL_MODULES} \
    xen-tools \
    qemu \
    "
FILES:${PN} = "\
    ${sysconfdir}/xen/auto \
    ${sysconfdir}/xen/cpupool \
    ${localstatedir}/xen/dump \
    /var \
    /var/lib \
    /usr/lib/xen/bin/test-paging-mempool \
    "

# The hypervisor may not be within the dom0 filesystem image but at least
# ensure that it is deployable:
do_image_wic[depends] += "${INITRD_IMAGE_LIVE}:do_image_complete"
do_build[depends] += "xen:do_deploy"

# Networking for HVM-mode guests (x86/64 only) requires the tun kernel module
#IMAGE_INSTALL:append:x86    = " kernel-module-tun"
#IMAGE_INSTALL:append:x86-64 = " kernel-module-tun"

# Linux kernel option CONFIG_XEN_PCIDEV_BACKEND depends on X86
XEN_PCIBACK_MODULE = ""
XEN_PCIBACK_MODULE:x86    = "kernel-module-xen-pciback"
XEN_PCIBACK_MODULE:x86-64 = "kernel-module-xen-pciback"
XEN_ACPI_PROCESSOR_MODULE = ""
#XEN_ACPI_PROCESSOR_MODULE:x86    = "kernel-module-xen-acpi-processor"
#XEN_ACPI_PROCESSOR_MODULE:x86-64 = "kernel-module-xen-acpi-processor"

LICENSE = "MIT"

QB_NETWORK_XEN_BRIDGE = "1"

inherit core-image
# Only inherit the qemuboot classes when building for a qemu machine
QB_QEMU_CLASSES = ""
QB_QEMU_CLASSES:qemuall = "qemuboot-xen-defaults qemuboot-xen-dtb qemuboot-testimage-network"
inherit ${QB_QEMU_CLASSES}

do_check_xen_state() {
    if [ "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' yes', 'no', d)}" = "no" ]; then
        die "DISTRO_FEATURES does not contain 'xen'"
    fi
}

addtask check_xen_state before do_rootfs

syslinux_iso_populate:append() {
        install -m 0444 ${STAGING_DATADIR}/syslinux/libcom32.c32 ${ISODIR}${ISOLINUXDIR}
        install -m 0444 ${STAGING_DATADIR}/syslinux/mboot.c32 ${ISODIR}${ISOLINUXDIR}
}

syslinux_hddimg_populate:append() {
        install -m 0444 ${STAGING_DATADIR}/syslinux/libcom32.c32 ${HDDDIR}${SYSLINUXDIR}
        install -m 0444 ${STAGING_DATADIR}/syslinux/mboot.c32 ${HDDDIR}${SYSLINUXDIR}
}

grubefi_populate:append() {
        install -m 0644 ${DEPLOY_DIR_IMAGE}/xen-${MACHINE}.gz ${DEST}${EFIDIR}/xen.gz
        install -m 0644 ${DEPLOY_DIR_IMAGE}/xen-${MACHINE} ${DEST}${EFIDIR}/xen
}

syslinux_populate:append() {
        install -m 0644 ${DEPLOY_DIR_IMAGE}/xen-${MACHINE}.gz ${DEST}/xen.gz
        install -m 0644 ${DEPLOY_DIR_IMAGE}/xen-${MACHINE} ${DEST}/xen
}

SYSLINUX_XEN_ARGS = "dom0=pvh dom0_mem=8G dom0_max_vcpus=4 dom0_vcpus_pin loglvl=all guest_loglvl=all com1=115200/3000000,8n1,0xfedc9000,4,reg-width=4,reg-shift=2 console=com1,vga console_timestamps=boot"
SYSLINUX_KERNEL_ARGS = "ramdisk_size=32768 root=/dev/ram0 console=hvc0 earlyprintk=xen console=ttyS0 panic=10 LABEL=boot debugshell=5"

build_syslinux_cfg () {
        echo "serial --unit=0 --speed=115200 --word=8 --parity=no --stop=1" >> ${SYSLINUX_CFG}
        echo "default=boot" >> ${SYSLINUX_CFG}
        echo "timeout=10" >> ${SYSLINUX_CFG}
        echo "menuentry 'boot'{" >> ${SYSLINUX_CFG}
        echo "multiboot2 /xen ${SYSLINUX_XEN_ARGS}" >> ${SYSLINUX_CFG}
        echo "module2 /bzImage ${SYSLINUX_KERNEL_ARGS}" >> ${SYSLINUX_CFG}
        echo "module2 /initrd" >> ${SYSLINUX_CFG}
        echo "}" >> ${SYSLINUX_CFG}
}

# Enable runqemu. eg: runqemu xen-image-minimal nographic slirp
WKS_FILE:x86-64 = "directdisk-xen.wks"
WKS_FILE:${MACHINE} = "qemuboot-xen-x86-64.wks"
QB_MEM ?= "-m 400"
QB_DEFAULT_KERNEL ?= "none"
QB_DEFAULT_FSTYPE ?= "wic"
QB_DEFAULT_FSTYPE:${MACHINE} = "wic iso"
QB_FSINFO ?= "wic:kernel-in-fs"
QB_SERIAL_OPT = "-serial mon:stdio"
# qemux86-64 machine does not include 'wic' in IMAGE_FSTYPES, which is needed
# to boot this image, so add it here:
IMAGE_FSTYPES:${MACHINE} += "wic iso"
# Networking: the qemuboot.bbclass default virtio network device works ok
# and so does the emulated e1000 -- choose according to the network device
# drivers that are present in your dom0 Linux kernel. To switch to e1000:
# QB_NETWORK_DEVICE = "-device e1000,netdev=net0,mac=@MAC@"
