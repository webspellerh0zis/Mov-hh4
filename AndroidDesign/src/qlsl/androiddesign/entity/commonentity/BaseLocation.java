package qlsl.androiddesign.entity.commonentity;

import java.io.Serializable;
import java.text.DecimalFormat;

import com.j256.ormlite.field.DatabaseField;

public class BaseLocation implements Serializable {

	private static final long serialVersionUID = 4999040518516266398L;

	public BaseLocation() {

	}

	@DatabaseField
	private Double latitude;

	@DatabaseField
	private Double latitudeBD;

	@DatabaseField
	private Double latitudeGCJ;

	@DatabaseField
	private Double longitude;

	@DatabaseField
	private Double longitudeBD;

	@DatabaseField
	private Double longitudeGCJ;

	@DatabaseField
	private Double distance;

	private static DecimalFormat decimalFormat = new DecimalFormat("#.000");

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLatitudeBD() {
		return latitudeBD;
	}

	public void setLatitudeBD(Double latitudeBD) {
		this.latitudeBD = latitudeBD;
	}

	public Double getLatitudeGCJ() {
		return latitudeGCJ;
	}

	public void setLatitudeGCJ(Double latitudeGCJ) {
		this.latitudeGCJ = latitudeGCJ;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLongitudeBD() {
		return longitudeBD;
	}

	public void setLongitudeBD(Double longitudeBD) {
		this.longitudeBD = longitudeBD;
	}

	public Double getLongitudeGCJ() {
		return longitudeGCJ;
	}

	public void setLongitudeGCJ(Double longitudeGCJ) {
		this.longitudeGCJ = longitudeGCJ;
	}

	public String getDistance() {
		if (distance != null) {
			return decimalFormat.format(distance) + "km";
		} else {
			return "正在获取距离";
		}
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String toString() {
		return getClass().getSimpleName() + ":  latitude=" + latitude
				+ "  longitude:" + longitude + "  distance:" + distance;
	}

}
