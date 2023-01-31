---
data:
  _extendedDependsOn: []
  _extendedRequiredBy: []
  _extendedVerifiedWith: []
  _isVerificationFailed: false
  _pathExtension: cpp
  _verificationStatusIcon: ':heavy_check_mark:'
  attributes:
    '*NOT_SPECIAL_COMMENTS*': ''
    PROBLEM: https://judge.yosupo.jp/problem/many_aplusb
    links:
    - https://judge.yosupo.jp/problem/many_aplusb
  bundledCode: "#line 1 \"test/example.test.cpp\"\n#define PROBLEM \"https://judge.yosupo.jp/problem/many_aplusb\"\
    \n\n#include <iostream>\n\nusing namespace std;\n\nint main(int argc, char const\
    \ *argv[])\n{\n    std::ios::sync_with_stdio(false);\n    std::cin.tie(nullptr);\n\
    \    int t;\n    cin >> t;\n    while (t--) {\n        long long a, b;\n     \
    \   cin >> a >> b;\n        cout << a + b << endl;\n    }\n    return 0;\n}\n"
  code: "#define PROBLEM \"https://judge.yosupo.jp/problem/many_aplusb\"\n\n#include\
    \ <iostream>\n\nusing namespace std;\n\nint main(int argc, char const *argv[])\n\
    {\n    std::ios::sync_with_stdio(false);\n    std::cin.tie(nullptr);\n    int\
    \ t;\n    cin >> t;\n    while (t--) {\n        long long a, b;\n        cin >>\
    \ a >> b;\n        cout << a + b << endl;\n    }\n    return 0;\n}\n"
  dependsOn: []
  isVerificationFile: true
  path: test/example.test.cpp
  requiredBy: []
  timestamp: '2023-01-31 05:49:01+00:00'
  verificationStatus: TEST_ACCEPTED
  verifiedWith: []
documentation_of: test/example.test.cpp
layout: document
redirect_from:
- /verify/test/example.test.cpp
- /verify/test/example.test.cpp.html
title: test/example.test.cpp
---
