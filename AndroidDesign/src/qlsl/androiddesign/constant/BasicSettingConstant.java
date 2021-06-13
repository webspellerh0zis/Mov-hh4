package qlsl.androiddesign.constant;

/**
 * 基本设置界面的各项初始值<br/>
 * 首次进入程序会按如下配置，之后进入按照存储文件的修改值配置<br/>
 * 首次配置，须将以下值与Preference初始值对应，否则初次进入Preference会与以下设置不匹配<br/>
 * 对应Preference文件：drawable/basic_setting.xml<br/>
 * 其它Preference同理<br/>
 */
public interface BasicSettingConstant {

	boolean showLeftMenu = true;

	boolean showRightMenu = true;

	boolean showTabTop = false;

	boolean showTabBottom = true;

	boolean showNoticeBar = true;

	boolean showActionBar = false;

	boolean screenOrientation = true;

	boolean showTitleTop = false;

	boolean showTabToast = false;

}
