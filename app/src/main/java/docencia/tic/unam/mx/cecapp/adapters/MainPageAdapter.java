package docencia.tic.unam.mx.cecapp.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.tabs.EventoExposFragment;
import docencia.tic.unam.mx.cecapp.tabs.EventoInfoFragment;
import docencia.tic.unam.mx.cecapp.tabs.EventoMapaFragment;
import docencia.tic.unam.mx.cecapp.tabs.EventosGeneralFragment;
import docencia.tic.unam.mx.cecapp.tabs.EventosInscritoFragment;
import docencia.tic.unam.mx.cecapp.tabs.EventosInteresFragment;
import docencia.tic.unam.mx.cecapp.tabs.InfoUsuarioGeneralFragment;
import docencia.tic.unam.mx.cecapp.tabs.InfoUsuarioInteresesFragment;

public class MainPageAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;
    private byte origen;
    private Context context;
    private long id;

    public MainPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public MainPageAdapter(FragmentManager fm, Context context, int NumOfTabs, byte origen) {
        super(fm);
        this.context = context;
        this.mNumOfTabs = NumOfTabs;
        this.origen = origen;
    }

    public MainPageAdapter(FragmentManager fm, Context context, int NumOfTabs, byte origen, long id) {
        super(fm);
        this.context = context;
        this.mNumOfTabs = NumOfTabs;
        this.origen = origen;
        this.id = id;
    }

    @Override
    public Fragment getItem(int position) {
        switch (origen){
            case Constants.SOURCE_MAIN:
                switch (position) {
                    case 0:
                        EventosInteresFragment tab1 = new EventosInteresFragment();
                        return tab1;
                    case 1:
                        EventosInscritoFragment tab2 = new EventosInscritoFragment();
                        return tab2;
                    case 2:
                        EventosGeneralFragment tab3 = new EventosGeneralFragment();
                        return tab3;
                    default:
                        return null;
                }
            case Constants.SOURCE_EVENT:
                switch (position) {
                    case 0:
                        Bundle bundle = new Bundle();
                        // Se supondría que no puede estar aquí con id = null (no definido)
                        bundle.putLong(Constants.ID_EVENT, id);
                        EventoInfoFragment tab1 = new EventoInfoFragment();
                        tab1.setArguments(bundle);
                        return tab1;
                    /*case 2:
                        EventoMapaFragment tab3 = new EventoMapaFragment();
                        return tab3;*/
                    case 1:
                        EventoExposFragment tab2 = new EventoExposFragment();
                        return tab2;
                    default:
                        return null;
                }
            case Constants.SOURCE_USER_INFO:
                switch (position) {
                    case 0:
                        InfoUsuarioGeneralFragment tab1 = new InfoUsuarioGeneralFragment();
                        return tab1;
                    case 1:
                        InfoUsuarioInteresesFragment tab2 = new InfoUsuarioInteresesFragment();
                        return tab2;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (origen){
            case Constants.SOURCE_MAIN:
                return Constants.TAB_TITLES_MAIN[position];
            case Constants.SOURCE_EVENT:
                return Constants.TAB_TITLES_EVENT[position];
            case Constants.SOURCE_USER_INFO:
                return Constants.TAB_TITLES_USER_INFO[position];
            default:
                return null;
        }
    }
}
