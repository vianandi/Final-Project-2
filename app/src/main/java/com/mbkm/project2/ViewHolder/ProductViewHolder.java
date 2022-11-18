package com.mbkm.project2.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mbkm.project2.Interface.ItemClickListener;
import com.mbkm.project2.R;

import org.w3c.dom.Text;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtProductItemName, txtProductID, txtProductDescription, txtProductQuantity, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listner;


    public ProductViewHolder(View itemView)
    {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductID = (TextView) itemView.findViewById(R.id.product_id);
        txtProductItemName = (TextView) itemView.findViewById(R.id.product_item_name);
//        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductQuantity = (TextView) itemView.findViewById(R.id.product_quantity);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);
    }

    public void setItemClickListner(ItemClickListener listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAbsoluteAdapterPosition(), false);
    }
}