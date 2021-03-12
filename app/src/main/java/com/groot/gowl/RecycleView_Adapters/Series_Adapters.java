package com.groot.gowl.RecycleView_Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groot.gowl.Activity.SelectActivity_Fiml;
import com.groot.gowl.Activity.SelectActivity_Serials;
import com.groot.gowl.ArraysPage.ArraysPageSerial;
import com.groot.gowl.Exoplayer.ExoActivity;
import com.groot.gowl.Exoplayer.ExoArrays;
import com.groot.gowl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Series_Adapters extends RecyclerView.Adapter<Series_Adapters.SerialsHolder> {

    private ArrayList<ArraysPageSerial> parseItems;
    private Context context;
    private boolean infoQuestion;

    public Series_Adapters(ArrayList<ArraysPageSerial> parseItems, Context context){
        this.parseItems = parseItems;
        this.context = context;

    }


    @NonNull
    @Override
    public Series_Adapters.SerialsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_recycle_sesons_series,parent,false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new SerialsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Series_Adapters.SerialsHolder holder, int position) {
        ArraysPageSerial pageSerial = parseItems.get(position);
        holder.textView.setText(pageSerial.getInfoSeries());
        Picasso.get().load(pageSerial.getImage()).into(holder.imageView);
            holder.itemView.setOnClickListener(view -> {
                if (pageSerial.getSeriesUrl()==null){
                    Toast.makeText(context,"weait" + pageSerial.getSeriesUrl(),Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context,pageSerial.getInfoSeries(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(holder.itemView.getContext(), ExoArrays.class);
                    intent.putExtra("url", pageSerial.getSeriesUrl());
                    intent.putExtra("series",parseItems);
                    context.startActivity(intent);
                }
            });


    }

    @Override
    public int getItemCount() {
        return parseItems.size();
    }



    public class SerialsHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public SerialsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Element_ImageViewStartPage);
            textView = itemView.findViewById(R.id.Element_TextView);
            itemView.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus){
                    Animation animation = AnimationUtils.loadAnimation(context,R.anim.scale_in_tv);
                    itemView.startAnimation(animation);
                    animation.setFillAfter(true);
                }else {
                    // run scale animation and make it smaller
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_tv);
                    itemView.startAnimation(anim);
                    anim.setFillAfter(true);
                }
            });
        }
    }


}
