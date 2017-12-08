package com.proj.game;

import com.proj.math.Vector3D;

public class Cube {
	
	public Vector3D[] vtxs = new Vector3D[8]; 
	public Vector3D pos;
	public Vector3D rotation;
	public float scale;
	public Vector3D angularVelocity;
	
	public Cube(Vector3D pos, Vector3D rotation, float scale) {
		this.pos = pos;
		this.rotation = rotation;
		this.scale = scale;
		this.angularVelocity = new Vector3D(0.01f, 0.03f, 0.3f, 0);
	}
	
	public void update(long delta) {
		this.rotation.x += angularVelocity.x*delta;
		this.rotation.y += angularVelocity.y*delta;
		this.rotation.z += angularVelocity.z*delta;
	}

}
