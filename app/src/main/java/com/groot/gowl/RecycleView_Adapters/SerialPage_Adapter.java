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
import com.groot.gowl.Activity.SerialActivity;
import com.groot.gowl.ArraysPage.SerialPage_Arrays;
import com.groot.gowl.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SerialPage_Adapter extends RecyclerView.Adapter<SerialPage_Adapter.SerialPage_Holder> {

    private ArrayList<SerialPage_Arrays> pageSerialArrays;
    private Context context;
    private boolean infoQuestion;

    public SerialPage_Adapter(ArrayList<SerialPage_Arrays> pageSerialArrays, Context context) {
        this.pageSerialArrays = pageSerialArrays;
        this.context = context;
    }

    @NonNull
    @Override
    public SerialPage_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_recycle_start_page,parent,false);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        return new SerialPage_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SerialPage_Holder holder, int position) {
        SerialPage_Arrays arrays = pageSerialArrays.get(position);
        holder.textView.setText(arrays.getNameSpace() + " \n " + arrays.getSesons());
        Picasso.get().load(arrays.getImgPage()).into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            infoQuestion = arrays.getUrlPage().contains("seriesss");

            if (infoQuestion||arrays.getUrlPage().contains("cartoonseries")||arrays.getUrlPage().contains("anime-series")){
                Intent intent = new Intent(context, SelectActivity_Serials.class);
                intent.putExtra("url",arrays.getUrlPage());
                intent.putExtra("name", arrays.getNameSpace());
                context.startActivity(intent);
                Toast.makeText(context,arrays.getNameSpace(),Toast.LENGTH_SHORT).show();
            }else if (infoQuestion==false){
                Intent intent = new Intent(context, SelectActivity_Fiml.class);
                intent.putExtra("url",arrays.getUrlPage());
                intent.putExtra("name", arrays.getNameSpace());
                context.startActivity(intent);
            }

//            Intent intent = new Intent(context, SelectActivity_Serials.class);
//            intent.putExtra("url",arrays.getUrlPage());
//            intent.putExtra("name", arrays.getNameSpace());
//            context.startActivity(intent);
//            Toast.makeText(context,arrays.getNameSpace(),Toast.LENGTH_SHORT).show();
        });

    }

    @Override
    public int getItemCount() {
        return pageSerialArrays.size();
    }

    public class SerialPage_Holder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public SerialPage_Holder(@NonNull View itemView) {
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
