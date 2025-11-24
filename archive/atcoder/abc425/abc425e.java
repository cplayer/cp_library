import java.io.*;
import java.util.*;

public class abc425e {
    public static void main(String[] args) {
        try (Kattio io = new Kattio()) {
            Test test = new Test();
            test.setIO(io);
        }
    }
}

class Test {
    Kattio io;
    void setIO(Kattio innerIO) {
        io = innerIO;
        run();
    }

    void run() {
        int t = io.nextInt(), m = io.nextInt();
        List<Integer> primes = getPrimes(5000);
        // io.println("Primes ended");
        while (t > 0) {
            t--;
            int n = io.nextInt();
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = io.nextInt();
            }
            // io.println("Input Ended");
            io.println(calc(n, m, primes, arr));
        }
        
    }

    List<Integer> getPrimes(int n) {
        List<Integer> primes = new ArrayList<>();
        int[] hsh = new int[n+1];
        for (int i = 2; i <= n; i++) {
            if (hsh[i] == 0) {
                for (int j = 2 * i; j <= n; j += i) {
                    hsh[j] = 1;
                }
            }
        }
        for (int i = 2; i <= n; i++) if (hsh[i] == 0) primes.add(i);
        return primes;
    }

    long calc (int n, int m, List<Integer> primes, int[] arr) {
        // 计算 n! / (arr[0]! * arr[1]! * ... * arr[n-1]!)
        int l = 0;
        for (int a : arr) l += a;
        // fp存的是最终的指数
        int[] fp = new int[primes.size()];
        for (int i = 0; i < primes.size(); i++) {
            fp[i] = calcP(l, primes.get(i));
            for (int j = 0; j < arr.length; j++) {
                fp[i] -= calcP(arr[j], primes.get(i));
            }
        }
        // io.println("fp Ended");
        long ans = 1;
        for (int i = 0; i < fp.length; i++) {
            ans *= calcMod(primes.get(i), fp[i], m);
            ans %= m;
        }
        // io.println("ans Ended");
        return ans;
    }

    // 计算p在n!中的指数
    int calcP (int n, int p) {
        int ans = 0;
        long t = p;
        while (n >= t) {
            ans += (int)(n / t);
            t = t * p;
        }
        return ans;
    }

    // 快速幂计算p^fp % m
    long calcMod (int p, int fp, int m) {
        if (fp == 0) return 1;
        long t = calcMod(p, fp/2, m);
        long res = (t * t) % m;
        if (fp % 2 == 1) res = (res * p) % m;
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
            while (st == null || !st.hasMoreTokens())
                st = new StringTokenizer(r.readLine());
            return st.nextToken();
        } catch (Exception e) {
        }
        return null;
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
}