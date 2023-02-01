/**
 * @brief SAT-Kosaraju
 */

#define PROBLEM "https://judge.yosupo.jp/problem/two_sat"

#include <string>
#include <iostream>

#include "cplayer/graph/SAT-Kosaraju.hpp"

int main()
{
    std::string p, cnf;
    int n, m;
    std::cin >> p >> cnf >> n >> m;
    cplayer::TwoSATKosaraju twosat(n);
    while (m--)
    {
        int a, b, zero;
        std::cin >> a >> b >> zero;
        // 将a和b转换成0开头的索引
        if (a < 0)
        {
            a = twosat.negate(-a - 1);
        }
        else
        {
            --a;
        }
        if (b < 0)
        {
            b = twosat.negate(-b - 1);
        }
        else
        {
            --b;
        }
        twosat.add_or(a, b);
    }
    const std::vector<int> ans = twosat.build();
    std::cout << "s ";
    if (ans.empty())
    {
        std::cout << "UNSATISFIABLE\n";
        return 0;
    }
    std::cout << "SATISFIABLE\nv ";
    for (int i = 0; i < n; ++i)
    {
        std::cout << (i + 1) * (ans[i] == 1 ? 1 : -1) << ' ';
    }
    std::cout << "0\n";
    return 0;
}