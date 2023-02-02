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
  bundledCode: "#line 1 \"include/cplayer/math/prime-factorize-precompute.hpp\"\n\n\
    \n\n#include <vector>\n\nnamespace cplayer\n{\n    class MultiPrimeFactorize\n\
    \    {\n    public:\n        std::vector<int> spf;\n        std::vector<int> primes;\n\
    \n        explicit MultiPrimeFactorize(const int n) : n(n)\n        {\n      \
    \      spf.resize(n + 1);\n        }\n\n        void init()\n        {\n     \
    \       std::fill(spf.begin(), spf.end(), 0);\n            spf[1] = 1;\n     \
    \       for (int i = 2; i <= n; i++) {\n                if (spf[i] == 0) {\n \
    \                   spf[i] = i;\n                    primes.push_back(i);\n  \
    \              }\n                for (int j = 0; i * primes[j] <= n; j++) {\n\
    \                    spf[i * primes[j]] = primes[j];\n                    if (primes[j]\
    \ == spf[i]) {\n                        break;\n                    }\n      \
    \          }\n            }\n        }\n\n        std::vector<int> getFactors(int\
    \ x)\n        {\n            std::vector<int> ret;\n            while (x != 1)\n\
    \            {\n                ret.push_back(spf[x]);\n                x = x\
    \ / spf[x];\n            }\n            return ret;\n        }\n\n        int\
    \ isPrime(int x)\n        {\n            if (spf[x] == x)\n                return\
    \ 1;\n            else\n                return 0;\n        }\n\n    protected:\n\
    \        const int n;\n    };\n} // namespace cplayer\n\n\n"
  code: "#ifndef CPLAYER_PRIME_FACTORIZE_PRECOMPUTE_H\n#define CPLAYER_PRIME_FACTORIZE_PRECOMPUTE_H\n\
    \n#include <vector>\n\nnamespace cplayer\n{\n    class MultiPrimeFactorize\n \
    \   {\n    public:\n        std::vector<int> spf;\n        std::vector<int> primes;\n\
    \n        explicit MultiPrimeFactorize(const int n) : n(n)\n        {\n      \
    \      spf.resize(n + 1);\n        }\n\n        void init()\n        {\n     \
    \       std::fill(spf.begin(), spf.end(), 0);\n            spf[1] = 1;\n     \
    \       for (int i = 2; i <= n; i++) {\n                if (spf[i] == 0) {\n \
    \                   spf[i] = i;\n                    primes.push_back(i);\n  \
    \              }\n                for (int j = 0; i * primes[j] <= n; j++) {\n\
    \                    spf[i * primes[j]] = primes[j];\n                    if (primes[j]\
    \ == spf[i]) {\n                        break;\n                    }\n      \
    \          }\n            }\n        }\n\n        std::vector<int> getFactors(int\
    \ x)\n        {\n            std::vector<int> ret;\n            while (x != 1)\n\
    \            {\n                ret.push_back(spf[x]);\n                x = x\
    \ / spf[x];\n            }\n            return ret;\n        }\n\n        int\
    \ isPrime(int x)\n        {\n            if (spf[x] == x)\n                return\
    \ 1;\n            else\n                return 0;\n        }\n\n    protected:\n\
    \        const int n;\n    };\n} // namespace cplayer\n\n#endif // CPLAYER_PRIME_FACTORIZE_PRECOMPUTE_H"
  dependsOn: []
  isVerificationFile: false
  path: include/cplayer/math/prime-factorize-precompute.hpp
  requiredBy: []
  timestamp: '2023-02-02 06:47:39+00:00'
  verificationStatus: LIBRARY_NO_TESTS
  verifiedWith: []
documentation_of: include/cplayer/math/prime-factorize-precompute.hpp
layout: document
title: "\u901A\u8FC7\u7B5B\u6CD5\u8FDB\u884C\u591A\u6B21\u56E0\u6570\u5206\u89E3"
---

## 描述

通过预计算（筛法），高效进行多次因数分解回答的解法。

## 时间复杂度

预处理`O(n)`，每次回答`O(logn)`。

## 参考文献

- https://cp-algorithms.com/algebra/prime-sieve-linear.html
- https://codeforces.com/blog/entry/54090

## Tips

其实如果写不出来线性的，用传统的方法也可以。

> In practice the linear sieve runs about as fast as a typical implementation of the sieve of Eratosthenes.