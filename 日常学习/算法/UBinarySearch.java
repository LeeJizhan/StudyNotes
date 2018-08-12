package main.wy;

import java.util.Scanner;

/**
 * Created by Asus- on 2018/8/11.
 * 题目描述：
 *   有n堆果子，每堆有若干个，现有m个果子的询问，判断指定的果子属于那一堆
 * 输入示例：
 * 5
 * 2 7 3 4 9
 * 3
 * 1 25 11
 * 第一行表示有n堆果子
 * 第二行表示每堆分别有几个果子，按递增序列排列
 * 第三行表示有m个关于果子的询问
 * 第四行表示每个询问的果子数
 * 输出示例
 * 1
 * 5
 * 3
 * 每个果子属于哪一行，每个输出一行
 * 解题思路：
 *   可以用一个数组保存前面所有果子的总数，可以得到一个递增的数组
 * 如示例可以得到2 9 12 16 25
 * 找第x个果子属于那一堆就可以简化为查找第一个大于等于x的数的下标
 * 如1，第一个大于等于它的是2，下标是1
 * 25，第一个大于等于它的是25，下标是5
 * 11，第一个大于等于它的是12，下标是3
 * 最终得到结果是
 * 1
 * 5
 * 3
 * 查找第一个大于等于x的数的下标，由于数组是有序的，可以用二分法
 * 代码如下
 */
public class Main2 {

    public static void main(String[] args) {
        int n, m;
        //读入数据
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        int[] a = new int[n];
        int[] index = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
            if (i == 0)
                index[i] = a[i];
            if (i > 0)
                index[i] = index[i - 1] + a[i];
        }
        m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int q = in.nextInt();
            int low = 0;
            int high = index.length - 1;
            while (low < high) {
                int mid = (low + high) / 2;
                if (index[mid] >= q) {
                    high = mid;
                } else if (index[mid] < q) {
                    low = mid + 1;
                }
            }
            if (index[low] >= q)
                System.out.println(low + 1);
        }
    }
}
