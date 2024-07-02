FILESEXTRAPATHS:prepend := "${THISDIR}:"
DESCRIPTION = "These binaries provide kernel support for newer AMD GPUs"
LICENSE = "CLOSED"
SRC_URI = "file://dcn_3_5_dmcub.bin \
        file://gc_11_5_0_imu.bin \
        file://gc_11_5_0_me.bin \
        file://gc_11_5_0_mec.bin \
        file://gc_11_5_0_mes1.bin \
        file://gc_11_5_0_mes_2.bin \
        file://gc_11_5_0_pfp.bin \
        file://gc_11_5_0_rlc.bin \
        file://psp_14_0_0_ta.bin \
        file://psp_14_0_0_toc.bin \
        file://sdma_6_1_0.bin \
        file://umsch_mm_4_0_0.bin \
        file://vcn_4_0_5.bin \
        file://vpe_6_1_0.bin \
        file://LICENSE \
"

LIC_FILES_CHKSUM = "file://LICENSE;md5=07b0c31777bd686d8e1609c6940b5e74"
S = "${WORKDIR}"

# Since no binaries are generated for a specific target,
# inherit allarch to simply populate prebuilt binaries
inherit allarch

do_compile() {
    :
}

do_install() {
    install -d ${D}/usr/lib/firmware/amdgpu
    install -v -m 444 -D ${S}/LICENSE ${D}/usr/lib/firmware/amdgpu/LICENSE
    install -v -m 0644 ${S}/*.bin ${D}/usr/lib/firmware/amdgpu
}

FILES:${PN} = "/usr/lib/firmware/*"
