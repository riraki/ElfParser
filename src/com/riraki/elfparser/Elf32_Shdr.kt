package com.riraki.elfparser

data class Elf32_Shdr(
    val sh_name: Elf32_Word,                    // 节名称在字符串表中的偏移量
    val sh_type: Elf32_Word,                    // 节的类型
    val sh_flags: Elf32_Word,                  // 一组描述 Section 属性的位标志
    val sh_addr: Elf32_Addr,                    // 节的虚拟地址
    val sh_offset: Elf32_Off,                   // 节在文件中的偏移
    val sh_size: Elf32_Word,                   // 节的大小
    val sh_link: Elf32_Word,                    // 与其他节关联的索引
    val sh_info: Elf32_Word,                    // 与节类型相关的附加信息
    val sh_addralign: Elf32_Word,              // 地址对齐要求
    val sh_entsize: Elf32_Word,                // 固定大小表项的大小
    private val sectionStringTable: String      // elf 文件的存放 Section 的名称的字符串表，也就是 .shstrtab
) {
    /**
     * typedef struct
     * {
     *   Elf32_Word	sh_name;		/* Section name (string tbl index) */
     *   Elf32_Word	sh_type;		/* Section type */
     *   Elf32_Word	sh_flags;		/* Section flags */
     *   Elf32_Addr	sh_addr;		/* Section virtual addr at execution */
     *   Elf32_Off	sh_offset;		/* Section file offset */
     *   Elf32_Word	sh_size;		/* Section size in bytes */
     *   Elf32_Word	sh_link;		/* Link to another section */
     *   Elf32_Word	sh_info;		/* Additional section information */
     *   Elf32_Word	sh_addralign;		/* Section alignment */
     *   Elf32_Word	sh_entsize;		/* Entry size if section holds table */
     * } Elf32_Shdr;
     */

    fun getName(): String = nameString()

    private fun nameString(): String {
        val startIndex = sh_name.toInt()
        val endIndex = sectionStringTable.indexOf('\u0000', startIndex)

        if (endIndex == -1) {
            throw IllegalStateException("Invalid string table: missing null terminator at offset $startIndex")
        }

        return sectionStringTable.substring(startIndex, endIndex)
    }

    private fun typeString(): String {
        return when (sh_type) {
            SHT_NULL -> "SHT_NULL"
            SHT_PROGBITS -> "SHT_PROGBITS"
            SHT_SYMTAB -> "SHT_SYMTAB"
            SHT_STRTAB -> "SHT_STRTAB"
            SHT_RELA -> "SHT_RELA"
            SHT_HASH -> "SHT_HASH"
            SHT_DYNAMIC -> "SHT_DYNAMIC"
            SHT_NOTE -> "SHT_NOTE"
            SHT_NOBITS -> "SHT_NOBITS"
            SHT_REL -> "SHT_REL"
            SHT_SHLIB -> "SHT_SHLIB"
            SHT_DYNSYM -> "SHT_DYNSYM"
            SHT_INIT_ARRAY -> "SHT_INIT_ARRAY"
            SHT_FINI_ARRAY -> "SHT_FINI_ARRAY"
            SHT_PREINIT_ARRAY -> "SHT_PREINIT_ARRAY"
            SHT_GROUP -> "SHT_GROUP"
            SHT_SYMTAB_SHNDX -> "SHT_SYMTAB_SHNDX"
            SHT_RELR -> "SHT_RELR"
            SHT_NUM -> "SHT_NUM"
            SHT_LOOS -> "SHT_LOOS"
            SHT_GNU_ATTRIBUTES -> "SHT_GNU_ATTRIBUTES"
            SHT_GNU_HASH -> "SHT_GNU_HASH"
            SHT_GNU_LIBLIST -> "SHT_GNU_LIBLIST"
            SHT_CHECKSUM -> "SHT_CHECKSUM"
            SHT_LOSUNW -> "SHT_LOSUNW"
            SHT_GNU_verdef -> "SHT_GNU_verdef"
            SHT_GNU_verneed -> "SHT_GNU_verneed"
            SHT_GNU_versym -> "SHT_GNU_versym"
            SHT_HISUNW -> "SHT_HISUNW"
            SHT_HIOS -> "SHT_HIOS"
            SHT_LOPROC -> "SHT_LOPROC"
            SHT_HIPROC -> "SHT_HIPROC"
            SHT_LOUSER -> "SHT_LOUSER"
            SHT_HIUSER -> "SHT_HIUSER"
            SHT_ANDROID_RELR -> "SHT_ANDROID_RELR"
            SHT_ANDROID_REL -> "SHT_ANDROID_REL"
            SHT_ANDROID_RELA -> "SHT_ANDROID_RELA"
            SHT_RISCV_ATTRIBUTES -> "SHT_RISCV_ATTRIBUTES"
            else -> "SHT_UNKNOWN($sh_type)"
        }
    }

    private fun flagsString(): String {
        return buildList{
            if ((sh_flags.toULong() and SHF_WRITE) != 0uL) {
                add("SHF_WRITE")
            }
            if ((sh_flags.toULong() and SHF_ALLOC) != 0uL) {
                add("SHF_ALLOC")
            }
            if ((sh_flags.toULong() and SHF_EXECINSTR) != 0uL) {
                add("SHF_EXECINSTR")
            }
            if ((sh_flags.toULong() and SHF_MERGE) != 0uL) {
                add("SHF_MERGE")
            }
            if ((sh_flags.toULong() and SHF_STRINGS) != 0uL) {
                add("SHF_STRINGS")
            }
            if ((sh_flags.toULong() and SHF_INFO_LINK) != 0uL) {
                add("SHF_INFO_LINK")
            }
            if ((sh_flags.toULong() and SHF_LINK_ORDER) != 0uL) {
                add("SHF_LINK_ORDER")
            }
            if ((sh_flags.toULong() and SHF_OS_NONCONFORMING) != 0uL) {
                add("SHF_OS_NONCONFORMING")
            }
            if ((sh_flags.toULong() and SHF_GROUP) != 0uL) {
                add("SHF_GROUP")
            }
            if ((sh_flags.toULong() and SHF_TLS) != 0uL) {
                add("SHF_TLS")
            }
            if ((sh_flags.toULong() and SHF_COMPRESSED) != 0uL) {
                add("SHF_COMPRESSED")
            }
            if ((sh_flags.toULong() and SHF_MASKOS) != 0uL) {
                add("SHF_MASKOS")
            }
            if ((sh_flags.toULong() and SHF_MASKPROC) != 0uL) {
                add("SHF_MASKPROC")
            }
            if ((sh_flags.toULong() and SHF_GNU_RETAIN) != 0uL) {
                add("SHF_GNU_RETAIN")
            }
            if ((sh_flags.toULong() and SHF_ORDERED) != 0uL) {
                add("SHF_ORDERED")
            }
            if ((sh_flags.toULong() and SHF_EXCLUDE) != 0uL) {
                add("SHF_EXCLUDE")
            }
        }.ifEmpty { listOf("SHF_UNKNOWN($sh_flags)") }
            .joinToString("|")
    }

    override fun toString(): String {
        return "Elf32_Shdr(sh_name=\"${nameString()}\"" +
                ", sh_type=\"${typeString()}\"" +
                ", sh_flags=\"${flagsString()}\"" +
                ", sh_addr=$sh_addr" +
                ", sh_offset=$sh_offset" +
                ", sh_size=$sh_size" +
                ", sh_link=$sh_link" +
                ", sh_info=$sh_info" +
                ", sh_addralign=$sh_addralign" +
                ", sh_entsize=$sh_entsize)"
    }
}
