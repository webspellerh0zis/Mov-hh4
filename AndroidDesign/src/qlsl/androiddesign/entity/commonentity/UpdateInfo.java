package qlsl.androiddesign.entity.commonentity;


/**
 * 更新信息<br/>
 * 
 */
public class UpdateInfo {

	private Integer versionCode;

	private String version;

	private String upgradeInstructions;

	private String downloadUrl;

	private String apkSize;

	private Boolean mandatory;

	private String md5;

	private String upgradeDate;

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpgradeInstructions() {
		return upgradeInstructions;
	}

	public void setUpgradeInstructions(String upgradeInstructions) {
		this.upgradeInstructions = upgradeInstructions;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getApkSize() {
		return apkSize;
	}

	public void setApkSize(String apkSize) {
		this.apkSize = apkSize;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getUpgradeDate() {
		return upgradeDate;
	}

	public void setUpgradeDate(String upgradeDate) {
		this.upgradeDate = upgradeDate;
	}

	public String toString() {
		return "version=" + version + "  versionCode=" + versionCode + "  upgradeDate=" + upgradeDate + "  downloadUrl="
				+ downloadUrl;
	}

}
