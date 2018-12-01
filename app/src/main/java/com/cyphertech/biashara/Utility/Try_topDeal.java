package com.cyphertech.biashara.Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyphertech.biashara.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class Try_topDeal  extends RecyclerView.Adapter<Try_topDeal.ViewHolder> {


    public interface OnItemClickListener {
        void onItemClick(Categories_ItemObject item);
    }

    private Context context;
    private List<Categories_ItemObject> items;
    private OnItemClickListener listener;

    public Try_topDeal(List<Categories_ItemObject> items, OnItemClickListener listener, Context context) {
        this.items = items;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public Try_topDeal.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_topdeal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(Try_topDeal.ViewHolder holder, int position) {
       // holder.bind(items.get(position), listener);
        Categories_ItemObject developersList = items.get(position);
        holder.name.setText(developersList.getCategoryName());
        Picasso.with(context).load(developersList.getCategoryPicture()).into(holder.picture);

        holder.bind(developersList, listener);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;
        LinearLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.category_name);
            picture = itemView.findViewById(R.id.category_image);
            relativeLayout = itemView.findViewById(R.id.linearLayout_category);
        }

        public void bind(final Categories_ItemObject item, final OnItemClickListener listener) {
          //  name.setText(item.getCategoryName());
         //   Picasso.with(context1).load(item.getCategoryPicture()).into(holder.picture);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
