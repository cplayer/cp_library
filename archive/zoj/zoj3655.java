import java.io.*;
import java.util.*;

public class zoj3655 {
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
        while (io.hasNext()) {
            int n = io.nextInt();
            long l = io.nextLong(), r = io.nextLong();
            long ac = io.nextLong(), bc = io.nextLong();
            long[] p = io.nextLongArray(n);
            int left = 0, right = n;
            while (left < right) {
                int mid = (left+right+1)/2;
                if (judge(n, mid, l, r, ac, bc, p)) {
                    left = mid;
                } else {
                    right = mid-1;
                }
            }
            io.println(left);
        }
    }

    boolean judge(int n, int m, long l, long r, long ac, long bc, long[] p) {
        long[] sl = new long[n];
        long[] sr = new long[n];
        boolean[] valid = new boolean[n];
        long w = ac - bc;
        for (int i = 0; i <= n-m; i++) {
            sl[i] = p[i+m-1]-w+1;
            sr[i] = p[i];
            if (sl[i] <= sr[i]) {
                valid[i] = true;
            }
        }
        for (int i = 0; i <= n-m; i++) {
            if (valid[i]) {
                if (i > 0 && valid[i-1] && sl[i] <= sr[i-1]+1) {
                    sl[i] = sl[i-1];
                }
                if (Math.max(l, sl[i]) <= Math.min(r-ac+1, sr[i]-bc)) {
                    return true;
                }
                // if (sr[i] - sl[i] + 1 >= bc) {
                //     return true;
                // }
            }
        }
        return false;

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