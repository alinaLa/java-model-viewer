package game.data;

public class Vector3f
{

	private float x;
	private float y;
	private float z;

	public float getY() { return y; }
	public float getX() { return x; }
	public float getZ() { return z; }

	public void setY(float y) { this.y = y; }
	public void setX(float x) { this.x = x; }
	public void setZ(float z) { this.z = z; }

	public Vector3f()
	{
		this(0, 0, 0);
	}

	public Vector3f(float x, float y, float z)
	{
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

	public Vector3f(Vector3f xyz)
	{
		this.setX(xyz.getX());
		this.setY(xyz.getY());
		this.setZ(xyz.getZ());
	}

	public Vector3f(Vector4f xyzw)
	{
		this.setX(xyzw.getX());
		this.setY(xyzw.getY());
		this.setZ(xyzw.getZ());
	}

	public float length()
	{
		return (float)Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
	}

	public Vector3f normalized()
	{
		float l = length();
		return new Vector3f(getX()/l, getY()/l, getZ()/l);
	}

	public Vector3f cross(Vector3f v)
	{
		return new Vector3f(getY()*v.getZ() - getZ()*v.getY(), getZ()*v.getX()-getX()*v.getZ(), getX()*v.getY()-getY()*v.getX());
	}

	public float dot(Vector3f v)
	{
		return getX() * v.getX() + getY() * v.getY() + getZ() * v.getZ();
	}

	public Vector3f sub(Vector3f v)
	{
		return new Vector3f(getX() - v.getX(), getY() - v.getY(), getZ() - v.getZ());
	}

}
