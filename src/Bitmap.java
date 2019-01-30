public class Bitmap
{

    private int width;
    private int height;
    private int[] pixels;

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Bitmap(int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    public void clear(int color)
    {
        ArrayHelper.fill(pixels, color);
    }

    public void setPixel(int index, int color)
    {
        if (index >= 0 && index < pixels.length)
            this.pixels[index] = color;
    }

    public void setPixel(float x, float y, int color)
    {
        if (x >= 0 && x < width && y >= 0 && y < height)
            this.pixels[Math.round(y) * width + Math.round(x)] = color;
    }

    public void drawLine(int x0, int y0, int x1, int y1, int color)
    {
        int dx = x1 - x0;
        int dy = y1 - y0;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (dx < 0) dx1 = -1; else if (dx > 0) dx1 = 1;
        if (dy < 0) dy1 = -1; else if (dy > 0) dy1 = 1;
        if (dx < 0) dx2 = -1; else if (dx > 0) dx2 = 1;
        int longest = Math.abs(dx);
        int shortest = Math.abs(dy);
        if (longest < shortest)
        {
            longest = Math.abs(dy);
            shortest = Math.abs(dx);
            if (dy < 0) dy2 = -1; else if (dy > 0) dy2 = 1;
            dx2 = 0;
        }
        int x = x0;
        int y = y0;
        int numerator = longest >> 1;
        for (int i = 0; i < longest; i++)
        {
            setPixel(x, y, color);
            numerator += shortest;
            if (numerator > longest)
            {
                numerator -= longest;
                x += dx1;
                y += dy1;
            }
            else
            {
                x += dx2;
                y += dy2;
            }
        }
    }

    public void copyToIntArray(int[] dest)
    {
        for (int i = 0; i < pixels.length; i++)
            dest[i] = pixels[i];
    }

    public void drawLine(float x0, float y0, float x1, float y1, int color)
    {
        drawLine(Math.round(x0), Math.round(y0), Math.round(x1), Math.round(y1), color);
    }

    public void drawFilledCircle(int ox, int oy, int r, int color) {
        for (int x = -r; x < r ; x++)
        {
            int height = (int)Math.sqrt(r * r - x * x);

            for (int y = -height; y < height; y++)
                setPixel(x + ox, y + oy, color);
        }
    }

}

