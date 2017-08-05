package com.app.picture;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.app.picture.bean.ItemListBeanData;
import com.app.picture.fragment.ConcernFragment;
import com.app.picture.fragment.DiscoverFragment;
import com.app.picture.fragment.HomePageFragment;
import com.app.picture.fragment.MeFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout mMain_tab;
    private ViewPager mMain_content;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);


        mMain_content = (ViewPager) findViewById (R.id.main_content);
        mMain_tab = (TabLayout) findViewById (R.id.main_tab);


        final String[] as={"首页","发现","关注","我"};
        initFragment ();
        mMain_content.setAdapter (new FragmentPagerAdapter (getSupportFragmentManager ()) {
            @Override
            public int getCount () {
                return mFragments.size ();
            }

            @Override
            public Fragment getItem (int position) {
                return mFragments.get (position);
            }

            @Override
            public CharSequence getPageTitle (int position) {
                return as[position];
            }
        });

        mMain_tab.addTab (mMain_tab.newTab ().setText (as[0]));
        mMain_tab.addTab (mMain_tab.newTab ().setText (as[1]));
        mMain_tab.addTab (mMain_tab.newTab ().setText (as[2]));
        mMain_tab.addTab (mMain_tab.newTab ().setText (as[3]));
        mMain_tab.setupWithViewPager (mMain_content);
        network ();

    }

    private void initFragment () {

        mFragments = new ArrayList<> ();
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    mFragments.add (HomePageFragment.getInstance ());
                    break;
                case 1:
                    mFragments.add (DiscoverFragment.getInstance ());
                    break;
                case 2:
                    mFragments.add (ConcernFragment.getInstance ());
                    break;
                case 3:
                    mFragments.add (MeFragment.getInstance ());
                    break;
            }
        }

    }

    private void network () {


        String url = "http://baobab.kaiyanapp.com/api/v4/tabs/selected";
        OkGo.<String>get (url)//
                .tag (this)//
                .execute (new StringCallback () {
                    @Override
                    public void onSuccess (Response<String> response) {

                        Logger.e (response.body ().toString ());

                        ItemListBeanData ItemListBeanData = JSON.parseObject (response.body ().toString (), ItemListBeanData.class);

                        List<ItemListBeanData.ItemListBean> feedList = ItemListBeanData.getItemList ();

                        for (ItemListBeanData.ItemListBean feedListBean : feedList) {

                            Logger.e (feedListBean.getType ());

                        }

                    }

                    @Override
                    public void onError (Response<String> response) {
                        super.onError (response);

                        Logger.e (response.toString ());
                    }
                });


    }
}
