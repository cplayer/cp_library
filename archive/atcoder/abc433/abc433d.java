import java.io.*;
import java.util.*;

public class abc433d {
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
        int m = io.nextInt();
        int[] arr = io.nextIntArray(n);
        Map<Integer, Integer> cnt = new HashMap<>();
        long ans = 0;
        for (int k = 1; k <= 10; k++) {
            cnt.clear();
            for (int i = 0; i < n; i++) {
                int len = countLen(arr[i]);
                if (k == len) {
                    int mod = arr[i] % m;
                    if (cnt.containsKey(mod)) {
                        cnt.replace(mod, cnt.get(mod)+1);
                    } else {
                        cnt.put(mod, 1);
                    }
                }
            }
            long pow10 = 1;
            for (int i = 0; i < k; i++) pow10 *= 10;
            for (int i = 0; i < n; i++) {
                long t = ((arr[i] % m) * pow10) % m;
                t = (m-t) % m;
                if (cnt.containsKey((int) t)) ans += cnt.get((int) t);
            }
        }
        io.println(ans);
    }

    int countLen(int value) {
        int res = 0;
        if (value == 0) return 1;
        while (value > 0) {
            res++;
            value /= 10;
        }
        return res;
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