/**
 * @author LiGuanglong
 * @date 2018/5/8
 */
public class Main {
    public static void main(String[] args) {
        int[] a = {1, 2, 1, 8, 5, 6, 7, 8, 9, 10};
        int b = LIS(a);
        System.out.println(b);

    }


    static int LIS(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }
        int[] b = new int[A.length];
        b[0] = 1;
        int result = 1;
        for (int i = 1; i < A.length; i++) {
            int max = -1;
            for (int j = 0; j < i; j++) {
                if (A[j] < A[i] && b[j] > max) {
                    max = b[j];
                }
            }
            b[i] = max + 1;
            result = Math.max(result, b[i]);
        }
        return result;
    }
}
