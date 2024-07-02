CORE_IMAGE_EXTRA_INSTALL += " kernel-modules"
IMAGE_INSTALL:append = " kernel-modules nvme-cli kernel-module-ntb  spitools linux-firmware spidev-test rpm opkg lshw mokutil autoconf stress-ng hwloc numactl packagegroup-core-buildessential"

DISTRO_FEATURES:append = " ptest pthread tpm systemd posix-testsuite"
VIRTUAL-RUNTIME_init_manager = "systemd"

