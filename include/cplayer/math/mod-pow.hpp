#ifndef CPLAYER_MOD_POW_H
#define CPLAYER_MOD_POW_H

namespace cplayer
{
    long long mod_pow(long long x, long long n, const int m)
    {
        if ((x %= m) < 0)
            x += m;
        long long res = 1;
        for (; n > 0; n >>= 1)
        {
            if (n & 1)
                res = (res * x) % m;
            x = (x * x) % m;
        }
        return res;
    }
} // namespace cplayer

#endif // CPLAYER_MOD_POW_H