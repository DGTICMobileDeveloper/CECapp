package docencia.tic.unam.mx.cecapp.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;

public class EventosGeneralFragment extends Fragment {
    private ProgressBar mProgressBar;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_eventos_general, container, false);
        TextView mTextView = (TextView) rootView.findViewById(R.id.tvEventosGeneral);
        mTextView.setVisibility(TextView.INVISIBLE);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarGeneral);
        mListView = (ListView) rootView.findViewById(R.id.lvEventosGeneral);

//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
//                Constants.USER_INTERESTS_PREFERENCES, Context.MODE_PRIVATE);

        AsyncHttpRetriever mAHR = new AsyncHttpRetriever(mProgressBar, getContext(),
                Constants.MODE_EVENT_LIST, mListView, getRequestParams(), mTextView);
        mAHR.postCommand(Constants.BASE_LINK_EVENT_LIST_MONTH);
        return rootView;
    }

    private RequestParams getRequestParams(){
        RequestParams params = new RequestParams();
        /** TODO
         * Modificar esto para ir solicitando nuevas páginas i.e. page=n
         * Agregar a todos los lugares donde se pidan listas de eventos
         * ↓ Es un dummy
         */
        params.put(Constants.SERVER_KEY_EVENT_LIST_MONTH,
                Constants.SERVER_KEY_EVENT_LIST_MONTH_C);
        return params;
    }
}
