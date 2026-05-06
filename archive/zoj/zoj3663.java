import java.io.*;
import java.util.*;

public class zoj3663 {
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
            double r = io.nextDouble(), h = io.nextDouble(), lat0 = io.nextDouble(), lng0 = io.nextDouble(), lat1 = io.nextDouble(), lng1 = io.nextDouble();
            double rr = r+h;
            // 3d坐标
            double za = -rr*Math.sin(lat0);
            double xa = rr*Math.cos(lat0)*Math.cos(lng0);
            double ya = rr*Math.cos(lat0)*Math.sin(lng0);
            double zb = -rr*Math.sin(lat1);
            double xb = rr*Math.cos(lat1)*Math.cos(lng1);
            double yb = rr*Math.cos(lat1)*Math.sin(lng1);
            // 夹角theta
            double dot = xa*xb+ya*yb+za*zb;
            double theta = Math.acos(Math.max(-1.0, Math.min(1.0, dot/(rr*rr))));
            // uz
            double uz = (zb-za*Math.cos(theta))/Math.sin(theta);
            // 看不到的坐标
            double zc = -Math.sqrt(rr*rr-r*r);
            // 辅助角
            double beta = Math.atan2(uz, za);
            // 最大高度
            double c = Math.sqrt(za*za+uz*uz);
            // 比例
            double ratio = zc/c;
            if (ratio >= 1.0) io.println(String.format("%.3f", 0.0));
            else if (ratio <= -1.0) io.println(String.format("%.3f", 100.0));
            else {
                double gamma = Math.acos(ratio);
                double alpha1 = beta-gamma;
                double alpha2 = beta+gamma;
                double res = 0;
                for (int k = -1; k <= 1; k++) {
                    double l1 = alpha1+2*k*Math.PI;
                    double l2 = alpha2+2*k*Math.PI;
                    double f1 = Math.max(0.0, l1);
                    double f2 = Math.min(theta, l2);
                    if (f1 < f2) res += (f2 - f1);
                }
                io.println(String.format("%.3f", res/theta*100));
            }
            
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