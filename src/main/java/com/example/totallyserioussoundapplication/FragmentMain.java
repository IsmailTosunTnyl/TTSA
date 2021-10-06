package com.example.totallyserioussoundapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FragmentMain extends Fragment {
    private RecyclerView recyclerView;
    private RvAdapter rvAdapter;
    private static ArrayList<Sounds> soundsArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_fragment_main,container,false);
       starter(view);

        return view;
    }
    private void starter(View view){
        listCreater();
        recyclerView=view.findViewById(R.id.rV);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager
                (3,StaggeredGridLayoutManager.VERTICAL));
        rvAdapter=new RvAdapter(getContext(),soundsArrayList);
        recyclerView.setAdapter(rvAdapter);



    }
    private void listCreater(){
        soundsArrayList=new ArrayList<>();
        soundsArrayList.add(new Sounds("Koçgri Egitilmez",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.kocgriegitilmez),
                "https://youtu.be/UHOKoqD-gog?t=104",R.drawable.kocgri,R.raw.kocgriegitilmez));

        soundsArrayList.add(new Sounds("Koçgri KüfürYabma",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.kocgriiftirakufur),
                "https://www.youtube.com/watch?v=UHOKoqD-gog",R.drawable.kocgri,R.raw.kocgriiftirakufur));

        soundsArrayList.add(new Sounds("Koçgri Zopa",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.kocgri_zopa),
                "https://youtu.be/UHOKoqD-gog?t=16",R.drawable.kocgri,R.raw.kocgri_zopa));

      soundsArrayList.add(new Sounds("Koçgri NeIstiyorsun",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.kocgrineistiyor),
                "https://youtu.be/UHOKoqD-gog?t=90",R.drawable.kocgri,R.raw.kocgrineistiyor));

         soundsArrayList.add(new Sounds("BeterAli Yarraaa",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.beteraliyarrra),
                "https://www.youtube.com/watch?v=cDMcjzf5psM",R.drawable.beterali,R.raw.beteraliyarrra));

        soundsArrayList.add(new Sounds("CJ Ah Shit",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.ahshit),
                "https://www.youtube.com/watch?v=-1qju6V1jLM",R.drawable.ahshit,R.raw.ahshit));

        soundsArrayList.add(new Sounds("Putin Walk",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.putinwalk),
                "https://www.youtube.com/watch?v=tMINTOSZhDo",R.drawable.putinwalk,R.raw.putinwalk));

        soundsArrayList.add(new Sounds("Memati Saka gül",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.sakaguldiye),
                "https://www.youtube.com/watch?v=dxIknRHLlxQ",R.drawable.sakagul,R.raw.sakaguldiye));

        soundsArrayList.add(new Sounds("Taner KafesDövüsü",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.senikafes),
                "https://www.youtube.com/watch?v=Nq_adDon3No",R.drawable.tanerkafas,R.raw.senikafes));

        soundsArrayList.add(new Sounds("Taner KafesDöv. 2",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.onlaridakafes),
                "https://www.youtube.com/watch?v=Nq_adDon3No",R.drawable.tanerkafas,R.raw.onlaridakafes));

        soundsArrayList.add(new Sounds("AKP Dede Hain",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.hain),
                "https://www.youtube.com/watch?v=HVs0rqag52M",R.drawable.akplidede,R.raw.hain));

        soundsArrayList.add(new Sounds("AKP Dede 155",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.polis),
                "https://www.youtube.com/watch?v=HVs0rqag52M",R.drawable.akplidede,R.raw.polis));

        soundsArrayList.add(new Sounds("PingGuy STFU",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.stfu),
                "https://www.youtube.com/watch?v=OLpeX4RRo28",R.drawable.pinkguy,R.raw.stfu));

        soundsArrayList.add(new Sounds("GORA FazlaDikkat",
                Uri.parse("android.resource://"+getActivity().getPackageName()+"/"+R.raw.dikkatcekiyosun),
                "https://www.youtube.com/watch?v=Z-aTqtRBTIM",R.drawable.gora,R.raw.dikkatcekiyosun));





    }
    public static ArrayList<Sounds> getSoundArrayList(){

        return soundsArrayList;
    }



}