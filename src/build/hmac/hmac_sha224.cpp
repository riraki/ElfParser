#include "hmac_sha224.h"

HMAC_SHA224::HMAC_SHA224(const std::string& key)
        : finish(false) {
    std::array<uint8_t, 64> h_key;
    if (key.size() > 64) {
        SHA224 sha224;
        sha224.update(key);
        std::array<uint8_t, 28> digest = sha224.digest();
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

    sha224_out.update(std::string(k_opad.begin(), k_opad.end()));
    sha224_in.update(std::string(k_ipad.begin(), k_ipad.end()));
}

void HMAC_SHA224::update(const std::string& data) {
    sha224_in.update(data);
}

std::string HMAC_SHA224::hexdigest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha224_out.hexdigest();
}

std::array<uint8_t, 28> HMAC_SHA224::digest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha224_out.digest();
}

void HMAC_SHA224::final() {
    std::array<uint8_t, 28> result_in = sha224_in.digest();
    sha224_out.update(std::string(result_in.begin(), result_in.end()));
}
