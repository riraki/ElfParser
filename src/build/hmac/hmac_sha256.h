#ifndef HMAC_SHA256_H
#define HMAC_SHA256_H

#include "sha256.h"

class HMAC_SHA256 {
public:
    HMAC_SHA256(const string& key);

public:
    void update(const string& data);
    string hexdigest();
    array<uint8_t, 32> digest();

private:
    void final();

private:
    static const uint8_t ipad = 0x36;
    static const uint8_t opad = 0x5c;

private:
    array<uint8_t, 64> k_ipad;
    array<uint8_t, 64> k_opad;

    SHA256 sha256_in;
    SHA256 sha256_out;

    bool finish;
};

#endif // HMAC_SHA256_H
