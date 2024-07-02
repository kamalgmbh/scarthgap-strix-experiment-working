LICENSE = "GPL-2.0-only"

SRCREV ?= "0df9387c8983e1b1e72d8c574356f572342c03e6"

XEN_REL ?= "4.18"
XEN_BRANCH ?= "stable-${XEN_REL}"

SRC_URI = " \
    gitsm://xenbits.xen.org/qemu-xen.git;branch=${XEN_BRANCH} \
	file://0001-virtio-gpu-virgl-teach-it-to-get-the-QEMU-EGL-displa.patch \
	file://0002-virtio-gpu-refactor-generate_edid-function-to-virtio.patch \
	file://0003-virtio-gpu-replace-the-surface-with-null-surface-whe.patch \
	file://0004-virtio-gpu-free-BHs-by-implementing-unrealize.patch \
	file://0005-virtio-gpu-reset-gfx-resources-in-main-thread.patch \
	file://0006-virtio-Add-shared-memory-capability.patch \
	file://0007-virtio-gpu-CONTEXT_INIT-feature.patch \
	file://0008-virtio-gpu-hostmem.patch \
	file://0009-virtio-gpu-blob-prep.patch \
	file://0010-virtio-gpu-factor-out-restore-mapping.patch \
	file://0011-virtio-gpu-move-scanout-restoration-to-post_load.patch \
	file://0012-virtio-gpu-add-virtio-gpu-blob-vmstate-subsection.patch \
	file://0013-linux-headers-Update-to-kernel-headers-to-add-venus-.patch \
	file://0014-virtio-gpu-Configure-new-feature-flag-context_create.patch \
	file://0015-virtio-gpu-Support-context-init-feature-with-virglre.patch \
	file://0016-virtio-gpu-Don-t-require-udmabuf-when-blobs-and-virg.patch \
	file://0017-virtio-gpu-Introduce-virgl_gpu_resource-structure.patch \
	file://0018-virtio-gpu-Handle-resource-blob-commands.patch \
	file://0019-virtio-gpu-Resource-UUID.patch \
	file://0020-virtio-gpu-Support-Venus-capset.patch \
	file://0021-virtio-gpu-Initialize-Venus.patch \
	file://0022-virtio-gpu-make-blob-scanout-use-dmabuf-fd.patch \
	file://0023-virtio-gpu-fix-capset-query.patch \
	file://0024-ui-sdl-implement-dpy-dmabuf-functions.patch \
	file://0025-wip-Extend-event-notifier-API.patch \
	file://0026-wip-virtio-gpu-Support-async-per-ctx-fences.patch \
	file://0027-wip-Support-passing-fences-from-guest-to-host.patch \
	file://0028-virtio-gpu-enable-native_context-support.patch \
	file://0029-fixup-wip-virtio-gpu-Support-async-per-ctx-fences.patch \
	file://0030-virtio-gpu-make-set_scanout_blob-duplicates-the-dmab.patch \
	file://0031-Do-not-signal-fence-from-context-0-that-aren-t-ctx0-.patch \
	file://0032-upstream-in-progress.patch \
	file://0033-xen-pci-translate-irq-of-host-pci-device-to-gsi.patch \
	file://0034-xen-map-hostmem-to-guest.patch \
	file://0035-xen-Add-xc_domain_memory_mapping-back-to-map-mmio-fo.patch \
	file://0036-xen-Seperate-mmio-and-system-memory-in-xen_unmap_mem.patch \
	file://0037-xen-Skip-map_hva_to_gpfns-in-xen_unmap_memory_sectio.patch \
	file://0038-Rework-xen_map_memory_section-xen_unmap_memory_secti.patch \
	file://0039-hw-xen-avoid-remap-the-passthroug-device-memory-regi.patch \
	file://0040-hw-i386-pc_piix-add-fw_cfg-by-default-when-xen-enabl.patch \
	file://0041-xen-init-and-update-dynamically-hva-to-guest-physica.patch \
	file://0042-XEN.patch \
	file://0043-Fix-build-dependencies-on-libdrm-and-libgbm.patch \
	file://0001-add-XBGR8888-and-ABGR8888-in-drm_format_pixman_map.patch \
	file://set-skip-sanity-check.patch \
    "

#	file://dumpiov_change.patch 
LIC_FILES_CHKSUM ?= "file://COPYING;md5=441c28d2cf86e15a37fa47e15a72fbac"

PV = "${XEN_REL}+stable${SRCPV}"

S = "${WORKDIR}/git"

do_configure[depends] += "pkgconfig-native:do_populate_sysroot"
do_configure[depends] += "libsdl2:do_populate_sysroot"
do_configure[depends] += "libsdl2-native:do_populate_sysroot"

DEPENDS = " \
    xen \
    alsa-lib \
    pciutils \
    libpng \
    xen-tools \
    zlib \
    libcap-ng \
    pixman \
    ninja-native \
    meson-native \
    virglrenderer \
    libsdl2 \
    libusb \
    libtasn1 \
    gtk+3 \
    glib-2.0-native \
    glib-2.0 \
    libpcre \
    libxkbcommon \
    dbus \
    python3-packaging-native \
"
FILES:${PN} += "/usr/lib \
		/usr/share/* \
		/usr/share/qemu/keymaps/en-us       \
                /usr/share/qemu/keymaps/commons     \
                /usr/share/qemu/keymaps/modifiers   \
                "
FILES:${PN}-dbg += "/usr/lib/.debug/* /usr/libexec/.debug/*"
FILES:${PN}-extra-keymaps = "/usr/share/qemu/keymaps/*"
FILES:${PN}-extra-roms = "/usr/share/qemu/*"
INSANE_SKIP:${PN} += "arch"
FILES:${PN}-utils = "/usr/libexec/* \
                     /usr/bin/qemu-pr-helper \
"
PACKAGES += "${PN}-extra-keymaps ${PN}-extra-roms"
PACKAGES =+ "${PN}-utils"

EXTRA_OECONF += " \
    --enable-xen \
    --enable-trace-backends=log \
    --disable-kvm \
    --disable-docs \
    --disable-guest-agent \
    --disable-vfio-user-server \
    --enable-system \
    --audio-drv-list=alsa \
    --target-list=i386-softmmu,x86_64-softmmu \
    --enable-debug \
    --libdir=/usr/lib \
    --includedir=/usr/include \
    --extra-cflags="-DXC_WANT_COMPAT_EVTCHN_API=1 \
        -DXC_WANT_COMPAT_GNTTAB_API=1 \
        -DXC_WANT_COMPAT_MAP_FOREIGN_API=1 \
        -DXC_WANT_COMPAT_DEVICEMODEL_API=1 \
        " \
    --extra-ldflags="-Wl,-rpath,/usr/xen/lib" \
    --bindir=/usr/bin \
    --datadir=/usr/share \
    --localstatedir=/var \
    --docdir=/usr/share/doc \
    --mandir=/usr/share/man \
    --libexecdir=/usr/libexec \
    --firmwarepath=/usr/share/qemu \
    --python=python3 \
    --enable-sdl \
    --enable-opengl \
    --enable-virglrenderer \
    --enable-libusb \
    --enable-dbus-display \
    --disable-xkbcommon \
    --cpu=x86_64 \
"

EXTRA_OECONF:remove = "--disable-static"

do_configure(){
    ${S}/configure --prefix=${prefix} --sysconfdir=${sysconfdir} ${EXTRA_OECONF}
        sed -i -s '/^CONFIG_L2TPV3=y/d' ${S}/build/config-host.mak
}

EXTRA_OEMAKE += "AUTOCONF_HOST='--host=`arch`' DESTDIR=${D} STRIP=''"

CFLAGS += "-Wno-uninitialized"

INC_PR = "r17"

do_compile:prepend() {
        rm -f ${S}/build/i386-softmmu-config-devices.mak
        rm -f ${S}/build/x86_64-softmmu-config-devices.mak
        oe_runmake ${S}/build/i386-softmmu-config-devices.mak
        oe_runmake ${S}/build/x86_64-softmmu-config-devices.mak

        dev_disable() {
                sed -i -e 's/\('$1'=\).*$/\1n/' ${S}/build/i386-softmmu-config-devices.mak
                sed -i -e 's/\('$1'=\).*$/\1n/' ${S}/build/x86_64-softmmu-config-devices.mak
        }
        dev_enable() {
                sed -i -e 's/\('$1'=\).*$/\1y/' ${S}/build/i386-softmmu-config-devices.mak
                sed -i -e 's/\('$1'=\).*$/\1y/' ${S}/build/x86_64-softmmu-config-devices.mak
        }

	dev_enable CONFIG_AC97
	dev_enable CONFIG_ACPI
	dev_enable CONFIG_ACPI_CPU_HOTPLUG
	dev_enable CONFIG_ACPI_CXL
	dev_enable CONFIG_ACPI_ERST
	dev_enable CONFIG_ACPI_HMAT
	dev_enable CONFIG_ACPI_HW_REDUCED
	dev_enable CONFIG_ACPI_MEMORY_HOTPLUG
	dev_enable CONFIG_ACPI_NVDIMM
	dev_enable CONFIG_ACPI_PCI
	dev_enable CONFIG_ACPI_PCIHP
	dev_enable CONFIG_ACPI_PIIX4
	dev_enable CONFIG_ACPI_SMBUS
	dev_enable CONFIG_ACPI_VIOT
	dev_enable CONFIG_ACPI_VMGENID
	dev_enable CONFIG_ACPI_X86
	dev_enable CONFIG_ACPI_X86_ICH
	dev_enable CONFIG_ADLIB
	dev_enable CONFIG_AHCI
	dev_enable CONFIG_AHCI_ICH9
	dev_enable CONFIG_AMD_IOMMU
	dev_enable CONFIG_APIC
	dev_enable CONFIG_APM
	dev_enable CONFIG_APPLESMC
	dev_enable CONFIG_ATI_VGA
	dev_enable CONFIG_BITBANG_I2C
	dev_enable CONFIG_BOCHS_DISPLAY
	dev_enable CONFIG_CAN_BUS
	dev_enable CONFIG_CAN_CTUCANFD
	dev_enable CONFIG_CAN_CTUCANFD_PCI
	dev_enable CONFIG_CAN_PCI
	dev_enable CONFIG_CAN_SJA1000
	dev_enable CONFIG_CS4231A
	dev_enable CONFIG_CXL
	dev_enable CONFIG_CXL_MEM_DEVICE
	dev_enable CONFIG_DDC
	dev_enable CONFIG_DIMM
	dev_enable CONFIG_E1000E_PCI_EXPRESS
	dev_enable CONFIG_E1000_PCI
	dev_enable CONFIG_EDID
	dev_enable CONFIG_EDU
	dev_enable CONFIG_EEPRO100_PCI
	dev_enable CONFIG_ES1370
	dev_enable CONFIG_ESP
	dev_enable CONFIG_ESP_PCI
	dev_enable CONFIG_FDC
	dev_enable CONFIG_FDC_ISA
	dev_enable CONFIG_FW_CFG_DMA
	dev_enable CONFIG_GENERIC_LOADER
	dev_enable CONFIG_GUEST_LOADER
	dev_enable CONFIG_GUS
	dev_enable CONFIG_HDA
	dev_enable CONFIG_HPET
	dev_enable CONFIG_I2C
	dev_enable CONFIG_I2C_BACKEND
	dev_enable CONFIG_I2C_BUILTIN
	dev_enable CONFIG_I440FX
	dev_enable CONFIG_I8254
	dev_enable CONFIG_I8257
	dev_enable CONFIG_I8259
	dev_enable CONFIG_I82801B11
	dev_enable CONFIG_IDE_CORE
	dev_enable CONFIG_IDE_ISA
	dev_enable CONFIG_IDE_PCI
	dev_enable CONFIG_IDE_PIIX
	dev_enable CONFIG_IDE_QDEV
	dev_enable CONFIG_IMX_USBPHY
	dev_enable CONFIG_IOAPIC
	dev_enable CONFIG_IOH3420
	dev_enable CONFIG_IPACK
	dev_enable CONFIG_IPMI
	dev_enable CONFIG_IPMI_EXTERN
	dev_enable CONFIG_IPMI_LOCAL
	dev_enable CONFIG_IPMI_SSIF
	dev_enable CONFIG_ISAPC
	dev_enable CONFIG_ISA_BUS
	dev_enable CONFIG_ISA_DEBUG
	dev_enable CONFIG_ISA_IPMI_BT
	dev_enable CONFIG_ISA_IPMI_KCS
	dev_enable CONFIG_ISA_TESTDEV
	dev_enable CONFIG_IVSHMEM_DEVICE
	dev_enable CONFIG_LPC_ICH9
	dev_enable CONFIG_LSI_SCSI_PCI
	dev_enable CONFIG_MC146818RTC
	dev_enable CONFIG_MEGASAS_SCSI_PCI
	dev_enable CONFIG_MEM_DEVICE
	dev_enable CONFIG_MICROVM
	dev_enable CONFIG_MPTSAS_SCSI_PCI
	dev_enable CONFIG_MSI_NONBROKEN
	dev_enable CONFIG_NE2000_COMMON
	dev_enable CONFIG_NE2000_ISA
	dev_enable CONFIG_NE2000_PCI
	dev_enable CONFIG_NMC93XX_EEPROM
	dev_enable CONFIG_NVDIMM
	dev_enable CONFIG_NVME_PCI
	dev_enable CONFIG_PAM
	dev_enable CONFIG_PARALLEL
	dev_enable CONFIG_PC
	dev_enable CONFIG_PCI
	dev_enable CONFIG_PCIE_PORT
	dev_enable CONFIG_PCI_DEVICES
	dev_enable CONFIG_PCI_EXPRESS
	dev_enable CONFIG_PCI_EXPRESS_GENERIC_BRIDGE
	dev_enable CONFIG_PCI_EXPRESS_Q35
	dev_enable CONFIG_PCI_I440FX
	dev_enable CONFIG_PCI_IPMI_BT
	dev_enable CONFIG_PCI_IPMI_KCS
	dev_enable CONFIG_PCI_TESTDEV
	dev_enable CONFIG_PCKBD
	dev_enable CONFIG_PCNET_COMMON
	dev_enable CONFIG_PCNET_PCI
	dev_enable CONFIG_PCSPK
	dev_enable CONFIG_PC_ACPI
	dev_enable CONFIG_PC_PCI
	dev_enable CONFIG_PFLASH_CFI01
	dev_enable CONFIG_PIIX3
	dev_enable CONFIG_PS2
	dev_enable CONFIG_PVPANIC_COMMON
	dev_enable CONFIG_PVPANIC_ISA
	dev_enable CONFIG_PVPANIC_PCI
	dev_enable CONFIG_PXB
	dev_enable CONFIG_Q35
	dev_enable CONFIG_ROCKER
	dev_enable CONFIG_RTL8139_PCI
	dev_enable CONFIG_SB16
	dev_enable CONFIG_SCSI
	dev_enable CONFIG_SD
	dev_enable CONFIG_SDHCI
	dev_enable CONFIG_SDHCI_PCI
	dev_enable CONFIG_SERIAL
	dev_enable CONFIG_SERIAL_ISA
	dev_enable CONFIG_SERIAL_PCI
	dev_enable CONFIG_SERIAL_PCI_MULTI
	dev_enable CONFIG_SGA
	dev_enable CONFIG_SMBIOS
	dev_enable CONFIG_SMBUS
	dev_enable CONFIG_SMBUS_EEPROM
	dev_enable CONFIG_TEST_DEVICES
	dev_enable CONFIG_TPM_BACKEND
	dev_enable CONFIG_TPM_CRB
	dev_enable CONFIG_TPM_EMULATOR
	dev_enable CONFIG_TPM_PASSTHROUGH
	dev_enable CONFIG_TPM_TIS
	dev_enable CONFIG_TPM_TIS_ISA
	dev_enable CONFIG_TULIP
	dev_enable CONFIG_USB
	dev_enable CONFIG_USB_AUDIO
	dev_enable CONFIG_USB_CANOKEY
	dev_enable CONFIG_USB_EHCI
	dev_enable CONFIG_USB_EHCI_PCI
	dev_enable CONFIG_USB_NETWORK
	dev_enable CONFIG_USB_OHCI
	dev_enable CONFIG_USB_OHCI_PCI
	dev_enable CONFIG_USB_SERIAL
	dev_enable CONFIG_USB_SMARTCARD
	dev_enable CONFIG_USB_STORAGE_CLASSIC
	dev_enable CONFIG_USB_STORAGE_CORE
	dev_enable CONFIG_USB_STORAGE_MTP
	dev_enable CONFIG_USB_STORAGE_UAS
	dev_enable CONFIG_USB_TABLET_WACOM
	dev_enable CONFIG_USB_U2F
	dev_enable CONFIG_USB_UHCI
	dev_enable CONFIG_USB_XHCI
	dev_enable CONFIG_USB_XHCI_NEC
	dev_enable CONFIG_USB_XHCI_PCI
	dev_enable CONFIG_USB_XHCI_SYSBUS
	dev_enable CONFIG_VFIO
	dev_enable CONFIG_VFIO_IGD
	dev_enable CONFIG_VFIO_PCI
	dev_enable CONFIG_VGA
	dev_enable CONFIG_VGA_CIRRUS
	dev_enable CONFIG_VGA_ISA
	dev_enable CONFIG_VGA_PCI
	dev_enable CONFIG_VHOST_SCSI
	dev_enable CONFIG_VHOST_USER_BLK
	dev_enable CONFIG_VHOST_USER_FS
	dev_enable CONFIG_VHOST_USER_GPIO
	dev_enable CONFIG_VHOST_USER_GPU
	dev_enable CONFIG_VHOST_USER_I2C
	dev_enable CONFIG_VHOST_USER_INPUT
	dev_enable CONFIG_VHOST_USER_RNG
	dev_enable CONFIG_VHOST_USER_SCSI
	dev_enable CONFIG_VHOST_USER_VGA
	dev_enable CONFIG_VHOST_USER_VSOCK
	dev_enable CONFIG_VHOST_VSOCK
	dev_enable CONFIG_VIRTIO
	dev_enable CONFIG_VIRTIO_BALLOON
	dev_enable CONFIG_VIRTIO_BLK
	dev_enable CONFIG_VIRTIO_CRYPTO
	dev_enable CONFIG_VIRTIO_GPIO
	dev_enable CONFIG_VIRTIO_GPU
	dev_enable CONFIG_VIRTIO_I2C
	dev_enable CONFIG_VIRTIO_INPUT
	dev_enable CONFIG_VIRTIO_INPUT_HOST
	dev_enable CONFIG_VIRTIO_IOMMU
	dev_enable CONFIG_VIRTIO_MEM
	dev_enable CONFIG_VIRTIO_MEM_SUPPORTED
	dev_enable CONFIG_VIRTIO_MMIO
	dev_enable CONFIG_VIRTIO_NET
	dev_enable CONFIG_VIRTIO_PCI
	dev_enable CONFIG_VIRTIO_PMEM
	dev_enable CONFIG_VIRTIO_PMEM_SUPPORTED
	dev_enable CONFIG_VIRTIO_RNG
	dev_enable CONFIG_VIRTIO_SCSI
	dev_enable CONFIG_VIRTIO_SERIAL
	dev_enable CONFIG_VIRTIO_TEE
	dev_enable CONFIG_VIRTIO_VGA
	dev_enable CONFIG_VMMOUSE
	dev_enable CONFIG_VMPORT
	dev_enable CONFIG_VMWARE_VGA
	dev_enable CONFIG_VMW_PVSCSI_SCSI_PCI
	dev_enable CONFIG_VMXNET3_PCI
	dev_enable CONFIG_VTD
	dev_enable CONFIG_WDT_IB6300ESB
	dev_enable CONFIG_WDT_IB700
	dev_enable CONFIG_X86_IOMMU
	dev_enable CONFIG_XEN_IGD_PASSTHROUGH
	dev_enable CONFIG_XIO3130
}

EXTRA_OECONF += " --audio-drv-list=alsa "

do_install:append() {
	install -d ${D}/usr
	install -d ${D}/usr/lib
	install -d ${D}/usr/share
        cp -rL ${S}/build/qemu-bundle/usr/ ${D}
}

DEPENDS += "python3-packaging-native"

