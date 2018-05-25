package com.hoolai.huizhen.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hoolai.huizhen.R;
import com.hoolai.huizhen.ui.adapter.BottomAdapter;
import com.hoolai.huizhen.ui.fragment.BrowseFragment;
import com.hoolai.huizhen.ui.fragment.FindFragment;
import com.hoolai.huizhen.ui.fragment.MineFragment;
import com.hoolai.huizhen.ui.fragment.TrainFragment;
import com.hoolai.huizhen.widget.BottomNavigationViewHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.vp_main)
    ViewPager vpMain;

    private BottomAdapter adapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_train:
                    vpMain.setCurrentItem(0);
                    return true;
                case R.id.navigation_find:
                    vpMain.setCurrentItem(1);
                    return true;
                case R.id.navigation_browse:
                    vpMain.setCurrentItem(2);
                    return true;
                case R.id.navigation_mine:
                    vpMain.setCurrentItem(3);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFragment();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setItemIconTintList(null);
    }

    private void initFragment() {
        adapter = new BottomAdapter(getSupportFragmentManager());
        adapter.addFragment(new TrainFragment());
        adapter.addFragment(new FindFragment());
        adapter.addFragment(new BrowseFragment());
        adapter.addFragment(new MineFragment());
        vpMain.setAdapter(adapter);
        vpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
