---
data:
  _extendedDependsOn:
  - icon: ':heavy_check_mark:'
    path: include/cplayer/graph/SAT-Kosaraju.hpp
    title: 2SAT-Kosaraju
  - icon: ':heavy_check_mark:'
    path: include/cplayer/graph/SCC-Kosaraju.hpp
    title: "\u5F3A\u8FDE\u901A\u5206\u91CF-Kosaraju"
  _extendedRequiredBy: []
  _extendedVerifiedWith: []
  _isVerificationFailed: false
  _pathExtension: cpp
  _verificationStatusIcon: ':heavy_check_mark:'
  attributes:
    '*NOT_SPECIAL_COMMENTS*': ''
    PROBLEM: https://judge.yosupo.jp/problem/two_sat
    document_title: SAT-Kosaraju
    links:
    - https://judge.yosupo.jp/problem/two_sat
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
    )\nonlinejudge_verify.languages.cplusplus_bundle.BundleErrorAt: cplayer/graph/SAT-Kosaraju.hpp:\
    \ line -1: no such header\n"
  code: "/**\n * @brief SAT-Kosaraju\n */\n\n#define PROBLEM \"https://judge.yosupo.jp/problem/two_sat\"\
    \n\n#include <string>\n#include <iostream>\n\n#include \"cplayer/graph/SAT-Kosaraju.hpp\"\
    \n\nint main()\n{\n    std::string p, cnf;\n    int n, m;\n    std::cin >> p >>\
    \ cnf >> n >> m;\n    cplayer::TwoSATKosaraju twosat(n);\n    while (m--)\n  \
    \  {\n        int a, b, zero;\n        std::cin >> a >> b >> zero;\n        //\
    \ \u5C06a\u548Cb\u8F6C\u6362\u62100\u5F00\u5934\u7684\u7D22\u5F15\n        if\
    \ (a < 0)\n        {\n            a = twosat.negate(-a - 1);\n        }\n    \
    \    else\n        {\n            --a;\n        }\n        if (b < 0)\n      \
    \  {\n            b = twosat.negate(-b - 1);\n        }\n        else\n      \
    \  {\n            --b;\n        }\n        twosat.add_or(a, b);\n    }\n    const\
    \ std::vector<int> ans = twosat.build();\n    std::cout << \"s \";\n    if (ans.empty())\n\
    \    {\n        std::cout << \"UNSATISFIABLE\\n\";\n        return 0;\n    }\n\
    \    std::cout << \"SATISFIABLE\\nv \";\n    for (int i = 0; i < n; ++i)\n   \
    \ {\n        std::cout << (i + 1) * (ans[i] == 1 ? 1 : -1) << ' ';\n    }\n  \
    \  std::cout << \"0\\n\";\n    return 0;\n}"
  dependsOn:
  - include/cplayer/graph/SAT-Kosaraju.hpp
  - include/cplayer/graph/SCC-Kosaraju.hpp
  isVerificationFile: true
  path: test/graph/SAT-Kosaraju.test.cpp
  requiredBy: []
  timestamp: '2023-02-01 06:19:55+00:00'
  verificationStatus: TEST_ACCEPTED
  verifiedWith: []
documentation_of: test/graph/SAT-Kosaraju.test.cpp
layout: document
redirect_from:
- /verify/test/graph/SAT-Kosaraju.test.cpp
- /verify/test/graph/SAT-Kosaraju.test.cpp.html
title: SAT-Kosaraju
---
