import java.io.*;
import java.util.*;

public class zoj3664 {
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

    int maxn = 1005;
    int nc = 0;
    Node[] arr = new Node[maxn];

    /* 核心逻辑 */
    void solve() {
        for (int i = 0; i < maxn; i++) arr[i] = new Node();
        while (io.hasNext()) {
            for (int i = 0; i < maxn; i++) arr[i].init();
            nc = 0;
            int xl = io.nextInt(), yl = io.nextInt();
            int xr = io.nextInt(), yr = io.nextInt();
            int n = io.nextInt(), q = io.nextInt();
            for (int i = 0; i < n; i++) {
                int x1 = io.nextInt(), y1 = io.nextInt();
                int x2 = io.nextInt(), y2 = io.nextInt();
                int isv = 0;
                if (x1 == x2) isv = 1;
                if (nc < 1) {
                    newNode(isv, (isv == 1 ? x1 : y1));
                } else {
                    insert(0, isv, (isv == 1 ? x1:y1), (x1+x2)/2.0, (y1+y2)/2.0);
                }
            }
            for (int i = 0; i < q; i++) {
                int x1 = io.nextInt(), y1 = io.nextInt();
                int x2 = io.nextInt(), y2 = io.nextInt();
                io.println(n+1-query(0, x1, y1, x2, y2));
            }
        }
    }

    void insert(int u, int isv, int val, double midx, double midy) {
        arr[u].sze++;
        // 竖墙
        if (arr[u].isv == 1) {
            if (midx < arr[u].val) {
                if (arr[u].lc == -1) arr[u].lc = newNode(isv, val);
                else insert(arr[u].lc, isv, val, midx, midy);
            } else {
                if (arr[u].rc == -1) arr[u].rc = newNode(isv, val);
                else insert(arr[u].rc, isv, val, midx, midy);
            }
        } else {
            if (midy < arr[u].val) {
                if (arr[u].lc == -1) arr[u].lc = newNode(isv, val);
                else insert(arr[u].lc, isv, val, midx, midy);
            } else {
                if (arr[u].rc == -1) arr[u].rc = newNode(isv, val);
                else insert(arr[u].rc, isv, val, midx, midy);
            }
        }
    }

    int newNode (int isv, int val) {
        int cur = nc;
        arr[cur] = new Node(isv, val);
        nc++;
        return cur;
    }

    int query(int u, double xa, double ya, double xb, double yb) {
        if (u == -1) return 0;
        if (arr[u].isv == 1) {
            if (xa < arr[u].val && xb < arr[u].val) return query(arr[u].lc, xa, ya, xb, yb);
            if (xa > arr[u].val && xb > arr[u].val) return query(arr[u].rc, xa, ya, xb, yb);
        } else {
            if (ya < arr[u].val && yb < arr[u].val) return query(arr[u].lc, xa, ya, xb, yb);
            if (ya > arr[u].val && yb > arr[u].val) return query(arr[u].rc, xa, ya, xb, yb);
        
        }
        return arr[u].sze;
    }
}

class Node {
    int isv;
    int val;
    int lc, rc, sze;
    void init() {
        this.isv = 0;
        this.val = 0;
        this.lc = -1; this.rc = -1; this.sze = 1;
    }
    public Node (int isv, int val) {
        this.init();
        this.isv = isv;
        this.val = val;
    }
    public Node() {}

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