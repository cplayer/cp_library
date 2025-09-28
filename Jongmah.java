import java.io.*;
import java.util.*;

public class Jongmah {
    public static void main(String[] args) {
        try (Kattio io = new Kattio()) {
            Test test = new Test();
            test.run(io);
        }
    }

}

class Test {
    void run(Kattio io) {
        int n = io.nextInt(), m = io.nextInt();
        int[] arr = new int[m+3];
        for (int i = 0; i < n; i++) arr[io.nextInt()]++;
        int[][][] dp = new int[m+3][3][3];
        for (int i = 0; i <= m; i++) 
            for (int j = 0; j < 3; j++) 
                for (int k = 0; k < 3; k++) dp[i][j][k] = Integer.MIN_VALUE;
        dp[0][0][0] = 0;
        for (int i = 1; i <= m+2; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (arr[i] >= j + k + l) {
                            dp[i][j][k] = Math.max(dp[i][j][k],
                            dp[i-1][k][l] + l + (arr[i] - j - k - l) / 3);
                        }
                    }
                }
            }
        }
        io.println(dp[m+2][0][0]);
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