package archive.atcoder.abc425;
import java.io.*;
import java.util.*;

public class abc425g {
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
    // 最长数位数
    int maxlen = 29;
    int maxnode = 7000000;
    int[] tr = new int[maxnode * 2];
    long[] memSum = new long[maxnode * 2];
    long[] memCnt = new long[maxnode * 2];
    boolean[] visited = new boolean[maxnode * 2];
    int tot = 0;
    int n, m;
    int[] a;
    void run() {
        // 读入
        n = io.nextInt(); m = io.nextInt();
        a = new int[n];
        for (int i = 0; i < n; i++) a[i] = io.nextInt();
        // 将a数组插入Trie中
        for (int num : a) {
            int u = 0;
            for (int i = maxlen; i >= 0; i--) {
                int bit = (num >> i) & 1;
                int idx = (u << 1) | bit;
                if (tr[idx] == 0) {
                    tr[idx] = ++tot;
                }
                u = tr[idx];
            }
        }
        // dfs
        m--;
        dfs(0, 29, 1);
        io.println(memSum[1]);
    }

    void dfs(int u, int k, int limit) {
        int curIdx = (u << 1) | limit;
        if (visited[curIdx]) {
            return;
        }
        if (k < 0) {
            memSum[curIdx] = 0;
            memCnt[curIdx] = 1;
            visited[curIdx] = true;
            return;
        }
        long totalSum = 0, totalCnt = 0;
        int up = (limit == 1) ? ((m >> k) & 1) : 1;
        for (int d = 0; d <= up; ++d) {
            int next;
            long cost = 0;
            int pathIdx = (u << 1) | d;
            if (tr[pathIdx] != 0) {
                next = tr[pathIdx];
            } else {
                next = tr[(u << 1) | (1-d)];
                cost = (1L << k);
            }
            int nextLimit = (limit == 1 && d == up) ? 1 : 0;
            dfs(next, k-1, nextLimit);
            int childIdx = (next << 1) | nextLimit;
            long childSum = memSum[childIdx];
            long childCnt = memCnt[childIdx];

            totalSum += (cost * childCnt) + childSum;
            totalCnt += childCnt;
        }
        memSum[curIdx] = totalSum;
        memCnt[curIdx] = totalCnt;
        visited[curIdx] = true;
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