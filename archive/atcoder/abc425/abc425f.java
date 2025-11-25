import java.io.*;
import java.util.*;

public class abc425f {
    public static void main(String[] args) {
        try (Kattio io = new Kattio()) {
            Test test = new Test();
            test.setIO(io);
        }
    }
}

class Test {
    long mod = 998244353;
    Kattio io;
    void setIO(Kattio innerIO) {
        io = innerIO;
        run();
    }

    void run() {
        // 输入
        int n = io.nextInt();
        String str = io.next();
        // 去重
        int[][] nxt = new int[n+1][26];
        for (int c = 0; c < 26; c++) {
            nxt[n][c] = -1;
        }
        for (int i = n-1; i >= 0; i--) {
            for (int c = 0; c < 26; c++) {
                nxt[i][c] = nxt[i+1][c];
            }
            nxt[i][str.charAt(i)-'a'] = i;
        }
        // dp
        long[] dp = new long[1<<n];
        dp[(1<<n)-1] = 1;
        List<Integer> indices = new ArrayList();
        for (int mask = (1<<n)-1; mask > 0; mask--) {
            if (dp[mask] == 0) continue;
            indices.clear();
            for (int i = 0; i < n; i++) {
                if ((mask & (1<<i)) != 0) {
                    indices.add(i);
                }
            }
            for (int k = 0; k < indices.size(); k++) {
                int currentIdx = indices.get(k);
                char currChar = str.charAt(currentIdx);
                if (k > 0) {
                    int prevIdx = indices.get(k-1);
                    char prevChar = str.charAt(prevIdx);
                    if (currChar == prevChar) continue;
                }
                int nextCanonicalMask = 0;
                int currentPosInStr = 0;
                for (int m = 0; m < indices.size(); m++) {
                    if (m == k) continue;
                    int charIndex = str.charAt(indices.get(m)) - 'a';
                    int foundPos = nxt[currentPosInStr][charIndex];
                    nextCanonicalMask |= (1<<foundPos);
                    currentPosInStr = foundPos + 1;
                }
                dp[nextCanonicalMask] = (dp[nextCanonicalMask] + dp[mask]) % mod;
            }
        }
        io.println(dp[0]);
    }

    
}

class Kattio extends PrintWriter {
    private BufferedReader r;
    private StringTokenizer st;

    public Kattio() {
        this(System.in, System.out);
    }

    public Kattio(InputStream i, OutputStream o) {
        super(o);
        r = new BufferedReader(new InputStreamReader(i));
    }

    public Kattio(String intput, String output) throws IOException {
        super(output);
        r = new BufferedReader(new FileReader(intput));
    }

    public String next() {
        try {
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(r.readLine());
            return st.nextToken();
        } catch (Exception e) {
        }
        return null;
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }
}

/**
以下是这道题的核心知识点总结：

1. 逆向思维 (Reverse Thinking)

问题转换： 题目问的是“从空串插入字符变成 T”，这是发散的（插入位置无限）。我们需要将其转化为“从 T 删除字符变成空串”，这是收敛的（每次长度 -1）。

应用： 将路径计数问题转化为从全集 Mask 到 0 的 DP 问题。

2. 状态压缩动态规划 (Bitmask DP)

信号： 数据范围 N≤22 是典型的指数级复杂度信号。

状态定义： 使用一个整数的二进制位（Mask）来表示子序列中保留了原字符串 T 的哪些下标。

复杂度： 时间复杂度 O(N⋅2^N)，刚好能在 2.5s 内通过。

3. 序列自动机 / Next 表 (Subsequence Automaton)

定义： 预处理二维数组 nxt[i][c]，表示“在 T 的第 i 位之后，字符 c 第一次出现的位置”。

作用： 它是实现 O(1) 贪心匹配的基础工具。如果没有这个表，每次重新扫描字符串寻找字符位置，复杂度会退化到 O(N^2⋅2^N)，导致超时。

4. 状态规范化 / 贪心匹配 (Canonicalization / Leftmost Matching)

核心难点： 不同的下标组合（物理状态）可能代表相同的字符串（语义状态）。例如 T="ABA"，下标 {0} 和 {2} 都代表字符串 "A"。

解决方案： 强制规定**“最左匹配原则”**。

不管当前字符串是怎么删出来的，我们总是用它在 T 中最靠左的那组下标来表示它。

利用 nxt 表，将任何物理 Mask 重新映射（Remap）为“规范化 Mask”。

意义： 实现了 DP 的状态去重，将所有等价的路径合并到同一个节点上。

5. 操作层面的去重 (Operation Deduplication)

问题： 对于字符串 "AAB"，删除第一个 'A' 和删除第二个 'A' 得到的结果一样，会导致路径重复计数。

解决方案： 限制转移规则。当出现连续相同字符时，只允许操作第一个。

意义： 防止在 DP 的单步转移中产生重复的边。

一句话总结

这道题是 “状压 DP” 的骨架，填充了 “序列自动机” 的血肉，最后通过 “贪心最左匹配” 的灵魂来解决去重问题。

这是一道非常适合用来练习 “如何处理子序列计数/去重” 的模板题。
 */