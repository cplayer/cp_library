---
title: 2SAT-Kosaraju
documentation_of: //include/cplayer/graph/SAT-Kosaraju.hpp
---

基于Kosaraju算法的2-SAT判定

## 时间复杂度

$O(\|V\|+\|E\|)$

## 方法

|方法名称|功能|
|---|---|
|explicit TwoSATKosaraju (const int n)|构造n个条件的2-SAT问题|
|std::vector<bool> build()|寻找一个2-SAT问题的解，如果有矛盾返回空序列|
|int negate(const int x)|$\lnot x$的顶点号|
|void add_or(const int x, const int y)|追加$x \lor y$|
|void add_if(const int x, const int y)|追加$x \Leftarrow y$|
|void add_nand(const int x, const int y)|追加$\lnot(x \land y)$|
|void set_true(const int x)|设置$x$为真|
|void set_false(const int x)|设置$x$为假|

## 描述

2-SAT问题：给$n$个变量$a_i$，每个变量只能取$0/1$两个可能值。同时给出若干条件，形式诸如$(\lnot) \ a_i \ opt \ (\lnot) a_j = 0/1$，其中$opt$表示逻辑运算符（`and,or,xor`）。

2-SAT问题求解就是求出满足题意的一组解。