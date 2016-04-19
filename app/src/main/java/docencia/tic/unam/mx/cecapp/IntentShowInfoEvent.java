package docencia.tic.unam.mx.cecapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import docencia.tic.unam.mx.cecapp.auxiliares.DownloadImageTask;

public class IntentShowInfoEvent extends AppCompatActivity {
    TextView adressTV;
    TextView webTV;
    TextView fbTV;
    TextView twTV;
    TextView ytTV;
    ImageView logoIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_show_event_info);

        adressTV = (TextView) findViewById(R.id.esi_adrres_tv_mod);
        webTV = (TextView) findViewById(R.id.esi_web_tv_mod);
        fbTV = (TextView) findViewById(R.id.esi_fb_tv_mod);
        twTV = (TextView) findViewById(R.id.esi_tw_tv_mod);
        ytTV = (TextView) findViewById(R.id.esi_yt_tv_mod);
        logoIV = (ImageView) findViewById(R.id.esi_img);

        Bundle extras = getIntent().getExtras();
        if(extras.getString(Constants.EVENT_INFO_NAME) != null)
            getSupportActionBar().setTitle(extras.getString(Constants.EVENT_INFO_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setInfo(extras);
        //TODO cargar la imagen como fondo
    }

    public void setInfo(Bundle extras){
        adressTV.setText(extras.getString(Constants.EVENT_INFO_ADRESS));
        webTV.setText(extras.getString(Constants.EVENT_INFO_URL));
        fbTV.setText(extras.getString(Constants.EVENT_INFO_FB));
        twTV.setText(extras.getString(Constants.EVENT_INFO_TW));
        ytTV.setText(extras.getString(Constants.EVENT_INFO_YT));
        new DownloadImageTask(logoIV).execute(extras.getString(Constants.EVENT_INFO_LOGO));
        /*
        List<String> aux = new ArrayList<>();
        if(extras.getString(Constants.EVENT_INFO_ADRESS) != null){
            aux.add("Dirección\n" + extras.getString(Constants.EVENT_INFO_ADRESS));
        }
        if(extras.getString(Constants.EVENT_INFO_URL) != null){
            aux.add("Página web\n" + Uri.parse(extras.getString(Constants.EVENT_INFO_URL)));
        }
        if(extras.getString(Constants.EVENT_INFO_FB) != null){
            aux.add("Facebook\n" + Uri.parse(extras.getString(Constants.EVENT_INFO_FB)));
        }
        if(extras.getString(Constants.EVENT_INFO_TW) != null){
            aux.add("Twitter\n" + Uri.parse(extras.getString(Constants.EVENT_INFO_TW)));
        }
        if(extras.getString(Constants.EVENT_INFO_YT) != null){
            aux.add("YouTube\n" + Uri.parse(extras.getString(Constants.EVENT_INFO_YT)));
        }
        if(aux.isEmpty())
            aux.add("No se tiene información");
        return aux;
        */
    }
}
