package com.example.lifestyle_management;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class BreakAdapter extends RecyclerView.Adapter<BreakAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<Breaks_Storage_Model> Breaks_Storage_ModelArrayList;

    // Constructor
    public BreakAdapter(Context context, ArrayList<Breaks_Storage_Model> Breaks_Storage_ModelArrayList) {
        this.context = context;
        this.Breaks_Storage_ModelArrayList = Breaks_Storage_ModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // to set data to textview and imageview of each card layout
        Breaks_Storage_Model model = Breaks_Storage_ModelArrayList.get(position);
        holder.BreakNameTV.setText(model.getBreak_name());
//        holder.BreakTimeTV.setText("" + model.getBreak_time());
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditBreaksPage.class);
                Bundle b = new Bundle();

                //get text for current item
            //put text into a bundle and add to intent
                intent.putExtra("BreakName", model.getBreak_name());

                //get position to carry integer
//                intent.putExtra("position", itemPosition);
//
//                intent.putExtras(b);

                //begin activity
                view.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        return Breaks_Storage_ModelArrayList.size();
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView BreakNameTV;
//        private final TextView BreakTimeTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();


                }
            });
            BreakNameTV = itemView.findViewById(R.id.idTVBreakName);
//            BreakTimeTV = itemView.findViewById(R.id.idTVBreakTime);

        }
    }
}
