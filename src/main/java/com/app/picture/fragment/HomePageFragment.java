package com.app.picture.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.picture.R;

/**
 * @author lhy 1083005240@qq.com
 * @time 2017/8/4 0004  下午 5:53
 * @explain
 */

public class HomePageFragment extends Fragment {

    static HomePageFragment mFragment;


    public static Fragment getInstance () {

        if (mFragment == null) {
            mFragment = new HomePageFragment ();
        } else {
            return mFragment;
        }

        return mFragment;
    }


    @Nullable
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {

       return inflater.inflate (R.layout.homepage_fragment, null);


    }


}
