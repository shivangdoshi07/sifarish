package com.legacy.sifarish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.legacy.sifarish.POJO.RecommendationItem;
import com.legacy.sifarish.R;
import com.legacy.sifarish.activity.RecommendationActivity;
import com.legacy.sifarish.util.ImageDownloaderTask;

import java.util.ArrayList;

/**
 * Created by shivang on 11/23/15.
 */
public class RecommendationAdapter extends BaseAdapter {

    ArrayList<RecommendationItem> ri;
    Context context;
    private static LayoutInflater inflater=null;

    public RecommendationAdapter(RecommendationActivity mainActivity, ArrayList<RecommendationItem> ri) {
        // TODO Auto-generated constructor stub
        this.ri = ri;
        context=mainActivity;

        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ri.size();
    }

    @Override
    public Object getItem(int position) {
        return ri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ri.get(position).itemId);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder h = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.recommend_list_item, null);

        h.itemName = (TextView) rowView.findViewById(R.id.itemName);
        h.itemMediumImage = (ImageView) rowView.findViewById(R.id.itemImage);
        h.itemDepartment = (TextView) rowView.findViewById(R.id.itemDepartment);
        h.itemShortDescription = (TextView)rowView.findViewById(R.id.itemDescription);
        h.itemRestrictedSalePrice = (TextView)rowView.findViewById(R.id.itemPrice);
        h.storeId = (TextView) rowView.findViewById(R.id.itemStoreName);

        RecommendationItem recommended = ri.get(position);

        h.itemName.setText(recommended.itemName);
        h.itemDepartment.setText(recommended.itemDepartment);
        h.itemShortDescription.setText(recommended.itemShortDescription);

        h.itemRestrictedSalePrice.setText(recommended.itemRestrictedSalePrice);
        h.storeId.setText(recommended.storeId);
        new ImageDownloaderTask(h.itemMediumImage).execute(recommended.itemMediumImage);
        h.itemMediumImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        return rowView;
    }

    public class Holder{
        TextView itemName;
        TextView itemDepartment;
        TextView itemShortDescription;
        ImageView itemMediumImage;
        TextView itemRestrictedSalePrice;
        //TextView itemCategory;
        TextView storeId;
    }

}
