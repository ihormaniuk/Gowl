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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groot.gowl.Activity.SelectActivity_Fiml;
import com.groot.gowl.Activity.SelectActivity_Serials;
import com.groot.gowl.Activity.SerialActivity;
import com.groot.gowl.ArraysPage.StartPage_Arrays;
import com.groot.gowl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StartPage_Adapter extends RecyclerView.Adapter<StartPage_Adapter.StartPage_Holder> {

    private ArrayList<StartPage_Arrays> startPage_arrays;
    private Context context;
    private boolean infoQuestion;

    public StartPage_Adapter(ArrayList<StartPage_Arrays> startPage_arrays,Context context) {
        this.startPage_arrays = startPage_arrays;
        this.context = context;
    }

    @NonNull
    @Override
    public StartPage_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_recycle_start_page,parent,false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new StartPage_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StartPage_Holder holder, int position) {
        StartPage_Arrays startPage = startPage_arrays.get(position);
        holder.textView.setText(startPage.getNameSpace() + " \n " + startPage.getSesons());
        Picasso.get().load(startPage.getImgPage()).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            System.out.println(infoQuestion);
            infoQuestion = startPage.getUrlPage().contains("seriesss");
              if (infoQuestion||startPage.getUrlPage().contains("cartoonseries")||startPage.getUrlPage().contains("anime-series")){
                  Intent intent = new Intent(context, SelectActivity_Serials.class);
                  intent.putExtra("url",startPage.getUrlPage());
                  intent.putExtra("name", startPage.getNameSpace());
                  context.startActivity(intent);
                  Toast.makeText(context,startPage.getNameSpace(),Toast.LENGTH_SHORT).show();
              }else if (infoQuestion==false){
                  Intent intent = new Intent(context, SelectActivity_Fiml.class);
                  intent.putExtra("url",startPage.getUrlPage());
                  intent.putExtra("name", startPage.getNameSpace());
                  context.startActivity(intent);
              }

            });
    }

    @Override
    public int getItemCount() {
        return startPage_arrays.size();
    }

    public class StartPage_Holder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        public StartPage_Holder(@NonNull View itemView) {
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
