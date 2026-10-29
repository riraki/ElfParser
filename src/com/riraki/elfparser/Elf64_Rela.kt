package com.riraki.elfparser

/**
 * @param r_offset 需要进行重定位的位置
 * @param r_info 重定位类型（低32位）与符号索引（高32位）
 * @param r_addend 参与计算的附加常量
 * @param machine 处理器架构
 * @param dynsymSections dynsym sections，用来获取符号的名称，如果不需要获取那么可以传入 null
 */
data class Elf64_Rela(
    val r_offset: Elf64_Addr,
    val r_info: Elf64_Xword,
    val r_addend: Elf64_Sxword,
    private val machine: ElfMachine,
    private val dynsymSections: List<Elf64_Sym>?
) {
    /**
     * typedef struct
     * {
     *   Elf64_Addr	r_offset;		/* Address */
     *   Elf64_Xword	r_info;			/* Relocation type and symbol index */
     *   Elf64_Sxword	r_addend;		/* Addend */
     * } Elf64_Rela;
     */

    /**
     * 获取重定位类型
     */
    private fun relocationType(): UInt = (r_info and 0xffffffffUL).toUInt()

    /**
     * 获取符号索引
     */
    private fun symbolIndex(): UInt = (r_info shr 32).toUInt()

    private fun relocationTypeString(): String {
        return when (machine) {
            ElfMachine.EM_AARCH64 -> relocationTypeStringWithAarch64()
            ElfMachine.EM_X86_64 -> relocationTypeStringWithX86_64()
        }
    }

    private fun relocationTypeStringWithAarch64(): String {
        return when (relocationType()) {
            R_AARCH64_NONE -> "R_AARCH64_NONE"
            R_AARCH64_ABS64 -> "R_AARCH64_ABS64"
            R_AARCH64_ABS32 -> "R_AARCH64_ABS32"
            R_AARCH64_ABS16 -> "R_AARCH64_ABS16"
            R_AARCH64_PREL64 -> "R_AARCH64_PREL64"
            R_AARCH64_PREL32 -> "R_AARCH64_PREL32"
            R_AARCH64_PREL16 -> "R_AARCH64_PREL16"
            R_AARCH64_MOVW_UABS_G0 -> "R_AARCH64_MOVW_UABS_G0"
            R_AARCH64_MOVW_UABS_G0_NC -> "R_AARCH64_MOVW_UABS_G0_NC"
            R_AARCH64_MOVW_UABS_G1 -> "R_AARCH64_MOVW_UABS_G1"
            R_AARCH64_MOVW_UABS_G1_NC -> "R_AARCH64_MOVW_UABS_G1_NC"
            R_AARCH64_MOVW_UABS_G2 -> "R_AARCH64_MOVW_UABS_G2"
            R_AARCH64_MOVW_UABS_G2_NC -> "R_AARCH64_MOVW_UABS_G2_NC"
            R_AARCH64_MOVW_UABS_G3 -> "R_AARCH64_MOVW_UABS_G3"
            R_AARCH64_MOVW_SABS_G0 -> "R_AARCH64_MOVW_SABS_G0"
            R_AARCH64_MOVW_SABS_G1 -> "R_AARCH64_MOVW_SABS_G1"
            R_AARCH64_MOVW_SABS_G2 -> "R_AARCH64_MOVW_SABS_G2"
            R_AARCH64_LD_PREL_LO19 -> "R_AARCH64_LD_PREL_LO19"
            R_AARCH64_ADR_PREL_LO21 -> "R_AARCH64_ADR_PREL_LO21"
            R_AARCH64_ADR_PREL_PG_HI21 -> "R_AARCH64_ADR_PREL_PG_HI21"
            R_AARCH64_ADR_PREL_PG_HI21_NC -> "R_AARCH64_ADR_PREL_PG_HI21_NC"
            R_AARCH64_ADD_ABS_LO12_NC -> "R_AARCH64_ADD_ABS_LO12_NC"
            R_AARCH64_LDST8_ABS_LO12_NC -> "R_AARCH64_LDST8_ABS_LO12_NC"
            R_AARCH64_TSTBR14 -> "R_AARCH64_TSTBR14"
            R_AARCH64_CONDBR19 -> "R_AARCH64_CONDBR19"
            R_AARCH64_JUMP26 -> "R_AARCH64_JUMP26"
            R_AARCH64_CALL26 -> "R_AARCH64_CALL26"
            R_AARCH64_LDST16_ABS_LO12_NC -> "R_AARCH64_LDST16_ABS_LO12_NC"
            R_AARCH64_LDST32_ABS_LO12_NC -> "R_AARCH64_LDST32_ABS_LO12_NC"
            R_AARCH64_LDST64_ABS_LO12_NC -> "R_AARCH64_LDST64_ABS_LO12_NC"
            R_AARCH64_MOVW_PREL_G0 -> "R_AARCH64_MOVW_PREL_G0"
            R_AARCH64_MOVW_PREL_G0_NC -> "R_AARCH64_MOVW_PREL_G0_NC"
            R_AARCH64_MOVW_PREL_G1 -> "R_AARCH64_MOVW_PREL_G1"
            R_AARCH64_MOVW_PREL_G1_NC -> "R_AARCH64_MOVW_PREL_G1_NC"
            R_AARCH64_MOVW_PREL_G2 -> "R_AARCH64_MOVW_PREL_G2"
            R_AARCH64_MOVW_PREL_G2_NC -> "R_AARCH64_MOVW_PREL_G2_NC"
            R_AARCH64_MOVW_PREL_G3 -> "R_AARCH64_MOVW_PREL_G3"
            R_AARCH64_LDST128_ABS_LO12_NC -> "R_AARCH64_LDST128_ABS_LO12_NC"
            R_AARCH64_MOVW_GOTOFF_G0 -> "R_AARCH64_MOVW_GOTOFF_G0"
            R_AARCH64_MOVW_GOTOFF_G0_NC -> "R_AARCH64_MOVW_GOTOFF_G0_NC"
            R_AARCH64_MOVW_GOTOFF_G1 -> "R_AARCH64_MOVW_GOTOFF_G1"
            R_AARCH64_MOVW_GOTOFF_G1_NC -> "R_AARCH64_MOVW_GOTOFF_G1_NC"
            R_AARCH64_MOVW_GOTOFF_G2 -> "R_AARCH64_MOVW_GOTOFF_G2"
            R_AARCH64_MOVW_GOTOFF_G2_NC -> "R_AARCH64_MOVW_GOTOFF_G2_NC"
            R_AARCH64_MOVW_GOTOFF_G3 -> "R_AARCH64_MOVW_GOTOFF_G3"
            R_AARCH64_GOTREL64 -> "R_AARCH64_GOTREL64"
            R_AARCH64_GOTREL32 -> "R_AARCH64_GOTREL32"
            R_AARCH64_GOT_LD_PREL19 -> "R_AARCH64_GOT_LD_PREL19"
            R_AARCH64_LD64_GOTOFF_LO15 -> "R_AARCH64_LD64_GOTOFF_LO15"
            R_AARCH64_ADR_GOT_PAGE -> "R_AARCH64_ADR_GOT_PAGE"
            R_AARCH64_LD64_GOT_LO12_NC -> "R_AARCH64_LD64_GOT_LO12_NC"
            R_AARCH64_LD64_GOTPAGE_LO15 -> "R_AARCH64_LD64_GOTPAGE_LO15"
            R_AARCH64_TLSGD_ADR_PREL21 -> "R_AARCH64_TLSGD_ADR_PREL21"
            R_AARCH64_TLSGD_ADR_PAGE21 -> "R_AARCH64_TLSGD_ADR_PAGE21"
            R_AARCH64_TLSGD_ADD_LO12_NC -> "R_AARCH64_TLSGD_ADD_LO12_NC"
            R_AARCH64_TLSGD_MOVW_G1 -> "R_AARCH64_TLSGD_MOVW_G1"
            R_AARCH64_TLSGD_MOVW_G0_NC -> "R_AARCH64_TLSGD_MOVW_G0_NC"
            R_AARCH64_TLSLD_ADR_PREL21 -> "R_AARCH64_TLSLD_ADR_PREL21"
            R_AARCH64_TLSLD_ADR_PAGE21 -> "R_AARCH64_TLSLD_ADR_PAGE21"
            R_AARCH64_TLSLD_ADD_LO12_NC -> "R_AARCH64_TLSLD_ADD_LO12_NC"
            R_AARCH64_TLSLD_MOVW_G1 -> "R_AARCH64_TLSLD_MOVW_G1"
            R_AARCH64_TLSLD_MOVW_G0_NC -> "R_AARCH64_TLSLD_MOVW_G0_NC"
            R_AARCH64_TLSLD_LD_PREL19 -> "R_AARCH64_TLSLD_LD_PREL19"
            R_AARCH64_TLSLD_MOVW_DTPREL_G2 -> "R_AARCH64_TLSLD_MOVW_DTPREL_G2"
            R_AARCH64_TLSLD_MOVW_DTPREL_G1 -> "R_AARCH64_TLSLD_MOVW_DTPREL_G1"
            R_AARCH64_TLSLD_MOVW_DTPREL_G1_NC -> "R_AARCH64_TLSLD_MOVW_DTPREL_G1_NC"
            R_AARCH64_TLSLD_MOVW_DTPREL_G0 -> "R_AARCH64_TLSLD_MOVW_DTPREL_G0"
            R_AARCH64_TLSLD_MOVW_DTPREL_G0_NC -> "R_AARCH64_TLSLD_MOVW_DTPREL_G0_NC"
            R_AARCH64_TLSLD_ADD_DTPREL_HI12 -> "R_AARCH64_TLSLD_ADD_DTPREL_HI12"
            R_AARCH64_TLSLD_ADD_DTPREL_LO12 -> "R_AARCH64_TLSLD_ADD_DTPREL_LO12"
            R_AARCH64_TLSLD_ADD_DTPREL_LO12_NC -> "R_AARCH64_TLSLD_ADD_DTPREL_LO12_NC"
            R_AARCH64_TLSLD_LDST8_DTPREL_LO12 -> "R_AARCH64_TLSLD_LDST8_DTPREL_LO12"
            R_AARCH64_TLSLD_LDST8_DTPREL_LO12_NC -> "R_AARCH64_TLSLD_LDST8_DTPREL_LO12_NC"
            R_AARCH64_TLSLD_LDST16_DTPREL_LO12 -> "R_AARCH64_TLSLD_LDST16_DTPREL_LO12"
            R_AARCH64_TLSLD_LDST16_DTPREL_LO12_NC -> "R_AARCH64_TLSLD_LDST16_DTPREL_LO12_NC"
            R_AARCH64_TLSLD_LDST32_DTPREL_LO12 -> "R_AARCH64_TLSLD_LDST32_DTPREL_LO12"
            R_AARCH64_TLSLD_LDST32_DTPREL_LO12_NC -> "R_AARCH64_TLSLD_LDST32_DTPREL_LO12_NC"
            R_AARCH64_TLSLD_LDST64_DTPREL_LO12 -> "R_AARCH64_TLSLD_LDST64_DTPREL_LO12"
            R_AARCH64_TLSLD_LDST64_DTPREL_LO12_NC -> "R_AARCH64_TLSLD_LDST64_DTPREL_LO12_NC"
            R_AARCH64_TLSIE_MOVW_GOTTPREL_G1 -> "R_AARCH64_TLSIE_MOVW_GOTTPREL_G1"
            R_AARCH64_TLSIE_MOVW_GOTTPREL_G0_NC -> "R_AARCH64_TLSIE_MOVW_GOTTPREL_G0_NC"
            R_AARCH64_TLSIE_ADR_GOTTPREL_PAGE21 -> "R_AARCH64_TLSIE_ADR_GOTTPREL_PAGE21"
            R_AARCH64_TLSIE_LD64_GOTTPREL_LO12_NC -> "R_AARCH64_TLSIE_LD64_GOTTPREL_LO12_NC"
            R_AARCH64_TLSIE_LD_GOTTPREL_PREL19 -> "R_AARCH64_TLSIE_LD_GOTTPREL_PREL19"
            R_AARCH64_TLSLE_MOVW_TPREL_G2 -> "R_AARCH64_TLSLE_MOVW_TPREL_G2"
            R_AARCH64_TLSLE_MOVW_TPREL_G1 -> "R_AARCH64_TLSLE_MOVW_TPREL_G1"
            R_AARCH64_TLSLE_MOVW_TPREL_G1_NC -> "R_AARCH64_TLSLE_MOVW_TPREL_G1_NC"
            R_AARCH64_TLSLE_MOVW_TPREL_G0 -> "R_AARCH64_TLSLE_MOVW_TPREL_G0"
            R_AARCH64_TLSLE_MOVW_TPREL_G0_NC -> "R_AARCH64_TLSLE_MOVW_TPREL_G0_NC"
            R_AARCH64_TLSLE_ADD_TPREL_HI12 -> "R_AARCH64_TLSLE_ADD_TPREL_HI12"
            R_AARCH64_TLSLE_ADD_TPREL_LO12 -> "R_AARCH64_TLSLE_ADD_TPREL_LO12"
            R_AARCH64_TLSLE_ADD_TPREL_LO12_NC -> "R_AARCH64_TLSLE_ADD_TPREL_LO12_NC"
            R_AARCH64_TLSLE_LDST8_TPREL_LO12 -> "R_AARCH64_TLSLE_LDST8_TPREL_LO12"
            R_AARCH64_TLSLE_LDST8_TPREL_LO12_NC -> "R_AARCH64_TLSLE_LDST8_TPREL_LO12_NC"
            R_AARCH64_TLSLE_LDST16_TPREL_LO12 -> "R_AARCH64_TLSLE_LDST16_TPREL_LO12"
            R_AARCH64_TLSLE_LDST16_TPREL_LO12_NC -> "R_AARCH64_TLSLE_LDST16_TPREL_LO12_NC"
            R_AARCH64_TLSLE_LDST32_TPREL_LO12 -> "R_AARCH64_TLSLE_LDST32_TPREL_LO12"
            R_AARCH64_TLSLE_LDST32_TPREL_LO12_NC -> "R_AARCH64_TLSLE_LDST32_TPREL_LO12_NC"
            R_AARCH64_TLSLE_LDST64_TPREL_LO12 -> "R_AARCH64_TLSLE_LDST64_TPREL_LO12"
            R_AARCH64_TLSLE_LDST64_TPREL_LO12_NC -> "R_AARCH64_TLSLE_LDST64_TPREL_LO12_NC"
            R_AARCH64_TLSDESC_LD_PREL19 -> "R_AARCH64_TLSDESC_LD_PREL19"
            R_AARCH64_TLSDESC_ADR_PREL21 -> "R_AARCH64_TLSDESC_ADR_PREL21"
            R_AARCH64_TLSDESC_ADR_PAGE21 -> "R_AARCH64_TLSDESC_ADR_PAGE21"
            R_AARCH64_TLSDESC_LD64_LO12 -> "R_AARCH64_TLSDESC_LD64_LO12"
            R_AARCH64_TLSDESC_ADD_LO12 -> "R_AARCH64_TLSDESC_ADD_LO12"
            R_AARCH64_TLSDESC_OFF_G1 -> "R_AARCH64_TLSDESC_OFF_G1"
            R_AARCH64_TLSDESC_OFF_G0_NC -> "R_AARCH64_TLSDESC_OFF_G0_NC"
            R_AARCH64_TLSDESC_LDR -> "R_AARCH64_TLSDESC_LDR"
            R_AARCH64_TLSDESC_ADD -> "R_AARCH64_TLSDESC_ADD"
            R_AARCH64_TLSDESC_CALL -> "R_AARCH64_TLSDESC_CALL"
            R_AARCH64_TLSLE_LDST128_TPREL_LO12 -> "R_AARCH64_TLSLE_LDST128_TPREL_LO12"
            R_AARCH64_TLSLE_LDST128_TPREL_LO12_NC -> "R_AARCH64_TLSLE_LDST128_TPREL_LO12_NC"
            R_AARCH64_TLSLD_LDST128_DTPREL_LO12 -> "R_AARCH64_TLSLD_LDST128_DTPREL_LO12"
            R_AARCH64_TLSLD_LDST128_DTPREL_LO12_NC -> "R_AARCH64_TLSLD_LDST128_DTPREL_LO12_NC"
            R_AARCH64_COPY -> "R_AARCH64_COPY"
            R_AARCH64_GLOB_DAT -> "R_AARCH64_GLOB_DAT"
            R_AARCH64_JUMP_SLOT -> "R_AARCH64_JUMP_SLOT"
            R_AARCH64_RELATIVE -> "R_AARCH64_RELATIVE"
            R_AARCH64_TLS_DTPMOD -> "R_AARCH64_TLS_DTPMOD"
            R_AARCH64_TLS_DTPREL -> "R_AARCH64_TLS_DTPREL"
            R_AARCH64_TLS_TPREL -> "R_AARCH64_TLS_TPREL"
            R_AARCH64_TLSDESC -> "R_AARCH64_TLSDESC"
            R_AARCH64_IRELATIVE -> "R_AARCH64_IRELATIVE"
            else -> "R_AARCH64_UNKNOWN(${relocationType()})"
        }
    }

    private fun relocationTypeStringWithX86_64(): String {
        return when (relocationType()) {
            R_X86_64_NONE -> "R_X86_64_NONE"
            R_X86_64_64 -> "R_X86_64_64"
            R_X86_64_PC32 -> "R_X86_64_PC32"
            R_X86_64_GOT32 -> "R_X86_64_GOT32"
            R_X86_64_PLT32 -> "R_X86_64_PLT32"
            R_X86_64_COPY -> "R_X86_64_COPY"
            R_X86_64_GLOB_DAT -> "R_X86_64_GLOB_DAT"
            R_X86_64_JUMP_SLOT -> "R_X86_64_JUMP_SLOT"
            R_X86_64_RELATIVE -> "R_X86_64_RELATIVE"
            R_X86_64_GOTPCREL -> "R_X86_64_GOTPCREL"
            R_X86_64_32 -> "R_X86_64_32"
            R_X86_64_32S -> "R_X86_64_32S"
            R_X86_64_16 -> "R_X86_64_16"
            R_X86_64_PC16 -> "R_X86_64_PC16"
            R_X86_64_8 -> "R_X86_64_8"
            R_X86_64_PC8 -> "R_X86_64_PC8"
            R_X86_64_DTPMOD64 -> "R_X86_64_DTPMOD64"
            R_X86_64_DTPOFF64 -> "R_X86_64_DTPOFF64"
            R_X86_64_TPOFF64 -> "R_X86_64_TPOFF64"
            R_X86_64_TLSGD -> "R_X86_64_TLSGD"
            R_X86_64_TLSLD -> "R_X86_64_TLSLD"
            R_X86_64_DTPOFF32 -> "R_X86_64_DTPOFF32"
            R_X86_64_GOTTPOFF -> "R_X86_64_GOTTPOFF"
            R_X86_64_TPOFF32 -> "R_X86_64_TPOFF32"
            R_X86_64_PC64 -> "R_X86_64_PC64"
            R_X86_64_GOTOFF64 -> "R_X86_64_GOTOFF64"
            R_X86_64_GOTPC32 -> "R_X86_64_GOTPC32"
            R_X86_64_GOT64 -> "R_X86_64_GOT64"
            R_X86_64_GOTPCREL64 -> "R_X86_64_GOTPCREL64"
            R_X86_64_GOTPC64 -> "R_X86_64_GOTPC64"
            R_X86_64_GOTPLT64 -> "R_X86_64_GOTPLT64"
            R_X86_64_PLTOFF64 -> "R_X86_64_PLTOFF64"
            R_X86_64_SIZE32 -> "R_X86_64_SIZE32"
            R_X86_64_SIZE64 -> "R_X86_64_SIZE64"
            R_X86_64_GOTPC32_TLSDESC -> "R_X86_64_GOTPC32_TLSDESC"
            R_X86_64_TLSDESC_CALL -> "R_X86_64_TLSDESC_CALL"
            R_X86_64_TLSDESC -> "R_X86_64_TLSDESC"
            R_X86_64_IRELATIVE -> "R_X86_64_IRELATIVE"
            R_X86_64_RELATIVE64 -> "R_X86_64_RELATIVE64"
            R_X86_64_GOTPCRELX -> "R_X86_64_GOTPCRELX"
            R_X86_64_REX_GOTPCRELX -> "R_X86_64_REX_GOTPCRELX"
            else -> "R_X86_64_UNKNOWN(${relocationType()})"
        }
    }

    /**
     * 如果 dynamicString 不为 null，那么根据 symbolIndex 获取 dynamic string 中对应的字符串
     * 如果 dynamicString 为 null，那么返回的将是 symbolIndex 转化成 16 进制数的字符串
     * @return symbol 对应的字符串
     */
    private fun symbolString(): String {
        return if (dynsymSections != null) {
            dynsymSections[symbolIndex().toInt()].getName()
        }
        else {
            FormatHelpers.toHexString(symbolIndex())
        }
    }

    override fun toString(): String {
        return "Elf64_Rela(" +
                "r_offset=${FormatHelpers.toHexString(r_offset)}" +
                ", r_info=${FormatHelpers.toHexString(r_info)}" +
                ", symbolIndex=${FormatHelpers.toHexString(symbolIndex())}" +
                ", symbol=\"${symbolString()}\"" +
                ", relocationType=${relocationTypeString()}" +
                ", r_addend=${FormatHelpers.toHexString(r_addend)}" +
                ")"
    }
}
