package cn.coisini.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author: xiaoxiang
 * @Description:
 */
public class cs {
    public static void arrangeSequence(char[] strArr, int i) {
        char temp;
        ArrayList<String> list = new ArrayList<>();
        if (strArr == null || i > strArr.length || i < 0) {
            return;
        } else if (i == strArr.length) {
            //将得到的组合存进ArrayList中
            list.add(String.valueOf(strArr));
        } else {
            for (int j = i; j < strArr.length; j++) {
                temp = strArr[j];
                strArr[j] = strArr[i];
                strArr[i] = temp;
                //递归调用
                arrangeSequence(strArr, i + 1);
                temp = strArr[j];
                strArr[j] = strArr[i];
                strArr[i] = temp;
            }
        }
        for (int m = 0; m < list.size(); m++) {
            System.out.println(list.get(m));
        }
    }

    public static void main(String[] args) {
        System.out.println("请输入字符串：");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        str = str.replaceAll(" ", "");
        char strArr[] = str.toCharArray();
        Arrays.sort(strArr);
        arrangeSequence(strArr, 0);
    }
}
