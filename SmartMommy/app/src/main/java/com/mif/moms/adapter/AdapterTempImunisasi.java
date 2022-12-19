package com.mif.moms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.R;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.model.ModelDetailImunisasi;
import com.mif.moms.model.ModelListVaksinTemp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterTempImunisasi extends RecyclerView.Adapter<AdapterTempImunisasi.ViewHolder> {
    List<ModelListVaksinTemp> childArrayList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_vaccine,parent,false);

        return new ViewHolder(view);
    }

    public AdapterTempImunisasi(Context context, List<ModelListVaksinTemp> childArrayList){
        this.context = context;
        this.childArrayList = childArrayList;
//        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ModelListVaksinTemp model = childArrayList.get(position);
        holder.tvImunisasi.setText(model.getjenis_imun());
        holder.strIDAnak = model.getId_anak();
        holder.strUserId = model.getid_user();

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDelete(holder.strIDAnak.trim(), holder.strUserId.trim(), holder.tvImunisasi.getText().toString().trim(), position);
            }
        });
    }

    @Override
    public int getItemCount() {

        return childArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvImunisasi;
        ImageView imageDelete;
        String strIDAnak, strUserId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvImunisasi = itemView.findViewById(R.id.tvValue);
            imageDelete = itemView.findViewById(R.id.imageDelete);
        }
    }

    private void processDelete(String strXIdAnak, String strXUserId, String strXNamaImun, int nPosition) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_SUSTER + "/deleteTemp", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")) {
                        childArrayList.remove(nPosition);
                        notifyItemRemoved(nPosition);
                        notifyItemRangeChanged(nPosition, childArrayList.size());
                        Toast.makeText(context, "" + "Delete Immunization: " + strXNamaImun + " Success", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "" + "Delete Immunization: " + strXNamaImun + " Failed", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    Log.e("e: ", "Error: " + e);
                    Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                String errMessage = "An error occurred. Try a few more moments.";
                if (error instanceof NetworkError){
                    errMessage = "Unable to connect to internet. Please check your connection.";
                } else if (error instanceof AuthFailureError) {
                    errMessage = "Delete failed. Please try again on a few moments.";
                } else if (error instanceof ClientError) {
                    errMessage = "Delete failed. Please try again on a few moments.";
                } else if (error instanceof NoConnectionError) {
                    errMessage = "No internet connection. Please check your connection.";
                } else if (error instanceof TimeoutError) {
                    errMessage = "Connection Time Out. Please check your connection.";
                }
                Log.e("e: ", "" + errMessage);
                Toast.makeText(context, "" + errMessage, Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_anak", strXIdAnak);
                params.put("id_user", strXUserId);
                params.put("nama_imunisasi", strXNamaImun);

                Log.e("data dari param: ", "" + params);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
