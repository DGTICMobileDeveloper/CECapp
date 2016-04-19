package docencia.tic.unam.mx.cecapp.navdrawitems;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import docencia.tic.unam.mx.cecapp.Constants;
import docencia.tic.unam.mx.cecapp.Interfaces.CustomFragmentLifeCycle;
import docencia.tic.unam.mx.cecapp.R;
import docencia.tic.unam.mx.cecapp.adapters.MainPageAdapter;


public class ConfiguracionItem extends Fragment {
    private Context context;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MainPageAdapter mPageAdapter;
    public static int currentPage = 0;  // empieza en la primera (0)

    public ConfiguracionItem() {
        // Empty constructor required for fragment subclasses
    }

    public void setCurrentPage(int page){
        currentPage = page;
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
        View rootView = inflater.inflate(R.layout.configuracion_item_fragment, null);
        Log.w(">>>ConfiguracionItem", "onCreateView savedInstanceState is "+(savedInstanceState == null ? "null" : "not null"));
        int i = getArguments().getInt(Constants.NAV_DRAW_PAGE);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) rootView.findViewById(R.id.config_item_viewpager);
        mPageAdapter = new MainPageAdapter(getChildFragmentManager(),
                getContext(), Constants.TAB_TITLES_USER_INFO.length, Constants.SOURCE_USER_INFO);
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

        viewPager.setCurrentItem(currentPage, true);
//        Log.i("currentPage", "1>" + currentPage);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        Log.i("currentPage", "2>" + currentPage);

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout)rootView.findViewById(R.id.config_item_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        getActivity().setTitle(Constants.NAV_DRAW_ITEMS[i]);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
