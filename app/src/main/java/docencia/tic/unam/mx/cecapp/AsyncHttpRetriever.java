package docencia.tic.unam.mx.cecapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import docencia.tic.unam.mx.cecapp.adapters.CustomExpandableListAdapter;
import docencia.tic.unam.mx.cecapp.adapters.InterestListArrayAdapter;
import docencia.tic.unam.mx.cecapp.adapters.ListArrayAdapter;
import docencia.tic.unam.mx.cecapp.models.Evento;
import docencia.tic.unam.mx.cecapp.models.EventoDeLista;
import docencia.tic.unam.mx.cecapp.models.ItemListaInteres;
import docencia.tic.unam.mx.cecapp.models.ServerEventListResponse;
import docencia.tic.unam.mx.cecapp.models.ServerEventResponse;
import docencia.tic.unam.mx.cecapp.models.ServerGetUserEventsResponse;
import docencia.tic.unam.mx.cecapp.models.ServerInterestListResponse;
import docencia.tic.unam.mx.cecapp.models.ServerMapResponse;
import docencia.tic.unam.mx.cecapp.models.ServerRegisterUserInfoResponse;
import docencia.tic.unam.mx.cecapp.models.ServerRegisterUserToEventResponse;
import docencia.tic.unam.mx.cecapp.models.OldStand;
import docencia.tic.unam.mx.cecapp.tabs.InfoUsuarioGeneralFragment;
import docencia.tic.unam.mx.cecapp.tabs.InfoUsuarioInteresesFragment;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AsyncHttpRetriever extends AsyncHttpClient {
    public final boolean REGISTER_T = true;
    public final boolean REGISTER_F = false;
    private AsyncHttpClient client;
    private RequestParams params;
    private ProgressBar pbCargando;
    private ListView lvListaEventos;
    protected TextView tvEventos;
    protected int responseCode;
    public static ListView lvListaIntereses;
    private ExpandableListView elvEventoInfo;
    private Activity context;
    private byte origen;
    public static Evento evento;
    private List<String> infoCategoryList;
    private List<String> infoContentList;
    private Map<String, List<String>> elvInfoContent;
    public static SharedPreferences sharedPrefs;
    private SharedPreferences infoUsuarioSP;
    private HashMap<String,String> mapKeyValue;
//    public static ArrayList<ItemListaInteres> arrayOfInterests;
    protected ImageView mapa;
    protected boolean tablet;
    protected PhotoViewAttacher photoViewAttacher;
    //TODO meter lo que no tiene que ser global a donde pertenezca

    public AsyncHttpRetriever(ProgressBar pb, Context c, byte origen, ListView lv, RequestParams rParams, TextView textView){
        this.client = new AsyncHttpClient();
        this.pbCargando = pb;
        this.context = (Activity) c;
        this.origen = origen;
        this.lvListaEventos = lv;
        this.params = rParams;
        this.tvEventos = textView;
    }

    public AsyncHttpRetriever(ProgressBar pb, Activity c, byte origen, ExpandableListView elv,
                              RequestParams rParams, List<String> infoCategoryList){
        this.client = new AsyncHttpClient();
        this.pbCargando = pb;
        this.context = c;
        this.origen = origen;
        this.elvEventoInfo = elv;
        this.params = rParams;
        this.infoCategoryList = infoCategoryList;
    }

    public AsyncHttpRetriever(ProgressBar pb, Context c, byte origen, ListView lv, RequestParams rParams,
                              SharedPreferences prefs){
        this.client = new AsyncHttpClient();
        this.pbCargando = pb;
        this.context = (Activity) c;
        this.origen = origen;
        lvListaIntereses = lv;
        this.params = rParams;
        sharedPrefs = prefs;
    }

    public AsyncHttpRetriever(ProgressBar pb, Context c, byte origen, ListView lv, RequestParams rParams,
                              SharedPreferences prefs, HashMap<String,String> map){
        this.client = new AsyncHttpClient();
        this.pbCargando = pb;
        this.context = (Activity) c;
        this.origen = origen;
        lvListaIntereses = lv;
        this.params = rParams;
        sharedPrefs = prefs;
        this.mapKeyValue = map;
    }

    public AsyncHttpRetriever(SharedPreferences sharedPreferences, byte origen, RequestParams params,
                              Activity activity){
        this.client = new AsyncHttpClient();
        this.infoUsuarioSP = sharedPreferences;
        this.origen = origen;
        this.params = params;
        this.context = activity;
    }

    public AsyncHttpRetriever(byte origen, RequestParams params, Context context){
        this.client = new AsyncHttpClient();
        this.origen = origen;
        this.params = params;
        this.context = (Activity) context;
    }

    public AsyncHttpRetriever(ImageView map, byte origen, boolean isTablet, Activity activity,
                              RequestParams params, String stringPrueba){
        this.client = new AsyncHttpClient();
        this.mapa = map;
        this.origen = origen;
        this.tablet = isTablet;
        this.context = activity;
        //putMap(stringPrueba);
    }

    public void postCommand(String link){
        // TODO con esto estableces el numero de reintentos y cuanto tiempo espera (default 5 intentos de 10 seg)
        if(origen == Constants.MODE_UPDATE_USER_INFO)
            this.client.setMaxRetriesAndTimeout(1, 500);
        client.post(link, params, new TextHttpResponseHandler() {
            @Override
            public void onRetry(int retryNo) {
                super.onRetry(retryNo);
                Log.i(">>> retry",Integer.toString(retryNo));
            }

            @Override
            public void onStart() {
                /** Oculta el elemento encargado de desplegar la información*/
                switch (origen) {
                    case Constants.MODE_EVENT_LIST:
                        pbCargando.setVisibility(ProgressBar.VISIBLE);
                        lvListaEventos.setVisibility(ListView.INVISIBLE);
                        tvEventos.setVisibility(TextView.INVISIBLE);
                        break;
                    case Constants.MODE_EVENT_INFO:
                        pbCargando.setVisibility(ProgressBar.VISIBLE);
                        elvEventoInfo.setVisibility(ExpandableListView.INVISIBLE);
                        break;
                    case Constants.MODE_INTEREST_LIST:
                        pbCargando.setVisibility(ProgressBar.VISIBLE);
                        lvListaIntereses.setVisibility(ExpandableListView.INVISIBLE);
                        break;
                    case Constants.MODE_REGISTER_USER_INFO:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_UPDATE_USER_INFO:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_GET_USER_EVENTS:
                        pbCargando.setVisibility(ProgressBar.VISIBLE);
                        lvListaEventos.setVisibility(ListView.INVISIBLE);
                        tvEventos.setVisibility(TextView.INVISIBLE);
                        break;
                    case Constants.MODE_INTEREST_EVENT_LIST:
                        pbCargando.setVisibility(ProgressBar.VISIBLE);
                        lvListaEventos.setVisibility(ListView.INVISIBLE);
                        tvEventos.setVisibility(TextView.INVISIBLE);
                        break;
                    case Constants.MODE_REGISTER_USER_TO_EVENT:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_REMOVE_USER_FROM_EVENT:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_GET_EVENT_MAP:
                        // No se hace nada aquí
                        break;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                throwable.printStackTrace(System.out);
                switch (origen) {
                    case Constants.MODE_EVENT_LIST:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        break;
                    case Constants.MODE_EVENT_INFO:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        break;
                    case Constants.MODE_INTEREST_LIST:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        break;
                    case Constants.MODE_REGISTER_USER_INFO:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_UPDATE_USER_INFO:
                        Toast.makeText(context, "Error de conexión, vuelve a intentar más tarde", Toast.LENGTH_SHORT).show();
                        InfoUsuarioGeneralFragment.setUserInfo(InfoUsuarioGeneralFragment.getListFromPreferences(infoUsuarioSP));
                        break;
                    case Constants.MODE_GET_USER_EVENTS:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        break;
                    case Constants.MODE_INTEREST_EVENT_LIST:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        break;
                    case Constants.MODE_REGISTER_USER_TO_EVENT:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_REMOVE_USER_FROM_EVENT:
                        // No se hace nada aquí
                        break;
                    case Constants.MODE_SEARCH_EVENT:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        break;
                    case Constants.MODE_GET_EVENT_MAP:
                        // No se hace nada aquí
                        break;
                }
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                switch (origen) {
                    case Constants.MODE_EVENT_LIST:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        putEventList(responseString);
                        break;
                    case Constants.MODE_EVENT_INFO:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        putEventInfo(responseString);
                        break;
                    case Constants.MODE_INTEREST_LIST:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        putInterestList(responseString);
                        break;
                    case Constants.MODE_REGISTER_USER_INFO:
                        showRegisterMessage(responseString);
                        break;
                    case Constants.MODE_UPDATE_USER_INFO:
                        showUpdateMessage(responseString);
                        break;
                    case Constants.MODE_GET_USER_EVENTS:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        putUserEventList(responseString);
                        break;
                    case Constants.MODE_INTEREST_EVENT_LIST:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        putEventList(responseString);
                        break;
                    case Constants.MODE_REGISTER_USER_TO_EVENT:
                        showRegisterUserToEventMessage(responseString);
                        break;
                    case Constants.MODE_REMOVE_USER_FROM_EVENT:
                        showRemoveUserFromEventMessage(responseString);
                        break;
                    case Constants.MODE_SEARCH_EVENT:
                        pbCargando.setVisibility(ProgressBar.INVISIBLE);
                        putEventList(responseString);
                        break;
                    case Constants.MODE_GET_EVENT_MAP:
                        putMap(responseString);
                        break;
                }
            }
        });
    }

    /** TODO
     * - Checar los diferentes significados de cada responseCode y poner la acción pertinente
     *   en los diferentes métodos de infladoBASE_LINK_REGISTER_USER
     */
    /** Métodos de decodificación e inflado
     *
     *  Realizan la decodificación de la información recibida del servidor/servicio y, de acuerdo
     *  al código de respuesta, inflan el contenido pertinente
     */
    public void putEventList(final String responseString) {
        Resources res = context.getResources();
        ServerEventListResponse serverEventListResponse = ServerEventListResponse.parseJSON(responseString);
        responseCode = serverEventListResponse.getRespCode();

        switch (responseCode) {
            case 0: // Sin errores
                List<EventoDeLista> listaEventos;
                listaEventos = serverEventListResponse.getData().getEventList();
                if (listaEventos.isEmpty()) {
                    tvEventos.setVisibility(TextView.VISIBLE);
                    switch (origen) {
                        case Constants.MODE_EVENT_LIST:
                            tvEventos.setText(res.getString(R.string.event_list_general));
                            break;
                        case Constants.MODE_INTEREST_EVENT_LIST:
                            tvEventos.setText(res.getString(R.string.event_list_interests));
                            break;
                        case Constants.MODE_SEARCH_EVENT:
                            tvEventos.setText(res.getString(R.string.event_list_search,
                                    IntentBusqueda.terminoBusqueda));
                            break;
                    }
                } else {
                    lvListaEventos.setVisibility(ListView.VISIBLE);

                    ArrayList<EventoDeLista> arrayOfEventos = new ArrayList<>(listaEventos);

                    final ListArrayAdapter adapter = new ListArrayAdapter(context, arrayOfEventos);

                    lvListaEventos.setAdapter(adapter);
                    lvListaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View v, int position,
                                                long id) {
                            EventoDeLista mEvento = adapter.getEvento(position);
                            Intent intent = new Intent(context, IntentEvento.class)
                                    .putExtra(Constants.NAME_EVENT, mEvento.getName())
                                    .putExtra(Constants.ID_EVENT, mEvento.getId())
                                            //.putExtra(Constants.IS_REGISTERED, mEvento.isSignedUp()
                                    .putExtra(Constants.IS_REGISTERED, REGISTER_F);
                            /** Se necesita poner el context.sta---() porque extendiendo AsyncHttpClient
                             no se tiene un contexto */
                            context.startActivity(intent);
                        }
                    });
                }
                break;
            case 1:
                tvEventos.setVisibility(TextView.VISIBLE);
                switch (origen) {
                    case Constants.MODE_EVENT_LIST:
                        tvEventos.setText(serverEventListResponse.getRespMsg());
                        break;
                    case Constants.MODE_INTEREST_EVENT_LIST:
                        tvEventos.setText(res.getString(R.string.event_list_interests_e1));
                        break;
                    case Constants.MODE_SEARCH_EVENT:
                        tvEventos.setText(serverEventListResponse.getRespMsg());
                        break;
                }
                break;
            case 2:
                //
                break;
            default:
                tvEventos.setVisibility(TextView.VISIBLE);
                tvEventos.setText("CR: " +responseCode + ", Mensaje: " +
                        serverEventListResponse.getRespMsg());
                break;
        }
    }

    // Todo Porque se dividió en dos con el método de ↑
    public void putUserEventList(final String responseString){
        ServerGetUserEventsResponse serverGetUserEventsResponse =
                ServerGetUserEventsResponse.parseJSON(responseString);
        responseCode = serverGetUserEventsResponse.getRespCode();

        if(responseCode == 0) {
            List<EventoDeLista> listaEventos;
            listaEventos = serverGetUserEventsResponse.getData().getEventList();

            if(listaEventos.isEmpty()){ // no hay eventos que desplegar
                tvEventos.setVisibility(TextView.VISIBLE);
                tvEventos.setText("No encontraron eventos registrados");
            } else {
                lvListaEventos.setVisibility(ListView.VISIBLE);

                ArrayList<EventoDeLista> arrayOfEventos = new ArrayList<>(listaEventos);
                final ListArrayAdapter adapter = new ListArrayAdapter(context, arrayOfEventos);

                lvListaEventos.setAdapter(adapter);
                lvListaEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position,
                                            long id) {
                        Intent intent = new Intent(context, IntentEvento.class);
                        EventoDeLista mEvento = adapter.getEvento(position);
                        intent.putExtra(Constants.NAME_EVENT, mEvento.getName());
                        intent.putExtra(Constants.ID_EVENT, mEvento.getId());
                        intent.putExtra(Constants.IS_REGISTERED, REGISTER_T);
                        context.startActivity(intent);
                    }
                });
            }
        }else {
            Toast.makeText(context, "Debbug: ->respCode " + responseCode, Toast.LENGTH_SHORT).show();
        }
    }

    public void putEventInfo(final String responseString) {
        ServerEventResponse serverEventResponse = ServerEventResponse.parseJSON(responseString);
        responseCode = serverEventResponse.getRespCode();

        if(responseCode == 0) {
            elvEventoInfo.setVisibility(ExpandableListView.VISIBLE);
            evento = serverEventResponse.getEvent();
            createCollection(evento);

            final CustomExpandableListAdapter expListAdapter = new CustomExpandableListAdapter(
                    context, infoCategoryList, elvInfoContent, Constants.SOURCE_EVENTO_INFO);
            elvEventoInfo.setAdapter(expListAdapter);
            elvEventoInfo.expandGroup(0);
            elvEventoInfo.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    if (groupPosition > 2 && groupPosition < 5) {
                        boolean show;
                        Intent intent = new Intent(context, IntentShowInfoEvent.class);
                        if (groupPosition == 3) { // Organizadores
                            if(!evento.getOrganizerList().isEmpty()) {
                                Evento.Organizer auxOrg = evento.getOrganizerList().get(childPosition);
                                intent.putExtra(Constants.EVENT_INFO_NAME, auxOrg.getName());
                                intent.putExtra(Constants.EVENT_INFO_URL, auxOrg.getLink());
                                intent.putExtra(Constants.EVENT_INFO_FB, auxOrg.getFaceBook());
                                intent.putExtra(Constants.EVENT_INFO_TW, auxOrg.getTwitter());
                                intent.putExtra(Constants.EVENT_INFO_YT, auxOrg.getYouTube());
                                intent.putExtra(Constants.EVENT_INFO_ADRESS, auxOrg.getAddress());
                                intent.putExtra(Constants.EVENT_INFO_LOGO, auxOrg.getLogoUrl());
                                show = true;
                            } else
                                show = false;
                        } else { // Patrocinadores
                            if(!evento.getSponsorList().isEmpty()) {
                                Evento.Sponsor auxSponsor = evento.getSponsorList().get(childPosition);
                                intent.putExtra(Constants.EVENT_INFO_NAME, auxSponsor.getName());
                                intent.putExtra(Constants.EVENT_INFO_URL, auxSponsor.getLink());
                                intent.putExtra(Constants.EVENT_INFO_FB, auxSponsor.getFaceBook());
                                intent.putExtra(Constants.EVENT_INFO_TW, auxSponsor.getTwitter());
                                intent.putExtra(Constants.EVENT_INFO_YT, auxSponsor.getYouTube());
                                intent.putExtra(Constants.EVENT_INFO_ADRESS, auxSponsor.getAddress());
                                intent.putExtra(Constants.EVENT_INFO_LOGO, auxSponsor.getLogoUrl());
                                show = true;
                            } else
                                show = false;

                        }
                        if(show)
                            context.startActivity(intent);
                        else
                            Toast.makeText(context, "No hay información relevante", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }else {
            Toast.makeText(context, "Debbug: ->respCode " + responseCode, Toast.LENGTH_SHORT).show();
        }
    }

    public void putInterestList(final String responseString){
        ServerInterestListResponse serverInterestListResponse =
                ServerInterestListResponse.parseJSON(responseString);
        responseCode = serverInterestListResponse.getRespCode();

        if(responseCode == 0) {
            List<ItemListaInteres> subjectList;

            lvListaIntereses.setVisibility(ListView.VISIBLE);
            subjectList = serverInterestListResponse.getData().getSubjectList();

            if (params == null) {    // Si no pasó parámetros (es la primera consulta)
                int x = 1;
                SharedPreferences.Editor editor = sharedPrefs.edit();
                for (ItemListaInteres interes : subjectList) {
                    editor.putString(InfoUsuarioInteresesFragment.INTEREST_KEY_SUBJECT + x, interes.getSubject());
                    editor.putBoolean(InfoUsuarioInteresesFragment.INTEREST_KEY_STATUS + x, false);
                    editor.putInt(InfoUsuarioInteresesFragment.INTEREST_KEY_ID + x, interes.getId());
                    editor.apply();
                    x++;
                }
            } else { //Pasó parámetros

            }
        /* TODO modificar params una vez que sea una actualización y también establecer su estado
        else if(params.has()){

        }*/

            InfoUsuarioInteresesFragment.data = new ArrayList<>();
            InfoUsuarioInteresesFragment.data.addAll(subjectList);
            final InterestListArrayAdapter adapter = new InterestListArrayAdapter(context, InfoUsuarioInteresesFragment.data);

            lvListaIntereses.setAdapter(adapter);
            lvListaIntereses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.i(">>>onSaveInstanceState", "Prev " +  data.get(position).isChecked());
                    InfoUsuarioInteresesFragment.data.get(position).setCheck(!InfoUsuarioInteresesFragment.data.get(position).isChecked());
//                    Log.i(">>>onSaveInstanceState", "Post " +  data.get(position).isChecked());
                    adapter.notifyDataSetChanged();
//                    Log.i(">>>onSaveInstanceState", "notifyDataChanged " +  data.get(position).isChecked());
                }
            });
        } else {
            Toast.makeText(context, "Debbug: ->respCode " + responseCode, Toast.LENGTH_SHORT).show();
        }
    }

    public void showRegisterMessage(String responseString){
        ServerRegisterUserInfoResponse serverRegisterUserInfoResponse =
                ServerRegisterUserInfoResponse.parseJSON(responseString);
        responseCode = serverRegisterUserInfoResponse.getRespCode();

        if(responseCode == 0){
            long id = serverRegisterUserInfoResponse.getData().getId();
            infoUsuarioSP.edit().putLong(Constants.USER_ID,id).apply();
            Toast.makeText(context, "Se registró con el id " + Long.toString(id), Toast.LENGTH_SHORT).show();
        } else if(responseCode == 4){
            Toast.makeText(context, "Ya se había registrado el usuario", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverRegisterUserInfoResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showUpdateMessage(String responseString){
        ServerRegisterUserInfoResponse serverRegisterUserInfoResponse =
                ServerRegisterUserInfoResponse.parseJSON(responseString);
        responseCode = serverRegisterUserInfoResponse.getRespCode();

        Log.i(">>> update Msj", responseString);

        if(responseCode == 0){
            InfoUsuarioGeneralFragment.asyncHttpSave();
            Toast.makeText(context, "Se actualizó su información con éxito", Toast.LENGTH_SHORT).show();
        } else {
            InfoUsuarioGeneralFragment.setUserInfo(InfoUsuarioGeneralFragment.getListFromPreferences(infoUsuarioSP));
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverRegisterUserInfoResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showRegisterUserToEventMessage(String responseString){
        ServerRegisterUserToEventResponse serverRegisterUserToEventResponse =
                ServerRegisterUserToEventResponse.parseJSON(responseString);
        responseCode = serverRegisterUserToEventResponse.getRespCode();

        if(responseCode == 0){
            Toast.makeText(context, "Se registró con éxito al evento", Toast.LENGTH_SHORT).show();
            IntentEvento.estadoEvento = true;   // Preregistrado
            IntentEvento.setFabDrawable();
        } else if(responseCode == 6){
            Toast.makeText(context, "Ya se había registrado el usuario al evento", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverRegisterUserToEventResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
            IntentEvento.estadoEvento = false;   // No se pudo completar el preregistro
            IntentEvento.setFabDrawable();
        }
    }

    public void showRemoveUserFromEventMessage(String responseString){
        ServerRegisterUserToEventResponse serverRegisterUserToEventResponse =
                ServerRegisterUserToEventResponse.parseJSON(responseString);
        responseCode = serverRegisterUserToEventResponse.getRespCode();

        Log.i(">>> remove Msj", responseString);

        if(responseCode == 0){
            Toast.makeText(context, "Se removió con éxito del evento", Toast.LENGTH_SHORT).show();
            IntentEvento.estadoEvento = false;   // Removido
            IntentEvento.setFabDrawable();
        } else if(responseCode == 6){
            Toast.makeText(context, "Error 6", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverRegisterUserToEventResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
            IntentEvento.estadoEvento = true;   // No se pudo completar la remoción
            IntentEvento.setFabDrawable();
        }
    }

    public void putMap(String responseString){
        final ServerMapResponse serverMapResponse = ServerMapResponse.parseJSON(responseString);
        responseCode = serverMapResponse.getRespCode();

        if(responseCode == 0){
            mapa.setImageBitmap( getMapaDibujado( serverMapResponse.getData() ) );
            photoViewAttacher = new PhotoViewAttacher(mapa);
            photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    int xPixels;
                    int yPixels;
                    if (tablet) {
                        xPixels = (int) (x * Constants.W) - Constants.X_OFFSET;
                        yPixels = (int) (y * Constants.H) - Constants.Y_OFFSET;
                    } else {
                        xPixels = (int) (Constants.H - (x * Constants.H)) - Constants.Y_OFFSET;
                        yPixels = (int) (y * Constants.W) - Constants.X_OFFSET;
                    }
                    boolean showToast = true;
                    OldStand.Coordenadas coord;
                    for (OldStand stand : serverMapResponse.getData().getOldStandList()) {
                        coord = stand.getCoordenadas();
                        if (tablet) {
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
        } else {
            Toast.makeText(context, "Hubo un error " + responseCode
                    + ": " + serverMapResponse.getRespMsg(), Toast.LENGTH_SHORT).show();
        }
    }

    /** Métodos de apoyo
     *
     *  Ayudan a los métodos de 'decodificación e inflado' para manejar la información de
     *  manera más sencilla.
     */
    private void createCollection(Evento evento) {
        elvInfoContent = new LinkedHashMap<String, List<String>>();
        int x = 0;
        int NumOfLinks = 4;
        List<String> auxListU = new ArrayList<String>();
        List<String> auxListF = new ArrayList<String>();
        List<String> auxListO = new ArrayList<String>();
        List<String> auxListP = new ArrayList<String>();
        List<String> auxListD = new ArrayList<String>();
        List<String> auxListL = new ArrayList<String>();
        String aux;

        for (String category : infoCategoryList) {
            switch (x){
                case 0: // Descripción
                    auxListD.add(evento.getDescription());
                    elvInfoContent.put(category, auxListD);
                    break;
                case 1: // Ubicación
                    auxListU.add(Constants.LOCATION_EVENT);
                    elvInfoContent.put(category, auxListU);
                    break;
                case 2: // Fecha
                    auxListF.add(getSimpleFecha(evento.getStartDate(), evento.getEndDate(), evento.getDuration()));
                    elvInfoContent.put(category, auxListF);
                    break;
                case 3: // Organizador(es)
                    List<Evento.Organizer> auxOrganizador = evento.getOrganizerList();
                    if(auxOrganizador.isEmpty()) {
                        auxListO.add("No se encontraron organizadores");
                        elvInfoContent.put(category, auxListO);
                    }
                    else{
                        if(auxOrganizador.size()>1){ // Hay más de un organizador
                            aux = category + "es";
                            infoCategoryList.set(x, aux);
                            elvInfoContent.put(aux, infoToStringList(auxOrganizador));
                        }
                        else
                            elvInfoContent.put(category, infoToStringList(auxOrganizador));
                    }
                    break;
                case 4: // Patrocinador(es)
                    List<Evento.Sponsor> auxSponsor = evento.getSponsorList();
                    if (auxSponsor.isEmpty()) {
                        auxListP.add("No se encontraron patrocinadores");
                        elvInfoContent.put(category, auxListP);
                    }
                    else {
                        if (auxSponsor.size()>1) {
                            aux = category + "es";
                            infoCategoryList.set(x, aux);
                            elvInfoContent.put(aux, infoToStringList(auxSponsor));
                        }
                        else
                            elvInfoContent.put(category, infoToStringList(auxSponsor));
                    }
                    break;
                case 5: // Link(s)
                    //for(int i = 0; i < NumOfLinks ; i++){
                    if(evento.getUrl() != null)
                        auxListL.add(evento.getUrl());
                    else
                        auxListL.add("No registró página web");
                    if(evento.getFaceBook() != null)
                        auxListL.add(evento.getFaceBook());
                    else
                        auxListL.add("No registró página de Facebook");
                    if(evento.getTwitter() != null)
                        auxListL.add(evento.getTwitter());
                    else
                        auxListL.add("No registró cuenta de Twitter");
                    if(evento.getYouTube() != null)
                        auxListL.add(evento.getYouTube());
                    else
                        auxListL.add("No registró canal de YouTube");

                    aux = category + "s";
                    infoCategoryList.set(x, aux);
                    elvInfoContent.put(aux, auxListL);
                    /*if(auxListL.isEmpty()){
                        auxListL.add("No se encontraron links");
                        elvInfoContent.put(category, auxListL);
                    }else {
                        if (auxListL.size() > 1) {
                            aux = category + "s";
                            infoCategoryList.set(x, aux);
                            elvInfoContent.put(aux, auxListL);
                        } else
                            elvInfoContent.put(category, auxListL);
                    }*/
                    break;
            }
            x++;
        }
    }

    private String getSimpleFecha(Date sDate, Date eDate, int duration) {
        String fecha;
        List<SimpleDateFormat> sdfList = new ArrayList<SimpleDateFormat>();
        String ampmS;
        String ampmE;

        for (String format : Constants.DATE_FORMAT_ARRAY) {
            sdfList.add(new SimpleDateFormat(format));
        }
        /*if (sdfList.get(Constants.DATE_HH).format(sDate).equals("1") ||
                sdfList.get(Constants.DATE_HH).format(sDate).equals("13"))*/
        if (sdfList.get(Constants.DATE_hh).format(sDate).equals("1"))
            ampmS = "la ";
        else
            ampmS = "las ";
        /*if (sdfList.get(Constants.DATE_HH).format(eDate).equals("1") ||
                sdfList.get(Constants.DATE_HH).format(eDate).equals("13"))*/
        if (sdfList.get(Constants.DATE_hh).format(eDate).equals("1"))
            ampmE = "la ";
        else
            ampmE = "las ";
        if (duration > 1) { // Si dura más de un día
            fecha = "\t" + sdfList.get(Constants.DATE_YEAR).format(sDate) + "\n" +
                    "Del " + sdfList.get(Constants.DATE_Day_dd_de_MONTH).format(sDate) + " a "
                    + ampmS + sdfList.get(Constants.DATE_hhmma).format(sDate) + " al " +
                    sdfList.get(Constants.DATE_Day_dd_de_MONTH).format(eDate) + " a " + ampmE +
                    sdfList.get(Constants.DATE_hhmma).format(eDate);
        }
        else {
            fecha = "\t" + sdfList.get(Constants.DATE_YEAR).format(sDate) + "\n" +
                    "El " + sdfList.get(Constants.DATE_Day_dd_de_MONTH).format(sDate) + ":\n" +
                    "De " + sdfList.get(Constants.DATE_hhmma).format(sDate) + " a " +
                    sdfList.get(Constants.DATE_hhmma).format(eDate);
        }
        /*
        if (duration > 1) { // Si dura más de un día
            fecha = "\t" + sdfList.get(Constants.DATE_YEAR).format(sDate) + "\n" +
                    "Del " + sdfList.get(Constants.DATE_W_DAY_S).format(sDate) + " " +
                    sdfList.get(Constants.DATE_M_DAY).format(sDate) + " de " +
                    sdfList.get(Constants.DATE_MONTH_S).format(sDate) + " a " + ampmS +
                    sdfList.get(Constants.DATE_hhmma).format(sDate) + " al " +
                    sdfList.get(Constants.DATE_W_DAY_S).format(eDate) + " " +
                    sdfList.get(Constants.DATE_M_DAY).format(eDate) + " de " +
                    sdfList.get(Constants.DATE_MONTH_S).format(eDate) + " a " + ampmE +
                    sdfList.get(Constants.DATE_hhmma).format(eDate);
        }
        else {
            fecha = "\t" + sdfList.get(Constants.DATE_YEAR).format(sDate) + "\n" +
                    "El " + sdfList.get(Constants.DATE_W_DAY_S).format(sDate) + " " +
                    sdfList.get(Constants.DATE_M_DAY).format(sDate) + " de " +
                    sdfList.get(Constants.DATE_MONTH_S).format(sDate) + ":\n" +
                    "De " + sdfList.get(Constants.DATE_hhmma).format(sDate) + " a " +
                    sdfList.get(Constants.DATE_hhmma).format(eDate);
        }*/
        return fecha;
    }

    private String getFecha(Date sDate, Date eDate, int duration) {
        String ampmS;
        String ampmE;
        String aux;
        Calendar sAuxDate = Calendar.getInstance();
        Calendar eAuxDate = Calendar.getInstance();
        sAuxDate.setTime(sDate);
        eAuxDate.setTime(eDate);
        if (sAuxDate.get(Calendar.HOUR) == 1 || sAuxDate.get(Calendar.HOUR) == 13)
            ampmS = "la ";
        else
            ampmS = "las ";
        if (eAuxDate.get(Calendar.HOUR) == 1 || eAuxDate.get(Calendar.HOUR) == 13)
            ampmE = "la ";
        else
            ampmE = "las ";
        if (duration > 1) { // Si dura más de un día
             aux = "Empieza el " + sAuxDate.get(Calendar.DAY_OF_MONTH) + " de " +
                    sAuxDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) +
                    " de " + sAuxDate.get(Calendar.YEAR) +
                    " a " + ampmS + sAuxDate.get(Calendar.HOUR) + ":" + sAuxDate.get(Calendar.MINUTE) +
                    ((sAuxDate.get(Calendar.MINUTE) < 10) ? "0" : "") +
                    ((sAuxDate.get(Calendar.AM_PM) == 0) ? "AM" : "PM") + "\n" +
                    "Termina el " + eAuxDate.get(Calendar.DAY_OF_MONTH) + " de " +
                    eAuxDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) +
                    " de " + eAuxDate.get(Calendar.YEAR) +
                    " a " + ampmE + eAuxDate.get(Calendar.HOUR) + ":" + eAuxDate.get(Calendar.MINUTE) +
                    ((eAuxDate.get(Calendar.MINUTE) < 10) ? "0" : "") +
                    ((eAuxDate.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
            evento.getEndDate();
        }
        else { // Dura solo un día
            aux = "Empieza el " + sAuxDate.get(Calendar.DAY_OF_MONTH) + " de " +
                    sAuxDate.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) +
                    " de " + sAuxDate.get(Calendar.YEAR) +
                    " a " + ampmS + sAuxDate.get(Calendar.HOUR) + ":" + sAuxDate.get(Calendar.MINUTE) +
                    ((sAuxDate.get(Calendar.MINUTE) < 10) ? "0" : "") +
                    ((sAuxDate.get(Calendar.AM_PM) == 0) ? "AM" : "PM") + "\n" +
                    "Termina el mismo día a " + ampmE +
                    eAuxDate.get(Calendar.HOUR) + ":" + eAuxDate.get(Calendar.MINUTE) +
                    ((eAuxDate.get(Calendar.MINUTE) < 10) ? "0" : "") +
                    ((eAuxDate.get(Calendar.AM_PM) == 0) ? "AM" : "PM");
        }
        return aux;
    }

    private List<String> infoToStringList(List lista){
        List<String> auxList = new ArrayList<String>();
        String parsedInfo = "";
        for (Object item : lista){
            if (item instanceof Evento.Organizer){
                Evento.Organizer aux = (Evento.Organizer) item;
                auxList.add(aux.getName());
            } /*else if (item instanceof Evento.Program){
                Evento.Program aux = (Evento.Program) item;
                if (size == 1){ // Solo es un día

                }else { // Son 2 o más días
                    List<Evento.Program.Activity> actListAux = aux.getActivityList();
                    parsedInfo = parsedInfo + "Día " + i + "\n-";
                    for (Evento.Program.Activity actividad : actListAux){
                        parsedInfo = parsedInfo + actividad.getSpan() + " " +
                                actividad.getName() + "\n-";
                        // Para no poner el '-' si es el último elemento
                        //if (actListAux.indexOf(actividad) == actListAux.size()-1){ // Es la última
                         //   parsedInfo = parsedInfo + actividad.getSpan() + " " +
                         //           actividad.getName() + "\n\t";
                        //}
                        //parsedInfo = parsedInfo + actividad.getSpan() + " " +
                         //       actividad.getName() + "\n\t";
                    }
                    parsedInfo.substring(0, parsedInfo.length()-1); // Quita el último '-'
                }
            }*/
            else if (item instanceof Evento.Sponsor){
                Evento.Sponsor auxAct = (Evento.Sponsor) item;
                auxList.add(auxAct.getName());
            }
        }
        return auxList;
    }

    public Bitmap getMapaDibujado(ServerMapResponse.RespData data) {
        List<OldStand> listaOldStands = data.getOldStandList();
        BitmapFactory.Options myOptions = new BitmapFactory.Options();
        myOptions.inDither = true;
        myOptions.inScaled = false;
        myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
        //myOptions.inPurgeable = true;
        Bitmap bitmap;
        if(tablet) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.plano_cec_landscape, myOptions);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.plano_cec_portrait, myOptions);
        }
        if(listaOldStands.isEmpty())   // No hay OldStands para dibujar
            return bitmap;
        else {  // Dibuja los stands en la imágen
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStrokeWidth(5);
            if(data.getFill() != null) {
                paint.setStyle( (data.getFill()) ? Paint.Style.FILL : Paint.Style.STROKE );
                if(data.getFill()) {
                    paint.setStyle(Paint.Style.FILL);
                }
                else {
                    paint.setStyle(Paint.Style.STROKE);
                }
            } else {
                paint.setStyle(Paint.Style.STROKE);
            }

            Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
            Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvas = new Canvas(mutableBitmap);

            Rect rect;
            int x, y, w, h, xOrigNotTablet;
            xOrigNotTablet = Constants.H - Constants.Y_OFFSET;
            for (OldStand stand : listaOldStands) {
                OldStand.Color color = stand.getColor();
                OldStand.Coordenadas coord = stand.getCoordenadas();
                x = coord.getX();
                y = coord.getY();
                w = coord.getW();
                h = coord.getH();

                paint.setARGB(color.getAlpha(), color.getR(), color.getG(), color.getB());
                if(stand.getFill() != null){
                    paint.setStyle( (stand.getFill()) ? Paint.Style.FILL : Paint.Style.STROKE );
                }
                if (tablet) {
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
            if(tablet){
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
