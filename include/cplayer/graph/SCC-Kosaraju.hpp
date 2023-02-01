#ifndef CPLAYER_SCC_KOSARAJU_H
#define CPLAYER_SCC_KOSARAJU_H

#include <vector>
#include <algorithm>

namespace cplayer
{
    class Kosaraju_Light
    {
    public:
        explicit Kosaraju_Light(const int n) : sccK_n(n), graph(n), rgraph(n),
                                               isVisited(n), ids(n)
        {
            order.reserve(n);
        }

        inline void add_edge (int st, int ed) {
            graph[st].emplace_back(ed);
        }

        inline void add_edge_rev (int st, int ed) {
            rgraph[st].emplace_back(ed);
        }

        void construct_scc()
        {
            std::fill(isVisited.begin(), isVisited.end(), false);
            std::fill(ids.begin(), ids.end(), -1);
            order.clear();
            for (int i = 0; i < sccK_n; ++i)
            {
                if (!isVisited[i])
                    dfs(i);
            }
            for (int i = sccK_n - 1, id = 0; i >= 0; --i)
            {
                if (ids[order[i]] == -1)
                    rdfs(order[i], id++);
            }
        }

    protected:
        const int sccK_n;
        std::vector<std::vector<int>> graph, rgraph;
        std::vector<bool> isVisited;
        std::vector<int> ids, order;

        void dfs(const int ver)
        {
            isVisited[ver] = true;
            for (const int dst : graph[ver])
            {
                if (!isVisited[dst])
                    dfs(dst);
            }
            order.emplace_back(ver);
        }

        void rdfs(const int ver, const int cur_id)
        {
            ids[ver] = cur_id;
            for (const int dst : rgraph[ver])
            {
                if (ids[dst] == -1)
                    rdfs(dst, cur_id);
            }
        }
    };
} // namespace cplayer

#endif // CPLAYER_SCC_KOSARAJU_H