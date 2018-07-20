package main;

/**
 * Created by Asus- on 2018/7/20.
 * 题意描述：
 * 字符数组循环右移k位，k可以大于，小于或者等于数组的长度n
 * 解题思路：
 * 1）当k=dn（d=0，1，2，3...n）时，右移之后的字符位置不变
 * 2）当k<n时，右移 k % n 位
 * 3）当k>n时，由于1）的条件，可知右移 k % n位
 * 4）总结当k=dn，直接return；当k!=dn，右移 k % n位
 */
public class CharArrayMove {

    public void moveArray(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return;
        }
        int n = arr.length;
        int count = k % arr.length;
        if (count == 0)
            return;
        //辅助数组
        int[] temp = new int[n];
        for(int i=0;i<n;i++){
            temp[i] = arr[i];
        }
        for (int i = 0; i < n; i++) {
            arr[(i + count) % n] = temp[i];
        }
    }

    public static void main(String[] args){
        CharArrayMove move = new CharArrayMove();
        int[] a = {1,2,3,4,5,6};
        move.moveArray(a,3);
        for(int i : a){
            System.out.println(i);
        }
    }
}
