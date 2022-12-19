package com.mif.moms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mif.moms.R;
import com.mif.moms.model.ModelDetailImunisasi;
import com.mif.moms.model.ModelDetailRiwayat;
import com.mif.moms.model.ModelImunisasi;
import com.mif.moms.model.ModelRiwayat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterImunisasi extends RecyclerView.Adapter<AdapterImunisasi.MyViewHolder> {
    ArrayList<ModelImunisasi> parentItem;
    private List<ModelDetailImunisasi> nested = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tgl_imunisasi, berat_anak, tinggi_anak;
        private RelativeLayout expandableLayout;
        private RecyclerView nestedRecyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            tgl_imunisasi = itemView.findViewById(R.id.text_tgl_imunisasi);
            berat_anak = itemView.findViewById(R.id.text_berat_imunisasi);
            tinggi_anak = itemView.findViewById(R.id.text_tinggi_imunisasi);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            nestedRecyclerView = itemView.findViewById(R.id.child_rv);
        }
    }

    public AdapterImunisasi(Context context, ArrayList<ModelImunisasi> parentItem) {
        this.context = context;
        this.parentItem = parentItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_imunisasi, viewGroup, false);
        MyViewHolder holderData = new MyViewHolder((view));
        return holderData;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        ModelImunisasi me = parentItem.get(position);
        holder.tgl_imunisasi.setText("Tanggal Imunisasi : " + me.getTglImunisasi());
        holder.berat_anak.setText("Weigth : " + me.getBeratAnak() + " kg");
        holder.tinggi_anak.setText("Heigth : " + me.getTinggiAnak() + " cm");

        boolean isExpandable = me.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        AdapterDetailImunisasi adapterMember = new AdapterDetailImunisasi(context, me.getNestedList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        holder.nestedRecyclerView.setLayoutManager(linearLayoutManager);
        holder.nestedRecyclerView.setHasFixedSize(true);
        holder.nestedRecyclerView.setAdapter(adapterMember);
        adapterMember.notifyDataSetChanged();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me.setExpandable(!me.isExpandable());
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
//        holder.foto.setImageResource(R.drawable.ic_chevron_right_primary);
    }

    public int getItemCount() {
        return parentItem.size();
    }

    public interface OnHistoryClickListener {
        public void onClick(int position);
    }
}
