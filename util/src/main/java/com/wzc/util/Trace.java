package com.wzc.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by wzc on 2017/8/24.
 */

public class Trace {
    public static  boolean PRINT_LOG = false;

    public static  void setPrintLogFlag(Context context){
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            File file = new File(context.getExternalFilesDir(null), "00000000");
            if (file.exists()){
                PRINT_LOG = true;
            }
        }
    }
}
