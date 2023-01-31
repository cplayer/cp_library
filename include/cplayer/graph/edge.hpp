#ifndef CPLAYER_EDGE_H
#define CPLAYER_EDGE_H

namespace cplayer
{
    template <typename CostType>
    struct Edge
    {
        int src, dst;
        CostType cost;
        explicit Edge(const int src, const int dst, const CostType cost = 0)
            : src(src), dst(dst), cost(cost) {}
        inline bool operator<(const Edge &x) const
        {
            if (cost != x.cost)
                return cost < x.cost;
            return src != x.src ? src < x.src : dst < x.dst;
        }
        inline bool operator<=(const Edge &x) const { return !(x < *this); }
        inline bool operator>(const Edge &x) const { return x < *this; }
        inline bool operator>=(const Edge &x) const { return !(*this < x); }
    };
} // namespace cplayer

#endif // CPLAYER_EDGE_H