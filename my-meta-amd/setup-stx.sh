#!/bin/bash

YOCTO_BRANCH="scarthgap"
REPO_DIR="poky-amd-${YOCTO_BRANCH}"

# Clone poky repository
git clone --single-branch --branch "${YOCTO_BRANCH}" "git://git.yoctoproject.org/poky" "${REPO_DIR}"

# Change to the poky directory
cd "${REPO_DIR}"

# Clone meta-openembedded and meta-dpdk repositories
git clone --single-branch --branch "${YOCTO_BRANCH}" "git://git.openembedded.org/meta-openembedded"
git clone --single-branch --branch "${YOCTO_BRANCH}" "git://git.yoctoproject.org/meta-dpdk"

# Clone meta-virtualization for enable virtualization or libvirt
git clone https://git.yoctoproject.org/meta-virtualization -b scarthgap

# Clone meta-secure-core for tpm tools
#git clone -b master https://github.com/Wind-River/meta-secure-core.git

# Clone meta-amd repository using SSH
git clone "ssh://gerritgit/eesc/ec/ese/meta-amd" -b kirkstone-auto

# Clone for tpm-tools
#git clone -b scarthgap https://github.com/Wind-River/meta-secure-core.git
git clone -b scarthgap https://git.yoctoproject.org/git/meta-security

git clone https://github.com/jwinarske/meta-vulkan -b kirkstone

cd poky-amd-${YOCTO_BRANCH}

cd meta
git checkout --quiet   f7def85be9f99dcb4ba488bead201f670304379b
cd ../meta-openembedded
git checkout --quiet   4a7bb77f7ebe0ac8be5bab5103d8bd993e17e18d

cd ../meta-poky
git checkout --quiet  f7def85be9f99dcb4ba488bead201f670304379b

cd ../meta-security
git checkout --quiet   11ea91192d43d7c2b0b95a93aa63ca7e73e38034

cd ../meta-selftest
git checkout --quiet   f7def85be9f99dcb4ba488bead201f670304379b

cd ../meta-skeleton
git checkout --quiet   f7def85be9f99dcb4ba488bead201f670304379b

cd ../meta-virtualization
git checkout --quiet  37c06acf58f9020bccfc61954eeefe160642d5f3

cd ../meta-vulkan
git checkout --quiet   3d722da4e5fb2b0f82b537c21e41022c3772c2dc

cd ../meta-yocto-bsp
git checkout --quiet  f7def85be9f99dcb4ba488bead201f670304379b
