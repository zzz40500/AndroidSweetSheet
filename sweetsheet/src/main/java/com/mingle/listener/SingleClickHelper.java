package com.mingle.listener;

import android.os.SystemClock;

/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 *
 */
public  class SingleClickHelper {

    private static long L_CLICK_INTERVAL = 400;
    private long preClickTime;


    public boolean clickEnable(){
        long clickTime= SystemClock.elapsedRealtime();
        if ( clickTime - preClickTime > L_CLICK_INTERVAL){
            preClickTime=clickTime;
            return true;
        }
        return false;
    }
}
