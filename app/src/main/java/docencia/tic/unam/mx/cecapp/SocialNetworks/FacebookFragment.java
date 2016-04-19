package docencia.tic.unam.mx.cecapp.SocialNetworks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.widget.ShareDialog;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;

public class FacebookFragment extends Fragment {

    CallbackManager callbackManager;
    private ProfilePictureView profilePictureView;
    private ShareDialog shareDialog;

    private LoginButton facebookButton;
    private final int FACEBOOK_REQUESTCODE = 64206;

    /*
    // El callback de las cosas que se comparten en FB
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
            String title = "Error";
            String alertMessage = error.getMessage();
            showResult(title, alertMessage);
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            if (result.getPostId() != null) {
                String title = "success";
                String id = result.getPostId();
                String alertMessage = "Post posteado satisfactoriamente";
                showResult(title, alertMessage);
            }
        }

        private void showResult(String title, String alertMessage) {
            new AlertDialog.Builder(getContext())
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton("Ok", null)
                    .show();
        }
    };
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        int i = getArguments().getInt(Constants.NAV_DRAW_PAGE);
        getActivity().setTitle(Constants.NAV_DRAW_ITEMS[i]);
        rootView = inflater.inflate(R.layout.profile_fb, container, false);
        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.profilePicture);
        Profile profile = Profile.getCurrentProfile();
        if (profile != null) {
            profilePictureView.setProfileId(profile.getId());
        }
        // Sirve para manejar lo que sucede con el botón "Login" de Facebook
        //callbackManager = CallbackManager.Factory.create();
        // Botón "Login" de Facebook
        facebookButton = (LoginButton) rootView.findViewById(R.id.login_button_fb);
        // Permiso de publicar
        facebookButton.setPublishPermissions("publish_actions");
        // Como está en fragment
        facebookButton.setFragment(this);
        // Link entre el manejador y el botón
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(),
                        "Sesión de FB iniciada",
                        Toast.LENGTH_SHORT).show();
                // Actualiza el fragment
                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.detach(FacebookFragment.this).attach(FacebookFragment.this).commit();
                Profile profile = Profile.getCurrentProfile();
                profilePictureView.setProfileId(profile.getId());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(),
                        "Cancelado",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getContext(),
                        "Hubo un error al iniciar FB",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pasa el resultado de la actividad al manejador correspondiente
        if (requestCode == FACEBOOK_REQUESTCODE) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}