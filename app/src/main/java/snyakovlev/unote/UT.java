package snyakovlev.unote;

/**
 * Created by Катя on 05.09.2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class UT {  private UT() {}

    private static final String L_TAG = "_X_";

    static final String MYROOT = "nbook";
    static final String MIME_TEXT = "text/plain";
    static final String MIME_JPEG = "image/jpg";
    static final String MIME_PNG = "image/png";
    static final String MIME_AMR = "audio/amr";
    static final String MIME_XML = "application/xml";
    static final String MIME_SQLITE="application/x-sqlite3";
    static final String MIME_FLDR = "application/vnd.google-apps.folder";

    static final String TITL = "titl";
    static final String GDID = "gdid";
    static final String MIME = "mime";

    private static final String TITL_FMT = "yyMMdd-HHmmss";

    private static SharedPreferences pfs;
    static Context acx;
    static void init(Context ctx) {
        acx = ctx.getApplicationContext();
        pfs = PreferenceManager.getDefaultSharedPreferences(acx);
    }

    static class AM { private AM(){}
        private static final String ACC_NAME = "account_name";
        private static String mEmail = null;

        static void setEmail(String email) {
            UT.pfs.edit().putString(ACC_NAME, (mEmail = email)).apply();
        }
        static String getEmail() {
            return mEmail != null ? mEmail : (mEmail = UT.pfs.getString(ACC_NAME, null));
        }
    }

    static ContentValues newCVs(String titl, String gdId, String mime) {
        ContentValues cv = new ContentValues();
        if (titl != null) cv.put(TITL, titl);
        if (gdId != null) cv.put(GDID, gdId);
        if (mime != null) cv.put(MIME, mime);
        return cv;
    }

    private static File cchFile(String flNm) {
        File cche = UT.acx.getExternalCacheDir();
        return (cche == null || flNm == null) ? null : new File(cche.getPath() + File.separator + flNm);
    }
    static File str2File(byte[] str, File fl) {
        if (str == null) return null;
        byte[] buf = str;
        if (fl == null) return null;
        BufferedOutputStream bs = null;
        try {
            bs = new BufferedOutputStream(new FileOutputStream(fl));
            bs.write(buf);
        } catch (Exception e) {
            le(e);
        } finally {
            if (bs != null) try {
                bs.close();
            } catch (Exception e) {
                le(e);
            }
        }
        return fl;
    }
    static byte[] is2Bytes(InputStream is) {
        byte[] buf = null;
        BufferedInputStream bufIS = null;
        if (is != null) try {
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            bufIS = new BufferedInputStream(is);
            buf = new byte[4096];
            int cnt;
            while ((cnt = bufIS.read(buf)) >= 0) {
                byteBuffer.write(buf, 0, cnt);
            }
            buf = byteBuffer.size() > 0 ? byteBuffer.toByteArray() : null;
        } catch (Exception ignore) {}
        finally {
            try {
                if (bufIS != null) bufIS.close();
            } catch (Exception ignore) {}
        }
        return buf;
    }

    static String time2Titl(Long milis) {       // time -> yymmdd-hhmmss
        Date dt = (milis == null) ? new Date() : (milis >= 0) ? new Date(milis) : null;
        return (dt == null) ? null : new SimpleDateFormat(TITL_FMT, Locale.US).format(dt);
    }
    static String titl2Month(String titl) {
        return titl == null ? null : ("20" + titl.substring(0, 2) + "-" + titl.substring(2, 4));
    }

    static void le(Throwable ex) {  Log.e(L_TAG, Log.getStackTraceString(ex));  }
    static void lg(String msg) {  Log.d(L_TAG, msg); }
}
