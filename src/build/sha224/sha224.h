#ifndef SHA224_H
#define SHA224_H

#include <cstdint>
#include <array>
#include <string>

// SHA224 只是 SHA256 的变体，只是修改了那 8 个链接值的初始值，然后在结果处删除了 h7，并且返回长度是 28 字节而已
// 其余内容完全一样
class SHA224 {
public:
	SHA224();

public:
	// 流式输入（类似MD5/SHA1 update）
	void update(const uint8_t* data, size_t len);

	void update(const std::string& data);

	// digest 输出
	std::string hexdigest();

	std::array<uint8_t, 28> digest();
private:
	// final padding
	void final();

	// 核心处理函数
	void process_block(const uint8_t* block);

private:
	// ROTR
	static uint32_t rotr(uint32_t x, int n);
	static uint32_t Ch(uint32_t x, uint32_t y, uint32_t z);
	static uint32_t Maj(uint32_t x, uint32_t y, uint32_t z);
	static uint32_t Sigma0(uint32_t x);
	static uint32_t Sigma1(uint32_t x);
	static uint32_t sigma0(uint32_t x);
	static uint32_t sigma1(uint32_t x);

	static std::string hex_digit(uint32_t x);

private:
	uint32_t H[8];
	uint8_t buffer[64];
	size_t buffer_size;
	uint64_t bit_len;
	bool finished = false;

private:
	// 常量K（必须记住特征）
	static const uint32_t K[64];
};

#endif // SHA224_H
