package qlsl.androiddesign.entity.commonentity;

import java.io.File;
import java.io.Serializable;

/**
 * 语音<br/>
 * 
 */
public class SpeechDetail implements Serializable {

	private static final long serialVersionUID = -7260393385215507823L;

	private String url;

	private Integer seconds;

	private File file;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String toString() {
		return "url:" + url + "  seconds:" + seconds + "  file:" + file;
	}

}
