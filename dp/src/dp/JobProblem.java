package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://www.bilibili.com/video/av16544031?from=search&seid=8163592400684517433
 */
public class JobProblem {

    public static void main(String[] args) {
        List<T> allTs = new ArrayList();
        allTs.add(new T(0, 0, 0, 0)); // 占位
        allTs.add(new T(1, 1, 4, 5));
        allTs.add(new T(2, 3, 5, 1));
        allTs.add(new T(3, 0, 6, 8));
        allTs.add(new T(4, 4, 7, 4));
        allTs.add(new T(5, 3, 8, 6));
        allTs.add(new T(6, 5, 9, 3));
        allTs.add(new T(7, 6, 10, 2));
        allTs.add(new T(8, 8, 11, 4));


        //计算所有的prevs
        int [] prevs = new int[9]; // 从1开始
        for (int i = 8; i > 0; i--) {
            int j = i -1;
            for ( j = i -1 ; j > 0; j--) {
                if (allTs.get(j).end <= allTs.get(i).start) {
                    break;
                }
            }
            prevs[i] = j;
        }

        System.out.println(Arrays.toString(prevs));

        int opts [] = new int[9];
        opts[1] = allTs.get(1).value;
        for (int i = 2; i <= 8; i++) {
            opts[i] = Math.max(opts[i-1], allTs.get(i).value + opts[prevs[i]]);
            System.out.println(String.format("i=%d max opt = %d", i, opts[i]));
        }
        System.out.println(opts[8]);
    }
    static class T {
        public final int id, start, end, value;
        public T(int id, int start, int end, int value) {
            this.id = id;
            this.start = start;
            this.end = end;
            this.value = value;
        }
    }
}
