package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.user.festivalbizerte.Model.ArtistesItem;
import com.user.festivalbizerte.R;

import java.util.List;

public class ArtisteAdapter extends RecyclerView.Adapter<ArtisteAdapter.NewsViewHolder>  {


    private Context mContext;
    private List<ArtistesItem> mData ;
    private List<ArtistesItem> List ;



//    public ProgrameAdapter(Context mContext, List<ProgrameItem> mData, boolean isDar) {
//        this.mContext = mContext;
//        this.mData = mData;
//        this.mDataFiltered = mData;
//    }

    public ArtisteAdapter(Context mContext, List<ArtistesItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.List = mData;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_artiste,viewGroup,false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
//        newsViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
//        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));


        newsViewHolder.date.setText(List.get(position).getJourNum());
        newsViewHolder.prix1.setText(List.get(position).getPrix1());
        newsViewHolder.prix2.setText(List.get(position).getPrix2());
        newsViewHolder.NomArtiste.setText(List.get(position).getNonArtiste());
//        newsViewHolder.imageArtiste.setText(List.get(position).getImage());




    }

    @Override
    public int getItemCount() {
        return List.size();
    }



    public class NewsViewHolder extends RecyclerView.ViewHolder {



        TextView date,prix1,prix2,NomArtiste;
        ImageView imageArtiste;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.date);
            prix1 = itemView.findViewById(R.id.prix1);
            prix2 = itemView.findViewById(R.id.prix2);
            NomArtiste = itemView.findViewById(R.id.NomArtiste);
            imageArtiste = itemView.findViewById(R.id.imageArtiste);






        }






    }
}
