package com.example.admin.pigfarm.ManageData_Page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.admin.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class Eventdel_Adapter extends RecyclerView.Adapter<Eventdel_Adapter.EventdelViewHolder> {

    private Context Eventdeldata;
    private List<DelEvent_items> delList;
    private OnItemLongClickListener eListener;
    private OnItemClickListener eListener2;
    String event_name;

    public interface OnItemLongClickListener {
        void OnDeleteClick(int position);
    }

    public interface OnItemClickListener {
        void OnEditClick(int pos);
    }


    public void setOnItemClickListener(OnItemLongClickListener listener) {

        eListener = listener;
    }

    public void setItemClickListener(OnItemClickListener listener1){
        eListener2 = listener1;
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


        event_name = delEvent_items.getevent_name();
        String event_recorddate = delEvent_items.getevent_recorddate();
        String detail_id = delEvent_items.getDetail_id();

        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = inputFormat.parse(event_recorddate);
            String outputDateStr = outputFormat.format(date);

            holder.textevent_name.setText("เหตุการณ์ : "+event_name);
            holder.textevent_recorddate.setText("วันที่บันทึกเหตุการณ์ : "+outputDateStr);
            holder.text_detail_id.setText(detail_id);

//            detail_id_adapter = Integer.parseInt(detail_id);

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return delList.size();
    }


    class EventdelViewHolder extends RecyclerView.ViewHolder {
        TextView textevent_name, textevent_recorddate,text_detail_id;

        public EventdelViewHolder(View itemView) {
            super(itemView);

            textevent_name = itemView.findViewById(R.id.textevent_name);
            textevent_recorddate = itemView.findViewById(R.id.textevent_recorddate);
            text_detail_id = itemView.findViewById(R.id.text_detail_id);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (delList != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            eListener.OnDeleteClick(position);
                        }
                    }
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (delList != null) {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            eListener2.OnEditClick(pos);
                        }
                    }
                }
            });
        }
    }
}