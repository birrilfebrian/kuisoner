package com.mif.moms.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mif.moms.R;
import com.mif.moms.activity.PilihAnakRiwayatActivity;
import com.mif.moms.activity.PilihAnakStimulasiActivity;
import com.mif.moms.auth.EditProfilActivity;
import com.mif.moms.configfile.ServerApi;
import com.mif.moms.configfile.authdata;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentLainnya extends Fragment {
    authdata authdataa;
    CircleImageView circleImageView;
    ImageView imageViewEdit;
    String foto;
    TextView kode, nama;
    LinearLayout linearriwayat, linearstimulasi, lineartentang, linearlogout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lainnya, container, false);
        authdataa = new authdata(getContext());
        circleImageView = v.findViewById(R.id.image_akun_lainnya);
        imageViewEdit = v.findViewById(R.id.edit_profil_lainnya);
        nama = v.findViewById(R.id.namasaya);
        kode = v.findViewById(R.id.kodesaya);
        linearriwayat = v.findViewById(R.id.linear_riwayat);
        linearstimulasi = v.findViewById(R.id.linear_stimulasi);
//        lineartentang = v.findViewById(R.id.linear_tentang);
        linearlogout = v.findViewById(R.id.linear_logout);

        nama.setText(authdataa.getNama());
        kode.setText(authdataa.getId_user());
        foto = ServerApi.FotoUser + authdataa.getFoto();
        Picasso.get().load(foto).into(circleImageView);

        imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getActivity(), EditProfilActivity.class);
                startActivity(edit);
            }
        });

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit = new Intent(getActivity(), EditProfilActivity.class);
                startActivity(edit);
            }
        });

        linearlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authdataa.logout();
            }
        });

        linearstimulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent riwayat = new Intent(getActivity(), PilihAnakRiwayatActivity.class);
                riwayat.putExtra("onActivity", "histo_imun");
                startActivity(riwayat);
            }
        });

        linearriwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent riwayat = new Intent(getActivity(), PilihAnakRiwayatActivity.class);
                riwayat.putExtra("onActivity", "histo_kuis");
                startActivity(riwayat);
            }
        });
        return v;
    }
}