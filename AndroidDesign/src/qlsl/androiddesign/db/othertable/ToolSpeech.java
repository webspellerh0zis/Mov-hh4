package qlsl.androiddesign.db.othertable;

import com.j256.ormlite.field.DatabaseField;

public class ToolSpeech {

	public ToolSpeech() {

	}

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField
	private Boolean isAutoPlay;

	@DatabaseField
	private Boolean isClickPlay;

	@DatabaseField
	private Integer speaker;

	@DatabaseField
	private Integer stream;

	@DatabaseField
	private Integer speed;

	@DatabaseField
	private Integer pitch;

	@DatabaseField
	private Integer volume;

	public Boolean getIsAutoPlay() {
		return isAutoPlay;
	}

	public void setIsAutoPlay(Boolean isAutoPlay) {
		this.isAutoPlay = isAutoPlay;
	}

	public Boolean getIsClickPlay() {
		return isClickPlay;
	}

	public void setIsClickPlay(Boolean isClickPlay) {
		this.isClickPlay = isClickPlay;
	}

	public Integer getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Integer speaker) {
		this.speaker = speaker;
	}

	public Integer getStream() {
		return stream;
	}

	public void setStream(Integer stream) {
		this.stream = stream;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getPitch() {
		return pitch;
	}

	public void setPitch(Integer pitch) {
		this.pitch = pitch;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

}
