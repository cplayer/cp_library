import java.io.*;
import java.util.*;

public class abc425d {
    public static void main(String[] args) {
        try (Kattio io = new Kattio()) {
            Test test = new Test();
            test.setIO(io);
        }
    }
}

class Pair {
    int x, y;

    Pair(int _x, int _y) {
        this.x = _x;
        this.y = _y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        Pair _pair = (Pair) obj;
        return this.x == _pair.x && this.y == _pair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

class Test {
    Kattio io;
    int maxn = 300005;

    void setIO(Kattio innerIO) {
        io = innerIO;
        run();
    }

    void run() {
        int h = io.nextInt(), w = io.nextInt();
        Set<Pair> blackSet = new HashSet<>();
        Queue<Pair> newBlackPairs = new ArrayDeque<>();
        List<Pair> tempBlackPairs = new ArrayList<>();
        int[] offsetx = { -1, 1, 0, 0 };
        int[] offsety = { 0, 0, -1, 1 };
        // 建图
        int[][] mp = new int[h][w];
        for (int i = 0; i < h; i++) {
            String t = io.next();
            for (int j = 0; j < t.length(); j++) {
                if (t.charAt(j) == '.') {
                    mp[i][j] = 0;
                } else {
                    mp[i][j] = 1;
                    blackSet.add(new Pair(i, j));
                    newBlackPairs.add(new Pair(i, j));
                }
            }
        }
        while (!newBlackPairs.isEmpty()) {
            Set<Pair> candidates = new HashSet<>();
            // 1. 遍历所有黑点，找出所有相邻的白点，并统计它们的黑邻居数
            while (!newBlackPairs.isEmpty()) {
                Pair pt = newBlackPairs.poll();
                for (int i = 0; i < 4; i++) {
                    int nx = pt.x + offsetx[i];
                    int ny = pt.y + offsety[i];

                    if (nx < h && nx >= 0 && ny < w && ny >= 0 && mp[nx][ny] != 1) {
                        Pair candidate = new Pair(nx, ny);
                        candidates.add(candidate);
                    }
                }
            }
            
            // 2. 遍历所有候选者，只把黑邻居数量恰好为 1 的加入下一轮的 newBlackSet
            for (Pair candidate : candidates) {
                int count = 0;
                for (int i = 0; i < 4; i++) {
                    int nx = candidate.x + offsetx[i];
                    int ny = candidate.y + offsety[i];
                    if (nx < h && nx >= 0 && ny < w && ny >= 0 && mp[nx][ny] == 1) {
                        count++;
                    }
                }
                if (count == 1) {
                    tempBlackPairs.add(candidate);
                }
            }
            if (candidates.isEmpty()) break;

            for (Pair tempPair : tempBlackPairs) {
                mp[tempPair.x][tempPair.y] = 1;
                blackSet.add(tempPair);
                newBlackPairs.add(tempPair);
            }
            tempBlackPairs.clear();
        }
        io.println(blackSet.size());
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

/**
 * 修正点：
 * 1. 获取新增黑点的时候只用上一轮更新的那一批，但是检查黑点数量则用整体集合；
 * 2. 更新地图和黑点集合统一操作
 */