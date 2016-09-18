package kiloboltgame;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

//
// MAIN APP CLASS
//
// Whenever we add a new object to our game, we have to do the following:
// 0. Create a class for it so that we have a blueprint for it. 
// 1. Within the StartingClass, we must create objects using this class and assign them values in the start() method.
// 2. Within the run() method, we must call the object's update() method.
// 3. We must paint the new object in the paint() method.
//

// create a Java based applet. The purpose of this is to make it easy to embed into HTML
public class StartingClass extends Applet implements Runnable, KeyListener
{
	private Robot robot;
	private Image image, character, characterDown, characterJumped,
			currentSprite, background;
	private Graphics second; // drawing context
	private URL base; // holds path to character asset
	private static Background bg1, bg2; // background objects

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
		} catch (Exception e)
		{
			// TODO: handle exception
		}

		// Image Setups
		character = getImage(base, "data/character.png");
		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");
		currentSprite = character;
		background = getImage(base, "data/background.png");
		// TODO: check for image load failure

	}

	@Override
	// runnable method
	public void start()
	{
		super.start();

		// create tiled backgrounds
		bg1 = new Background(0, 0);
		bg2 = new Background(Background.getBgXSize(), 0);

		// create robot
		robot = new Robot();

		Thread thread = new Thread(this);
		thread.start(); // calls our run method
	}

	@Override
	// runnable method
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
			// update objects
			robot.update();
			if (robot.isDucked() == false && robot.isJumped() == false)
			{
				currentSprite = character;
			}
			bg1.update();
			bg2.update();

			// draw objects
			repaint(); // calls paint, where we'll draw objects

			try
			{
				Thread.sleep(17); // don't go faster than 60 fps
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
		// draw background images
		g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
		g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

		// draw robot
		g.drawImage(currentSprite, // image
				robot.getCenterX() - 61, robot.getCenterY() - 63, // x, y
				this); // observer

	}

	public static Background getBg1()
	{
		return bg1;
	}

	public static void setBg1(Background bg1)
	{
		StartingClass.bg1 = bg1;
	}

	public static Background getBg2()
	{
		return bg2;
	}

	public static void setBg2(Background bg2)
	{
		StartingClass.bg2 = bg2;
	}

	@Override
	// key listener method
	public void keyPressed(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			System.out.println("Move up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = characterDown;
			if (robot.isJumped() == false)
			{
				robot.setDucked(true);
				robot.setSpeedX(0);
			}
			break;

		case KeyEvent.VK_LEFT:
			robot.moveLeft();
			robot.setMovingLeft(true);
			break;

		case KeyEvent.VK_RIGHT:
			robot.moveRight();
			robot.setMovingRight(true);
			break;

		case KeyEvent.VK_SPACE:
			currentSprite = characterJumped;
			robot.jump();
			break;

		}

	}

	@Override
	// key listener method
	public void keyReleased(KeyEvent e)
	{
		switch (e.getKeyCode())
		{
		case KeyEvent.VK_UP:
			System.out.println("Stop moving up");
			break;

		case KeyEvent.VK_DOWN:
			currentSprite = character;
			robot.setDucked(false);
			break;

		case KeyEvent.VK_LEFT:
			robot.stopLeft();
			break;

		case KeyEvent.VK_RIGHT:
			robot.stopRight();
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
