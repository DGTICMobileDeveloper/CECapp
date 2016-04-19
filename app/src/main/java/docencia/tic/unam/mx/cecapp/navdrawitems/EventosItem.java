package docencia.tic.unam.mx.cecapp.navdrawitems;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.adapters.MainPageAdapter;

public class EventosItem extends Fragment {
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    protected MainPageAdapter mPageAdapter;

    public EventosItem() {
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
        View rootView = inflater.inflate(R.layout.eventos_item_fragment, null);
        int i = getArguments().getInt(Constants.NAV_DRAW_PAGE);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) rootView.findViewById(R.id.eventos_item_viewpager);
        mPageAdapter = new MainPageAdapter(getChildFragmentManager(),
                getContext(), Constants.TAB_TITLES_MAIN.length, Constants.SOURCE_MAIN);
        viewPager.setAdapter(mPageAdapter);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout)rootView.findViewById(R.id.eventos_item_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        getActivity().setTitle(Constants.NAV_DRAW_ITEMS[i]);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.POSITION, tabLayout.getSelectedTabPosition());
    }
}