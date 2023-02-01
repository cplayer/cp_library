---
data:
  _extendedDependsOn: []
  _extendedRequiredBy: []
  _extendedVerifiedWith: []
  _isVerificationFailed: false
  _pathExtension: hpp
  _verificationStatusIcon: ':warning:'
  attributes:
    document_title: "\u8FB9"
    links: []
  bundledCode: "#line 1 \"include/cplayer/graph/edge.hpp\"\n/**\n * @brief \u8FB9\n\
    */\n\n#ifndef CPLAYER_EDGE_H\n#define CPLAYER_EDGE_H\n\nnamespace cplayer\n{\n\
    \    template <typename CostType>\n    struct Edge\n    {\n        int src, dst;\n\
    \        CostType cost;\n        explicit Edge(const int src, const int dst, const\
    \ CostType cost = 0)\n            : src(src), dst(dst), cost(cost) {}\n      \
    \  inline bool operator<(const Edge &x) const\n        {\n            if (cost\
    \ != x.cost)\n                return cost < x.cost;\n            return src !=\
    \ x.src ? src < x.src : dst < x.dst;\n        }\n        inline bool operator<=(const\
    \ Edge &x) const { return !(x < *this); }\n        inline bool operator>(const\
    \ Edge &x) const { return x < *this; }\n        inline bool operator>=(const Edge\
    \ &x) const { return !(*this < x); }\n    };\n} // namespace cplayer\n\n#endif\
    \ // CPLAYER_EDGE_H\n"
  code: "/**\n * @brief \u8FB9\n*/\n\n#ifndef CPLAYER_EDGE_H\n#define CPLAYER_EDGE_H\n\
    \nnamespace cplayer\n{\n    template <typename CostType>\n    struct Edge\n  \
    \  {\n        int src, dst;\n        CostType cost;\n        explicit Edge(const\
    \ int src, const int dst, const CostType cost = 0)\n            : src(src), dst(dst),\
    \ cost(cost) {}\n        inline bool operator<(const Edge &x) const\n        {\n\
    \            if (cost != x.cost)\n                return cost < x.cost;\n    \
    \        return src != x.src ? src < x.src : dst < x.dst;\n        }\n       \
    \ inline bool operator<=(const Edge &x) const { return !(x < *this); }\n     \
    \   inline bool operator>(const Edge &x) const { return x < *this; }\n       \
    \ inline bool operator>=(const Edge &x) const { return !(*this < x); }\n    };\n\
    } // namespace cplayer\n\n#endif // CPLAYER_EDGE_H"
  dependsOn: []
  isVerificationFile: false
  path: include/cplayer/graph/edge.hpp
  requiredBy: []
  timestamp: '2023-02-01 03:16:57+00:00'
  verificationStatus: LIBRARY_NO_TESTS
  verifiedWith: []
documentation_of: include/cplayer/graph/edge.hpp
layout: document
redirect_from:
- /library/include/cplayer/graph/edge.hpp
- /library/include/cplayer/graph/edge.hpp.html
title: "\u8FB9"
---
