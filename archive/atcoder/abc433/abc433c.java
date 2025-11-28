import java.io.*;
import java.util.*;

public class abc433c {
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
        io.close();
    }

    /* 核心逻辑 */
    void solve() {
        char[] str = io.next().toCharArray();
        int n = str.length;
        int pt = 0;
        char prevChr, curChar;
        int prevLen = 0, curLen = 0;
        int ans = 0;
        // 分块初始化
        prevChr = '*';
        while (pt < n) {
            curChar = str[pt];
            int np = pt;
            while (np < n && str[np] == curChar) np++;
            curLen = np - pt;
            if (prevLen > 0 && curChar == prevChr + 1) {
                ans += Math.min(prevLen, curLen);
            }
            prevChr = curChar;
            prevLen = curLen;
            pt = np;
        }
        io.println(ans);
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
            while (st == null || !st.hasMoreTokens()) {
                String line = r.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
}