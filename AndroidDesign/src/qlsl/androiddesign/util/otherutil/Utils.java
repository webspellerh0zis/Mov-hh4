package qlsl.androiddesign.util.otherutil;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 常用工具类
 * Created by shoy on 2014/9/4.
 */
@SuppressLint("NewApi")
public class Utils {
    private static final String TAG = "Utils";
    private static final String JPUSH_APP_KEY = "JPUSH_APPKEY";
    private static String userAgent;
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @SuppressWarnings("unused")
	private static final String DF_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 日期格式：HH:mm *
     */
    public static final String DF_HH_MM = "HH:mm";

    private final static long minute = 60 * 1000;// 1分钟
    private final static long hour = 60 * minute;// 1小时
    private final static long day = 24 * hour;// 1天
    private final static long month = 31 * day;// 月
    @SuppressWarnings("unused")
	private final static long year = 12 * month;// 年

    /**
     * 判断邮箱是否合法
     *
     * @param strEmail 邮箱地址
     * @return boolean
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        return m.matches();
    }

    /**
     * MD5编码
     *
     * @param input 字符串
     * @return Md5
     */
    public static String md5(String input) {
        if (isEmpty(input))
            return input;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(input.getBytes());

            byte byteData[] = md.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aByteData : byteData) {
                String hex = Integer.toHexString(0xff & aByteData);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 隐藏输入法
     */
    public static void closeInputMethod(Context mcontext,EditText edit_jian) {
        InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();
        if (isOpen) {
            imm.hideSoftInputFromWindow(edit_jian.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static String buildParam(SortedMap<String, String> mapParam) {
        StringBuilder sb = new StringBuilder();
        if (mapParam == null || mapParam.size() == 0)
            return sb.toString();
        Set<Map.Entry<String, String>> entrySet = mapParam.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
            try {
                sb.append(entry.getKey()).append("=")
                        .append(URLDecoder.decode(entry.getValue(), "utf-8"))
                        .append("&");
            } catch (UnsupportedEncodingException e) {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
        }
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 接口参数签名
     *
     * @param mapParam 参数
     * @param sb       url链接
     * @return 签名
     */
    public static String signParam(SortedMap<String, String> mapParam,
                                   StringBuffer sb) {
        String pm = buildParam(mapParam);
        sb.append(pm).append("&");
        // 注意+
        String beforeSignStr = pm + "+" + Constants.PARTNER_KEY;
        return md5(beforeSignStr);
    }

    /**
     * 获取返回的实体对象
     *
     * @param context context
     * @param json    json
     * @param tClass  class
     * @param <T>     泛型T
     * @return T
     */
    public static <T> T getResult(Context context, JSONObject json, Class<T> tClass) {
        try {
            boolean status = json.getBoolean(Constants.RESULT_STATUS);
            if (!status) {
                Toast.makeText(context, json.getString(Constants.RESULT_DESC),
                        Toast.LENGTH_SHORT).show();
                return null;
            }
            JSONObject data = json.getJSONObject(Constants.RESULT_DATA);
            if (data == null)
                return null;
            return JSON.toJavaObject(data, tClass);
        } catch (JSONException e) {
            Toast.makeText(context, "返回数据格式异常！", Toast.LENGTH_SHORT)
                    .show();
            return null;
        }
    }

    /**
     * 获取返回的列表对象
     *
     * @param context context
     * @param json    json
     * @param tClass  class
     * @param <T>     泛型T
     * @return T
     */
    public static <T> List<T> getResults(Context context, JSONObject json,
                                         Class<T> tClass) {
        try {
            boolean status = json.getBoolean(Constants.RESULT_STATUS);
            if (!status) {
                Toast.makeText(context, json.getString(Constants.RESULT_DESC),
                        Toast.LENGTH_SHORT).show();
                return null;
            }
            JSONArray data = json.getJSONArray(Constants.RESULT_DATA);
            if (data == null)
                return new ArrayList<T>();
            return JSON.parseArray(data.toJSONString(), tClass);
        } catch (JSONException e) {
            Toast.makeText(context, "返回数据格式异常！", Toast.LENGTH_SHORT)
                    .show();
            return null;
        }
    }

    /**
     * 获取缩略图
     *
     * @param url    基础图片 路径
     * @param width  宽度
     * @param height 高度
     * @return 缩略图地址
     */
    public static String makeThumb(String url, int width, int height) {
        if (isEmpty(url))
            return url;
        width = (width <= 0 ? 120 : width);
        String fileName = url;
        String ext = fileName.substring(fileName.lastIndexOf("."));
        if (ext.isEmpty())
            return fileName;
        fileName = url.replaceAll(ext, "_s" + width + "x" + (height <= 0 ? "auto" : height) + ext);
        return fileName;
    }

    /**
     * 根据手机分辨率从dp转成px
     *
     * @param context 当前实例
     * @param dpValue dp
     * @return px
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 空字符判断
     *
     * @param s 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String s) {
        return null == s || s.length() == 0 || s.trim().length() == 0;
    }

    /**
     * 校验Tag Alias 只能是数字、英文字母和中文
     *
     * @param s 字符
     * @return 是否符合
     */
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]*$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * Jpush取得AppKey
     *
     * @param context 当前实例
     * @return AppKey
     */
    public static String getAppKey(Context context) {
        Bundle metaData = null;
        String appKey = null;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai)
                metaData = ai.metaData;
            if (null != metaData) {
                appKey = metaData.getString(JPUSH_APP_KEY);
                if ((null == appKey) || appKey.length() != 24) {
                    appKey = null;
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getAppKey Exception", e);
        }
        return appKey;
    }

    /**
     * 获取版本
     *
     * @param context 当前实例
     * @return 版本
     */
    public static String getVersion(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "Unknown";
        }
    }

    /**
     * 显示提示信息
     *
     * @param toast   信息
     * @param context 当前实例
     */
    public static void showToast(final String toast, final Context context) {
        showToast(toast, context, Toast.LENGTH_SHORT);
    }

    /**
     * 显示提示信息
     *
     * @param toast   信息
     * @param context 当前实例
     */
    public static void showToast(final String toast, final Context context, final int duration) {
        //在异步线程中调用UI组件，必须在Looper.prepare() 和 Looper.loop()中调用
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, toast, duration).show();
                Looper.loop();
            }
        }).start();
    }

    /**
     * 连接状态
     *
     * @param context 当前实例
     * @return 是否连接
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnectedOrConnecting());
    }

    public static String getImei(Context context, String imei) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            imei = telephonyManager.getDeviceId();
        } catch (Exception e) {
            Log.e(Utils.class.getSimpleName(), e.getMessage());
        }
        return imei;
    }

    /**
     * 获取客户端环境
     *
     * @return 客户端环境
     */
    public static String getUserAgent(Context context) {
        if (isEmpty(userAgent)) {
            StringBuilder ua = new StringBuilder("Dayeasy-EBook");
            PackageInfo appInfo = AppConfigUtils.getPackageInfo();
            if (appInfo != null) {
                //版本信息
                ua.append("/").append(appInfo.versionName).append("_").append(appInfo.versionCode);
            }
            //手机系统版本
            ua.append("/android ").append(android.os.Build.VERSION.RELEASE);
            //手机型号
            ua.append("/").append(android.os.Build.MODEL);
            //客户端唯一标识
            ua.append("/").append(AppConfigUtils.getAppId());
            userAgent = ua.toString();
        }
        return userAgent;
    }

    /**
     * 获取UUID
     *
     * @return 32UUID小写字符串
     */
    public static String gainUUID() {
        String strUUID = UUID.randomUUID().toString();
        strUUID = strUUID.replaceAll("-", "").toLowerCase();
        return strUUID;
    }

    public static int gainIntRandom(int min, int max) {
        return (int) Math.floor((Math.random() * max + min) % max + 1);
    }

    public static String dateFormat(long time) {
        return dateFormat(time, "");
    }

    @SuppressLint("SimpleDateFormat")
	public static String dateFormat(long time, String format) {
        if (isEmpty(format))
            format = DEFAULT_DATE_FORMAT;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = new Date(time - TimeZone.getDefault().getRawOffset());
        return sdf.format(date);
    }

    public static String dateFormatForChat(long time) {
        Time then = new Time();
        then.set(time - TimeZone.getDefault().getRawOffset());
        Time now = new Time();
        now.setToNow();
        String formatStr;
        if (then.year != now.year) {
            formatStr = "yyyy-MM-dd HH:mm";
        } else if (then.yearDay != now.yearDay && (now.yearDay - then.yearDay) > 1) {
            formatStr = "MM-dd HH:mm";
        } else {
            formatStr = "HH:mm";
        }

        if (then.year == now.year && then.yearDay == now.yearDay) {
            //今天
            return dateFormat(time, formatStr);
        } else if ((then.year == now.year) && ((now.yearDay - then.yearDay) == 1)) {
            //昨天
            return "昨天 " + dateFormat(time, formatStr);
        } else {
            return dateFormat(time, formatStr);
        }
    }
    
    public static String dateFormatForMemos(long time) {
        Time then = new Time();
        then.set(time - TimeZone.getDefault().getRawOffset());
        Time now = new Time();
        now.setToNow();
        String formatStr;
        if (then.year != now.year) {
            formatStr = "yyyy-MM-dd HH:mm";
        } else if (then.yearDay != now.yearDay && (now.yearDay - then.yearDay) > 1) {
            formatStr = "MM-dd";
        } else {
            formatStr = "HH:mm";
        }

        if (then.year == now.year && then.yearDay == now.yearDay) {
            //今天
            return dateFormat(time, formatStr);
        } else if ((then.year == now.year) && ((now.yearDay - then.yearDay) == 1)) {
            //昨天
            return "昨天 " + dateFormat(time, formatStr);
        } else {
            return dateFormat(time, formatStr);
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static long dateToLong(String date) {
        if (isEmpty(date))
            return 0L;
        date = date.replaceAll("T", " ").replaceAll("Z", "");
        try {
            Date d = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(date);
            return d.getTime() + TimeZone.getDefault().getRawOffset();// 获取标准格林尼治时间下日期时间对应的时间戳即东八区的时间戳
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 根据图片名称进行排序
     *
     * @param dirPath 文件路径
     * @return 返回文件列表数组
     */
    public static File[] getFilesByOrder(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        File[] files = dirFile.listFiles();
        File temp;
        for (int i = 0; i < files.length; i++) {
            for (int j = 0; j > files.length - i - 1; j++) {
                if (files[j].getName().compareTo(files[j + 1].getName()) > 0) {
                    temp = files[j];
                    files[j] = files[j + 1];
                    files[j + 1] = temp;
                }
            }
        }
        return files;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName 文件路径/文件名
     * @return 扩展名
     */
    public static String getFileExt(String fileName) {
        String ext = "";
        if (!isEmpty(fileName)) {
            ext = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
        }
        return ext;
    }
}
