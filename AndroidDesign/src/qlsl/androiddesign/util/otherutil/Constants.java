package qlsl.androiddesign.util.otherutil;

/**
 * 全局静态变量
 * Created by shoy on 2014/9/4.
 */
public final class Constants {
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";
    public static final String HTTP_TYPE = "type";
    public static final String BASE_DIR = "dayeasy";
    public static final String APP_UUID = "app_uuid";
    public static final String APP_NAME = "Dayeasy-EBook";
    public static final String TAG = APP_NAME;
    public static final String ACTIVITY_DTO_KEY = "ACTIVITY_DTO_KEY";
    public static final String USER_TOKEN_KEY = "user_token_key";
    public static final String USER_STARTED = "is_started";

    //文件夹
    public static final String DIR_IMAGE = "images";
    public static final String DIR_TEMP = "temp";
    //文件
    public static final String FILE_History = "fileHistory.txt";

    //    public static final String PARTNER = "test";
//  public static final String REST_SERVER = "http://192.168.100.105:1231/router";//聪哥电脑
    public static final String PARTNER = "android_ebook";
    public static final int MANIFEST_TYPE = 30;

    //测试服务器
    public static final String PARTNER_KEY = "dy123456";
    public static final String REST_SERVER = "http://open.deyi.com/router";//测试服务器接口
    public static final String TICKS_SERVER = "http://open.deyi.com/ticks";
    //        public static final String STATIC_SERVER = "http://static.dayez.net";
//    上传文件接口
    public static final String URL_FILE_SERVER = "http://file.dayez.net/uploader?type=1";//图片
    public static final String URL_FILE_AUDIO_SERVER = "http://file.dayez.net/uploader?type=6";//音频


    //线上接口
//    public static final String PARTNER_KEY = "A10A0D200BC949B69B0CB8A50C12303A";
//    public static final String REST_SERVER = "http://open.dayeasy.net/router";
//    public static final String TICKS_SERVER = "http://open.dayeasy.net/ticks";
//    //public static final String STATIC_SERVER = "http://static.dayeasy.net";
//    //上传文件接口
//    public static final String URL_FILE_SERVER = "http://file.dayeasy.net/uploader?type=1";
//    public static final String URL_FILE_AUDIO_SERVER = "http://file.dayeasy.net/uploader?type=6";//音频

    //API返回参数
    public static final String RESULT_STATUS = "status";
    public static final String RESULT_CODE = "code";
    public static final String RESULT_DESC = "desc";
    public static final String RESULT_MSG = "msg";
    public static final String RESULT_DATA = "data";
    public static final String RESULT_URLS = "urls";

    //API接口方法
    public static final String API_METHOD = "method";

    public static final String METHOD_MANIFEST = "system.manifest";
    public static final String METHOD_LOGIN = "user.login";
    public static final String METHOD_USER_LOAD = "user.load";
    public static final String METHOD_USER_UPDATE = "user.update";
    public static final String METHOD_TEACHER_CLASSES = "user.teacherClasses";
    public static final String METHOD_STUDENT_CLASS = "user.classes";
    public static final String METHOD_MESSAGE_SEND = "message.send";
    public static final String METHOD_MESSAGE_BATCH_SEND = "message.batchsend";
    public static final String METHOD_MESSAGE_RECEIVE = "message.receive";
    public static final String METHOD_EBOOK_HAND_IN = "ebook.handin";

    //API接口参数
    public static final String PARAM_TICK = "tick";
    public static final String PARAM_PARTNER = "partner";
    public static final String PARAM_SIGN = "sign";
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_ACCOUNT = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_OLD_PASSWORD = "old_pwd";
    public static final String PARAM_TOKEN = "token";

    //数据库
    public static final String DB_PATH = "sqlite";
    public static int oldVersion = -1;

    //消息intent参数
    public static final String MESSAGE_INTENT_QUEUE_ID = "queue_id";
    public static final String MESSAGE_INTENT_QUEUE_TYPE = "queue_type";
    public static final String MESSAGE_INTENT_SENDER_ID = "sender_id";
    public static final String MESSAGE_INTENT_SENDER_NAME = "sender_name";
    public static final String MESSAGE_INTENT_HEAD_IMG = "head_img";

    //WebView
    public static final String WEBVIEW_INTENT_TEMPLATE = "template";
    public static final String WEBVIEW_INTENT_TITLE = "title";
    public static final String WEBVIEW_INTENT_PARAMS = "web_view_params";

    public static final String PARAM_ID = "id";
    public static final String PARAM_BOOK_ID = "book_id";
    public static final String PARAM_CLASS_ID = "class_id";
    public static final String PARAM_STUDENT_ID = "student_id";
    public static final String PARAM_TEACHER_ID = "teacher_id";
    public static final String PARAM_PICTURE = "picture";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_NUMBER = "num";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_CORRECTS = "corrects";
    public static final String PARAM_BATCH = "batch";
    public static final String PARAM_PAPER_ID = "paper_id";

    //Web View Template
    public static final String TEMPLATE_URL = "file:///android_asset/www/index.html?tpl=";
    public static final String TEMPLATE_STUDENT_WORK = "student_work";
    public static final String TEMPLATE_STUDENT_WORK_DETAILS = "student_work_details";
    public static final String TEMPLATE_STUDENT_MARKING_DETAIL = "marking_detail";
    public static final String TEMPLATE_TEACHER_WORK = "teacher_work";
    public static final String TEMPLATE_STUDENT_REPORT = "student_report";
    public static final String TEMPLATE_TEACHER_REPORT = "teacher_report";
}
