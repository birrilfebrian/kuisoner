package com.mif.moms.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mif.moms.R;
import com.mif.moms.activity.DetailAnakActivity;
import com.mif.moms.activity.TambahAnakActivity;
import com.mif.moms.adapter.AdapterListAnak;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.mif.moms.model.ModelListAnak;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentAnak extends Fragment {
    RecyclerView recyclerView;
    List<ModelListAnak> item;
    AdapterListAnak adapterListAnak;

    RequestQueue requestQueue;
    authdata authdataa;
    ProgressDialog progressDialog;

    String mIdUser;
    ImageView tambah;

    int nomor = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_anak, container, false);
        authdataa = new authdata(getContext());
        requestQueue = Volley.newRequestQueue(getContext());
        progressDialog = new ProgressDialog(getContext());

        recyclerView = v.findViewById(R.id.recyclerAnak);
        mIdUser = authdataa.getId_user();
        tambah = v.findViewById(R.id.tambah_anak);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(), TambahAnakActivity.class);
                startActivity(a);
            }
        });

        loadAnak();
        return v;
//
//        tombol omclick (
//                nomor++;
//
//
//                )
//        getSoal(nomor;
    }

    private void loadAnak(){
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ServerApi.URL_ANAK + "/listanak?id_user=" + mIdUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray data = jsonObject.getJSONArray("data");

                    item = new ArrayList<>();
                    for (int i = 0; i < data.length(); i++)
                    {
                        ModelListAnak modelListAnak = new ModelListAnak();
                        JSONObject datanya = data.getJSONObject(i);
                        modelListAnak.setId_anak(datanya.getString("id_anak"));
                        modelListAnak.setNama_anak(datanya.getString("nama_anak"));
                        modelListAnak.setTanggal_lahir(datanya.getString("tanggal_lahir"));
                        modelListAnak.setFoto_anak(datanya.getString("foto_anak"));
                        item.add(modelListAnak);
                    }
                    adapterListAnak = new AdapterListAnak(getActivity(), item);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapterListAnak);

                    adapterListAnak.setListener(new AdapterListAnak.OnHistoryClickListener() {
                        @Override
                        public void onClick(int position) {
                            ModelListAnak modelListAnak = item.get(position);
                            Intent detail = new Intent(getActivity(), DetailAnakActivity.class);
                            detail.putExtra("id_anak", modelListAnak.getId_anak());
                            startActivity(detail);
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "Data tidak ada", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Data tidak ada", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}