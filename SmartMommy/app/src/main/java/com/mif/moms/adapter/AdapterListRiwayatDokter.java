package com.mif.moms.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.model.ModelDetailRiwayat;
import com.mif.moms.model.ModelListAnak;
import com.mif.moms.model.ModelRiwayat;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterListRiwayatDokter extends RecyclerView.Adapter<AdapterListRiwayatDokter.MyViewHolder> {
    ArrayList<ModelRiwayat> parentItem;
    private List<ModelDetailRiwayat> nested = new ArrayList<>();
    private Context context;
    AlertDialog alertDialogEdit;
    String strTips;
    TextInputEditText txtEdtActualKg, txtEdtActualYard, txtEdtLayer, txtEdtRemark, txtEdtMergeRoll, txtEdtRejectRoll;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id_anak, nama_anak, tanggal_lahir, riwayat, text_tips;
        ImageView foto_anak, imgAddTips;
        String id_hasil;
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
            imgAddTips = itemView.findViewById(R.id.imgAddTips);
        }
    }

    public AdapterListRiwayatDokter(Context context, ArrayList<ModelRiwayat> parentItem) {
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
        holder.id_hasil = me.getId_hasil();

//        holder.text_tips.setVisibility(View.VISIBLE);

        if (me.getText_tips().equals("null") || me.getText_tips().equals("")){
            holder.text_tips.setVisibility(View.GONE);
            holder.imgAddTips.setVisibility(View.VISIBLE);
            holder.imgAddTips.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
                    View view = LayoutInflater.from(v.getContext()).inflate(
                            R.layout.layout_insert_row,
                            (ConstraintLayout)v.findViewById(R.id.layoutInsertContainer)
                    );
                    builder.setView(view);
                    ((TextView) view.findViewById(R.id.textTitle)).setText("Add Tips");
                    ((TextView) view.findViewById(R.id.buttonSubmit)).setText("Submit");
                    ((TextView) view.findViewById(R.id.buttonCancel)).setText("Cancel");

                    txtEdtRemark = view.findViewById(R.id.edtInsertText);

                    alertDialogEdit = builder.create();
                    alertDialogEdit.setCancelable(false);

                    view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            strTips = txtEdtRemark.getText().toString();

                            UpdateTips(holder.id_hasil,strTips);
                            holder.text_tips.requestLayout();
//                        alertDialogEdit.dismiss();
                        }
                    });

                    view.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialogEdit.dismiss();
                        }
                    });

                    if (alertDialogEdit.getWindow() != null){
                        alertDialogEdit.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    alertDialogEdit.show();
                }
            });
        } else {
            holder.text_tips.setVisibility(View.VISIBLE);
            holder.imgAddTips.setVisibility(View.GONE);
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

    private void UpdateTips(String id_hasil, String tipsdokter){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DOKTER + "/insertTips", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")){
                        alertDialogEdit.dismiss();
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    } else {
                        alertDialogEdit.dismiss();
                        Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("Error Adapter Spreading", e.toString());
//                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Adapter Spreading", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_hasil", id_hasil);
                params.put("tipsdokter", tipsdokter);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public int getItemCount() {
        return parentItem.size();
    }

    public interface OnHistoryClickListener {
        public void onClick(int position);
    }

    public Filter getFilter() {
        return filterRiwayat;
    }
    private Filter filterRiwayat = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ModelRiwayat> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(parentItem);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ModelRiwayat item : parentItem) {
                    if (item.getId_hasil().toLowerCase().contains(filterPattern) || item.getNama_anak().toLowerCase().contains(filterPattern) || item.getTanggal_lahir().toLowerCase().contains(filterPattern)) {
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
            parentItem.clear();
            parentItem.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
