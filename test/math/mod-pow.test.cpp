/*
 * @brief 数学/$i^k \bmod m$ ($0 \leq i \leq n$)
 */

#define PROBLEM "http://judge.u-aizu.ac.jp/onlinejudge/description.jsp?id=NTL_1_B"

#include <iostream>

#include "cplayer/math/mod-pow.hpp"

int main () {
    int m, n;
    std::cin >> m >> n;
    std::cout << cplayer::mod_pow(m, n, 1000000007) << '\n';
    return 0;
}