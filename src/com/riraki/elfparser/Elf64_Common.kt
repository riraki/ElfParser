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
typealias Elf64_Sxword = Long



// elf parser constant
// 这个用来区分不同处理器的情况
enum class ElfMachine{
    EM_AARCH64,
    EM_X86_64,
    EM_ARM,
    EM_386,
}



/* Legal values for e_machine (architecture).  */
const val EM_AARCH64 = 183                  /* ARM AARCH64 */
const val EM_X86_64 =62                     /* AMD x86-64 architecture */
const val EM_ARM = 40                       /* ARM 32-bit architecture */
const val EM_386 = 3                        /* Intel 80386 architecture */



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

const val SHT_ANDROID_RELR = 0x6fffff00u
const val SHT_ANDROID_REL = 0x60000001u
const val SHT_ANDROID_RELA = 0x60000002u

const val SHT_RISCV_ATTRIBUTES = 0x70000003u



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



/* Legal values for d_tag (dynamic entry type). */
const val DT_NULL = 0L                 // Marks end of dynamic section
const val DT_NEEDED = 1L               // Name of needed library
const val DT_PLTRELSZ = 2L             // Size in bytes of PLT relocs
const val DT_PLTGOT = 3L               // Processor defined value
const val DT_HASH = 4L                 // Address of symbol hash table
const val DT_STRTAB = 5L               // Address of string table
const val DT_SYMTAB = 6L               // Address of symbol table
const val DT_RELA = 7L                 // Address of Rela relocs
const val DT_RELASZ = 8L               // Total size of Rela relocs
const val DT_RELAENT = 9L              // Size of one Rela reloc
const val DT_STRSZ = 10L               // Size of string table
const val DT_SYMENT = 11L              // Size of one symbol table entry
const val DT_INIT = 12L                // Address of init function
const val DT_FINI = 13L                // Address of termination function
const val DT_SONAME = 14L              // Name of shared object
const val DT_RPATH = 15L               // Library search path (deprecated)
const val DT_SYMBOLIC = 16L            // Start symbol search here
const val DT_REL = 17L                 // Address of Rel relocs
const val DT_RELSZ = 18L               // Total size of Rel relocs
const val DT_RELENT = 19L              // Size of one Rel reloc
const val DT_PLTREL = 20L              // Type of reloc in PLT
const val DT_DEBUG = 21L               // For debugging; unspecified
const val DT_TEXTREL = 22L             // Reloc might modify .text
const val DT_JMPREL = 23L              // Address of PLT relocs
const val DT_BIND_NOW = 24L            // Process relocations of object
const val DT_INIT_ARRAY = 25L          // Array with addresses of init fct
const val DT_FINI_ARRAY = 26L          // Array with addresses of fini fct
const val DT_INIT_ARRAYSZ = 27L        // Size in bytes of DT_INIT_ARRAY
const val DT_FINI_ARRAYSZ = 28L        // Size in bytes of DT_FINI_ARRAY
const val DT_RUNPATH = 29L             // Library search path
const val DT_FLAGS = 30L               // Flags for the object being loaded
const val DT_ENCODING = 32L            // Start of encoded range
const val DT_PREINIT_ARRAY = 32L       // Array with addresses of preinit fct
const val DT_PREINIT_ARRAYSZ = 33L     // Size in bytes of DT_PREINIT_ARRAY
const val DT_SYMTAB_SHNDX = 34L        // Address of SYMTAB_SHNDX section
const val DT_RELRSZ = 35L              // Total size of RELR relative relocations
const val DT_RELR = 36L                // Address of RELR relative relocations
const val DT_RELRENT = 37L             // Size of one RELR relative relocaction
const val DT_NUM = 38L                 // Number used
const val DT_LOOS = 0x6000000dL        // Start of OS-specific
const val DT_HIOS = 0x6ffff000L        // End of OS-specific
const val DT_LOPROC = 0x70000000L      // Start of processor-specific
const val DT_HIPROC = 0x7fffffffL      // End of processor-specific

// DT_* entries which fall between DT_VALRNGHI & DT_VALRNGLO
const val DT_VALRNGLO = 0x6ffffd00L
const val DT_GNU_PRELINKED = 0x6ffffdf5L   // Prelinking timestamp
const val DT_GNU_CONFLICTSZ = 0x6ffffdf6L  // Size of conflict section
const val DT_GNU_LIBLISTSZ = 0x6ffffdf7L   // Size of library list
const val DT_CHECKSUM = 0x6ffffdf8L
const val DT_PLTPADSZ = 0x6ffffdf9L
const val DT_MOVEENT = 0x6ffffdfaL
const val DT_MOVESZ = 0x6ffffdfbL
const val DT_FEATURE_1 = 0x6ffffdfcL     // Feature selection (DTF_*)
const val DT_POSFLAG_1 = 0x6ffffdfdL     // Flags for DT_* entries
const val DT_SYMINSZ = 0x6ffffdfeL     // Size of syminfo table (in bytes)
const val DT_SYMINENT = 0x6ffffdffL    // Entry size of syminfo
const val DT_VALRNGHI = 0x6ffffdffL

// DT_* entries which fall between DT_ADDRRNGHI & DT_ADDRRNGLO
const val DT_ADDRRNGLO = 0x6ffffe00L
const val DT_GNU_HASH = 0x6ffffef5L    // GNU-style hash table
const val DT_TLSDESC_PLT = 0x6ffffef6L
const val DT_TLSDESC_GOT = 0x6ffffef7L
const val DT_GNU_CONFLICT = 0x6ffffef8L  // Start of conflict section
const val DT_GNU_LIBLIST = 0x6ffffef9L   // Library list
const val DT_CONFIG = 0x6ffffefaL      // Configuration information
const val DT_DEPAUDIT = 0x6ffffefbL    // Dependency auditing
const val DT_AUDIT = 0x6ffffefcL       // Object auditing
const val DT_PLTPAD = 0x6ffffefdL      // PLT padding
const val DT_MOVETAB = 0x6ffffefeL     // Move table
const val DT_SYMINFO = 0x6ffffeffL     // Syminfo table
const val DT_ADDRRNGHI = 0x6ffffeffL

// Versioning entry types and GNU extensions
const val DT_VERSYM = 0x6ffffff0L
const val DT_RELACOUNT = 0x6ffffff9L
const val DT_RELCOUNT = 0x6ffffffaL
const val DT_FLAGS_1 = 0x6ffffffbL     // State flags
const val DT_VERDEF = 0x6ffffffcL      // Address of version definition table
const val DT_VERDEFNUM = 0x6ffffffdL   // Number of version definitions
const val DT_VERNEED = 0x6ffffffeL     // Address of table with needed versions
const val DT_VERNEEDNUM = 0x6fffffffL  // Number of needed versions

// Sun machine-independent extensions in processor-specific range
const val DT_AUXILIARY = 0x7ffffffdL   // Shared object to load before self
const val DT_FILTER = 0x7fffffffL      // Shared object to get values from

const val DT_ANDROID_RELR = 0x6fffe000L
const val DT_ANDROID_RELRSZ = 0x6fffe001L
const val DT_ANDROID_RELRENT = 0x6fffe003L
const val DT_ANDROID_RELRCOUNT = 0x6fffe005L
const val DT_ANDROID_REL = 0x6000000fL // DT_LOOS + 2
const val DT_ANDROID_RELSZ = 0x60000010L // DT_LOOS + 3
const val DT_ANDROID_RELA = 0x60000011L // DT_LOOS + 4
const val DT_ANDROID_RELASZ = 0x60000012L // DT_LOOS + 5

const val DT_AARCH64_MEMTAG_MODE = 0x70000009L
const val DT_AARCH64_MEMTAG_HEAP = 0x7000000bL
const val DT_AARCH64_MEMTAG_STACK = 0x7000000cL
const val DT_AARCH64_MEMTAG_GLOBALS = 0x7000000dL
const val DT_AARCH64_MEMTAG_GLOBALSSZ = 0x7000000fL



/* Values of `d_un.d_val' in the DT_FLAGS entry.  */
const val DF_ORIGIN = 0x00000001UL      /* Object may use DF_ORIGIN */
const val DF_SYMBOLIC = 0x00000002UL    /* Symbol resolutions starts here */
const val DF_TEXTREL = 0x00000004UL     /* Object contains text relocations */
const val DF_BIND_NOW = 0x00000008UL    /* No lazy binding for this object */
const val DF_STATIC_TLS = 0x00000010UL  /* Module uses the static TLS model */



/* State flags selectable in the `d_un.d_val' element of the DT_FLAGS_1
   entry in the dynamic section.  */
const val DF_1_NOW = 0x00000001UL           /* Set RTLD_NOW for this object.  */
const val DF_1_GLOBAL = 0x00000002UL        /* Set RTLD_GLOBAL for this object.  */
const val DF_1_GROUP = 0x00000004UL         /* Set RTLD_GROUP for this object.  */
const val DF_1_NODELETE = 0x00000008UL      /* Set RTLD_NODELETE for this object.*/
const val DF_1_LOADFLTR = 0x00000010UL      /* Trigger filtee loading at runtime.*/
const val DF_1_INITFIRST = 0x00000020UL     /* Set RTLD_INITFIRST for this object*/
const val DF_1_NOOPEN = 0x00000040UL        /* Set RTLD_NOOPEN for this object.  */
const val DF_1_ORIGIN = 0x00000080UL        /* $ORIGIN must be handled.  */
const val DF_1_DIRECT = 0x00000100UL        /* Direct binding enabled.  */
const val DF_1_TRANS = 0x00000200UL
const val DF_1_INTERPOSE = 0x00000400UL     /* Object is used to interpose.  */
const val DF_1_NODEFLIB = 0x00000800UL      /* Ignore default lib search path.  */
const val DF_1_NODUMP = 0x00001000UL        /* Object can't be dldump'ed.  */
const val DF_1_CONFALT = 0x00002000UL       /* Configuration alternative created.*/
const val DF_1_ENDFILTEE = 0x00004000UL     /* Filtee terminates filters search. */
const val DF_1_DISPRELDNE = 0x00008000UL    /* Disp reloc applied at build time. */
const val DF_1_DISPRELPND = 0x00010000UL    /* Disp reloc applied at run-time.  */
const val DF_1_NODIRECT = 0x00020000UL      /* Object has no-direct binding. */
const val DF_1_IGNMULDEF = 0x00040000UL
const val DF_1_NOKSYMS = 0x00080000UL
const val DF_1_NOHDR = 0x00100000UL
const val DF_1_EDITED = 0x00200000UL        /* Object is modified after built.  */
const val DF_1_NORELOC = 0x00400000UL
const val DF_1_SYMINTPOSE = 0x00800000UL    /* Object has individual interposers.  */
const val DF_1_GLOBAUDIT = 0x01000000UL     /* Global auditing required.  */
const val DF_1_SINGLETON = 0x02000000UL     /* Singleton symbols are used.  */
const val DF_1_STUB = 0x04000000UL
const val DF_1_PIE = 0x08000000UL
const val DF_1_KMOD = 0x10000000UL
const val DF_1_WEAKFILTER = 0x20000000UL
const val DF_1_NOCOMMON = 0x40000000UL



/* ARM relocs.  */
const val R_ARM_NONE: UInt = 0u                 // No reloc
const val R_ARM_PC24: UInt = 1u                 // Deprecated PC relative 26 bit branch.
const val R_ARM_ABS32: UInt = 2u                // Direct 32 bit
const val R_ARM_REL32: UInt = 3u                // PC relative 32 bit
const val R_ARM_PC13: UInt = 4u
const val R_ARM_ABS16: UInt = 5u                // Direct 16 bit
const val R_ARM_ABS12: UInt = 6u                // Direct 12 bit
const val R_ARM_THM_ABS5: UInt = 7u             // Direct & 0x7C (LDR, STR).
const val R_ARM_ABS8: UInt = 8u                 // Direct 8 bit
const val R_ARM_SBREL32: UInt = 9u
const val R_ARM_THM_PC22: UInt = 10u            // PC relative 24 bit (Thumb32 BL).
const val R_ARM_THM_PC8: UInt = 11u             // PC relative & 0x3FC (Thumb16 LDR, ADD, ADR).
const val R_ARM_AMP_VCALL9: UInt = 12u
const val R_ARM_SWI24: UInt = 13u               // Obsolete static relocation.
const val R_ARM_TLS_DESC: UInt = 13u            // Dynamic relocation.
const val R_ARM_THM_SWI8: UInt = 14u            // Reserved.
const val R_ARM_XPC25: UInt = 15u               // Reserved.
const val R_ARM_THM_XPC22: UInt = 16u           // Reserved.
const val R_ARM_TLS_DTPMOD32: UInt = 17u        // ID of module containing symbol
const val R_ARM_TLS_DTPOFF32: UInt = 18u        // Offset in TLS block
const val R_ARM_TLS_TPOFF32: UInt = 19u         // Offset in static TLS block
const val R_ARM_COPY: UInt = 20u                // Copy symbol at runtime
const val R_ARM_GLOB_DAT: UInt = 21u            // Create GOT entry
const val R_ARM_JUMP_SLOT: UInt = 22u           // Create PLT entry
const val R_ARM_RELATIVE: UInt = 23u            // Adjust by program base
const val R_ARM_GOTOFF: UInt = 24u              // 32 bit offset to GOT
const val R_ARM_GOTPC: UInt = 25u               // 32 bit PC relative offset to GOT
const val R_ARM_GOT32: UInt = 26u               // 32 bit GOT entry
const val R_ARM_PLT32: UInt = 27u               // Deprecated, 32 bit PLT address.
const val R_ARM_CALL: UInt = 28u                // PC relative 24 bit (BL, BLX).
const val R_ARM_JUMP24: UInt = 29u              // PC relative 24 bit (B, BL<cond>).
const val R_ARM_THM_JUMP24: UInt = 30u          // PC relative 24 bit (Thumb32 B.W).
const val R_ARM_BASE_ABS: UInt = 31u            // Adjust by program base.
const val R_ARM_ALU_PCREL_7_0: UInt = 32u       // Obsolete.
const val R_ARM_ALU_PCREL_15_8: UInt = 33u      // Obsolete.
const val R_ARM_ALU_PCREL_23_15: UInt = 34u     // Obsolete.
const val R_ARM_LDR_SBREL_11_0: UInt = 35u      // Deprecated, prog. base relative.
const val R_ARM_ALU_SBREL_19_12: UInt = 36u     // Deprecated, prog. base relative.
const val R_ARM_ALU_SBREL_27_20: UInt = 37u     // Deprecated, prog. base relative.
const val R_ARM_TARGET1: UInt = 38u
const val R_ARM_SBREL31: UInt = 39u             // Program base relative.
const val R_ARM_V4BX: UInt = 40u
const val R_ARM_TARGET2: UInt = 41u
const val R_ARM_PREL31: UInt = 42u              // 32 bit PC relative.
const val R_ARM_MOVW_ABS_NC: UInt = 43u         // Direct 16-bit (MOVW).
const val R_ARM_MOVT_ABS: UInt = 44u            // Direct high 16-bit (MOVT).
const val R_ARM_MOVW_PREL_NC: UInt = 45u        // PC relative 16-bit (MOVW).
const val R_ARM_MOVT_PREL: UInt = 46u           // PC relative (MOVT).
const val R_ARM_THM_MOVW_ABS_NC: UInt = 47u     // Direct 16 bit (Thumb32 MOVW).
const val R_ARM_THM_MOVT_ABS: UInt = 48u        // Direct high 16 bit (Thumb32 MOVT).
const val R_ARM_THM_MOVW_PREL_NC: UInt = 49u    // PC relative 16 bit (Thumb32 MOVW).
const val R_ARM_THM_MOVT_PREL: UInt = 50u       // PC relative high 16 bit (Thumb32 MOVT).
const val R_ARM_THM_JUMP19: UInt = 51u          // PC relative 20 bit (Thumb32 B<cond>.W).
const val R_ARM_THM_JUMP6: UInt = 52u           // PC relative X & 0x7E (Thumb16 CBZ, CBNZ).
const val R_ARM_THM_ALU_PREL_11_0: UInt = 53u   // PC relative 12 bit (Thumb32 ADR.W).
const val R_ARM_THM_PC12: UInt = 54u            // PC relative 12 bit (Thumb32 LDR{D,SB,H,SH}).
const val R_ARM_ABS32_NOI: UInt = 55u           // Direct 32-bit.
const val R_ARM_REL32_NOI: UInt = 56u           // PC relative 32-bit.
const val R_ARM_ALU_PC_G0_NC: UInt = 57u        // PC relative (ADD, SUB).
const val R_ARM_ALU_PC_G0: UInt = 58u           // PC relative (ADD, SUB).
const val R_ARM_ALU_PC_G1_NC: UInt = 59u        // PC relative (ADD, SUB).
const val R_ARM_ALU_PC_G1: UInt = 60u           // PC relative (ADD, SUB).
const val R_ARM_ALU_PC_G2: UInt = 61u           // PC relative (ADD, SUB).
const val R_ARM_LDR_PC_G1: UInt = 62u           // PC relative (LDR,STR,LDRB,STRB).
const val R_ARM_LDR_PC_G2: UInt = 63u           // PC relative (LDR,STR,LDRB,STRB).
const val R_ARM_LDRS_PC_G0: UInt = 64u          // PC relative (STR{D,H}, LDR{D,SB,H,SH}).
const val R_ARM_LDRS_PC_G1: UInt = 65u          // PC relative (STR{D,H}, LDR{D,SB,H,SH}).
const val R_ARM_LDRS_PC_G2: UInt = 66u          // PC relative (STR{D,H}, LDR{D,SB,H,SH}).
const val R_ARM_LDC_PC_G0: UInt = 67u           // PC relative (LDC, STC).
const val R_ARM_LDC_PC_G1: UInt = 68u           // PC relative (LDC, STC).
const val R_ARM_LDC_PC_G2: UInt = 69u           // PC relative (LDC, STC).
const val R_ARM_ALU_SB_G0_NC: UInt = 70u        // Program base relative (ADD,SUB).
const val R_ARM_ALU_SB_G0: UInt = 71u           // Program base relative (ADD,SUB).
const val R_ARM_ALU_SB_G1_NC: UInt = 72u        // Program base relative (ADD,SUB).
const val R_ARM_ALU_SB_G1: UInt = 73u           // Program base relative (ADD,SUB).
const val R_ARM_ALU_SB_G2: UInt = 74u           // Program base relative (ADD,SUB).
const val R_ARM_LDR_SB_G0: UInt = 75u           // Program base relative (LDR, STR, LDRB, STRB).
const val R_ARM_LDR_SB_G1: UInt = 76u           // Program base relative (LDR, STR, LDRB, STRB).
const val R_ARM_LDR_SB_G2: UInt = 77u           // Program base relative (LDR, STR, LDRB, STRB).
const val R_ARM_LDRS_SB_G0: UInt = 78u          // Program base relative (LDR, STR, LDRB, STRB).
const val R_ARM_LDRS_SB_G1: UInt = 79u          // Program base relative (LDR, STR, LDRB, STRB).
const val R_ARM_LDRS_SB_G2: UInt = 80u          // Program base relative (LDR, STR, LDRB, STRB).
const val R_ARM_LDC_SB_G0: UInt = 81u           // Program base relative (LDC,STC).
const val R_ARM_LDC_SB_G1: UInt = 82u           // Program base relative (LDC,STC).
const val R_ARM_LDC_SB_G2: UInt = 83u           // Program base relative (LDC,STC).
const val R_ARM_MOVW_BREL_NC: UInt = 84u        // Program base relative 16 bit (MOVW).
const val R_ARM_MOVT_BREL: UInt = 85u           // Program base relative high 16 bit (MOVT).
const val R_ARM_MOVW_BREL: UInt = 86u           // Program base relative 16 bit (MOVW).
const val R_ARM_THM_MOVW_BREL_NC: UInt = 87u    // Program base relative 16 bit (Thumb32 MOVW).
const val R_ARM_THM_MOVT_BREL: UInt = 88u       // Program base relative high 16 bit (Thumb32 MOVT).
const val R_ARM_THM_MOVW_BREL: UInt = 89u       // Program base relative 16 bit (Thumb32 MOVW).
const val R_ARM_TLS_GOTDESC: UInt = 90u
const val R_ARM_TLS_CALL: UInt = 91u
const val R_ARM_TLS_DESCSEQ: UInt = 92u         // TLS relaxation.
const val R_ARM_THM_TLS_CALL: UInt = 93u
const val R_ARM_PLT32_ABS: UInt = 94u
const val R_ARM_GOT_ABS: UInt = 95u             // GOT entry.
const val R_ARM_GOT_PREL: UInt = 96u            // PC relative GOT entry.
const val R_ARM_GOT_BREL12: UInt = 97u          // GOT entry relative to GOT origin (LDR).
const val R_ARM_GOTOFF12: UInt = 98u            // 12 bit, GOT entry relative to GOT origin (LDR, STR).
const val R_ARM_GOTRELAX: UInt = 99u
const val R_ARM_GNU_VTENTRY: UInt = 100u
const val R_ARM_GNU_VTINHERIT: UInt = 101u
const val R_ARM_THM_PC11: UInt = 102u           // PC relative & 0xFFE (Thumb16 B).
const val R_ARM_THM_PC9: UInt = 103u            // PC relative & 0x1FE (Thumb16 B/B<cond>).
const val R_ARM_TLS_GD32: UInt = 104u           // PC-rel 32 bit for global dynamic thread local data
const val R_ARM_TLS_LDM32: UInt = 105u          // PC-rel 32 bit for local dynamic thread local data
const val R_ARM_TLS_LDO32: UInt = 106u          // 32 bit offset relative to TLS block
const val R_ARM_TLS_IE32: UInt = 107u           // PC-rel 32 bit for GOT entry of static TLS block offset
const val R_ARM_TLS_LE32: UInt = 108u           // 32 bit offset relative to static TLS block
const val R_ARM_TLS_LDO12: UInt = 109u          // 12 bit relative to TLS block (LDR, STR).
const val R_ARM_TLS_LE12: UInt = 110u           // 12 bit relative to static TLS block (LDR, STR).
const val R_ARM_TLS_IE12GP: UInt = 111u         // 12 bit GOT entry relative to GOT origin (LDR).
const val R_ARM_ME_TOO: UInt = 128u             // Obsolete.
const val R_ARM_THM_TLS_DESCSEQ: UInt = 129u
const val R_ARM_THM_TLS_DESCSEQ16: UInt = 129u
const val R_ARM_THM_TLS_DESCSEQ32: UInt = 130u
const val R_ARM_THM_GOT_BREL12: UInt = 131u     // GOT entry relative to GOT origin, 12 bit (Thumb32 LDR).
const val R_ARM_IRELATIVE: UInt = 160u
const val R_ARM_RXPC25: UInt = 249u
const val R_ARM_RSBREL32: UInt = 250u
const val R_ARM_THM_RPC22: UInt = 251u
const val R_ARM_RREL32: UInt = 252u
const val R_ARM_RABS22: UInt = 253u
const val R_ARM_RPC24: UInt = 254u
const val R_ARM_RBASE: UInt = 255u
// Keep this the last entry.
const val R_ARM_NUM: UInt = 256u



/* AArch64 relocs.  */
const val R_AARCH64_NONE: UInt = 0u                     // No relocation.

const val R_AARCH64_ABS64: UInt = 257u                  // Direct 64 bit.
const val R_AARCH64_ABS32: UInt = 258u                  // Direct 32 bit.
const val R_AARCH64_ABS16: UInt = 259u                  // Direct 16-bit.
const val R_AARCH64_PREL64: UInt = 260u                 // PC-relative 64-bit.
const val R_AARCH64_PREL32: UInt = 261u                 // PC-relative 32-bit.
const val R_AARCH64_PREL16: UInt = 262u                 // PC-relative 16-bit.
const val R_AARCH64_MOVW_UABS_G0: UInt = 263u           // Dir. MOVZ imm. from bits 15:0.
const val R_AARCH64_MOVW_UABS_G0_NC: UInt = 264u        // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_UABS_G1: UInt = 265u           // Dir. MOVZ imm. from bits 31:16.
const val R_AARCH64_MOVW_UABS_G1_NC: UInt = 266u        // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_UABS_G2: UInt = 267u           // Dir. MOVZ imm. from bits 47:32.
const val R_AARCH64_MOVW_UABS_G2_NC: UInt = 268u        // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_UABS_G3: UInt = 269u           // Dir. MOV{K,Z} imm. from 63:48.
const val R_AARCH64_MOVW_SABS_G0: UInt = 270u           // Dir. MOV{N,Z} imm. from 15:0.
const val R_AARCH64_MOVW_SABS_G1: UInt = 271u           // Dir. MOV{N,Z} imm. from 31:16.
const val R_AARCH64_MOVW_SABS_G2: UInt = 272u           // Dir. MOV{N,Z} imm. from 47:32.
const val R_AARCH64_LD_PREL_LO19: UInt = 273u           // PC-rel. LD imm. from bits 20:2.
const val R_AARCH64_ADR_PREL_LO21: UInt = 274u          // PC-rel. ADR imm. from bits 20:0.
const val R_AARCH64_ADR_PREL_PG_HI21: UInt = 275u       // Page-rel. ADRP imm. from 32:12.
const val R_AARCH64_ADR_PREL_PG_HI21_NC: UInt = 276u    // Likewise; no overflow check.
const val R_AARCH64_ADD_ABS_LO12_NC: UInt = 277u        // Dir. ADD imm. from bits 11:0.
const val R_AARCH64_LDST8_ABS_LO12_NC: UInt = 278u      // Likewise for LD/ST; no check.
const val R_AARCH64_TSTBR14: UInt = 279u                // PC-rel. TBZ/TBNZ imm. from 15:2.
const val R_AARCH64_CONDBR19: UInt = 280u               // PC-rel. cond. br. imm. from 20:2.
const val R_AARCH64_JUMP26: UInt = 282u                 // PC-rel. B imm. from bits 27:2.
const val R_AARCH64_CALL26: UInt = 283u                 // Likewise for CALL.
const val R_AARCH64_LDST16_ABS_LO12_NC: UInt = 284u     // Dir. ADD imm. from bits 11:1.
const val R_AARCH64_LDST32_ABS_LO12_NC: UInt = 285u     // Likewise for bits 11:2.
const val R_AARCH64_LDST64_ABS_LO12_NC: UInt = 286u     // Likewise for bits 11:3.
const val R_AARCH64_MOVW_PREL_G0: UInt = 287u           // PC-rel. MOV{N,Z} imm. from 15:0.
const val R_AARCH64_MOVW_PREL_G0_NC: UInt = 288u        // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_PREL_G1: UInt = 289u           // PC-rel. MOV{N,Z} imm. from 31:16.
const val R_AARCH64_MOVW_PREL_G1_NC: UInt = 290u        // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_PREL_G2: UInt = 291u           // PC-rel. MOV{N,Z} imm. from 47:32.
const val R_AARCH64_MOVW_PREL_G2_NC: UInt = 292u        // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_PREL_G3: UInt = 293u           // PC-rel. MOV{N,Z} imm. from 63:48.
const val R_AARCH64_LDST128_ABS_LO12_NC: UInt = 299u    // Dir. ADD imm. from bits 11:4.
const val R_AARCH64_MOVW_GOTOFF_G0: UInt = 300u         // GOT-rel. off. MOV{N,Z} imm. 15:0.
const val R_AARCH64_MOVW_GOTOFF_G0_NC: UInt = 301u      // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_GOTOFF_G1: UInt = 302u         // GOT-rel. o. MOV{N,Z} imm. 31:16.
const val R_AARCH64_MOVW_GOTOFF_G1_NC: UInt = 303u      // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_GOTOFF_G2: UInt = 304u         // GOT-rel. o. MOV{N,Z} imm. 47:32.
const val R_AARCH64_MOVW_GOTOFF_G2_NC: UInt = 305u      // Likewise for MOVK; no check.
const val R_AARCH64_MOVW_GOTOFF_G3: UInt = 306u         // GOT-rel. o. MOV{N,Z} imm. 63:48.
const val R_AARCH64_GOTREL64: UInt = 307u               // GOT-relative 64-bit.
const val R_AARCH64_GOTREL32: UInt = 308u               // GOT-relative 32-bit.
const val R_AARCH64_GOT_LD_PREL19: UInt = 309u          // PC-rel. GOT off. load imm. 20:2.
const val R_AARCH64_LD64_GOTOFF_LO15: UInt = 310u       // GOT-rel. off. LD/ST imm. 14:3.
const val R_AARCH64_ADR_GOT_PAGE: UInt = 311u           // P-page-rel. GOT off. ADRP 32:12.
const val R_AARCH64_LD64_GOT_LO12_NC: UInt = 312u       // Dir. GOT off. LD/ST imm. 11:3.
const val R_AARCH64_LD64_GOTPAGE_LO15: UInt = 313u      // GOT-page-rel. GOT off. LD/ST 14:3
const val R_AARCH64_TLSGD_ADR_PREL21: UInt = 512u       // PC-relative ADR imm. 20:0.
const val R_AARCH64_TLSGD_ADR_PAGE21: UInt = 513u       // page-rel. ADRP imm. 32:12.
const val R_AARCH64_TLSGD_ADD_LO12_NC: UInt = 514u      // direct ADD imm. from 11:0.
const val R_AARCH64_TLSGD_MOVW_G1: UInt = 515u          // GOT-rel. MOV{N,Z} 31:16.
const val R_AARCH64_TLSGD_MOVW_G0_NC: UInt = 516u       // GOT-rel. MOVK imm. 15:0.
const val R_AARCH64_TLSLD_ADR_PREL21: UInt = 517u       // Like 512; local dynamic model.
const val R_AARCH64_TLSLD_ADR_PAGE21: UInt = 518u       // Like 513; local dynamic model.
const val R_AARCH64_TLSLD_ADD_LO12_NC: UInt = 519u      // Like 514; local dynamic model.
const val R_AARCH64_TLSLD_MOVW_G1: UInt = 520u          // Like 515; local dynamic model.
const val R_AARCH64_TLSLD_MOVW_G0_NC: UInt = 521u       // Like 516; local dynamic model.
const val R_AARCH64_TLSLD_LD_PREL19: UInt = 522u        // TLS PC-rel. load imm. 20:2.
const val R_AARCH64_TLSLD_MOVW_DTPREL_G2: UInt = 523u   // TLS DTP-rel. MOV{N,Z} 47:32.
const val R_AARCH64_TLSLD_MOVW_DTPREL_G1: UInt = 524u   // TLS DTP-rel. MOV{N,Z} 31:16.
const val R_AARCH64_TLSLD_MOVW_DTPREL_G1_NC: UInt = 525u // Likewise; MOVK; no check.
const val R_AARCH64_TLSLD_MOVW_DTPREL_G0: UInt = 526u   // TLS DTP-rel. MOV{N,Z} 15:0.
const val R_AARCH64_TLSLD_MOVW_DTPREL_G0_NC: UInt = 527u // Likewise; MOVK; no check.
const val R_AARCH64_TLSLD_ADD_DTPREL_HI12: UInt = 528u  // DTP-rel. ADD imm. from 23:12.
const val R_AARCH64_TLSLD_ADD_DTPREL_LO12: UInt = 529u  // DTP-rel. ADD imm. from 11:0.
const val R_AARCH64_TLSLD_ADD_DTPREL_LO12_NC: UInt = 530u // Likewise; no ovfl. check.
const val R_AARCH64_TLSLD_LDST8_DTPREL_LO12: UInt = 531u // DTP-rel. LD/ST imm. 11:0.
const val R_AARCH64_TLSLD_LDST8_DTPREL_LO12_NC: UInt = 532u // Likewise; no check.
const val R_AARCH64_TLSLD_LDST16_DTPREL_LO12: UInt = 533u // DTP-rel. LD/ST imm. 11:1.
const val R_AARCH64_TLSLD_LDST16_DTPREL_LO12_NC: UInt = 534u // Likewise; no check.
const val R_AARCH64_TLSLD_LDST32_DTPREL_LO12: UInt = 535u // DTP-rel. LD/ST imm. 11:2.
const val R_AARCH64_TLSLD_LDST32_DTPREL_LO12_NC: UInt = 536u // Likewise; no check.
const val R_AARCH64_TLSLD_LDST64_DTPREL_LO12: UInt = 537u // DTP-rel. LD/ST imm. 11:3.
const val R_AARCH64_TLSLD_LDST64_DTPREL_LO12_NC: UInt = 538u // Likewise; no check.
const val R_AARCH64_TLSIE_MOVW_GOTTPREL_G1: UInt = 539u // GOT-rel. MOV{N,Z} 31:16.
const val R_AARCH64_TLSIE_MOVW_GOTTPREL_G0_NC: UInt = 540u // GOT-rel. MOVK 15:0.
const val R_AARCH64_TLSIE_ADR_GOTTPREL_PAGE21: UInt = 541u // Page-rel. ADRP 32:12.
const val R_AARCH64_TLSIE_LD64_GOTTPREL_LO12_NC: UInt = 542u // Direct LD off. 11:3.
const val R_AARCH64_TLSIE_LD_GOTTPREL_PREL19: UInt = 543u // PC-rel. load imm. 20:2.
const val R_AARCH64_TLSLE_MOVW_TPREL_G2: UInt = 544u    // TLS TP-rel. MOV{N,Z} 47:32.
const val R_AARCH64_TLSLE_MOVW_TPREL_G1: UInt = 545u    // TLS TP-rel. MOV{N,Z} 31:16.
const val R_AARCH64_TLSLE_MOVW_TPREL_G1_NC: UInt = 546u // Likewise; MOVK; no check.
const val R_AARCH64_TLSLE_MOVW_TPREL_G0: UInt = 547u    // TLS TP-rel. MOV{N,Z} 15:0.
const val R_AARCH64_TLSLE_MOVW_TPREL_G0_NC: UInt = 548u // Likewise; MOVK; no check.
const val R_AARCH64_TLSLE_ADD_TPREL_HI12: UInt = 549u   // TP-rel. ADD imm. 23:12.
const val R_AARCH64_TLSLE_ADD_TPREL_LO12: UInt = 550u   // TP-rel. ADD imm. 11:0.
const val R_AARCH64_TLSLE_ADD_TPREL_LO12_NC: UInt = 551u // Likewise; no ovfl. check.
const val R_AARCH64_TLSLE_LDST8_TPREL_LO12: UInt = 552u // TP-rel. LD/ST off. 11:0.
const val R_AARCH64_TLSLE_LDST8_TPREL_LO12_NC: UInt = 553u // Likewise; no ovfl. check.
const val R_AARCH64_TLSLE_LDST16_TPREL_LO12: UInt = 554u // TP-rel. LD/ST off. 11:1.
const val R_AARCH64_TLSLE_LDST16_TPREL_LO12_NC: UInt = 555u // Likewise; no check.
const val R_AARCH64_TLSLE_LDST32_TPREL_LO12: UInt = 556u // TP-rel. LD/ST off. 11:2.
const val R_AARCH64_TLSLE_LDST32_TPREL_LO12_NC: UInt = 557u // Likewise; no check.
const val R_AARCH64_TLSLE_LDST64_TPREL_LO12: UInt = 558u // TP-rel. LD/ST off. 11:3.
const val R_AARCH64_TLSLE_LDST64_TPREL_LO12_NC: UInt = 559u // Likewise; no check.
const val R_AARCH64_TLSDESC_LD_PREL19: UInt = 560u      // PC-rel. load immediate 20:2.
const val R_AARCH64_TLSDESC_ADR_PREL21: UInt = 561u     // PC-rel. ADR immediate 20:0.
const val R_AARCH64_TLSDESC_ADR_PAGE21: UInt = 562u     // Page-rel. ADRP imm. 32:12.
const val R_AARCH64_TLSDESC_LD64_LO12: UInt = 563u      // Direct LD off. from 11:3.
const val R_AARCH64_TLSDESC_ADD_LO12: UInt = 564u       // Direct ADD imm. from 11:0.
const val R_AARCH64_TLSDESC_OFF_G1: UInt = 565u         // GOT-rel. MOV{N,Z} imm. 31:16.
const val R_AARCH64_TLSDESC_OFF_G0_NC: UInt = 566u      // GOT-rel. MOVK imm. 15:0; no ck.
const val R_AARCH64_TLSDESC_LDR: UInt = 567u            // Relax LDR.
const val R_AARCH64_TLSDESC_ADD: UInt = 568u            // Relax ADD.
const val R_AARCH64_TLSDESC_CALL: UInt = 569u           // Relax BLR.
const val R_AARCH64_TLSLE_LDST128_TPREL_LO12: UInt = 570u // TP-rel. LD/ST off. 11:4.
const val R_AARCH64_TLSLE_LDST128_TPREL_LO12_NC: UInt = 571u // Likewise; no check.
const val R_AARCH64_TLSLD_LDST128_DTPREL_LO12: UInt = 572u // DTP-rel. LD/ST imm. 11:4.
const val R_AARCH64_TLSLD_LDST128_DTPREL_LO12_NC: UInt = 573u // Likewise; no check.
const val R_AARCH64_COPY: UInt = 1024u                  // Copy symbol at runtime.
const val R_AARCH64_GLOB_DAT: UInt = 1025u              // Create GOT entry.
const val R_AARCH64_JUMP_SLOT: UInt = 1026u             // Create PLT entry.
const val R_AARCH64_RELATIVE: UInt = 1027u              // Adjust by program base.
const val R_AARCH64_TLS_DTPMOD: UInt = 1028u            // Module number, 64 bit.
const val R_AARCH64_TLS_DTPREL: UInt = 1029u            // Module-relative offset, 64 bit.
const val R_AARCH64_TLS_TPREL: UInt = 1030u             // TP-relative offset, 64 bit.
const val R_AARCH64_TLSDESC: UInt = 1031u               // TLS Descriptor.
const val R_AARCH64_IRELATIVE: UInt = 1032u             // STT_GNU_IFUNC relocation.



/* i386 relocs.  */
const val R_386_NONE: UInt = 0u             // No reloc
const val R_386_32: UInt = 1u               // Direct 32 bit
const val R_386_PC32: UInt = 2u             // PC relative 32 bit
const val R_386_GOT32: UInt = 3u            // 32 bit GOT entry
const val R_386_PLT32: UInt = 4u            // 32 bit PLT address
const val R_386_COPY: UInt = 5u             // Copy symbol at runtime
const val R_386_GLOB_DAT: UInt = 6u         // Create GOT entry
const val R_386_JMP_SLOT: UInt = 7u         // Create PLT entry
const val R_386_RELATIVE: UInt = 8u         // Adjust by program base
const val R_386_GOTOFF: UInt = 9u           // 32 bit offset to GOT
const val R_386_GOTPC: UInt = 10u           // 32 bit PC relative offset to GOT
const val R_386_32PLT: UInt = 11u
const val R_386_TLS_TPOFF: UInt = 14u       // Offset in static TLS block
const val R_386_TLS_IE: UInt = 15u          // Address of GOT entry for static TLS block offset
const val R_386_TLS_GOTIE: UInt = 16u       // GOT entry for static TLS block offset
const val R_386_TLS_LE: UInt = 17u          // Offset relative to static TLS block
const val R_386_TLS_GD: UInt = 18u          // Direct 32 bit for GNU version of general dynamic thread local data
const val R_386_TLS_LDM: UInt = 19u         // Direct 32 bit for GNU version of local dynamic thread local data in LE code
const val R_386_16: UInt = 20u
const val R_386_PC16: UInt = 21u
const val R_386_8: UInt = 22u
const val R_386_PC8: UInt = 23u
const val R_386_TLS_GD_32: UInt = 24u       // Direct 32 bit for general dynamic thread local data
const val R_386_TLS_GD_PUSH: UInt = 25u     // Tag for pushl in GD TLS code
const val R_386_TLS_GD_CALL: UInt = 26u     // Relocation for call to __tls_get_addr()
const val R_386_TLS_GD_POP: UInt = 27u      // Tag for popl in GD TLS code
const val R_386_TLS_LDM_32: UInt = 28u      // Direct 32 bit for local dynamic thread local data in LE code
const val R_386_TLS_LDM_PUSH: UInt = 29u    // Tag for pushl in LDM TLS code
const val R_386_TLS_LDM_CALL: UInt = 30u    // Relocation for call to __tls_get_addr() in LDM code
const val R_386_TLS_LDM_POP: UInt = 31u     // Tag for popl in LDM TLS code
const val R_386_TLS_LDO_32: UInt = 32u      // Offset relative to TLS block
const val R_386_TLS_IE_32: UInt = 33u       // GOT entry for negated static TLS block offset
const val R_386_TLS_LE_32: UInt = 34u       // Negated offset relative to static TLS block
const val R_386_TLS_DTPMOD32: UInt = 35u    // ID of module containing symbol
const val R_386_TLS_DTPOFF32: UInt = 36u    // Offset in TLS block
const val R_386_TLS_TPOFF32: UInt = 37u     // Negated offset in static TLS block
const val R_386_SIZE32: UInt = 38u          // 32-bit symbol size
const val R_386_TLS_GOTDESC: UInt = 39u     // GOT offset for TLS descriptor.
const val R_386_TLS_DESC_CALL: UInt = 40u   // Marker of call through TLS descriptor for relaxation.
const val R_386_TLS_DESC: UInt = 41u        // TLS descriptor containing pointer to code and to argument, returning the TLS offset for the symbol.
const val R_386_IRELATIVE: UInt = 42u       // Adjust indirectly by program base
const val R_386_GOT32X: UInt = 43u          // Load from 32 bit GOT entry, relaxable.
// Keep this the last entry.
const val R_386_NUM: UInt = 44u



/* AMD x86-64 relocations.  */
const val R_X86_64_NONE: UInt = 0u              // No reloc
const val R_X86_64_64: UInt = 1u                // Direct 64 bit
const val R_X86_64_PC32: UInt = 2u              // PC relative 32 bit signed
const val R_X86_64_GOT32: UInt = 3u             // 32 bit GOT entry
const val R_X86_64_PLT32: UInt = 4u             // 32 bit PLT address
const val R_X86_64_COPY: UInt = 5u              // Copy symbol at runtime
const val R_X86_64_GLOB_DAT: UInt = 6u          // Create GOT entry
const val R_X86_64_JUMP_SLOT: UInt = 7u         // Create PLT entry
const val R_X86_64_RELATIVE: UInt = 8u          // Adjust by program base
const val R_X86_64_GOTPCREL: UInt = 9u          // 32 bit signed PC relative offset to GOT
const val R_X86_64_32: UInt = 10u               // Direct 32 bit zero extended
const val R_X86_64_32S: UInt = 11u              // Direct 32 bit sign extended
const val R_X86_64_16: UInt = 12u               // Direct 16 bit zero extended
const val R_X86_64_PC16: UInt = 13u             // 16 bit sign extended pc relative
const val R_X86_64_8: UInt = 14u                // Direct 8 bit sign extended
const val R_X86_64_PC8: UInt = 15u              // 8 bit sign extended pc relative
const val R_X86_64_DTPMOD64: UInt = 16u         // ID of module containing symbol
const val R_X86_64_DTPOFF64: UInt = 17u         // Offset in module's TLS block
const val R_X86_64_TPOFF64: UInt = 18u          // Offset in initial TLS block
const val R_X86_64_TLSGD: UInt = 19u            // 32 bit signed PC relative offset to two GOT entries for GD symbol
const val R_X86_64_TLSLD: UInt = 20u            // 32 bit signed PC relative offset to two GOT entries for LD symbol
const val R_X86_64_DTPOFF32: UInt = 21u         // Offset in TLS block
const val R_X86_64_GOTTPOFF: UInt = 22u         // 32 bit signed PC relative offset to GOT entry for IE symbol
const val R_X86_64_TPOFF32: UInt = 23u          // Offset in initial TLS block
const val R_X86_64_PC64: UInt = 24u             // PC relative 64 bit
const val R_X86_64_GOTOFF64: UInt = 25u         // 64 bit offset to GOT
const val R_X86_64_GOTPC32: UInt = 26u          // 32 bit signed pc relative offset to GOT
const val R_X86_64_GOT64: UInt = 27u            // 64-bit GOT entry offset
const val R_X86_64_GOTPCREL64: UInt = 28u       // 64-bit PC relative offset to GOT entry
const val R_X86_64_GOTPC64: UInt = 29u          // 64-bit PC relative offset to GOT
const val R_X86_64_GOTPLT64: UInt = 30u         // like GOT64, says PLT entry needed
const val R_X86_64_PLTOFF64: UInt = 31u         // 64-bit GOT relative offset to PLT entry
const val R_X86_64_SIZE32: UInt = 32u           // Size of symbol plus 32-bit addend
const val R_X86_64_SIZE64: UInt = 33u           // Size of symbol plus 64-bit addend
const val R_X86_64_GOTPC32_TLSDESC: UInt = 34u  // GOT offset for TLS descriptor.
const val R_X86_64_TLSDESC_CALL: UInt = 35u     // Marker for call through TLS descriptor.
const val R_X86_64_TLSDESC: UInt = 36u          // TLS descriptor.
const val R_X86_64_IRELATIVE: UInt = 37u        // Adjust indirectly by program base
const val R_X86_64_RELATIVE64: UInt = 38u       // 64-bit adjust by program base
// 39 Reserved was R_X86_64_PC32_BND
// 40 Reserved was R_X86_64_PLT32_BND
const val R_X86_64_GOTPCRELX: UInt = 41u        // Load from 32 bit signed pc relative offset to GOT entry without REX prefix, relaxable.
const val R_X86_64_REX_GOTPCRELX: UInt = 42u    // Load from 32 bit signed pc relative offset to GOT entry with REX prefix, relaxable.
const val R_X86_64_NUM: UInt = 43u



/* ABI information.  The descriptor consists of words:
   word 0: OS descriptor
   word 1: major version of the ABI
   word 2: minor version of the ABI
   word 3: subminor version of the ABI
*/
const val NT_GNU_ABI_TAG = 1u

/* Synthetic hwcap information.  The descriptor begins with two words:
   word 0: number of entries
   word 1: bitmask of enabled entries
   Then follow variable-length entries, one byte followed by a
   '\0'-terminated hwcap name string.  The byte gives the bit
   number to test if enabled, (1U << bit) & bitmask.  */
const val NT_GNU_HWCAP = 2u

/* Build ID bits as generated by ld --build-id.
   The descriptor consists of any nonzero number of bytes.  */
const val NT_GNU_BUILD_ID = 3u

/* Version note generated by GNU gold containing a version string.  */
const val NT_GNU_GOLD_VERSION = 4u

/* Program property.  */
const val NT_GNU_PROPERTY_TYPE_0 = 5u

/* Contains a version string.  */
const val NT_VERSION = 1u








