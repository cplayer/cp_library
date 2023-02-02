---
title: 通过筛法进行多次因数分解
documentation_of: //include/cplayer/math/prime-factorize-precompute.hpp
---

## 描述

通过预计算（筛法），高效进行多次因数分解回答的解法。

## 时间复杂度

预处理`O(n)`，每次回答`O(logn)`。

## 参考文献

- https://cp-algorithms.com/algebra/prime-sieve-linear.html
- https://codeforces.com/blog/entry/54090

## Tips

其实如果写不出来线性的，用传统的方法也可以。

> In practice the linear sieve runs about as fast as a typical implementation of the sieve of Eratosthenes.