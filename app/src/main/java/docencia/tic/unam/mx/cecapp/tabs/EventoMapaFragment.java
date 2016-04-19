package docencia.tic.unam.mx.cecapp.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;

public class EventoMapaFragment extends Fragment {
    private ProgressBar mProgressBar;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_evento_mapa, container, false);
        /*
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarGeneral);
        mListView = (ListView) rootView.findViewById(R.id.lvEventosGeneral);
        AsyncHttpRetriever mAHR = new AsyncHttpRetriever(mProgressBar, getContext(),
                Constants.MODE_EVENT_LIST, mListView, getRequestParams());
        mAHR.postCommand(Constants.BASE_LINK_EVENT_LIST);
        */
        return rootView;
    }

    private RequestParams getRequestParams(){
        RequestParams params = new RequestParams();
        params.put(Constants.SERVER_KEY_EVENT_LIST, Constants.SERVER_PARAM_TRUE);
        params.put(Constants.SERVER_KEY_CATEGORY, Constants.SERVER_PARAM_EL_GENERAL);
        return params;
    }
}
