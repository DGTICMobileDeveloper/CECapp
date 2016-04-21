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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import docencia.tic.unam.mx.cecapp.models.GeneralMap;
import docencia.tic.unam.mx.cecapp.models.ServerSingleMapResponse;
import docencia.tic.unam.mx.cecapp.models.ServerSingleMapResponse;
import docencia.tic.unam.mx.cecapp.models.Stand;
import uk.co.senab.photoview.PhotoViewAttacher;

public class IntentSimpleMap extends AppCompatActivity {
    private static ImageView mapa;
    private static Activity context;
    protected static String jsonAsString;

    protected static boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_single_mapa);
        //setContentView(R.layout.intent_mapa);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Bundle extras = getIntent().getExtras();
//        getSupportActionBar().setTitle("Mapa de la actividad " + Long.toString(extras.getInt(Constants.ID_ACTIVITY)));
        getSupportActionBar().setTitle(extras.getString(Constants.NAME_ACTIVITY));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        isTablet = getResources().getBoolean(R.bool.isTablet);
        context = this;

        // Debb
        String strigPrueba = "{ \"response_code\":0, \"response_msg\":\"Procesamiento concluido exitósamente.\", \"response_data\":{ \"map\":0, \"location\":{ \"id\":28, \"boundaries\":{ \"x\":120, \"y\":300, \"w\":200, \"h\":400 }, \"rgbColor\":[ 3, 33, 233 ], \"activity\":{ \"id\":10, \"name\":\"Activity 3\", \"schedule\":\"08:00-17:00\" } } } }";


        mapa = (ImageView) findViewById(R.id.mapa);
        mapa.setAdjustViewBounds(true);

        AsyncHttpRetriever asyncHttpRetriever = new AsyncHttpRetriever(Constants.MODE_GET_SINGLE_EVENT_MAP,
                this, getRequestParams(extras.getLong(Constants.ID_EVENT),
                extras.getInt(Constants.ID_ACTIVITY) ) , strigPrueba);
        //asyncHttpRetriever.postCommand(Constants.BASE_LINK_GET_SINGLE_EVENT_MAP);
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

    private RequestParams getRequestParams(long eid, long acid){
        RequestParams params = new RequestParams();
        params.put(Constants.SERVER_KEY_EVENT_ID, eid);
        params.put(Constants.SERVER_KEY_EVENT_ACTIVITY_ID, acid);
        return params;
    }



    public static void putResponse(String responseString){
        int responseCode;
        ServerSingleMapResponse serverResponse = ServerSingleMapResponse.parseJSON(responseString);
        responseCode = serverResponse.getRespCode();

        if(responseCode == 0){
            putMapa(serverResponse.getData().getStand(), serverResponse.getData().getMap());
        } else {
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    private static void putMapa(final Stand stand, int map){
        PhotoViewAttacher photoViewAttacher;

        mapa.setImageBitmap(getMapaDibujado(stand, map));
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
                coord = stand.getBoundaries();
                if (isTablet) {
                    if (xPixels >= coord.getX() && yPixels >= coord.getY()) {
                        if (xPixels <= (coord.getX() + coord.getW()) &&
                                yPixels <= (coord.getY() + coord.getH())) {
                            showToast = false;
                            Toast.makeText(context, "id:" + stand.getId(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (xPixels >= coord.getY() && yPixels >= coord.getX()) {
                        if (xPixels <= (coord.getY() + coord.getH()) &&
                                yPixels <= (coord.getX() + coord.getW())) {
                            showToast = false;
                            Toast.makeText(context, "id:" + stand.getId(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (showToast)
                    Toast.makeText(context, "Tocaste en x=" + xPixels + ", y=" + yPixels, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Bitmap getMapaDibujado(Stand stand, int map) {
        //Log.i(">>> getMapaDibujado", "size 0->" + standList.get(0).getRgbColor().length);
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        //myOptions.inPurgeable = true;
        Bitmap bitmap;
        switch (map){
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
