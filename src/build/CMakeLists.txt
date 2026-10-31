# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.22.1)

project("native-lib")

set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/../)
set(CMAKE_BUILD_TYPE "Release")
set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -s")
set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -s")

include_directories(.)
include_directories(md5)
include_directories(sha1)
include_directories(sha224)
include_directories(sha256)
include_directories(sha384)
include_directories(sha512)
include_directories(hmac)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

set(LIBRARY_NAME "${CMAKE_PROJECT_NAME}-${ANDROID_ABI}")

add_library( # Specifies the name of the library.
             ${LIBRARY_NAME}

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             native-lib.cpp
             md5/md5.cpp
             sha1/sha1.cpp
             sha224/sha224.cpp
             sha256/sha256.cpp
             sha384/sha384.cpp
             sha512/sha512.cpp
             hmac/hmac_md5.cpp
             hmac/hmac_sha1.cpp
             hmac/hmac_sha224.cpp
             hmac/hmac_sha256.cpp
             hmac/hmac_sha384.cpp
             hmac/hmac_sha512.cpp)

target_link_libraries(${LIBRARY_NAME}
        # List libraries link to the target library
        android
        log)
