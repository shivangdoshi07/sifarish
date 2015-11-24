package com.legacy.sifarish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.legacy.sifarish.POJO.PurchaseItem;
import com.legacy.sifarish.R;
import com.legacy.sifarish.activity.PurchaseActivity;
import com.legacy.sifarish.util.Constants;

import java.util.ArrayList;

/**
 * Created by shivang on 11/23/15.
 */
public class PurchaseAdapter extends BaseAdapter {

    ArrayList<PurchaseItem> ri;
    Context context;
    private static LayoutInflater inflater=null;

    public PurchaseAdapter(PurchaseActivity mainActivity, ArrayList<PurchaseItem> ri) {
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
    public PurchaseItem getItem(int position) {
        return ri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(ri.get(position).storeId);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder h = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.purchase_list_item, null);

        h.productName = (TextView) rowView.findViewById(R.id.purchasedName);

        h.storeId = (TextView) rowView.findViewById(R.id.purchasedStore);
        h.price = (TextView)rowView.findViewById(R.id.purchasedPrice);

        PurchaseItem recommended = ri.get(position);

        h.productName.setText(recommended.productName);
        h.storeId.setText(Constants.stores[Integer.parseInt(recommended.storeId)-1]);
        h.price.setText("$ "+recommended.price);

        return rowView;
    }

    public class Holder{
        TextView productName;
        TextView storeId;
        TextView price;
    }

}