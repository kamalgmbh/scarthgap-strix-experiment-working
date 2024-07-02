SRCREV ?= "dfd7b3fe0380a0313d1b573afb918ffcd92c4be5"

XEN_REL ?= "4.18"
#XEN_BRANCH ?= "4.18.0-rc5"
XEN_BRANCH ?= "stable-${XEN_REL}"

SRC_URI = " \
    git://xenbits.xen.org/xen.git;branch=${XEN_BRANCH} \
    file://0001-x86-time-Fix-UBSAN-failure-in-__update_vcpu_system_t.patch  	\
file://0002-golang-Fixup-binding-for-Arm-FF-A.patch                     	\
file://0003-golang-Fix-bindings-after-XSA-443.patch                     	\
file://0004-docs-misra-Add-missing-SPDX-tag.patch                       	\
file://0005-docs-Delete-kconfig-docs-to-fix-licensing-violation.patch   	\
file://0006-docs-sphinx-Fix-syntax-issues-in-hyperlaunch.rst.patch      	\
file://0007-docs-sphinx-Fix-syntax-issues-in-exclude-list.rst.patch     	\
file://0008-docs-sphinx-Fix-indexing.patch                              	\
file://0009-iommu-amd-vi-use-correct-level-for-quarantine-domain.patch  	\
file://0010-x86-spec-ctrl-Remove-conditional-IRQs-on-ness-for-IN.patch  	\
file://0011-Config.mk-Bump-tags-to-4.18.0-final.patch                   	\
file://0012-README-make-heading-say-4.18.patch                          	\
file://0013-Set-4.18-version.patch                                      	\
file://0014-Config.mk-Fix-tag-for-mini-os.patch                         	\
file://0015-SUPPORT.md-Define-support-lifetime.patch                    	\
file://0016-SUPPORT.md-Update-release-notes-URL.patch                   	\
file://0017-update-Xen-version-to-4.18.1-pre.patch                      	\
file://0018-x86-mem_sharing-add-missing-m2p-entry-when-mapping-s.patch  	\
file://0019-x86-pv-shim-fix-grant-table-operations-for-32-bit-gu.patch  	\
file://0020-x86-x2apic-remove-usage-of-ACPI_FADT_APIC_CLUSTER.patch     	\
file://0021-x86-i8259-do-not-assume-interrupts-always-target-CPU.patch  	\
file://0022-x86-spec-ctrl-Add-SRSO-whitepaper-URL.patch                 	\
file://0023-xen-sched-fix-sched_move_domain.patch                       	\
file://0024-x86-mem_sharing-Release-domain-if-we-are-not-able-to.patch  	\
file://0025-livepatch-do-not-use-.livepatch.funcs-section-to-sto.patch  	\
file://0026-xen-x86-In-x2APIC-mode-derive-LDR-from-APIC-ID.patch        	\
file://0027-tools-xg-Fix-potential-memory-leak-in-cpu-policy-get.patch  	\
file://0028-x86emul-avoid-triggering-event-related-assertions.patch     	\
file://0029-xen-sched-fix-adding-offline-cpu-to-cpupool.patch           	\
file://0030-xen-domain-fix-error-path-in-domain_create.patch            	\
file://0031-Only-compile-the-hypervisor-with-Wdeclaration-after-.patch  	\
file://0032-xen-sched-fix-sched_move_domain.patch                       	\
file://0033-xen-arm-page-Avoid-pointer-overflow-on-cache-clean-i.patch  	\
file://0034-x86-x2apic-introduce-a-mixed-physical-cluster-mode.patch    	\
file://0035-pci-fail-device-assignment-if-phantom-functions-cann.patch  	\
file://0036-VT-d-Fix-else-vs-endif-misplacement.patch                   	\
file://0037-x86-amd-Extend-CPU-erratum-1474-fix-to-more-affected.patch  	\
file://0038-CirrusCI-drop-FreeBSD-12.patch                              	\
file://0039-x86-intel-ensure-Global-Performance-Counter-Control-.patch  	\
file://0040-x86-vmx-Fix-IRQ-handling-for-EXIT_REASON_INIT.patch         	\
file://0041-x86-vmx-Disallow-the-use-of-inactivity-states.patch         	\
file://0042-lib-fdt-elf-move-lib-fdt-elf-temp.o-and-their-deps-t.patch  	\
file://0043-x86-p2m-pt-fix-off-by-one-in-entry-check-assert.patch       	\
file://0044-x86-ucode-Fix-stability-of-the-raw-CPU-Policy-rescan.patch  	\
file://0045-tools-xentop-fix-sorting-bug-for-some-columns.patch         	\
file://0046-amd-vi-fix-IVMD-memory-type-checks.patch                    	\
file://0047-block-common-Fix-same_vm-for-no-targets.patch               	\
file://0048-x86-hvm-Fix-fast-singlestep-state-persistence.patch         	\
file://0049-x86-HVM-tidy-state-on-hvmemul_map_linear_addr-s-erro.patch  	\
file://0050-build-Replace-which-with-command-v.patch                    	\
file://0051-libxl-Disable-relocating-memory-for-qemu-xen-in-stub.patch  	\
file://0052-build-make-sure-build-fails-when-running-kconfig-fai.patch  	\
file://0053-x86emul-add-missing-EVEX.R-checks.patch                     	\
file://0054-xen-livepatch-fix-norevert-test-hook-setup-typo.patch       	\
file://0055-xen-cmdline-fix-printf-format-specifier-in-no_config.patch  	\
file://0056-x86-altcall-use-a-union-as-register-type-for-functio.patch  	\
file://0057-x86-spec-fix-BRANCH_HARDEN-option-to-only-be-set-whe.patch  	\
file://0058-x86-account-for-shadow-stack-in-exception-from-stub-.patch  	\
file://0059-xen-arm-Fix-UBSAN-failure-in-start_xen.patch                	\
file://0060-x86-HVM-hide-SVM-VMX-when-their-enabling-is-prohibit.patch  	\
file://0061-xen-sched-Fix-UB-shift-in-compat_set_timer_op.patch         	\
file://0062-x86-spec-print-the-built-in-SPECULATIVE_HARDEN_-opti.patch  	\
file://0063-x86-spec-fix-INDIRECT_THUNK-option-to-only-be-set-wh.patch  	\
file://0064-x86-spec-do-not-print-thunk-option-selection-if-not-.patch  	\
file://0065-xen-livepatch-register-livepatch-regions-when-loaded.patch  	\
file://0066-xen-livepatch-search-for-symbols-in-all-loaded-paylo.patch  	\
file://0067-xen-livepatch-fix-norevert-test-attempt-to-open-code.patch  	\
file://0068-xen-livepatch-properly-build-the-noapply-and-norever.patch  	\
file://0069-libxl-Fix-segfault-in-device_model_spawn_outcome.patch      	\
file://0070-x86-altcall-always-use-a-temporary-parameter-stashin.patch  	\
file://0071-x86-cpu-policy-Allow-for-levelling-of-VERW-side-effe.patch  	\
file://0072-hvmloader-PCI-skip-huge-BARs-in-certain-calculations.patch  	\
file://0073-x86-mm-fix-detection-of-last-L1-entry-in-modify_xen_.patch  	\
file://0074-x86-hvm-make-X86_EMU_USE_PIRQ-optional.patch                	\
file://0075-tools-don-t-expose-XENFEAT_hvm_pirqs-by-default.patch       	\
file://0076-vpci-accept-BAR-writes-if-dom0-is-PVH.patch                 	\
file://0077-hvm-shouldn-t-check-pirq-flag-when-map-pirq-in-PVH.patch    	\
file://0078-hvm-PVH-dom0-also-need-PHYSDEVOP_setup_gsi-call.patch       	\
file://0079-libs-add-linux-os-call-to-get-gsi-from-irq.patch            	\
file://0080-light-pci-translate-irq-to-gsi.patch                        	\
file://0081-paging_mode_translate-memory-exchange-hack.patch            	\
file://0082-xen-add-privcmd-ioctl-to-map-host-virtual-address-sp.patch  	\
file://0083-vpci-reinitialize-msi-interrupts-upon-resume-from-D3.patch  	\
file://0084-tools-libs-store-disable-fortification-when-USE_PTHR.patch  	\
file://0085-libxl-use-maxmem-as-max-memory-upper-allocation-limi.patch  	\
file://0086-x86-hvm-report-s3-resume-correctly-in-acpi-pm1-statu.patch  	\
file://0087-x86-irq-do-not-insert-emulated-MSIs-in-emuirq-mappin.patch  	\
file://0088-pvh-dom0-fix-MSI-x-initialization-for-passed-through.patch  	\
file://0089-x86-hvm-do-not-invalidate-mapcache-when-removing-non.patch  	\
file://0090-x86-hvm-test-and-clear-mapcache_invalidate-atomicall.patch  	\
file://0091-pvh-dom0-do-not-set-mapcache_invalidate-for-dom0-vcp.patch  	\
file://0092-pvh-dom0-skip-any-ioreq-handling.patch                      	\
file://0093-x86-p2m-release-the-foreign-mappings-of-a-dying-doma.patch  	\
file://0094-x86-p2m-extend-XENMAPSPACE_gmfn_foreign-to-mmio-and-.patch  	\
file://0095-x86-ioreq-cancel-in-flight-emulation-if-GPFN-became-.patch  	\
file://0096-privcmd-add-interface-to-init-and-update-hva-mapping.patch  	\
file://0097-WA-ignore-error-in-parse_ivmd_device_range.patch            	\
file://0098-WA-for-SWDEV-452110.patch                                   	\
file://0099-x86-svm-do-not-crash-domain-if-pte-was-mapped-while-.patch  	\
file://0100-xen-public-fix-flexible-array-definitions.patch             	\
file://0101-ioreq-debug-add-more-debug-around-ioreq-failure-emul.patch  	\
file://0102-libelf-Expand-ELF-note-printing.patch                       	\
file://0103-tools-init-xenstore-domain-Replace-variable-MB-usage.patch  	\
file://0104-tools-Move-MB-GB-to-common-macros.h.patch                   	\
file://0105-libelf-Store-maximum-PHDR-p_align.patch                     	\
file://0106-x86-PVH-Support-relocatable-dom0-kernels.patch              	\
"
LIC_FILES_CHKSUM ?= "file://COPYING;md5=d1a1e216f80b6d8da95fec897d0dbec9"

PV = "${XEN_REL}+stable${SRCPV}"

S = "${WORKDIR}/git"

require xen.inc
require xen-hypervisor.inc
