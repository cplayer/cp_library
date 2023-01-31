---
title: 强连通分量-Kosaraju
documentation_of: //include/cplayer/graph/SCC-Kosaraju.hpp
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