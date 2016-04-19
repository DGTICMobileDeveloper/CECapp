package docencia.tic.unam.mx.cecapp.navdrawitems;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.R;

public class AcercaDeItem extends Fragment {
    private Context context;

    public AcercaDeItem() {
        // Empty constructor required for fragment subclasses
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.acerca_item_fragment, null);
        /*int i = getArguments().getInt(Constants.NAV_DRAW_PAGE);
        getActivity().setTitle(Constants.NAV_DRAW_ITEMS[i]);*/
        getActivity().setTitle("Centro de Exposiciones y Congresos UNAM");
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}