#ifndef HMAC_SHA1_H
#define HMAC_SHA1_H

#include "sha1.h"

class HMAC_SHA1 {
public:
    HMAC_SHA1(const string& key);

public:
    void update(const string& data);
    string hexdigest();
    array<uint8_t, 20> digest();

private:
    void final();

private:
    static const uint8_t ipad = 0x36;
    static const uint8_t opad = 0x5c;
private:
    array<uint8_t, 64> k_ipad;
    array<uint8_t, 64> k_opad;

    SHA1 sha1_in;
    SHA1 sha1_out;

    bool finish;
};

#endif // HMAC_SHA1_H
