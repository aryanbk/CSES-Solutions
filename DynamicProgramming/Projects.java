import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.StringTokenizer;

public class Projects {
    public static void main(String[] args) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();
        int[][] time = new int[n][3];
        for (int i = 0; i < n; ++i)
            time[i] = fs.readArray(3);

        out.println(jobScheduling(time));

        out.close();
    }

    public static int jobScheduling(int[][] time) {
        int n = time.length;

        Arrays.sort(time, (a, b) -> {
            return a[1] == b[1] ? b[0] - a[0] : a[1] - b[1];
        }); // sort based on end time

        int[] end = new int[n];
        int[] profit = new int[n];
        for (int i = 0; i < n; ++i) {
            end[i] = time[i][1];
            profit[i] = time[i][2];
        } // made copy of end and profite for BS and ST

        int ans = profit[0];
        SegmentTree seg = new SegmentTree(profit);

        for (int i = 1; i < n; ++i) {
            int idx = bs(end, 0, i - 1, time[i][0] - 1);
            int mx = seg.query(0, idx);
            profit[i] += mx;
            seg.update(i, profit[i]);
            ans = Math.max(ans, profit[i]);
        }
        return ans;
    }

    static int bs(int[] nums, int i, int j, int x) {
        int ans = -1;
        while (i <= j) {
            int mid = i + (j - i) / 2;
            if (nums[mid] <= x) {
                ans = mid;
                i = mid + 1;
            } else
                j = mid - 1;
        }
        return ans;
    }

    static class SegmentTree {
        int[] tree;
        int n;

        public SegmentTree(int[] nums) {
            n = nums.length;
            int height = (int) (Math.ceil(Math.log(n) / Math.log(2)));
            int mxSize = 2 * (int) Math.pow(2, height) - 1;
            tree = new int[mxSize];
            buildTree(nums, 0, 0, n - 1);
        }
        // or we can use 4*n as size
        // tree = new int[4 * n];

        private void buildTree(int[] nums, int index, int start, int end) {
            if (start == end) {
                tree[index] = nums[start];
                return;
            }

            int mid = start + (end - start) / 2;
            buildTree(nums, 2 * index + 1, start, mid);
            buildTree(nums, 2 * index + 2, mid + 1, end);
            tree[index] = Math.max(tree[2 * index + 1], tree[2 * index + 2]);
        }

        public int query(int left, int right) {
            if (right < left)
                return 0;
            return queryHelper(0, 0, n - 1, left, right);
        }

        private int queryHelper(int index, int start, int end, int left, int right) {
            if (start > right || end < left) {
                return Integer.MIN_VALUE; // Out of range
            }

            if (start >= left && end <= right) {
                return tree[index]; // Current segment is completely within the range
            }

            int mid = start + (end - start) / 2;
            int leftMax = queryHelper(2 * index + 1, start, mid, left, right);
            int rightMax = queryHelper(2 * index + 2, mid + 1, end, left, right);

            return Math.max(leftMax, rightMax);
        }

        public void update(int index, int val) {
            updateHelper(0, 0, n - 1, index, val);
        }

        private void updateHelper(int index, int start, int end, int idx, int val) {
            if (start == end) {
                tree[index] = val;
                return;
            }

            int mid = start + (end - start) / 2;
            if (idx <= mid) {
                updateHelper(2 * index + 1, start, mid, idx, val);
            } else {
                updateHelper(2 * index + 2, mid + 1, end, idx, val);
            }

            tree[index] = Math.max(tree[2 * index + 1], tree[2 * index + 2]);
        }
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