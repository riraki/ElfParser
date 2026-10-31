#ifndef HMAC_SHA224_H
#define HMAC_SHA224_H

#include "sha224.h"

class HMAC_SHA224 {
public:
    HMAC_SHA224(const std::string& key);

public:
    void update(const std::string& data);

    std::string hexdigest();

    std::array<uint8_t, 28> digest();

private:
    void final();

private:
    static const uint8_t ipad = 0x36;
    static const uint8_t opad = 0x5c;

private:
    std::array<uint8_t, 64> k_ipad;
    std::array<uint8_t, 64> k_opad;

    SHA224 sha224_in;
    SHA224 sha224_out;

    bool finish;
};

#endif // HMAC_SHA224_H
