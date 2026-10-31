#include "hmac_sha1.h"

HMAC_SHA1::HMAC_SHA1(const string& key)
    : finish(false) {
    array<uint8_t, 64> h_key;
    if (key.size() > 64) {
        SHA1 sha1;
        sha1.update(key);
        array<uint8_t, 20> digest = sha1.digest();
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

    sha1_out.update(string(k_opad.begin(), k_opad.end()));
    sha1_in.update(string(k_ipad.begin(), k_ipad.end()));
}

void HMAC_SHA1::update(const string& data) {
    sha1_in.update(data);
}

string HMAC_SHA1::hexdigest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha1_out.hexdigest();
}

array<uint8_t, 20> HMAC_SHA1::digest() {
    if (!finish) {
        finish = true;
        final();
    }

    return sha1_out.digest();
}

void HMAC_SHA1::final() {
    array<uint8_t, 20> result_in = sha1_in.digest();
    sha1_out.update(string(result_in.begin(), result_in.end()));
}
