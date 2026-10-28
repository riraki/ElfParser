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





