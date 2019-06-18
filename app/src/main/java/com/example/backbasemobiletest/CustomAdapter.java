package com.example.backbasemobiletest;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> implements Filterable {

    List<AboutInfo> aboutInfos;
    List<AboutInfo> aboutInfosFiltered;
    private CustomAdapterListener listener;

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        final AboutInfo aboutInfo = aboutInfosFiltered.get(i);
        customViewHolder.onBind(aboutInfo,listener);
        if(i %2 == 1){
            customViewHolder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            customViewHolder.itemView.setBackgroundColor(Color.parseColor("#CACACA"));
        }
    }

    @Override
    public int getItemCount() {
        return aboutInfosFiltered != null ? aboutInfosFiltered.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()){
                    aboutInfosFiltered = aboutInfos;
                } else {
                    List<AboutInfo> filteredList = new ArrayList<>();
                    for(AboutInfo city : aboutInfos){
                        if(city.getName().toLowerCase().startsWith(charString.toLowerCase()))
                        {
                            filteredList.add(city);
                        }
                    }
                    aboutInfosFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = aboutInfosFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(results.values != null){

                aboutInfosFiltered = (ArrayList<AboutInfo>) results.values;
                notifyDataSetChanged();
                }
            }
        };
    }

    public void setDataSet(List<AboutInfo> aboutInfos,CustomAdapterListener listener) {
        this.aboutInfos = aboutInfos;
        aboutInfosFiltered = aboutInfos;
        this.listener = listener;
        notifyDataSetChanged();
    }
}
