package com.mif.moms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mif.moms.R;
import com.mif.moms.model.ModelDetailRiwayat;

import java.util.List;

public class AdapterDetailRiwayat extends RecyclerView.Adapter<AdapterDetailRiwayat.ViewHolder> {
    List<ModelDetailRiwayat> childArrayList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_kuisoner,parent,false);

        return new ViewHolder(view);
    }

    public AdapterDetailRiwayat(Context context, List<ModelDetailRiwayat> childArrayList){
        this.context = context;
        this.childArrayList = childArrayList;
//        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelDetailRiwayat model = childArrayList.get(position);
        holder.tvSoal.setText(model.getmSoal());
        holder.tvJawaban.setText(model.getmJawaban());
        if (model.getmJawaban().trim().equals("1")){
            holder.tvJawaban.setTextColor(Color.parseColor("#00a608"));
            holder.tvJawaban.setText("Ya");
        } else {
            holder.tvJawaban.setTextColor(Color.parseColor("#ff001e"));
            holder.tvJawaban.setText("Tidak");
        }
    }

    @Override
    public int getItemCount() {

        return childArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvSoal, tvJawaban;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSoal = itemView.findViewById(R.id.tvSoal);
            tvJawaban = itemView.findViewById(R.id.tvJawaban);
        }
    }
}
