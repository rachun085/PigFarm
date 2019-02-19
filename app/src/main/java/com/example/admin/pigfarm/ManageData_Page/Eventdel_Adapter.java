package com.example.admin.pigfarm.ManageData_Page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.admin.R;

import java.util.List;

public class Eventdel_Adapter extends RecyclerView.Adapter<Eventdel_Adapter.EventdelViewHolder> {

    private Context Eventdeldata;
    private List<DelEvent_items> delList;
    private OnItemLongClickListener eListener;

    public interface OnItemLongClickListener {
        void OnDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemLongClickListener listener) {

        eListener = listener;
    }

    public Eventdel_Adapter(Context Eventdeldata, List<DelEvent_items> delList) {
        this.Eventdeldata = Eventdeldata;
        this.delList = delList;
    }

    @NonNull
    @Override
    public Eventdel_Adapter.EventdelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(Eventdeldata);
        View view = inflater.inflate(R.layout.list_item_eventtodel, null);
        return new Eventdel_Adapter.EventdelViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Eventdel_Adapter.EventdelViewHolder holder, int position) {
        DelEvent_items delEvent_items = delList.get(position);

        String event_name = delEvent_items.getevent_name();
        String event_recorddate = delEvent_items.getevent_recorddate();

        holder.textevent_name.setText("เหตุการณ์ : "+event_name);
        holder.textevent_recorddate.setText("วันที่บันทึก : "+event_recorddate);

    }

    @Override
    public int getItemCount() {
        return delList.size();
    }

    class EventdelViewHolder extends RecyclerView.ViewHolder {
        TextView textevent_name, textevent_recorddate;

        public EventdelViewHolder(View itemView) {
            super(itemView);

            textevent_name = itemView.findViewById(R.id.textevent_name);
            textevent_recorddate = itemView.findViewById(R.id.textevent_recorddate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (delList != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            eListener.OnDeleteClick(position);

                        }
                    }
                }
            });
        }
    }
}