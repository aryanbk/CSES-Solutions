import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class RectangleCutting {

    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int a = fs.nextInt();
        int b = fs.nextInt();
        int mx = Math.max(a, b);
        int mn = Math.min(a, b);
        int[][] dp = new int[mx + 1][mx + 1];

        for (int x = 1; x <= mx; ++x) {
            for (int y = 1; y <= x; ++y) { // IMP - Limits
                if (x == y)
                    continue;

                dp[x][y] = 1000000000;
                for (int n = 1; n < x; ++n)
                    dp[x][y] = Math.min(dp[x][y], 1 + dp[n][y] + dp[x - n][y]);
                for (int n = 1; n < y; ++n)
                    dp[x][y] = Math.min(dp[x][y], 1 + dp[x][n] + dp[x][y - n]);
                dp[y][x] = dp[x][y]; // IMP
            }
        }

        out.println(dp[mx][mn]);

        out.close();
    }

    // memo - TLE on 499 500
    //
    // static int solve(int a, int b) {
    // if (a == b)
    // return 0;
    // if (a == 1 || b == 1)
    // return a + b - 2;
    // if (dp[a][b] != null)
    // return dp[a][b];

    // dp[a][b] = 1000000000;
    // for (int i = 1; i < a; ++i) {
    // int la = Math.max(i, b);
    // int lb = Math.min(i, b);
    // int ra = Math.max(a - i, b);
    // int rb = Math.min(a - i, b);
    // dp[a][b] = Math.min(dp[a][b], 1 + solve(la, lb) + solve(ra, rb));
    // }
    // for (int i = 1; i < b; ++i) {
    // int la = Math.max(i, a);
    // int lb = Math.min(i, a);
    // int ra = Math.max(b - i, a);
    // int rb = Math.min(b - i, a);
    // dp[a][b] = Math.min(dp[a][b], 1 + solve(la, lb) + solve(ra, rb));
    // }
    // return dp[a][b];
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