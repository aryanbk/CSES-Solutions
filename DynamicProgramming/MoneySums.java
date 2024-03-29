import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class MoneySums {

    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();
        int sumt = 0;
        int[] coins = new int[n];
        for (int i = 0; i < n; ++i) {
            coins[i] = fs.nextInt();
            sumt += coins[i];
        }

        boolean[] dp = new boolean[sumt + 1]; // reachable
        dp[0] = true;

        for (int i = 0; i < n; ++i) {
            for (int j = sumt; j >= coins[i]; --j) {
                dp[j] |= dp[j - coins[i]];
            }
        }

        int ans = 0;

        for (int i = 1; i < sumt + 1; ++i)
            if (dp[i])
                ++ans;
        out.println(ans);
        for (int i = 1; i < sumt + 1; ++i)
            if (dp[i])
                out.print(i + " ");

        out.close();
    }

    // Second attempt
    //
    // wont solve TLE in 1 testcase
    //
    // static int[] coins;
    // static SortedSet<Integer> ans;
    // static int[][] dp;

    // public static void main(String[] args) {
    // FastScanner fs = new FastScanner();
    // PrintWriter out = new PrintWriter(System.out);

    // int n = fs.nextInt();
    // coins = fs.readArray(n);
    // ans = new TreeSet<>();

    // ans.add(0);
    // solve(0);

    // out.println(ans.size() - 1);
    // for (int a : ans)
    // if (a != 0)
    // out.print(a + " ");

    // out.close();
    // }

    // static void solve(int i) {
    // if (i == coins.length)
    // return;
    // HashSet<Integer> cur = new HashSet<>();
    // for (int a : ans)
    // cur.add(a + coins[i]);
    // for (int a : cur)
    // ans.add(a);
    // solve(i + 1);
    // }

    // First attempt
    //
    // wont work TLE
    //
    // static void solve(int n, int sumt) {
    // if (n == 0) {
    // if (sumt == 0)
    // return;
    // ans.add(sumt);
    // return;
    // }
    // solve(n - 1, sumt + coins[n - 1]);
    // solve(n - 1, sumt);
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