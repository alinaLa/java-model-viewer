package game.render;

import java.awt.Color;

import game.data.Matrix4f;
import game.data.Vector3f;
import game.data.Vector4f;
import game.tool.ArrayHelper;

public class Renderer
{

	private RenderMode renderMode;
	private Matrix4f transform;

	private Bitmap b;
	private float[] zBuffer;

	private Vector3f light;

	public Renderer(Bitmap b)
	{
		this.b = b;
		zBuffer = new float[b.getWidth() * b.getHeight()];
		
		light = new Vector3f(0, 0, 1);
	}

	public void render(Model model)
	{
		if (renderMode == RenderMode.WIREFRAME) renderWireframe(model);
		else if (renderMode == RenderMode.SHADED) renderShaded(model);
	}

	private void renderWireframe(Model model)
	{
		for (Triangle t : model.getTriangles())
		{
			Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
			Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
			Vector3f v2 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));
			
			b.drawLine(v0.getX(), v0.getY(), v1.getX(), v1.getY(), 0xFFFFFF);
			b.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY(), 0xFFFFFF);
			b.drawLine(v2.getX(), v2.getY(), v0.getX(), v0.getY(), 0xFFFFFF);
		}
	}

	private void renderShaded(Model model)
	{
		ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

		for (Triangle t : model.getTriangles())
		{
			Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
			Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
			Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

			Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
			float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

			int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
			int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
			int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
			int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

			float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

			for (int y = minY; y <= maxY; y++)
			{
				if (y < 0 || y >= b.getHeight()) continue;
				int yIndex = y * b.getWidth();
				for (int x = minX; x <= maxX; x++)
				{
					if (x < 0 || x >= b.getWidth()) continue;
					int index = yIndex + x;
					float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
					float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
					float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
					if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
					{
						float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
						if (zBuffer[index] < depth)
						{
							b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
							zBuffer[index] = depth;
						}
					}
				}
			}
		}
	}

	private Color getShade(Color color, float shade)
	{
		int red = (int) ((float)color.getRed() * shade);
		int green = (int) ((float)color.getGreen() * shade);
		int blue = (int) ((float)color.getBlue() * shade);
		return new Color(red, green, blue);
	}

	public void setTransform(Matrix4f transform)
	{
		this.transform = transform;
	}

	public void setRenderMode(RenderMode renderMode)
	{
		this.renderMode = renderMode;
	}

	public RenderMode getRenderMode()
	{
		return renderMode;
	}

}
