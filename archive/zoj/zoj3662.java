import java.io.*;
import java.util.*;

public class zoj3662 {
    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                new Solution().run();
            }
        }, "run", 1 << 27).start();
    }
}

class Solution {
    Kattio io;

    void run() {
        io = new Kattio();
        solve();
        io.flush();
    }

    List<Integer> primes = new ArrayList<>();
    // 质因子分解因子总数
    int pl = 0;
    // 质因子分解因子
    int[] pf = new int[105];
    // 质因子分解因子单个个数
    int[] pc = new int[105];
    // 因子个数
    int fl = 0;
    // 因子数组
    int[] factors = new int[1005];
    // dp
    int[][][] dp = new int[105][1005][16];
    
    int mod = 1000000007;

    void getPrimes() {
        int maxm = 1005;
        int[] hash = new int[maxm];
        for (int i = 2; i < maxm; i++) {
            if (hash[i] == 1) continue;
            for (int j = i+i; j < maxm; j += i) {
                hash[j] = 1;
            }
        }
        for (int i = 2; i < maxm; i++) if (hash[i] == 0) primes.add(i);
    }

    void analyzePrime(int s) {
        pl = 0;
        for (int i : primes) {
            if (s <= 1) break;
            if (s % i == 0) {
                pf[pl] = i;
                pc[pl] = 0;
                while (s % i == 0) {
                    pc[pl]++;
                    s /= i;
                }
                pl++;
            }
        }
    }

    int getMask(int[] mpf, int[] mpc, int f) {
        int l = mpf.length;
        int res = 0;
        for (int i = 0; i < l; i++) {
            int c = 0;
            while (f % mpf[i] == 0) {
                c++;
                f /= mpf[i];
            }
            if (c == mpc[i]) res = (res << 1) | 1;
            else res = (res << 1) | 0;
        }
        return res;
    }

    /* 核心逻辑 */
    void solve() {
        // 筛选质因数
        getPrimes();
        while (io.hasNext()) {
            int n = io.nextInt(), m = io.nextInt(), k = io.nextInt();
            // 质因子分解
            analyzePrime(m);
            // 缓存m的结果
            int[] mpf = Arrays.copyOfRange(pf, 0, pl);
            int[] mpc = Arrays.copyOfRange(pc, 0, pl);
            // 01背包因子
            fl = 0;
            for (int i = 1; i * i <= m; i++) {
                if (m % i == 0) {
                    factors[fl++] = i;
                    if (i * i != m) factors[fl++] = m/i;
                }
            }
            Arrays.sort(factors, 0, fl);
            int[] fmask = new int[fl];
            for (int f = 0; f < fl; f++) fmask[f] = getMask(mpf, mpc, factors[f]);
            // dp
            for (int i = 0; i <= k; i++) for (int j = 0; j <= n; j++) for (int s = 0; s < 16; s++) dp[i][j][s] = 0;
            dp[0][0][0] = 1;
            for (int i = 1; i <= k; i++) {
                for (int j = 0; j <= n; j++) {
                    for (int s = 0; s < 16; s++) {
                        if (dp[i-1][j][s] > 0) {
                            for (int f = 0; f < fl; f++) {
                                if (j + factors[f] <= n) {
                                    dp[i][j+factors[f]][s|fmask[f]] = (dp[i][j+factors[f]][s|fmask[f]]+dp[i-1][j][s]) % mod;
                                }
                            }
                        }
                    }
                }
            }
            io.println(dp[k][n][(1<<mpf.length)-1]);
        }
    }
}

class Kattio extends PrintWriter {
    private final InputStream in;
    private final boolean closeInput;

    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0, len = 0;

    // -2: 没有缓存；-1: EOF；其他: 缓存的一个字节
    private int peek = -2;

    public Kattio() {
        this(System.in, System.out);
    }

    public Kattio(InputStream input, OutputStream output) {
        super(new BufferedWriter(new OutputStreamWriter(output)));
        this.in = input;
        this.closeInput = false; // 默认不主动关 System.in
    }

    public Kattio(String inputFile, String outputFile) throws IOException {
        super(new BufferedWriter(new FileWriter(outputFile)));
        this.in = new FileInputStream(inputFile);
        this.closeInput = true;
    }

    private int readByte() {
        if (ptr >= len) {
            try {
                len = in.read(buffer);
                ptr = 0;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            if (len <= 0) return -1;
        }
        return buffer[ptr++] & 0xff; // 修正 signed byte 问题
    }

    private int nextByte() {
        if (peek != -2) {
            int c = peek;
            peek = -2;
            return c;
        }
        return readByte();
    }

    private int skipBlank() {
        int c;
        do {
            c = nextByte();
        } while (c != -1 && c <= ' ');
        return c;
    }

    public boolean hasNext() {
        int c = skipBlank();
        if (c == -1) return false;
        peek = c;
        return true;
    }

    public String next() {
        int c = skipBlank();
        if (c == -1) throw new NoSuchElementException("No more tokens");

        StringBuilder sb = new StringBuilder();
        while (c > ' ') {
            sb.append((char) c);
            c = nextByte();
        }
        return sb.toString();
    }

    public int nextInt() {
        long x = nextLong();
        if (x < Integer.MIN_VALUE || x > Integer.MAX_VALUE) {
            throw new NumberFormatException("int overflow: " + x);
        }
        return (int) x;
    }

    public long nextLong() {
        int c = skipBlank();
        if (c == -1) throw new NoSuchElementException("No more tokens");

        int sign = 1;
        if (c == '-' || c == '+') {
            sign = (c == '-') ? -1 : 1;
            c = nextByte();
        }

        if (c < '0' || c > '9') {
            throw new NumberFormatException("Invalid long");
        }

        long val = 0;
        while (c > ' ') {
            if (c < '0' || c > '9') {
                throw new NumberFormatException("Invalid long");
            }
            val = val * 10 + (c - '0');
            c = nextByte();
        }

        return sign == 1 ? val : -val;
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public int[] nextIntArray(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = nextInt();
        return a;
    }

    public long[] nextLongArray(int n) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) a[i] = nextLong();
        return a;
    }

    @Override
    public void close() {
        flush();
        super.close();
        if (closeInput) {
            try {
                in.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }
}

/**
 * 题目：MathMagic (和为N，LCM为M的K元序列总数)
 * 核心考点：定长排列 DP + 质因数状态压缩 (状压背包)
 *
 * 【问题抽象】
 * 从 M 的所有因子中，挑选出 K 个数（可重复挑选，且考虑排列顺序）。
 * 要求这 K 个数的和恰好为 N，且它们的最小公倍数 (LCM) 恰好为 M。
 *
 * 【破局点 1：LCM 的本质与“碎片收集”】
 * 1. 任意数的 LCM，等于它们所有质因数的“最高次幂”的乘积。
 * 2. 要让 K 个数的 LCM 恰好等于 M，等价于：对于 M 的每一个质因数 p^e，
 *    这 K 个数中，【至少有一个数】必须包含完整的 p^e（也就是达到最高次幂）。
 * 3. 因此，挑选数字的过程，本质上是一个“收集质因数满级碎片”的过程。
 * 
 * 【破局点 2：隐藏的数据极值与状态压缩】
 * 1. 题目给定 M <= 1000。
 * 2. 1000 以内的数，最多能有几个不同的质因数？
 *    2 * 3 * 5 * 7 = 210 <= 1000
 *    2 * 3 * 5 * 7 * 11 = 2310 > 1000
 * 3. 结论：M 最多只有 4 个不同的质因数！
 * 4. 我们可以用一个 4 位的二进制数 (Mask) 来记录碎片的收集状态：
 *    - 0位为1：代表挑出的数中，已经达到了第1个质因数的最高次幂。
 *    - 1位为1：代表达到了第2个质因数的最高次幂...以此类推。
 *    - 状态总数极小：0000 到 1111（即 0 到 15）。
 *
 * 【算法设计：状态转移方程】
 * 定义 dp[i][j][S]：
 *   - i: 当前已经挑选了几个数 (1 <= i <= K)
 *   - j: 当前挑出数字的总和 (0 <= j <= N)
 *   - S: 当前收集到的质因数最高次幂的状态 (0 <= S < 16)
 *
 * 转移逻辑：
 * 假设从上一层 dp[i-1][j][S] 出发，我们新挑选了一个因子 f，其自身的状态掩码为 mask_f。
 * 如果 j + f <= N，则可以转移：
 * dp[i][j + f][S | mask_f] = (dp[i][j + f][S | mask_f] + dp[i-1][j][S]) % MOD
 * 
 * （注：外层是阶段 i，相当于“第 i 步选什么”，因此完美区分了 (1,2) 和 (2,1) 的顺序，
 *  且内层 j 正序或倒序遍历均可，不会产生传统一维背包的数据覆盖污染。）
 *
 * 【工程性能优化 (防 TLE 避坑指南)】
 * 1. 预处理 M 的所有因子：只在 M 的因子中进行背包转移，极大缩小搜索空间。
 * 2. 预处理因子的 Mask：由于在最深层循环（千万次级别）中计算掩码会导致 TLE，
 *    必须在 DP 开始前，算出并缓存好每个因子对应的 Mask (fMask 数组)。
 * 3. 无效状态剪枝：在进入最内层循环前，判断 if (dp[i-1][j][S] == 0) continue;
 *
 * @TimeComplexity  O(K * N * 16 * factors_count) ≈ 100 * 1000 * 16 * 32 ≈ 5.1 * 10^7 次运算，完美 AC。
 * @SpaceComplexity O(K * N * 16) 约为 100 * 1005 * 16 * 4 byte ≈ 6.4 MB，内存极其安全。
 */