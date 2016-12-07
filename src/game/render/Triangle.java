package game.render;

import game.data.Vector3f;

public class Triangle
{

	public Vector3f v0, v1, v2;
	public Vector3f n0, n1, n2;

	public Triangle()
	{
		this(new Vector3f(), new Vector3f(), new Vector3f());
	}

	public Triangle(Vector3f v0, Vector3f v1, Vector3f v2)
	{
		this(v0, v1, v2, new Vector3f(), new Vector3f(), new Vector3f());
	}
	
	public Triangle(Vector3f v0, Vector3f v1, Vector3f v2, Vector3f n0, Vector3f n1, Vector3f n2)
	{
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.n0 = n0;
		this.n1 = n1;
		this.n2 = n2;
	}
	
}
