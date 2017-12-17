package com.dahai.mtest.view.wheelView;

/**
 * Created by 张海洋 on 2017-11-12.
 */

public interface WheelAdapter<T> {
    /**
     *  Gets items count
     * @return the count of wheel items;
     */
    int getItemsCount();

    /**
     *  Gets a wheel item by index.
     * @param index the item index
     * @return the wheel item text or null
     */
    T getItem(int index);

    /**
     * Gets maximum item length. It is used to determine the wheel width.
     * @param o
     * @return the maximum item length or -1
     */
    int indexOf(T o);
}














