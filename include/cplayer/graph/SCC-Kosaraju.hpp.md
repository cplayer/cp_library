---
data:
  _extendedDependsOn: []
  _extendedRequiredBy:
  - icon: ':heavy_check_mark:'
    path: include/cplayer/graph/SAT-Kosaraju.hpp
    title: 2SAT-Kosaraju
  _extendedVerifiedWith:
  - icon: ':heavy_check_mark:'
    path: test/graph/SAT-Kosaraju.test.cpp
    title: SAT-Kosaraju
  _isVerificationFailed: false
  _pathExtension: hpp
  _verificationStatusIcon: ':heavy_check_mark:'
  attributes:
    links: []
  bundledCode: "#line 1 \"include/cplayer/graph/SCC-Kosaraju.hpp\"\n\n\n\n#include\
    \ <vector>\n#include <algorithm>\n\nnamespace cplayer\n{\n    class Kosaraju_Light\n\
    \    {\n    public:\n        explicit Kosaraju_Light(const int n) : sccK_n(n),\
    \ graph(n), rgraph(n),\n                                               isVisited(n),\
    \ ids(n)\n        {\n            order.reserve(n);\n        }\n\n        inline\
    \ void add_edge (int st, int ed) {\n            graph[st].emplace_back(ed);\n\
    \        }\n\n        inline void add_edge_rev (int st, int ed) {\n          \
    \  rgraph[st].emplace_back(ed);\n        }\n\n        void construct_scc()\n \
    \       {\n            std::fill(isVisited.begin(), isVisited.end(), false);\n\
    \            std::fill(ids.begin(), ids.end(), -1);\n            order.clear();\n\
    \            for (int i = 0; i < sccK_n; ++i)\n            {\n               \
    \ if (!isVisited[i])\n                    dfs(i);\n            }\n           \
    \ for (int i = sccK_n - 1, id = 0; i >= 0; --i)\n            {\n             \
    \   if (ids[order[i]] == -1)\n                    rdfs(order[i], id++);\n    \
    \        }\n        }\n\n    protected:\n        const int sccK_n;\n        std::vector<std::vector<int>>\
    \ graph, rgraph;\n        std::vector<int> isVisited;\n        std::vector<int>\
    \ ids, order;\n\n        void dfs(const int ver)\n        {\n            isVisited[ver]\
    \ = 1;\n            for (const int dst : graph[ver])\n            {\n        \
    \        if (isVisited[dst] == 0)\n                    dfs(dst);\n           \
    \ }\n            order.emplace_back(ver);\n        }\n\n        void rdfs(const\
    \ int ver, const int cur_id)\n        {\n            ids[ver] = cur_id;\n    \
    \        for (const int dst : rgraph[ver])\n            {\n                if\
    \ (ids[dst] == -1)\n                    rdfs(dst, cur_id);\n            }\n  \
    \      }\n    };\n} // namespace cplayer\n\n\n"
  code: "#ifndef CPLAYER_SCC_KOSARAJU_H\n#define CPLAYER_SCC_KOSARAJU_H\n\n#include\
    \ <vector>\n#include <algorithm>\n\nnamespace cplayer\n{\n    class Kosaraju_Light\n\
    \    {\n    public:\n        explicit Kosaraju_Light(const int n) : sccK_n(n),\
    \ graph(n), rgraph(n),\n                                               isVisited(n),\
    \ ids(n)\n        {\n            order.reserve(n);\n        }\n\n        inline\
    \ void add_edge (int st, int ed) {\n            graph[st].emplace_back(ed);\n\
    \        }\n\n        inline void add_edge_rev (int st, int ed) {\n          \
    \  rgraph[st].emplace_back(ed);\n        }\n\n        void construct_scc()\n \
    \       {\n            std::fill(isVisited.begin(), isVisited.end(), false);\n\
    \            std::fill(ids.begin(), ids.end(), -1);\n            order.clear();\n\
    \            for (int i = 0; i < sccK_n; ++i)\n            {\n               \
    \ if (!isVisited[i])\n                    dfs(i);\n            }\n           \
    \ for (int i = sccK_n - 1, id = 0; i >= 0; --i)\n            {\n             \
    \   if (ids[order[i]] == -1)\n                    rdfs(order[i], id++);\n    \
    \        }\n        }\n\n    protected:\n        const int sccK_n;\n        std::vector<std::vector<int>>\
    \ graph, rgraph;\n        std::vector<int> isVisited;\n        std::vector<int>\
    \ ids, order;\n\n        void dfs(const int ver)\n        {\n            isVisited[ver]\
    \ = 1;\n            for (const int dst : graph[ver])\n            {\n        \
    \        if (isVisited[dst] == 0)\n                    dfs(dst);\n           \
    \ }\n            order.emplace_back(ver);\n        }\n\n        void rdfs(const\
    \ int ver, const int cur_id)\n        {\n            ids[ver] = cur_id;\n    \
    \        for (const int dst : rgraph[ver])\n            {\n                if\
    \ (ids[dst] == -1)\n                    rdfs(dst, cur_id);\n            }\n  \
    \      }\n    };\n} // namespace cplayer\n\n#endif // CPLAYER_SCC_KOSARAJU_H"
  dependsOn: []
  isVerificationFile: false
  path: include/cplayer/graph/SCC-Kosaraju.hpp
  requiredBy:
  - include/cplayer/graph/SAT-Kosaraju.hpp
  timestamp: '2023-02-01 06:19:55+00:00'
  verificationStatus: LIBRARY_ALL_AC
  verifiedWith:
  - test/graph/SAT-Kosaraju.test.cpp
documentation_of: include/cplayer/graph/SCC-Kosaraju.hpp
layout: document
title: "\u5F3A\u8FDE\u901A\u5206\u91CF-Kosaraju"
---

## Description

TWO-PASS算法（Kosaraju算法）

## 时间复杂度
$O(|V|+|E|)$（邻接矩阵）

## 方法

|方法名称|功能|
|---|---|
|explicit Kosaraju_Light(const int n)|构造n个顶点的图|
|inline void add_edge (int st, int ed)|在正向图中从st往ed连一条边|
|inline void add_edge_rev (int st, int ed)|在转置图中从st往ed连一条边|
|void construct_scc()|构造强连通分量，分量编号存放在`ids`数组中|

## 步骤

1. 深度优先遍历$G$，算出每个结点$u$的结束时间$f[u]$，起点如何选择无所谓。
2. 深度优先遍历$G$的转置图$G^T$，选择遍历的起点时，按照结点的结束时间从大到小进行。遍历的过程中，一边遍历，一边给结点做分类标记，每找到一个新的起点，分类标记值就加1。
3. 第2步中产生的标记值相同的结点构成深度优先森林中的一棵树，也即一个强连通分量。

## 参考文献

- [wikipedia](https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm)
- [Topcoder上的讲解](https://www.topcoder.com/thrive/articles/kosarajus-algorithm-for-strongly-connected-components)