---
title: 二分快速幂
documentation_of: //include/cplayer/math/mod-pow.hpp
---

## Description

二分快速幂

## 时间复杂度
$O(\log{N})$

## 使用方法

|名称|返回值|
|:--|:--|
|`long long mod_pow(long long x, long long n, const int m);`|$x^n \bmod{m}$|


## 大体逻辑
核心思想是每一步都把指数分成两半，对应的底数做平方运算。

举例：
求$a^{11}$的结果。

$11 = 2^0 + 2^1 + 2^3$

那么，可以得出$a^{11} = a^{2^0} \cdot a^{2^1} \cdot a^{2^3}$

## 代码讲解：
每次通过`n & 1`取出`n`的最末位，`x`为对应的底数，如果指数最末位为1，则`res`乘上对应的底数并取余。
