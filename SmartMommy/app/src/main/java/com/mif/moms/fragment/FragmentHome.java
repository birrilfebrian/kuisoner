package com.mif.moms.fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.mif.moms.R;
import com.mif.moms.activity.PilihAnakActivity;

public class FragmentHome extends Fragment {
    ConstraintLayout cardkpsp, cardtdd, cardtdl;
    String jns0, jns1, jns2;
    MaterialButton btn_kuesioner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
//        cardkpsp = v.findViewById(R.id.card_KPSP_home);
//        cardtdd = v.findViewById(R.id.card_TDD_home);
//        cardtdl = v.findViewById(R.id.card_TDL_home);
        btn_kuesioner = v.findViewById(R.id.btn_kuesioner);
        jns0 = "0";
        jns1 = "1";
        jns2 = "2";

        btn_kuesioner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kpsp = new Intent(getActivity(), PilihAnakActivity.class);
                kpsp.putExtra("id_jenis", jns0);
                startActivity(kpsp);
            }
        });

//        cardkpsp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent kpsp = new Intent(getActivity(), PilihAnakActivity.class);
//                kpsp.putExtra("id_jenis", jns0);
//                startActivity(kpsp);
//            }
//        });
//
//        cardtdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent tdd = new Intent(getActivity(), PilihAnakActivity.class);
//                tdd.putExtra("id_jenis", jns1);
//                startActivity(tdd);
//            }
//        });
//
//        cardtdl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent tdl = new Intent(getActivity(), PilihAnakActivity.class);
//                tdl.putExtra("id_jenis", jns2);
//                startActivity(tdl);
//            }
//        });
        return v;
    }
}