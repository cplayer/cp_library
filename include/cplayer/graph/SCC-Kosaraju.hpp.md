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
  bundledCode: "#line 1 \"include/cplayer/graph/SCC-Kosaraju.hpp\"\n\n\n\n#include\
    \ <vector>\n#include <algorithm>\n\nnamespace cplayer\n{\n    class TwoSat\n \
    \   {\n        explicit TwoSat(const int n) : n(n), graph(n << 1), rgraph(n <<\
    \ 1),\n                                       isVisited(n << 1), ids(n << 1)\n\
    \        {\n            order.reserve(n << 1);\n        }\n\n        int negate(const\
    \ int x) const { return (n + x) % (n << 1); }\n\n        void add_or(const int\
    \ x, const int y)\n        {\n            graph[negate(x)].emplace_back(y);\n\
    \            graph[negate(y)].emplace_back(x);\n            rgraph[y].emplace_back(negate(x));\n\
    \            rgraph[x].emplace_back(negate(y));\n        }\n\n        void add_if(const\
    \ int x, const int y)\n        {\n            add_or(negate(x), y);\n        }\n\
    \n        void add_nand(const int x, const int y)\n        {\n            add_or(negate(x),\
    \ negate(y));\n        }\n\n        void set_true(const int x) { add_or(x, x);\
    \ }\n        void set_false(const int x) { set_true(negate(x)); }\n\n        std::vector<bool>\
    \ build()\n        {\n            std::fill(isVisited.begin(), isVisited.end(),\
    \ false);\n            std::fill(ids.begin(), ids.end(), -1);\n            order.clear();\n\
    \            for (int i = 0; i < (n << 1); ++i)\n            {\n             \
    \   if (!isVisited[i])\n                    dfs(i);\n            }\n         \
    \   for (int i = (n << 1) - 1, id = 0; i >= 0; --i)\n            {\n         \
    \       if (ids[order[i]] == -1)\n                    rdfs(order[i], id++);\n\
    \            }\n            std::vector<bool> res(n);\n            for (int i\
    \ = 0; i < n; ++i)\n            {\n                if (ids[i] == ids[negate(i)])\n\
    \                    return {};\n                res[i] = ids[negate(i)] < ids[i];\n\
    \            }\n            return res;\n        }\n\n    private:\n        const\
    \ int n;\n        std::vector<std::vector<int>> graph, rgraph;\n        std::vector<bool>\
    \ isVisited;\n        std::vector<int> ids, order;\n\n        void dfs(const int\
    \ ver)\n        {\n            isVisited[ver] = true;\n            for (const\
    \ int dst : graph[ver])\n            {\n                if (!isVisited[dst])\n\
    \                    dfs(dst);\n            }\n            order.emplace_back(ver);\n\
    \        }\n\n        void rdfs(const int ver, const int cur_id)\n        {\n\
    \            ids[ver] = cur_id;\n            for (const int dst : rgraph[ver])\n\
    \            {\n                if (ids[dst] == -1)\n                    rdfs(dst,\
    \ cur_id);\n            }\n        }\n    };\n} // namespace cplayer\n\n\n"
  code: "#ifndef CPLAYER_SCC_KOSARAJU_H\n#define CPLAYER_SCC_KOSARAJU_H\n\n#include\
    \ <vector>\n#include <algorithm>\n\nnamespace cplayer\n{\n    class TwoSat\n \
    \   {\n        explicit TwoSat(const int n) : n(n), graph(n << 1), rgraph(n <<\
    \ 1),\n                                       isVisited(n << 1), ids(n << 1)\n\
    \        {\n            order.reserve(n << 1);\n        }\n\n        int negate(const\
    \ int x) const { return (n + x) % (n << 1); }\n\n        void add_or(const int\
    \ x, const int y)\n        {\n            graph[negate(x)].emplace_back(y);\n\
    \            graph[negate(y)].emplace_back(x);\n            rgraph[y].emplace_back(negate(x));\n\
    \            rgraph[x].emplace_back(negate(y));\n        }\n\n        void add_if(const\
    \ int x, const int y)\n        {\n            add_or(negate(x), y);\n        }\n\
    \n        void add_nand(const int x, const int y)\n        {\n            add_or(negate(x),\
    \ negate(y));\n        }\n\n        void set_true(const int x) { add_or(x, x);\
    \ }\n        void set_false(const int x) { set_true(negate(x)); }\n\n        std::vector<bool>\
    \ build()\n        {\n            std::fill(isVisited.begin(), isVisited.end(),\
    \ false);\n            std::fill(ids.begin(), ids.end(), -1);\n            order.clear();\n\
    \            for (int i = 0; i < (n << 1); ++i)\n            {\n             \
    \   if (!isVisited[i])\n                    dfs(i);\n            }\n         \
    \   for (int i = (n << 1) - 1, id = 0; i >= 0; --i)\n            {\n         \
    \       if (ids[order[i]] == -1)\n                    rdfs(order[i], id++);\n\
    \            }\n            std::vector<bool> res(n);\n            for (int i\
    \ = 0; i < n; ++i)\n            {\n                if (ids[i] == ids[negate(i)])\n\
    \                    return {};\n                res[i] = ids[negate(i)] < ids[i];\n\
    \            }\n            return res;\n        }\n\n    private:\n        const\
    \ int n;\n        std::vector<std::vector<int>> graph, rgraph;\n        std::vector<bool>\
    \ isVisited;\n        std::vector<int> ids, order;\n\n        void dfs(const int\
    \ ver)\n        {\n            isVisited[ver] = true;\n            for (const\
    \ int dst : graph[ver])\n            {\n                if (!isVisited[dst])\n\
    \                    dfs(dst);\n            }\n            order.emplace_back(ver);\n\
    \        }\n\n        void rdfs(const int ver, const int cur_id)\n        {\n\
    \            ids[ver] = cur_id;\n            for (const int dst : rgraph[ver])\n\
    \            {\n                if (ids[dst] == -1)\n                    rdfs(dst,\
    \ cur_id);\n            }\n        }\n    };\n} // namespace cplayer\n\n#endif\
    \ // CPLAYER_SCC_KOSARAJU_H"
  dependsOn: []
  isVerificationFile: false
  path: include/cplayer/graph/SCC-Kosaraju.hpp
  requiredBy: []
  timestamp: '2023-01-31 09:30:21+00:00'
  verificationStatus: LIBRARY_NO_TESTS
  verifiedWith: []
documentation_of: include/cplayer/graph/SCC-Kosaraju.hpp
layout: document
title: "\u5F3A\u8FDE\u901A\u5206\u91CF-Kosaraju"
---

## Description

TWO-PASS算法（Kosaraju算法）

## 步骤

1. 将原图$G$中的每条边都反向，得到$G^{rev}$;
2. 在$G^{rev}$中运行`Loop-DFS`，记录每个顶点的完成时间$f(v)$;
3. 按$f(v)$的降序，在$G$中运行`Loop-DFS`，记录每个顶点的`Leader` $l(v)$。

`Loop-DFS`伪代码:

```
全局变量 t = 0
全局变量 s = NULL
共有n个顶点，顶点序号为0 ... n-1
For i = 0 to n-1
    IF i的标记为'未探索'
        s = i;
        DFS(G, i);
EndFor
```

`DFS(G, i)`伪代码：

```
将顶点i标记为'已探索'
Leader(i) = s
For i的所有邻居j
    IF j的标记为'未探索'
        DFS(G, j);
    t++;
    f(i) = t;
EndFor
```