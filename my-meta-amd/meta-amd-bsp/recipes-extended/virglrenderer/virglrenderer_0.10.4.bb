LICENSE = "GPL-2.0-only"
SRCREV ?= "690680e5f0f952e22424fca1538c1b24457a0868"

XEN_REL ?= "virglrender"
XEN_BRANCH ?= "main"

SRC_URI = " \
    git://gitlab.freedesktop.org/virgl/virglrenderer.git;branch=${XEN_BRANCH};protocol=https \
	file://0001-Uprev-Mesa-to-33b77ec774a10f052a2814d9ff3668cc0aa130.patch \
	file://0002-vkr-lock-device-tracked-object-list.patch \
	file://0003-vrend-Properly-seperate-the-mirror_clamp-feat-report.patch \
	file://0004-drm-msm-Remove-unused-msm_object-res.patch \
	file://0005-drm-Switch-to-refactored-proto-headers.patch \
	file://0006-virgl-create-transient-dmabuf-in-resource_map.patch \
	file://0007-vrend-Add-XRGB2101010-support.patch \
	file://0008-util-add-set_dmabuf_name-helper.patch \
	file://0009-drm-include-drm_util.h-for-drm_log.patch \
	file://0010-drm-pass-debugname-param-to-backend-create.patch \
	file://0011-tests-fuzzer-add-virgl_deps-to-dependencies.patch \
	file://0012-drm-add-amdgpu-native-context-implementation.patch \
	file://0013-drm-amdgpu-optimize-dmabuf-creation.patch \
	file://0014-drm-amdgpu-remove-duplicate-command-name-array.patch \
	file://0015-drm-add-wait_for_idle-cmd.patch \
	file://0016-virtio-gpu-support-blob-type-cursor.patch \
    "
SRC_URI[sha256sum] = "8a0456dd2765f413beecccd9cac7dce74141f87d1ee855808bd95098abafe2b4"
LIC_FILES_CHKSUM = "file://COPYING;md5=c81c08eeefd9418fca8f88309a76db10"


S = "${WORKDIR}/git"
DEPENDS = "libdrm virtual/egl virtual/libgbm libepoxy opencl-headers mesa"
inherit meson pkgconfig features_check

EXTRA_OECONF += " \
	-Dbuildtype=debug \
	-Dplatforms=egl \
	-Dvenus-experimental=true \
	-Dminigbm_allocation=false \
	"
PACKAGECONFIG ?= "venus render-server drm-amdgpu-experimental"
 
PACKAGECONFIG[venus] = "-Dvenus=true,-Dvenus=false,vulkan-loader vulkan-headers"
PACKAGECONFIG[va] = "-Dvideo=true,-Dvideo=false,libva"
PACKAGECONFIG[render-server] = "-Drender-server=true,-Drender-server=false"
PACKAGECONFIG[drm-msm-experimental] = "-Ddrm-msm-experimental=true,-Ddrm-msm-experimental=false"
PACKAGECONFIG[drm-amdgpu-experimental] = "-Ddrm-amdgpu-experimental=true,-Ddrm-amdgpu-experimental=false"
PACKAGECONFIG[minigbm_allocation] = "-Dminigbm_allocation=true,-Dminigbm_allocation=false"
PACKAGECONFIG[venus-validate] = "-Dvenus-validate=true,-Dvenus-validate=false"
PACKAGECONFIG[tests] = "-Dtests=true,-Dtests=false,libcheck"

REQUIRED_DISTRO_FEATURES = "opengl"

