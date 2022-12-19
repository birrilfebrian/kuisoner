package com.mif.moms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mif.moms.R;
import com.mif.moms.model.ModelListAnak;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterListAnak extends RecyclerView.Adapter<AdapterListAnak.MyViewHolder> {
    private List<ModelListAnak> item;
    private Context context;
    private OnHistoryClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_anak, nama_anak, tanggal_lahir;
        ImageView foto_anak;
        public MyViewHolder(View itemView, final OnHistoryClickListener listener) {
            super(itemView);
            id_anak = itemView.findViewById(R.id.text_idanak);
            nama_anak = itemView.findViewById(R.id.text_namaanak);
            tanggal_lahir = itemView.findViewById(R.id.text_tgllahiranak);
            foto_anak = itemView.findViewById(R.id.image_fotoanak);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onClick(position);
                        }
                    }
                }
            });
        }
    }

    public AdapterListAnak(Context context, List<ModelListAnak> item) {
        this.context = context;
        this.item = item;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_anak, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(layout, listener);
        return myViewHolder;
    }

    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        ModelListAnak me = item.get(position);
        holder.id_anak.setText(me.getId_anak());
        holder.nama_anak.setText(me.getNama_anak());
        holder.tanggal_lahir.setText(me.getTanggal_lahir());
        Picasso.get().load(me.getFoto_anak()).into(holder.foto_anak);
//        holder.foto.setImageResource(R.drawable.ic_chevron_right_primary);
    }

    public int getItemCount() {
        return item.size();
    }

    public interface OnHistoryClickListener {
        public void onClick(int position);
    }

    public void setListener(OnHistoryClickListener listener) {
        this.listener = listener;
    }

    public Filter getFilter() {
        return filterAnak;
    }
    private Filter filterAnak = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelListAnak> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(item);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelListAnak item : item) {
                    if (item.getNama_anak().toLowerCase().contains(filterPattern) || item.getTanggal_lahir().toLowerCase().contains(filterPattern)) {
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            item.clear();
            item.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
