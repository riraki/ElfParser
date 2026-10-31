#ifndef HMAC_SHA512_H
#define HMAC_SHA512_H

#include <string>
#include <array>
#include <cstdint>

#include "sha512.h"

class HMAC_SHA512 {
public:
    HMAC_SHA512(const std::string& key);

public:
    void update(const std::string& data);
    std::string hexdigest();
    std::array<uint8_t, 64> digest();

private:
    void final();

private:
    static const uint8_t ipad = 0x36;
    static const uint8_t opad = 0x5c;

private:
    std::array<uint8_t, 128> k_ipad;
    std::array<uint8_t, 128> k_opad;

    SHA512 sha512_in;
    SHA512 sha512_out;

    bool finish;
};

#endif // HMAC_SHA512_H
