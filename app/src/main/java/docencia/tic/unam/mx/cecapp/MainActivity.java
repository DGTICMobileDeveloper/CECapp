package docencia.tic.unam.mx.cecapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import docencia.tic.unam.mx.cecapp.models.ServerMapsResponse;
import docencia.tic.unam.mx.cecapp.navdrawitems.AcercaDeItem;
import docencia.tic.unam.mx.cecapp.navdrawitems.ConfiguracionItem;
import docencia.tic.unam.mx.cecapp.navdrawitems.EventosItem;
import docencia.tic.unam.mx.cecapp.tabs.InfoUsuarioGeneralFragment;
import docencia.tic.unam.mx.cecapp.tabs.InfoUsuarioInteresesFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Home: Hace referencia al primer item del menú (Eventos)
     * Config: Hace referencia al segundo item del menú (Configuración/Registro)
     * About: Hace referencia al tercer item del menú (Acerca del CEC)
     */
    private SharedPreferences userInfoPrefs;    // Archivo de información del usuario
    private NavigationView navigationView;  // Vista del Menú (NavDrawer)
    protected boolean firstTime;    // Para registrar si es la primera vez que el usuario abre la app
    protected boolean ftNavDrawHome;    // Para registrar si checa el 'Home' por primera vez
    protected boolean ftNavDrawConfig;  // Para registrar si checa la 'Config' por primera vez
    protected boolean ftNavDrawAbout;   // Para registrar si checa el 'About' por primera vez

    final byte INFO_USER = 0;   // Referencia a la primera pestaña en 'Config' (Datos)
    final byte INFO_INTERESTS = 1;  // Referencia a la segunda pestaña en 'Config' (Temáticas)

    private CharSequence title;
    private FloatingActionButton floatingActionButton;
    private  final String DRAWER_STATE = "itemMenu";
    //private static byte navigationalDrawerState;   // Guarda en que pestaña del menú se encuentra el usuario (static para que no se borre al cambiar orientación)
    private byte navigationalDrawerState;   // Guarda en que pestaña del menú se encuentra el usuario (static para que no se borre al cambiar orientación)
//    final String[] categoryUserPrefs = Constants.USER_INFO_PREFS_KEYS;

    /** Configuraciones para redes sociales */
    private CallbackManager callbackManager;    // Registra callbacks de Facebook
//    private ProfilePictureView profilePictureView;
//    private ShareDialog shareDialog;
    private LoginButton facebookButton;
    private final int FACEBOOK_REQUESTCODE = 64206;
    private TwitterLoginButton twitterButton;
    private final int TWITTER_REQUESTCODE = 140;

    // TODO al cambiar de posición desaparece el botón de guardar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.w(">>>MainActivity", "onCreate savedInstanceState = "+(savedInstanceState == null ? "null" : "not null"));

        callbackManager = CallbackManager.Factory.create();
        userInfoPrefs = this.getSharedPreferences(
                Constants.USER_INFO_PREFERENCES, Context.MODE_PRIVATE);

        title = getTitle();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setVisibility(FloatingActionButton.INVISIBLE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(title);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                //getSupportActionBar().setTitle(mDrawerTitle);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Registra si es la primera vez que se abre la app
        if(userInfoPrefs.getBoolean(Constants.FIRST_RUN, true)) {
            firstTime = true;
            ftNavDrawHome = true;
            ftNavDrawConfig = true;
            ftNavDrawAbout = true;
        } else {
            firstTime = false;
        }

        // Si no hay savedInstanceState y no es la primera vez que se abre la app, inicia en Home
        if (savedInstanceState == null) {
            Log.i("onCreate", "saved == null");
            if(!firstTime) {
                setNavigationItem(Constants.NAVIGATIONAL_DRAWER_HOME);
            }
        } else {    // Manda a llamar onCreateOptionsMenu
            invalidateOptionsMenu();
        }

        // Botón "Login" de Facebook
        facebookButton = new LoginButton(this);
        // Permiso de publicar
        facebookButton.setPublishPermissions("publish_actions");
        facebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(),
                        "Sesión de FB iniciada",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),
                        "Cancelado",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(getApplicationContext(),
                        "Hubo un error al iniciar FB",
                        Toast.LENGTH_SHORT).show();
            }
        });

        twitterButton = new TwitterLoginButton(this);
        twitterButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                twitterButton.setVisibility(TwitterLoginButton.INVISIBLE);
                Toast.makeText(getApplicationContext(),
                        "Sesión de Twitter iniciada",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(getApplicationContext(),
                        "Sesión de Twitter fallida",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Debugg
//        ServerSingleMapResponse serverSingleMapResponse = ServerSingleMapResponse.parseJSON("{ \"response_code\":0, \"response_msg\":\"Procesamiento concluido exitósamente.\", \"response_data\":{ \"location\":{ \"id\":28, \"boundaries\":{ \"x\":120, \"y\":300, \"w\":200, \"h\":400 }, \"rgbColor\":[ 3, 33, 233 ], \"activity\":{ \"id\":10, \"name\":\"Activity 3\", \"schedule\":\"08:00-17:00\" } } } }");
//        Stand stand = serverSingleMapResponse.getData().getStand();
//        Toast.makeText(this,"id "+stand.getId()+" ,rgb[2]:"+stand.getRgbColor()[2]+" ,Schedule: "+stand.getActivity().getSchedule(),Toast.LENGTH_LONG).show();
        ServerMapsResponse response = ServerMapsResponse.parseJSON("{ \"response_code\":0, \"response_msg\":\"Procesamiento concluido exitósamente.\", \"response_data\":{ \"maps\":[ { \"id\":1, \"name\":\"Planta Baja General\", \"permanentActivities\":[ { \"id\":24, \"boundaries\":{ \"x\":0, \"y\":0, \"w\":100, \"h\":200 }, \"rgbColor\":[ 120, 203, 200 ], \"activity\":{ \"id\":10, \"name\":\"Activity 3\", \"schedule\":\"08:00-17:00\" } }, { \"id\":28, \"boundaries\":{ \"x\":120, \"y\":300, \"w\":200, \"h\":400 }, \"rgbColor\":[ 3, 33, 233 ], \"activity\":{ \"id\":10, \"name\":\"Activity 3\", \"schedule\":\"08:00-17:00\" } } ], \"activitiesByDate\":[ { \"day\":\"2016-04-09\", \"locations\":[ { \"id\":12, \"boundaries\":{ \"x\":0, \"y\":0, \"w\":100, \"h\":200 }, \"rgbColor\":[ 0, 0, 0 ], \"activities\":[ { \"id\":43, \"name\":\"Activity 20\", \"schedule\":\"09:00-12:00\" }, { \"id\":44, \"name\":\"Activity 22\", \"schedule\":\"13:00-15:00\" } ] }, { \"id\":13, \"boundaries\":{ \"x\":120, \"y\":50, \"w\":100, \"h\":200 }, \"rgbColor\":[ ], \"activities\":[ { \"id\":48, \"name\":\"Activity 15\", \"schedule\":\"09:00-10:00\" }, { \"id\":49, \"name\":\"Activity 30\", \"schedule\":\"10:00-12:00\" }, { \"id\":50, \"name\":\"Activity 32\", \"schedule\":\"12:00-16:00\" } ] }, { \"id\":14, \"boundaries\":{ \"x\":150, \"y\":290, \"w\":200, \"h\":300 }, \"rgbColor\":[ 0, 0, 0 ], \"activities\":[ { \"id\":31, \"name\":\"Activity 6\", \"schedule\":\"09:00-12:00\" } ] } ] }, { \"day\":\"2016-04-10\", \"locations\":[ { \"id\":12, \"boundaries\":{ \"x\":0, \"y\":0, \"w\":100, \"h\":200 }, \"rgbColor\":[ 0, 0, 0 ], \"activities\":[ { \"id\":43, \"name\":\"Activity 20\", \"schedule\":\"09:00-12:00\" } ] }, { \"id\":13, \"boundaries\":{ \"x\":120, \"y\":50, \"w\":100, \"h\":200 }, \"rgbColor\":[ ], \"activities\":[ { \"id\":48, \"name\":\"Activity 15\", \"schedule\":\"09:00-10:00\" } ] } ] } ] }, { \"id\":2, \"name\":\"Mezzanine General\", \"permanentActivities\":[ ], \"activitiesByDate\":[ ] }, { \"id\":3, \"name\":\"Sótano General\", \"permanentActivities\":[ ], \"activitiesByDate\":[ ] } ] } }");
        Toast.makeText(this, "size = " + response.getData().getMapList().get(0).getAllStandsOnDay("2016-04-09").size(),Toast.LENGTH_LONG).show(); // Si da 5

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putByte(DRAWER_STATE, navigationalDrawerState);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        // Restore state members from saved instance
        Log.i("onCreate", "Antes savedIns... " + DRAWER_STATE + " " + navigationalDrawerState);
        navigationalDrawerState = savedInstanceState.getByte(DRAWER_STATE);
        Log.i("onCreate", "Después savedIns... " + DRAWER_STATE + " " + navigationalDrawerState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Es la primera vez que corre la aplicación
        if(firstTime){
            setNavigationItem(Constants.NAVIGATIONAL_DRAWER_CONFIG);
            userInfoPrefs.edit().putBoolean(Constants.FIRST_RUN, false).commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pasa el resultado de la actividad al manejador correspondiente
        if(requestCode == TWITTER_REQUESTCODE) {
            twitterButton.onActivityResult(requestCode, resultCode, data);
        }else if(requestCode == FACEBOOK_REQUESTCODE){
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setTitle(CharSequence viewTitle) {
        title = viewTitle;
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        final Resources resources = getResources();
        switch (navigationalDrawerState){
            case Constants.NAVIGATIONAL_DRAWER_HOME:
                floatingActionButton.setVisibility(FloatingActionButton.INVISIBLE);
                getMenuInflater().inflate(R.menu.main_w_content, menu);

                // Recupera e infla el icono para realizar búsquedas
                MenuItem searchItem = menu.findItem(R.id.action_search);
                final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
                // No se ve muy bien
                //searchView.setIconifiedByDefault(false);
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        searchView.clearFocus();
                        if(query.length() < 3){
                            Toast.makeText(MainActivity.this,
                                    "El temino de búsqueda debe ser de al menos 3 caracteres",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(MainActivity.this, IntentBusqueda.class);
                            intent.putExtra(Constants.SEARCH_TERM, query);
                            startActivity(intent);
                        }
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                break;
            case Constants.NAVIGATIONAL_DRAWER_CONFIG:
                // Se trae de vuelta el FAB y se le da la funcionalidad de guardar la información del usuario
                floatingActionButton.setVisibility(FloatingActionButton.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    floatingActionButton.setImageDrawable(resources.getDrawable(
                            R.drawable.ic_save, MainActivity.this.getTheme()));
                } else {
                    floatingActionButton.setImageDrawable(resources.getDrawable(
                            R.drawable.ic_save));
                }
                //final List<String> auxInfo = getListFromPreferences(userInfoPrefs);
                /** TODO
                 * -Checar en que pestaña está para así guardar la información por separado
                 * -Si se va a actualizar quitar el floatingActionButton para evitar
                 * crasheos (talvez cambiando de private a public static)
                 */
//                Log.i(">> FAB left", Integer.toString(floatingActionButton.getLeft()));
//                Log.i(">> FAB right", Integer.toString(floatingActionButton.getRight()));
//                Log.i(">> FAB top", Integer.toString(floatingActionButton.getTop()));
//                Log.i(">> FAB bottom", Integer.toString(floatingActionButton.getBottom()));

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(ConfiguracionItem.currentPage == INFO_USER) {
                            InfoUsuarioGeneralFragment.saveChanges();
                            Snackbar.make(v, resources.getString(R.string.config_usr_info_save_msg)
                                    , Snackbar.LENGTH_SHORT)
                                    .setAction("", null).show();
                        }else { // Está en pestaña INFO_INTERESTS
                            InfoUsuarioInteresesFragment.saveChanges();
                            Snackbar.make(v, resources.getString(R.string.config_save_msg) + " " +
                                    Constants.TAB_TITLES_USER_INFO[INFO_INTERESTS]
                                    , Snackbar.LENGTH_SHORT)
                                    .setAction("", null).show();
                            if(firstTime){
                                setNavigationItem(Constants.NAVIGATIONAL_DRAWER_HOME);
                                ConfiguracionItem.currentPage = INFO_USER;
                                //firstTime = false;
                            }
                        }
                    }
                });
                getMenuInflater().inflate(R.menu.main_wo_content, menu);
                break;
            case Constants.NAVIGATIONAL_DRAWER_ABOUT:
                floatingActionButton.setVisibility(FloatingActionButton.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    floatingActionButton.setImageDrawable(resources.getDrawable(
                            R.drawable.ic_web, MainActivity.this.getTheme()));
                } else {
                    floatingActionButton.setImageDrawable(resources.getDrawable(
                            R.drawable.ic_web));
                }
                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                        intentWeb.setData(Uri.parse(Constants.CEC_WEB_PAGE));
                        startActivity(intentWeb);
                    }
                });
                getMenuInflater().inflate(R.menu.main_wo_content, menu);
                break;
            default:
                floatingActionButton.setVisibility(FloatingActionButton.INVISIBLE);
                break;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle args = new Bundle();
        switch(item.getItemId()){
            case(R.id.nav_eventos): // Inicia el fragment de las listas de eventos
                navigationalDrawerState = Constants.NAVIGATIONAL_DRAWER_HOME;
                EventosItem fragmentEI = new EventosItem();
                if(ftNavDrawHome){ // Se muestra la explicación inicial
                    showAlertDialog(null, Constants.NAVIGATIONAL_DRAWER_HOME);
                    ftNavDrawHome = false;
                }
                fragmentEI.setContext(MainActivity.this);
                invalidateOptionsMenu();    //Llama a onCreateOptionsMenu()
                args.putInt(Constants.NAV_DRAW_PAGE, navigationalDrawerState);
                fragmentEI.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentEI).commit();
                break;
            case(R.id.nav_configuracion):   // Inicia el fragment de la información del usuario
                navigationalDrawerState = Constants.NAVIGATIONAL_DRAWER_CONFIG;
                ConfiguracionItem fragmentCI = new ConfiguracionItem();
                if(ftNavDrawConfig){ // Se muestra la explicación inicial
                    showAlertDialog(null, Constants.NAVIGATIONAL_DRAWER_CONFIG);
                    // Ponemos la página inicial como la de temáticas/intereses
                    fragmentCI.setCurrentPage(INFO_INTERESTS);
                    ftNavDrawConfig = false;
                }
                fragmentCI.setContext(MainActivity.this);
                args.putInt(Constants.NAV_DRAW_PAGE, navigationalDrawerState);
                fragmentCI.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentCI).commit();
                invalidateOptionsMenu();
                break;
            case(R.id.nav_acerca):  // Inicia el fragment que muestra la información del CEC
                navigationalDrawerState = Constants.NAVIGATIONAL_DRAWER_ABOUT;
                AcercaDeItem fragmentADI = new AcercaDeItem();
                if(ftNavDrawAbout){ // Se muestra la explicación inicial
                    showAlertDialog(null, Constants.NAVIGATIONAL_DRAWER_ABOUT);
                    ftNavDrawAbout = false;
                }
                fragmentADI.setContext(MainActivity.this);
                args.putInt(Constants.NAV_DRAW_PAGE, navigationalDrawerState);
                fragmentADI.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentADI).commit();
                invalidateOptionsMenu();
                break;
            case(R.id.fb):  // Inicia sesión o pregunta si cerrar sesión de Facebook
                Profile profile = Profile.getCurrentProfile();
                if(AccessToken.getCurrentAccessToken() != null ||
                        profile != null){
                    showAlertDialog(profile.getName(), Constants.NAVIGATIONAL_DRAWER_FB);
                } else {
                    facebookButton.performClick();
                }
                break;
            case (R.id.tw): // Inicia sesión o pregunta si cerrar sesión de Twitter
                // Checa si hay una sesión de Twitter activa
                final TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                if(twitterSession != null){
                    showAlertDialog(twitterSession.getUserName(), Constants.NAVIGATIONAL_DRAWER_TW);
                } else {
                    twitterButton.performClick();
                }
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNavigationItem(int item){
        navigationView.getMenu().getItem(item).setChecked(true);
        onNavigationItemSelected(navigationView.getMenu().getItem(item));
    }

    /**
     * Sirve para cerrar la sesión en Twitter, ya que en las librerías de Fabric no se
     * incluye una forma directa para cerrar sesión se tiene que hacer manuealmente
     */
    public void logoutTwitter() {
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (twitterSession != null) {
            clearCookies(getApplicationContext());
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
        }
    }

    /**
     * Dependiendo de la versión de android manda a llamar al CookieManager para
     * borrar las cookies
     *
     * @param context el contexto de donde se manda a llamar
     */
    public static void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager=CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }

    //public void showAlertDialog(String name, int drawerOrigin, int tabOrigin) {
    // Como arriba para ir mostrando mensajes en cada pestaña [poner por cada case NAV_DRAWER_: switch (tabOrigin)]

    /**
     * Muestra AlertDialogs personalizados que dependen de los parámetros que se le pasen
     *
     * @param name solo se toma en cuenta cuando drawerOrigin está en la posición de Facebook
     *             o Twitter
     * @param drawerOrigin hace referencia a la posición del menú en el que se está
     */
    public void showAlertDialog(String name, int drawerOrigin) {
        int icon;
        String title, msg;
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        switch (drawerOrigin) {
            case Constants.NAVIGATIONAL_DRAWER_HOME:
                icon = android.R.drawable.ic_dialog_alert;
                title = "Eventos en el CEC";
                msg = "En esta sección puedes ver los eventos filtrados según: tus intereses, a los que te has inscrito y los del mes.";
                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                break;
            case Constants.NAVIGATIONAL_DRAWER_CONFIG:
                icon = android.R.drawable.ic_dialog_alert;
                title = "Registro";
                msg = "En esta sección puedes establecer tus temas de interés y tu información de contacto";
                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                break;
            case Constants.NAVIGATIONAL_DRAWER_ABOUT:
                icon = android.R.drawable.ic_dialog_alert;
                title = "Acerca del CEC";
                msg = "En esta sección está la información del Centro de Exposiciones y Congresos";
                builder.setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                });
                break;
            case Constants.NAVIGATIONAL_DRAWER_FB:
                title = "Sesión iniciada como " + name;
                msg = "Cerrar sesión?";
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                icon = com.facebook.R.drawable.com_facebook_button_icon_blue;
                break;
            case Constants.NAVIGATIONAL_DRAWER_TW:
                title = "Sesión iniciada como " + name;
                msg = "Cerrar sesión?";
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logoutTwitter();
                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        });
                icon = com.twitter.sdk.android.R.drawable.tw__composer_logo_blue;
                break;
            default:
                title = "";
                msg = "";
                icon = android.R.drawable.ic_dialog_alert;
                break;
        }
        builder.setTitle(title)
                .setMessage(msg)
                .setIcon(icon)
                .show();
    }
}
