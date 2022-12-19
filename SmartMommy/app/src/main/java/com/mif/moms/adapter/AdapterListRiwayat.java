package com.mif.moms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mif.moms.R;
import com.mif.moms.model.ModelDetailRiwayat;
import com.mif.moms.model.ModelRiwayat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterListRiwayat extends RecyclerView.Adapter<AdapterListRiwayat.MyViewHolder> {
    ArrayList<ModelRiwayat> parentItem;
    private List<ModelDetailRiwayat> nested = new ArrayList<>();
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_anak, nama_anak, tanggal_lahir, riwayat, text_tips;
        ImageView foto_anak;
        private RelativeLayout expandableLayout;
        private RecyclerView nestedRecyclerView;
        public MyViewHolder(View itemView) {
            super(itemView);
            id_anak = itemView.findViewById(R.id.text_idanak_riwayat);
            nama_anak = itemView.findViewById(R.id.text_namaanak_riwayat);
            tanggal_lahir = itemView.findViewById(R.id.text_tgllahiranak_riwayat);
            riwayat = itemView.findViewById(R.id.text_hasil_riwayat);
            foto_anak = itemView.findViewById(R.id.image_fotoanak_riwayat);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            nestedRecyclerView = itemView.findViewById(R.id.child_rv);
            text_tips = itemView.findViewById(R.id.text_tips);
        }
    }

    public AdapterListRiwayat(Context context, ArrayList<ModelRiwayat> parentItem) {
        this.context = context;
        this.parentItem = parentItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_riwayat, viewGroup, false);
        MyViewHolder holderData = new MyViewHolder((view));
        return holderData;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        ModelRiwayat me = parentItem.get(position);
        holder.id_anak.setText(me.getId_anak());
        holder.nama_anak.setText(me.getNama_anak());
        holder.tanggal_lahir.setText(me.getTanggal_lahir());

//        holder.text_tips.setVisibility(View.VISIBLE);

        if (me.getText_tips().equals("null") || me.getText_tips().equals("")){
            holder.text_tips.setVisibility(View.GONE);
        } else {
            holder.text_tips.setVisibility(View.VISIBLE);
            holder.text_tips.setText("Tips Dokter : " + me.getText_tips());
        }

        if (me.getRiwayat() > 00.8 && me.getRiwayat() <= 1){
            holder.riwayat.setText("Hasil Kuisioner : Anak Sesuai Dengan Tahapan");
        } else if (me.getRiwayat() > 00.6 && me.getRiwayat() <= 00.8){
            holder.riwayat.setText("Hasil Kuisioner : Anak Penyimpangan Meragukan");
        } else if (me.getRiwayat() < 00.6){
            holder.riwayat.setText("Hasil Kuisioner : Kemungkinan Penyimpangan");
        }
        Picasso.get().load(me.getFoto_anak()).into(holder.foto_anak);

        boolean isExpandable = me.isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        AdapterDetailRiwayat adapterMember = new AdapterDetailRiwayat(context, me.getNestedList());
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
