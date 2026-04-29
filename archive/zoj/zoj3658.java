import java.io.*;
import java.util.*;

public class zoj3658 {
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

    List<Interval> res = new ArrayList<>();
    List<Interval> nextRange = new ArrayList<>();
    double eps = 1e-9;

    /* 核心逻辑 */
    void solve() {
        int t = io.nextInt();
        while (t-- > 0) {
            res.clear();
            int a = io.nextInt(), b = io.nextInt(), c = io.nextInt(), d = io.nextInt(), e = io.nextInt();
            long alpha = d*d - 4*e;
            long beta = 4*a*e + 4*c - 2*b*d;
            long gamma = b*b - 4*a*c;
            long delta = beta*beta - 4*alpha*gamma;
            if (b == a*d && c == a*e) {
                res.add(new Interval(a, a, true, true));
            } else {
                // 候选区间
                if (alpha != 0 && delta > 0) {
                    double y1 = (-beta - Math.sqrt(delta))/(2*alpha);
                    double y2 = (-beta + Math.sqrt(delta))/(2*alpha);
                    if (y1 > y2) {
                        double tmp = y1;
                        y1 = y2;
                        y2 = tmp;
                    }
                    if (alpha > 0) {
                        res.add(new Interval(Double.NEGATIVE_INFINITY, y1, false, true));
                        res.add(new Interval(y2, Double.POSITIVE_INFINITY, true, false));
                    } else if (alpha < 0) {
                        res.add(new Interval(y1, y2, true, true));
                    }
                } else if (alpha > 0 && delta <= 0) {
                    res.add(new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false));
                } else if (alpha < 0 && isZero(delta)) {
                    double y0 = -1.0*beta/(2.0*alpha);
                    res.add(new Interval(y0, y0, true, true));
                } else if (alpha == 0) {
                    if (isZero(beta) && gamma >= 0) {
                        res.add(new Interval(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, false));
                    } else if (beta > 0) {
                        res.add(new Interval(-1.0*gamma/beta, Double.POSITIVE_INFINITY, true, false));
                    } else if (beta < 0) {
                        res.add(new Interval(Double.NEGATIVE_INFINITY, -1.0*gamma/beta, false, true));
                    }
                }
                // y == A
                digIfNotReachable(a,a,b,c,d,e);
                // 分母
                delta = d*d-4*e;
                if (delta > 0) {
                    double x1 = (-1*d-Math.sqrt(delta))/2;
                    double x2 = (-1*d+Math.sqrt(delta))/2;
                    processRoot(x1,a,b,c,d,e);
                    processRoot(x2,a,b,c,d,e);
                } else if (isZero(delta)) {
                    double x = -1.0*d/2;
                    processRoot(x,a,b,c,d,e);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < res.size(); i++) {
                sb.append(res.get(i).toString());
                if (i < res.size()-1) sb.append(" U ");
            }
            io.println(sb.toString());
        }
    }

    void processRoot(double r, int a, int b, int c, int d, int e) {
        double pr = a*r*r+b*r+c;
        if (isZero(pr)) {
            double dyr = 2*r+d;
            if (!isZero(dyr)) {
                double y = (2*a*r+b)/(2*r+d);
                digIfNotReachable(y,a,b,c,d,e);
            } else {
                double prr = 2*a*r+b;
                if (isZero(prr)) {
                    digIfNotReachable(a,a,b,c,d,e);
                }
            }
        }
    }

    void digIfNotReachable(double y, int a, int b, int c, int d, int e) {
        if (!hasLegalSolution(y,a,b,c,d,e)) digHole(y);
    }

    boolean hasLegalSolution(double y, int a, int b, int c, int d, int e) {
        if (isZero(a-y)) {
            if (isZero(b-d*y)) {
                if (!isZero(c-e*y)) return false;
                else return true;
            } else {
                double x = -(c-e*y)/(b-d*y);
                if (!isZero(x*x+d*x+e)) {
                    return true;
                } else return false;
            }
        } else {
            double newa = a-y;
            double newb = b-d*y;
            double newc = c-e*y;
            double delta = newb*newb - 4*newa*newc;
            if (delta < -eps) return false;
            else if (isZero(delta)) {
                double x = -newb/(2.0*newa);
                if (isZero(x*x+d*x+e)) return false;
                else return true;
            } else {
                double x0 = (-newb-Math.sqrt(delta))/(2.0*newa);
                double x1 = (-newb+Math.sqrt(delta))/(2.0*newa);
                if (isZero(x0*x0+d*x0+e) && isZero(x1*x1+d*x1+e)) {
                    return false;
                } else return true;
            }
        }
    }

    boolean isZero(double x) {
        return Math.abs(x) < eps;
    }

    void digHole(double hole) {
        nextRange.clear();
        List<Interval> invs = new ArrayList<>();
        for (Interval inv : res) {
            if (hole > inv.left+eps && hole < inv.right-eps) {
                invs.add(new Interval(inv.left, hole, inv.leftClosed, false));
                invs.add(new Interval(hole, inv.right, false, inv.rightClosed));
            } else if (isZero(Math.abs(hole-inv.left)) && inv.leftClosed) {
                invs.add(new Interval(inv.left, inv.right, false, inv.rightClosed));
            } else if (isZero(Math.abs(hole-inv.right)) && inv.rightClosed) {
                invs.add(new Interval(inv.left, inv.right, inv.leftClosed, false));
            } else {
                invs.add(inv);
            }
        }
        for (Interval inv : invs) {
            if (isZero(inv.left-inv.right)) {
                if (inv.leftClosed && inv.rightClosed) {
                    nextRange.add(inv);
                }
            }
            else nextRange.add(inv);
        }
        res = nextRange;
        nextRange = new ArrayList<>();
    }

    class Interval {
        double left, right;
        boolean leftClosed, rightClosed;

        public Interval(double left, double right, boolean leftClosed, boolean rightClosed) {
            this.left = left;
            this.right = right;
            this.leftClosed = leftClosed;
            this.rightClosed = rightClosed;
        }
        
        @Override
        public String toString() {
            String lstr = (left == Double.NEGATIVE_INFINITY) ? "-INF" : getRes(left);
            String rstr = (right == Double.POSITIVE_INFINITY) ? "INF" : getRes(right);
            String lBracket = leftClosed ? "[" : "(";
            String rBracket = rightClosed ? "]" : ")";
            return lBracket + lstr + ", " + rstr + rBracket;
        }

        String getRes(double y) {
            if (isZero(y)) return String.format("%.4f", 0.0);
            return String.format("%.4f", y);
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