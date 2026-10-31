#ifndef SHA384_H
#define SHA384_H

#include <string>
#include <array>
#include <cstdint>

// SHA384 和 SHA512 完全一样，只是修改了那 8 个链接值的初始值，然后在结果处删掉了 h6 和 h7，并且返回长度是 48 字节而已
class SHA384 {
public:
	SHA384();

public:
	void update(const std::string& data);
	void update(const uint8_t* data, size_t len);

	std::string hexdigest();
	std::array<uint8_t, 48> digest();

private:
	void final();

	// SHA384 核心算法函数
	void process_block(const uint8_t* block);

private:
	// 64 位循环右移
	static uint64_t rotr(uint64_t x, uint64_t n);
	static uint64_t Ch(uint64_t x, uint64_t y, uint64_t z);
	static uint64_t Maj(uint64_t x, uint64_t y, uint64_t z);
	static uint64_t Sigma0(uint64_t x);
	static uint64_t Sigma1(uint64_t x);
	static uint64_t sigma0(uint64_t x);
	static uint64_t sigma1(uint64_t x);

	static std::string hex_digit(uint64_t x);

private:
	uint64_t H[8];

	uint8_t buffer[128];
	size_t buffer_size;

	uint64_t bit_len_low;
	uint64_t bit_len_high;

	bool finished;

	// SHA384 用于核心 80 轮计算的 80 个常量
	static const uint64_t K[80];
};

#endif // SHA384_H
