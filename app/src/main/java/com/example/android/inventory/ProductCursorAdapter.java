package com.example.android.inventory;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventory.data.ProductContract;

/**
 * Created by rmhuneineh on 16/04/2017.
 */

public class ProductCursorAdapter extends CursorAdapter {

    private CatalogActivity activity = new CatalogActivity();


    public ProductCursorAdapter(CatalogActivity context, Cursor c) {
        super(context, c, 0 /* flags */);
        this.activity = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        final long id;

        final int mQuantity;

        TextView nameTextView = (TextView) view.findViewById(R.id.text_product_name);
        TextView priceTextView = (TextView) view.findViewById(R.id.text_product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.text_product_quantity);
        ImageView buy = (ImageView) view.findViewById(R.id.buy);
        ImageView productPicture = (ImageView) view.findViewById(R.id.product_image);

        id = cursor.getLong(cursor.getColumnIndex(ProductContract.ProductEntry._ID));
        int nameColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_QUANTITY);
        int pictureColumnIndex = cursor.getColumnIndex(ProductContract.ProductEntry.COLUMN_PRODUCT_PICTURE);

        String productName = cursor.getString(nameColumnIndex);
        String productPrice = cursor.getString(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);
        String imageUriString = cursor.getString(pictureColumnIndex);
        Uri imageUri = Uri.parse(imageUriString);

        mQuantity = quantity;

        nameTextView.setText(productName);
        priceTextView.setText(productPrice);
        quantityTextView.setText(String.valueOf(quantity));
        productPicture.setImageURI(imageUri);
        productPicture.invalidate();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onItemClick(id);
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mQuantity >0 ) {
                    activity.onBuyClick(id, mQuantity);
                } else {
                    Toast.makeText(activity, "Quantity Unavailable", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
