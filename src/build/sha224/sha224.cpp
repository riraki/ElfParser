#include <sstream>
#include <iomanip>

#include "sha224.h"

SHA224::SHA224() {
    // 初始状态
    H[0] = 0xc1059ed8;
    H[1] = 0x367cd507;
    H[2] = 0x3070dd17;
    H[3] = 0xf70e5939;
    H[4] = 0xffc00b31;
    H[5] = 0x68581511;
    H[6] = 0x64f98fa7;
    H[7] = 0xbefa4fa4;

    bit_len = 0;
    buffer_size = 0;
}

// 流式输入（类似MD5/SHA1 update）
void SHA224::update(const uint8_t* data, size_t len) {
    bit_len += len * 8;

    size_t i = 0;

    // 填满 buffer
    if (buffer_size > 0) {
        while (len > 0 && buffer_size < 64) {
            buffer[buffer_size++] = data[i++];
            len--;
        }

        if (buffer_size == 64) {
            process_block(buffer);
            buffer_size = 0;
        }
    }

    // 直接处理完整块
    while (len >= 64) {
        process_block(data + i);
        i += 64;
        len -= 64;
    }

    // 剩余数据
    while (len > 0) {
        buffer[buffer_size++] = data[i++];
        len--;
    }
}

void SHA224::update(const std::string& data) {
    update(reinterpret_cast<const uint8_t*>(data.data()), data.size());
}

// digest 输出
std::string SHA224::hexdigest() {
    if (!finished) {
        finished = true;
        final();
    }
    std::string result;
    for (int i = 0; i < 7; ++i) {
        result += hex_digit(H[i]);
    }
    return result;
}

std::array<uint8_t, 28> SHA224::digest() {
    if (!finished) {
        finished = true;
        final();
    }

    std::array<uint8_t, 28> result;
    // SHA256 是大端，要把高位存在数组的最前面
    // 大端是高位存低地址，低位存高地址
    for (int i = 0; i < 7; ++i) {
        for (int j = 0; j < 4; ++j) {
            result[i * 4 + j] = ((H[i] >> (24 - j * 8)) & 0xff);
        }
    }

    return result;
}

// final padding
void SHA224::final() {
    buffer[buffer_size++] = 0x80;

    // padding到56字节
    while (buffer_size != 56) {
        if (buffer_size == 64) {
            process_block(buffer);
            buffer_size = 0;
        }
        buffer[buffer_size++] = 0x00;
    }

    // SHA224 是大端，要把高位存在数组的最前面
    // 大端是高位存低地址，低位存高地址
    for (int i = 7; i >= 0; --i) {
        buffer[buffer_size++] = ((bit_len >> (i * 8)) & 0xff);
    }

    process_block(buffer);
}

// 核心处理函数
void SHA224::process_block(const uint8_t* block) {
    uint32_t W[64];

    // 先将 64 个 uint8_t 转化成 16 个 uint32_t
    // 大端序处理，把 uint8_t 数组的前面存在 uint32_t 的高位
    // 大端是高位存低地址，低位存高地址
    // 0x01020304 => 01 02 03 04
    // 01 02 03 04 => 0x01020304
    for (int i = 0; i < 16; ++i) {
        W[i] = (block[i * 4] << 24) |
            (block[i * 4 + 1] << 16) |
            (block[i * 4 + 2] << 8) |
            block[i * 4 + 3];
    }

    // 扩展到 64
    for (int i = 16; i < 64; ++i) {
        W[i] = sigma1(W[i - 2]) + W[i - 7] + sigma0(W[i - 15]) + W[i - 16];
    }

    uint32_t a = H[0];
    uint32_t b = H[1];
    uint32_t c = H[2];
    uint32_t d = H[3];
    uint32_t e = H[4];
    uint32_t f = H[5];
    uint32_t g = H[6];
    uint32_t h = H[7];

    // 核心 64 轮
    for (int i = 0; i < 64; ++i) {
        uint32_t T1 = h + Sigma1(e) + Ch(e, f, g) + K[i] + W[i];
        uint32_t T2 = Sigma0(a) + Maj(a, b, c);

        h = g;
        g = f;
        f = e;
        e = d + T1;
        d = c;
        c = b;
        b = a;
        a = T1 + T2;
    }

    H[0] += a;
    H[1] += b;
    H[2] += c;
    H[3] += d;
    H[4] += e;
    H[5] += f;
    H[6] += g;
    H[7] += h;
}

// ROTR
uint32_t SHA224::rotr(uint32_t x, int n) {
    return (x >> n) | (x << (32 - n));
}

uint32_t SHA224::Ch(uint32_t x, uint32_t y, uint32_t z) {
    return (x & y) ^ (~x & z);
}

uint32_t SHA224::Maj(uint32_t x, uint32_t y, uint32_t z) {
    return (x & y) ^ (x & z) ^ (y & z);
}

uint32_t SHA224::Sigma0(uint32_t x) {
    return rotr(x, 2) ^ rotr(x, 13) ^ rotr(x, 22);
}

uint32_t SHA224::Sigma1(uint32_t x) {
    return rotr(x, 6) ^ rotr(x, 11) ^ rotr(x, 25);
}

uint32_t SHA224::sigma0(uint32_t x) {
    return rotr(x, 7) ^ rotr(x, 18) ^ (x >> 3);
}

uint32_t SHA224::sigma1(uint32_t x) {
    return rotr(x, 17) ^ rotr(x, 19) ^ (x >> 10);
}

std::string SHA224::hex_digit(uint32_t x) {
    std::stringstream ss;
    for (int i = 3; i >= 0; --i) {
        ss << std::hex << std::setw(2) << std::setfill('0') << ((x >> (i * 8)) & 0xff);
    }
    return ss.str();
}

const uint32_t SHA224::K[64] = {
    0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
    0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
    0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
    0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
    0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
    0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
    0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
    0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
    0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
    0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
    0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
    0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
    0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
    0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
    0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
    0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2
};
