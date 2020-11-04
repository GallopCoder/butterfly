package com.ali.retail.common.queue;

public class ByteMap {

    //位图法，数组排序
        public static int[] bitMapSort(int[] arr) {
            // 找出数组中最值
            int max = arr[0];
            int min = max;

            for (int i : arr) {
                if (max < i) {
                    max = i;
                }
                if (min > i) {
                    min = i;
                }
            }
            //初始化位图数组大小
            int temp=0;//用于解决数组有负数的情况
            int[] newArr=null;
            if(min<0){
                temp=0-min;
                newArr = new int[max - min + 1];
            }else{
                newArr = new int[max+1];
                min=0;
            }

            //构建位图
            for(int i:arr){
                newArr[i+temp]++;//算法体现,对应位置1
            }
            // 重新调整arr数组中的元素
            int index = 0;
            for (int i = 0; i < newArr.length; i++) {
                // 位图是1的就输出，对数组排序
                while (newArr[i] > 0) {
                    arr[index] = i + min;
                    index++;
                    newArr[i]--;
                }
            }
            return arr;
   }

        public static void main(String[] args) {
            int[] arr={5,2,3,7,1};
            //int[] arr={-5,2,-3,7,1};
            int[] arrSort=bitMapSort(arr);
            for(int i:arrSort)
                System.out.println(i);
        }

}
