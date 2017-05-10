package com.example.android.inventory;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.ProductContract;

/**
 * Created by rmhuneineh on 10/05/2017.
 */

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.ViewHolder> {

    private Cursor mCursor;
    private CatalogActivity mContext;

    public ProductRecyclerAdapter(CatalogActivity context, Cursor c) {
        this.mContext = context;
        this.mCursor = c;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        protected TextView nameTextView;
        protected TextView priceTextView;
        protected TextView quantityTextView;
        protected ImageView buy;
        protected ImageView productPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.text_product_name);
            priceTextView = (TextView) itemView.findViewById(R.id.text_product_price);
            quantityTextView = (TextView) itemView.findViewById(R.id.text_product_quantity);
            buy = (ImageView) itemView.findViewById(R.id.buy);
            productPicture = (ImageView) itemView.findViewById(R.id.product_image);
        }
    }

    @Override
    public ProductRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        ViewHolder vh = new ViewHolder(listItem);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final long id;
        final int mQuantity;

        id = mCursor.getLong(mCursor.getColumnIndex(ProductContract.ProductEntry._ID));
        int nameColumnIndex = mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int pictureColumnIndex = mCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PICTURE);

        String productName = mCursor.getString(nameColumnIndex);
        String productPrice = mCursor.getString(priceColumnIndex);
        int quantity = mCursor.getInt(quantityColumnIndex);
        String imageUriString = mCursor.getString(pictureColumnIndex);
        Uri imageUri = Uri.parse(imageUriString);

        mQuantity = quantity;

        holder.nameTextView.setText(productName);
        holder.priceTextView.setText(productPrice);
        holder.quantityTextView.setText(String.valueOf(quantity));
        holder.productPicture.setImageURI(imageUri);
        holder.productPicture.invalidate();

        holder.nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.onItemClick(id);
            }
        });

        holder.buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuantity >0 ) {
                    mContext.onBuyClick(id, mQuantity);
                } else {
                    Toast.makeText(mContext, "Quantity Unavailable", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
