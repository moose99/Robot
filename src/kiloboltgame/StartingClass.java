package kiloboltgame;

import kiloboltgame.framework.Animation;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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
	enum GameState
	{
		Running, Dead
	}

	GameState state = GameState.Running;
	private static Robot robot;
	public static Heliboy hb, hb2;
	private Image image, currentSprite, character, character2, character3,
			characterDown, characterJumped, background, heliboy, heliboy2,
			heliboy3, heliboy4, heliboy5;
	public static Image tilegrassTop, tilegrassBot, tilegrassLeft,
			tilegrassRight, tiledirt;
	private Graphics second; // drawing context
	private URL base; // holds path to character asset
	private static Background bg1, bg2; // background objects
	private Animation anim, hanim; // Animation sequences for character and
									// heliboy
	private ArrayList<Tile> tilearray = new ArrayList<Tile>();

	public static int score = 0;
	private Font font = new Font(null, Font.BOLD, 30);

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
		character2 = getImage(base, "data/character2.png");
		character3 = getImage(base, "data/character3.png");

		characterDown = getImage(base, "data/down.png");
		characterJumped = getImage(base, "data/jumped.png");

		heliboy = getImage(base, "data/heliboy.png");
		heliboy2 = getImage(base, "data/heliboy2.png");
		heliboy3 = getImage(base, "data/heliboy3.png");
		heliboy4 = getImage(base, "data/heliboy4.png");
		heliboy5 = getImage(base, "data/heliboy5.png");

		background = getImage(base, "data/background.png");

		tiledirt = getImage(base, "data/tiledirt.png");
		tilegrassTop = getImage(base, "data/tilegrasstop.png");
		tilegrassBot = getImage(base, "data/tilegrassbot.png");
		tilegrassLeft = getImage(base, "data/tilegrassleft.png");
		tilegrassRight = getImage(base, "data/tilegrassright.png");

		// Animation setup
		anim = new Animation();
		anim.addFrame(character, 1250);
		anim.addFrame(character2, 50);
		anim.addFrame(character3, 50);
		anim.addFrame(character2, 50);

		hanim = new Animation();
		hanim.addFrame(heliboy, 100);
		hanim.addFrame(heliboy2, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy5, 100);
		hanim.addFrame(heliboy4, 100);
		hanim.addFrame(heliboy3, 100);
		hanim.addFrame(heliboy2, 100);

		currentSprite = anim.getImage();
	}

	private void initTiles()
	{
		// Initialize Tiles
		try
		{
			loadMap("data/map1.txt");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	//
	// read map text file and create tiles
	//
	private void loadMap(String filename) throws IOException
	{
		ArrayList<String> lines = new ArrayList<String>();
		int width = 0;
		int height = 0;

		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while (true)
		{
			String line = reader.readLine();
			if (line == null)
			{ // no more lines to read
				reader.close();
				break;
			}

			if (!line.startsWith("!"))
			{ // ignore lines which start with !, indicating comment
				lines.add(line);
				width = Math.max(width, line.length());
			}
		}
		height = lines.size(); // = 13

		for (int j = 0; j < 12; j++)
		{
			String line = (String) lines.get(j);
			for (int i = 0; i < width; i++)
			{
				System.out.println(i + " is i ");

				if (i < line.length())
				{
					char ch = line.charAt(i);
					Tile t = new Tile(i, j, Character.getNumericValue(ch));
					tilearray.add(t);
				}
			}
		}
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

		initTiles();

		// create heliboy enemies
		hb = new Heliboy(340, 360);
		hb2 = new Heliboy(700, 360);

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
		if (state == GameState.Running)
		{
			while (true)
			{
				// update objects
				robot.update();
				if (robot.isDucked() == false && robot.isJumped() == false)
				{
					currentSprite = anim.getImage(); // get current image from
														// from
														// character animation
				}

				updateProjectiles();
				updateTiles();
				hb.update();
				hb2.update();

				bg1.update();
				bg2.update();

				animate();

				// draw objects
				repaint(); // calls paint, where we'll draw objects

				try
				{
					Thread.sleep(17); // don't go faster than 60 fps
				} catch (InterruptedException e)
				{ // in case sleep fails
					e.printStackTrace();
				}

				// fell off the world
				if (robot.getCenterY() > 500)
				{
					state = GameState.Dead;
				}

			}
		}
	}

	// update Animations
	public void animate()
	{
		anim.update(10);
		hanim.update(50);
	}

	// update all projectiles
	// TODO - move this into robot class?
	private void updateProjectiles()
	{
		// update projectiles
		ArrayList<Projectile> projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++)
		{
			Projectile p = projectiles.get(i);
			if (p.isVisible() == true)
				p.update();
			else
				projectiles.remove(i);
		}
	}

	//
	// paint all projectiles
	//
	private void paintProjectiles(Graphics g)
	{
		ArrayList<Projectile> projectiles = robot.getProjectiles();
		for (int i = 0; i < projectiles.size(); i++)
		{
			Projectile p = projectiles.get(i);
			p.draw(g);
		}
	}

	private void updateTiles()
	{
		for (int i = 0; i < tilearray.size(); i++)
		{
			Tile t = (Tile) tilearray.get(i);
			t.update();
		}

	}

	private void paintTiles(Graphics g)
	{
		for (int i = 0; i < tilearray.size(); i++)
		{
			Tile t = (Tile) tilearray.get(i);
			t.draw(g, this);
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
		if (state == GameState.Running)
		{

			// draw background images
			g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
			g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

			paintTiles(g);

			// draw projectiles
			paintProjectiles(g);

			/*
			 * // draw robot collision bounds
			 * 
			 * g.drawRect((int)Robot.rect.x, (int)Robot.rect.y,
			 * (int)Robot.rect.width, (int)Robot.rect.height);
			 * g.drawRect((int)Robot.rect2.x, (int)Robot.rect2.y,
			 * (int)Robot.rect2.width, (int)Robot.rect2.height);
			 */

			// draw robot
			g.drawImage(currentSprite, // image
					robot.getCenterX() - 61, robot.getCenterY() - 63, // x, y
					this); // observer

			// draw enemies, size of 96x96
			g.drawImage(hanim.getImage(), hb.getCenterX() - 48,
					hb.getCenterY() - 48, this);
			g.drawImage(hanim.getImage(), hb2.getCenterX() - 48,
					hb2.getCenterY() - 48, this);

			// draw score
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(score), 740, 30);
		} else 
		if (state == GameState.Dead)
		{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 800, 480);
			g.setColor(Color.WHITE);
			g.drawString("Dead", 360, 240);
		}
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

		case KeyEvent.VK_CONTROL:
			if (robot.isDucked() == false && robot.isJumped() == false)
			{
				robot.shoot();
				robot.setReadyToFire(false);
			}
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
			currentSprite = anim.getImage();
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

		case KeyEvent.VK_CONTROL:
			robot.setReadyToFire(true);
			break;

		}
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public static Robot getRobot()
	{
		return robot;
	}
}
