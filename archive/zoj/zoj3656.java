import java.io.*;
import java.util.*;

public class zoj3656 {
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
            int[][] b = new int[n][n];
            for (int i = 0; i < n; i++) {
                b[i] = io.nextIntArray(n);
            }
            boolean flag = true;
            for (int i = 0; i < 32; i++) {
                // 第i位
                // 假设a[0]和a[1]
                if (!judge(b, n, i)) {
                    flag = false;
                    break;
                }
            }
            if (flag) io.println("YES");
            else io.println("NO");
        }
    }

    boolean judge(int[][] b, int n, int bit) {
        // 每次都枚举a[0]
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (b[i][i] != 0) return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    if (b[i][j] != b[j][i]) return false;
                }
            }
        }
        a[0] = 0;
        boolean ok0 = judgeSingle(b, a, n, bit);
        a[0] = 1;
        boolean ok1 = judgeSingle(b, a, n, bit);
        if (ok0 || ok1) return true;
        return false;
    }

    boolean judgeSingle(int[][] b, int[] a, int n, int bit) {
        for (int i = 1; i < n; i += 2) {
            a[i] = a[0] ^ ((b[0][i] >> bit) & 1);
        }
        for (int i = 2; i < n; i += 2) {
            a[i] = a[1] ^ ((b[1][i] >> bit) & 1);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                if ((i % 2 == 1) && (j % 2 == 1)){
                    if ((a[i] | a[j]) != ((b[i][j] >> bit) & 1)) {
                        return false;
                    }
                } else if ((i % 2 == 0) && (j % 2 == 0)) {
                    if ((a[i] & a[j]) != ((b[i][j] >> bit) & 1)) {
                        return false;
                    }
                } else {
                    if ((a[i] ^ a[j]) != ((b[i][j] >> bit) & 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
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