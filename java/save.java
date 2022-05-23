package com;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class save {
    private static String path = "";
    private static String r0 = "res0";
    private static String r1 = "res1";

    public static void a(Context context) {
        for (int i = 0; i < 2; i++) {
            if (r(i, context)) {
                unZip(i, context);
            }
        }
    }

    private static String d(int i, Context context) {
        return o((i == 0 ? context.getFilesDir() : context.getExternalCacheDir()).getPath());
    }

    private static InputStream is(int i, Context context) throws Throwable {
        AssetManager assets = context.getAssets();
        StringBuilder sb = new StringBuilder();
        sb.append(path);
        sb.append(i == 0 ? r0 : r1);
        return assets.open(sb.toString());
    }

    private static String o(String str) {
        return str.substring(0, str.length() - 6);
    }

    private static boolean r(int i, Context context) {
        try {
            return is(i, context).available() > 0;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static void unZip(int i, Context context) {
        try {
            ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(is(i, context)));
            byte[] bArr = new byte[8192];
            while (true) {
                ZipEntry nextEntry = zipInputStream.getNextEntry();
                if (nextEntry != null) {
                    File file = new File(d(i, context), nextEntry.getName());
                    if (!file.exists()) {
                        File parentFile = nextEntry.isDirectory() ? file : file.getParentFile();
                        if (!parentFile.isDirectory() && !parentFile.mkdirs()) {
                            throw new FileNotFoundException("Failed to ensure directory: " + parentFile.getAbsolutePath());
                        } else if (!nextEntry.isDirectory()) {
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            while (true) {
                                int read = zipInputStream.read(bArr);
                                if (read == -1) {
                                    break;
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                            fileOutputStream.close();
                        }
                    }
                } else {
                    zipInputStream.close();
                    return;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
