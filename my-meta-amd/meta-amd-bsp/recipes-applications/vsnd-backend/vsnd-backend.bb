# xen 4.16.1 release sha
LICENSE = "GPL-2.0-only"

SRCREV_repo1 ?= "21f1fdc090ee5342df7608358c5130a7675dad68"
SRCREV_repo2 ?= "b2764c2849f02c051f1d16dc6b592da59d1675c1"

VSND_BRANCH ?= "master"

PROVIDES = "vsnd_backend"

DEPENDS = "pkgconfig xen xen-tools"

inherit cmake

SRC_URI = " \
           git://github.com/xen-troops/libxenbe.git;branch=master;protocol=https;name=repo1;destsuffix=${S}/libxenbe \
           git://github.com/xen-troops/snd_be.git;branch=master;protocol=https;name=repo2;destsuffix=${S}/snd_be \
           file://catch-hpp-fixes.patch;patchdir=${S}/libxenbe \
           file://0001-Modified-handling-of-underrun-condition.patch;patchdir=${S} \
           file://0002-Added-query-device-close-in-hw-param-query-call.patch;patchdir=${S} \
           file://0003-Rounding-to-get-no-of-bytes-sent-by-I2S.patch;patchdir=${S} \
           file://0004-Fix-compilation-error.patch;patchdir=${S} \
          "

SRC_URI[sha256sum] = "8a0456dd2765f413beecccd9cac7dce74141f87d1ee855808bd95098abafe2b4"
LIC_FILES_CHKSUM = "file://libxenbe/LICENSE;md5=a23a74b3f4caf9616230789d94217acb \
                    file://snd_be/LICENSE;md5=a23a74b3f4caf9616230789d94217acb"

SRCREV_FORMAT = "repo1_repo2"

S = "${WORKDIR}/git"

EXTRA_OECONF = "-DWITH_DOC=OFF -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=${D}/usr"
EXTRA_CONF = "-DWITH_PULSE=OFF -DWITH_ALSA=ON -DCMAKE_BUILD_TYPE=Release -DXENBE_INCLUDE_PATH=${WORKDIR}/git/libxenbe/include -DXENBE_LIB_PATH=${WORKDIR}/git/libxenbe/build/src -DCMAKE_INSTALL_PREFIX=${D}/usr"

FILES:${PN} += "/usr/lib \
                /usr/include \
                "
FILES_SOLIBSDEV = ""

do_configure() {
        cd ${WORKDIR}/git/libxenbe
        mkdir -p build
        cd build
        cmake ${EXTRA_OECONF} ..

        cd ${WORKDIR}/git/snd_be
        mkdir -p build
        cd build
        cmake ${EXTRA_CONF} ..
}

do_compile() {
        cd ${WORKDIR}/git/libxenbe/build
        oe_runmake

        cd ${WORKDIR}/git/snd_be/build
        oe_runmake
}

do_install() {
        cd ${WORKDIR}/git/libxenbe/build
        oe_runmake install

        cd ${WORKDIR}/git/snd_be/build
        oe_runmake install
}
