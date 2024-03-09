import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

// Did not get Accepted yet.

public class GridPaths {
    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        String s = fs.next();
        boolean[][] map = new boolean[7][7];
        // map[0][0] = true;
        out.println(bk(0, 0, 0, s, map));

        out.close();
    }

    static int[] d = { 0, 1, 0, -1, 0 };

    static boolean valid(int i, int j) {
        return i >= 0 && j >= 0 && i < 7 && j < 7;
    }

    static int bk(int i, int j, int ptr, String s, boolean[][] map) {
        if (ptr == s.length())
            return i == 6 && j == 0 ? 1 : 0;
        if (i < 0 || i >= 7 || j < 0 || j >= 7 || ptr >= s.length() || map[i][j])
            return 0;
        if (i == 6 && j == 0 && ptr != 48)
            return 0;
        boolean[] visited = new boolean[4];
        for (int k = 0; k < 4; ++k) {
            if (valid(i + d[k], j + d[k + 1]))
                visited[k] = map[i + d[k]][j + d[k + 1]];
            else
                visited[k] = true;
        }

        if (!visited[1] && !visited[3] && visited[0] && visited[3])
            return 0;

        if (visited[1] && visited[3] && !visited[0] && !visited[3])
            return 0;

        if (valid(i - 1, j + 1) && map[i - 1][j + 1])
            if (!visited[0] && !visited[3])
                return 0;
        if (valid(i + 1, j + 1) && map[i + 1][j + 1])
            if (!visited[0] && !visited[1])
                return 0;
        if (valid(i - 1, j - 1) && map[i - 1][j - 1])
            if (!visited[2] && !visited[3])
                return 0;
        if (valid(i + 1, j - 1) && map[i + 1][j - 1])
            if (!visited[2] && !visited[1])
                return 0;

        char c = s.charAt(ptr);
        int ans = 0;

        if (c == '?' || c == 'D') {
            map[i][j] = true;
            ans += bk(i + 1, j, ptr + 1, s, map);
            map[i][j] = false;
        }
        if (c == '?' || c == 'R') {
            map[i][j] = true;
            ans += bk(i, j + 1, ptr + 1, s, map);
            map[i][j] = false;
        }
        if (c == '?' || c == 'U') {
            map[i][j] = true;
            ans += bk(i - 1, j, ptr + 1, s, map);
            map[i][j] = false;
        }
        if (c == '?' || c == 'L') {
            map[i][j] = true;
            ans += bk(i, j - 1, ptr + 1, s, map);
            map[i][j] = false;
        }

        return ans;
    }

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