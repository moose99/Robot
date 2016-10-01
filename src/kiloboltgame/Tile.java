package kiloboltgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

public class Tile
{
	private int tileX, tileY, speedX, type;
	public Image tileImage;

	private Background bg = StartingClass.getBg1();
	private Robot robot = StartingClass.getRobot();

	// collision rect
	private Rectangle r;

	public Tile(int x, int y, int type)
	{
		// each tile is 40 pixels
		tileX = x * 40;
		tileY = y * 40;
		this.type = type;
		if (type == 5)
		{
			tileImage = StartingClass.tiledirt;
		} else if (type == 8)
		{
			tileImage = StartingClass.tilegrassTop;
		} else if (type == 4)
		{
			tileImage = StartingClass.tilegrassLeft;
		} else if (type == 6)
		{
			tileImage = StartingClass.tilegrassRight;
		} else if (type == 2)
		{
			tileImage = StartingClass.tilegrassBot;
		} else
		{
			// System.out.println("no tile image: " + type);
			this.type = 0;
		}

		r = new Rectangle();
	}

	public void update()
	{
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;

		// check collision
		r.setBounds(tileX, tileY, 40, 40);

		// check for collision if near the robot and not an empty tile
		if (r.intersects(Robot.yellowRed) && type != 0)
		{
			checkVerticalCollision(Robot.rect, Robot.rect2);
			checkSideCollision(Robot.rect3, Robot.rect4, Robot.footleft,
					Robot.footright);
		}
	}

	public int getTileX()
	{
		return tileX;
	}

	public void setTileX(int tileX)
	{
		this.tileX = tileX;
	}

	public int getTileY()
	{
		return tileY;
	}

	public void setTileY(int tileY)
	{
		this.tileY = tileY;
	}

	public Image getTileImage()
	{
		return tileImage;
	}

	public void setTileImage(Image tileImage)
	{
		this.tileImage = tileImage;
	}

	public void draw(Graphics g, ImageObserver obs)
	{
		g.drawImage(getTileImage(), getTileX(), getTileY(), obs);
	}

	public void checkVerticalCollision(Rectangle rtop, Rectangle rbot)
	{
		if (rtop.intersects(r))
		{
			// System.out.println("upper collision");
		}

		if (rbot.intersects(r) && type == 8 /* dirt */)
		{
			// System.out.println("lower collision");
			robot.setJumped(false);
			robot.setSpeedY(0);
			robot.setCenterY(tileY - 63);
		}
	}

	public void checkSideCollision(Rectangle rleft, Rectangle rright,
			Rectangle leftfoot, Rectangle rightfoot)
	{
		if (type != 5 && type != 2 && type != 0)
		{
			if (rleft.intersects(r))
			{
				robot.setCenterX(tileX + 102);
				robot.setSpeedX(0);
			} 
			else if (leftfoot.intersects(r))
			{
				robot.setCenterX(tileX + 85);
				robot.setSpeedX(0);
			}

			if (rright.intersects(r))
			{
				robot.setCenterX(tileX - 62);
				robot.setSpeedX(0);
			}
			else if (rightfoot.intersects(r))
			{
				robot.setCenterX(tileX - 45);
				robot.setSpeedX(0);
			}
		}
	}
}