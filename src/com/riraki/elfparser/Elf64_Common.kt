package com.riraki.elfparser

// typedef uint16_t Elf64_Half;
typealias Elf64_Half = UShort

// typedef uint32_t Elf64_Word;
typealias Elf64_Word = UInt

// typedef uint64_t Elf64_Addr;
typealias Elf64_Addr = ULong

// typedef uint64_t Elf64_Off;
typealias Elf64_Off = ULong

// typedef uint64_t Elf64_Xword;
typealias Elf64_Xword = ULong

// typedef uint16_t Elf64_Section;
typealias Elf64_Section = UShort

// typedef	int64_t  Elf64_Sxword;
typealias Elf64_Sxword = ULong



/* Legal values for p_type (segment type).  */
const val PT_NULL = 0u                      /* Program header table entry unused */
const val PT_LOAD = 1u                      /* Loadable program segment */
const val PT_DYNAMIC = 2u                   /* Dynamic linking information */
const val PT_INTERP = 3u                    /* Program interpreter */
const val PT_NOTE = 4u                      /* Auxiliary information */
const val PT_SHLIB = 5u                     /* Reserved */
const val PT_PHDR = 6u                      /* Entry for header table itself */
const val PT_TLS = 7u                       /* Thread-local storage segment */
const val PT_NUM = 8u                       /* Number of defined types */
const val PT_LOOS = 0x60000000u             /* Start of OS-specific */
const val PT_GNU_EH_FRAME = 0x6474e550u     /* GCC .eh_frame_hdr segment */
const val PT_GNU_STACK = 0x6474e551u        /* Indicates stack executability */
const val PT_GNU_RELRO = 0x6474e552u        /* Read-only after relocation */
const val PT_GNU_PROPERTY = 0x6474e553u     /* GNU property */
const val PT_GNU_SFRAME = 0x6474e554u       /* SFrame segment.  */
const val PT_SUNWBSS = 0x6ffffffau          /* Sun Specific segment */
const val PT_SUNWSTACK = 0x6ffffffbu        /* Stack segment */
const val PT_HIOS = 0x6fffffffu             /* End of OS-specific */
const val PT_LOPROC = 0x70000000u           /* Start of processor-specific */
const val PT_HIPROC = 0x7fffffffu           /* End of processor-specific */



/* Legal values for p_flags (segment flags).  */
const val PF_X = 1u                         /* Segment is executable */
const val PF_W = 2u                         /* Segment is writable */
const val PF_R = 4u                         /* Segment is readable */
const val PF_MASKOS = 0x0ff00000u           /* OS-specific */
const val PF_MASKPROC = 0xf0000000u         /* Processor-specific */



/* Legal values for sh_type (section type).  */
const val SHT_NULL = 0u                     /* Section header table entry unused */
const val SHT_PROGBITS = 1u                 /* Program data */
const val SHT_SYMTAB = 2u                   /* Symbol table */
const val SHT_STRTAB = 3u                   /* String table */
const val SHT_RELA = 4u                     /* Relocation entries with addends */
const val SHT_HASH = 5u                     /* Symbol hash table */
const val SHT_DYNAMIC = 6u                  /* Dynamic linking information */
const val SHT_NOTE = 7u                     /* Notes */
const val SHT_NOBITS = 8u                   /* Program space with no data (bss) */
const val SHT_REL = 9u                      /* Relocation entries, no addends */
const val SHT_SHLIB = 10u                   /* Reserved */
const val SHT_DYNSYM = 11u                  /* Dynamic linker symbol table */
const val SHT_INIT_ARRAY = 14u              /* Array of constructors */
const val SHT_FINI_ARRAY = 15u              /* Array of destructors */
const val SHT_PREINIT_ARRAY = 16u           /* Array of pre-constructors */
const val SHT_GROUP = 17u                   /* Section group */
const val SHT_SYMTAB_SHNDX = 18u            /* Extended section indices */
const val SHT_RELR = 19u                    /* RELR relative relocations */
const val SHT_NUM = 20u                     /* Number of defined types.  */
const val SHT_LOOS = 0x60000000u            /* Start OS-specific.  */
const val SHT_GNU_ATTRIBUTES = 0x6ffffff5u  /* Object attributes.  */
const val SHT_GNU_HASH = 0x6ffffff6u        /* GNU-style hash table.  */
const val SHT_GNU_LIBLIST = 0x6ffffff7u     /* Prelink library list */
const val SHT_CHECKSUM = 0x6ffffff8u        /* Checksum for DSO content.  */
const val SHT_LOSUNW = 0x6ffffffau          /* Sun-specific low bound.  */
const val SHT_GNU_verdef = 0x6ffffffdu      /* Version definition section.  */
const val SHT_GNU_verneed = 0x6ffffffeu     /* Version needs section.  */
const val SHT_GNU_versym = 0x6fffffffu      /* Version symbol table.  */
const val SHT_HISUNW = 0x6fffffffu          /* Sun-specific high bound.  */
const val SHT_HIOS = 0x6fffffffu            /* End OS-specific type */
const val SHT_LOPROC = 0x70000000u          /* Start of processor-specific */
const val SHT_HIPROC = 0x7fffffffu          /* End of processor-specific */
const val SHT_LOUSER = 0x80000000u          /* Start of application-specific */
const val SHT_HIUSER = 0xffffffffu          /* End of application-specific */



/* Legal values for sh_flags (section flags).  */
const val SHF_WRITE = 0x1UL                 /* Writable */
const val SHF_ALLOC = 0x2UL                 /* Occupies memory during execution */
const val SHF_EXECINSTR = 0x4UL             /* Executable */
const val SHF_MERGE = 0x10UL                /* Might be merged */
const val SHF_STRINGS = 0x20UL              /* Contains nul-terminated strings */
const val SHF_INFO_LINK = 0x40UL            /* `sh_info' contains SHT index */
const val SHF_LINK_ORDER = 0x80UL           /* Preserve order after combining */
const val SHF_OS_NONCONFORMING = 0x100UL    /* Non-standard OS specific handling required */
const val SHF_GROUP = 0x200UL               /* Section is member of a group.  */
const val SHF_TLS = 0x400UL                 /* Section hold thread-local data.  */
const val SHF_COMPRESSED = 0x800UL          /* Section with compressed data. */
const val SHF_MASKOS = 0x0ff00000UL         /* OS-specific.  */
const val SHF_MASKPROC = 0xf0000000UL       /* Processor-specific */
const val SHF_GNU_RETAIN = 0x200000UL       /* Not to be GCed by linker.  */
const val SHF_ORDERED = 0x40000000UL        /* Special ordering requirement (Solaris).  */
const val SHF_EXCLUDE = 0x80000000UL        /* Section is excluded unless referenced or allocated (Solaris).*/



/* Legal values for ST_BIND subfield of st_info (symbol binding).  */
const val STB_LOCAL = 0             /* Local symbol */
const val STB_GLOBAL = 1            /* Global symbol */
const val STB_WEAK = 2              /* Weak symbol */
const val STB_NUM = 3               /* Number of defined types.  */
const val STB_LOOS = 10             /* Start of OS-specific */
const val STB_GNU_UNIQUE = 10       /* Unique symbol.  */
const val STB_HIOS = 12             /* End of OS-specific */
const val STB_LOPROC = 13           /* Start of processor-specific */
const val STB_HIPROC = 15           /* End of processor-specific */



/* Legal values for ST_TYPE subfield of st_info (symbol type).  */
const val STT_NOTYPE = 0            /* Symbol type is unspecified */
const val STT_OBJECT = 1            /* Symbol is a data object */
const val STT_FUNC = 2              /* Symbol is a code object */
const val STT_SECTION = 3           /* Symbol associated with a section */
const val STT_FILE = 4              /* Symbol's name is file name */
const val STT_COMMON = 5            /* Symbol is a common data object */
const val STT_TLS = 6               /* Symbol is thread-local data object*/
const val STT_NUM = 7               /* Number of defined types.  */
const val STT_LOOS = 10             /* Start of OS-specific */
const val STT_GNU_IFUNC = 10        /* Symbol is indirect code object */
const val STT_HIOS = 12             /* End of OS-specific */
const val STT_LOPROC = 13           /* Start of processor-specific */
const val STT_HIPROC = 15           /* End of processor-specific */







