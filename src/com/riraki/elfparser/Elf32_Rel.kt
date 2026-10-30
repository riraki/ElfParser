package com.riraki.elfparser

/**
 * @param r_offset 需要进行重定位的位置
 * @param r_info ELF32 重定位信息：高 24 位是符号索引，低 8 位是重定位类型
 * @param machine 处理器架构
 * @param dynsymSections 用于获取符号名称；不需要名称时可传入 null
 */
data class Elf32_Rel(
    val r_offset: Elf32_Addr,
    val r_info: Elf32_Word,
    private val machine: ElfMachine,
    private val dynsymSections: List<Elf32_Sym>?
) {
    /**
     * typedef struct {
     *   Elf32_Addr r_offset;
     *   Elf32_Word r_info;
     * } Elf32_Rel;
     */

    private fun relocationType(): UInt = r_info and 0xFFu

    private fun symbolIndex(): UInt = r_info shr 8

    private fun symbolString(): String {
        return if (dynsymSections != null) {
            val index = symbolIndex().toInt()
            if (index !in dynsymSections.indices) {
                throw IndexOutOfBoundsException("Relocation symbol index $index is outside .dynsym")
            }
            dynsymSections[index].getName()
        } else {
            FormatHelpers.toHexString(symbolIndex())
        }
    }

    private fun relocationTypeString(): String {
        return when (machine) {
            ElfMachine.EM_ARM -> relocationTypeStringWithArm()
            ElfMachine.EM_386 -> relocationTypeStringWith386()
            ElfMachine.EM_AARCH64 -> "UNSUPPORTED_ELF64_MACHINE(EM_AARCH64)"
            ElfMachine.EM_X86_64 -> "UNSUPPORTED_ELF64_MACHINE(EM_X86_64)"
        }
    }

    private fun relocationTypeStringWithArm(): String {
        return when (relocationType()) {
            R_ARM_NONE -> "R_ARM_NONE"
            R_ARM_PC24 -> "R_ARM_PC24"
            R_ARM_ABS32 -> "R_ARM_ABS32"
            R_ARM_REL32 -> "R_ARM_REL32"
            R_ARM_PC13 -> "R_ARM_PC13"
            R_ARM_ABS16 -> "R_ARM_ABS16"
            R_ARM_ABS12 -> "R_ARM_ABS12"
            R_ARM_THM_ABS5 -> "R_ARM_THM_ABS5"
            R_ARM_ABS8 -> "R_ARM_ABS8"
            R_ARM_SBREL32 -> "R_ARM_SBREL32"
            R_ARM_THM_PC22 -> "R_ARM_THM_PC22"
            R_ARM_THM_PC8 -> "R_ARM_THM_PC8"
            R_ARM_AMP_VCALL9 -> "R_ARM_AMP_VCALL9"
            R_ARM_TLS_DESC -> "R_ARM_TLS_DESC"
            R_ARM_THM_SWI8 -> "R_ARM_THM_SWI8"
            R_ARM_XPC25 -> "R_ARM_XPC25"
            R_ARM_THM_XPC22 -> "R_ARM_THM_XPC22"
            R_ARM_TLS_DTPMOD32 -> "R_ARM_TLS_DTPMOD32"
            R_ARM_TLS_DTPOFF32 -> "R_ARM_TLS_DTPOFF32"
            R_ARM_TLS_TPOFF32 -> "R_ARM_TLS_TPOFF32"
            R_ARM_COPY -> "R_ARM_COPY"
            R_ARM_GLOB_DAT -> "R_ARM_GLOB_DAT"
            R_ARM_JUMP_SLOT -> "R_ARM_JUMP_SLOT"
            R_ARM_RELATIVE -> "R_ARM_RELATIVE"
            R_ARM_GOTOFF -> "R_ARM_GOTOFF"
            R_ARM_GOTPC -> "R_ARM_GOTPC"
            R_ARM_GOT32 -> "R_ARM_GOT32"
            R_ARM_PLT32 -> "R_ARM_PLT32"
            R_ARM_CALL -> "R_ARM_CALL"
            R_ARM_JUMP24 -> "R_ARM_JUMP24"
            R_ARM_THM_JUMP24 -> "R_ARM_THM_JUMP24"
            R_ARM_BASE_ABS -> "R_ARM_BASE_ABS"
            R_ARM_ALU_PCREL_7_0 -> "R_ARM_ALU_PCREL_7_0"
            R_ARM_ALU_PCREL_15_8 -> "R_ARM_ALU_PCREL_15_8"
            R_ARM_ALU_PCREL_23_15 -> "R_ARM_ALU_PCREL_23_15"
            R_ARM_LDR_SBREL_11_0 -> "R_ARM_LDR_SBREL_11_0"
            R_ARM_ALU_SBREL_19_12 -> "R_ARM_ALU_SBREL_19_12"
            R_ARM_ALU_SBREL_27_20 -> "R_ARM_ALU_SBREL_27_20"
            R_ARM_TARGET1 -> "R_ARM_TARGET1"
            R_ARM_SBREL31 -> "R_ARM_SBREL31"
            R_ARM_V4BX -> "R_ARM_V4BX"
            R_ARM_TARGET2 -> "R_ARM_TARGET2"
            R_ARM_PREL31 -> "R_ARM_PREL31"
            R_ARM_MOVW_ABS_NC -> "R_ARM_MOVW_ABS_NC"
            R_ARM_MOVT_ABS -> "R_ARM_MOVT_ABS"
            R_ARM_MOVW_PREL_NC -> "R_ARM_MOVW_PREL_NC"
            R_ARM_MOVT_PREL -> "R_ARM_MOVT_PREL"
            R_ARM_THM_MOVW_ABS_NC -> "R_ARM_THM_MOVW_ABS_NC"
            R_ARM_THM_MOVT_ABS -> "R_ARM_THM_MOVT_ABS"
            R_ARM_THM_MOVW_PREL_NC -> "R_ARM_THM_MOVW_PREL_NC"
            R_ARM_THM_MOVT_PREL -> "R_ARM_THM_MOVT_PREL"
            R_ARM_THM_JUMP19 -> "R_ARM_THM_JUMP19"
            R_ARM_THM_JUMP6 -> "R_ARM_THM_JUMP6"
            R_ARM_THM_ALU_PREL_11_0 -> "R_ARM_THM_ALU_PREL_11_0"
            R_ARM_THM_PC12 -> "R_ARM_THM_PC12"
            R_ARM_ABS32_NOI -> "R_ARM_ABS32_NOI"
            R_ARM_REL32_NOI -> "R_ARM_REL32_NOI"
            R_ARM_ALU_PC_G0_NC -> "R_ARM_ALU_PC_G0_NC"
            R_ARM_ALU_PC_G0 -> "R_ARM_ALU_PC_G0"
            R_ARM_ALU_PC_G1_NC -> "R_ARM_ALU_PC_G1_NC"
            R_ARM_ALU_PC_G1 -> "R_ARM_ALU_PC_G1"
            R_ARM_ALU_PC_G2 -> "R_ARM_ALU_PC_G2"
            R_ARM_LDR_PC_G1 -> "R_ARM_LDR_PC_G1"
            R_ARM_LDR_PC_G2 -> "R_ARM_LDR_PC_G2"
            R_ARM_LDRS_PC_G0 -> "R_ARM_LDRS_PC_G0"
            R_ARM_LDRS_PC_G1 -> "R_ARM_LDRS_PC_G1"
            R_ARM_LDRS_PC_G2 -> "R_ARM_LDRS_PC_G2"
            R_ARM_LDC_PC_G0 -> "R_ARM_LDC_PC_G0"
            R_ARM_LDC_PC_G1 -> "R_ARM_LDC_PC_G1"
            R_ARM_LDC_PC_G2 -> "R_ARM_LDC_PC_G2"
            R_ARM_ALU_SB_G0_NC -> "R_ARM_ALU_SB_G0_NC"
            R_ARM_ALU_SB_G0 -> "R_ARM_ALU_SB_G0"
            R_ARM_ALU_SB_G1_NC -> "R_ARM_ALU_SB_G1_NC"
            R_ARM_ALU_SB_G1 -> "R_ARM_ALU_SB_G1"
            R_ARM_ALU_SB_G2 -> "R_ARM_ALU_SB_G2"
            R_ARM_LDR_SB_G0 -> "R_ARM_LDR_SB_G0"
            R_ARM_LDR_SB_G1 -> "R_ARM_LDR_SB_G1"
            R_ARM_LDR_SB_G2 -> "R_ARM_LDR_SB_G2"
            R_ARM_LDRS_SB_G0 -> "R_ARM_LDRS_SB_G0"
            R_ARM_LDRS_SB_G1 -> "R_ARM_LDRS_SB_G1"
            R_ARM_LDRS_SB_G2 -> "R_ARM_LDRS_SB_G2"
            R_ARM_LDC_SB_G0 -> "R_ARM_LDC_SB_G0"
            R_ARM_LDC_SB_G1 -> "R_ARM_LDC_SB_G1"
            R_ARM_LDC_SB_G2 -> "R_ARM_LDC_SB_G2"
            R_ARM_MOVW_BREL_NC -> "R_ARM_MOVW_BREL_NC"
            R_ARM_MOVT_BREL -> "R_ARM_MOVT_BREL"
            R_ARM_MOVW_BREL -> "R_ARM_MOVW_BREL"
            R_ARM_THM_MOVW_BREL_NC -> "R_ARM_THM_MOVW_BREL_NC"
            R_ARM_THM_MOVT_BREL -> "R_ARM_THM_MOVT_BREL"
            R_ARM_THM_MOVW_BREL -> "R_ARM_THM_MOVW_BREL"
            R_ARM_TLS_GOTDESC -> "R_ARM_TLS_GOTDESC"
            R_ARM_TLS_CALL -> "R_ARM_TLS_CALL"
            R_ARM_TLS_DESCSEQ -> "R_ARM_TLS_DESCSEQ"
            R_ARM_THM_TLS_CALL -> "R_ARM_THM_TLS_CALL"
            R_ARM_PLT32_ABS -> "R_ARM_PLT32_ABS"
            R_ARM_GOT_ABS -> "R_ARM_GOT_ABS"
            R_ARM_GOT_PREL -> "R_ARM_GOT_PREL"
            R_ARM_GOT_BREL12 -> "R_ARM_GOT_BREL12"
            R_ARM_GOTOFF12 -> "R_ARM_GOTOFF12"
            R_ARM_GOTRELAX -> "R_ARM_GOTRELAX"
            R_ARM_GNU_VTENTRY -> "R_ARM_GNU_VTENTRY"
            R_ARM_GNU_VTINHERIT -> "R_ARM_GNU_VTINHERIT"
            R_ARM_THM_PC11 -> "R_ARM_THM_PC11"
            R_ARM_THM_PC9 -> "R_ARM_THM_PC9"
            R_ARM_TLS_GD32 -> "R_ARM_TLS_GD32"
            R_ARM_TLS_LDM32 -> "R_ARM_TLS_LDM32"
            R_ARM_TLS_LDO32 -> "R_ARM_TLS_LDO32"
            R_ARM_TLS_IE32 -> "R_ARM_TLS_IE32"
            R_ARM_TLS_LE32 -> "R_ARM_TLS_LE32"
            R_ARM_TLS_LDO12 -> "R_ARM_TLS_LDO12"
            R_ARM_TLS_LE12 -> "R_ARM_TLS_LE12"
            R_ARM_TLS_IE12GP -> "R_ARM_TLS_IE12GP"
            R_ARM_ME_TOO -> "R_ARM_ME_TOO"
            R_ARM_THM_TLS_DESCSEQ -> "R_ARM_THM_TLS_DESCSEQ"
            R_ARM_THM_TLS_DESCSEQ32 -> "R_ARM_THM_TLS_DESCSEQ32"
            R_ARM_THM_GOT_BREL12 -> "R_ARM_THM_GOT_BREL12"
            R_ARM_IRELATIVE -> "R_ARM_IRELATIVE"
            R_ARM_RXPC25 -> "R_ARM_RXPC25"
            R_ARM_RSBREL32 -> "R_ARM_RSBREL32"
            R_ARM_THM_RPC22 -> "R_ARM_THM_RPC22"
            R_ARM_RREL32 -> "R_ARM_RREL32"
            R_ARM_RABS22 -> "R_ARM_RABS22"
            R_ARM_RPC24 -> "R_ARM_RPC24"
            R_ARM_RBASE -> "R_ARM_RBASE"
            else -> "R_ARM_UNKNOWN(${relocationType()})"
        }
    }

    private fun relocationTypeStringWith386(): String {
        return when (relocationType()) {
            R_386_NONE -> "R_386_NONE"
            R_386_32 -> "R_386_32"
            R_386_PC32 -> "R_386_PC32"
            R_386_GOT32 -> "R_386_GOT32"
            R_386_PLT32 -> "R_386_PLT32"
            R_386_COPY -> "R_386_COPY"
            R_386_GLOB_DAT -> "R_386_GLOB_DAT"
            R_386_JMP_SLOT -> "R_386_JMP_SLOT"
            R_386_RELATIVE -> "R_386_RELATIVE"
            R_386_GOTOFF -> "R_386_GOTOFF"
            R_386_GOTPC -> "R_386_GOTPC"
            R_386_32PLT -> "R_386_32PLT"
            R_386_TLS_TPOFF -> "R_386_TLS_TPOFF"
            R_386_TLS_IE -> "R_386_TLS_IE"
            R_386_TLS_GOTIE -> "R_386_TLS_GOTIE"
            R_386_TLS_LE -> "R_386_TLS_LE"
            R_386_TLS_GD -> "R_386_TLS_GD"
            R_386_TLS_LDM -> "R_386_TLS_LDM"
            R_386_16 -> "R_386_16"
            R_386_PC16 -> "R_386_PC16"
            R_386_8 -> "R_386_8"
            R_386_PC8 -> "R_386_PC8"
            R_386_TLS_GD_32 -> "R_386_TLS_GD_32"
            R_386_TLS_GD_PUSH -> "R_386_TLS_GD_PUSH"
            R_386_TLS_GD_CALL -> "R_386_TLS_GD_CALL"
            R_386_TLS_GD_POP -> "R_386_TLS_GD_POP"
            R_386_TLS_LDM_32 -> "R_386_TLS_LDM_32"
            R_386_TLS_LDM_PUSH -> "R_386_TLS_LDM_PUSH"
            R_386_TLS_LDM_CALL -> "R_386_TLS_LDM_CALL"
            R_386_TLS_LDM_POP -> "R_386_TLS_LDM_POP"
            R_386_TLS_LDO_32 -> "R_386_TLS_LDO_32"
            R_386_TLS_IE_32 -> "R_386_TLS_IE_32"
            R_386_TLS_LE_32 -> "R_386_TLS_LE_32"
            R_386_TLS_DTPMOD32 -> "R_386_TLS_DTPMOD32"
            R_386_TLS_DTPOFF32 -> "R_386_TLS_DTPOFF32"
            R_386_TLS_TPOFF32 -> "R_386_TLS_TPOFF32"
            R_386_SIZE32 -> "R_386_SIZE32"
            R_386_TLS_GOTDESC -> "R_386_TLS_GOTDESC"
            R_386_TLS_DESC_CALL -> "R_386_TLS_DESC_CALL"
            R_386_TLS_DESC -> "R_386_TLS_DESC"
            R_386_IRELATIVE -> "R_386_IRELATIVE"
            R_386_GOT32X -> "R_386_GOT32X"
            else -> "R_386_UNKNOWN(${relocationType()})"
        }
    }

    override fun toString(): String {
        return "Elf32_Rel(" +
                "r_offset=${FormatHelpers.toHexString(r_offset)}" +
                ", r_info=${FormatHelpers.toHexString(r_info)}" +
                ", symbolIndex=${FormatHelpers.toHexString(symbolIndex())}" +
                ", symbol=\"${symbolString()}\"" +
                ", relocationType=${relocationTypeString()}" + ")"
    }
}
