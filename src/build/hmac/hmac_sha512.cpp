#include "hmac_sha512.h"

HMAC_SHA512::HMAC_SHA512(const std::string& key)
       : finish(false) {
    std::array<uint8_t, 128> h_key;
    if (key.size() > 128) {
        SHA512 sha512;
        sha512.update(key);
        std::array<uint8_t, 64> digest = sha512.digest();
        std::copy(digest.begin(), digest.end(), h_key.begin());
        std::fill(h_key.begin() + digest.size(), h_key.end(), 0x00);
    }
    else {
        for (size_t i = 0; i < key.size(); ++i) {
            h_key[i] = static_cast<uint8_t>(key[i]);
        }
        std::fill(h_key.begin() + key.size(), h_key.end(), 0x00);
    }

    for (size_t i = 0; i < h_key.size(); ++i) {
        k_ipad[i] = h_key[i] ^ ipad;
        k_opad[i] = h_key[i] ^ opad;
    }

    sha512_out.update(std::string(k_opad.begin(), k_opad.end()));
    sha512_in.update(std::string(k_ipad.begin(), k_ipad.end()));
}

void HMAC_SHA512::update(const std::string& data) {
    sha512_in.update(data);
}

std::string HMAC_SHA512::hexdigest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha512_out.hexdigest();
}

std::array<uint8_t, 64> HMAC_SHA512::digest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha512_out.digest();
}

void HMAC_SHA512::final() {
    std::array<uint8_t, 64> result_in = sha512_in.digest();
    sha512_out.update(std::string(result_in.begin(), result_in.end()));
}
