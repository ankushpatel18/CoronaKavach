package com.cosafe.android.utils;

import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.IOException;

public class LocationFeedback {
    static final String FILE_NAME = "location_file.txt";
    private static final String FOLDER_NAME = "SafeTogetherLogger";
    public static final String TAG = LocationFeedback.class.getSimpleName();

    private static void createNewFile() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SafeTogetherLogger");
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                String str = TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("Root Folder created status:");
                sb.append(mkdirs);
                Log.e(str, sb.toString());
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(file);
            sb2.append(File.separator);
            sb2.append(FILE_NAME);
            boolean createNewFile = new File(sb2.toString()).createNewFile();
            String str2 = TAG;
            StringBuilder sb3 = new StringBuilder();
            sb3.append("Log File Created status:");
            sb3.append(createNewFile);
            Log.e(str2, sb3.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File getFile() {
        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        StringBuilder sb = new StringBuilder();
        sb.append("SafeTogetherLogger");
        sb.append(File.separator);
        sb.append(FILE_NAME);
        File file = new File(absolutePath, sb.toString());
        if (!file.exists()) {
            createNewFile();
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0059 A[SYNTHETIC, Splitter:B:21:0x0059] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0064 A[SYNTHETIC, Splitter:B:26:0x0064] */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void writeToFile(String r7) {
        /*
            java.io.File r0 = getFile()
            boolean r1 = r0.exists()
            if (r1 == 0) goto L_0x006d
            r1 = 0
            java.io.FileWriter r2 = new java.io.FileWriter     // Catch:{ IOException -> 0x0053 }
            r3 = 1
            r2.<init>(r0, r3)     // Catch:{ IOException -> 0x0053 }
            long r3 = r0.length()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r5 = 10485760(0xa00000, double:5.180654E-317)
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 <= 0) goto L_0x0039
            boolean r0 = r0.delete()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            java.lang.String r1 = TAG     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r3.<init>()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            java.lang.String r4 = "Log file delete status :"
            r3.append(r4)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r3.append(r0)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            java.lang.String r0 = r3.toString()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            android.util.Log.e(r1, r0)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            createNewFile()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
        L_0x0039:
            r2.append(r7)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            java.lang.String r7 = "\n"
            r2.append(r7)     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.flush()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.close()     // Catch:{ IOException -> 0x004e, all -> 0x004b }
            r2.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x006d
        L_0x004b:
            r7 = move-exception
            r1 = r2
            goto L_0x0062
        L_0x004e:
            r7 = move-exception
            r1 = r2
            goto L_0x0054
        L_0x0051:
            r7 = move-exception
            goto L_0x0062
        L_0x0053:
            r7 = move-exception
        L_0x0054:
            r7.printStackTrace()     // Catch:{ all -> 0x0051 }
            if (r1 == 0) goto L_0x006d
            r1.close()     // Catch:{ IOException -> 0x005d }
            goto L_0x006d
        L_0x005d:
            r7 = move-exception
            r7.printStackTrace()
            goto L_0x006d
        L_0x0062:
            if (r1 == 0) goto L_0x006c
            r1.close()     // Catch:{ IOException -> 0x0068 }
            goto L_0x006c
        L_0x0068:
            r0 = move-exception
            r0.printStackTrace()
        L_0x006c:
            throw r7
        L_0x006d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cosafe.android.utils.LocationFeedback.writeToFile(java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:28:0x0051 A[SYNTHETIC, Splitter:B:28:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x005e A[SYNTHETIC, Splitter:B:36:0x005e] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static String readFromFile() {
        /*
            java.io.File r0 = getFile()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r2 = 0
            boolean r3 = r0.isFile()     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004d }
            if (r3 == 0) goto L_0x003c
            boolean r3 = r0.exists()     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004d }
            if (r3 == 0) goto L_0x003c
            java.io.BufferedReader r3 = new java.io.BufferedReader     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004d }
            java.io.FileReader r4 = new java.io.FileReader     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004d }
            r4.<init>(r0)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004d }
            r3.<init>(r4)     // Catch:{ FileNotFoundException -> 0x005a, IOException -> 0x004d }
            java.lang.String r0 = r3.readLine()     // Catch:{ FileNotFoundException -> 0x003a, IOException -> 0x0038, all -> 0x0035 }
        L_0x0024:
            if (r0 == 0) goto L_0x0033
            r1.append(r0)     // Catch:{ FileNotFoundException -> 0x003a, IOException -> 0x0038, all -> 0x0035 }
            java.lang.String r0 = "\n"
            r1.append(r0)     // Catch:{ FileNotFoundException -> 0x003a, IOException -> 0x0038, all -> 0x0035 }
            java.lang.String r0 = r3.readLine()     // Catch:{ FileNotFoundException -> 0x003a, IOException -> 0x0038, all -> 0x0035 }
            goto L_0x0024
        L_0x0033:
            r2 = r3
            goto L_0x003c
        L_0x0035:
            r0 = move-exception
            r2 = r3
            goto L_0x0067
        L_0x0038:
            r2 = r3
            goto L_0x004d
        L_0x003a:
            r2 = r3
            goto L_0x005a
        L_0x003c:
            if (r2 == 0) goto L_0x0046
            r2.close()     // Catch:{ IOException -> 0x0042 }
            goto L_0x0046
        L_0x0042:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0046:
            java.lang.String r0 = r1.toString()
            return r0
        L_0x004b:
            r0 = move-exception
            goto L_0x0067
        L_0x004d:
            java.lang.String r0 = "Error reading log file"
            if (r2 == 0) goto L_0x0059
            r2.close()     // Catch:{ IOException -> 0x0055 }
            goto L_0x0059
        L_0x0055:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0059:
            return r0
        L_0x005a:
            java.lang.String r0 = "File not found"
            if (r2 == 0) goto L_0x0066
            r2.close()     // Catch:{ IOException -> 0x0062 }
            goto L_0x0066
        L_0x0062:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0066:
            return r0
        L_0x0067:
            if (r2 == 0) goto L_0x0071
            r2.close()     // Catch:{ IOException -> 0x006d }
            goto L_0x0071
        L_0x006d:
            r1 = move-exception
            r1.printStackTrace()
        L_0x0071:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cosafe.android.utils.LocationFeedback.readFromFile():java.lang.String");
    }
}
