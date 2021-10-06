package com.example.totallyserioussoundapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
private Toolbar toolbar;
private ConstraintLayout constraintLayoutMain;
private AnimationDrawable animationDrawable;
private TabLayout tabLayout;
private ViewPager2 viewPager;
private ArrayList<String> tabLayoutText;
private  ArrayList<Fragment> fragmentArrayList;
private MediaPlayer mediaPlayer;
private Handler handler;

private int splashScreenColorID;
private static WebView webView;
private static final int PERMISSION_REQUEST_CODE = 1;
private SharedPreferences sharedPreferences;
private FragmentManager fm;
private FragmentTransaction ft;
private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        starter();//bağlantı,toolbar ayar,
        permissionCheckStarter();
        viewPager.setCurrentItem(1);
        viewPager.setCurrentItem(0);













    }


    public void starter(){
        toolbar=findViewById(R.id.toolbar);
        constraintLayoutMain=findViewById(R.id.consrainLayoutMain);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);

        webView=findViewById(R.id.webView);
        frameLayout=findViewById(R.id.frameLayout);



        fragmentArrayList=new ArrayList<>();
        fragmentArrayList.add(new FragmentMain());
        fragmentArrayList.add(new FragmentFavori());
        tabLayoutText=new ArrayList<>();
        tabLayoutText.add(getResources().getString(R.string.tablayout_hompage));
        tabLayoutText.add(getResources().getString(R.string.tablayout_fav));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(tabLayout,viewPager,
                (tab,position)->tab.setText(tabLayoutText.get(position))).attach();
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.favori);


        toolbar.setTitle(getResources().getString(R.string.toolbar_maintitle));
        toolbar.setSubtitle("BETA");
        toolbar.setLogo(R.drawable.speaker);
        setSupportActionBar(toolbar);

        animationDrawable=(AnimationDrawable)constraintLayoutMain.getBackground();
        animationDrawable.setExitFadeDuration(3500);
        animationDrawable.setEnterFadeDuration(3500);


    }
    public void permissionCheckStarter(){
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkPermission())
            {
                
            } else {
                requestPermission(); // Code for permission
            }
        }
        else
        {

           
        }



    }

    public static void loadURL(String url){
      webView.loadUrl(url);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch (item.getItemId()){

           case R.id.actionButtonAyarlar:{
               Log.e("Toolbar Options Menu","ayarlar");
              Intent intent = new Intent(MainActivity.this,Activity2.class);
              intent.putExtra("fragment","ayarlar");
              startActivity(intent);
               return true;
           }
           case R.id.ActionButtonBilgi:{
               Log.e("Toolbar Options Menu","bilgi");
               Intent intent = new Intent(MainActivity.this,Activity2.class);
               intent.putExtra("fragment","info");
               startActivity(intent);
               return true;
           }
           default:



       }


        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (animationDrawable!=null&& !animationDrawable.isRunning()){
           animationDrawable.start(); }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable!=null&&animationDrawable.isRunning()){
            animationDrawable.stop();

        }
    }



    public class ViewPagerAdapter extends FragmentStateAdapter{
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return fragmentArrayList.get(position);
        }

        @Override
        public int getItemCount() {
            return fragmentArrayList.size();
        }
    }
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
            aBuilder.setMessage("Depolama işlemlerine izni vermediğiniz için ses paylaşma özelliğini kullanamayabilirsiniz \n İzin vermek istermisiniz");
            aBuilder.setTitle("Paylaşma Ayarları");
            aBuilder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
                }
            });
            aBuilder.setNegativeButton("Hayır",null);
            aBuilder.create().show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
                    aBuilder.setMessage("Depolama işlemlerine izni vermediğiniz için ses paylaşma özelliğini kullanamayabilirsiniz \n Ayarlar\\Uygulama\\İzinler kısmından açabilirsiniz");
                    aBuilder.setTitle("Paylaşma Ayarları");
                    aBuilder.setPositiveButton("Anladım",null);
                    aBuilder.create().show();
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }




}