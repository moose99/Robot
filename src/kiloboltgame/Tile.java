package kiloboltgame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;

public class Tile
{
	private int tileX, tileY, speedX;
	public Image tileImage;

	private Background bg = StartingClass.getBg1();
	private Robot robot = StartingClass.getRobot();
	
	public Tile(int x, int y, int type)
	{
		// each tile is 40 pixels
		tileX = x * 40;
		tileY = y * 40;
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
			System.out.println("no tile image: " + type);
		}
	}

	public void update()
	{
		speedX = bg.getSpeedX() * 5;
		tileX += speedX;
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

}