package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import game.input.Keyboard;
import game.render.Bitmap;

public class Display extends Canvas
{

	private JFrame frame;

	private BufferedImage image;
	private int[] pixels;

	private BufferStrategy bufferStrategy;
	private Graphics g;

	private Bitmap bitmap;

	public Bitmap getBitmap() { return bitmap; }

	public Display(int width, int height, String title)
	{
		Dimension size = new Dimension(width, height);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		addKeyListener(new Keyboard());
		setFocusable(true);
		requestFocus();
		
		bitmap = new Bitmap(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(3);
		frame.setResizable(false);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		createBufferStrategy(1);
		bufferStrategy = getBufferStrategy();
		g = bufferStrategy.getDrawGraphics();
	}

	public void swapBuffers()
	{
		bitmap.copyToIntArray(pixels);
		g.drawImage(image, 0, 0, null);
		bufferStrategy.show();
	}

}
