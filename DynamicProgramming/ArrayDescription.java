import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class ArrayDescription {
    static int[] arr;
    static int n;
    static int m;
    // static Integer[][] dp;
    static int[][] dp;

    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        n = fs.nextInt();
        m = fs.nextInt();

        arr = fs.readArray(n);
        dp = new int[n][m + 2];

        for (int i = n - 1; i >= 0; --i) {
            for (int val = 0; val <= m; ++val) {
                if (i == n - 1)
                    dp[i][val] = ((val >= 1 && val <= m) && (arr[i] == 0 || arr[i] == val)) ? 1 : 0;
                else if (val < 1 || val > m)
                    dp[i][val] = 0;
                else if (arr[i] != 0 && arr[i] != val)
                    dp[i][val] = 0;
                else {
                    dp[i][val] = (dp[i][val] + dp[i + 1][val - 1]) % mod;
                    dp[i][val] = (dp[i][val] + dp[i + 1][val]) % mod;
                    dp[i][val] = (dp[i][val] + dp[i + 1][val + 1]) % mod;
                }
            }
        }

        if (arr[0] != 0)
            out.println(dp[0][arr[0]]);
        else {
            int ans = 0;
            for (int val = 1; val <= m; ++val)
                ans = (ans + dp[0][val]) % mod;
            out.println(ans);
        }

        // memoization code - Stack Over Flow
        //
        // if (arr[0] != 0)
        // out.println(solve(0, arr[0]));
        // else {
        // int ans = 0;
        // for (int val = 1; val <= m; ++val) {
        // ans = (ans + solve(0, val)) % mod;
        // }
        // out.println(ans);
        // }

        out.close();
    }

    // memoization code - Stack Over Flow
    //
    // static int solve(int i, int val) {
    // if (i == n - 1)
    // return ((val >= 1 && val <= m) && (arr[i] == 0 || arr[i] == val)) ? 1 : 0;

    // if (val < 1 || val > m)
    // return 0;

    // if (dp[i][val] != null)
    // return dp[i][val];

    // if (arr[i] != 0 && arr[i] != val) {
    // dp[i][val] = 0;
    // return 0;
    // }

    // dp[i][val] = 0;
    // dp[i][val] = (dp[i][val] + solve(i + 1, val - 1));
    // dp[i][val] = (dp[i][val] + solve(i + 1, val));
    // dp[i][val] = (dp[i][val] + solve(i + 1, val + 1));
    // return dp[i][val];
    // }

    static final Random random = new Random();
    static final int mod = 1_000_000_007;

    static void ruffleSort(int[] a) {
        int n = a.length;// shuffle, then sort
        for (int i = 0; i < n; i++) {
            int oi = random.nextInt(n), temp = a[oi];
            a[oi] = a[i];
            a[i] = temp;
        }
        Arrays.sort(a);
    }

    static long add(long a, long b) {
        return (a + b) % mod;
    }

    static long sub(long a, long b) {
        return ((a - b) % mod + mod) % mod;
    }

    static long mul(long a, long b) {
        return (a * b) % mod;
    }

    static long exp(long base, long exp) {
        if (exp == 0)
            return 1;
        long half = exp(base, exp / 2);
        if (exp % 2 == 0)
            return mul(half, half);
        return mul(half, mul(half, base));
    }

    static long[] factorials = new long[2_000_001];
    static long[] invFactorials = new long[2_000_001];

    static void precompFacts() {
        factorials[0] = invFactorials[0] = 1;
        for (int i = 1; i < factorials.length; i++)
            factorials[i] = mul(factorials[i - 1], i);
        invFactorials[factorials.length - 1] = exp(factorials[factorials.length - 1], mod - 2);
        for (int i = invFactorials.length - 2; i >= 0; i--)
            invFactorials[i] = mul(invFactorials[i + 1], i + 1);
    }

    static long nCk(int n, int k) {
        return mul(factorials[n], mul(invFactorials[k], invFactorials[n - k]));
    }

    static void sort(int[] a) {
        ArrayList<Integer> l = new ArrayList<>();
        for (int i : a)
            l.add(i);
        Collections.sort(l);
        for (int i = 0; i < a.length; i++)
            a[i] = l.get(i);
    }

    static class FastScanner {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer("");

        String next() {
            while (!st.hasMoreTokens())
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        int[] readArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        long nextLong() {
            return Long.parseLong(next());
        }
    }
}