package com.user.festivalbizerte.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.user.festivalbizerte.Model.ProgrameItem;
import com.user.festivalbizerte.R;

import java.util.ArrayList;
import java.util.List;

public class ProgrameAdapter extends RecyclerView.Adapter<ProgrameAdapter.NewsViewHolder> implements Filterable {


    private Context mContext;
    private List<ProgrameItem> mData ;
    private List<ProgrameItem> List ;



//    public ProgrameAdapter(Context mContext, List<ProgrameItem> mData, boolean isDar) {
//        this.mContext = mContext;
//        this.mData = mData;
//        this.mDataFiltered = mData;
//    }

    public ProgrameAdapter(Context mContext, List<ProgrameItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.List = mData;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.item_news,viewGroup,false);
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
        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_scale_animation));



        newsViewHolder.date_jour.setText(List.get(position).getJour());
        newsViewHolder.date_mois.setText(List.get(position).getMois());
        newsViewHolder.Description.setText(List.get(position).getDescription());
        newsViewHolder.Titre.setText(List.get(position).getTitle());
        newsViewHolder.Prix.setText(List.get(position).getPrix());
        newsViewHolder.Horaire.setText(List.get(position).getHoraire());



    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    List = mData ;

                }
                else {
                    List<ProgrameItem> lstFiltered = new ArrayList<>();
                    for (ProgrameItem row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    List = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= List;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                List = (List<ProgrameItem>) results.values;
                notifyDataSetChanged();

            }
        };




    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {



        TextView date_jour,date_mois,Titre,Description,Horaire,Prix;

        LinearLayout container;





        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);

            date_jour = itemView.findViewById(R.id.date_jour);
            date_mois = itemView.findViewById(R.id.date_mois);
            Titre = itemView.findViewById(R.id.Titre);
            Prix = itemView.findViewById(R.id.Prix);
            Description = itemView.findViewById(R.id.Description);
            Horaire = itemView.findViewById(R.id.Horaire);





        }






    }
}
