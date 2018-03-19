package com.expansion.lg.kimaru.training.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.expansion.lg.kimaru.training.R;
import com.expansion.lg.kimaru.training.database.DatabaseHelper;
import com.expansion.lg.kimaru.training.network.JsonParser;
import com.expansion.lg.kimaru.training.network.TrainingDataSync;
import com.expansion.lg.kimaru.training.objs.User;
import com.expansion.lg.kimaru.training.receivers.ConnectivityReceiver;
import com.expansion.lg.kimaru.training.receivers.TremapApp;
import com.expansion.lg.kimaru.training.utils.CircleTransform;
import com.expansion.lg.kimaru.training.utils.SessionManagement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;

public class SplashScreen extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    String now_playing, earned;
    ImageView imageView;
    Animation aniRotateClk;
    boolean canProceed = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        imageView = (ImageView) findViewById(R.id.imageView);
        Glide.with(this).load(getImage("lg_bg"))
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        aniRotateClk = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);
        aniRotateClk.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                new TrainingDataSync(getApplicationContext()).syncSessionTopics();
                new PrefetchData().execute();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (canProceed){
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    i.putExtra("now_playing", "Kimaru");
                    i.putExtra("earned", "millions");
                    startActivity(i);
                }else{
                    animateLogo();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(aniRotateClk);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                canProceed = true;
            }
        }, 6000);

    }

    private void checkConnection(){
        boolean isConnected = ConnectivityReceiver.isConnected();
    }

    private class PrefetchData extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
			/*
			 * Will make http call here This call will download required data
			 * before launching the app
			 * example:
			 * 1. Downloading login details and storing them in the App
			 * 2. Download initial training data {training, classes and trainees}
			 * 2. Downloading images
			 * 3. Parsing the xml / json
			 * 4. Sending device information to server
			 * 5. etc.,
			 */
            JsonParser jsonParser = new JsonParser();
            SessionManagement sessionManagement = new SessionManagement(getBaseContext());

            String json = jsonParser
                    .getJSONFromUrl(sessionManagement.getApiUrl() +sessionManagement.getUsersEndpoint());

            Log.v("Tremap: ", ">> "+ sessionManagement.getApiUrl() +sessionManagement.getUsersEndpoint());
            Log.v("Tremap: ", "> " + json);

            if (json != null) {
                try {
                    JSONObject reader= new JSONObject(json);
                    DatabaseHelper databaseHelper = new DatabaseHelper(getBaseContext());
                    JSONArray recs = reader.getJSONArray("users");
                    for (int x = 0; x < recs.length(); x++){
                        // pass it to dbHelper for save
                        databaseHelper.usersFromJson(recs.getJSONObject(x));
                    }
                    Log.d("Tremap", " Data >>>> " + json);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            canProceed = true;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        TremapApp.getmInstance().setConnectivityListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                canProceed = true;
            }
        }, 6000);
        animateLogo();
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected){

    }

    public int getImage(String imageName) {
        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());
        return drawableResourceId;
    }

    public void animateLogo(){
        imageView.startAnimation(aniRotateClk);
    }
}
