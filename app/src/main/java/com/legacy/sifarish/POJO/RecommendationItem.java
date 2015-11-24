package com.legacy.sifarish.POJO;

/**
 * Created by shivang on 11/23/15.
 */
public class RecommendationItem {
    public String id;
    public String itemId;
    public String itemName;
    public String itemDepartment;
    public String itemShortDescription;
    public String itemMediumImage;
    public String itemRestrictedSalePrice;
    public String itemCategory;
    public String storeId;

    @Override
    public String toString() {
        return "RecommendationItem{" +
                "id='" + id + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemDepartment='" + itemDepartment + '\'' +
                ", itemShortDescription='" + itemShortDescription + '\'' +
                ", itemMediumImage='" + itemMediumImage + '\'' +
                ", itemRestrictedSalePrice='" + itemRestrictedSalePrice + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", storeId='" + storeId + '\'' +
                '}';
    }
}
