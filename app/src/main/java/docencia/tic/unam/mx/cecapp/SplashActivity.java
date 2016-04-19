package docencia.tic.unam.mx.cecapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.FacebookSdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import java.util.Random;


public class SplashActivity extends AppCompatActivity {
    //public final static String CRASHLYTICS_KEY_CRASHES = "are_crashes_enabled";

    private static SplashActivity singleton;
    private TwitterAuthConfig authConfig;
    //private Typeface avenirFont;

    /** Llaves para twitter */
    private static final String TWITTER_KEY = "xxx";
    private static final String TWITTER_SECRET = "xxxx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Inicialización para las librerías de las redes sociales */
        authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        FacebookSdk.sdkInitialize(getApplicationContext());


        //TODO checar si sirve este singleton
        singleton = this;
        //extractAvenir();

        /**TODO Si quieres que se guarde el estado en el MainActivity
         * -Descomenta en el Manifest ln 26 y pasalo a la ln 24, el problema
         *  es que causa que no se actualicen (destruyan y vuelvan a crear)
         *  las pestañas de listas de eventos, lo que causa que al inscribir
         *  un nuevo evento no se vea reflejado inmediatamente en la lista
         *  se debe pasar a otro 'item/tab' del navDrawer o perder focus
         *  para que se actualize
         */

        /** Se cargan DateFormatAray y UserInfoPrefsKeys a la clase Constants*/
        Constants.setDateFormatArray(getResources().getStringArray(R.array.date_format_array));
        String[] categoryUserPrefs = {getResources().getString(R.string.usr_name),
                getResources().getString(R.string.usr_lastName),
                getResources().getString(R.string.usr_academic_provenance),
                getResources().getString(R.string.usr_birth_date),
                getResources().getString(R.string.usr_gender),
                getResources().getString(R.string.usr_num_cta),
                getResources().getString(R.string.usr_academic_grade),
                getResources().getString(R.string.usr_mail),
                getResources().getString(R.string.usr_phone_number),
                getResources().getString(R.string.usr_adress)};
        Constants.setUserInfoPrefsKeys(categoryUserPrefs);

        /** Carga el IMEI al archivo de la información del usuario */
        SharedPreferences userInfoIMEI = getApplicationContext().getSharedPreferences(
                Constants.USER_INFO_PREFERENCES, Context.MODE_PRIVATE);
        //TODO poner el método para obtener el IMEI/MEID
        Random rand = new Random();
        int min = 1;
        int max = 32000;
        long randIMEI = rand.nextInt((max - min) + 1) + min;
        userInfoIMEI.edit().putLong(Constants.IMEI_MEID, randIMEI).apply();

        /** Inicia MainActivity */
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public static SplashActivity getInstance() {
        return singleton;
    }
}