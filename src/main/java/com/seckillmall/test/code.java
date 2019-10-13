package com.seckillmall.test;

public class code {
    public static void main(String[] args) {
        int[] arr = {4,8,6,12,16,14,10};
        int[] arr2 = {5,7,6,9,11,10,8};
        int[] arr3 = {7,4,6,5};
        int len = arr3.length;
        System.out.println(helper(arr3, len-1));
    }

    public static boolean helper(int[] arr, int root){
        if(arr == null || root < 0){
            return false;
        }
        int step=0;
        for(;step<root;step++){
            if(arr[step] > arr[root]){
                break;
            }
        }
        for(int j=step;j<root;j++){
            if(arr[j] <= arr[root]){
                return false;
            }
        }
        boolean left = true;
        if(step>0){
            left = helper(arr, step-1);
        }
        boolean right = true;
        if(step<root){
            right = helper(arr, root-1);
        }
        return left && right;
    }
}
