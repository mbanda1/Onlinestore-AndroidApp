package com.cyphertech.biashara.Utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cyphertech.biashara.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Topdeal_CustomAdapter extends RecyclerView.Adapter<Topdeal_CustomAdapter.ViewHolder> {

    public static final String CATEGORY_NAME1 = "name";
    public static final String CATEGORY_IMAGE = "image";
    public static final String the_Id = "000000000";
    public static final String KEY_NAME = "";

    private List<Categories_ItemObject> categories_List;
    private Context context ;
    private Context context1;
    // FragmentManager fragmentManager;

    public interface OnImageClickListener {
        void onImageClick(String imageName);


    }

    private OnImageClickListener onImageClickListener;



    public Topdeal_CustomAdapter(List<Categories_ItemObject> developersLists, Context context, OnImageClickListener onImageClickListener) {
        this.categories_List = developersLists;
        this.context1 = context;
        this.onImageClickListener = onImageClickListener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

         View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_topdeal, parent, false);

         //context1 = parent.getContext();

        return new Topdeal_CustomAdapter.ViewHolder(v);

    }

    @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {



        final Categories_ItemObject developersList = categories_List.get(position);
        holder.name.setText(developersList.getCategoryName());

        Picasso.with(context1).load(developersList.getCategoryPicture()).into(holder.picture);


        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            onImageClickListener.onImageClick(developersList.getCategoryName());



                //final FragmentActivity fragmentBelongActivity = getActivity();
         //FOR FRAGMENT     FragmentManager fragmentManager = context.getSupportFragmentManager();
//               FragmentManager fragmentManager = this.getSupportFragmentManager();
////
//                Fragment rightFragment = fragmentManager.findFragmentById(R.id.fragment_two);
////
//               TextView name = rightFragment.getView().findViewById(R.id.title);
//                ImageView image = rightFragment.getView().findViewById(R.id.product_image);
//                TextView price = rightFragment.getView().findViewById(R.id.price);
//                TextView description = rightFragment.getView().findViewById(R.id.description);
//                TextView specification =rightFragment.getView().findViewById(R.id.specifications);
//
//                name.setText(developersList.getCategoryName());
//                price.setText(developersList.getCategoryName());



                //SingleCategory newFragment = new SingleCategory();
            // FragmentManager fragmentManager = getSupportFragmentManager();
            // fragmentManager.beginTransaction().replace(R.id.single_Deal, newFragment).addToBackStack(null).commit();


//



            }
        });


    }


    @Override
    public int getItemCount() {
        return categories_List.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picture;
        TextView name;
        LinearLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            picture = itemView.findViewById(R.id.category_image);
            name = itemView.findViewById(R.id.category_name);
            relativeLayout = itemView.findViewById(R.id.linearLayout_category);

        }
    }

}

