LICENSE = "GPL-2.0-only"
SRCREV_repo1 ?= "ba91d0292e593df8528b66f99c1b0b14fadc8e16"
SRCREV_repo2 ?= "b51ef8f3201669b2288104c28546fc72532a1ea4"
SRCREV_repo3 ?= "830bf8e1e4749ad65c51b6a1d0d769ae689404ba"
SRCREV_repo4 ?= "f4153a09f87cbb9c826d8fc12c74642bb2d879ea"
SRCREV_repo5 ?= "abfc8ff81df4067f309032467785e06975678f0d"
SRCREV_repo6 ?= "cfff805481bdea27f900c32698171286542b8d3c"
SRCREV_repo7 ?= "370b5944c046bab043dd8b133727b2135af7747a"
SRCREV_repo8 ?= "e9ebfa7e77a6bee77df44e096b100e7131044059"
SRCREV_repo9 ?= "59dc0013f9f29fcf212fe4911c78e734263ce24c"
SRCREV_repo10 ?= "86add13493e5c881d7e4ba77fb91c1f57752b3a4"
SRCREV_repo11 ?= "83d4e1ebef3588fae48b69a7352cc21801cb70bc"
SRCREV_repo12 ?= "f4153a09f87cbb9c826d8fc12c74642bb2d879ea"
SRCREV_repo13 ?= "a6f06bf2fd3b832822cd4e9e554b7d47f32ec084"
SRCREV_repo14 ?= "c53fdab93af76106b963216d85897614b996f8b6"
SRCREV_repo15 ?= "fa84d185c0fefa7d612759e02a26def2f948fe34"
SRCREV_repo16 ?= "2196000605e45d91097147c9c71f26b72af58003"
SRCREV_repo17 ?= "890ca2f401924cdcb88f493950b04700bbe52db3"
SRCREV_repo18 ?= "2070f8ad9151dc8f3a73bffaa146b5e6937a583f"

XEN_REL ?= "edk2-stable202305"
XEN_BRANCH ?= "master"


SRC_URI = " \
    git://github.com/tianocore/edk2.git;branch=${XEN_BRANCH};protocol=https;name=repo1 \
    file://0001-ovmf-update-path-to-native-BaseTools.patch \
    file://0002-BaseTools-makefile-amd.patch \
    file://0003-ovmf-Update-to-latest-amd.patch \
    file://0006-reproducible-amd.patch \
    file://0001-OvmfPkg-OvmfXen-Fix-S3-suspend-resume-path.patch \
    file://0002-Update-submodules-url-for-internal-build.patch \
    file://0004-SWDEV-452110-do-not-add-variable-range-MTRR-for-pci-.patch \
    file://0005-OvmfPkg-OvmfXen-Enable-virtio-net.patch \
    file://0006-NetworkPkg-Disable-completely-PXE.patch \
    git://github.com/ucb-bar/berkeley-softfloat-3.git;branch=${XEN_BRANCH};protocol=https;name=repo2;destsuffix=${S}/ArmPkg/Library/ArmSoftFloatLib/berkeley-softfloat-3 \
    git://github.com/openssl/openssl.git;branch="OpenSSL_1_1_1-stable";protocol=https;name=repo3;destsuffix=${S}/CryptoPkg/Library/OpensslLib/openssl \
    git://github.com/pyca/cryptography.git;branch=main;protocol=https;name=repo15;destsuffix=${S}/CryptoPkg/Library/OpensslLib/openssl/pyca-cryptography \
    git://github.com/C2SP/wycheproof.git;branch=master;protocol=https;name=repo16;destsuffix=${S}/CryptoPkg/Library/OpensslLib/openssl/wycheoproof \
    git://github.com/krb5/krb5.git;branch="krb5-1.17";protocol=https;name=repo17;destsuffix=${S}/CryptoPkg/Library/OpensslLib/openssl/krb5 \
    git://boringssl.googlesource.com/boringssl;branch=master;protocol=https;name=repo18;destsuffix=${S}/CryptoPkg/Library/OpensslLib/openssl/boringssl \
    git://github.com/google/brotli.git;branch=${XEN_BRANCH};protocol=https;name=repo4;destsuffix=${S}/BaseTools/Source/C/BrotliCompress/brotli \
    git://github.com/kkos/oniguruma.git;branch=${XEN_BRANCH};protocol=https;name=repo5;destsuffix=${S}/MdeModulePkg/Universal/RegularExpressionDxe/oniguruma \
    git://github.com/devicetree-org/pylibfdt.git;branch=main;protocol=https;name=repo6;destsuffix=${S}/MdePkg/Library/BaseFdtLib/libfdt \
    git://github.com/MIPI-Alliance/public-mipi-sys-t.git;branch=main;protocol=https;name=repo7;destsuffix=${S}/MdePkg/Library/MipiSysTLib/mipisyst \
    git://github.com/google/googletest.git;branch=main;protocol=https;name=repo13;destsuffix=${S}/MdePkg/Library/MipiSysTLib/mipisyst/external/googletest \
    git://github.com/zeux/pugixml.git;branch=master;protocol=https;name=repo14;destsuffix=${S}/MdePkg/Library/MipiSysTLib/mipisyst/external/pugixml \
    git://github.com/akheron/jansson.git;branch=${XEN_BRANCH};protocol=https;name=repo8;destsuffix=${S}/RedfishPkg/Library/JsonLib/jansson \
    git://github.com/tianocore/edk2-cmocka.git;branch=${XEN_BRANCH};protocol=https;name=repo9;destsuffix=${S}/UnitTestFrameworkPkg/Library/CmockaLib/cmocka \
    git://github.com/google/googletest.git;branch="v1.12.x";protocol=https;name=repo10;destsuffix=${S}/UnitTestFrameworkPkg/Library/GoogleTestLib/googletest \
    git://github.com/Zeex/subhook.git;branch=${XEN_BRANCH};protocol=https;name=repo11;destsuffix=${S}/UnitTestFrameworkPkg/Library/SubhookLib/subhook \
    git://github.com/google/brotli.git;branch=${XEN_BRANCH};protocol=https;name=repo12;destsuffix=${S}/MdeModulePkg/Library/BrotliCustomDecompressLib/brotli \
    "
SRCREV_FORMAT = "repo1_repo2_repo3_repo15_repo16_repo17_repo18_repo4_repo5_repo6_repo7_repo13_repo14_repo8_repo9_repo10_repo11_repo12"

SRC_URI[sha256sum] = "8a0456dd2765f413beecccd9cac7dce74141f87d1ee855808bd95098abafe2b4"
LIC_FILES_CHKSUM = "file://License.txt;md5=2b415520383f7964e96700ae12b4570a"

PV = "${XEN_REL}+master"

S = "${WORKDIR}/git"

FILES:${PN} = "/usr/share/firmware"

DEPENDS = "nasm-native acpica-native ovmf-native util-linux-native"

EDK_TOOLS_DIR="edk2_basetools"

# OVMF has trouble building with the default optimization of -O2.
BUILD_OPTIMIZATION="-pipe"
INSANE_SKIP:${PN} += "arch"
# OVMF supports IA only, although it could conceivably support ARM someday.
COMPATIBLE_HOST:class-target='(i.86|x86_64).*'

# Additional build flags for OVMF with Secure Boot.
# Fedora also uses "-D SMM_REQUIRE -D EXCLUDE_SHELL_FROM_FD".
OVMF_SECURE_BOOT_EXTRA_FLAGS ??= ""
OVMF_SECURE_BOOT_FLAGS = "-DSECURE_BOOT_ENABLE=TRUE ${OVMF_SECURE_BOOT_EXTRA_FLAGS}"

export PYTHON_COMMAND = "${HOSTTOOLS_DIR}/python3"

do_patch[postfuncs] += "fix_basetools_location"
fix_basetools_location () {
}
fix_basetools_location:class-target() {
    # Replaces the fake path inserted by 0002-ovmf-update-path-to-native-BaseTools.patch.
    # Necessary for finding the actual BaseTools from ovmf-native.
    sed -i -e 's#BBAKE_EDK_TOOLS_PATH#${STAGING_BINDIR_NATIVE}/${EDK_TOOLS_DIR}#' ${S}/OvmfPkg/build.sh
}

do_patch[postfuncs] += "fix_iasl"
fix_iasl() {
}

fix_iasl:class-native() {
    # iasl is not installed under /usr/bin when building with OE.
    sed -i -e 's#/usr/bin/iasl#${STAGING_BINDIR_NATIVE}/iasl#' ${S}/BaseTools/Conf/tools_def.template
}

# Inject CC and friends into the build. LINKER already is in GNUmakefile.
# Must be idempotent and thus remove old assignments that were inserted
# earlier.
do_patch[postfuncs] += "fix_toolchain"
fix_toolchain() {
    sed -i \
        -e '/^\(CC\|CXX\|AS\|AR\|LD\|LINKER\) =/d' \
        -e '/^APPLICATION/a CC = ${CC}\nCXX = ${CXX}\nAS = ${AS}\nAR = ${AR}\nLD = ${LD}\nLINKER = $(CC)' \
        ${S}/BaseTools/Source/C/Makefiles/app.makefile
    sed -i \
        -e '/^\(CC\|CXX\|AS\|AR\|LD\)/d' \
        -e '/^VFR_CPPFLAGS/a CC = ${CC}\nCXX = ${CXX}\nAS = ${AS}\nAR = ${AR}\nLD = ${LD}' \
        ${S}/BaseTools/Source/C/VfrCompile/GNUmakefile
}
fix_toolchain:append:class-native() {
    # This tools_def.template is going to be used by the target ovmf and
    # defines which compilers to use. For the GCC toolchain definitions,
    # that will be ${HOST_PREFIX}gcc. However, "make" doesn't need that
    # prefix.
    #
    # Injecting ENV(HOST_PREFIX) matches exporting that value as env
    # variable in do_compile:class-target.
    sed -i \
        -e 's#\(ENV\|DEF\)(GCC.*_PREFIX)#ENV(HOST_PREFIX)#' \
        -e 's#ENV(HOST_PREFIX)make#make#' \
        ${S}/BaseTools/Conf/tools_def.template
    sed -i \
        -e '/^\(LFLAGS\|CFLAGS\) +=/d' \
        -e '/^LINKER/a LFLAGS += ${BUILD_LDFLAGS}\nCFLAGS += ${BUILD_CFLAGS}' \
        ${S}/BaseTools/Source/C/Makefiles/app.makefile \
        ${S}/BaseTools/Source/C/VfrCompile/GNUmakefile
    # Linking with gold fails:
    # internal error in do_layout, at ../../gold/object.cc:1821
    # make: *** [.../OUTPUT/Facs.acpi] Error 1
    # We intentionally hard-code the use of ld.bfd regardless of DISTRO_FEATURES
    # to make ovmf-native reusable across distros.
    sed -i \
        -e 's#^\(DEFINE GCC.*DLINK.*FLAGS  *=\)#\1 -fuse-ld=bfd#' \
        -e 's#-flto#-fno-lto#g' \
        -e 's#-DUSING_LTO##g' \
        ${S}/BaseTools/Conf/tools_def.template
}

# We disable lto above since the results are not reproducible and make it hard to compare
# binary build aretfacts to debug reproducibility problems.
# Surprisingly, if you disable lto, you see compiler warnings which are fatal. We therefore
# have to hack warnings overrides into GCC_PREFIX_MAP to allow it to build.

# We want to pass ${DEBUG_PREFIX_MAP} to gcc commands and also pass in
# --debug-prefix-map to nasm (we carry a patch to nasm for this). The
# tools definitions are built by ovmf-native so we need to pass this in
# at target build time when we know the right values.
export NASM_PREFIX_MAP = "--debug-prefix-map=${WORKDIR}=/usr/src/debug/ovmf/${EXTENDPE}${PV}-${PR}"
export GCC_PREFIX_MAP = "${DEBUG_PREFIX_MAP} -Wno-stringop-overflow -Wno-maybe-uninitialized"

GCC_VER="$(${CC} -v 2>&1 | tail -n1 | awk '{print $3}')"

fixup_target_tools() {
    case ${1} in
      4.4.*)
        FIXED_GCCVER=GCC44
        ;;
      4.5.*)
        FIXED_GCCVER=GCC45
        ;;
      4.6.*)
        FIXED_GCCVER=GCC46
        ;;
      4.7.*)
        FIXED_GCCVER=GCC47
        ;;
      4.8.*)
        FIXED_GCCVER=GCC48
        ;;
      4.9.*)
        FIXED_GCCVER=GCC49
        ;;
      *)
        FIXED_GCCVER=GCC5
        ;;
    esac
    echo ${FIXED_GCCVER}
}

do_compile:class-native() {
    oe_runmake -C ${S}/BaseTools
}

do_compile:class-target() {
    export LFLAGS="${LDFLAGS}"
    PARALLEL_JOBS="${@oe.utils.parallel_make_argument(d, '-n %d')}"
    OVMF_ARCH="X64"
    if [ "${TARGET_ARCH}" != "x86_64" ] ; then
        OVMF_ARCH="IA32"
    fi

    # The build for the target uses BaseTools/Conf/tools_def.template
    # from ovmf-native to find the compiler, which depends on
    # exporting HOST_PREFIX.
    export HOST_PREFIX="${HOST_PREFIX}"

    # BaseTools/Conf gets copied to Conf, but only if that does not
    # exist yet. To ensure that an updated template gets used during
    # incremental builds, we need to remove the copy before we start.
    rm -f `ls ${S}/Conf/*.txt | grep -v ReadMe.txt`

    # ${WORKDIR}/ovmf is a well-known location where do_install and
    # do_deploy will be able to find the files.
    rm -rf ${WORKDIR}/ovmf
    mkdir ${WORKDIR}/ovmf
    OVMF_DIR_SUFFIX="Xen"
    if [ "${TARGET_ARCH}" != "x86_64" ] ; then
        OVMF_DIR_SUFFIX="Ia32" # Note the different capitalization
    fi
    FIXED_GCCVER=$(fixup_target_tools ${GCC_VER})
    bbnote FIXED_GCCVER is ${FIXED_GCCVER}
    build_dir="${S}/Build/Ovmf$OVMF_DIR_SUFFIX/RELEASE_${FIXED_GCCVER}"

    bbnote "Building without Secure Boot."
    rm -rf ${S}/Build/Ovmf$OVMF_DIR_SUFFIX
    ${S}/OvmfPkg/build.sh $PARALLEL_JOBS -a $OVMF_ARCH -b RELEASE -t ${FIXED_GCCVER} -p ${S}/OvmfPkg/OvmfXen.dsc
    ln ${build_dir}/FV/OVMF.fd ${WORKDIR}/ovmf/ovmf.fd
    ln ${build_dir}/FV/OVMF_CODE.fd ${WORKDIR}/ovmf/ovmf.code.fd
    ln ${build_dir}/FV/OVMF_VARS.fd ${WORKDIR}/ovmf/ovmf.vars.fd
    ln ${build_dir}/${OVMF_ARCH}/Shell.efi ${WORKDIR}/ovmf/

    if ${@bb.utils.contains('PACKAGECONFIG', 'secureboot', 'true', 'false', d)}; then
        # Repeat build with the Secure Boot flags.
        bbnote "Building with Secure Boot."
        rm -rf ${S}/Build/Ovmf$OVMF_DIR_SUFFIX
        ${S}/OvmfPkg/build.sh $PARALLEL_JOBS -a $OVMF_ARCH -b RELEASE -t ${FIXED_GCCVER} ${PACKAGECONFIG_CONFARGS} ${OVMF_SECURE_BOOT_FLAGS}
        ln ${build_dir}/FV/OVMF.fd ${WORKDIR}/ovmf/ovmf.fd
        ln ${build_dir}/FV/OVMF_CODE.fd ${WORKDIR}/ovmf/ovmf.code.fd
        ln ${build_dir}/${OVMF_ARCH}/EnrollDefaultKeys.efi ${WORKDIR}/ovmf/
    fi
}

do_install:class-native() {
    install -d ${D}/${bindir}/edk2_basetools
    cp -r ${S}/BaseTools ${D}/${bindir}/${EDK_TOOLS_DIR}
}

do_install:class-target() {
    # Content for UEFI shell iso. We install the EFI shell as
    # bootx64/ia32.efi because then it can be started even when the
    # firmware itself does not contain it.
    install -d ${D}/efi/boot
    install ${WORKDIR}/ovmf/Shell.efi ${D}/efi/boot/boot${@ "ia32" if "${TARGET_ARCH}" != "x86_64" else "x64"}.efi
    if ${@bb.utils.contains('PACKAGECONFIG', 'secureboot', 'true', 'false', d)}; then
        install ${WORKDIR}/ovmf/EnrollDefaultKeys.efi ${D}
    fi
    install -d ${D}${datadir}/firmware
    install -m 0600 ${WORKDIR}/ovmf/ovmf.fd ${D}${datadir}/firmware/ovmf.bin
}
# This always gets packaged because ovmf-shell-image depends on it.
# This allows testing that recipe in all configurations because it
# can always be part of a world build.
#
# However, EnrollDefaultKeys.efi is only included when Secure Boot is enabled.
PACKAGES =+ "ovmf-shell-efi"
FILES:ovmf-shell-efi = " \
    EnrollDefaultKeys.efi \
    efi/ \
"

DEPLOYDEP = ""
DEPLOYDEP:class-target = "qemu-system-native:do_populate_sysroot"
DEPLOYDEP:class-target += " ${@bb.utils.contains('PACKAGECONFIG', 'secureboot', 'openssl-native:do_populate_sysroot', '', d)}"
do_deploy[depends] += "${DEPLOYDEP}"

do_deploy() {
}
do_deploy:class-target() {
    if ${@bb.utils.contains('PACKAGECONFIG', 'secureboot', 'true', 'false', d)}; then
        # Create a test Platform Key and first Key Exchange Key to use with EnrollDefaultKeys
        openssl req -new -x509 -newkey rsa:2048 -keyout ${DEPLOYDIR}/OvmfPkKek1.key \
                -out ${DEPLOYDIR}/OvmfPkKek1.crt -nodes -days 20 -subj "/CN=OVMFSecBootTest"
        openssl x509 -in ${DEPLOYDIR}/OvmfPkKek1.crt -out ${DEPLOYDIR}/OvmfPkKek1.pem -outform PEM
    fi
}

addtask do_deploy after do_compile before do_build

BBCLASSEXTEND = "native"
TOOLCHAIN = "gcc"

