import java.io.*;
import java.util.*;

public class zoj3660 {
    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                new Solution().run();
            }
        }, "run", 1 << 25).start();
    }
}

class Solution {
    
    Kattio io;

    void run() {
        io = new Kattio();
        solve();
        io.flush();
    }

    long mod = 1000000007;
    private static final int maxn = 100005;
    int edgeCount = 0;
    int[] head = new int[maxn];
    int[] to = new int[maxn];
    int[] nextEdge = new int[maxn];
    long[] tempHashes = new long[maxn];
    Map<Long, Long> globalDps = new HashMap<>();

    void addEdge(int u, int v) {
        to[edgeCount] = v;
        nextEdge[edgeCount] = head[u];
        head[u] = edgeCount;
        edgeCount++;
    }

    /* 核心逻辑 */
    void solve() {
        while (io.hasNext()) {
            // 读入
            long n = io.nextLong(), m = io.nextLong()%mod;
            long m_minus_1 = (m-1+mod)%mod;
            int[] graph = new int[(int)n+1];
            int[] inDegree = new int[(int)n+1];
            Arrays.fill(head,0,(int)n+1,-1);
            edgeCount = 0;
            for (int i = 1; i <= n; i++) {
                int p = io.nextInt();
                // 正图，只有一个出边
                graph[i] = p;
                // 反图
                addEdge(p, i);
                inDegree[p]+=1;
            }
            // 拓扑排序找环
            Queue<Integer> queue = new LinkedList<>();
            // 入度为0入队
            for (int i = 1; i <= n; i++) if (inDegree[i] == 0) queue.offer(i);
            long[] treehash = new long[(int)n+1];
            Arrays.fill(treehash, 1L);
            globalDps.clear();
            // globalDps.put(1L,1L);
            long[] dp = new long[(int)n+1];
            while (!queue.isEmpty()) {
                int top = queue.poll();
                // 融合top的treehash
                int count = 0;
                for (int e = head[top]; e != -1; e = nextEdge[e]) {
                    int u = to[e];
                    tempHashes[count++] = treehash[u];
                }
                Arrays.sort(tempHashes, 0, count);
                for (int i = 0; i < count; i++) {
                    treehash[top] += shift(tempHashes[i]);
                }
                // 找一样的treehash，统计个数，并计算新dp
                long dpNew = 1;
                for (int i = 0; i < count; ) {
                    int j = i;
                    while (j < count && tempHashes[j] == tempHashes[i]) j++;
                    int c = j-i;
                    // 子树的hash值
                    long w = globalDps.get(tempHashes[i]);
                    dpNew = (dpNew * getComp(w, c)) % mod;
                    i = j;
                }
                dp[top] = (dpNew*m_minus_1)%mod;
                globalDps.put(treehash[top], dp[top]);
                // 减入度
                int p = graph[top];
                inDegree[p]--;
                if (inDegree[p] == 0) queue.offer(p);
            }
            // 找主环
            List<Integer> cycle = new ArrayList<>();
            int pt = 0;
            for (int i = 1; i <= n; i++) if (inDegree[i] > 0) {
                pt = i; break;
            }
            for (int i = pt;;) {
                cycle.add(i);
                i = graph[i];
                if (i == pt) break;
            }
            int cyclesize = cycle.size();
            // 遍历环，找同样的方案
            for (int k = 0; k < cyclesize; k++) {
                int top = cycle.get(k);
                // 融合top的treehash
                int count = 0;
                for (int e = head[top]; e != -1; e = nextEdge[e]) {
                    int u = to[e];
                    if (inDegree[u] == 0) tempHashes[count++] = treehash[u];
                }
                Arrays.sort(tempHashes, 0, count);
                for (int i = 0; i < count; i++) {
                    treehash[top] += shift(tempHashes[i]);
                }
                // 找一样的treehash，统计个数，并计算新dp
                long dpNew = 1;
                for (int i = 0; i < count; ) {
                    int j = i;
                    while (j < count && tempHashes[j] == tempHashes[i]) j++;
                    int c = j-i;
                    // 子树的hash值
                    long w = globalDps.get(tempHashes[i]);
                    dpNew = (dpNew * getComp(w, c)) % mod;
                    i = j;
                }
                dp[top] = dpNew;
            }
            // Polya计数定理
            long ans = 0;
            long validcyclesize = 0;
            for (int d : getDivisors(cyclesize)) {
                // 旋转k步之后，环会被切分成k个独立的轨道
                // 轨道内的树hash必须一致
                boolean valid = true;
                for (int i = 0; i < d; i++) {
                    long expectedHash = treehash[cycle.get(i)];
                    long expectedDP = dp[cycle.get(i)];
                    for (int j = i+d; j < cyclesize; j+=d) {
                        if (treehash[cycle.get(j)] != expectedHash || dp[cycle.get(j)] != expectedDP) {
                            valid = false;
                            break;
                        }
                    }
                    if (!valid) break;
                }
                if (!valid) continue;
                // 给d的环染色
                long term2 = (d%2==1)?(mod-m_minus_1):m_minus_1;
                long cycleColorWays = (power(m_minus_1,d,mod)+term2) % mod;
                // 累加树枝的贡献
                long totalWaysForK = cycleColorWays;
                for (int i = 0; i < d; i++) totalWaysForK = (totalWaysForK * dp[cycle.get(i)]) % mod;
                long countK = getPhi(cyclesize/d);
                validcyclesize += countK;
                long contribution = (totalWaysForK*(countK%mod))%mod;
                ans = (ans + contribution) % mod;
            }
            ans = (ans * power(validcyclesize, mod-2, mod)) % mod;
            io.println(ans);
        }
    }

    int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a%b);
    }

    long getPhi(int n) {
        long res = n;
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                res = res / i * (i - 1);
                while (n % i == 0) n /= i;
            }
        }
        if (n > 1) res = res / n * (n - 1);
        return res;
    }

    List<Integer> getDivisors(int n) {
        List<Integer> divisors = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                if (i * i != n) {
                    divisors.add(n / i);
                }
            }
        }
        return divisors;
    }

    // 打乱函数
    long shift(long x) {
        x ^= x << 13;
        x ^= x >>> 7;
        x ^= x << 17;
        return x;
    }

    long getComp(long a, long b) {
        long up = 1;
        long down = 1;
        for (int i = 1; i <= b; i++) {
            up = (up * ((a+b-i) % mod)) % mod;
            down = (down * i) % mod;
        }
        return (up * power(down, mod-2, mod)) % mod;
    }

    long power(long x, long power, long mod) {
        if (power <= 0) return 1;
        if (power <= 1) return x;
        long res = power(x, power/2, mod);
        res = (res * res) % mod;
        if (power % 2 == 1) res = (res * x) % mod;
        return res % mod;
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