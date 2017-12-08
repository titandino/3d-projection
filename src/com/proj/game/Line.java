package com.proj.game;

import com.proj.math.Vector3D;

public class Line {
	
	private Vector3D start;
	private Vector3D end;
	
	private Vector3D normal;
	
	public Line(Vector3D start, Vector3D end) {
		this.setStart(start);
		this.setEnd(end);
		this.updateNormal();
	}

	public Vector3D getStart() {
		return start;
	}

	public void setStart(Vector3D start) {
		this.start = start;
	}

	public Vector3D getEnd() {
		return end;
	}

	public void setEnd(Vector3D end) {
		this.end = end;
	}

	public Vector3D getNormal() {
		return normal;
	}
	
	public void updateNormal() {
		float dx = this.end.x-this.start.x;
		float dy = this.end.y-this.start.y;
		float dz = this.end.z-this.start.z;
		this.normal = new Vector3D(-dx, dy, dz, 0);
	}
}
