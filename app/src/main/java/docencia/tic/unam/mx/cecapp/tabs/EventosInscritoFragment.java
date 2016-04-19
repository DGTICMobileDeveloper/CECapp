package docencia.tic.unam.mx.cecapp.tabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;

import java.util.List;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.R;

public class EventosInscritoFragment extends Fragment {
    private ProgressBar mProgressBar;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_eventos_inscrito, container, false);
        TextView mTextView = (TextView) rootView.findViewById(R.id.tvEventosInscrito);
        mTextView.setVisibility(TextView.INVISIBLE);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarInscrito);
        mListView = (ListView) rootView.findViewById(R.id.lvEventosInscrito);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                Constants.USER_INFO_PREFERENCES, Context.MODE_PRIVATE);
        long user_id = sharedPreferences.getLong(Constants.USER_ID, -1);

        if(user_id != -1) { // Ya tiene un ID (ya se registró)
            AsyncHttpRetriever mAHR = new AsyncHttpRetriever(mProgressBar, getActivity(),
                    Constants.MODE_GET_USER_EVENTS, mListView, getRequestParams(user_id), mTextView);
            mAHR.postCommand(Constants.BASE_LINK_GET_USER_EVENTS);
        } else {    // Aún no tiene un ID
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            mListView.setVisibility(ListView.INVISIBLE);
            mTextView.setVisibility(TextView.VISIBLE);
            mTextView.setText(getResources().getString(R.string.no_id_msg));
        }
        return rootView;
    }

    private RequestParams getRequestParams(long id){
        RequestParams params = new RequestParams();
        params.put(Constants.SERVER_KEY_GET_USER_EVENTS, id);
        return params;
    }
}
