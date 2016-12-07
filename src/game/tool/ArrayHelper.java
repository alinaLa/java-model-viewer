package game.tool;

public class ArrayHelper
{

	public static void fill(int[] array, int value)
	{
		int len = array.length;
		if (len > 0)
			array[0] = value;
		for (int i = 1; i < len; i += i) {
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}
	
	public static void fill(float[] array, float value)
	{
		int len = array.length;
		if (len > 0)
			array[0] = value;
		for (int i = 1; i < len; i += i) {
			System.arraycopy(array, 0, array, i, ((len - i) < i) ? (len - i) : i);
		}
	}
	
}
