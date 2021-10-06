package com.example.totallyserioussoundapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.CardViewHolder> implements SeekBar.OnSeekBarChangeListener{
    private Context mContext;
    private List<Sounds> soundsList;
    private MediaPlayer mediaPlayer;
    public Handler handler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;




    public RvAdapter(Context mContext, List<Sounds> soundsList) {
        this.mContext = mContext;
        this.soundsList = soundsList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_designe,parent,false);
        return new CardViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        holder.songName.setText(soundsList.get(position).getSongName());
        holder.imageViewMain.setImageResource(soundsList.get(position).getImageId());


        holder.imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if (mediaPlayer == null)
                      mediaPlayer= MediaPlayer.create(mContext,soundsList.get(position).getSongUri());
                if (!mediaPlayer.isPlaying()){
                    try {


                        mediaPlayer.reset();
                        mediaPlayer= MediaPlayer.create(mContext,soundsList.get(position).getSongUri());


                        mediaPlayer.setLooping(false);
                        mediaPlayer.seekTo(0);
                        holder.imageViewPlay.setImageResource(R.drawable.ic_baseline_pause_circle_outline_24);

                        Snackbar snackbar=Snackbar.make(holder.imageViewPlay,
                                "Şuan  Çalıyor:           --- "+soundsList.get(position).getSongName()+" ---",
                                BaseTransientBottomBar.LENGTH_LONG);
                        snackbar.setBackgroundTint(Color.BLACK);
                        snackbar.setTextColor(Color.parseColor("#FDD406"));
                        snackbar.show();


                        mediaPlayer.start();




                        new Thread(new Runnable() {
                            @Override
                            public void run() {

                                while (mediaPlayer !=null){
                                    try {

                                        Thread.sleep(1000);

                                        if (!mediaPlayer.isPlaying()){

                                            mediaPlayer.seekTo(0);
                                            //seekBarmain.setVisibility(View.INVISIBLE);

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                if (holder.imageViewPlay.getDrawable().getConstantState()!=mContext.getDrawable(R.drawable.ic_baseline_play_circle_outline_24).getConstantState())
                                                 holder.imageViewPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
                                            }else
                                                holder.imageViewPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);


                                        }

                                        //mediaPlayer.this=mediaPlayer;
                                    }catch (Exception e){}

                                }

                                try {


                                }catch (Exception e){}

                            }
                        }).start();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }else {
                    mediaPlayer.pause();
                    holder.imageViewPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);


                }
            }
        });


        holder.imageViewVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar=Snackbar.make(holder.imageViewPlay,
                        "Video Yükleniyor:           --- "+soundsList.get(position).getSongName()+" ---",
                        BaseTransientBottomBar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.BLACK);
                snackbar.setTextColor(Color.parseColor("#FDD406"));
                snackbar.show();
                MainActivity.loadURL(soundsList.get(position).getSongLink());
            }
        });

        holder.imageViewShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try {
                        //Copy file to external ExternalStorage.
                        String mediaPath = copyFiletoExternalStorage(soundsList.get(position).getSoundId(), soundsList.get(position).getSoundId()+".mp3");

                        Intent shareMedia = new Intent(Intent.ACTION_SEND);
                        //set WhatsApp application.
                        shareMedia.setPackage("com.whatsapp");
                        shareMedia.setType("audio/mp3");
                        //set path of media file in ExternalStorage.
                        shareMedia.putExtra(Intent.EXTRA_STREAM, Uri.parse(mediaPath));
                        mContext.startActivity(Intent.createChooser(shareMedia, "Compartiendo archivo."));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "Whatsapp no se encuentra instalado", Toast.LENGTH_LONG).show();
                    }



                }

        });

        sharedPreferences = mContext.getSharedPreferences("fav",mContext.MODE_PRIVATE);
        editor=sharedPreferences.edit();
       holder.toggleButton.setChecked(sharedPreferences.getBoolean(soundsList.get(position).getSongName(),false));
       if (holder.toggleButton.isChecked()){
           holder.toggleButton.setBackground(mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_24));

       }else
           holder.toggleButton.setBackground(mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));

        holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    holder.toggleButton.setBackground(mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    editor.putBoolean(soundsList.get(position).getSongName(),true);
                    editor.commit();

                    FragmentFavori.additem(soundsList.get(position));
                    Toast.makeText(mContext,"Favorilerin güncellenmesi için uygulamayı kapayıp açmanız gerekebilir",Toast.LENGTH_LONG).show();
                }else{


                    holder.toggleButton.setBackground(mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    editor.putBoolean(soundsList.get(position).getSongName(),false);
                    editor.commit();
                    FragmentFavori.removeitem(position);}


            }
        });














    }
    public String copyFiletoExternalStorage(int resourceId, String resourceName){
        String pathSDCard = Environment.getExternalStorageDirectory() + "/Android/data/" + resourceName;
        try{
            InputStream in = mContext.getResources().openRawResource(resourceId);
            FileOutputStream out = null;
            out = new FileOutputStream(pathSDCard);
            byte[] buff = new byte[1024];
            int read = 0;
            try {
                while ((read = in.read(buff)) > 0) {
                    out.write(buff, 0, read);
                }
            } finally {
                in.close();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  pathSDCard;
    }

    @Override
    public int getItemCount() {
        return soundsList.size();
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
   if (fromUser){
       mediaPlayer.seekTo(progress);



   }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView songName;
        public ImageView imageViewPlay,imageViewMain,imageViewVideo,imageViewShare;
        public ToggleButton toggleButton;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            songName=itemView.findViewById(R.id.songName);
            imageViewMain=itemView.findViewById(R.id.imageViewMain);
            imageViewShare=itemView.findViewById(R.id.imageViewShare);
            imageViewVideo=itemView.findViewById(R.id.imageViewVideo);
            imageViewPlay=itemView.findViewById(R.id.imageViewPlay);
            toggleButton=itemView.findViewById(R.id.toggleButton3);






        }
    }

}
