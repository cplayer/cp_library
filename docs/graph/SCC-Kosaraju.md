---
title: 强连通分量-Kosaraju
documentation_of: //include/cplayer/graph/SCC-Kosaraju.hpp
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