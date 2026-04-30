import java.io.*;
import java.util.*;

public class zoj3659 {
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

    /* 核心逻辑 */
    void solve() {
        // 想明白是个并查集就行了，按容量倒序排序，然后依次插入并合并集合即可。
        while (io.hasNext()) {
            int n = io.nextInt();
            int[] u = new int[n+1];
            int[] sze = new int[n+1];
            Arrays.fill(sze, 1);
            for (int i = 0; i <= n; i++) u[i] = i;
            long[] dp = new long[n+1];
            Edge[] edges = new Edge[n-1];
            for (int i = 0; i < n-1; i++) {
                int a = io.nextInt(), b = io.nextInt(), c = io.nextInt();
                edges[i] = new Edge(a, b, c);
            }
            Arrays.sort(edges);
            for (int i = 0; i < n-1; i++) {
                int a = edges[i].x;
                int b = edges[i].y;
                int c = edges[i].value;
                int rootA = find(a, u);
                int rootB = find(b, u);
                long da = dp[rootA] + 1L * sze[rootB] * c;
                long db = dp[rootB] + 1L * sze[rootA] * c;
                dp[rootA] = Math.max(da, db);
                dp[rootB] = Math.max(da, db);
                int newSze = sze[rootA] + sze[rootB];
                sze[rootA] = newSze;
                sze[rootB] = newSze;
                if (da > db) union(b, a, u);
                else union(a, b, u);
            }
            io.println(dp[find(1, u)]);
        }
    }

    public int find(int x, int[] u) {
        if (u[x] != x) u[x] = find(u[x], u);
        return u[x];
    }

    public void union(int x, int y, int[] u) {
        int rootX = find(x, u);
        int rootY = find(y, u);
        if (rootX != rootY) u[rootX] = rootY;
    }

    class Edge implements Comparable<Edge> {
        int x, y, value;
        public Edge(int x, int y, int value) {
            this.x = x; this.y = y; this.value = value;
        }
        @Override
        public int compareTo(Edge other) {
            return Integer.compare(other.value, value);
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