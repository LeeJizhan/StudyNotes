package main;

/**
 * Created by Asus- on 2018/7/21
 * 从一个升序数组中找到两个值，他们相加的和等于目标值，返回两个值的下标,时间复杂度要求为O(n)
 */
public class ArrayFIndTarget {

    private int[] findTarget(int[] numbers, int target) {
        if (numbers == null || numbers.length == 0) {
            return null;
        }
        int[] indexs = new int[2];
        int right = numbers.length - 1;
        int left = 0;
        while (left < right) {
            if (numbers[left] + numbers[right] > target) {
                right--;
            } else if (numbers[left] + numbers[right] < target) {
                left++;
            } else {
                indexs[0] = left;
                indexs[1] = right;
                break;
            }
        }
        return indexs;
    }

    public static void main(String[] args) {
        ArrayFIndTarget arrayFIndTarget = new ArrayFIndTarget();
        int[] arr = {1, 2, 3, 5, 6, 7, 8, 9, 11};
        //int[] arr = {};
        //int target = 20;
        //int target = 3;
        int target = 25;
        int[] indexs = arrayFIndTarget.findTarget(arr, target);
        if (indexs != null) {
            if (indexs[0] == 0 && indexs[1] == 0){
                System.out.println("target not found!");
            }else {
                System.out.println(indexs[0]);
                System.out.println(indexs[1]);
            }
        }
    }
}
