package dp;

/**
 * https://www.bilibili.com/video/av18512769/?spm_id_from=333.788.videocard.0
 * <p>
 * 给定一个数组 看是不是存在选择某些数加起来正好等于某个数
 * <p>
 * <p>
 * 非递归实现
 */
public class ProblemSumEqual2 {
    public static void main(String[] args) {
        int t[] = {3, 34, 4, 12, 5, 2};
        System.out.println(exists(t, 9)); // 3 + 4 + 2
        System.out.println(exists(t, 18)); // 12 + 5
        System.out.println(exists(t, 11)); // 4 + 5 + 2
        System.out.println(exists(t, 27));  // false

    }

    // 不允许选择相邻的数字
    static boolean exists(int[] nums, int target) {
        boolean[][] subset = new boolean[nums.length][target + 1];
        // 第一行 case 1
        for (int i = 0; i < target + 1; i++) {
            subset[0][i] = nums[0] == i;
        }
        // 第一列 case 2
        for (int j = 0; j < nums.length; j++) {
            subset[j][0] = true;
        }
        for (int i = 1; i < nums.length; i++) {
            for (int j = 1; j < target + 1; j++) {
                if (nums[i] > j) {
                    // case 3
                    subset[i][j] = subset[i - 1][j];
                }
                else {
                    subset[i][j] = subset[i - 1][j - nums[i]] /*要了第i个*/
                            || subset[i - 1][j]; // 不要第i个
                }
            }
        }
        return subset[nums.length - 1][target];


    }


}
