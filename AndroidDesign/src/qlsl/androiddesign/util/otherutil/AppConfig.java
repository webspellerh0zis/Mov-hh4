package qlsl.androiddesign.util.otherutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * 全局配置文件管理
 * Created by shay on 2015/4/1.
 */
@SuppressLint("NewApi")
public class AppConfig {
    private static final String TAG = "AppConfig";
    private final static String APP_CONFIG = "config";
    @SuppressLint("NewApi")
    public final static String BASE_PATH = Environment.getExternalStorageDirectory()
            + File.separator
            + Constants.BASE_DIR
            + File.separator;
    private Context mContext;
    private static AppConfig appConfig;
    private static String configPath;

    /**
     * 单例实例
     *
     * @param context 当前上下文
     * @return AppConfig
     */
    public static AppConfig getInstance(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
            File baseFile = new File(BASE_PATH);
            if (!baseFile.exists()) {
                baseFile.mkdirs();
            }
        }
        return appConfig;
    }

    /**
     * 获取key值
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        Properties props = get();
        return (props != null ? props.getProperty(key) : null);
    }

    /**
     * 设置键值
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, String value) {
        Properties props = get();
        props.setProperty(key, value);
        setProps(props);
    }

    /**
     * 删除键
     *
     * @param key 键名
     */
    public void remove(String... key) {
        Properties props = get();
        for (String k : key)
            props.remove(k);
        setProps(props);
    }

    public void set(Properties ps) {
        Properties props = get();
        props.putAll(ps);
        setProps(props);
    }

    public Properties get() {
        FileInputStream fis = null;
        Properties props = new Properties();
        String path = getConfigPath();
        if (!(new File(path)).exists())
            return props;
        try {
            // 读取app_config目录下的config
            fis = new FileInputStream(path);
            props.load(fis);
        } catch (Exception e) {
            Log.e(TAG, "获取配置文件出错", e);
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Exception e) {
                Log.e(TAG, "关闭文件出错", e);
            }
        }
        return props;
    }

    private void setProps(Properties p) {
        FileOutputStream fos = null;
        try {
            // 把config建在files目录下
            fos = new FileOutputStream(getConfigPath());
            p.store(fos, null);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null)
                    fos.close();
            } catch (Exception e) {
                Log.e(TAG, "关闭文件出错", e);
            }
        }
    }

    private String getConfigPath() {
        if (Utils.isEmpty(configPath)) {
            File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
            configPath = dirConf.getPath() + File.separator + APP_CONFIG;
        }
        Log.d(TAG, "configPath:" + configPath);
        return configPath;
    }
}
