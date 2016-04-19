package docencia.tic.unam.mx.cecapp.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.R;

public class EventoInfoFragment extends Fragment implements CustomFragmentLifeCycle {
    private ProgressBar mProgressBar;
    private long id;
    private ExpandableListView expListView;
    private List<String> infoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_evento_info, container, false);

        id = getArguments().getLong(Constants.ID_EVENT);
        infoList = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.category_array)));
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.evento_info_progressBar);
        expListView = (ExpandableListView) rootView.findViewById(R.id.elv_category_list);
        AsyncHttpRetriever mAHR = new AsyncHttpRetriever(mProgressBar, getActivity(),
                Constants.MODE_EVENT_INFO, expListView, getRequestParams(), infoList);
        mAHR.postCommand(Constants.BASE_LINK_EVENT);

        return rootView;
    }

    private RequestParams getRequestParams() {
        RequestParams params = new RequestParams();
        params.put(Constants.SERVER_KEY_EVENT_ID, id);

        //Log.i(">>> param, ID",">" + Constants.SERVER_KEY_EVENT_ID + " " + Long.toString(id));
        return params;
    }

    @Override
    public void onPauseFragment() {
        //
    }

    @Override
    public void onResumeFragment() {
        //
    }
}
