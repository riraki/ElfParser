#include "hmac_sha256.h"

HMAC_SHA256::HMAC_SHA256(const string& key)
        : finish(false) {
    array<uint8_t, 64> h_key;
    if (key.size() > 64) {
        SHA256 sha256;
        sha256.update(key);
        array<uint8_t, 32> digest = sha256.digest();
        copy(digest.begin(), digest.end(), h_key.begin());
        fill(h_key.begin() + digest.size(), h_key.end(), 0x00);
    }
    else {
        for (size_t i = 0; i < key.size(); ++i) {
            h_key[i] = static_cast<uint8_t>(key[i]);
        }
        fill(h_key.begin() + key.size(), h_key.end(), 0x00);
    }

    for (size_t i = 0; i < h_key.size(); ++i) {
        k_ipad[i] = h_key[i] ^ ipad;
        k_opad[i] = h_key[i] ^ opad;
    }

    sha256_out.update(string(k_opad.begin(), k_opad.end()));
    sha256_in.update(string(k_ipad.begin(), k_ipad.end()));
}

void HMAC_SHA256::update(const string& data) {
    sha256_in.update(data);
}

string HMAC_SHA256::hexdigest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha256_out.hexdigest();
}

array<uint8_t, 32> HMAC_SHA256::digest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha256_out.digest();
}

void HMAC_SHA256::final() {
    array<uint8_t, 32> result_in = sha256_in.digest();
    sha256_out.update(string(result_in.begin(), result_in.end()));
}
