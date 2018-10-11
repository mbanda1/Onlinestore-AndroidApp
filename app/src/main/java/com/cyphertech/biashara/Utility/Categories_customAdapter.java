package com.cyphertech.biashara.Utility;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyphertech.biashara.R;
import com.cyphertech.biashara.product.Brands;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Categories_customAdapter extends RecyclerView.Adapter<Categories_customAdapter.ViewHolder> {


    public static final String CATEGORY_NAME = "name";
    public static final String CATEGORY_IMAGE = "image";

    private List<Categories_ItemObject> developersLists;
    private Context context;

    public Categories_customAdapter(List<Categories_ItemObject> developersLists, Context context) {
        this.developersLists = developersLists;
        this.context = context;
    }




    @Override
    public Categories_customAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category, parent, false);
        return new Categories_customAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Categories_customAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Categories_ItemObject developersList = developersLists.get(position);
        holder.name.setText(developersList.getCategoryName());


       holder.catProgress.setVisibility(View.VISIBLE);
        Picasso.with(context).load(developersList.getCategoryPicture()).into(holder.picture, new Callback(){
            @Override
            public void onSuccess() {
          holder.catProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

       // Picasso.with(context).load(developersList.getCategoryPicture()).into(holder.picture);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categories_ItemObject developersList1 = developersLists.get(position);
                Intent skipIntent = new Intent(v.getContext(), Brands.class);

                skipIntent.putExtra(CATEGORY_NAME, developersList1.getCategoryName().trim());
                skipIntent.putExtra(CATEGORY_IMAGE, developersList1.getCategoryPicture().trim());

                v.getContext().startActivity(skipIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return developersLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;
        LinearLayout relativeLayout;
        ProgressBar catProgress;



        public ViewHolder(View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.category_image);
            name = itemView.findViewById(R.id.category_name);
            relativeLayout = itemView.findViewById(R.id.linearLayout_category);
            catProgress= itemView.findViewById(R.id.catProgress);
        }
    }
}

