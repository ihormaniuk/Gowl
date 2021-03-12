package com.groot.gowl.RecycleView_Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groot.gowl.ArraysPage.ImageArrays_Head;
import com.groot.gowl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter_Head extends RecyclerView.Adapter<ImageAdapter_Head.Image_HeadHolder> {
    private ArrayList<ImageArrays_Head> arrayImage;
    private Context context;

    public ImageAdapter_Head(ArrayList<ImageArrays_Head> arrayImage, Context context) {
        this.arrayImage = arrayImage;
        this.context = context;
    }

    @NonNull
    @Override
    public Image_HeadHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_preview_image_to_select_activity,parent,false);
        return new Image_HeadHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Image_HeadHolder holder, int position) {
        ImageArrays_Head img = arrayImage.get(position);
        Picasso.get().load(img.getUrlImage_Head()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayImage.size();
    }

    public class Image_HeadHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public Image_HeadHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageHead);
        }
    }

}
