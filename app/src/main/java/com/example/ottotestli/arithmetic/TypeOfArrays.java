package com.example.ottotestli.arithmetic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by lxx
 * Date : 2019/12/13 15:58
 * Use by
 */
public class TypeOfArrays {

    public static void main(String[] args) {
        int[] ints = new int[]{1, 2, -2147483648};
        int[] ints1 = new int[]{1, 1, 2, 2, 2, 2, 2, 2,};
        int[] ints2 = new int[]{3, 2, 1};
        int[] ints3 = new int[]{1, 2, 3};
        int[] ints4 = new int[]{3, 1, 4, 1, 5};
        int[] ints5 = new int[]{3, 2, 2};
        int[] ints6 = new int[]{5, 5, 5, 5, 5, 5, 5, 1, 2, 2, 5, 3, 5};
//        System.out.println(problem414(ints5));
        problem532(ints4, 2);
    }


    /**
     * 寻找第三大的数
     * 给定一个非空数组，返回此数组中第三大的数。如果不存在，则返回数组中最大的数。要求算法时间复杂度必须是O(n)。
     * <p>
     * 解法关键:确立3个数，依次为最大，第二大，第三大。如果比较是大于并且不大于前一位的情况，默认将当前值给下一位，这个比较好处理;
     * 如果是小于的或者等于的情况，需要再次判断这个值是不是新出现的值(确立3个数时以第一个数作为初始化，便利到后面时需要修改，除非它没有第三大的数。)
     *
     * @param nums
     */
    public static int problem414(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        if (nums.length == 2) {
            return nums[0] > nums[1] ? nums[0] : nums[1];
        }

        int first = nums[0];
        int second = nums[0];
        int third = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > first) {
                third = second;
                second = first;
                first = nums[i];
                System.out.println(first + " " + second + " " + third);
                continue;
            }
            //大于第二个值
            if (nums[i] > second && nums[i] < first) {
                third = second;
                second = nums[i];
                System.out.println(first + " " + second + " " + third);
                continue;
            } else {
                //出现了一个新的值，要重新赋值(这个数未被初始化)
                if (first == second) {
                    second = nums[i];
                    System.out.println(first + " " + second + " " + third);
                    continue;
                }
            }
            if (nums[i] > third && nums[i] < second) {
                third = nums[i];
            } else {
                //出现了一个新值
                if (third == first || third == second) {
                    third = nums[i];
                }
            }
            System.out.println(first + " " + second + " " + third);
        }
        return (first == second || second == third) ? first : third;

//        执行用时 :80 ms, 在所有 java 提交中击败了 5.12% 的用户
//        内存消耗 :39 MB, 在所有 java 提交中击败了 48.62% 的用户
    }

    /**
     * 给定一个范围在  1 ≤ a[i] ≤ n ( n = 数组大小 ) 的 整型数组，数组中的元素一些出现了两次，另一些只出现一次。
     * <p>
     * 找到所有在 [1, n] 范围之间没有出现在数组中的数字。
     * <p>
     * 您能在不使用额外空间且时间复杂度为O(n)的情况下完成这个任务吗? 你可以假定返回的数组不算在额外空间内
     * <p>
     *
     * @param nums
     * @return
     */
    public static List<Integer> problem448(int[] nums) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[Math.abs(nums[i]) - 1] > 0)
                nums[Math.abs(nums[i]) - 1] = -nums[Math.abs(nums[i]) - 1];
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                list.add(i + 1);
                System.out.println(i + 1);
            }
        }
        return list;
    }

    /**
     * 给定一个整数数组 a，其中1 ≤ a[i] ≤ n （n为数组长度）, 其中有些元素出现两次而其他元素出现一次。
     * 找到所有出现两次的元素。
     * 你可以不用到任何额外空间并在O(n)时间复杂度内解决这个问题吗？
     *
     * @param nums
     * @return
     */
    public static List<Integer> problem442(int[] nums) {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[Math.abs(nums[i]) - 1] > 0) {
                nums[Math.abs(nums[i]) - 1] = -nums[Math.abs(nums[i]) - 1];
            } else {
                list.add(Math.abs(nums[i]));
                System.out.print(" " + nums[i]);
            }
        }
        return list;
//        执行用时 :11 ms, 在所有 java 提交中击败了 43.11% 的用户
//        内存消耗 :47.4 MB, 在所有 java 提交中击败了 97.97% 的用户
    }

    /**
     * 给定一个二进制数组， 计算其中最大连续1的个数。
     * 示例 1:
     * <p>
     * 输入: [1,1,0,1,1,1]
     * 输出: 3
     * 解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.
     *
     * @param nums
     */
    public static int problem485(int[] nums) {

        int max = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                count++;
            } else {
                max = Math.max(count, max);
                count = 0;
            }
        }
        max = Math.max(count, max);
        System.out.println(max);
        return max;
//        执行用时 :2 ms, 在所有 java 提交中击败了 100.00% 的用户
//        内存消耗 :39.6 MB, 在所有 java 提交中击败了 92.19% 的用户
    }

    /**
     * 给定一个整数数组和一个整数 k, 你需要在数组里找到不同的 k-diff 数对。这里将 k-diff 数对定义为一个整数对 (i, j),
     * 其中 i 和 j 都是数组中的数字，且两数之差的绝对值是 k.
     * 示例 1:
     * <p>
     * 输入: [3, 1, 4, 1, 5], k = 2
     * 输出: 2
     * 解释: 数组中有两个 2-diff 数对, (1, 3) 和 (3, 5)。
     * 尽管数组中有两个1，但我们只应返回不同的数对的数量。
     * 示例 2:
     * <p>
     * 输入:[1, 2, 3, 4, 5], k = 1
     * 输出: 4
     * 解释: 数组中有四个 1-diff 数对, (1, 2), (2, 3), (3, 4) 和 (4, 5)。
     * 示例 3:
     * <p>
     * 输入: [1, 3, 1, 5, 4], k = 0
     * 输出: 1
     * 解释: 数组中只有一个 0-diff 数对，(1, 1)。
     * <p>
     * 注意:
     * 数对 (i, j) 和数对 (j, i) 被算作同一数对。
     * 数组的长度不超过10,000。
     * 所有输入的整数的范围在 [-1e7, 1e7]。
     *
     * @param nums
     * @param k
     * @return
     */
    public static int problem532(int nums[], int k) {

        if (nums.length < 2) return 0;
        int p = 0;
        int q = p + 1;
        HashMap<Integer, Integer> map = new HashMap<>();
        while (p < nums.length) {
            if (q > nums.length - 1) {
                p++;
                q = p + 1;
                continue;
            }
            System.out.println(+nums[p] + ":" + nums[q]);
            if (Math.abs(nums[p] - nums[q]) == k) {
                if (map.get(nums[p]) == null || (map.get(nums[p]) != q)) {
                    map.put(nums[p], nums[q]);
                    System.out.println("合格:" + nums[p] + ":" + nums[q]);
                }
            }
            q++;
        }
        System.out.println(map.size());
        return map.size();
    }
}
