package com.riraki.elfparser

/**
 * @param d_tag
 *
 * @param d_un 这个 d_un 被解释为 d_val 时，它就是一个数值，可能为：
 *     大小与长度
 *     单项条目大小 / 计数
 *     字符串表偏移索引
 *     配置标志
 * 如果被解释为 d_ptr，那它就是一个虚拟内存地址
 *
 * @param dynamicString
 * 这个参数用来在 d_un 被解释成数值并且 d_tag 为 DT_NEEDED 时，用作获取其依赖库的名称
 * 其余时刻，该参数将会被忽略
 */
data class Elf64_Dyn(
    val d_tag: Elf64_Sxword,
    val d_un: ULong,
    private val dynamicString: String?
) {
    /**
     * typedef struct
     * {
     *   Elf64_Sxword	d_tag;			/* Dynamic entry type */
     *   union
     *     {
     *       Elf64_Xword d_val;		/* Integer value */
     *       Elf64_Addr d_ptr;			/* Address value */
     *     } d_un;
     * } Elf64_Dyn;
     */

    /**
     * 把 d_tag 转化为字符串
     */
    private fun tagString(): String {
        return when (d_tag) {
            DT_NULL -> "DT_NULL"
            DT_NEEDED -> "DT_NEEDED"
            DT_PLTRELSZ -> "DT_PLTRELSZ"
            DT_PLTGOT -> "DT_PLTGOT"
            DT_HASH -> "DT_HASH"
            DT_STRTAB -> "DT_STRTAB"
            DT_SYMTAB -> "DT_SYMTAB"
            DT_RELA -> "DT_RELA"
            DT_RELASZ -> "DT_RELASZ"
            DT_RELAENT -> "DT_RELAENT"
            DT_STRSZ -> "DT_STRSZ"
            DT_SYMENT -> "DT_SYMENT"
            DT_INIT -> "DT_INIT"
            DT_FINI -> "DT_FINI"
            DT_SONAME -> "DT_SONAME"
            DT_RPATH -> "DT_RPATH"
            DT_SYMBOLIC -> "DT_SYMBOLIC"
            DT_REL -> "DT_REL"
            DT_RELSZ -> "DT_RELSZ"
            DT_RELENT -> "DT_RELENT"
            DT_PLTREL -> "DT_PLTREL"
            DT_DEBUG -> "DT_DEBUG"
            DT_TEXTREL -> "DT_TEXTREL"
            DT_JMPREL -> "DT_JMPREL"
            DT_BIND_NOW -> "DT_BIND_NOW"
            DT_INIT_ARRAY -> "DT_INIT_ARRAY"
            DT_FINI_ARRAY -> "DT_FINI_ARRAY"
            DT_INIT_ARRAYSZ -> "DT_INIT_ARRAYSZ"
            DT_FINI_ARRAYSZ -> "DT_FINI_ARRAYSZ"
            DT_RUNPATH -> "DT_RUNPATH"
            DT_FLAGS -> "DT_FLAGS"
            DT_ENCODING -> "DT_ENCODING"
            DT_PREINIT_ARRAY -> "DT_PREINIT_ARRAY"
            DT_PREINIT_ARRAYSZ -> "DT_PREINIT_ARRAYSZ"
            DT_SYMTAB_SHNDX -> "DT_SYMTAB_SHNDX"
            DT_RELRSZ -> "DT_RELRSZ"
            DT_RELR -> "DT_RELR"
            DT_RELRENT -> "DT_RELRENT"
            DT_NUM -> "DT_NUM"
            DT_LOOS -> "DT_LOOS"
            DT_HIOS -> "DT_HIOS"
            DT_LOPROC -> "DT_LOPROC"
            DT_HIPROC -> "DT_HIPROC"
            DT_VALRNGLO -> "DT_VALRNGLO"
            DT_GNU_PRELINKED -> "DT_GNU_PRELINKED"
            DT_GNU_CONFLICTSZ -> "DT_GNU_CONFLICTSZ"
            DT_GNU_LIBLISTSZ -> "DT_GNU_LIBLISTSZ"
            DT_CHECKSUM -> "DT_CHECKSUM"
            DT_PLTPADSZ -> "DT_PLTPADSZ"
            DT_MOVEENT -> "DT_MOVEENT"
            DT_MOVESZ -> "DT_MOVESZ"
            DT_FEATURE_1 -> "DT_FEATURE_1"
            DT_POSFLAG_1 -> "DT_POSFLAG_1"
            DT_SYMINSZ -> "DT_SYMINSZ"
            DT_SYMINENT -> "DT_SYMINENT"
            DT_VALRNGHI -> "DT_VALRNGHI"
            DT_ADDRRNGLO -> "DT_ADDRRNGLO"
            DT_GNU_HASH -> "DT_GNU_HASH"
            DT_TLSDESC_PLT -> "DT_TLSDESC_PLT"
            DT_TLSDESC_GOT -> "DT_TLSDESC_GOT"
            DT_GNU_CONFLICT -> "DT_GNU_CONFLICT"
            DT_GNU_LIBLIST -> "DT_GNU_LIBLIST"
            DT_CONFIG -> "DT_CONFIG"
            DT_DEPAUDIT -> "DT_DEPAUDIT"
            DT_AUDIT -> "DT_AUDIT"
            DT_PLTPAD -> "DT_PLTPAD"
            DT_MOVETAB -> "DT_MOVETAB"
            DT_SYMINFO -> "DT_SYMINFO"
            DT_ADDRRNGHI -> "DT_ADDRRNGHI"
            DT_VERSYM -> "DT_VERSYM"
            DT_RELACOUNT -> "DT_RELACOUNT"
            DT_RELCOUNT -> "DT_RELCOUNT"
            DT_FLAGS_1 -> "DT_FLAGS_1"
            DT_VERDEF -> "DT_VERDEF"
            DT_VERDEFNUM -> "DT_VERDEFNUM"
            DT_VERNEED -> "DT_VERNEED"
            DT_VERNEEDNUM -> "DT_VERNEEDNUM"
            DT_AUXILIARY -> "DT_AUXILIARY"
            DT_FILTER -> "DT_FILTER"
            else -> "DT_UNKNOWN($d_tag)"
        }
    }

    /**
     * 把 d_un 转化为字符串
     */
    private fun unString(): String {
        return if (isPointerTag()) {
            FormatHelpers.toHexString(d_tag)
        }
        else {
            /**
             * 当 d_value 为数值时，其具体应该解释成什么还与 d_tag 有关
             *     当 d_tag 是 DT_NEEDED 时：这个 d_val 代表的是一个字符串在 .dynstr（动态字符串表）中的字节偏移量
             *     当 d_tag 是 DT_STRSZ 时：这个 d_val 代表的是整个字符串表的总大小（字节数）
             *     当 d_tag 是 DT_SYMENT 时：这个 d_val 代表的是单个符号表条目（Symbol Entry）的大小
             *     当 d_tag 是 DT_FLAGS 时：这个 d_val 代表的是一组位掩码标志（Bitmask Flags）
             *     其余情况认为是通用数值
             */
            when (d_tag) {
                DT_NEEDED -> ""
                DT_STRSZ -> FormatHelpers.toHexString(d_tag)
                DT_SYMENT -> d_tag.toString()
                DT_FLAGS -> ""
                else -> ""
            }
        }
    }

    /**
     * The d_tag member controls the interpretation of d_un.
     * 这个 union 到底是 d_val 还是 d_ptr 是按照 d_tag 的来决定的
     * 这个方法就是用来判断这个 union 到底是应该解释 d_val 还是 d_ptr
     *
     * 我解释的规则为：
     * 规则一：看它是否落在“大范围区间内” （标准化自动归类）
     *     解释为指针（d_ptr）：只要 d_tag 的值落在 DT_ADDRRNGLO (0x6ffffe00) 到 DT_ADDRRNGHI (0x6ffffeff) 之间，一律解释为内存地址
     *     解释为数值（d_val）：只要 d_tag 的值落在 DT_VALRNGLO (0x6ffffd00) 到 DT_VALRNGHI (0x6ffffdff) 之间，一律解释为数值/大小
     * 规则二：看它是否属于“常见的显式地址标签”（特例枚举）
     *     必须解释为指针（d_ptr）：代表它存的是某个表、段或函数在内存中的虚拟地址：
     *         DT_STRTAB（字符串表地址）
     *         DT_SYMTAB（符号表地址）
     *         DT_PLTGOT（GOT/PLT 表地址）
     *         DT_HASH / DT_GNU_HASH（哈希表地址）
     *         DT_RELA / DT_REL（重定位表地址）
     *         DT_INIT / DT_FINI（初始化/终止函数地址）
     *         DT_INIT_ARRAY / DT_FINI_ARRAY（初始化/终止函数指针数组地址）
     *         DT_VERSYM / DT_VERDEF / DT_VERNEED（版本控制表地址）
     *     必须解释为数值（d_val）：代表它存的是大小、字节数、数量、索引或标志：
     *         DT_NEEDED（依赖库名称在字符串表中的偏移索引）
     *         所有带 SZ（如 DT_STRSZ, DT_RELSZ —— 表示表的大小）的标签
     *         所有带 ENT（如 DT_SYMENT, DT_RELENT —— 表示单项结构的大小）的标签
     *         DT_FLAGS / DT_FLAGS_1（配置标志位）
     * 规则三：兜底默认规则（其余所有）
     *     如果一个标签不在上述任何地址区间内，也不在已知的地址特例名单中：统一默认解释为数值（d_val）
     *     因为在 ELF 设计中，“地址”是需要明确授权的特权属性，绝大多数未知的、扩展的、计数类的标签本质上全都是无符号整数
     */
    private fun isPointerTag(): Boolean {
        return when (d_tag) {
            // 规则一：
            // DT_ADDRRNGLO (0x6ffffe00) 到 DT_ADDRRNGHI (0x6ffffeff) 之间解释为指针
            DT_ADDRRNGLO, DT_GNU_HASH, DT_TLSDESC_PLT, DT_TLSDESC_GOT, DT_GNU_CONFLICT, DT_GNU_LIBLIST, DT_CONFIG
                , DT_DEPAUDIT, DT_AUDIT, DT_PLTPAD, DT_MOVETAB, DT_SYMINFO, DT_ADDRRNGHI -> true
            // DT_VALRNGLO (0x6ffffd00) 到 DT_VALRNGHI (0x6ffffdff) 之间解释为数值
            DT_VALRNGLO, DT_GNU_PRELINKED, DT_GNU_CONFLICTSZ, DT_GNU_LIBLISTSZ, DT_CHECKSUM, DT_PLTPADSZ, DT_MOVEENT
                , DT_MOVESZ, DT_FEATURE_1, DT_POSFLAG_1, DT_SYMINSZ, DT_SYMINENT, DT_VALRNGHI -> false

            // 规则二（删除了与规则一中重复的选项）：
            // 常见地址标签应该解释为指针
            DT_STRTAB, DT_SYMTAB, DT_PLTGOT, DT_HASH, DT_RELA, DT_REL, DT_INIT, DT_FINI, DT_INIT_ARRAY, DT_FINI_ARRAY
                , DT_VERSYM, DT_VERDEF, DT_VERNEED -> true
            // 其余标签中，带有 SZ 的
            DT_NEEDED, DT_PLTRELSZ, DT_RELASZ, DT_STRSZ, DT_RELSZ, DT_INIT_ARRAYSZ, DT_FINI_ARRAYSZ, DT_PREINIT_ARRAYSZ
                , DT_RELRSZ -> false
            // 其余标签中，带有 ENT 的
            DT_RELAENT, DT_SYMENT, DT_RELENT, DT_RELRENT -> false

            // 规则三（其余所有）：
            else -> false
        }
    }

    /**
     * 用来判断这个 union 到底是应该解释 d_val 还是 d_ptr
     */
    private fun isValueTag(): Boolean {
        return isPointerTag().not()
    }

    override fun toString(): String {
        return "Elf64_Dyn(d_tag=${tagString()}" +
                ", d_un=${unString()})"
    }
}
