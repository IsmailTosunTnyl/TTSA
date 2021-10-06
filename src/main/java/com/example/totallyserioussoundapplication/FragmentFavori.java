package com.example.totallyserioussoundapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentFavori extends Fragment {
    private RecyclerView recyclerViewFav;
    private static RvAdapterFav rvAdapterFav;
    private static ArrayList<Sounds> soundsArrayListFav;
    private   ArrayList<Sounds> soundsArrayList;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_fragment_favori,container,false);
        starter();

        return view;
    }

    public void starter( ){
        soundsArrayList=FragmentMain.getSoundArrayList();
        listCreaterFav();
        recyclerViewFav=view.findViewById(R.id.rVfav);

        recyclerViewFav.setHasFixedSize(true);
        recyclerViewFav.setLayoutManager(new StaggeredGridLayoutManager
                (3,StaggeredGridLayoutManager.VERTICAL));
        if (soundsArrayListFav != null){
        rvAdapterFav=new RvAdapterFav(getContext(),soundsArrayListFav);
        recyclerViewFav.setAdapter(rvAdapterFav);}



    }
    public void listCreaterFav(){
        soundsArrayListFav=new ArrayList<>();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("fav", getContext().MODE_PRIVATE);

      for (int i =0;i<soundsArrayList.size();i++){

          if (sharedPreferences.getBoolean(soundsArrayList.get(i).getSongName(),false)){
              soundsArrayListFav.add(soundsArrayList.get(i));


          }



      }




    }
  public static void additem(Sounds sound){
        Log.e("fac",sound.getSongName());
        soundsArrayListFav.add(sound);
        rvAdapterFav.notifyItemInserted(0);




  }
  public static void removeitem(int index){

        soundsArrayListFav.remove(index);
      if (soundsArrayListFav!=null){
        rvAdapterFav.notifyItemRemoved(index);

  }}

}