package com.riraki.elfparser

data class Elf32_Phdr(
    val p_type: Elf32_Word,     // 段的类型
    val p_offset: Elf32_Off,    // 对应内容在文件中的偏移
    val p_vaddr: Elf32_Addr,    // 对应内容的虚拟地址
    val p_paddr: Elf32_Addr,    // 物理地址字段
    val p_filesz: Elf32_Word,   // 文件中对应内容的大小
    val p_memsz: Elf32_Word,    // 内存中对应区域的大小
    val p_flags: Elf32_Word,    // 读、写、执行权限
    val p_align: Elf32_Word     // 对齐要求
) {
    /**
     * typedef struct {
     *   Elf32_Word p_type;
     *   Elf32_Off p_offset;
     *   Elf32_Addr p_vaddr;
     *   Elf32_Addr p_paddr;
     *   Elf32_Word p_filesz;
     *   Elf32_Word p_memsz;
     *   Elf32_Word p_flags;
     *   Elf32_Word p_align;
     * } Elf32_Phdr;
     */

    private fun typeString(): String {
        return when (p_type) {
            PT_NULL -> "PT_NULL"
            PT_LOAD -> "PT_LOAD"
            PT_DYNAMIC -> "PT_DYNAMIC"
            PT_INTERP -> "PT_INTERP"
            PT_NOTE -> "PT_NOTE"
            PT_SHLIB -> "PT_SHLIB"
            PT_PHDR -> "PT_PHDR"
            PT_TLS -> "PT_TLS"
            PT_NUM -> "PT_NUM"
            PT_LOOS -> "PT_LOOS"
            PT_GNU_EH_FRAME -> "PT_GNU_EH_FRAME"
            PT_GNU_STACK -> "PT_GNU_STACK"
            PT_GNU_RELRO -> "PT_GNU_RELRO"
            PT_GNU_PROPERTY -> "PT_GNU_PROPERTY"
            PT_GNU_SFRAME -> "PT_GNU_SFRAME"
            PT_SUNWBSS -> "PT_SUNWBSS"
            PT_SUNWSTACK -> "PT_SUNWSTACK"
            PT_HIOS -> "PT_HIOS"
            PT_LOPROC -> "PT_LOPROC"
            PT_HIPROC -> "PT_HIPROC"
            else -> "PT_UNKNOWN($p_type)"
        }
    }

    private fun flagsString(): String {
        return buildList {
            if ((p_flags and PF_X) != 0u) add("PF_X")
            if ((p_flags and PF_W) != 0u) add("PF_W")
            if ((p_flags and PF_R) != 0u) add("PF_R")
            if ((p_flags and PF_MASKOS) != 0u) add("PF_MASKOS")
            if ((p_flags and PF_MASKPROC) != 0u) add("PF_MASKPROC")
        }.ifEmpty { listOf("PF_UNKNOWN($p_flags)") }.joinToString("|")
    }

    override fun toString(): String {
        return "Elf32_Phdr(p_type=\"${typeString()}\"" +
                ", p_offset=${FormatHelpers.toHexString(p_offset)}" +
                ", p_vaddr=${FormatHelpers.toHexString(p_vaddr)}" +
                ", p_paddr=${FormatHelpers.toHexString(p_paddr)}" +
                ", p_filesz=${FormatHelpers.toHexString(p_filesz)}" +
                ", p_memsz=${FormatHelpers.toHexString(p_memsz)}" +
                ", p_flags=\"${flagsString()}\"" +
                ", p_align=${FormatHelpers.toHexString(p_align)})"
    }
}
