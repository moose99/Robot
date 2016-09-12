package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

// create a Java based applet. The purpose of this is to make it easy to embed into HTML
public class StartingClass extends Applet implements Runnable, KeyListener
{
	private Robot robot;
	private Image image, character;
	private Graphics second;	// back buffer
	private URL base;			// holds path to characetr asset
	
	// set size, background, title
	@Override
	public void init()
	{
		super.init();

		// set up window
		setSize(800, 480);
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this); // set ourselves as the key listener
		Frame frame = (Frame) this.getParent().getParent();
		frame.setTitle("Q-Bot Alpha");
		
		try 
		{
			base = getDocumentBase();
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		// Image Setups
		character = getImage(base, "data/character.png");
		if (character.getHeight(this) == -1)
		{	
			System.out.println("Image load fail");
		}
	}

	@Override
	public void start()
	{
		super.start();

		robot = new Robot();
		
		Thread thread = new Thread(this);
		thread.start(); // calls our run method
	}

	@Override
	public void stop()
	{
		// TODO Auto-generated method stub
		super.stop();
	}

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		super.destroy();
	}

	//
	// thread run method
	// MAIN LOOP
	//
	@Override
	public void run()
	{
		// main game loop
		while (true)
		{
			repaint(); // calls paint, where we'll draw objects
			try
			{
				Thread.sleep(17);
			} catch (InterruptedException e)
			{ // in case sleep fails
				e.printStackTrace();
			}
		}
	}

	//
	// Displays our image
	//
	public void update(Graphics g)
	{
		if (image == null) 
		{
			image = createImage(this.getWidth(), this.getHeight());
			second = image.getGraphics();
		}

		second.setColor(getBackground());
		second.fillRect(0, 0, getWidth(), getHeight());
		second.setColor(getForeground());
		paint(second);

		g.drawImage(image, 0, 0, this);
	}
	
	//
	// Draws our graphics
	//
	public void paint(Graphics g) 	
	{
		g.drawImage(character,	// image 
				robot.getCenterX() - 61, robot.getCenterY() - 63, 	// x, y
				this);			// observer

	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			break;

		case KeyEvent.VK_LEFT:
			break;

		case KeyEvent.VK_RIGHT:
			break;

		case KeyEvent.VK_SPACE:
			break;
		}

	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			break;

		case KeyEvent.VK_DOWN:
			break;

		case KeyEvent.VK_LEFT:
			break;

		case KeyEvent.VK_RIGHT:
			break;
			
		case KeyEvent.VK_SPACE:
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}
