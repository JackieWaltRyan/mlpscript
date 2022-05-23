package com;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class changedb {
    public static void manipulateDb(Context context) {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath("gameloft_sharing"), (SQLiteDatabase.CursorFactory) null);
            long currentTimeMillis = System.currentTimeMillis() / 1000;
            String[] strArr = {"ANMP.GloftPOHM_GAIA_ANON_GLUID", "ANMP.GloftPOHM_GAIA_ENC_KEY_GLUID"};
            for (int i = 0; i < 2; i++) {
                String str = strArr[i];
                String simpleQueryForString = sQLiteDatabase.compileStatement("SELECT substr(value, instr(value, 'time')+7, 10) AS unix_time FROM glshare WHERE key = '" + str + "'").simpleQueryForString();
                sQLiteDatabase.compileStatement("UPDATE glshare SET value = replace(value, '" + simpleQueryForString + "', '" + currentTimeMillis + "') WHERE key = '" + str + "'").executeUpdateDelete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable th) {
            try {
                sQLiteDatabase.close();
            } catch (Exception unused) {
            }
            throw th;
        }
        try {
            sQLiteDatabase.close();
        } catch (Exception unused2) {
        }
    }
}