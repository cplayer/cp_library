#ifndef CPLAYER_SAT_KOSARAJU_H
#define CPLAYER_SAT_KOSARAJU_H

#include "cplayer/graph/SCC-Kosaraju.hpp"

namespace cplayer
{
    class TwoSATKosaraju : public Kosaraju_Light
    {
    public:
        explicit TwoSATKosaraju(const int n) : Kosaraju_Light(n << 1), satK_n(n) {}

        std::vector<int> build()
        {
            construct_scc();
            std::vector<int> res(satK_n);
            for (int i = 0; i < satK_n; ++i)
            {
                if (ids[i] == ids[negate(i)])
                    return {};
                res[i] = ids[negate(i)] < ids[i] ? 1 : -1;
            }
            return res;
        }

        int negate(const int x) const { return (satK_n + x) % (satK_n << 1); }

        void add_or(const int x, const int y)
        {
            add_edge(negate(x), y);
            add_edge(negate(y), x);
            add_edge_rev(y, negate(x));
            add_edge_rev(x, negate(y));
        }

        void add_if(const int x, const int y)
        {
            add_or(negate(x), y);
        }

        void add_nand(const int x, const int y)
        {
            add_or(negate(x), negate(y));
        }

        void set_true(const int x) { add_or(x, x); }
        void set_false(const int x) { set_true(negate(x)); }

    protected:
        const int satK_n;
    };
} // namespace cplayer

#endif // CPLAYER_SAT_KOSARAJU_H