package kiloboltgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Projectile
{
	private int x, y, speedX;
	private boolean visible;

	// collision rect
	private Rectangle r;

	// ctor
	public Projectile(int startX, int startY)
	{
		x = startX;
		y = startY;
		speedX = 7;
		visible = true;
		r = new Rectangle(0, 0, 0, 0);
	}

	//
	// update is called each frame.
	// move the projectile, handle going off screen
	//
	public void update()
	{
		x += speedX;
		r.setBounds(x, y, 10, 5);
		if (x > 800)
		{
			visible = false;
			r = null;
		}
		if (x < 800)
		{
			checkCollision();
		}

	}

	private void checkCollision()
	{
		if (r.intersects(StartingClass.hb.r))
		{
			visible = false;
			StartingClass.score += 1;
		}

		if (r.intersects(StartingClass.hb2.r))
		{
			visible = false;
			StartingClass.score += 1;
		}
	}

	public int getX()
	{
		return x;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public void draw(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(getX(), getY(), 10, 5);
	}
}
