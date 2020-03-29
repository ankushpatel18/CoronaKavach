package com.cosafe.android.utils;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager.WakeLock;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.cosafe.android.R;
import com.cosafe.android.MainActivity;
import com.cosafe.android.models.GetUserID;
import com.cosafe.android.models.SaveUserDetails;
import com.cosafe.android.models.getAllupdateflag;
import com.cosafe.android.utils.retrofit.RetrofitAPIManager;
import com.cosafe.android.utils.retrofit.RetrofitResponseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.internal.cache.DiskLruCache;
import org.json.JSONObject;

public class DownloadLocationDataManager extends AsyncTask<String, Integer, String> implements RetrofitResponseListener {
    private static final String FILE_NAME = "location_file_down.txt";
    public static final String FILE_NAME_BLUETOOTH = "bluetooth_file.txt";
    private static final String FILE_NAME_LOCATION = "location_file.txt";
    private static final String FOLDER_NAME = "SafeTogetherLogger";
    private static final String TAG = "DownloadLocationDataManager";
    private String Flag1;
    private String Flag2;
    private String Flag3;
    /* access modifiers changed from: private */
    public MainActivity activity;
    private RetrofitAPIManager mApiManager;
    private UpdateAPIListener mListener;
    private WakeLock mWakeLock;
    private String updatedFlag1;
    private String updatedFlag2;
    private String updatedFlag3;

    public interface UpdateAPIListener {
        void onUpdateAPIResult();
    }

    private class UploadBluetoothTask extends AsyncTask<Void, Void, String> {
        private UploadBluetoothTask() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(Void... voidArr) {
            String str = "bluetooth_file.txt";
            String str2 = "";
            try {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SafeTogetherLogger");
                StringBuilder sb = new StringBuilder();
                sb.append(file);
                sb.append(File.separator);
                sb.append(str);
                File file2 = new File(sb.toString());
                MediaType parse = MediaType.parse("text/plain");
                MyPreferences myPreferences = new MyPreferences(DownloadLocationDataManager.this.activity);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("https://test1.ncog.gov.in/CoSafe_key/uploadfile?mobile=");
                sb2.append(myPreferences.getPref(MyPreferences.PREF_MOBILE_NUMBER, str2));
                sb2.append("&type=blth&flag=1");
                String sb3 = sb2.toString();
                return new OkHttpClient().newCall(new Builder().addHeader("mobile", PreferenceManager.read(PreferenceManager.USER_MOBILE_NO, str2)).addHeader("authkey", PreferenceManager.read(PreferenceManager.KEY_TOKEN, str2)).url(sb3).post(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", str, RequestBody.create(parse, file2)).build()).build()).execute().body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (str != null) {
                try {
                    new JSONObject(str).optString("statusCode").equalsIgnoreCase(DiskLruCache.VERSION_1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class UploadLocationTask extends AsyncTask<Void, Void, String> {
        private UploadLocationTask() {
        }

        /* access modifiers changed from: protected */
        public String doInBackground(Void... voidArr) {
            String str = DownloadLocationDataManager.FILE_NAME_LOCATION;
            String str2 = "";
            try {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "SafeTogetherLogger");
                StringBuilder sb = new StringBuilder();
                sb.append(file);
                sb.append(File.separator);
                sb.append(str);
                File file2 = new File(sb.toString());
                MediaType parse = MediaType.parse("text/plain");
                MyPreferences myPreferences = new MyPreferences(DownloadLocationDataManager.this.activity);
                StringBuilder sb2 = new StringBuilder();
                sb2.append("https://test1.ncog.gov.in/CoSafe_key/uploadfile?mobile=");
                sb2.append(myPreferences.getPref(MyPreferences.PREF_MOBILE_NUMBER, str2));
                sb2.append("&type=latlon&flag=1");
                String sb3 = sb2.toString();
                return new OkHttpClient().newCall(new Builder().addHeader("mobile", PreferenceManager.read(PreferenceManager.USER_MOBILE_NO, str2)).addHeader("authkey", PreferenceManager.read(PreferenceManager.KEY_TOKEN, str2)).url(sb3).post(new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", str, RequestBody.create(parse, file2)).build()).build()).execute().body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            if (str != null) {
                try {
                    new JSONObject(str).optString("statusCode").equalsIgnoreCase(DiskLruCache.VERSION_1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void isError(String str) {
    }

    public DownloadLocationDataManager(MainActivity mainActivity) {
        this.activity = mainActivity;
    }

    private String compare(String str) throws IOException {
        Point[] readFile = readFile(str);
        Point[] readFile2 = readFile(FILE_NAME_LOCATION);
        int i = 0;
        for (int i2 = 0; i2 < readFile.length; i2++) {
            Range findRelatedRange = findRelatedRange(readFile[i2], readFile2);
            for (int i3 = findRelatedRange.start; i3 < findRelatedRange.end; i3++) {
                if (geoDistance(readFile[i2].lat, readFile[i3].lng, readFile2[i3].lat, readFile2[i3].lng) < 10.0d) {
                    i++;
                }
            }
        }
        return i > 0 ? "YELLOW" : "GREEN";
    }

    /* JADX WARNING: type inference failed for: r3v1 */
    /* JADX WARNING: type inference failed for: r3v2, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r3v3, types: [java.io.OutputStream] */
    /* JADX WARNING: type inference failed for: r7v0, types: [java.io.OutputStream, java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r3v4 */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* JADX WARNING: type inference failed for: r3v9 */
    /* JADX WARNING: type inference failed for: r3v14 */
    /* JADX WARNING: type inference failed for: r3v15 */
    /* JADX WARNING: type inference failed for: r3v16 */
    /* JADX WARNING: type inference failed for: r3v17 */
    /* JADX WARNING: type inference failed for: r3v18 */
    /* JADX WARNING: type inference failed for: r3v20 */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x0128 A[SYNTHETIC, Splitter:B:102:0x0128] */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x0109 A[SYNTHETIC, Splitter:B:84:0x0109] */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public String doInBackground(String... r16) {
        /*
            r15 = this;
            r1 = r15
            java.io.File r0 = android.os.Environment.getExternalStorageDirectory()
            java.io.File r2 = new java.io.File
            java.lang.String r0 = r0.getAbsolutePath()
            java.lang.String r3 = "SafeTogetherLogger"
            r2.<init>(r0, r3)
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r2)
            java.lang.String r2 = java.io.File.separator
            r0.append(r2)
            java.lang.String r2 = "location_file_down.txt"
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            r3 = 0
            java.net.URL r0 = new java.net.URL     // Catch:{ Exception -> 0x0100, all -> 0x00fb }
            r4 = 0
            r5 = r16[r4]     // Catch:{ Exception -> 0x0100, all -> 0x00fb }
            r0.<init>(r5)     // Catch:{ Exception -> 0x0100, all -> 0x00fb }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ Exception -> 0x0100, all -> 0x00fb }
            r5 = r0
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ Exception -> 0x0100, all -> 0x00fb }
            r5.connect()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            int r0 = r5.getResponseCode()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            r6 = 200(0xc8, float:2.8E-43)
            if (r0 == r6) goto L_0x0074
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            r0.<init>()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.lang.String r4 = "Server returned HTTP "
            r0.append(r4)     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            int r4 = r5.getResponseCode()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            r0.append(r4)     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.lang.String r4 = " "
            r0.append(r4)     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.lang.String r4 = r5.getResponseMessage()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            r0.append(r4)     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.lang.String r3 = r0.toString()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.lang.String r0 = r15.compare(r2)     // Catch:{ IOException -> 0x0067 }
            return r0
        L_0x0067:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ IOException -> 0x006d }
            goto L_0x006e
        L_0x006d:
        L_0x006e:
            if (r5 == 0) goto L_0x0073
            r5.disconnect()
        L_0x0073:
            return r3
        L_0x0074:
            int r0 = r5.getContentLength()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.io.InputStream r6 = r5.getInputStream()     // Catch:{ Exception -> 0x00f8, all -> 0x00f4 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00f2 }
            r7.<init>(r2)     // Catch:{ Exception -> 0x00f2 }
            r8 = 4096(0x1000, float:5.74E-42)
            byte[] r8 = new byte[r8]     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r9 = 0
        L_0x0087:
            int r11 = r6.read(r8)     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r12 = -1
            if (r11 == r12) goto L_0x00d0
            boolean r12 = r15.isCancelled()     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            if (r12 == 0) goto L_0x00b1
            r6.close()     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r7.close()     // Catch:{ IOException -> 0x00aa }
            java.lang.String r0 = r15.compare(r2)     // Catch:{ IOException -> 0x009f }
            return r0
        L_0x009f:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ IOException -> 0x00aa }
            if (r6 == 0) goto L_0x00ab
            r6.close()     // Catch:{ IOException -> 0x00aa }
            goto L_0x00ab
        L_0x00aa:
        L_0x00ab:
            if (r5 == 0) goto L_0x00b0
            r5.disconnect()
        L_0x00b0:
            return r3
        L_0x00b1:
            long r12 = (long) r11
            long r9 = r9 + r12
            if (r0 <= 0) goto L_0x00c9
            r12 = 1
            java.lang.Integer[] r12 = new java.lang.Integer[r12]     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r13 = 100
            long r13 = r13 * r9
            long r3 = (long) r0     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            long r13 = r13 / r3
            int r3 = (int) r13     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r4 = 0
            r12[r4] = r3     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r15.publishProgress(r12)     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
        L_0x00c9:
            r3 = 0
            r7.write(r8, r3, r11)     // Catch:{ Exception -> 0x00ef, all -> 0x00eb }
            r3 = 0
            r4 = 0
            goto L_0x0087
        L_0x00d0:
            r7.close()     // Catch:{ IOException -> 0x00e3 }
            java.lang.String r0 = r15.compare(r2)     // Catch:{ IOException -> 0x00d8 }
            return r0
        L_0x00d8:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ IOException -> 0x00e3 }
            if (r6 == 0) goto L_0x00e4
            r6.close()     // Catch:{ IOException -> 0x00e3 }
            goto L_0x00e4
        L_0x00e3:
        L_0x00e4:
            if (r5 == 0) goto L_0x00e9
            r5.disconnect()
        L_0x00e9:
            r3 = 0
            return r3
        L_0x00eb:
            r0 = move-exception
            r4 = r0
            r3 = r7
            goto L_0x0126
        L_0x00ef:
            r0 = move-exception
            r3 = r7
            goto L_0x0103
        L_0x00f2:
            r0 = move-exception
            goto L_0x0103
        L_0x00f4:
            r0 = move-exception
            r4 = r0
            r6 = r3
            goto L_0x0126
        L_0x00f8:
            r0 = move-exception
            r6 = r3
            goto L_0x0103
        L_0x00fb:
            r0 = move-exception
            r4 = r0
            r5 = r3
            r6 = r5
            goto L_0x0126
        L_0x0100:
            r0 = move-exception
            r5 = r3
            r6 = r5
        L_0x0103:
            java.lang.String r4 = r0.toString()     // Catch:{ all -> 0x0124 }
            if (r3 == 0) goto L_0x010f
            r3.close()     // Catch:{ IOException -> 0x010d }
            goto L_0x010f
        L_0x010d:
            goto L_0x011e
        L_0x010f:
            java.lang.String r0 = r15.compare(r2)     // Catch:{ IOException -> 0x0114 }
            return r0
        L_0x0114:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ IOException -> 0x010d }
            if (r6 == 0) goto L_0x011e
            r6.close()     // Catch:{ IOException -> 0x010d }
        L_0x011e:
            if (r5 == 0) goto L_0x0123
            r5.disconnect()
        L_0x0123:
            return r4
        L_0x0124:
            r0 = move-exception
            r4 = r0
        L_0x0126:
            if (r3 == 0) goto L_0x012e
            r3.close()     // Catch:{ IOException -> 0x012c }
            goto L_0x012e
        L_0x012c:
            goto L_0x013d
        L_0x012e:
            java.lang.String r0 = r15.compare(r2)     // Catch:{ IOException -> 0x0133 }
            return r0
        L_0x0133:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()     // Catch:{ IOException -> 0x012c }
            if (r6 == 0) goto L_0x013d
            r6.close()     // Catch:{ IOException -> 0x012c }
        L_0x013d:
            if (r5 == 0) goto L_0x0142
            r5.disconnect()
        L_0x0142:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.cosafe.android.utils.DownloadLocationDataManager.doInBackground(java.lang.String[]):java.lang.String");
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        TextView textView = (TextView) this.activity.findViewById(R.id.tv_scroll_text);
        if (str == "YELLOW") {
            textView.setBackgroundColor(-16711936);
            showDialog("Don't panic everything looks good but we are analysing your report for more accurate feedback .");
            new UploadBluetoothTask().execute(new Void[0]);
            new UploadLocationTask().execute(new Void[0]);
            this.mApiManager.getAllupdateflag(new MyPreferences(this.activity).getPref(MyPreferences.PREF_MOBILE_NUMBER, ""), DiskLruCache.VERSION_1, "4", "latlon");
        } else if (str == "GREEN") {
            textView.setBackgroundColor(-16711936);
            textView.setText("CODE GREEN: ");
            showDialog("ALL is Well");
        }
    }

    /* access modifiers changed from: 0000 */
    public Point[] readFile(String str) throws IOException {
        File file = new File(new File(Environment.getExternalStorageDirectory(), "SafeTogetherLogger"), str);
        ArrayList arrayList = new ArrayList();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return (Point[]) arrayList.toArray();
            }
            String[] split = readLine.split(",");
            if (split.length > 4) {
                Point point = new Point(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Long.parseLong(split[4]));
                arrayList.add(point);
            }
        }
    }

    public static double geoDistance(double d, double d2, double d3, double d4) {
        if (d == d3 && d2 == d4) {
            return 0.0d;
        }
        double radians = Math.toRadians(d2);
        double radians2 = Math.toRadians(d4);
        double radians3 = Math.toRadians(d);
        double radians4 = Math.toRadians(d3);
        return Math.asin(Math.sqrt(Math.pow(Math.sin((radians4 - radians3) / 2.0d), 2.0d) + (Math.cos(radians3) * Math.cos(radians4) * Math.pow(Math.sin((radians2 - radians) / 2.0d), 2.0d)))) * 2.0d * 6371.0d * 1000.0d;
    }

    private Range findRelatedRange(Point point, Point[] pointArr) {
        long j = point.time - 5000;
        int length = pointArr.length;
        int i = (length + 0) >> 1;
        int i2 = 0;
        while (i2 < length) {
            i = (i2 + length) >> 1;
            if (pointArr[i].time < j) {
                i2 = i + 1;
            } else {
                length = i;
            }
        }
        int i3 = i;
        while (i3 < pointArr.length && Math.abs(pointArr[i3].time - point.time) < 3600000) {
            i3++;
        }
        return new Range(i, i3);
    }

    private void showDialog(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.activity);
        builder.setTitle((CharSequence) "YOUR REPORT");
        builder.setMessage((CharSequence) str).setPositiveButton((CharSequence) "OK", (OnClickListener) new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog create = builder.create();
        create.setCancelable(false);
        create.show();
    }

    public void isSuccess(Object obj, int i) {
        String str = "Please take survey and see a doctor at your convenience if have any symptoms";
        String str2 = "All is Well";
        String str3 = "";
        String str4 = MyPreferences.PREF_MOBILE_NUMBER;
        String str5 = DiskLruCache.VERSION_1;
        switch (i) {
            case 104:
                GetUserID getUserID = (GetUserID) obj;
                if (getUserID == null || getUserID.getStatusCode() == null || getUserID.getMessage().equalsIgnoreCase("success")) {
                    this.Flag1 = getUserID.getRows().getFlag1();
                    this.Flag2 = getUserID.getRows().getFlag2();
                    String flag3 = getUserID.getRows().getFlag3();
                    this.Flag3 = flag3;
                    if (flag3.equals(str5)) {
                        this.mApiManager.getUserIDRequest(new MyPreferences(this.activity).getPref(str4, str3));
                    }
                    if (this.Flag1.equals(str5)) {
                        showDialog(str);
                    } else {
                        showDialog(str2);
                    }
                    if (this.Flag2.equals(str5)) {
                        showDialog("Quarantine with 14 Days till quarantine.");
                        return;
                    }
                    return;
                }
                return;
            case 105:
                getAllupdateflag getallupdateflag = (getAllupdateflag) obj;
                if (getallupdateflag != null && getallupdateflag.getStatusCode().equals(str5)) {
                    this.updatedFlag1 = getallupdateflag.getRows().getFlag1();
                    this.updatedFlag2 = getallupdateflag.getRows().getFlag2();
                    this.updatedFlag3 = getallupdateflag.getRows().getFlag3();
                    if (this.updatedFlag1.equals(str5)) {
                        showDialog(str);
                    } else {
                        showDialog(str2);
                    }
                    if (this.updatedFlag2.equals(str5)) {
                        showDialog("Please set your quarantine zone, after pressing the location button");
                        UpdateAPIListener updateAPIListener = this.mListener;
                        if (updateAPIListener != null) {
                            updateAPIListener.onUpdateAPIResult();
                        }
                    }
                    if (this.updatedFlag3.equals(str5)) {
                        this.mApiManager.getUserIDRequest(new MyPreferences(this.activity).getPref(str4, str3));
                        return;
                    }
                    return;
                }
                return;
            case 106:
                SaveUserDetails saveUserDetails = (SaveUserDetails) obj;
                if (saveUserDetails != null && saveUserDetails.getStatusCode().equals(str5)) {
                    saveUserDetails.getRows().getIsactive();
                    saveUserDetails.getRows().getQuarantine_arr_ppl_count();
                    saveUserDetails.getRows().getQuarantine_curr_location();
                    return;
                }
                return;
            default:
                return;
        }
    }
}
