require linux-yocto-6.8.inc

PR := "${INC_PR}.0"

SRCREV_machine = "e8f897f4afef0031fe618a8e94127a0934896aba"

inherit kernel

do_configure() {
   :; 
}

#S = "${WORKDIR}"


# Ensure that the kernel modules are installed under /usr/lib/modules instead of /lib/modules
do_install:append() {
    # Ensure the installation directory exists
    install -d ${D}/usr/lib/modules/${KERNEL_VERSION}/

    # Install modules
    oe_runmake INSTALL_MOD_PATH=${D}/usr modules_install

	 # Remove unnecessary files
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/Module.symvers
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/modules.*.bin
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/modules.alias
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/modules.dep
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/modules.devname
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/modules.symbols
    rm -rf ${D}/usr/lib/modules/${KERNEL_VERSION}/modules.softdep
}

# Include kernel modules and other necessary files
FILES:${KERNEL_PACKAGE_NAME}-modules = "\
    /lib/modules/${KERNEL_VERSION}/kernel/* \
    /lib/modules/${KERNEL_VERSION}/modules.builtin \
    /lib/modules/${KERNEL_VERSION}/modules.order \
    /lib/modules/${KERNEL_VERSION}/modules.builtin.modinfo \
    /lib/modules/${KERNEL_VERSION}/build \
    /boot \
"
# Include /boot and kernel-related files in the main package
FILES:${PN} += "\
    /boot \
    /usr/lib/modules/${KERNEL_VERSION}/kernel/crypto/*.ko \
    /usr/lib/modules/${KERNEL_VERSION}/kernel/lib/*.ko \
    /usr/lib/modules/${KERNEL_VERSION}/kernel/net/**/*.ko \
    /usr/lib/modules/${KERNEL_VERSION}/kernel/fs/**/*.ko \
"

EXTRA_OEMAKE += "INSTALL_MOD_STRIP=1"

INSANE_SKIP:${PN} += "already-stripped"

VIRTUAL-RUNTIME_init_manager = "systemd"

PREFERRED_PROVIDER_virtual/kernel = "linux-yocto"
