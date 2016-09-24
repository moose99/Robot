package kiloboltgame;

import java.util.ArrayList;

//
// The players robot object.
// Contains a list of Projectiles
// The Origin (0,0) pixel is at the TOP LEFT.
//
public class Robot
{
	// Constants are Here
	final int JUMPSPEED = -15;
	final int MOVESPEED = 5;

	private int centerX = 100;
	private int centerY = 377;

	private boolean jumped = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	private boolean ducked = false;
	private boolean readyToFire = true;
	
	public boolean isReadyToFire()
	{
		return readyToFire;
	}

	public void setReadyToFire(boolean readyToFire)
	{
		this.readyToFire = readyToFire;
	}

	private static Background bg1 = StartingClass.getBg1();
	private static Background bg2 = StartingClass.getBg2();

	private int speedX = 0;
	private int speedY = 0;

	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	public void update()
	{
		// Moves Character or Scrolls Background accordingly.
		if (speedX < 0)
		{
			centerX += speedX;
		}
		if (speedX == 0 || speedX < 0)
		{
			bg1.setSpeedX(0);
			bg2.setSpeedX(0);

		}
		if (centerX <= 200 && speedX > 0)
		{
			centerX += speedX;
		}
		if (speedX > 0 && centerX > 200)
		{	// move background at 1/5 speed of character
			bg1.setSpeedX(-MOVESPEED/5);
			bg2.setSpeedX(-MOVESPEED/5);
		}

		// Updates Y Position
		centerY += speedY;

		// Handles Jumping
		if (jumped == true)
		{
			speedY += 1;
		}

		// Prevents going beyond X coordinate of 0
		if (centerX + speedX <= 60)
		{
			centerX = 61;
		}
	}

	public void moveRight()
	{
		if (ducked == false)
		{
			speedX = MOVESPEED;
		}
	}

	public void moveLeft()
	{
		if (ducked == false)
		{
			speedX = -MOVESPEED;
		}
	}

	public void stopRight()
	{
		setMovingRight(false);
		stop();
	}

	public boolean isMovingLeft()
	{
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft)
	{
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight()
	{
		return movingRight;
	}

	public void setMovingRight(boolean movingRight)
	{
		this.movingRight = movingRight;
	}

	public void stopLeft()
	{
		setMovingLeft(false);
		stop();
	}

	private void stop()
	{
		if (isMovingRight() == false && isMovingLeft() == false)
		{
			speedX = 0;
		}

		if (isMovingRight() == false && isMovingLeft() == true)
		{
			moveLeft();
		}

		if (isMovingRight() == true && isMovingLeft() == false)
		{
			moveRight();
		}

	}

	public void jump()
	{
		if (jumped == false)
		{
			speedY = JUMPSPEED;
			jumped = true;
		}
	}

	// create a new projectile and add it to the list
	public void shoot() 
	{
		if (readyToFire)
		{
			Projectile p = new Projectile(centerX + 50, centerY - 25);
			projectiles.add(p);
		}
	}
	
	//
	// Getters and Setters
	//
	
	public int getCenterX()
	{
		return centerX;
	}

	public int getCenterY()
	{
		return centerY;
	}

	public boolean isJumped()
	{
		return jumped;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public int getSpeedY()
	{
		return speedY;
	}

	public boolean isDucked()
	{
		return ducked;
	}

	public void setDucked(boolean ducked)
	{
		this.ducked = ducked;
	}

	public void setCenterX(int centerX)
	{
		this.centerX = centerX;
	}

	public void setCenterY(int centerY)
	{
		this.centerY = centerY;
	}

	public void setJumped(boolean jumped)
	{
		this.jumped = jumped;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
	}

	public void setSpeedY(int speedY)
	{
		this.speedY = speedY;
	}

	public ArrayList<Projectile> getProjectiles()
	{
		return projectiles;
	}

}
