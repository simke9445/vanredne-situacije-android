package com.etf.pajseri.vanrednesituacije;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventRecycleViewAdapter extends RecyclerView
        .Adapter<EventRecycleViewAdapter
        .EventObjectHolder> {
    private static String LOG_TAG = "EventRecycleViewAdapter";
    private ArrayList<EventObject> mDataset;
    private static MyClickListener myClickListener;

    public static class EventObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView title;
        TextView description;
        TextView manpower;


        public EventObjectHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
            manpower = (TextView) itemView.findViewById(R.id.manpower);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public EventRecycleViewAdapter(ArrayList<EventObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public EventObjectHolder onCreateViewHolder(ViewGroup parent,
                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card_view_row, parent, false);

        EventObjectHolder dataObjectHolder = new EventObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(EventObjectHolder holder, int position) {
        holder.title.setText(mDataset.get(position).getTitle());
        holder.description.setText(mDataset.get(position).getDescription());
        holder.manpower.setText(mDataset.get(position).getManpower());
    }

    public void addItem(EventObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    public EventObject getItem(int position){
        return mDataset.get(position);
    }

    public void clearData() {
        int size = this.mDataset.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                this.mDataset.remove(0);
            }

            this.notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }
}