package game.data;

public class Vector4f
{

	private float x;
	private float y;
	private float z;
	private float w;

	public float getX() { return x; }
	public float getY() { return y; }
	public float getZ() { return z; }
	public float getW() { return w; }

	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setZ(float z) { this.z = z; }
	public void setW(float w) { this.w = w; }

	public Vector4f()
	{
		this(0, 0, 0, 0);
	}

	public Vector4f(float x, float y, float z, float w)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
		this.setW(w);
	}

	public Vector4f(Vector3f xyz, float w)
	{
		this.setX(xyz.getX());
		this.setY(xyz.getY());
		this.setZ(xyz.getZ());
		this.setW(w);
	}

}
