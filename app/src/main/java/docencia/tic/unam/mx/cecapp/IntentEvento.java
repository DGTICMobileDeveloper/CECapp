package docencia.tic.unam.mx.cecapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.loopj.android.http.RequestParams;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.adapters.MainPageAdapter;

public class IntentEvento extends AppCompatActivity {
    public static boolean estadoEvento;  // Preinscrito o no
    private int mEstadoEventoIcon;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainPageAdapter mPageAdapter;
    private Bundle extras;
    protected static Drawable preInsDrawable;
    protected static Drawable quitInsDrawable;
    protected static FloatingActionButton floatingActionButton;
    private boolean canPresentShareDialog;
    private boolean canPresentShareDialogWithPhotos;
    private boolean isFaceBookOn;
    private boolean isTwitterOn;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    // El callback de las cosas que se comparten en FB
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("Facebook", "Post Canceled");
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
            new AlertDialog.Builder(IntentEvento.this)
                    .setTitle(title)
                    .setMessage(alertMessage)
                    .setPositiveButton("Ok", null)
                    .show();
        }
    };

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_evento);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            quitInsDrawable = getResources().getDrawable(R.drawable.ic_3, IntentEvento.this.getTheme());
            preInsDrawable = getResources().getDrawable(R.drawable.ic_1, IntentEvento.this.getTheme());
        }else {
            quitInsDrawable =  getResources().getDrawable(R.drawable.ic_3);
            preInsDrawable = getResources().getDrawable(R.drawable.ic_1);
        }

        extras = getIntent().getExtras();
        if(extras == null)
            extras = Constants.getAuxBundle();
        else
            Constants.setAuxBundle(extras);
        getSupportActionBar().setTitle(extras.getString(Constants.NAME_EVENT));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.evento_fab);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(
                Constants.USER_INFO_PREFERENCES, Context.MODE_PRIVATE);
        final long user_id = sharedPreferences.getLong(Constants.USER_ID, -1);
        final long event_id = extras.getLong(Constants.ID_EVENT);
        estadoEvento = extras.getBoolean(Constants.IS_REGISTERED);

        // TODO: Ver que remover el Item del menú (flecha) o el FloatingActionButton
        //TODO: Checar que conviene más: Solicitar la info del evento desde aquí o desde el fragment EventoInfoFragment (aqui por el momento)

        if(user_id == -1){  // No tiene un ID asignado, por lo tanto no puede inscribir eventos
            floatingActionButton.setVisibility(FloatingActionButton.INVISIBLE);
            Toast.makeText(this, "No te has registrado aún, por lo cual no puedes registrarte al evento", Toast.LENGTH_SHORT).show();
        } else {
            setFabDrawable();
            AsyncHttpRetriever mAHR = new AsyncHttpRetriever(Constants.MODE_REGISTER_USER_TO_EVENT,
                    getRequestParams(user_id, event_id), IntentEvento.this);
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!estadoEvento) {
                        AsyncHttpRetriever mAHR = new AsyncHttpRetriever(Constants.MODE_REGISTER_USER_TO_EVENT,
                                getRequestParams(user_id, event_id), IntentEvento.this);
                        mAHR.postCommand(Constants.BASE_LINK_REGISTER_USER_TO_EVENT);

                        Snackbar.make(view, "Se mandó la petición de preregistro", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    } else {
                        // TODO poner acción para anular preinscripción
                        AsyncHttpRetriever mAHR = new AsyncHttpRetriever(Constants.MODE_REMOVE_USER_FROM_EVENT,
                                getRequestParams(user_id, event_id), IntentEvento.this);
                        mAHR.postCommand(Constants.BASE_LINK_REMOVE_USER_FROM_EVENT);
                        Snackbar.make(view, "Se mandó la petición de anular preregistro", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
            });
        }

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.evento_viewpager);
        mPageAdapter = new MainPageAdapter(getSupportFragmentManager(),
                IntentEvento.this, Constants.TAB_TITLES_EVENT.length, Constants.SOURCE_EVENT,
                extras.getLong(Constants.ID_EVENT));
        viewPager.setAdapter(mPageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            int currentPosition = 0;

            @Override
            public void onPageSelected(int newPosition) {

                CustomFragmentLifeCycle fragmentToHide = (CustomFragmentLifeCycle) mPageAdapter.getItem(currentPosition);
                fragmentToHide.onPauseFragment();

                CustomFragmentLifeCycle fragmentToShow = (CustomFragmentLifeCycle) mPageAdapter.getItem(newPosition);
                fragmentToShow.onResumeFragment();

                currentPosition = newPosition;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.evento_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /** Redes sociales*/
//        if(AccessToken.getCurrentAccessToken() != null ||
//                Profile.getCurrentProfile() != null){
//            isFaceBookOn = true;
//        } else {
//            isFaceBookOn = false;
//        }
        isFaceBookOn = (AccessToken.getCurrentAccessToken() != null || Profile.getCurrentProfile() != null);
        isTwitterOn = (TwitterCore.getInstance().getSessionManager().getActiveSession() != null);
        if(isFaceBookOn) {
            callbackManager = CallbackManager.Factory.create();
            // Elemento que se puede utilizar al compartir (Como el preview)
            shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(
                    callbackManager,
                    shareCallback);
            // Can we present the share dialog for regular links?
            canPresentShareDialog = ShareDialog.canShow(ShareLinkContent.class);
            // Can we present the share dialog for photos?
            canPresentShareDialogWithPhotos = ShareDialog.canShow(SharePhotoContent.class);
        }
    }
    //POSITION sirve para guardar la pestaña seleccionada antes de un cambio y recuperarla

    public static void setFabDrawable(){
        if(estadoEvento)
            floatingActionButton.setImageDrawable(quitInsDrawable);
        else
            floatingActionButton.setImageDrawable(preInsDrawable);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(extras == null)
            Log.i("<<< extras en OnSave","null");
        else
            Log.i("<<< extras en OnSave", "not null");
        outState.putAll(extras);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.secondary, menu);
        // Para cambiar lo que dice el submenú
//        MenuItem fbItem = menu.findItem(R.id.compartir_fb);
//        fbItem.setTitle((isFaceBookOn ? getResources().getString(R.string.share_fb):getResources().getString(R.string.share_fb_not)));
//        MenuItem twItem = menu.findItem(R.id.compartir_tw);
//        twItem.setTitle((isTwitterOn ? getResources().getString(R.string.share_tw):getResources().getString(R.string.share_tw_not)));
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.evento_mapa:
//                if(!estadoEvento)
//                    Toast.makeText(this, "Preinscrito >", Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(this, "Removido  ><", Toast.LENGTH_SHORT).show();
//                estadoEvento = !estadoEvento;
//                invalidateOptionsMenu();
                Intent intent = new Intent(this, IntentMapa.class);
                intent.putExtra(Constants.ID_EVENT, AsyncHttpRetriever.evento.getId());
                this.startActivity(intent);
                return true;
            case R.id.compartir_fb:
                if(isFaceBookOn){
                    Bitmap image = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_splash_screen);

                    SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(image).build();
                    ArrayList<SharePhoto> photos = new ArrayList<>();
                    photos.add(sharePhoto);

                    SharePhotoContent sharePhotoContent =
                            new SharePhotoContent.Builder()
                                    .setPhotos(photos).setPlaceId("295698220624920")
//                                    .setContentUrl(Uri.parse(Constants.CEC_WEB_PAGE))
                                    .build();
                    if (canPresentShareDialogWithPhotos) {
                        shareDialog.show(sharePhotoContent);
                    } else {
                        ShareApi.share(sharePhotoContent, shareCallback);
                    }
                }else {
                    Toast.makeText(this, getResources().getString(R.string.share_fb_not),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.compartir_tw:
                if(isTwitterOn){
                    final TweetComposer.Builder builder = new TweetComposer.Builder(this)
                            .text("#CEC_UNAM");
//                    try {
//                        URL url;
//                        url = new URL(Constants.CEC_WEB_PAGE);
//                        builder.url(url);
//                    } catch (MalformedURLException e){
//                        Log.i(">>> Twitter", e.getMessage());
//                    }
                    builder.show();
                }else {
                    Toast.makeText(this, getResources().getString(R.string.share_tw_not),
                            Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

//        MenuItem mPreinsc = menu.findItem(R.id.evento_mapa);
//        if(estadoEvento)
//            mEstadoEventoIcon = R.drawable.ic_3;
//        else
//            mEstadoEventoIcon = R.drawable.ic_1;
//        mPreinsc.setIcon(ContextCompat.getDrawable(this, mEstadoEventoIcon));

        return super.onPrepareOptionsMenu(menu);
    }

    public RequestParams getRequestParams(long usr_id, long event_id){
        RequestParams params = new RequestParams();
        String param = "" + usr_id + "," + event_id;
        params.put(Constants.SERVER_KEY_REGISTER_USER_TO_EVENT, param);
        return params;
    }
}
