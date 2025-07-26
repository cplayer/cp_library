import java.io.*;
import java.util.*;

public class C_Count_Good_Numbers {
    public static void main(String[] args) {
        Kattio io = new Kattio();
        Test test = new Test();
        test.run(io);
        io.close();
    }

}

class Test {
    void run(Kattio io) {
        int t = io.nextInt();
        while (t > 0) {
            t--;
            long l = io.nextLong(), r = io.nextLong();
            io.println(calc(r) - calc(l-1));
        }
    }

    int[] primes = {2, 3, 5, 7};

    public long calc(long right) {
        long result = 0;
        for (int i = 0; i < (1 << 4); i++) {
            int nums = 1;
            int factor = 1;
            if ((i & 1) > 0) { factor *= 2; nums *= -1; }
            if ((i & 2) > 0) { factor *= 3; nums *= -1; }
            if ((i & 4) > 0) { factor *= 5; nums *= -1; }
            if ((i & 8) > 0) { factor *= 7; nums *= -1; }
            result += nums * (right / factor);
        }
        return result;
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