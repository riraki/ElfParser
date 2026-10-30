package com.riraki.elfparser

data class Elf64_Hash(
    val nbucket: UInt,
    val nchain: UInt,
    val buckets: UIntArray,
    val chains: UIntArray
) {
    fun lookupSymbol(name: String, symbols: List<Elf64_Sym>): Elf64_Sym? {
        val bucketIndex = (elfHash(name) % nbucket).toInt()
        var symbolIndex = buckets[bucketIndex].toInt()

        while (symbolIndex != 0) {
            val symbol = symbols[symbolIndex]

            if (symbol.getName() == name) {
                return symbol
            }

            symbolIndex = chains[symbolIndex].toInt()
        }

        return null
    }

    companion object {
        /**
         * 因为 elf_hash 本身就是一个 32 位环境的函数，所以这里的 long 本身应该是 4 字节
         * unsigned long
         * elf_hash(const unsigned char *name)
         * {
         * 	unsigned long	h = 0, g;
         * 	while (*name)
         * 	{
         * 		h = (h << 4) + *name++;
         * 		if (g = h & 0xf0000000)
         * 			h ^= g >> 24;
         * 		h &= ~g;
         * 	}
         * 	return h;
         * }
         */
        fun elfHash(name: String): UInt {
            var h = 0u
            name.forEach { ch ->
                h = (h shl 4) + ch.code.toUInt()
                val g = h and 0xf0000000u
                if (g != 0u) {
                    h = h xor (g shr 24)
                }
                h = h and g.inv()
            }
            return h
        }
    }
}
