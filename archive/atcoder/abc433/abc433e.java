import java.io.*;
import java.util.*;

public class abc433e {
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

    HashSet<Integer> hs = new HashSet<>();
    HashMap<Integer, Integer> hm = new HashMap<>();

    public boolean isDuplicate(int[] arr) {
        hs.clear();
        for (int i = 0; i < arr.length; i++) {
            if (hs.contains(arr[i])) return true;
            hs.add(arr[i]);
        }
        return false;
    }

    /* 核心逻辑 */
    void solve() {
        int t = io.nextInt();
        List<int[]> ls = new ArrayList<>();
        while(t > 0) {
            t--;
            int n = io.nextInt(), m = io.nextInt();
            int[] x = io.nextIntArray(n);
            int[] y = io.nextIntArray(m);
            if (isDuplicate(x) || isDuplicate(y)) {
                io.println("No");
                continue;
            }
            int[][] res = new int[n+1][m+1];
            int[] hshx = new int[n];
            int[] hshy = new int[m];
            int[] hsh = new int[n*m+1];

            // solve duplicate
            hm.clear();
            for (int i = 0; i < m; i++) hm.put(y[i], i+1);
            for (int i = 0; i < n; i++) {
                if (hm.containsKey(x[i])) {
                    res[i+1][hm.get(x[i])] = x[i];
                    hshx[i] = 1;
                    hshy[hm.get(x[i])-1] = 1;
                    hsh[x[i]] = 1;
                }
            }
            // solve other vip
            boolean flag = true;
            List<int[]> vipls = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (hshx[i] == 0) {
                    vipls.add(new int[]{x[i], i});
                }
            }
            vipls.sort((a, b) -> b[0] - a[0]);
            for (int i = 0; i < vipls.size(); i++) {
                int[] tmp = vipls.get(i);
                boolean isPlaced = false;
                for (int j = 0; j < m; j++) {
                    if (tmp[0] < y[j]) {
                        if (res[tmp[1]+1][j+1] == 0) {
                            res[tmp[1]+1][j+1] = tmp[0];
                            hsh[tmp[0]] = 1;
                            isPlaced = true;
                            break;
                        }
                    }
                }
                if (!isPlaced) flag = false;
            }
            vipls.clear();
            for (int i = 0; i < m; i++) {
                if (hshy[i] == 0) {
                    vipls.add(new int[]{y[i], i});
                }
            }
            vipls.sort((a, b) -> b[0] - a[0]);
            for (int i = 0; i < vipls.size(); i++) {
                int[] tmp = vipls.get(i);
                boolean isPlaced = false;
                for (int j = 0; j < n; j++) {
                    if (tmp[0] < x[j]) {
                        if (res[j+1][tmp[1]+1] == 0) {
                            res[j+1][tmp[1]+1] = tmp[0];
                            hsh[tmp[0]] = 1;
                            isPlaced = true;
                            break;
                        }
                    }
                }
                if (!isPlaced) flag = false;
            }
            if (!flag) {
                io.println("No");
                continue;
            }
            ls.clear();
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (res[i][j] == 0)
                        ls.add(new int[]{i, j, Math.min(x[i-1], y[j-1])});
                }
            }
            ls.sort((a, b) -> {
                return Integer.compare(a[2], b[2]);
            });
            int cnt = 0;
            
            for (int i = 0; i < ls.size(); i++) {
                cnt++;
                while (cnt <= n * m && hsh[cnt] > 0) cnt++;
                int[] cur = ls.get(i);
                if (cnt <= cur[2]) res[cur[0]][cur[1]] = cnt;
                else {
                    flag = false;
                    break;
                }
            }
            if (!flag) {
                io.println("No");
            } else {
                io.println("Yes");
                for (int i = 1; i <= n; i++) {
                    for (int j = 1; j <= m; j++) {
                        io.print(res[i][j]);
                        if (j < m) io.print(' ');
                    }
                    io.println();
                }
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