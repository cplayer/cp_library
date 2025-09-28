import java.io.*;
import java.util.*;

public class D_Jongmah {
    public static void main(String[] args) {
        try (Kattio io = new Kattio()) {
            Test test = new Test();
            test.run(io);
        }
    }

}

class Test {
    void run(Kattio io) {
        int n = io.nextInt(), m = io.nextInt();
        int[] arr = new int[m+3];
        for (int i = 0; i < n; i++) arr[io.nextInt()]++;
        int[][][] dp = new int[m+3][3][3];
        for (int i = 0; i <= m; i++) 
            for (int j = 0; j < 3; j++) 
                for (int k = 0; k < 3; k++) dp[i][j][k] = Integer.MIN_VALUE;
        dp[0][0][0] = 0;
        for (int i = 1; i <= m+2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (arr[i] >= j + k + l) {
                            dp[i][j][k] = Math.max(dp[i][j][k],
                            dp[i-1][k][l] + l + (arr[i] - j - k - l) / 3);
                        }
                    }
                }
            }
        }
        io.println(dp[m+2][0][0]);
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
 * 这道题可以用**动态规划 (Dynamic Programming)** 来解决。核心思想是按顺序处理每一种数字的牌，并记录下为了组成顺子而“预留”了哪些牌。

---

### ## 解题思路

我们首先统计每种数字牌的数量，记为 `count[i]`。然后，我们从数字 1 开始依次处理到 `m`。

当我们处理数字为 `i` 的牌时，我们需要做的决策是：用 `i`、`i+1`、`i+2` 这三种牌组成多少个顺子。这个决策会影响到后面 `i+1` 和 `i+2` 的决策，因为它们的部分牌被预定了。这种依赖关系非常适合用动态规划来解决。

关键在于，当我们考虑数字 `i` 时，有多少种不同的“预留”状态需要记录？
我们可以发现，对于任何一种顺子，比如 `(i, i+1, i+2)`，如果我们计划组成 3 个或更多，比如 4 个，那么这会用掉 4 张 `i`，4 张 `i+1` 和 4 张 `i+2`。这和我们分别组成 4 个刻子 `(i,i,i)`、`(i+1,i+1,i+1)`、`(i+2,i+2,i+2)` 相比，在不影响其他牌的情况下，获得的面子（三元组）数量是一样的。甚至拆分成刻子可能更优，因为它不依赖其他牌。

一个更严谨的观察是，3 个 `(i, i+1, i+2)` 的顺子和 1 个 `(i,i,i)`、1 个 `(i+1,i+1,i+1)`、1 个 `(i+2,i+2,i+2)` 的刻子，它们消耗的牌完全相同。所以，我们计划组成的任何顺子数量 `k`，都可以用 `k % 3` 来替代，多出来的部分可以视为直接组成了刻子。因此，对于任何一种顺子，我们只需要考虑组成 0, 1, 或 2 个就足够了。

这大大减少了我们需要记录的状态数量。

---

### ## 动态规划设计

#### 状态定义

我们可以定义一个三维 DP 数组：
`dp[i][j][k]` 表示：**已经考虑完 1 到 `i` 号牌，并且已经承诺要组成 `j` 个顺子 `(i, i+1, i+2)` 和 `k` 个顺子 `(i-1, i, i+1)` 的情况下，所能获得的最大面子数**。

这里的 `j` 和 `k` 的取值范围都是 `0, 1, 2`。



#### 状态转移

为了计算 `dp[i][j][k]`，我们需要考虑上一个状态，也就是处理 `i-1` 号牌时的状态。
上一个状态应该是 `dp[i-1][k][l]`，其中：
* `k` 对应 `(i-1, i, i+1)` 顺子的数量（在新状态中，这是“过去”的承诺）。
* `l` 对应 `(i-2, i-1, i)` 顺子的数量（这是“更过去”的承诺）。`l` 的取值范围也是 `0, 1, 2`。

现在，我们站在 `i` 号牌的角度，回顾一下牌的消耗情况：
1.  我们承诺了 `j` 个 `(i, i+1, i+2)`，需要 `j` 张 `i` 号牌。
2.  我们从上一步继承了 `k` 个 `(i-1, i, i+1)` 的承诺，需要 `k` 张 `i` 号牌。
3.  我们从上上步继承了 `l` 个 `(i-2, i-1, i)` 的承诺，需要 `l` 张 `i` 号牌。

所以，为了满足这些顺子的承诺，我们总共需要 `j + k + l` 张 `i` 号牌。这个数量不能超过我们拥有的 `i` 号牌数量，即 `count[i]`。

如果 `count[i] >= j + k + l`，那么这个状态转移就是可能的。
* 在这一步，`l` 个 `(i-2, i-1, i)` 的顺子正式完成，所以我们获得了 `l` 个面子。
* 剩下的 `count[i] - (j + k + l)` 张 `i` 号牌，可以用来组成刻子 `(i, i, i)`。能组成的数量是 `(count[i] - j - k - l) / 3`。

因此，状态转移方程如下：
$$dp[i][j][k] = \max_{0 \le l \le 2} \left( dp[i-1][k][l] + l + \frac{\text{count}[i] - j - k - l}{3} \right)$$
这个方程需要在 `count[i] \ge j + k + l` 的条件下进行。我们遍历所有可能的 `l` (0, 1, 2)，从中取最大值。

---

### ## 最终答案与实现细节

* **初始化**：将 DP 数组全部初始化为一个非常小的负数（表示不可达），但 `dp[0][0][0] = 0`。
* **循环**：我们从 `i = 1` 循环到 `m+2`。多循环两次是为了确保以 `m` 结尾的顺子（如 `(m-1, m, m+1)` 和 `(m, m+1, m+2)`) 能够被正确计算和计入总数。
* **最终答案**：所有牌都处理完毕后，我们不应该有任何未完成的顺子承诺。所以最终答案就是 `dp[m+2][0][0]`。

这个算法的时间复杂度是 $O(n + m \times 3^3) = O(n+m)$，空间复杂度是 $O(m \times 3^2) = O(m)$，对于 $10^6$ 的数据量来说是足够快的。
 */