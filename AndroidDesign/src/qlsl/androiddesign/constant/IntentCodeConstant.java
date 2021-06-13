package qlsl.androiddesign.constant;

/**
 * 
 * 作用：设置Intent的状态码<br>
 * 请求码：1001~1999<br>
 * 结果码：2001~2999
 * 
 */
public interface IntentCodeConstant {

	// ------------分界线------------

	int REQUEST_CODE_MAIN = 1001;

	int RESULT_CODE_SETTING = 2001;

	// ------------分界线------------

	int REQUEST_CODE_PHOTO_TAKE = 1002;

	int REQUEST_CODE_PHOTO_PICKED = 1003;

	// ------------分界线------------

	int SELECT_PHOTO = 1004;

	int SELECET_PHOTO_AFTER_KIKAT = 1005;

	int SET_ALBUM_PHOTO_KITKAT = 1006;

	int TAKE_PHOTO = 1007;

	int CROP_PHOTO = 1008;

	// ------------分界线------------

	int REQUEST_CODE_GROUP_REQUEST = 1009;

}
