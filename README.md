# ElfParser

一个用 Kotlin 编写的解析Elf文件的示例程序。

### 开始

首先将项目克隆到本地，然后使用 Intellij IDEA 打开项目，运行 src/com/riraka/elfparser/Main.kt 即可得出示例 Elf 文件的结果。

要运行解析自己的 Elf 文件，只需将路径更换即可。

要修改示例程序的源码，修改 src/build/ 中的 .cpp 文件即可，可以添加 .h，.c，.cpp，.hpp，.cc 等文件，将对应的文件加入到 CMake 中即可。

示例程序使用的是安卓 ndk 构建的，如果你也想使用 安卓 ndk 构建，那么修改 build.sh 替换相关工具的路径为你自己的真实路径。

如果要在 Linux/MacOS 环境构建，那么修改 build.sh 为 cmake 解析 cmake 文件，然后 make 编译 makefile 文件等方式即可。

### 结构

src/com/riraki/elfparser 中的 .kt 文件是整个程序的源代码。

src/build/ 中的文件是用来构建示例 elf 文件的。
