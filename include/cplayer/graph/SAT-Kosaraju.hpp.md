---
data:
  _extendedDependsOn:
  - icon: ':heavy_check_mark:'
    path: include/cplayer/graph/SCC-Kosaraju.hpp
    title: "\u5F3A\u8FDE\u901A\u5206\u91CF-Kosaraju"
  _extendedRequiredBy: []
  _extendedVerifiedWith:
  - icon: ':heavy_check_mark:'
    path: test/graph/SAT-Kosaraju.test.cpp
    title: SAT-Kosaraju
  _isVerificationFailed: false
  _pathExtension: hpp
  _verificationStatusIcon: ':heavy_check_mark:'
  attributes:
    links: []
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
    )\nonlinejudge_verify.languages.cplusplus_bundle.BundleErrorAt: cplayer/graph/SCC-Kosaraju.hpp:\
    \ line -1: no such header\n"
  code: "#ifndef CPLAYER_SAT_KOSARAJU_H\n#define CPLAYER_SAT_KOSARAJU_H\n\n#include\
    \ \"cplayer/graph/SCC-Kosaraju.hpp\"\n\nnamespace cplayer\n{\n    class TwoSATKosaraju\
    \ : public Kosaraju_Light\n    {\n    public:\n        explicit TwoSATKosaraju(const\
    \ int n) : Kosaraju_Light(n << 1), satK_n(n) {}\n\n        std::vector<int> build()\n\
    \        {\n            construct_scc();\n            std::vector<int> res(satK_n);\n\
    \            for (int i = 0; i < satK_n; ++i)\n            {\n               \
    \ if (ids[i] == ids[negate(i)])\n                    return {};\n            \
    \    res[i] = ids[negate(i)] < ids[i] ? 1 : -1;\n            }\n            return\
    \ res;\n        }\n\n        int negate(const int x) const { return (satK_n +\
    \ x) % (satK_n << 1); }\n\n        void add_or(const int x, const int y)\n   \
    \     {\n            add_edge(negate(x), y);\n            add_edge(negate(y),\
    \ x);\n            add_edge_rev(y, negate(x));\n            add_edge_rev(x, negate(y));\n\
    \        }\n\n        void add_if(const int x, const int y)\n        {\n     \
    \       add_or(negate(x), y);\n        }\n\n        void add_nand(const int x,\
    \ const int y)\n        {\n            add_or(negate(x), negate(y));\n       \
    \ }\n\n        void set_true(const int x) { add_or(x, x); }\n        void set_false(const\
    \ int x) { set_true(negate(x)); }\n\n    protected:\n        const int satK_n;\n\
    \    };\n} // namespace cplayer\n\n#endif // CPLAYER_SAT_KOSARAJU_H"
  dependsOn:
  - include/cplayer/graph/SCC-Kosaraju.hpp
  isVerificationFile: false
  path: include/cplayer/graph/SAT-Kosaraju.hpp
  requiredBy: []
  timestamp: '2023-02-01 06:19:55+00:00'
  verificationStatus: LIBRARY_ALL_AC
  verifiedWith:
  - test/graph/SAT-Kosaraju.test.cpp
documentation_of: include/cplayer/graph/SAT-Kosaraju.hpp
layout: document
title: 2SAT-Kosaraju
---

基于Kosaraju算法的2-SAT判定

## 时间复杂度

$O(\|V\|+\|E\|)$

## 方法

|方法名称|功能|
|---|---|
|explicit TwoSATKosaraju (const int n)|构造n个条件的2-SAT问题|
|std::vector<int> build()|寻找一个2-SAT问题的解，如果有矛盾返回空序列，选中对应节点返回1，否则返回-1|
|int negate(const int x)|$\lnot x$的顶点号|
|void add_or(const int x, const int y)|追加$x \lor y$|
|void add_if(const int x, const int y)|追加$x \Leftarrow y$|
|void add_nand(const int x, const int y)|追加$\lnot(x \land y)$|
|void set_true(const int x)|设置$x$为真|
|void set_false(const int x)|设置$x$为假|

## 描述

2-SAT问题：给$n$个变量$a_i$，每个变量只能取$0/1$两个可能值。同时给出若干条件，形式诸如$(\lnot) \ a_i \ opt \ (\lnot) a_j = 0/1$，其中$opt$表示逻辑运算符（`and,or,xor`）。

2-SAT问题求解就是求出满足题意的一组解。