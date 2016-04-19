package docencia.tic.unam.mx.cecapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;


public class IntentBusqueda extends AppCompatActivity {
    public static String terminoBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intent_busqueda);
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.pbBusqueda);
        ListView mListView = (ListView) findViewById(R.id.lvBusqueda);
        TextView mTextView = (TextView) findViewById(R.id.tvBusqueda);
        mTextView.setVisibility(TextView.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            terminoBusqueda = extras.getString(Constants.SEARCH_TERM);
            AsyncHttpRetriever mAHR = new AsyncHttpRetriever(mProgressBar, this,
                    Constants.MODE_SEARCH_EVENT, mListView, getRequestParams(terminoBusqueda), mTextView);
            mAHR.postCommand(Constants.BASE_LINK_SEARCH_EVENT);
        } else {
            Toast.makeText(this, "Hubo un error con el término de búsqueda",
                    Toast.LENGTH_SHORT).show();
            terminoBusqueda = "Default";
        }

        /**TODO
         * Al hacer click en un evento de la lista de resultados manda al IntentEvento y
         * presionar ← regresa al Home (pero presionar back [de la navegación de android]
         * tiene el comportamiento deseado)
         */

        getSupportActionBar().setTitle("Busqueda: \'" + terminoBusqueda + "\'");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }
    private RequestParams getRequestParams(String searchTerm) {
        RequestParams params = new RequestParams();
        /** TODO
         * Modificar esto para ir solicitando nuevas páginas i.e. page=n
         * Agregar a todos los lugares donde se pidan listas de eventos
         */
        params.put(Constants.SERVER_KEY_SEARCH_TERM, searchTerm);
        return params;
    }
}
