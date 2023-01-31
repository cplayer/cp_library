---
data:
  _extendedDependsOn:
  - icon: ':heavy_check_mark:'
    path: include/cplayer/math/mod-pow.hpp
    title: "\u4E8C\u5206\u5FEB\u901F\u5E42"
  _extendedRequiredBy: []
  _extendedVerifiedWith: []
  _isVerificationFailed: false
  _pathExtension: cpp
  _verificationStatusIcon: ':heavy_check_mark:'
  attributes:
    '*NOT_SPECIAL_COMMENTS*': ''
    PROBLEM: http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=NTL_1_B
    document_title: "\u6570\u5B66/$i^k \\bmod m$ ($0 \\leq i \\leq n$)"
    links:
    - http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=NTL_1_B
  bundledCode: "Traceback (most recent call last):\n  File \"/opt/hostedtoolcache/Python/3.11.1/x64/lib/python3.11/site-packages/onlinejudge_verify/documentation/build.py\"\
    , line 71, in _render_source_code_stat\n    bundled_code = language.bundle(stat.path,\
    \ basedir=basedir, options={'include_paths': [basedir]}).decode()\n          \
    \         ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n\
    \  File \"/opt/hostedtoolcache/Python/3.11.1/x64/lib/python3.11/site-packages/onlinejudge_verify/languages/cplusplus.py\"\
    , line 187, in bundle\n    bundler.update(path)\n  File \"/opt/hostedtoolcache/Python/3.11.1/x64/lib/python3.11/site-packages/onlinejudge_verify/languages/cplusplus_bundle.py\"\
    , line 401, in update\n    self.update(self._resolve(pathlib.Path(included), included_from=path))\n\
    \                ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^\n \
    \ File \"/opt/hostedtoolcache/Python/3.11.1/x64/lib/python3.11/site-packages/onlinejudge_verify/languages/cplusplus_bundle.py\"\
    , line 260, in _resolve\n    raise BundleErrorAt(path, -1, \"no such header\"\
    )\nonlinejudge_verify.languages.cplusplus_bundle.BundleErrorAt: cplayer/math/mod-pow.hpp:\
    \ line -1: no such header\n"
  code: "/*\n * @brief \u6570\u5B66/$i^k \\bmod m$ ($0 \\leq i \\leq n$)\n */\n\n\
    #define PROBLEM \"http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=NTL_1_B\"\
    \n\n#include <iostream>\n\n#include \"cplayer/math/mod-pow.hpp\"\n\nint main ()\
    \ {\n    int m, n;\n    std::cin >> m >> n;\n    std::cout << cplayer::mod_pow(m,\
    \ n, 1000000007) << '\\n';\n    return 0;\n}"
  dependsOn:
  - include/cplayer/math/mod-pow.hpp
  isVerificationFile: true
  path: test/math/mod-pow.test.cpp
  requiredBy: []
  timestamp: '2023-01-31 09:30:21+00:00'
  verificationStatus: TEST_ACCEPTED
  verifiedWith: []
documentation_of: test/math/mod-pow.test.cpp
layout: document
redirect_from:
- /verify/test/math/mod-pow.test.cpp
- /verify/test/math/mod-pow.test.cpp.html
title: "\u6570\u5B66/$i^k \\bmod m$ ($0 \\leq i \\leq n$)"
---
