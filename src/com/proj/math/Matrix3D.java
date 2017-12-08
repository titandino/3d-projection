package com.proj.math;

public class Matrix3D {
	
	private float[] matrix = new float[16];
	
	public Matrix3D() {
		this.matrix = new float[16];
		this.buildIdentityMtx();
	}
	
	public void print() {
		System.out.println("Matrix3D:");
		for(int i = 0;i < matrix.length;i++) {
			if ((i % 4) == 0)
				System.out.println();
			System.out.print(matrix[i]+"|");
		}
		System.out.println();
	}
	
	public Matrix3D buildIdentityMtx() {
		this.matrix = new float[16];
		this.matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1;
		return this;
	}
	
	public Matrix3D buildProjectionMtx(float fov, float aspect, float n, float f) {
		float tanHalfFOV = (float) Math.tan(fov / 2);
		float zRange = n - f;
		
		matrix = new float[16];

		matrix[0] = 1.0f / (tanHalfFOV * aspect);
		matrix[5] = 1.0f / tanHalfFOV;
		matrix[10] = (-n - f) / zRange;
		matrix[14] = 2 * f * n / zRange;
		matrix[11] = 1;
		
		return this;
	}

	public Matrix3D buildMappingMtx(Vector3D x, Vector3D y, Vector3D z) {
		matrix = new float[16];
		
		matrix[0] = x.x;
		matrix[1] = x.y;
		matrix[2] = x.z;
		matrix[3] = x.w;
		matrix[4] = y.x;
		matrix[5] = y.y;
		matrix[6] = y.z;
		matrix[7] = y.w;
		matrix[8] = z.x;
		matrix[9] = z.y;
		matrix[10] = z.z;
		matrix[11] = z.w;
		matrix[12] = 0;
		matrix[13] = 0;
		matrix[14] = 0;
		matrix[15] = 1;
		return this;
	}

	public Matrix3D buildTranslationMtx(float tX, float tY, float tZ) {
		this.matrix = new float[16];
		matrix[0] = matrix[5] = matrix[10] = matrix[15] = 1;

		matrix[3] = tX;
		matrix[7] = tY;
		matrix[11] = tZ;
		return this;
	}

	public Matrix3D buildScaleMtx(float sX, float sY, float sZ) {
		this.matrix = new float[16];
		matrix[15] = 1;

		matrix[0] = sX;
		matrix[5] = sY;
		matrix[10] = sZ;
		return this;
	}

	public Matrix3D buildRotationXMtx(float rX) {
		float sine;
		this.matrix = new float[16];
		matrix[0] = matrix[15] = 1;

		matrix[5] = matrix[10] = (float) Math.cos(rX*(Math.PI/180));

		sine = (float) Math.sin(rX*(Math.PI/180));
		matrix[9] = sine;
		matrix[6] = -sine;
		return this;
	}

	public Matrix3D buildRotationYMtx(float rY) {
		float sine;
		this.matrix = new float[16];
		matrix[5] = matrix[15] = 1;

		matrix[0] = matrix[10] = (float) Math.cos(rY*(Math.PI/180));
		
		sine = (float) Math.sin(rY*(Math.PI/180));
		matrix[2] = sine;
		matrix[8] = -sine;
		return this;
	}

	public Matrix3D buildRotationZMtx(float rZ) {
		float sine;
		this.matrix = new float[16];
		matrix[10] = matrix[15] = 1;

		matrix[0] = matrix[5] = (float) Math.cos(rZ*(Math.PI/180));
		
		sine = (float) Math.sin(rZ*(Math.PI/180));
		matrix[4] = sine;
		matrix[1] = -sine;
		return this;
	}

	public static Matrix3D concatMtx(Matrix3D mtx1, Matrix3D mtx2) {
		Matrix3D temp = new Matrix3D();

		temp.matrix[0] = mtx1.matrix[0]*mtx2.matrix[0]+mtx1.matrix[1]*mtx2.matrix[4]+mtx1.matrix[2]*mtx2.matrix[8]+mtx1.matrix[3]*mtx2.matrix[12];
		temp.matrix[4] = mtx1.matrix[4]*mtx2.matrix[0]+mtx1.matrix[5]*mtx2.matrix[4]+mtx1.matrix[6]*mtx2.matrix[8]+mtx1.matrix[7]*mtx2.matrix[12];
		temp.matrix[8] = mtx1.matrix[8]*mtx2.matrix[0]+mtx1.matrix[9]*mtx2.matrix[4]+mtx1.matrix[10]*mtx2.matrix[8]+mtx1.matrix[11]*mtx2.matrix[12];
		temp.matrix[12] = mtx1.matrix[12]*mtx2.matrix[0]+mtx1.matrix[13]*mtx2.matrix[4]+mtx1.matrix[14]*mtx2.matrix[8]+mtx1.matrix[15]*mtx2.matrix[12];

		temp.matrix[1] = mtx1.matrix[0]*mtx2.matrix[1]+mtx1.matrix[1]*mtx2.matrix[5]+mtx1.matrix[2]*mtx2.matrix[9]+mtx1.matrix[3]*mtx2.matrix[13];
		temp.matrix[5] = mtx1.matrix[4]*mtx2.matrix[1]+mtx1.matrix[5]*mtx2.matrix[5]+mtx1.matrix[6]*mtx2.matrix[9]+mtx1.matrix[7]*mtx2.matrix[13];
		temp.matrix[9] = mtx1.matrix[8]*mtx2.matrix[1]+mtx1.matrix[9]*mtx2.matrix[5]+mtx1.matrix[10]*mtx2.matrix[9]+mtx1.matrix[11]*mtx2.matrix[13];
		temp.matrix[13] = mtx1.matrix[12]*mtx2.matrix[1]+mtx1.matrix[13]*mtx2.matrix[5]+mtx1.matrix[14]*mtx2.matrix[9]+mtx1.matrix[15]*mtx2.matrix[13];

		temp.matrix[2] = mtx1.matrix[0]*mtx2.matrix[2]+mtx1.matrix[1]*mtx2.matrix[6]+mtx1.matrix[2]*mtx2.matrix[10]+mtx1.matrix[3]*mtx2.matrix[14];
		temp.matrix[6] = mtx1.matrix[4]*mtx2.matrix[2]+mtx1.matrix[5]*mtx2.matrix[6]+mtx1.matrix[6]*mtx2.matrix[10]+mtx1.matrix[7]*mtx2.matrix[14];
		temp.matrix[10] = mtx1.matrix[8]*mtx2.matrix[2]+mtx1.matrix[9]*mtx2.matrix[6]+mtx1.matrix[10]*mtx2.matrix[10]+mtx1.matrix[11]*mtx2.matrix[14];
		temp.matrix[14] = mtx1.matrix[12]*mtx2.matrix[2]+mtx1.matrix[13]*mtx2.matrix[6]+mtx1.matrix[14]*mtx2.matrix[10]+mtx1.matrix[15]*mtx2.matrix[14];

		temp.matrix[3] = mtx1.matrix[0]*mtx2.matrix[3]+mtx1.matrix[1]*mtx2.matrix[7]+mtx1.matrix[2]*mtx2.matrix[11]+mtx1.matrix[3]*mtx2.matrix[15];
		temp.matrix[7] = mtx1.matrix[4]*mtx2.matrix[3]+mtx1.matrix[5]*mtx2.matrix[7]+mtx1.matrix[6]*mtx2.matrix[11]+mtx1.matrix[7]*mtx2.matrix[15];
		temp.matrix[11] = mtx1.matrix[8]*mtx2.matrix[3]+mtx1.matrix[9]*mtx2.matrix[7]+mtx1.matrix[10]*mtx2.matrix[11]+mtx1.matrix[11]*mtx2.matrix[15];
		temp.matrix[15] = mtx1.matrix[12]*mtx2.matrix[3]+mtx1.matrix[13]*mtx2.matrix[7]+mtx1.matrix[14]*mtx2.matrix[11]+mtx1.matrix[15]*mtx2.matrix[15];

		return temp;
	}
	
	public Matrix3D concat(Matrix3D mtx2) {
		Matrix3D temp = new Matrix3D();

		temp.matrix[0] = this.matrix[0]*mtx2.matrix[0]+this.matrix[1]*mtx2.matrix[4]+this.matrix[2]*mtx2.matrix[8]+this.matrix[3]*mtx2.matrix[12];
		temp.matrix[4] = this.matrix[4]*mtx2.matrix[0]+this.matrix[5]*mtx2.matrix[4]+this.matrix[6]*mtx2.matrix[8]+this.matrix[7]*mtx2.matrix[12];
		temp.matrix[8] = this.matrix[8]*mtx2.matrix[0]+this.matrix[9]*mtx2.matrix[4]+this.matrix[10]*mtx2.matrix[8]+this.matrix[11]*mtx2.matrix[12];
		temp.matrix[12] = this.matrix[12]*mtx2.matrix[0]+this.matrix[13]*mtx2.matrix[4]+this.matrix[14]*mtx2.matrix[8]+this.matrix[15]*mtx2.matrix[12];

		temp.matrix[1] = this.matrix[0]*mtx2.matrix[1]+this.matrix[1]*mtx2.matrix[5]+this.matrix[2]*mtx2.matrix[9]+this.matrix[3]*mtx2.matrix[13];
		temp.matrix[5] = this.matrix[4]*mtx2.matrix[1]+this.matrix[5]*mtx2.matrix[5]+this.matrix[6]*mtx2.matrix[9]+this.matrix[7]*mtx2.matrix[13];
		temp.matrix[9] = this.matrix[8]*mtx2.matrix[1]+this.matrix[9]*mtx2.matrix[5]+this.matrix[10]*mtx2.matrix[9]+this.matrix[11]*mtx2.matrix[13];
		temp.matrix[13] = this.matrix[12]*mtx2.matrix[1]+this.matrix[13]*mtx2.matrix[5]+this.matrix[14]*mtx2.matrix[9]+this.matrix[15]*mtx2.matrix[13];

		temp.matrix[2] = this.matrix[0]*mtx2.matrix[2]+this.matrix[1]*mtx2.matrix[6]+this.matrix[2]*mtx2.matrix[10]+this.matrix[3]*mtx2.matrix[14];
		temp.matrix[6] = this.matrix[4]*mtx2.matrix[2]+this.matrix[5]*mtx2.matrix[6]+this.matrix[6]*mtx2.matrix[10]+this.matrix[7]*mtx2.matrix[14];
		temp.matrix[10] = this.matrix[8]*mtx2.matrix[2]+this.matrix[9]*mtx2.matrix[6]+this.matrix[10]*mtx2.matrix[10]+this.matrix[11]*mtx2.matrix[14];
		temp.matrix[14] = this.matrix[12]*mtx2.matrix[2]+this.matrix[13]*mtx2.matrix[6]+this.matrix[14]*mtx2.matrix[10]+this.matrix[15]*mtx2.matrix[14];

		temp.matrix[3] = this.matrix[0]*mtx2.matrix[3]+this.matrix[1]*mtx2.matrix[7]+this.matrix[2]*mtx2.matrix[11]+this.matrix[3]*mtx2.matrix[15];
		temp.matrix[7] = this.matrix[4]*mtx2.matrix[3]+this.matrix[5]*mtx2.matrix[7]+this.matrix[6]*mtx2.matrix[11]+this.matrix[7]*mtx2.matrix[15];
		temp.matrix[11] = this.matrix[8]*mtx2.matrix[3]+this.matrix[9]*mtx2.matrix[7]+this.matrix[10]*mtx2.matrix[11]+this.matrix[11]*mtx2.matrix[15];
		temp.matrix[15] = this.matrix[12]*mtx2.matrix[3]+this.matrix[13]*mtx2.matrix[7]+this.matrix[14]*mtx2.matrix[11]+this.matrix[15]*mtx2.matrix[15];

		for (int i = 0;i < matrix.length;i++) {
			this.matrix[i] = temp.matrix[i];
		}
		
		return this;
	}

	public static Vector3D concatMtxWithVec(Matrix3D mtx, Vector3D v) {
		Vector3D temp = new Vector3D();
		
		temp.x = mtx.matrix[0]*v.x + mtx.matrix[1]*v.y + mtx.matrix[2]*v.z + mtx.matrix[3]*v.w;
		temp.y = mtx.matrix[4]*v.x + mtx.matrix[5]*v.y + mtx.matrix[6]*v.z + mtx.matrix[7]*v.w;
		temp.z = mtx.matrix[8]*v.x + mtx.matrix[9]*v.y + mtx.matrix[10]*v.z + mtx.matrix[11]*v.w;
		temp.w = mtx.matrix[12]*v.x + mtx.matrix[13]*v.y + mtx.matrix[14]*v.z + mtx.matrix[15]*v.w;
		
		return temp;
	}
}
