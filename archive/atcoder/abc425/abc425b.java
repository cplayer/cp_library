import java.io.*;
import java.util.*;

public class abc425b {
    public static void main(String[] args) {
        try (Kattio io = new Kattio()) {
            Test test = new Test();
            test.run(io);
        }
    }

}

class Test {
    boolean flag = false;
    int[] per;

    void swap (int i, int j) {
        int t = per[j];
        per[j] = per[i];
        per[i] = t;
    }

    void dfs(int k, int n, int[] arr) {
        if (flag) return;
        // System.out.println(Arrays.toString(per));
        if (k == n) {
            handle(arr);
            return;
        }
        for (int i = k; i < n; i++) {
            swap(k, i);
            dfs(k+1, n, arr);
            swap(k, i);
        }
    }

    void handle(int[] arr) {
        // System.out.println("per = " + Arrays.toString(per));
        boolean temp = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != -1 && per[i] != arr[i]) {
                temp = false;
                break;
            }
        }
        if (temp) {
            flag = true;
            System.out.println("Yes");
            for (int i = 0; i < per.length; i++) {
                System.out.print(per[i]);
                if (i < per.length - 1) System.out.print(" ");
            }
            System.out.println();
        }
    }

    void run(Kattio io) {
        int n = io.nextInt();
        int[] arr = new int[n];
        per = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = io.nextInt();
            per[i] = i+1;
        }
        dfs(0, n, arr);
        if (!flag) {
            io.println("No");
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
 * 递归全排列模版
 * import java.util.Scanner;
import java.util.Arrays;

// 在ACM模式中，通常所有代码都在一个Main类里
public class Main {
    static int n;
    static int[] a;

    // 交换数组中的两个元素
    static void swap(int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // 深度优先搜索（回溯）生成排列
    // k 表示当前正在确定第 k 个位置的元素 (数组索引从0开始)
    static void dfs(int k) {
        // --- 递归基：当确定了最后一个位置时 ---
        if (k == n) {
            // 一个完整的排列已生成在数组 a 中
            // 在这里对排列 a 进行处理，例如：
            // 1. 检查是否满足特定条件
            // 2. 计算一个值并更新全局最优解
            // 3. 直接打印（如下所示）
            System.out.println(Arrays.toString(a));
            return;
        }

        // --- 递归体：尝试将后续的每个元素放到第 k 位 ---
        for (int i = k; i < n; i++) {
            swap(k, i);      // 将第 i 个元素换到第 k 位
            dfs(k + 1);    // 递归处理下一个位置 k+1
            swap(k, i);      // 回溯：将数组恢复原状，以便进行下一次交换
        }
    }

    public static void main(String[] args) {
        // ---- 输入处理 ----
        Scanner sc = new Scanner(System.in);
        n = 3; // 假设有3个元素
        a = new int[]{1, 2, 3};
        // 在比赛中，通常这样读输入：
        // n = sc.nextInt();
        // a = new int[n];
        // for (int i = 0; i < n; i++) {
        //     a[i] = sc.nextInt();
        // }
        // ------------------

        // 从第 0 个位置开始生成排列
        dfs(0);
    }
}
 */