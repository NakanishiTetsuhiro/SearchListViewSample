package com.tutorialkart.searchlistviewsample;

import android.app.LauncherActivity;

import java.util.Comparator;

/**
 * Created by tetsuhiro on 2017/12/31.
 */

class NameComparator implements Comparator<LauncherActivity.ListItem>
{
//    @Override
//    public int compare(ListData lhs, ListData rhs)
//    {
//        return lhs.name.compareTo(rhs.name);
//    }

    @Override
    public int compare(LauncherActivity.ListItem o1, LauncherActivity.ListItem o2) {
        return 0;
    }
}