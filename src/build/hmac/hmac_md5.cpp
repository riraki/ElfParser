#include "hmac_md5.h"

HMAC_MD5::HMAC_MD5(const string& key)
    : finish(false) {
    array<uint8_t, 64> h_key;
    if (key.size() > 64) {
        MD5 md5;
        md5.update(key);
        array<uint8_t, 16> digest = md5.digest();
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

    md5_out.update(string(k_opad.begin(), k_opad.end()));
    md5_in.update(string(k_ipad.begin(), k_ipad.end()));
}

void HMAC_MD5::update(const string& data) {
    md5_in.update(data);
}

string HMAC_MD5::hexdigest() {
    if (!finish) {
        finish = true;
        final();
    }

    return md5_out.hexdigest();
}

array<uint8_t, 16> HMAC_MD5::digest() {
    if (!finish) {
        finish = true;
        final();
    }

    return md5_out.digest();
}

void HMAC_MD5::final() {
    array<uint8_t, 16> result_in = md5_in.digest();
    md5_out.update(string(result_in.begin(), result_in.end()));
}
