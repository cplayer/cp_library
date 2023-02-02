#ifndef CPLAYER_PRIME_FACTORIZE_PRECOMPUTE_H
#define CPLAYER_PRIME_FACTORIZE_PRECOMPUTE_H

#include <vector>

namespace cplayer
{
    class MultiPrimeFactorize
    {
    public:
        std::vector<int> spf;
        std::vector<int> primes;

        explicit MultiPrimeFactorize(const int n) : n(n)
        {
            spf.resize(n + 1);
        }

        void init()
        {
            std::fill(spf.begin(), spf.end(), 0);
            spf[1] = 1;
            for (int i = 2; i <= n; i++) {
                if (spf[i] == 0) {
                    spf[i] = i;
                    primes.push_back(i);
                }
                for (int j = 0; i * primes[j] <= n; j++) {
                    spf[i * primes[j]] = primes[j];
                    if (primes[j] == spf[i]) {
                        break;
                    }
                }
            }
        }

        std::vector<int> getFactors(int x)
        {
            std::vector<int> ret;
            while (x != 1)
            {
                ret.push_back(spf[x]);
                x = x / spf[x];
            }
            return ret;
        }

        int isPrime(int x)
        {
            if (spf[x] == x)
                return 1;
            else
                return 0;
        }

    protected:
        const int n;
    };
} // namespace cplayer

#endif // CPLAYER_PRIME_FACTORIZE_PRECOMPUTE_H