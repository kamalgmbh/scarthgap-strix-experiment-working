KBRANCH ?= "linux-6.8.y"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
FILESEXTRAPATHS:prepend := "${THISDIR}/linux-yocto-6.8:"

#LIC_FILES_CHKSUM ?= "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

UPSTREAM_CHECK_GITTAGREGEX = "(?P<pver>\d+\.\d+(\.\d+)*)"

RECIPE_NO_UPDATE_REASON = "Recipe is updated through a separate process"

# Skip processing of this recipe if it is not explicitly specified as the
# PREFERRED_PROVIDER for virtual/kernel. This avoids network access required
# by the use of AUTOREV SRCREVs, which are the default for this recipe.
python () {
    if d.getVar("KERNEL_PACKAGE_NAME") == "kernel" and d.getVar("PREFERRED_PROVIDER_virtual/kernel") != d.getVar("PN"):
        d.delVar("BB_DONT_CACHE")
        raise bb.parse.SkipRecipe("Set PREFERRED_PROVIDER_virtual/kernel to %s to enable it" % (d.getVar("PN")))
}

EXTRA_OECONF += " KARCH=${TARGET_ARCH}"

DEPENDS += "xz-native bc-native"
DEPENDS:append:aarch64 = " libgcc"
KERNEL_CC:append:aarch64 = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD:append:aarch64 = " ${TOOLCHAIN_OPTIONS}"

DEPENDS:append:nios2 = " libgcc"
KERNEL_CC:append:nios2 = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD:append:nios2 = " ${TOOLCHAIN_OPTIONS}"

DEPENDS:append:arc = " libgcc"
KERNEL_CC:append:arc = " ${TOOLCHAIN_OPTIONS}"
KERNEL_LD:append:arc = " ${TOOLCHAIN_OPTIONS}"

KERNEL_FEATURES:append:qemuall=" features/debug/printk.scc"

KERNEL_FEATURES:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'numa', 'features/numa/numa.scc', '', d)}"
KERNEL_FEATURES:append = " ${@bb.utils.contains('MACHINE_FEATURES', 'vfat', 'cfg/fs/vfat.scc', '', d)}"


# A KMACHINE is the mapping of a yocto $MACHINE to what is built
# by the kernel. This is typically the branch that should be built,
# and it can be specific to the machine or shared
# KMACHINE = "UNDEFINED"

LINUX_VERSION_EXTENSION ??= "-yocto-${LINUX_KERNEL_TYPE}"

# Pick up shared functions
#inherit kernel
#inherit kernel-yocto

B = "${WORKDIR}/linux-${PACKAGE_ARCH}-${LINUX_KERNEL_TYPE}-build"


# enable kernel-sample for oeqa/runtime/cases's ksample.py test
KERNEL_FEATURES:append:qemuall=" features/kernel-sample/kernel-sample.scc"

KERNEL_DEBUG_OPTIONS ?= "stack"
KERNEL_EXTRA_ARGS:append:x86-64 = " ${@bb.utils.contains('KERNEL_DEBUG_OPTIONS', 'stack', 'HOST_LIBELF_LIBS="-L${RECIPE_SYSROOT_NATIVE}/usr/lib/pkgconfig/../../../usr/lib/ -lelf"', '', d)}"

do_devshell:prepend() {
    # setup native pkg-config variables (kconfig scripts call pkg-config directly, cannot generically be overriden to pkg-config-native)
    d.setVar("PKG_CONFIG_DIR", "${STAGING_DIR_NATIVE}${libdir_native}/pkgconfig")
    d.setVar("PKG_CONFIG_PATH", "${PKG_CONFIG_DIR}:${STAGING_DATADIR_NATIVE}/pkgconfig")
    d.setVar("PKG_CONFIG_LIBDIR", "${PKG_CONFIG_DIR}")
    d.setVarFlag("PKG_CONFIG_SYSROOT_DIR", "unexport", "1")
    d.appendVar("OE_TERMINAL_EXPORTS", " PKG_CONFIG_DIR PKG_CONFIG_PATH PKG_CONFIG_LIBDIR PKG_CONFIG_SYSROOT_DIR")
}



# board specific branches
#KBRANCH:qemuarm  ?= "v5.15/standard/arm-versatile-926ejs"
#KBRANCH:qemuarm64 ?= "v5.15/standard/qemuarm64"
#KBRANCH:qemumips ?= "v5.15/standard/mti-malta32"
#KBRANCH:qemuppc  ?= "v5.15/standard/qemuppc"
#KBRANCH:qemuriscv64  ?= "v5.15/standard/base"
#KBRANCH:qemuriscv32  ?= "v5.15/standard/base"
#KBRANCH:qemux86  ?= "v5.15/standard/base"
#KBRANCH:qemux86-64 ?= "v5.15/standard/base"
#KBRANCH:qemumips64 ?= "v5.15/standard/mti-malta64"

SRCREV_machine:qemuarm ?= "21687086c27bb112f19b0aac455d800961c0b830"
SRCREV_machine:qemuarm64 ?= "7144f86a73fe2ffe4fe57c9e6cf28d8fc8db4b6a"
SRCREV_machine:qemumips ?= "557c06060cb218ade536fccc66f8f3e755537f31"
SRCREV_machine:qemuppc ?= "db19dbdcdf51b9d2a071dcf180ba9e20b8286e9b"
SRCREV_machine:qemuriscv64 ?= "024d08fb706170a9723e9751e505681f9d4c7ab6"
SRCREV_machine:qemuriscv32 ?= "024d08fb706170a9723e9751e505681f9d4c7ab6"
SRCREV_machine:qemux86 ?= "024d08fb706170a9723e9751e505681f9d4c7ab6"
SRCREV_machine:qemux86-64 ?= "024d08fb706170a9723e9751e505681f9d4c7ab6"
SRCREV_machine:qemumips64 ?= "6f1dbe8c258d49f4dba59827124dfe9aa2c151db"
SRCREV_machine ?= "024d08fb706170a9723e9751e505681f9d4c7ab6"
SRCREV_meta ?= "441f5fe00073620cec471166cf6e94c4ef9c69b2"


# set your preferred provider of linux-yocto to 'linux-yocto-upstream', and you'll
# get the <version>/base branch, which is pure upstream -stable, and the same
# meta SRCREV as the linux-yocto-standard builds. Select your version using the
# normal PREFERRED_VERSION settings.
#BBCLASSEXTEND = "devupstream:target"
SRCREV_machine:class-devupstream ?= "8020ae3c051d1c9ec7b7a872e226f9720547649b"
#PN:class-devupstream = "linux-yocto-upstream"
KBRANCH:class-devupstream = "linux-6.8.y"

INC_PR := "r1"
SRCBRANCHARG = "${@['nobranch=1', 'branch=${KBRANCH}'][d.getVar('KBRANCH', True) != '']}"

FILESOVERRIDES:append := ":${@bb.parse.vars_from_file(d.getVar('FILE', False),d)[1] or ''}"

KERNELURI ?= "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux.git;protocol=https;name=machine;branch=linux-6.8.y"
YOCTO_META ?= "git://git.yoctoproject.org/yocto-kernel-cache;type=kmeta;name=meta;branch=yocto-6.5;destsuffix=yocto-kmeta"
SRC_URI = "${KERNELURI};${SRCBRANCHARG} ${YOCTO_META} file://usb.cfg file://install.cfg file://kernel.cfg file://isomount.cfg"
SRC_URI += " \
	file://0001-KVM-Relax-BUG_ON-argument-validation.patch \
	file://0002-KVM-mmu-Introduce-kvm_follow_pfn.patch \
	file://0003-KVM-mmu-Improve-handling-of-non-refcounted-pfns.patch \
	file://0004-KVM-Migrate-kvm_vcpu_map-to-__kvm_follow_pfn.patch \
	file://0005-KVM-x86-Migrate-to-__kvm_follow_pfn.patch \
	file://0006-KVM-x86-mmu-Track-if-sptes-refer-to-refcounted-pages.patch \
	file://0007-KVM-x86-mmu-Handle-non-refcounted-pages.patch \
	file://0008-drm-virtio-Support-fence-passing-feature.patch \
	file://0009-drm-virtio-allow-import-if-the-other-device-is-also-.patch \
	file://0010-drm-virtio-don-t-create-a-context-with-default-param.patch \
	file://0011-xen-grants-update-initialization-order-of-xen-grant-.patch \
	file://0012-pci-xen-acpi-register-gsi-for-xen-pvh.patch \
	file://0013-xen-privcmd-add-IOCTL_PRIVCMD_GSI_FROM_IRQ.patch \
	file://0014-swiotlb-xen-expose-xen_phys_to_dma-and-range_straddl.patch \
	file://0015-xen-introduce-a-new-DMA-ops-for-physically-contiguou.patch \
	file://0016-xen-x86-implement-create_contiguous_region-function-.patch \
	file://0017-xen-pvh-xen_pvh_destroy_contiguous_region.patch \
	file://0018-xen-privcmd-to-map-host-virtual-address-space-to-gue.patch \
	file://0019-hack-don-t-fail-privcmd_ioctl_map_hva_to_gpfns-when-.patch \
	file://0020-improve-privcmd_ioctl_map_hva_to_gpfns.patch \
	file://0021-xen-pvh-change-discontig_frames-from-global-to-local.patch \
	file://0022-xen-pvh-disable-cpu-power-management.patch \
	file://0023-xen-pvh-return-true-from-xen_initdom_restore_msi.patch \
	file://0024-xen-x86-cleanup-the-checking-for-max-order.patch \
	file://0025-mm-mmap-fix-MAP_FAILED-to-mmap-the-iomem-which-reser.patch \
	file://0026-xen-pvh-register-suspend-notifiers.patch \
	file://0027-xen-do-not-resume-on-ACPI-S3.patch \
	file://0028-drm-amdgpu-set-passthrough-mode-for-xen-pvh-hvm.patch \
	file://0029-drm-amdgpu-Avoids-registers-access-if-device-is-phys.patch \
	file://0030-xen-init-and-update-dynamically-hva-to-guest-physica.patch \
	file://0031-drm-amdgpu-remove-invalid-resource-start-check.patch \
	file://0032-xen-immediately-call-hva_range_update.patch \
	file://0033-xen-netfront-Add-missing-skb_mark_for_recycle.patch \
	file://0034-Revert-xen-immediately-call-hva_range_update.patch \
	file://0035-Revert-xen-init-and-update-dynamically-hva-to-guest-.patch \
	file://0036-xen-init-and-update-dynamically-hva-to-guest-physica.patch \
	file://0037-fixup-xen-privcmd-add-IOCTL_PRIVCMD_GSI_FROM_IRQ.patch \
	file://0038-xen-sync-elfnote.h-from-xen-tree.patch \
	file://0039-x86-pvh-Make-PVH-entrypoint-PIC-for-x86-64.patch \
	file://0040-x86-pvh-Set-phys_base-when-calling-xen_prepare_pvh.patch \
	file://0041-x86-kernel-Move-page-table-macros-to-new-header.patch \
	file://0042-x86-pvh-Add-64bit-relocation-page-tables.patch \
	file://0043-fixup-xen-init-and-update-dynamically-hva-to-guest-p.patch \
	file://0044-drm-amdgpu-fix-visible-VRAM-handling-during-faults.patch \
	file://0047-fixup-xen-init-and-update-dynamically-hva-to-guest-p.patch \
	file://0048-xen-hostmem-premap-regions-to-avoid-first-access-lat.patch \
	file://0049-fixup-fixup-xen-init-and-update-dynamically-hva-to-g.patch \
	file://0001-amdgpu-GL2C_CTRL-register-update-for-GL2C-cache-size.patch \
	file://0002-amdgpu-gfx-harvesting-for-wgp-and-rbs.patch \
	file://0003-Reduce-CPU-L3-cache-size-from-Kernel-only-works-in-b.patch \
	file://0004-Add-error-handling.patch \
	file://0005-wgp-wgp-harvesting-based-on-inactive-WGP.patch \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"
KMETA = "kernel-meta"
KCONF_BSP_AUDIT_LEVEL = "1"

KERNEL_DEVICETREE:qemuarmv5 = "versatile-pb.dtb"

#COMPATIBLE_MACHINE = "^(qemuarm|qemuarmv5|qemuarm64|qemux86|qemuppc|qemuppc64|qemumips|qemumips64|qemux86-64|qemuriscv64|qemuriscv32)$"
KBRANCH = "linux-6.8.y"

SRCREV_machine = "e8f897f4afef0031fe618a8e94127a0934896aba"
LINUX_VERSION = "6.8"

KMACHINE = "common-pc-64"
SRCREV_meta = "8368cc1f9d0a6f8681602a918746df3b3932bad6"

LINUX_VERSION_EXTENSION = "-amd-${LINUX_KERNEL_TYPE}"

COMPATIBLE_MACHINE = "${MACHINE}"
SRC_URI += "${@bb.utils.contains('DISTRO_FEATURES', 'xen', ' file://xen.scc', '', d)}"

