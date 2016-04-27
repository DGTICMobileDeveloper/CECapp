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
import android.widget.Toast;

import com.loopj.android.http.RequestParams;

import docencia.tic.unam.mx.cecapp.AsyncHttpRetriever;
import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.MainActivity;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.models.ItemListaInteres;


public class EventosInteresFragment extends Fragment {
    private ProgressBar mProgressBar;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_eventos_interes, container, false);
        TextView mTextView = (TextView) rootView.findViewById(R.id.tvEventosInteres);
        mTextView.setVisibility(TextView.INVISIBLE);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarInteres);
        mListView = (ListView) rootView.findViewById(R.id.lvEventosInteres);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(
                Constants.USER_INTERESTS_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences sharedPreferencesInfo = getActivity().getSharedPreferences(
                Constants.USER_INFO_PREFERENCES, Context.MODE_PRIVATE);

        AsyncHttpRetriever mAHR = new AsyncHttpRetriever(mProgressBar, getContext(),
                Constants.MODE_INTEREST_EVENT_LIST, mListView, getRequestParams(sharedPreferences, sharedPreferencesInfo), mTextView);
        mAHR.postCommand(Constants.BASE_LINK_EVENT_LIST);
        return rootView;
    }

    private RequestParams getRequestParams(SharedPreferences sharedPreferences, SharedPreferences sharedPreferencesInfo){
        RequestParams params = new RequestParams();
        String interests = "";
        for (int x = 1; true; x++) {
            if(!sharedPreferences.getString(InfoUsuarioInteresesFragment.INTEREST_KEY_SUBJECT + x,"").equals("")){
                if(sharedPreferences.getBoolean(InfoUsuarioInteresesFragment.INTEREST_KEY_STATUS + x, false)) {
                    interests = interests + Integer.toString(sharedPreferences.getInt(
                            InfoUsuarioInteresesFragment.INTEREST_KEY_ID + x, 0)) + ",";
                }
            }else {
                if(!interests.equals(""))   // Tiene interéses seleccionados (se quita el último caracter)
                    interests = interests.substring(0, interests.length()-1);
                break;
            }
        }
        Log.i(">>> Interests", Constants.BASE_LINK_EVENT_LIST);
        params.put(Constants.SERVER_KEY_EVENTS_FILTER, interests);
        Log.i(">>> Interests", Constants.SERVER_KEY_EVENTS_FILTER + interests);
        params.put(Constants.SERVER_KEY_GET_USER_EVENTS, sharedPreferencesInfo.getLong(Constants.USER_ID, -1));
        Log.i(">>> Interests", Constants.SERVER_KEY_GET_USER_EVENTS + sharedPreferencesInfo.getLong(Constants.USER_ID, -1));
//        Toast.makeText(getActivity(),Constants.BASE_LINK_EVENT_LIST + "\n" +
//                Constants.SERVER_KEY_EVENTS_FILTER + interests + "\n" +
//                Constants.SERVER_KEY_GET_USER_EVENTS + sharedPreferencesInfo.getLong(Constants.USER_ID, -1),
//                Toast.LENGTH_LONG).show();
        return params;
    }
}
