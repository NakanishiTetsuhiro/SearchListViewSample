package com.tutorialkart.searchlistviewsample;

import java.util.Comparator;

/**
 * Created by tetsuhiro on 2017/12/31.
 */
public class KanaComparator implements Comparator<Company> {

    @Override
    public int compare(Company lhs, Company rhs)
    {
        return lhs.getKana().compareTo(rhs.getKana());
    }
}