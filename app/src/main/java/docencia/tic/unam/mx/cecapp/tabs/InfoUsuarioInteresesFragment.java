package docencia.tic.unam.mx.cecapp.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.adapters.InterestListArrayAdapter;
import docencia.tic.unam.mx.cecapp.models.ItemListaInteres;

public class InfoUsuarioInteresesFragment extends Fragment implements CustomFragmentLifeCycle {
    public static final String INTEREST_KEY_SUBJECT = "TEMA";
    public static final String INTEREST_KEY_STATUS = "CHECK";
    public static final String INTEREST_KEY_ID = "ID";
    private static final byte ALL = 0;
    private static final byte FROM = 1;
    private static final byte NOT = 3;
    private static final byte OTHER = 4;
    private final byte INCLUSIVE = 1;
    private final byte EXCLUSIVE = 0;
    private static ProgressBar interesPB;
    private static ListView interesLV;
    private static SharedPreferences interestsUsuario;
    private static ArrayList<ItemListaInteres> auxData;
    public static ArrayList<ItemListaInteres> data;
    private static byte use;
    private static InterestListArrayAdapter adapter;
    private final String DATA = "Data";

    // TODO Checar si sirve tener la variable auxData (solo quedarse con data)


//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //setRetainInstance(true);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_info_usuario_intereses, container, false);
        auxData = new ArrayList<>();

//        Log.w(">>>InfoUsuarioIn...", " :: onCreateView savedInstanceState is " + (savedInstanceState == null ? "null" : "not null"));

        interesLV = (ListView) rootView.findViewById(R.id.interes_lv);
        interesPB = (ProgressBar) rootView.findViewById(R.id.interes_pb);

        interestsUsuario = getActivity().getSharedPreferences(
                Constants.USER_INTERESTS_PREFERENCES, Context.MODE_PRIVATE);
        if(interestsUsuario.getString(INTEREST_KEY_SUBJECT + "1", "").equals("")) {   // Checa si no tiene registros previos
            subjectListUpdate(interestsUsuario, ALL, 0);
            use = ALL;
        }
        else{
            use = OTHER;
            setAuxDataWithSavedPreferences(interestsUsuario);
            data = new ArrayList<>(auxData);
            if(savedInstanceState != null) {
                boolean[] array = savedInstanceState.getBooleanArray(DATA);
                for(int x = 0; x < array.length; x++){
//                    Log.i(">>>onSaveInstanceState", "load x->"+x+"="+array[x]);
                    data.get(x).setCheck(array[x]);
                }
            }
            interesPB.setVisibility(ProgressBar.INVISIBLE);
//            Log.w(">>>InfoUsuarioIn...", " -> auxData.size " + data.size() + " "+interesLV);
            adapter = new InterestListArrayAdapter(getContext(), data);
            interesLV.setAdapter(adapter);
            interesLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Log.i(">>>onSaveInstanceState", "Prev " +  data.get(position).isChecked());
                    data.get(position).setCheck(!data.get(position).isChecked());
//                    Log.i(">>>onSaveInstanceState", "Post " +  data.get(position).isChecked());
                    adapter.notifyDataSetChanged();
//                    Log.i(">>>onSaveInstanceState", "notifyDataChanged " +  data.get(position).isChecked());
                }
            });
        }
        //Log.i(">>>InfoUsuarioIn...","onCreateView " + interesLV);
//        //Log.i(">>> onCreateView", "interesPB " + interesPB);
//        Log.i(">>> onCreateView", "interesUsuario " + interestsUsuario);
        // TODO establecer que pase cuando se actualice
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //Log.w(">>>InfoUsuarioIn...", "onAttach " + interesLV);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        //Log.w(">>>InfoUsuarioIn...", "onSaveInstanceState " + interesLV);
        boolean[] array = new boolean[data.size()];
        int x = 0;
        for(ItemListaInteres item : data){
//            Log.i(">>>onSaveInstanceState", "x->"+x+"="+item.isChecked());
            array[x] = item.isChecked();
            x++;
        }
        outState.putBooleanArray(DATA, array);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResumeFragment() {
        //Log.w(">>>InfoUsuarioIn...", "onResumeFragment " + interesLV);
    }

    @Override
    public void onResume() {
        super.onResume();
        //Log.w(">>>InfoUsuarioIn...", "onResume " + interesLV);
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.w(">>>InfoUsuarioIn...", "onPause " + interesLV);
    }

    @Override
    public void onPauseFragment() {
        //Log.w(">>>InfoUsuarioIn...", "onPauseFragment " + interesLV);
    }

    protected static void setAuxDataWithSavedPreferences(SharedPreferences savedPreferences){
        //Log.w(">>>InfoUsuarioIn...", "setAuxDataWithSavedPreferences BC " + auxData.size());
        auxData.clear();    //Borramos los datos para evitar que haya un 'overflow'
        //Log.w(">>>InfoUsuarioIn...", "AC " + auxData.size());
        String auxSubject;
        Boolean auxState;
        int auxID;
        int x;

        for (x = 1; true; x++) {
            auxSubject = savedPreferences.getString(INTEREST_KEY_SUBJECT + x,"");
            //Log.w(">>>InfoUsuarioIn...", auxSubject);
            if(!auxSubject.equals("")){
                auxState = savedPreferences.getBoolean(INTEREST_KEY_STATUS + x, false);
                auxID = savedPreferences.getInt(INTEREST_KEY_ID + x, 0);
                auxData.add(new ItemListaInteres(auxID, auxSubject, auxState));
            }else {
                break;
            }
        }
        //Log.w(">>>InfoUsuarioIn...", "AF " + auxData.size());
    }

    public static boolean stringToBoolean(String str){ // '1' - true | 'cualquier otro' - false
        if(str.equals("1"))
            return true;
        else
            return false;
    }

    //private void subjectListUpdate(SharedPreferences prefs, byte wich, int... position){ //Para cuando se use el filtro inclusivo
    private void subjectListUpdate(SharedPreferences prefs, byte wich, int position){
        switch (wich){
            case ALL:
                AsyncHttpRetriever interesAHR_ALL = new AsyncHttpRetriever(interesPB, getContext(),
                        //Constants.MODE_INTEREST_LIST, interesLV, getRequestParams(wich, 0, null),prefs);
                        Constants.MODE_INTEREST_LIST, interesLV, null, prefs);
                interesAHR_ALL.postCommand(Constants.BASE_LINK_INTERESTS);
                break;
            case FROM:
                int x;
                for(x = 1; true; x++){
                    if(prefs.getString(INTEREST_KEY_SUBJECT + x, "").equals(""))
                        break;
                }
                AsyncHttpRetriever interesAHR_FROM = new AsyncHttpRetriever(interesPB, getContext(),
                        Constants.MODE_INTEREST_LIST, interesLV, getRequestParams(wich, x, null),
                        prefs, getKeyValueMap(wich,x));
                interesAHR_FROM.postCommand(Constants.BASE_LINK_INTERESTS);
                break;
        }
    }

    private RequestParams getRequestParams(byte wich, int position, int ids[] ){
        RequestParams params = new RequestParams();
        switch (wich){
            case ALL:
                //No se manda ningún parámetro
                break;
            case FROM:
                params.put(Constants.SERVER_KEY_INTERESTS_FILTER, getIDsHasString(position));
                params.put(Constants.SERVER_KEY_INTERESTS_INCLUSIVE, EXCLUSIVE);
                break;
            case NOT:
                //hacer para cuando se quiera inclusivo
                break;
        }
        return params;
    }

    private String getIDsHasString(int pos){
        String sIds = "";
        for(int x = 1; x < pos; x++){
            sIds = sIds + x + ",";
        }
        sIds = sIds.substring(0, sIds.length()-1);  // Quita el último ','
        return sIds;
    }

    private HashMap<String,String> getKeyValueMap(byte wich, int pos){
        HashMap<String,String> aux = new HashMap<>();
        switch (wich){
            case ALL:
                //No debería entrar aquí
                break;
            case FROM:
                aux.put(Constants.SERVER_KEY_INTERESTS_FILTER, getIDsHasString(pos));
                aux.put(Constants.SERVER_KEY_INTERESTS_INCLUSIVE, Integer.toString(EXCLUSIVE));
                break;
            case NOT:
                //hacer para cuando se quiera inclusivo
                break;
        }
        return aux;
    }
    /** Se quitó la parte de anular cambios por simplicidad, estaba causando muchos problemas
     *  se modificaba la información al inicializar [dataToSave = new ArrayList<>(auxData);] o
     *  [dataToSave.addAll(auxData);]*/
    public static void saveChanges(){
        /** Se pueden ahorrar métodos estáticos repitiendo el código que está en
         * setAuxDataWithSavedPreferences() aquí (se quitaría ese y el de
         * stringToBoolean)*/
        //Log.i(">>>InfoUsuarioIn...", "saveChanges " + interesLV);
//        adapter.notifyDataSetChanged();
        int x;
        InterestListArrayAdapter.ViewHolder viewHolder;
        //ArrayList<ItemListaInteres> dataToSave = new ArrayList<>();
        ArrayList<ItemListaInteres> dataToSave;
        ItemListaInteres item;
        View view = null;
        switch (use){
            case OTHER:
                setAuxDataWithSavedPreferences(interestsUsuario);
                //Log.i("<< Debb auxData 0 -", String.valueOf(auxData.get(0).isChecked()));
                // Se llama con getAdapter porque no necesariamnente tiene el adapter de aquí
                // puede tener el adapter del AsyncHttpRetriever
//                dataToSave = new ArrayList<>(auxData);
//                dataToSave.addAll(auxData);
                //Log.i(">>>InfoUsuarioIn...", "size "+ dataToSave.size());
                //Log.i(">>>InfoUsuarioIn...", "Pos "+ interesLV.getCheckedItemPositions());
//                for (x = 0; x < dataToSave.size(); x++) {
////                    view = adapter.getView(x, view, interesLV);
////                    viewHolder = (InterestListArrayAdapter.ViewHolder) view.getTag();
////                    Log.i(">>>InfoUsuarioIn...", "for " +x+" viewHolder "+ viewHolder.status.isChecked());
//
////                    Log.i(">>>InfoUsuarioIn...", "for " + x +" child "+ interesLV.getChildAt(x));
////                    viewHolder = (InterestListArrayAdapter.ViewHolder)interesLV.getChildAt(x).getTag();
//                    item = (ItemListaInteres) interesLV.getItemAtPosition(x);
//
//                    Log.i(">>>InfoUsuarioIn...", "for " + x +" child "+ item.isChecked());
//                    //viewHolder = (InterestListArrayAdapter.ViewHolder)interesLV.getChildAt(x).getTag();
//                    //dataToSave.get(x).setCheck(viewHolder.status.isChecked());
//                    dataToSave.get(x).setCheck(item.isChecked());
//                }
                // Aquí ya se tienen los checkboxes e id's actualizados (información a guardar)
                for (x = 0; x < data.size(); x++) {
                    Log.i(">>> onSave", "x->" + x+ "=" + data.get(x).isChecked());
                    interestsUsuario.edit()
                            .putBoolean(INTEREST_KEY_STATUS + (x + 1), data.get(x).isChecked())
                            .apply();
                }
                //Log.i("<< Debb datatoSave 0 +", String.valueOf(dataToSave.get(0).isChecked()));
                break;
            case ALL:
                setAuxDataWithSavedPreferences(AsyncHttpRetriever.sharedPrefs);
                //Log.i("<< Debb auxData 0 -", String.valueOf(auxData.get(0).isChecked()));
//                dataToSave = new ArrayList<>(auxData);
                //dataToSave.addAll(auxData);
//                for (x = 0; x < dataToSave.size(); x++) {
//                    viewHolder = (InterestListArrayAdapter.ViewHolder)interesLV.getChildAt(x).getTag();
//                    dataToSave.get(x).setCheck(viewHolder.status.isChecked());
//                }
                // Aquí ya se tienen los checkboxes actualizados (información a guardar)
                for (x = 0; x < data.size(); x++) {
                    AsyncHttpRetriever.sharedPrefs.edit().putBoolean(INTEREST_KEY_STATUS + (x + 1),
                            data.get(x).isChecked()).apply();
                }
                //dataToSave.addAll(auxData);
                break;
            case FROM:

                dataToSave = new ArrayList<>(auxData);
                break;
            case NOT:

                dataToSave = new ArrayList<>(auxData);
                break;
        }

        //Log.i("<< Debb auxData 0 +", String.valueOf(auxData.get(0).isChecked()));
    }

    /** No se usan los siguientes métodos */

    public static void deleteChanges() {
        /** Se quitó esto porque me daba mucha lata
         *  - Al inicializart dataToSave por alguna razón linkeaba auxData
         *    con dataToSave*/
        Log.i("<< Debb", "deleteChanges");
        switch (use) {
            case OTHER:
                for (int x = 0; x < auxData.size(); x++) {
                    //Log.i("<< Debb delete " + x, String.valueOf(auxData.get(x).isChecked()));
                    interestsUsuario.edit().putBoolean(INTEREST_KEY_STATUS + (x + 1),
                            auxData.get(x).isChecked()).apply();
                }
                ((InterestListArrayAdapter)interesLV.getAdapter()).notifyDataSetChanged();
                break;
            case ALL:
                /*
                for (int x = 0; x < auxData.size(); x++) {
                    interestsUsuario.edit().putBoolean(INTEREST_KEY_STATUS + (x + 1),
                            auxData.get(x).isChecked()).apply();
                }*/
                break;
            case FROM:

                break;
            case NOT:

                break;
        }
        //setUserInterestInfo(auxData);
        //Log.i("<< Debb setUser", String.valueOf(auxData.isEmpty()));
        auxData.clear();
    }

    public static void setUserInterestInfo(ArrayList<ItemListaInteres> listaInteresConfig){
        int x;
        InterestListArrayAdapter.ViewHolder viewHolder;
        Log.i("<< Debb setUser " + use, String.valueOf(listaInteresConfig.isEmpty()));
        switch (use) {
            case OTHER:
                for (x = 0; x < listaInteresConfig.size(); x++) {
                    viewHolder = (InterestListArrayAdapter.ViewHolder) interesLV.getChildAt(x).getTag();
                    viewHolder.status.setChecked(listaInteresConfig.get(x).isChecked());
                }
                interesLV.invalidateViews();
                break;
            case ALL:
                Log.i(">> Debbug size All", Integer.toString(listaInteresConfig.size()));
                for (x = 0; x < listaInteresConfig.size(); x++) {
                    Log.i(">> Debbug x All", Integer.toString(x));
                    viewHolder = (InterestListArrayAdapter.ViewHolder) AsyncHttpRetriever
                            .lvListaIntereses.getChildAt(x).getTag();
                    viewHolder.status.setChecked(listaInteresConfig.get(x).isChecked());
                }
                break;
            case FROM:

                break;
            case NOT:

                break;
        }
    }
}

