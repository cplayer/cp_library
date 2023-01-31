---
data:
  _extendedDependsOn: []
  _extendedRequiredBy: []
  _extendedVerifiedWith:
  - icon: ':heavy_check_mark:'
    path: test/math/mod-pow.test.cpp
    title: "\u6570\u5B66/$i^k \\bmod m$ ($0 \\leq i \\leq n$)"
  _isVerificationFailed: false
  _pathExtension: hpp
  _verificationStatusIcon: ':heavy_check_mark:'
  attributes:
    links: []
  bundledCode: "#line 1 \"include/cplayer/math/mod-pow.hpp\"\n\n\n\nnamespace cplayer\n\
    {\n    long long mod_pow(long long x, long long n, const int m)\n    {\n     \
    \   if ((x %= m) < 0)\n            x += m;\n        long long res = 1;\n     \
    \   for (; n > 0; n >>= 1)\n        {\n            if (n & 1)\n              \
    \  res = (res * x) % m;\n            x = (x * x) % m;\n        }\n        return\
    \ res;\n    }\n} // namespace cplayer\n\n\n"
  code: "#ifndef CPLAYER_MOD_POW_H\n#define CPLAYER_MOD_POW_H\n\nnamespace cplayer\n\
    {\n    long long mod_pow(long long x, long long n, const int m)\n    {\n     \
    \   if ((x %= m) < 0)\n            x += m;\n        long long res = 1;\n     \
    \   for (; n > 0; n >>= 1)\n        {\n            if (n & 1)\n              \
    \  res = (res * x) % m;\n            x = (x * x) % m;\n        }\n        return\
    \ res;\n    }\n} // namespace cplayer\n\n#endif // CPLAYER_MOD_POW_H"
  dependsOn: []
  isVerificationFile: false
  path: include/cplayer/math/mod-pow.hpp
  requiredBy: []
  timestamp: '2023-01-31 09:30:21+00:00'
  verificationStatus: LIBRARY_ALL_AC
  verifiedWith:
  - test/math/mod-pow.test.cpp
documentation_of: include/cplayer/math/mod-pow.hpp
layout: document
title: "\u4E8C\u5206\u5FEB\u901F\u5E42"
---

## Description

二分快速幂

## 时间复杂度
$O(\log{N})$

## 使用方法

|名称|返回值|
|:--|:--|
|`long long mod_pow(long long x, long long n, const int m);`|$x^n \bmod{m}$|


## 大体逻辑
核心思想是每一步都把指数分成两半，对应的底数做平方运算。

举例：
求$a^{11}$的结果。

$11 = 2^0 + 2^1 + 2^3$

那么，可以得出$a^{11} = a^{2^0} \cdot a^{2^1} \cdot a^{2^3}$

## 代码讲解：
每次通过`n & 1`取出`n`的最末位，`x`为对应的底数，如果指数最末位为1，则`res`乘上对应的底数并取余。
