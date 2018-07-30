package main;

/**
 * Created by Asus- on 2018/7/30.
 * 鸡尾酒排序
 * 优点：可以很好的针对大部分已经排好序的数组，避免过多的遍历数组
 * tips：鸡尾酒排序的元素比较和交换过程是双向的。
 */
public class CocktailSort {

    private static void cocktailSort(int[] arr) {
        if (arr == null && arr.length == 0) {
            return;
        }

        int temp = 0;
        //左边最后一次遍历的位置
        int leftLastIndex = 0;
        //右边最后一次遍历的位置
        int rightLastIndex = arr.length - 1;
        //左边边界
        int leftBorden = 0;
        //右边边界
        int rightBorden = arr.length - 1;

        /**
         * 鸡尾酒排序步骤
         * 1.
         */
        for (int i = 0; i < arr.length / 2 - 1; i++) {
            boolean isSorted = true;
            //从左向右
            for (int j = leftBorden; j < rightBorden; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    isSorted = false;
                    rightLastIndex = j;
                }
            }
            rightBorden = rightLastIndex;
            if (isSorted)
                break;
            //从右向左
            for (int j = rightBorden; j > leftBorden; j--) {
                if (arr[j] < arr[j - 1]) {
                    temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                    isSorted = false;
                    leftLastIndex = j;
                }
            }
            leftBorden = leftLastIndex;
            if (isSorted)
                break;
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 2, 3, 4, 5, 1, 2};
        CocktailSort cocktailSort = new CocktailSort();
        cocktailSort.cocktailSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
