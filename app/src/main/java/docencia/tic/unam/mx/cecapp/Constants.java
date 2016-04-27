package docencia.tic.unam.mx.cecapp;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class Constants {
    /** EventosItem */
    public static final String NAV_DRAW_PAGE = "NAV_DRAW_PAGE";
    public static final String POSITION = "POSITION";

    /** IntentEvento */
    public static final String NAME_EVENT = "NAME_EVENT";
    public static final String DATE_EVENT = "DATE_EVENT";
    public static final String ID_EVENT = "ID_EVENT";
    public static final String IS_REGISTERED = "IS_REGISTERED";
    public static final String LOCATION_EVENT = "Av. del Imán #10, Cuidad Universitaria, Coyoacán, Ciudad de México";
    public static final String CEC_WEB_PAGE = "http://www.cec.unam.mx/index.action";
    public static final String LOCATION_EVENT_LAT_LON = "http://maps.google.com/maps?daddr=19.3094213,-99.1765897 (\"Centro de Exposiciones y Congresos UNAM\")";
    public static final String CEC_FB = "https://www.facebook.com/pages/Centro-De-Exposiciones-Y-Congresos-Unam/295698220624920";
    public static final String CEC_FB_APP = "fb://facewebmodal/f?href=295698220624920";
    public static final String CEC_TW = "https://twitter.com/UNAM_MX";
    public static final String CEC_YT = "https://www.youtube.com/channel/UCDaRHOeEHfD3ZyA_zMXzFSA/feed";

    public static final String ID_ACTIVITY = "ID_ACTIVITY";
    public static final String NAME_ACTIVITY = "NAME_ACTIVITY";
    public static final String EVENT_INFO_NAME = "nombre";
    public static final String EVENT_INFO_URL = "url";
    public static final String EVENT_INFO_FB = "fb";
    public static final String EVENT_INFO_TW = "tw";
    public static final String EVENT_INFO_YT = "yt";
    public static final String EVENT_INFO_ADRESS = "adress";
    public static final String EVENT_INFO_LOGO = "logo";

    /** MainActivity */
    public static final String SEARCH_TERM = "SEARCH_TERM";
    public static final byte NAVIGATIONAL_DRAWER_HOME = 0;
    public static final byte NAVIGATIONAL_DRAWER_CONFIG = 1;
    public static final byte NAVIGATIONAL_DRAWER_ABOUT = 2;
    public static final byte NAVIGATIONAL_DRAWER_FB = 3;
    public static final byte NAVIGATIONAL_DRAWER_TW = 4;
    public static final String USER_INFO_PREFERENCES = "docencia.tic.unam.mx.cecapp.userinfo";
    public static final String USER_INTERESTS_PREFERENCES = "docencia.tic.unam.mx.cecapp.userinterests";
    public static final String IMEI_MEID = "PHONE_ID";
    public static final String USER_ID = "USER_ID";
    public static final String FIRST_RUN = "FIRST_RUN";
    //public static final String USER_INFO_PREFERENCES_KEY = "USER_PREFERENCES";
    public static String USER_INFO_PREFS_KEYS[];
    public static void setUserInfoPrefsKeys(String[] array) {
        USER_INFO_PREFS_KEYS = array;
    }

    /** AsyncHttpRetriever */
    //public static final byte MODE_USER_INFO = 2;
    public static final byte MODE_EVENT_LIST = 0;
    public static final byte MODE_EVENT_INFO = 1;
    public static final byte MODE_REGISTER_USER_INFO = 2;
    public static final byte MODE_UPDATE_USER_INFO = 3;
    public static final byte MODE_INTEREST_LIST = 4;
    public static final byte MODE_GET_USER_EVENTS = 5;
    public static final byte MODE_REGISTER_USER_TO_EVENT = 6;
    public static final byte MODE_REMOVE_USER_FROM_EVENT = 7;
    public static final byte MODE_INTEREST_EVENT_LIST = 8;
    public static final byte MODE_SEARCH_EVENT = 9;
    public static final byte MODE_GET_EVENT_MAP = 10;
    public static final byte MODE_GET_SINGLE_EVENT_MAP = 11;
    //public static final byte MODE_DELETE_USERS = 9;
    public static final String BASE_LINK_EVENT_LIST = "http://132.248.108.7/cec/Controladores/listaEventos.php";
    public static final String BASE_LINK_EVENT_LIST_MONTH = "http://132.248.108.7/cec/Controladores/listEventsOfThisMonth.php";
    public static final String BASE_LINK_EVENT = "http://132.248.108.7/cec/Controladores/datosEvento.php";
    public static final String BASE_LINK_INTERESTS = "http://132.248.108.7/cec/Controladores/listSubjects.php";
    public static final String BASE_LINK_GET_USER_EVENTS = "http://132.248.108.7/cec/Controladores/getUserEvents.php";
    public static final String BASE_LINK_REGISTER_USER_TO_EVENT = "http://132.248.108.7/cec/Controladores/addUserToEvent.php";
    public static final String BASE_LINK_REMOVE_USER_FROM_EVENT = "http://132.248.108.7/cec/Controladores/removeUserFromEvent.php";
    public static final String BASE_LINK_REGISTER_USER = "http://132.248.108.7/cec/Controladores/addUser.php";
    public static final String BASE_LINK_UPDATE_USER = "http://132.248.108.7/cec/Controladores/updateUser.php";
    public static final String BASE_LINK_SEARCH_EVENT = "http://132.248.108.7/cec/Controladores/searchEvents.php";
    public static final String BASE_LINK_GET_EVENT_MAP= "http://132.248.108.7/cec/Controladores/getDataMap.php";
    public static final String BASE_LINK_GET_SINGLE_EVENT_MAP= "http://132.248.108.7/cec/Controladores/getDataSingleMap.php";
    //public static final String BASE_LINK_CLEAR_USERS = "http://132.248.108.7/cec/Controladores/deleteUsers.php";

    public static final String SERVER_KEY_EVENT_LIST = "page";
    public static final String SERVER_KEY_EVENT_LIST_MONTH = "month";
    public static final String SERVER_KEY_EVENT_LIST_MONTH_C = "current";
    public static final String SERVER_KEY_EVENT_ID = "eid";
    public static final String SERVER_KEY_GET_USER_EVENTS = "aid";
    public static final String SERVER_KEY_REGISTER_USER_TO_EVENT = "record";
    public static final String SERVER_KEY_INTERESTS_FILTER = "filter";
    public static final String SERVER_KEY_EVENTS_FILTER = "filter";
    public static final String SERVER_KEY_INTERESTS_INCLUSIVE = "inclusive";
    public static final String SERVER_KEY_SEARCH_TERM = "s";
    public static final String SERVER_KEY_EVENT_ACTIVITY_ID = "acid";
    public static final String SERVER_KEY_2 = "";
    public static final String SERVER_KEY_3 = "";
    public static final String SERVER_KEY_4 = "";
    public static final String SERVER_KEY_5 = "";


    /** Mapa */
    public static final int X_OFFSET = 0;
    public static final int Y_OFFSET = 0;
    public static final int W = 1148;
    public static final int H = 735;



    /** Realmente no se usan estos*/
    public static final String SERVER_KEY_USER_INFO = "InfoDelUsuario";
    public static final String SERVER_KEY_CATEGORY = "Categoria";
    public static final String SERVER_PARAM_TRUE = "True";
    public static final String SERVER_PARAM_FALSE = "False";
    public static final String SERVER_PARAM_EL_INTEREST = "Interes";
    public static final String SERVER_PARAM_EL_INSCRIBED = "Inscrito";
    public static final String SERVER_PARAM_EL_GENERAL = "General";
    //public static final String SERVER_PARAM_EI_ID = "";
    //public static final String SERVER_PARAM_UI_ID = "";

    /** MainPageAdapter */
    public static final byte SOURCE_MAIN = 0;
    public static final byte SOURCE_EVENT = 1;
    public static final byte SOURCE_USER_INFO = 2;
    public static final byte SOURCE_EVENTO_INFO = 3;
    public static final byte SOURCE_EVENTO_EXPOS = 4;

    public static final String BUTTON_LOCATION = "¿Cómo llegar?";
    public static final String BUTTON_AGENDA = "Agregar a agenda";


    public static final String NAV_DRAW_ITEMS[] = new String[] {"Eventos", "Registro", "Acerca del CEC", "Facebook", "Twitter"};
    public static final String TAB_TITLES_DEFAULT[] = new String[] {"Tab 1", "Tab 2", "Tab 3"};
    public static final String TAB_TITLES_MAIN[] = new String[] {"De interés", "Suscrito", "Mes"};
    public static final String TAB_TITLES_EVENT[] = new String[] {"Información", "Actividades"};
    //public static final String TAB_TITLES_EVENT[] = new String[] {"Información", "Mapas", "Exposiciones"};
    public static final String TAB_TITLES_USER_INFO[] = new String[] {"Datos", "Temáticas"};

    /**
     *  Formatos de hora
     */
    public static String DATE_FORMAT_ARRAY[];
    public static final byte DATE_ERA = 0;
    public static final byte DATE_W_DAY_S = 1;
    public static final byte DATE_W_DAY_L = 2;
    public static final byte DATE_HH = 3;
    public static final byte DATE_mm = 4;
    public static final byte DATE_DAYtoYEAR = 5;
    public static final byte DATE_YEARtoDAY = 6;
    public static final byte DATE_M_DAY = 7;
    public static final byte DATE_MONTH_INT = 8;
    public static final byte DATE_MONTH_S = 9;
    public static final byte DATE_MONTH_L = 10;
    public static final byte DATE_MONTH_M = 11;
    public static final byte DATE_YEAR = 12;
    public static final byte DATE_hh = 13;
    public static final byte DATE_hhmma = 14;
    public static final byte DATE_Day_dd_de_MONTH = 15;

    public static void setDateFormatArray(String[] FormatoFechaArreglo) {
        DATE_FORMAT_ARRAY = FormatoFechaArreglo;
    }

    public static ListView AUX_LV;
    public static void setAuxListView(ListView aux){
        AUX_LV = aux;
    }
    public static ExpandableListView AUX_E_L_V;
    public static void setAuxExpandableList(ExpandableListView aux) {
        AUX_E_L_V = aux;
    }
    public static ExpandableListView getAuxExpandableList(){
        return AUX_E_L_V;
    }
    public static Activity AUX_CONTEXT;
    public static void setAuxContext(Activity aux) {
        AUX_CONTEXT = aux;
    }
    public static Activity getAuxContext(){
        return AUX_CONTEXT;
    }
    public static Activity AUX_FRAGMENT_ACTIVITY;
    public static void setAuxFragmentActivity(Activity aux) {
        AUX_FRAGMENT_ACTIVITY = aux;
    }
    public static Activity getAuxFragmentActivity(){
        return AUX_FRAGMENT_ACTIVITY;
    }
    public static Bundle AUX_BUNDLE;
    public static void setAuxBundle(Bundle aux) {
        AUX_BUNDLE = aux;
    }
    public static Bundle getAuxBundle(){
        return AUX_BUNDLE;
    }
}
