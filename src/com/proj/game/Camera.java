package com.proj.game;

import com.proj.Game;
import com.proj.math.Vector3D;

public class Camera {
	
	public Vector3D position;
	public Vector3D rotation;
	
	public float fieldOfView;
	public float near;
	public float far;
	public float aspect;
	
	public Camera(Vector3D position, Vector3D rotation) {
		this.position = position;
		this.rotation = rotation;
		this.fieldOfView = (float) (Math.PI/4);
		this.near = 0.1f;
		this.far = 2000.0f;
		this.aspect = Game.dimensionX/Game.dimensionY;
	}
}
