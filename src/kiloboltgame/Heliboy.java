package kiloboltgame;

//
// heliboy enemey, 96x96 sprite
//
// to display heliboy
/*
1. Create an Image object for Heliboy.
2. Define hb variables that will be objects created using the Heliboy constructor.
3. Call the update() method for these objects.
4. Paint hb objects with the Image object created in step
*/

public class Heliboy extends Enemy
{

	public Heliboy(int centerX, int centerY)
	{
		setCenterX(centerX);
		setCenterY(centerY);
	}

}
