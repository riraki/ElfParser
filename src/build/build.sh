cmake \
-S./ \
-B./ninja-arm \
 -DANDROID_ABI=armeabi-v7a \
-DANDROID_PLATFORM=android-31 \
-DANDROID_NDK=/home/ignite/Software/Android/Sdk/ndk/25.1.8937393 \
-DCMAKE_TOOLCHAIN_FILE=/home/ignite/Software/Android/Sdk/ndk/25.1.8937393/build/cmake/android.toolchain.cmake \
-G Ninja

ninja -C ./ninja-arm

cmake \
-S./ \
-B./ninja-arm64 \
 -DANDROID_ABI=arm64-v8a \
-DANDROID_PLATFORM=android-31 \
-DANDROID_NDK=/home/ignite/Software/Android/Sdk/ndk/25.1.8937393 \
-DCMAKE_TOOLCHAIN_FILE=/home/ignite/Software/Android/Sdk/ndk/25.1.8937393/build/cmake/android.toolchain.cmake \
-G Ninja

ninja -C ./ninja-arm64
