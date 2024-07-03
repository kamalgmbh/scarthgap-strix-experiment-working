YOCTO_BRANCH="kirkstone"
MACHINE='stx'
source ./oe-init-build-env build-${MACHINE}-${YOCTO_BRANCH}

tee conf/auto.conf <<EOF
DL_DIR ?= "\${TOPDIR}/../downloads"
SSTATE_DIR ?= "\${TOPDIR}/../sstate-cache"
MACHINE = "${MACHINE}"
DISTRO = "poky-amd"
BBMASK += "recipes-core/initrdscripts/initramfs-module-install-efi_1.0.bbappend"
EOF

bitbake-layers add-layer ../meta-openembedded/meta-oe
bitbake-layers add-layer ../meta-openembedded/meta-python
bitbake-layers add-layer ../meta-openembedded/meta-networking
bitbake-layers add-layer ../meta-dpdk
bitbake-layers add-layer ../meta-amd/meta-amd-distro
bitbake-layers add-layer ../meta-amd/meta-amd-bsp
bitbake-layers add-layer ../meta-openembedded/meta-filesystems
bitbake-layers add-layer ../meta-virtualization

bitbake core-image-sato -k
