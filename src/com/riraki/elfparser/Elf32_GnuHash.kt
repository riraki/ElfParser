package com.riraki.elfparser

data class Elf32_GnuHash(
    val nbuckets: UInt,
    val symoffset: UInt,
    val bloomSize: UInt,
    val bloomShift: UInt,
    val bloomFilter: UIntArray,
    val buckets: UIntArray,
    val chains: UIntArray,
) {
    private fun mightContain(hash: UInt): Boolean {
        val bloomIndex = ((hash.toULong() / 32uL) % bloomSize.toULong()).toInt()
        val bit1 = (hash % 32u).toInt()
        val bit2 = ((hash shr bloomShift.toInt()) % 32u).toInt()
        val mask = (1u shl bit1) or (1u shl bit2)
        val word = bloomFilter[bloomIndex]
        return (word and mask) == mask
    }

    fun lookupSymbol(name: String, symbols: List<Elf32_Sym>): Elf32_Sym? {
        if (symbols.isEmpty()) {
            return null
        }

        val hash = gnuHash(name)

        // Bloom Filter 预过滤
        if (!mightContain(hash)) {
            return null
        }

        // 根据哈希值定位 bucket
        val bucketIndex = (hash % nbuckets).toInt()
        val firstSymbolIndex = buckets[bucketIndex]

        // 空 bucket 用 0 表示
        if (firstSymbolIndex == 0u) {
            return null
        }

        // GNU Hash 不覆盖 symoffset 之前的符号
        if (firstSymbolIndex < symoffset) {
            return null
        }

        var symbolIndex = firstSymbolIndex.toInt()
        while (true) {
            val chainIndex = symbolIndex - symoffset.toInt()
            val chainHash = chains[chainIndex]

            // chain 的最低位用于标记链终止
            // 比较时忽略最低位
            if ((chainHash or 1u) == (hash or 1u)) {
                val symbol = symbols[symbolIndex]
                // 哈希值相同仍可能发生碰撞，需要比较真实名称
                if (symbol.getName() == name) {
                    return symbol
                }
            }

            // chainHash 最低位为 1，表示当前链结束
            if ((chainHash and 1u) != 0u) {
                return null
            }

            symbolIndex++
        }
    }

    companion object {
        fun gnuHash(name: String): UInt {
            var hash = 5381u

            for (byte in name.toByteArray(Charsets.UTF_8)) {
                val charValue = (byte.toInt() and 0xFF).toUInt()
                hash = hash * 33u + charValue
            }

            return hash
        }
    }
}
