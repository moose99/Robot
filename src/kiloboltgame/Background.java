package kiloboltgame;

//
// A tiled background image
//
public class Background
{
	private int bgX, bgY; // background upper left
	private int speedX;
	private static int bgXSize = 2160;
	private static int bgYSize = 480;

	//
	// ctor
	//
	public Background(int x, int y)
	{
		bgX = x;
		bgY = y;
		speedX = 0;
	}

	//
	// the background will scroll when the character moves to the right.
	// create an infinitely scrolling background. It will consist of two long images that will consistently loop like this:
	// 1, 2, 1, 2, 1...
	// This function will update the postion an destroy the background when it is no longer visible
	//
	public void update()
	{
		bgX += speedX;

		if (bgX <= -bgXSize)
		{
			bgX += bgXSize*2;
		}
	}

	public int getBgX()
	{
		return bgX;
	}

	public void setBgX(int bgX)
	{
		this.bgX = bgX;
	}

	public int getBgY()
	{
		return bgY;
	}

	public void setBgY(int bgY)
	{
		this.bgY = bgY;
	}

	public int getSpeedX()
	{
		return speedX;
	}

	public void setSpeedX(int speedX)
	{
		this.speedX = speedX;
	}

	public static int getBgXSize()
	{
		return bgXSize;
	}

	public static void setBgXSize(int bgXSize)
	{
		Background.bgXSize = bgXSize;
	}

	public static int getBgYSize()
	{
		return bgYSize;
	}

	public static void setBgYSize(int bgYSize)
	{
		Background.bgYSize = bgYSize;
	}
}
