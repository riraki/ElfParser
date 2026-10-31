#ifndef HMAC_SHA384_H
#define HMAC_SHA384_H

#include <cstdint>
#include <string>
#include <array>

#include "sha384.h"

class HMAC_SHA384 {
public:
    HMAC_SHA384(const std::string& key);
public:
    void update(const std::string& data);

    std::string hexdigest();
    std::array<uint8_t, 48> digest();

private:
    void final();

private:
    static const uint8_t ipad = 0x36;
    static const uint8_t opad = 0x5c;

private:
    std::array<uint8_t, 128> k_ipad;
    std::array<uint8_t, 128> k_opad;

    SHA384 sha384_in;
    SHA384 sha384_out;

    bool finish;
};

#endif // HMAC_SHA384_H
