package game;

import game.data.Matrix4f;
import game.data.Vector3f;
import game.input.Keyboard;
import game.render.Bitmap;
import game.render.Loader;
import game.render.Model;
import game.render.RenderMode;
import game.render.Renderer;

public class Main
{

	private Display display;
	private Bitmap bitmap;

	private Renderer renderer;
	private Loader loader;

	private Model model;

	private Vector3f rotation;
	private float scale;
	private float rotSpeed;
	private float sclFactor;

	public Main()
	{
		display = new Display(1280, 720, "Model Viewer");
		bitmap = display.getBitmap();

		rotation = new Vector3f();
		scale = 1.0f;
		rotSpeed = 100.0f;
		sclFactor = 1.0f;

		renderer = new Renderer(bitmap);
		renderer.setRenderMode(RenderMode.WIREFRAME);
		updateTransformMatrix();

		loader = new Loader();
		model = loader.loadModelFromObj("monkey");

		long past = System.nanoTime();
		while (true)
		{
			long now = System.nanoTime();
			float dt = (float)(now - past) / 1000000000.0f;
			past = now;

			update(dt);
			render();

			display.swapBuffers();
		}
	}

	private void update(float dt)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_1)) { renderer.setRenderMode(RenderMode.WIREFRAME); }
		if (Keyboard.isKeyDown(Keyboard.KEY_2)) { renderer.setRenderMode(RenderMode.SHADED); }
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
		{
			rotation.setX(rotation.getX() + rotSpeed * dt);
			if (rotation.getY() > 360) rotation.setY(rotation.getY() - 360);
			updateTransformMatrix();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
		{
			rotation.setY(rotation.getY() + rotSpeed * dt);
			if (rotation.getZ() > 360) rotation.setZ(rotation.getZ() - 360);
			updateTransformMatrix();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
		{
			rotation.setX(rotation.getX() - rotSpeed * dt);
			if (rotation.getY() < 0) rotation.setY(rotation.getY() + 360);
			updateTransformMatrix();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
		{
			rotation.setY(rotation.getY() - rotSpeed * dt);
			if (rotation.getZ() < 0) rotation.setZ(rotation.getZ() + 360);
			updateTransformMatrix();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_EQUALS))
		{
			scale += (scale * sclFactor) * dt;
			updateTransformMatrix();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_MINUS))
		{
			scale -= (scale * sclFactor) * dt;
			if (scale < .01f) scale = .01f;
			updateTransformMatrix();
		}
	}

	private void render()
	{
		bitmap.clear(0x000000);
		renderer.render(model);
		display.swapBuffers();
	}

	private void updateTransformMatrix()
	{
		Matrix4f t = new Matrix4f().initTranslation(bitmap.getWidth()/2, bitmap.getHeight()/2, 0);
		Matrix4f r = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
		Matrix4f s = new Matrix4f().initScale(scale, scale, scale);
		renderer.setTransform(t.mul(r).mul(s));
	}

	public static void main(String[] args)
	{
		new Main();
	}

}
