package dp;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.bilibili.com/video/av18512769/?spm_id_from=333.788.videocard.0
 *
 * 给定一个数组 看是不是存在选择某些数加起来正好等于某个数
 */
public class ProblemSumEqual {
    public static void main(String[] args) {
        int t [] = {3, 34, 4, 12, 5, 2};
        System.out.println(exists(t, 9)); // 3 + 4 + 2
        System.out.println(exists(t, 18)); // 12 + 5
        System.out.println(exists(t, 11)); // 4 + 5 + 2
        System.out.println(exists(t, 27));

    }
    // 不允许选择相邻的数字
    static boolean exists(int []nums, int target) {
        List<Integer> r = new ArrayList<>();
        boolean res =  subset(nums, nums.length - 1, target , r);
        System.out.println(r);
        return res;
    }


    static boolean subset(int [] nums, int i, int s, List<Integer> result) {
        if (i == 0) { // case 1 ->> 到了最后一个元素了
            if ( nums[0] == s) {
                result.add(nums[0]);
                return true;
            } else {
                result.clear();
                return false;
            }
        } else if (s == 0) {  // case 2 ->  当前S为 0
            return true;
        } else if (nums[i] > s) {  // case 3 -> 如果当前数据已经比预期S大了
            return subset(nums, i-1, s, result);
        }

        // case 4
        boolean t1 = subset(nums, i-1, s - nums[i], result);
        if (t1) {
            result.add(nums[i]);
            return t1;
        }
        return subset(nums, i-1, s, result);
    }
}
