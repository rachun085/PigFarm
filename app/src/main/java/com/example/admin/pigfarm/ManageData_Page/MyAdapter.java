package com.example.admin.pigfarm.ManageData_Page;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.RecyclerVH>{

    Context c;
    String[] noid;

    public MyAdapter(Context c, String[] noid){
        this.c = c;
        this.noid = noid;
    }

    @NonNull
    @Override
    public RecyclerVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(c).inflate(R.layout.list_item_datamanage, parent,false);
        return new RecyclerVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVH holder, int position) {
        holder.textnumberid.setText(noid[position]);
    }

    @Override
    public int getItemCount() {
        return noid.length;
    }



    public class RecyclerVH extends RecyclerView.ViewHolder{
        TextView textnumberid;

        public RecyclerVH(View itemView){
            super(itemView);

            textnumberid = itemView.findViewById(R.id.textnumberid);

        }
    }
}
