package com.mif.moms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mif.moms.R;
import com.mif.moms.model.ModelDetailImunisasi;
import com.mif.moms.model.ModelDetailRiwayat;

import java.util.List;

public class AdapterDetailImunisasi extends RecyclerView.Adapter<AdapterDetailImunisasi.ViewHolder> {
    List<ModelDetailImunisasi> childArrayList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detail_imunisasi,parent,false);

        return new ViewHolder(view);
    }

    public AdapterDetailImunisasi(Context context, List<ModelDetailImunisasi> childArrayList){
        this.context = context;
        this.childArrayList = childArrayList;
//        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelDetailImunisasi model = childArrayList.get(position);
        holder.tvImunisasi.setText(model.getmImunisasi());
    }

    @Override
    public int getItemCount() {

        return childArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvImunisasi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvImunisasi = itemView.findViewById(R.id.tvImunisasi);
        }
    }
}
