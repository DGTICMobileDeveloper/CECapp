package docencia.tic.unam.mx.cecapp;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import docencia.tic.unam.mx.cecapp.models.GeneralMap;
import docencia.tic.unam.mx.cecapp.models.OldStand;
import docencia.tic.unam.mx.cecapp.models.ServerMapsResponse;
import docencia.tic.unam.mx.cecapp.models.Stand;
import uk.co.senab.photoview.PhotoViewAttacher;

public class IntentMapa extends AppCompatActivity {
    private static Spinner spinnerDia;
    private static Spinner spinnerMapa;
    private static ImageView mapa;
    private static Activity context;
    private static ArrayAdapter<String> adapterDia;
    private static List<String> dayList;
    private static ArrayAdapter<String> adapterMapa;
    private static List<String> mapList;
    protected static String jsonAsString;

    protected static boolean first;

    protected static boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_mapa);
        //setContentView(R.layout.intent_mapa);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setTitle("Mapa del evento");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        context = this;

        // Debb
//        String strigPrueba = getResources().getString(R.string.string_prueba2);
//        ServerMapsResponse response = ServerMapsResponse.parseJSON("{ \"response_code\":0, \"response_msg\":\"Procesamiento concluido exitósamente.\", \"response_data\":{ \"maps\":[ { \"id\":1, \"name\":\"Planta Baja General\", \"permanentActivities\":[ { \"id\":24, \"boundaries\":{ \"x\":0, \"y\":0, \"w\":100, \"h\":200 }, \"rgbColor\":[ 120, 203, 200 ], \"activity\":{ \"id\":10, \"name\":\"Activity 3\", \"schedule\":\"08:00-17:00\" } }, { \"id\":28, \"boundaries\":{ \"x\":120, \"y\":300, \"w\":200, \"h\":400 }, \"rgbColor\":[ 3, 33, 233 ], \"activity\":{ \"id\":10, \"name\":\"Activity 3\", \"schedule\":\"08:00-17:00\" } } ], \"activitiesByDate\":[ { \"day\":\"2016-04-09\", \"locations\":[ { \"id\":12, \"boundaries\":{ \"x\":0, \"y\":0, \"w\":100, \"h\":200 }, \"rgbColor\":[ 0, 0, 0 ], \"activities\":[ { \"id\":43, \"name\":\"Activity 20\", \"schedule\":\"09:00-12:00\" }, { \"id\":44, \"name\":\"Activity 22\", \"schedule\":\"13:00-15:00\" } ] }, { \"id\":13, \"boundaries\":{ \"x\":120, \"y\":50, \"w\":100, \"h\":200 }, \"rgbColor\":[ ], \"activities\":[ { \"id\":48, \"name\":\"Activity 15\", \"schedule\":\"09:00-10:00\" }, { \"id\":49, \"name\":\"Activity 30\", \"schedule\":\"10:00-12:00\" }, { \"id\":50, \"name\":\"Activity 32\", \"schedule\":\"12:00-16:00\" } ] }, { \"id\":14, \"boundaries\":{ \"x\":150, \"y\":290, \"w\":200, \"h\":300 }, \"rgbColor\":[ 0, 0, 0 ], \"activities\":[ { \"id\":31, \"name\":\"Activity 6\", \"schedule\":\"09:00-12:00\" } ] } ] }, { \"day\":\"2016-04-10\", \"locations\":[ { \"id\":12, \"boundaries\":{ \"x\":0, \"y\":0, \"w\":100, \"h\":200 }, \"rgbColor\":[ 0, 0, 0 ], \"activities\":[ { \"id\":43, \"name\":\"Activity 20\", \"schedule\":\"09:00-12:00\" } ] }, { \"id\":13, \"boundaries\":{ \"x\":120, \"y\":50, \"w\":100, \"h\":200 }, \"rgbColor\":[ ], \"activities\":[ { \"id\":48, \"name\":\"Activity 15\", \"schedule\":\"09:00-10:00\" } ] } ] } ] }, { \"id\":2, \"name\":\"Mezzanine General\", \"permanentActivities\":[ ], \"activitiesByDate\":[ ] }, { \"id\":3, \"name\":\"Sótano General\", \"permanentActivities\":[ ], \"activitiesByDate\":[ ] } ] } }");
        //String strigPrueba = "{ \"response_code\": 0, \"response_msg\": \"Procesamiento concluido exitósamente.\", \"response_data\": { \"maps\": [ { \"id\": 1, \"name\": \"Planta Baja General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 239, \"y\": 214, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 326, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 244, \"y\": 440, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] }, { \"day\": \"2016-04-10\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 669, \"y\": 455, \"w\": 65, \"h\": 152 }, \"rgbColor\": [ 50, 50, 50 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 815, \"y\": 458, \"w\": 52, \"h\": 149 }, \"rgbColor\": [ 58, 90, 210 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" } ] } ] } ] }, { \"id\": 2, \"name\": \"Mezzanine General\", \"permanentActivities\": [], \"activitiesByDate\": [] }, { \"id\": 3, \"name\": \"Sótano General\", \"permanentActivities\": [], \"activitiesByDate\": [] } ] } }";
        //String strigPrueba = "{ \"response_code\": 0, \"response_msg\": \"Procesamiento concluido exitósamente.\", \"response_data\": { \"maps\": [ { \"id\": 1, \"name\": \"Planta Baja General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 239, \"y\": 214, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 326, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 244, \"y\": 440, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] }, { \"day\": \"2016-04-10\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 669, \"y\": 455, \"w\": 65, \"h\": 152 }, \"rgbColor\": [ 50, 50, 50 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 815, \"y\": 458, \"w\": 52, \"h\": 149 }, \"rgbColor\": [ 58, 90, 210 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" } ] } ] } ] }, { \"id\": 2, \"name\": \"Mezzanine General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 139, \"y\": 114, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 226, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 344, \"y\": 340, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] }, { \"day\": \"2016-04-10\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 669, \"y\": 455, \"w\": 65, \"h\": 152 }, \"rgbColor\": [ 50, 50, 50 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 815, \"y\": 458, \"w\": 52, \"h\": 149 }, \"rgbColor\": [ 58, 90, 210 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" } ] } ] } ] }, { \"id\": 3, \"name\": \"Sótano General\", \"permanentActivities\": [], \"activitiesByDate\": [] } ] } }";
        //String strigPrueba = "{ \"response_code\": 0, \"response_msg\": \"Procesamiento concluido exitósamente.\", \"response_data\": { \"maps\": [ { \"id\": 1, \"name\": \"Planta Baja General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 239, \"y\": 214, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 326, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 244, \"y\": 440, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] }, { \"day\": \"2016-04-10\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 669, \"y\": 455, \"w\": 65, \"h\": 152 }, \"rgbColor\": [ 50, 50, 50 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 815, \"y\": 458, \"w\": 52, \"h\": 149 }, \"rgbColor\": [ 58, 90, 210 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" } ] } ] } ] }, { \"id\": 2, \"name\": \"Mezzanine General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 139, \"y\": 114, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 226, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 344, \"y\": 340, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] }, { \"day\": \"2016-04-10\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 669, \"y\": 455, \"w\": 65, \"h\": 152 }, \"rgbColor\": [ 50, 50, 50 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 815, \"y\": 458, \"w\": 52, \"h\": 149 }, \"rgbColor\": [ 58, 90, 210 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" } ] } ] } ] }, { \"id\": 3, \"name\": \"Sótano General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 239, \"y\": 214, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 326, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 244, \"y\": 440, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] } ] } ] } }";
        String strigPrueba = "{ \"response_code\": 0, \"response_msg\": \"Procesamiento concluido exitósamente.\", \"response_data\": { \"maps\": [ { \"id\": 1, \"name\": \"Planta Baja General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 239, \"y\": 214, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 326, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 244, \"y\": 440, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 711, \"y\": 220, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] }, { \"day\": \"2016-04-10\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 669, \"y\": 455, \"w\": 65, \"h\": 152 }, \"rgbColor\": [ 50, 50, 50 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 815, \"y\": 458, \"w\": 52, \"h\": 149 }, \"rgbColor\": [ 58, 90, 210 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" } ] } ] } ] }, { \"id\": 2, \"name\": \"Mezzanine General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [] }, { \"id\": 3, \"name\": \"Sótano General\", \"permanentActivities\": [ { \"id\": 24, \"boundaries\": { \"x\": 239, \"y\": 214, \"w\": 170, \"h\": 68 }, \"rgbColor\": [ 120, 203, 200 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } }, { \"id\": 28, \"boundaries\": { \"x\": 243, \"y\": 326, \"w\": 167, \"h\": 70 }, \"rgbColor\": [ 3, 33, 233 ], \"activity\": { \"id\": 10, \"name\": \"Activity 3\", \"schedule\": \"08:00-17:00\" } } ], \"activitiesByDate\": [ { \"day\": \"2016-04-09\", \"locations\": [ { \"id\": 12, \"boundaries\": { \"x\": 244, \"y\": 440, \"w\": 316, \"h\": 137 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 43, \"name\": \"Activity 20\", \"schedule\": \"09:00-12:00\" }, { \"id\": 44, \"name\": \"Activity 22\", \"schedule\": \"13:00-15:00\" } ] }, { \"id\": 13, \"boundaries\": { \"x\": 475, \"y\": 219, \"w\": 193, \"h\": 172 }, \"rgbColor\": [ 100, 100, 100 ], \"activities\": [ { \"id\": 48, \"name\": \"Activity 15\", \"schedule\": \"09:00-10:00\" }, { \"id\": 49, \"name\": \"Activity 30\", \"schedule\": \"10:00-12:00\" }, { \"id\": 50, \"name\": \"Activity 32\", \"schedule\": \"12:00-16:00\" } ] }, { \"id\": 14, \"boundaries\": { \"x\": 611, \"y\": 320, \"w\": 199, \"h\": 174 }, \"rgbColor\": [ 200, 100, 50 ], \"activities\": [ { \"id\": 31, \"name\": \"Activity 6\", \"schedule\": \"09:00-12:00\" } ] } ] } ] } ] } }";
        first = true;


        mapa = (ImageView) findViewById(R.id.mapa);
        mapa.setAdjustViewBounds(true);

        dayList = new ArrayList<>();
        mapList = new ArrayList<>();

        spinnerDia = (Spinner) findViewById(R.id.spinner_dia);
        adapterDia = new ArrayAdapter<String>(IntentMapa.this, android.R.layout.simple_spinner_dropdown_item, dayList);
//        adapterDia = new ArrayAdapter<String>(IntentMapa.this, android.R.layout.simple_spinner_dropdown_item) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
////                View v = super.getView(position, convertView, parent);
////                if (position == getCount()) {
////                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
////                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
////                }
////                return v;
//                return super.getView(position, convertView, parent);
//            }
//            @Override
//            public int getCount() {
//                return super.getCount();
//            }
//        };
        adapterDia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dayList.add("Fecha");
        spinnerDia.setAdapter(adapterDia);
        spinnerDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if(spinnerDia.getCount() > 0) {
                                putInfoThenMapa(jsonAsString, position);
                            }
                            //Log.i(">>> onItemSelected", "pos " + position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
//                        putMapa(mapaInfo.getAllStandsOnDay(dayStandListInfo.get(0).getDay()));
                        //Log.i(">>> onNothingSelected", dayStandListInfo.get(0).getDay());
                        }
                    });

        spinnerMapa = (Spinner) findViewById(R.id.spinner_mapa);
        adapterMapa = new ArrayAdapter<String>(IntentMapa.this, android.R.layout.simple_spinner_dropdown_item, mapList);
//        adapterMapa = new ArrayAdapter<String>(IntentMapa.this, android.R.layout.simple_spinner_dropdown_item) {
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
////                View v = super.getView(position, convertView, parent);
////                if (position == getCount()) {
////                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
////                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
////                }
////                return v;
//                return super.getView(position, convertView, parent);
//            }
//            @Override
//            public int getCount() {
//                return super.getCount();
//            }
//        };
        adapterMapa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapList.add("Planta baja general");
        mapList.add("Mezzanie general");
        mapList.add("Sótano general");
        adapterMapa.notifyDataSetChanged();
        spinnerMapa.setAdapter(adapterMapa);
        spinnerMapa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.i(">>> spinMapa ItemSel", String.format("pos %d %b", position, first));
//                setMapaDrawable(position);
//    //                if(jsonAsString != null && !jsonAsString.equals("")){
//                    if(jsonAsString != null){
//                        putResponse(jsonAsString);
//                    }
                if(!first) {
                    setMapaDrawable(position);
                    //                if(jsonAsString != null && !jsonAsString.equals("")){
                    if(jsonAsString != null){
                        dayList.clear();
                        dayList.add("Fecha");
                        putResponse(jsonAsString);
                    }
                    //adapterDia.notifyDataSetChanged();
                } else {
                    first = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Log.i(">>> spinMapa onNothing", "pos " + 0);
//                setMapaDrawable(0); // El mapa por default
////                if(jsonAsString != null){
////                    putResponse(jsonAsString);
////                }
            }
        });


        AsyncHttpRetriever asyncHttpRetriever = new AsyncHttpRetriever(Constants.MODE_GET_EVENT_MAP,
                this, getRequestParams(extras.getLong(Constants.ID_EVENT)) , strigPrueba);
        //asyncHttpRetriever.postCommand(Constants.BASE_LINK_GET_EVENT_MAP);
    }


    private void setMapaDrawable(int pos){
        // TODO pensaba ponerlo en las opciones de layout el asignar portrait o landscape pero no
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(isTablet) {
                switch (pos){
                    case 0:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape_pbg,
                                this.getTheme()));
                        break;
                    case 1:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape_mg,
                                this.getTheme()));
                        break;
                    case 2:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape_sg,
                                this.getTheme()));
                        break;
                    default:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape,
                                this.getTheme()));
                        break;
                }
            } else {
                switch (pos){
                    case 0:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait_pbg,
                                this.getTheme()));
                        break;
                    case 1:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait_mg,
                                this.getTheme()));
                        break;
                    case 2:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait_sg,
                                this.getTheme()));
                        break;
                    default:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait,
                                this.getTheme()));
                        break;
                }
            }
        }else {
            if(isTablet) {
                switch (pos){
                    case 0:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape_pbg));
                        break;
                    case 1:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape_mg));
                        break;
                    case 2:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape_sg));
                        break;
                    default:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_landscape));
                        break;
                }
            } else {
                switch (pos){
                    case 0:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait_pbg));
                        break;
                    case 1:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait_mg));
                        break;
                    case 2:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait_sg));
                        break;
                    default:
                        mapa.setImageDrawable(getResources().getDrawable(R.drawable.plano_cec_portrait));
                        break;
                }
            }
        }
    }

    private RequestParams getRequestParams(long eid){
        RequestParams params = new RequestParams();
        params.put(Constants.SERVER_KEY_EVENT_ID, eid);
        return params;
    }

    public void putInfoThenMapa(String json, int pos) {
        ServerMapsResponse serverMapsResponse = ServerMapsResponse.parseJSON(json);
        GeneralMap mapaInfo = serverMapsResponse.getData().getMapList().get(spinnerMapa.getSelectedItemPosition());
        List<GeneralMap.DayStands> dayStandListInfo = mapaInfo.getByDayActs();
        List<Stand> fixedStandList = mapaInfo.getPermanentActs();
        if(dayStandListInfo.size() != 0) {
            putMapa(mapaInfo.getAllStandsOnDay(dayStandListInfo.get(pos).getDay()));
        } else if (fixedStandList.size() != 0){
            putMapa(fixedStandList);
        }
    }

    public static void putResponse(String responseString){
        //Log.i(">>> putResponse", "pos Fecha->" + adapterDia.getPosition("Fecha") + responseString.substring(1,5));
        int responseCode;
        ServerMapsResponse serverMapsResponse = ServerMapsResponse.parseJSON(responseString);
        responseCode = serverMapsResponse.getRespCode();
        adapterDia.notifyDataSetChanged();


        if(responseCode == 0){
//            first = false;
            GeneralMap mapaInfo = serverMapsResponse.getData().getMapList().get(spinnerMapa.getSelectedItemPosition());
            List<GeneralMap.DayStands> dayStandListInfo = mapaInfo.getByDayActs();
            List<Stand> fixedStandList = mapaInfo.getPermanentActs();
            //Log.i(">>> putResponse", "dayStandList.size=" + dayStandListInfo.size());
            if(dayStandListInfo.size() != 0) {  // Hay al menos un día con actividades no fijas
                //Log.i(">>> putResponse", "pos Fecha->" + adapterDia.getPosition("Fecha"));
                if(adapterDia.getPosition("Fecha") == 0) {
                    for (GeneralMap.DayStands standInfo : dayStandListInfo) {
                        dayList.add(standInfo.getDay());
                    }
                    dayList.remove("Fecha");
                }
                adapterDia.notifyDataSetChanged();
                if(!first) {
                    putMapa(mapaInfo.getAllStandsOnDay(dayStandListInfo.get(0).getDay()));
                }
//                if(first) {
//                    spinnerDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                            putMapa(mapaInfo.getAllStandsOnDay(dayStandListInfo.get(position).getDay()));
//                            Log.i(">>> onItemSelected", dayStandListInfo.get(position).getDay());
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
////                        putMapa(mapaInfo.getAllStandsOnDay(dayStandListInfo.get(0).getDay()));
//                        Log.i(">>> onNothingSelected", dayStandListInfo.get(0).getDay());
//                        }
//                    });
//                }

            } else if (fixedStandList.size() != 0){    // Solo hay actividades fijas en ese mapa
                putMapa(fixedStandList);
                Toast.makeText(context, "Las actividades no cambian en este piso", Toast.LENGTH_SHORT).show();
            } else {    // No hay actividades en ese mapa
                Toast.makeText(context, "No hay actividades en este piso", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverMapsResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private static void putMapa(final List<Stand> standList){
        PhotoViewAttacher photoViewAttacher;

        mapa.setImageBitmap(getMapaDibujado(standList));
        photoViewAttacher = new PhotoViewAttacher(mapa);
        photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                int xPixels;
                int yPixels;
                if (isTablet) {
                    xPixels = (int) (x * Constants.W) - Constants.X_OFFSET;
                    yPixels = (int) (y * Constants.H) - Constants.Y_OFFSET;
                } else {
                    xPixels = (int) (Constants.H - (x * Constants.H)) - Constants.Y_OFFSET;
                    yPixels = (int) (y * Constants.W) - Constants.X_OFFSET;
                }
                boolean showToast = true;
                Stand.Boundaries coord;
                for (Stand stand : standList) {
                    coord = stand.getBoundaries();
                    if (isTablet) {
                        if (xPixels >= coord.getX() && yPixels >= coord.getY()) {
                            if (xPixels <= (coord.getX() + coord.getW()) &&
                                    yPixels <= (coord.getY() + coord.getH())) {
                                showToast = false;
                                Toast.makeText(context, "id:" + stand.getId(),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    } else {
                        if (xPixels >= coord.getY() && yPixels >= coord.getX()) {
                            if (xPixels <= (coord.getY() + coord.getH()) &&
                                    yPixels <= (coord.getX() + coord.getW())) {
                                showToast = false;
                                Toast.makeText(context, "id:" + stand.getId(),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                    }
                }
                if (showToast)
                    Toast.makeText(context, "Tocaste en x=" + xPixels + ", y=" + yPixels, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Bitmap getMapaDibujado(List<Stand> standList) {
        //Log.i(">>> getMapaDibujado", "size 0->" + standList.get(0).getRgbColor().length);
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        //myOptions.inPurgeable = true;
        Bitmap bitmap;
        switch (spinnerMapa.getSelectedItemPosition()){
            case 0:
                if(isTablet) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_landscape_pbg, myOptions);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_portrait_pbg, myOptions);
                }
                break;
            case 1:
                if(isTablet) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_landscape_mg, myOptions);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_portrait_mg, myOptions);
                }
                break;
            case 2:
                if(isTablet) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_landscape_sg, myOptions);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_portrait_sg, myOptions);
                }
                break;
            default:
                if(isTablet) {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_landscape, myOptions);
                } else {
                    bitmap = BitmapFactory.decodeResource(context.getResources(),
                            R.drawable.plano_cec_portrait, myOptions);
                }
                break;
        }
        if(standList.isEmpty())   // No hay Stands para dibujar
            return bitmap;
        else {  // Dibuja los stands en la imágen
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            paint.setStyle(Paint.Style.STROKE);
//            if(data.getFill() != null) {
//                paint.setStyle( (data.getFill()) ? Paint.Style.FILL : Paint.Style.STROKE );
//                if(data.getFill()) {
//                    paint.setStyle(Paint.Style.FILL);
//                }
//                else {
//                    paint.setStyle(Paint.Style.STROKE);
//                }
//            } else {
//                paint.setStyle(Paint.Style.STROKE);
//            }

            Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
            Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvas = new Canvas(mutableBitmap);

            Rect rect;
            int x, y, w, h, xOrigNotTablet;
            xOrigNotTablet = Constants.H - Constants.Y_OFFSET;
            for (Stand stand : standList) {
                //Log.i(">>>  -> for", "size ->" + stand.getRgbColor().length);
                int[] color = {125,125,125};
                if(stand.getRgbColor().length == 3) {
                    color = stand.getRgbColor();
                }
                Stand.Boundaries coord = stand.getBoundaries();
                x = coord.getX();
                y = coord.getY();
                w = coord.getW();
                h = coord.getH();

                paint.setARGB(255, color[0], color[1], color[2]);
//                if(stand.getFill() != null){
//                    paint.setStyle( (stand.getFill()) ? Paint.Style.FILL : Paint.Style.STROKE );
//                }
                if (isTablet) {
                    rect = new Rect(x, y + h, x + w, y);
                    rect.offset(Constants.X_OFFSET, Constants.Y_OFFSET);
                } else {
                    rect = new Rect(-(y + h), x + w, -y, x);
                    rect.offset(xOrigNotTablet, Constants.X_OFFSET);
                }

                canvas.drawRect(rect, paint);
            }
            //debb
            paint.setColor(Color.BLUE);
            if(isTablet){
                rect = new Rect(0,5,5,0);
                rect.offset(Constants.X_OFFSET,Constants.Y_OFFSET);
            } else {
                rect = new Rect(0,5,-5,0);
                rect.offset(xOrigNotTablet, Constants.X_OFFSET);
            }
            canvas.drawRect(rect, paint);
            //debb

            return mutableBitmap;
        }
    }
}
