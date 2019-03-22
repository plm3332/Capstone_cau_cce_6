package org.techtown.carchap_v11;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mdata;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);

        mdata= new ArrayList<>();

        mdata.add(new ReserFra_carchap());
        mdata.add(new ReserFra_socar());
        mdata.add(new ReserFra_tada());
        mdata.add(new ReserFra_tada());
        mdata.add(new ReserFra_tada());
        mdata.add(new ReserFra_tada());


    }

    @Override
    public Fragment getItem(int i) {
        return mdata.get(i);
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0:
                return "carchap";
            case 1:
                return "socar";
            case 2:
                return "tada";
            case 3:
                return "tada";
            case 4:
                return "tada";
            case 5:
                return "tada";
            case 6:
                return "tada";
            default:
                return "??";
        }

    }
}

