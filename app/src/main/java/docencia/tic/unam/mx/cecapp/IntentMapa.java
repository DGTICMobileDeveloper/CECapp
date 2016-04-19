package docencia.tic.unam.mx.cecapp;


import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.support.design.widget.TabLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.List;

import docencia.tic.unam.mx.cecapp.adapters.MainPageAdapter;
import docencia.tic.unam.mx.cecapp.models.Stand;
import uk.co.senab.photoview.PhotoViewAttacher;

public class IntentMapa extends AppCompatActivity {
    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private static ProgressBar progressBar;
    private static Spinner spinner1;
    private static Spinner spinner2;
    private MainPageAdapter pageAdapter;

    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_mapa);
        //setContentView(R.layout.intent_mapa);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setTitle("Mapa de la actividad " + Long.toString(extras.getLong(Constants.ID_ACTIVITY)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        isTablet = getResources().getBoolean(R.bool.isTablet);

        // Debb
        String strigPrueba = getResources().getString(R.string.string_prueba2);

        ImageView mapa = (ImageView) findViewById(R.id.mapa);
        mapa.setAdjustViewBounds(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(isTablet) {
                mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape,
                        this.getTheme()));
            } else {
                mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait,
                        this.getTheme()));
            }
        }else {
            if(isTablet) {
                mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape));
            } else {
                mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait));
            }
        }
        //viewPager = (ViewPager) findViewById(R.id.mapa_viewpager);

        progressBar = (ProgressBar) findViewById(R.id.mapa_progressBar);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);

        /** Para presentar propuesta */
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(IntentMapa.this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount();
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.add("2016-04-13");
        adapter.add("2016-04-14");
        adapter.add("2016-04-15");
        adapter.add("2016-04-16");

        spinner1.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(IntentMapa.this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount();
            }

        };

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.add("Planta baja general");
        adapter2.add("Mezzanie general");
        adapter2.add("Sótano general");

        spinner2.setAdapter(adapter2);
        /** Para presentar propuesta */

        //tabLayout = (TabLayout) findViewById(R.id.mapa_sliding_tabs);

        AsyncHttpRetriever asyncHttpRetriever = new AsyncHttpRetriever(mapa, Constants.MODE_GET_EVENT_MAP,
                isTablet, this, getRequestParams(extras.getLong(Constants.ID_EVENT),
                extras.getLong(Constants.ID_ACTIVITY) ) , strigPrueba);
        //asyncHttpRetriever.postCommand(Constants.BASE_LINK_GET_EVENT_MAP);
    }

    private RequestParams getRequestParams(long eid, long acid){
        RequestParams params = new RequestParams();
        params.put(Constants.SERVER_KEY_EVENT_ID, eid);
        params.put(Constants.SERVER_KEY_EVENT_ACTIVITY_ID, acid);
        return params;
    }
}
