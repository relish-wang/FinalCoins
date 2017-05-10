package com.example.aedvance.finalcoins.util.merge;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : 王鑫
 *     e-mail : wangxin@souche.com
 *     time   : 2017/05/10
 *     desc   : 列表合并工具类
 *     version: 1.0
 * </pre>
 */
public class MergeUtil<T> {

    /**
     * 获得两个列表的合并结果
     *
     * @param list1 列表1
     * @param list2 列表2
     * @param aVoid 默认值
     * @return 合并后的列表
     */
    public List<T> mergeTwo(List<T> list1, List<T> list2, final T aVoid) {
        int len1 = list1.size();
        int len2 = list2.size();
        final int max = len1 > len2 ? len1 : len2;
        final List<T> mergedList = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            T t1 = aVoid;
            T t2 = aVoid;
            if (i < len1) {
                t1 = list1.get(i);
            }
            if (i < len2) {
                t2 = list2.get(i);
            }
            T merge = merge(t1, t2, aVoid);
            mergedList.add(merge);
        }
        return mergedList;
    }


    /**
     * 根据t1,t2与空值的关系返回合并结果
     *
     * @param t1    值1
     * @param t2    值2
     * @param aVoid 空值
     * @return 合并结果
     */
    private T merge(T t1, T t2, T aVoid) {
        if (t1 == aVoid) {
            if (t2 == aVoid) {
                return aVoid;
            } else {
                return t2;
            }
        } else {
            if (t2 != aVoid) {
                throw new MergeConflictException("can not merge them: " + t1.toString() + "、" + t2.toString());
            } else {
                return t2;
            }
        }
    }

}
