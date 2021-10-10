package com.example.faloka_mobile.Checkout;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class CheckoutFragmentUtil {

    public final static String TAG_NAME_FRAGMENT = "ACTIVITY_FRAGMENT";

    public static Fragment getFragmentByTagName(FragmentManager fragmentManager, String fragmentTagName)
    {
        Fragment ret = null;

        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentList!=null)
        {
            int size = fragmentList.size();
            for(int i=0;i<size;i++)
            {
                Fragment fragment = fragmentList.get(i);
                if(fragment!=null) {
                    String fragmentTag = fragment.getTag();
                    if (fragmentTag.equals(fragmentTagName)) {
                        ret = fragment;
                    }
                }
            }
        }
        return ret;
    }

    public static void printActivityFragmentList(FragmentManager fragmentManager)
    {

        List<Fragment> fragmentList = fragmentManager.getFragments();
        if(fragmentList!=null)
        {
            int size = fragmentList.size();
            for(int i=0;i<size;i++)
            {
                Fragment fragment = fragmentList.get(i);
                if(fragment!=null) {
                    String fragmentTag = fragment.getTag();
                    Log.d(TAG_NAME_FRAGMENT, fragmentTag);
                }
            }
            Log.d(TAG_NAME_FRAGMENT, "***********************************");
        }
    }
}
