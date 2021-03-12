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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groot.gowl.Activity.SelectActivity_Serials;
import com.groot.gowl.ArraysPage.PageSerialsAndSezons;
import com.groot.gowl.Exoplayer.ExoActivity;
import com.groot.gowl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SeriasAdaptersAndSezons extends RecyclerView.Adapter<SeriasAdaptersAndSezons.SisonsHolder> {

    private ArrayList<PageSerialsAndSezons> pageSerialsAndSezons;
    private Context context;
    private String url;

    public SeriasAdaptersAndSezons(ArrayList<PageSerialsAndSezons> pageSerialsAndSezons, Context context) {
        this.pageSerialsAndSezons = pageSerialsAndSezons;
        this.context = context;
        this.url = url;
    }

    @NonNull
    @Override
    public SeriasAdaptersAndSezons.SisonsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_recycle_sesons_series,parent,false);
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        return new SisonsHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriasAdaptersAndSezons.SisonsHolder holder, int position) {
        PageSerialsAndSezons page = pageSerialsAndSezons.get(position);
        holder.textView.setText(page.getInfoSesons());
        Picasso.get().load(page.getImg()).into(holder.imageView);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), SelectActivity_Serials.class);
            intent.putExtra("url",page.getUrlSesons());
            context.startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return pageSerialsAndSezons.size();
    }


    public class SisonsHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        public SisonsHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.Element_ImageViewStartPage);
            textView = itemView.findViewById(R.id.Element_TextView);
            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
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
                }
            });
        }
    }

}