#define PROBLEM "https://judge.yosupo.jp/problem/many_aplusb"

#include <iostream>

using namespace std;

int main(int argc, char const *argv[])
{
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    int t;
    cin >> t;
    while (t--) {
        long long a, b;
        cin >> a >> b;
        cout << a + b << endl;
    }
    return 0;
}
