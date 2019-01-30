public class Model
{

    private Triangle[] triangles;
    private int vertexCount;


    public Model(Triangle[] triangles, int vertexCount)
    {
        this.triangles = triangles;
        this.vertexCount = vertexCount;
    }

    public void setTriangles(Triangle[] triangles)
    {
        this.triangles = triangles.clone();
    }

    public Triangle[] getTriangles()
    {
        return triangles;
    }

    public int getTriangleCount()
    {
        return triangles.length;
    }

    public void setVertexCount(int vertexCount)
    {
        this.vertexCount = vertexCount;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

}
