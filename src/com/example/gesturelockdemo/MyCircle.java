package com.example.gesturelockdemo;

public class MyCircle {

	private float centerX ;
	private float centerY ;
	private float radius ;
	private boolean touched ;
	public MyCircle(float centerX, float centerY, float radius, boolean touched) {
		super();
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.touched = touched;
	}
	public float getCenterX() {
		return centerX;
	}
	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}
	public float getCenterY() {
		return centerY;
	}
	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}
	public float getRadius() {
		return radius;
	}
	public void setRadius(float radius) {
		this.radius = radius;
	}
	public boolean isTouched() {
		return touched;
	}
	public void setTouched(boolean touched) {
		this.touched = touched;
	}
	@Override
	public String toString() {
		return "MyCircle [centerX=" + centerX + ", centerY=" + centerY
				+ ", radius=" + radius + ", touched=" + touched + "]";
	}
	
}
