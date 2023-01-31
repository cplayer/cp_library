---
data:
  _extendedDependsOn: []
  _extendedRequiredBy: []
  _extendedVerifiedWith: []
  _isVerificationFailed: false
  _pathExtension: hpp
  _verificationStatusIcon: ':warning:'
  attributes:
    links: []
  bundledCode: "#line 1 \"include/cplayer/math/mod_pow.hpp\"\n\n\n\nnamespace cplayer\n\
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
  path: include/cplayer/math/mod_pow.hpp
  requiredBy: []
  timestamp: '2023-01-31 05:49:01+00:00'
  verificationStatus: LIBRARY_NO_TESTS
  verifiedWith: []
documentation_of: include/cplayer/math/mod_pow.hpp
layout: document
title: "\u4E8C\u5206\u5FEB\u901F\u5E42"
---

## Description

二分快速幂