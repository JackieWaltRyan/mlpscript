package com.jwr;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class copy {
    public static void cpl(Context context) {
        try {
            final String mlpSavePrime = "mlp_save_prime.dat";
            File saveFile = new File(context.getExternalCacheDir(), mlpSavePrime);

            if (saveFile.exists()) {
                try {
                    File dir = new File(context.getFilesDir() + "/");
                    for (File file : dir.listFiles()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream in = null;
                    OutputStream out = null;
                    File targetFile = new File(context.getFilesDir(), mlpSavePrime);
                    targetFile.getParentFile().mkdirs();

                    in = new FileInputStream(saveFile);
                    out = new FileOutputStream(targetFile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    out.close();
                    in.close();
                    new File(String.valueOf(saveFile)).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            final String gameloftSharing = "gameloft_sharing";
            File gameloftSharingFile = new File(context.getExternalCacheDir(), gameloftSharing);

            if (gameloftSharingFile.exists()) {
                final File databaseFolder = new File(context.getDatabasePath("gameloft_sharing").getParentFile().getPath());
                try {
                    for (File f : databaseFolder.listFiles()) {
                        f.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    InputStream in = null;
                    OutputStream out = null;
                    File outputFile = new File(databaseFolder, gameloftSharing);
                    outputFile.getParentFile().mkdirs();
                    in = new FileInputStream(gameloftSharingFile);
                    out = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    out.close();
                    in.close();
                    new File(String.valueOf(gameloftSharingFile)).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
