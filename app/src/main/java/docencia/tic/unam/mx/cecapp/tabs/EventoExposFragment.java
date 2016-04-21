package docencia.tic.unam.mx.cecapp.tabs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.IntentEvento;
import docencia.tic.unam.mx.cecapp.IntentMapa;
import docencia.tic.unam.mx.cecapp.IntentSimpleMap;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.adapters.CustomExpandableListAdapter;
import docencia.tic.unam.mx.cecapp.models.Evento;

public class EventoExposFragment extends Fragment implements CustomFragmentLifeCycle {
    private ProgressBar mProgressBar;
    private ExpandableListView eListView;
    private boolean eventStatus;
    private LinkedHashMap<String, List<String>> mapExpandableList = new LinkedHashMap<String, List<String>>();
    private Activity context;
    private Activity fragmentActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_evento_expos, container, false);
        //eListView = (ExpandableListView) rootView.findViewById(R.id.elv_activities);
        //context = getActivity();
        // TODO ver si cambiar esto de poner auxiliares en Constants
        // ↑ se resolvería con poner los campos estáticos aquí (al parecer el ciclo de vida no afecta a lo estático)
        Constants.setAuxExpandableList((ExpandableListView) rootView.findViewById(R.id.elv_activities));
        Constants.setAuxContext(getActivity());

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Constants.setAuxFragmentActivity((Activity) context);
        /*if (context instanceof Activity){
            fragmentActivity = (Activity) context;
        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onPauseFragment() {
        //
    }

    @Override
    public void onResumeFragment() {
        eListView = Constants.getAuxExpandableList();
        context = Constants.getAuxContext();
        fragmentActivity = Constants.getAuxFragmentActivity();
        if(AsyncHttpRetriever.evento != null) {
            final List<Evento.Program> mProgramList = AsyncHttpRetriever.evento.getProgramList();
            List<String> groupList = new ArrayList<>();
            for(Evento.Program programa : mProgramList) {
                groupList.add(programa.getDate());
                mapExpandableList.put(programa.getDate(), getActivitiesAsStringList(programa.getActivityList()));
            }
            final CustomExpandableListAdapter expListAdapter = new CustomExpandableListAdapter(
                    context, groupList, mapExpandableList, Constants.SOURCE_EVENTO_EXPOS);
            eListView.setAdapter(expListAdapter);

            eListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    Intent intent = new Intent(fragmentActivity, IntentSimpleMap.class);
                    intent.putExtra(Constants.ID_EVENT, AsyncHttpRetriever.evento.getId());
                    int activityId = 1;
                    for (int x = 0; x <= groupPosition; x++) {
                        if (x > 0){
                            activityId += mProgramList.get(x-1).getActivityList().size();
                        }
                        else
                            activityId += childPosition;
                    }
                    intent.putExtra(Constants.ID_ACTIVITY, AsyncHttpRetriever.evento.getActivityIdByPosition(activityId));
                    intent.putExtra(Constants.NAME_ACTIVITY, AsyncHttpRetriever.evento.getActivityNameByPosition(activityId));
                    fragmentActivity.startActivity(intent);
                    return true;
                }
            });
        } else {
            // TODO Hacer algo si no hay evento cargado
        }
    }

    public List<String> getActivitiesAsStringList(List<Evento.Program.Activity> listaActividades) {
        List<String> activitiesListString = new ArrayList<>();
        String temp;

        for(Evento.Program.Activity actividad : listaActividades){
            temp = "" + actividad.getStartTime() + "-" + actividad.getEndTime() +
                    "\t" + actividad.getName();
            activitiesListString.add(temp);
        }
        return activitiesListString;
    }

    private RequestParams getRequestParams(){
        RequestParams params = new RequestParams();
        //params.put(Constants.SERVER_KEY_EVENT_LIST, );
        return params;
    }

    /*public long getEventId() {
        long id;

        return id;
    }*/
}
