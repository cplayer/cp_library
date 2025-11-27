import java.io.*;
import java.util.*;

public class abc433b {
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
        int n = io.nextInt();
        int[] a = io.nextIntArray(n);
        Stack<Integer> stk = new Stack<>();
        for (int i = 0; i < n; i++) {
            while (!stk.isEmpty() && a[stk.peek()] <= a[i]) {
                stk.pop();
            }
            if (stk.isEmpty()) {
                io.println(-1);
                stk.push(i);
            } else {
                io.println(stk.peek()+1);
                stk.push(i);
            }
        }
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