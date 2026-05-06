import java.io.*;
import java.util.*;

public class zoj3661 {
    public static void main(String[] args) {
        new Thread(null, new Runnable() {
            public void run() {
                new Solution().run();
            }
        }, "run").start();
    }
}

class Solution {
    Kattio io;

    int maxn = 100005;
    int mod = 777777777;
    int[] len = new int[maxn];
    int[] fail = new int[maxn];
    int[][] ch = new int[maxn][26];
    int[] cnt = new int[maxn];

    int[] pam_parent = new int[maxn]; 
    int[] pam_edge = new int[maxn];

    long[] pow26 = new long[maxn];
    long[] nodeScore = new long[maxn];
    long[] pack = new long[maxn];
    int last, sz;

    void run() {
        io = new Kattio();
        solve();
        io.flush();
    }

    /* 核心逻辑 */
    void solve() {
        int t = io.nextInt();
        while (t-- > 0) {
            int n = io.nextInt(), m = io.nextInt();
            // 读入c数组
            char[] c = ("#"+io.next()).toCharArray();
            // 预处理26的次幂
            pow26[0] = 1;
            for (int i = 1; i <= n; i++) pow26[i] = (pow26[i-1]*26)%mod;
            init();
            for(int i = 1; i < c.length; i++) insert(i,c);
            for (int i = sz; i >= 2; i--) cnt[fail[i]] += cnt[i];
            while (m-- > 0) {
                long k = io.nextLong();
                int[] w = io.nextIntArray(26);
                int packSz = 0;
                nodeScore[0] = 0;
                nodeScore[1] = 0;
                for (int i = 2; i <= sz; i++) {
                    int p = pam_parent[i];
                    int edgeChar = pam_edge[i];
                    int H = (len[i]+1)/2;
                    long weight = ((long)w[edgeChar] * pow26[H-1]) % mod;
                    nodeScore[i] = (nodeScore[p]+weight) % mod;

                    pack[packSz++] = (nodeScore[i]<<32) | (cnt[i]&0xFFFFFFFFL);
                }
                Arrays.sort(pack, 0, packSz);
                long sum = 0;
                for (int i = 0; i < packSz; i++) {
                    sum += (pack[i] & 0xFFFFFFFFL);
                    if (sum >= k) {
                        io.println(pack[i] >>> 32);
                        break;
                    }
                }
            }
            io.println();
        }
    }

    // 初始化回文Trie
    void init() {
        // 一个父节点对应奇回文，另一个对应偶回文
        sz = 1;
        last = 0;
        // 偶回文父节点，fail指针指向奇回文的父节点
        len[0] = 0;
        fail[0] = 1;
        cnt[0] = 0;
        Arrays.fill(ch[0], 0);
        // 奇回文父节点，len=-1的原因是为了在包一层之后长度变为1，备胎该版实现取自己
        len[1] = -1;
        fail[1] = 1;
        cnt[1] = 0;
        Arrays.fill(ch[1], 0);
    }

    // 尝试包裹
    int get_fail(int u, int i, char[] s) {
        while (s[i-len[u]-1] != s[i]) u = fail[u];
        return u;
    }
    
    // 插入一个字符
    void insert(int i, char[] s) {
        // 将字母转成数字
        int c = s[i]-'a';
        // 找位置能包裹c的
        int u = get_fail(last, i, s);
        // 包裹完c的新节点是否还存在
        if (ch[u][c] == 0) {
            // 新编号np，长度+=2
            int np = ++sz;
            Arrays.fill(ch[np], 0);
            cnt[np] = 0;
            len[np] = len[u]+2;
            pam_parent[np] = u;
            pam_edge[np] = c;
            // 找新节点的后备节点
            int fail_node = get_fail(fail[u], i, s);
            fail[np] = ch[fail_node][c];
            // 连指针
            ch[u][c] = np;
        }
        // 更新状态
        last = ch[u][c];
        cnt[last]++;
    }

    class Node implements Comparable<Node>{
        long value;
        int cnt;
        public Node (long value, int cnt) {
            this.value = value;
            this.cnt = cnt;
        }
        @Override
        public int compareTo(Node other) {
            return Long.compare(this.value, other.value);
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