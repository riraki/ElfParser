#ifndef HMAC_MD5_H
#define HMAC_MD5_H

#include "md5.h"

class HMAC_MD5 {
public:
    HMAC_MD5(const string& key);

public:
    void update(const string& data);
    string hexdigest();
    array<uint8_t, 16> digest();

private:
    void final();

private:
    static const uint8_t ipad = 0x36;
    static const uint8_t opad = 0x5c;
private:
    array<uint8_t, 64> k_ipad;
    array<uint8_t, 64> k_opad;

    MD5 md5_in;
    MD5 md5_out;

    bool finish;
};

#endif // HMAC_MD5_H
