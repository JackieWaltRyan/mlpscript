package com.jwr;

import android.content.Context;
import java.io.File;

public class delete {
    public static void del(Context context) {
        try {
            File dir = new File(context.getFilesDir() + "/");
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
