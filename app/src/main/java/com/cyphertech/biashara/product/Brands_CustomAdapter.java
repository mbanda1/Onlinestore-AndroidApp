package com.cyphertech.biashara.product;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyphertech.biashara.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Ravi Tamada on 18/05/16.
 */


public class Brands_CustomAdapter extends RecyclerView.Adapter<Brands_CustomAdapter.ViewHolder> {

    public static final String BRAND_ID = "brandId";
    public static final String BRAND_CAT = "category";
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PRICE = "PRICE";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_SPECIFICATION = "specification";

    private List<Album> developersLists;
    private Context context;

    public Brands_CustomAdapter(List<Album> developersLists, Context applicationContext) {
        this.developersLists = developersLists;
        this.context = applicationContext;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.__album_card, parent, false);
        return new ViewHolder(v);


    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Album developersList = developersLists.get(position);
        holder.name.setText(developersList.getName());
        //price
        Double prc = developersList.getPrice();
        String stringDoubleprice = Double.toString(prc);
        holder.price.setText(stringDoubleprice);

        holder.description.setText(developersList.getProductDescription());
        holder.specification.setText(developersList.getProductSpecification());

      //  Picasso.with(context).load(developersList.getProductImage()).into(holder.image);

        holder.brandsProgress.setVisibility(View.VISIBLE);
        Picasso.with(context).load(developersList.getProductImage()).into(holder.image, new Callback(){
            @Override
            public void onSuccess() {
          holder.brandsProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
          // do nothing
            }
        });

        //Glide.with(context).load(developersList.getProductImage()).into((holder.avatar_url));


        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Album developersList1 = developersLists.get(position);
                Intent skipIntent = new Intent(v.getContext(), SingleBrand_Details.class);

                skipIntent.putExtra(BRAND_ID, developersList1.getBrandId());
                skipIntent.putExtra(BRAND_CAT, developersList1.getBrandCategory());

                skipIntent.putExtra(KEY_NAME, developersList1.getName());
                skipIntent.putExtra(KEY_IMAGE, developersList1.getProductImage());

                Double double_1 = developersList1.getPrice();
                 //String String_double_1 = Double.toString(double_1);
                 skipIntent.putExtra(KEY_PRICE, double_1);
                //  skipIntent.putExtra(String.valueOf(KEY_PRICE), new Double[]{developersList1.getPrice()});

                 skipIntent.putExtra(KEY_DESCRIPTION, developersList1.getProductDescription());
                skipIntent.putExtra(KEY_SPECIFICATION, developersList1.getProductSpecification());



                v.getContext().startActivity(skipIntent);
            }
        });
    }


    @Override

    //return the size of the listItems (developersList)

    public int getItemCount() {
        return developersLists.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;
        private TextView price;
        private TextView description;
        private TextView specification;
        ProgressBar brandsProgress;
        private LinearLayout layout_item;

//
//        final IOSDialog dialog0 = new IOSDialog.Builder(IOSDialogActivity.this)
//                .setTitle("Default IOS bar")
//                .setTitleColorRes(R.color.gray)
//                .build();


        public ViewHolder(View itemView) {
            super(itemView);

            // initialize the View objects

            name = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.product_image);
             price = itemView.findViewById(R.id.price);
             description = itemView.findViewById(R.id.description);
             specification =itemView.findViewById(R.id.specifications);
            layout_item = itemView.findViewById(R.id.layout_item);
            brandsProgress = itemView.findViewById(R.id.brandsProgress);

            //brandsProgress.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"),
                 ///   android.graphics.PorterDuff.Mode.MULTIPLY);
        }
    }
}

