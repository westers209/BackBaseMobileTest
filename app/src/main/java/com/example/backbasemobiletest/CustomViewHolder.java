package com.example.backbasemobiletest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class CustomViewHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView subtitle;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

    }
    public void onBind(final AboutInfo aboutInfo, final CustomAdapterListener listener){
        title = itemView.findViewById(R.id.title);
        subtitle = itemView.findViewById(R.id.subtitle);
        title.setText(aboutInfo.getName()+" "+aboutInfo.getCountry());
        subtitle.setText(aboutInfo.getCoord().getLat()+","+aboutInfo.getCoord().getLon());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCitySelected(aboutInfo);
            }
        });

    }
}
