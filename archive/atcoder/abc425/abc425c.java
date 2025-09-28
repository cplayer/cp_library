import java.io.*;
import java.util.*;

public class abc425c {
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

    long[] sum;

    long getsum(int l, int r, int[] arr) {
        // io.println("l = " + l + "; r = " + r);
        if (l <= r) {
            if (l == 0) return sum[r];
            else return sum[r] - sum[l-1];
        } else {
            return sum[sum.length-1] - (sum[l-1] - sum[r]);
        }
    }

    void run() {
        int n = io.nextInt(), q = io.nextInt();
        int[] arr = new int[n];
        sum = new long[n];
        for (int i = 0; i < n; i++) arr[i] = io.nextInt();
        for (int i = 0; i < n; i++) 
            if (i == 0) sum[i] = arr[i];
            else sum[i] = arr[i] + sum[i-1];
        int offset = 0;
        while (q > 0) {
            q--;
            int code = io.nextInt();
            if (code == 1) {
                int c = io.nextInt();
                offset = (offset + c) % n;
            } else {
                int lt = io.nextInt()-1, rt = io.nextInt()-1;
                io.println(getsum((lt + offset) % n, (rt + offset) % n, arr));
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


// ### ✅ 针对环形数组求和的正确写法

// 为了实现您的目标，代码可以修改得更清晰、更准确。我们只需要区分 `l <= r` 和 `l > r` 两种情况即可。

// ```java
// /**
//  * 支持环形数组的区间求和。
//  * 1. 如果 l <= r，计算 arr[l] 到 arr[r] 的和。
//  * 2. 如果 l > r，计算 arr[l] 到 arr[n-1] 和 arr[0] 到 arr[r] 的和。
//  *
//  * @param l   区间的左边界（包含）
//  * @param r   区间的右边界（包含）
//  * @param arr 原始数组
//  * @param sum arr 数组对应的前缀和数组
//  * @return long 类型的区间总和
//  */
// long getCircularSum(int l, int r, int[] arr, long[] sum) {
//     // 获取整个数组的总和
//     long totalSum = sum[arr.length - 1];

//     if (l <= r) {
//         // --- 情况1：普通非环形区间 ---
//         if (l == 0) {
//             return sum[r];
//         } else {
//             return sum[r] - sum[l - 1];
//         }
//     } else {
//         // --- 情况2：l > r，环形区间 ---
//         // 中间不包含的部分是从 r+1 到 l-1
//         // 计算这个“gap”的和
//         long gapSum = sum[l - 1] - sum[r];
        
//         // 总和减去 gap 的和，就是环形区间的和
//         return totalSum - gapSum;
//     }
// }
// ```

// 这个版本的代码逻辑非常清晰：

//   * 如果是非环形区间 (`l <= r`)，就使用标准的前缀和公式。
//   * 如果是环形区间 (`l > r`)，就用总和减去中间部分的和。

// 这样就既能实现您的设计意图，又避免了之前版本中的逻辑错误。
