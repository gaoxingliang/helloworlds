package dp;

/**
 * https://www.bilibili.com/video/av18512769/?spm_id_from=333.788.videocard.0
 *
 * 第一题
 */
public class Problem41191 {
    public static void main(String[] args) {
        int t [] = {4, 1, 1, 9, 1};
        System.out.println(maxValues(t));
    }
    // 不允许选择相邻的数字
    static int maxValues(int []nums) {
        int [] sums = new int[nums.length];
        sums[0] = nums[0];
        sums[1] = Math.max(sums[0], sums[1]);
        for (int i = 2; i < nums.length; i++) {
            // 选择第i个就只能选择前i-2个
            // 或者不选择第i个 就是前i-1个
            sums[i] = Math.max(nums[i] + sums[i-2], sums[i-1]);
        }
        return sums[nums.length - 1];
    }
}
