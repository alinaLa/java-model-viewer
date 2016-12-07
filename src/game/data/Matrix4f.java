package game.data;

public class Matrix4f
{

	private float[][] m;

	public Matrix4f()
	{
		m = new float[4][4];
	}

	public Matrix4f(float[][] m)
	{
		this.m = m.clone();
	}

	public Matrix4f mul(Matrix4f other)
	{
		float[][] result = new float[4][4];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 4; k++)
					result[i][j] += this.m[i][k] * other.m[k][j];
		return new Matrix4f(result);
	}

	public Vector4f transform(Vector4f v)
	{
		return new Vector4f(
				m[0][0] * v.getX() + m[0][1] * v.getY() + m[0][2] * v.getZ() + m[0][3] * v.getW(),
				m[1][0] * v.getX() + m[1][1] * v.getY() + m[1][2] * v.getZ() + m[1][3] * v.getW(),
				m[2][0] * v.getX() + m[2][1] * v.getY() + m[2][2] * v.getZ() + m[2][3] * v.getW(),
				m[3][0] * v.getX() + m[3][1] * v.getY() + m[3][2] * v.getZ() + m[3][3] * v.getW()
		);
	}

	public float get(int i)
	{
		return m[i / 4][i % 4];
	}

	public void set(int i, float f)
	{
		m[i / 4][i % 4] = f;
	}

	public float get(int x, int y)
	{
		return m[x][y];
	}

	public void set(int x, int y, float f)
	{
		m[x][y] = f;
	}

	public Matrix4f initIndentity()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		return this;
	}

	public Matrix4f initTranslation(float x, float y, float z)
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		return this;
	}

	public Matrix4f initRotation(float x, float y, float z)
	{
		double xx = Math.toRadians(x);
		double yy = Math.toRadians(y);
		double zz = Math.toRadians(z);
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		rz.m[0][0] = (float)Math.cos(zz);	rz.m[0][1] = -(float)Math.sin(zz);	rz.m[0][2] = 0;						rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(zz);	rz.m[1][1] = (float)Math.cos(zz);	rz.m[1][2] = 0;						rz.m[1][3] = 0;
		rz.m[2][0] = 0;						rz.m[2][1] = 0;						rz.m[2][2] = 1;						rz.m[2][3] = 0;
		rz.m[3][0] = 0;						rz.m[3][1] = 0;						rz.m[3][2] = 0;						rz.m[3][3] = 1;

		rx.m[0][0] = 1;						rx.m[0][1] = 0;						rx.m[0][2] = 0;						rx.m[0][3] = 0;
		rx.m[1][0] = 0;						rx.m[1][1] = (float)Math.cos(xx);	rx.m[1][2] = -(float)Math.sin(xx);	rx.m[1][3] = 0;
		rx.m[2][0] = 0;						rx.m[2][1] = (float)Math.sin(xx);	rx.m[2][2] = (float)Math.cos(xx);	rx.m[2][3] = 0;
		rx.m[3][0] = 0;						rx.m[3][1] = 0;						rx.m[3][2] = 0;						rx.m[3][3] = 1;

		ry.m[0][0] = (float)Math.cos(yy);	ry.m[0][1] = 0;						ry.m[0][2] = -(float)Math.sin(yy);	ry.m[0][3] = 0;
		ry.m[1][0] = 0;						ry.m[1][1] = 1;						ry.m[1][2] = 0;						ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(yy);	ry.m[2][1] = 0;						ry.m[2][2] = (float)Math.cos(yy);	ry.m[2][3] = 0;
		ry.m[3][0] = 0;						ry.m[3][1] = 0;						ry.m[3][2] = 0;						ry.m[3][3] = 1;

		m = rz.mul(ry.mul(rx)).m.clone();
		return this;
	}

	public Matrix4f initScale(float x, float y, float z)
	{
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;
		return this;
	}

}
