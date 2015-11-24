package com.legacy.sifarish.POJO;

/**
 * Created by shivang on 11/23/15.
 */
public class BuyItem {
    public Long userId;
    public Long itemId;

    public BuyItem(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }
}
