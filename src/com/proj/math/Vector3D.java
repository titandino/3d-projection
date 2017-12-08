package com.proj.math;

import com.proj.util.Util;

public class Vector3D {
	public float x;
	public float y;
	public float z;
	public float w;
	
	public Vector3D() {
		this(0.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public Vector3D(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector3D multiply(Vector3D v2) {
		this.x = this.x*v2.x;
		this.y = this.y*v2.y;
		this.z = this.z*v2.z;
		return this;
	}
	
	public Vector3D subtract(Vector3D v2) {
		this.x = this.x-v2.x;
		this.y = this.y-v2.y;
		this.z = this.z-v2.z;
		return this;
	}
	
	@Override
	public String toString() {
		return "Vector3D: {"+Util.fmt(this.x)+", "+Util.fmt(this.y)+", "+Util.fmt(this.z)+"}";
	}

}
