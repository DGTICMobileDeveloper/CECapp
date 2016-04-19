package docencia.tic.unam.mx.cecapp.SocialNetworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;

public class TwitterFragment extends Fragment{

    private TwitterLoginButton twitterButton;
    private TextView twMsg;
    private final int TWITTER_REQUESTCODE = 140;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        int i = getArguments().getInt(Constants.NAV_DRAW_PAGE);
        getActivity().setTitle(Constants.NAV_DRAW_ITEMS[i]);

        rootView = inflater.inflate(R.layout.profile_tw, container, false);
        twMsg = (TextView) rootView.findViewById(R.id.tw_logedin);
        // Botón "Login" de Twitter
        twitterButton = (TwitterLoginButton) rootView.findViewById(R.id.login_button_tw);
        if(TwitterCore.getInstance().getSessionManager().getActiveSession() != null){
            twitterButton.setVisibility(TwitterLoginButton.INVISIBLE);
        } else {
            twMsg.setVisibility(TextView.INVISIBLE);
        }
        // Definiendo el manejador del botón
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                twMsg.setVisibility(TextView.VISIBLE);
                twitterButton.setVisibility(TwitterLoginButton.INVISIBLE);
                Toast.makeText(getContext(),
                        "Sesión de Twitter iniciada",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getContext(),
                        "Sesión de Twitter fallida",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("Twitter Login button", "rqC:" + requestCode + " rtC:" + resultCode);
        // Pasa el resultado de la actividad al manejador correspondiente
        if(requestCode == TWITTER_REQUESTCODE) {
            twitterButton.onActivityResult(requestCode, resultCode, data);
        }
    }
}
