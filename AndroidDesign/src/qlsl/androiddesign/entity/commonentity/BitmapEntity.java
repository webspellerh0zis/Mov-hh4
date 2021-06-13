package qlsl.androiddesign.entity.commonentity;

public class BitmapEntity {

	private float x;

	private float y;

	private float width;

	private float height;

	private int devide;

	private int index;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public int getDevide() {
		return devide;
	}

	public void setDevide(int devide) {
		this.devide = devide;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String toString() {
		return "BitmapEntity [x=" + x + ", y=" + y + ", width=" + width
				+ ", height=" + height + ", devide=" + devide + ", index="
				+ index + "]";
	}
}
