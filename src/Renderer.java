import java.awt.Color;
import java.util.ArrayList;

public class Renderer
{

    private RenderMode renderMode;
    private RenderType renderType;
    private Matrix4f transform;

    private Bitmap b;
    private float[] zBuffer;

    private Vector3f light;

    private Vector3f cam;
    private Vector3f camNew;

    private Vector3f to;

    public static ArrayList<Vector3f> pyramidViewXY;
    public static ArrayList<Vector3f> pyramidViewXZ;
    public static ArrayList<Vector3f> pyramidViewYZ;


    public Renderer(Bitmap b)
    {
        this.b = b;
        zBuffer = new float[b.getWidth() * b.getHeight()];

        light = Main.light;
        cam = Main.cam;
        to = Main.to;

        pyramidViewXY = Main.pyramidViewXY;
        pyramidViewXZ = Main.pyramidViewXZ;
        pyramidViewYZ = Main.pyramidViewYZ;

    }

    public void render(Model model)
    {
        if (renderMode == RenderMode.WIREFRAME){
            if (renderType == RenderType.XY){
                renderWireframeXY(model);
            }else if (renderType == RenderType.XZ){
                renderWireframeXZ(model);
            }else if (renderType == RenderType.YZ){
                renderWireframeYZ(model);
            }else if (renderType == RenderType.P) {
                renderWireframeP(model);
            }
        }
        else if (renderMode == RenderMode.SHADED) {
            if (renderType == RenderType.XY) {
                renderShadedXY(model);
            } else if (renderType == RenderType.XZ) {
                renderShadedXZ(model);
            } else if (renderType == RenderType.YZ) {
                renderShadedYZ(model);
            } else if (renderType == RenderType.P) {
                renderShadedP(model);
            }
        }
        else if (renderMode == RenderMode.SHADED_GOURAUD) {
            if (renderType == RenderType.XY) {
                renderShadedGouraudXY(model);
            } else if (renderType == RenderType.XZ) {
                renderShadedGouraudXZ(model);
            } else if (renderType == RenderType.YZ) {
                renderShadedGouraudYZ(model);
            } else if (renderType == RenderType.P) {
                renderShadedGouraudP(model);
            }
        }
        else if (renderMode == RenderMode.SHADED_PHONG) {
            if (renderType == RenderType.XY) {
                renderShadedPhongXY(model);
            } else if (renderType == RenderType.XZ) {
                renderShadedPhongXZ(model);
            } else if (renderType == RenderType.YZ) {
                renderShadedPhongYZ(model);
            } else if (renderType == RenderType.P) {
                renderShadedPhongP(model);
            }
        }

        //light and camera
        int c = 0xe056e0;
        if (renderType == RenderType.XY){
            //b.drawFilledCircle(b.getWidth() - (int)light.getX(), b.getHeight() - (int)light.getY(), 10, 0xFFF2CC);
            b.drawFilledCircle((int)camNew.getX(), (int)camNew.getY(), 10, 0xFF00FF);
            b.drawFilledCircle((int)to.getX(), (int)to.getY(), 10, 0x00FF00);
            b.drawFilledCircle((int)light.getX(), (int)light.getY(), 10, 0xFFFF00);

            b.drawLine(pyramidViewXY.get(0).getX(), pyramidViewXY.get(0).getY(), pyramidViewXY.get(1).getX(), pyramidViewXY.get(1).getY(), c);
            b.drawLine(pyramidViewXY.get(1).getX(), pyramidViewXY.get(1).getY(), pyramidViewXY.get(2).getX(), pyramidViewXY.get(2).getY(), c);
            b.drawLine(pyramidViewXY.get(2).getX(), pyramidViewXY.get(2).getY(), pyramidViewXY.get(3).getX(), pyramidViewXY.get(3).getY(), c);
            b.drawLine(pyramidViewXY.get(3).getX(), pyramidViewXY.get(3).getY(), pyramidViewXY.get(0).getX(), pyramidViewXY.get(0).getY(), c);
            b.drawLine(pyramidViewXY.get(0).getX(), pyramidViewXY.get(0).getY(), camNew.getX(), camNew.getY(), c);
            b.drawLine(pyramidViewXY.get(1).getX(), pyramidViewXY.get(1).getY(), camNew.getX(), camNew.getY(), c);
            b.drawLine(pyramidViewXY.get(2).getX(), pyramidViewXY.get(2).getY(), camNew.getX(), camNew.getY(), c);
            b.drawLine(pyramidViewXY.get(3).getX(), pyramidViewXY.get(3).getY(), camNew.getX(), camNew.getY(), c);

            //b.drawLine(0,to.getY()-b.getHeight()/2, b.getWidth(),to.getY()-b.getHeight()/2, 0xF2B9F2);
            //b.drawLine(0,to.getY()+b.getHeight()/2, b.getWidth(),to.getY()+b.getHeight()/2, 0xF2B9F2);

        }else if (renderType == RenderType.XZ){
            //b.drawFilledCircle(b.getWidth() - (int)light.getX(), b.getHeight() - (int)light.getZ(), 10, 0xFFF2CC);
            b.drawFilledCircle((int)camNew.getX(), (int)camNew.getZ(), 10, 0xFF00FF);
            b.drawFilledCircle((int)to.getX(), (int)to.getZ(), 10, 0x00FF00);
            b.drawFilledCircle((int)light.getX(), (int)light.getZ(), 10, 0xFFFF00);

            b.drawLine(pyramidViewXZ.get(0).getX(), pyramidViewXZ.get(0).getZ(), pyramidViewXZ.get(1).getX(), pyramidViewXZ.get(1).getZ(), c);
            b.drawLine(pyramidViewXZ.get(1).getX(), pyramidViewXZ.get(1).getZ(), pyramidViewXZ.get(2).getX(), pyramidViewXZ.get(2).getZ(), c);
            b.drawLine(pyramidViewXZ.get(2).getX(), pyramidViewXZ.get(2).getZ(), pyramidViewXZ.get(3).getX(), pyramidViewXZ.get(3).getZ(), c);
            b.drawLine(pyramidViewXZ.get(3).getX(), pyramidViewXZ.get(3).getZ(), pyramidViewXZ.get(0).getX(), pyramidViewXZ.get(0).getZ(), c);
            b.drawLine(pyramidViewXZ.get(0).getX(), pyramidViewXZ.get(0).getZ(), camNew.getX(), camNew.getZ(), c);
            b.drawLine(pyramidViewXZ.get(1).getX(), pyramidViewXZ.get(1).getZ(), camNew.getX(), camNew.getZ(), c);
            b.drawLine(pyramidViewXZ.get(2).getX(), pyramidViewXZ.get(2).getZ(), camNew.getX(), camNew.getZ(), c);
            b.drawLine(pyramidViewXZ.get(3).getX(), pyramidViewXZ.get(3).getZ(), camNew.getX(), camNew.getZ(), c);
        }else if (renderType == RenderType.YZ){
            //b.drawFilledCircle(b.getWidth() - (int)light.getX(), b.getHeight() - (int)light.getZ(), 10, 0xFFF2CC);
            b.drawFilledCircle((int)camNew.getY(), (int)camNew.getZ(), 10, 0xFF00FF);
            b.drawFilledCircle((int)to.getY(), (int)to.getZ(), 10, 0x00FF00);
            b.drawFilledCircle((int)light.getY(), (int)light.getZ(), 10, 0xFFFF00);

            b.drawLine(pyramidViewYZ.get(0).getY(), pyramidViewYZ.get(0).getZ(), pyramidViewYZ.get(1).getY(), pyramidViewYZ.get(1).getZ(), c);
            b.drawLine(pyramidViewYZ.get(1).getY(), pyramidViewYZ.get(1).getZ(), pyramidViewYZ.get(2).getY(), pyramidViewYZ.get(2).getZ(), c);
            b.drawLine(pyramidViewYZ.get(2).getY(), pyramidViewYZ.get(2).getZ(), pyramidViewYZ.get(3).getY(), pyramidViewYZ.get(3).getZ(), c);
            b.drawLine(pyramidViewYZ.get(3).getY(), pyramidViewYZ.get(3).getZ(), pyramidViewYZ.get(0).getY(), pyramidViewYZ.get(0).getZ(), c);
            b.drawLine(pyramidViewYZ.get(0).getY(), pyramidViewYZ.get(0).getZ(), camNew.getY(), camNew.getZ(), c);
            b.drawLine(pyramidViewYZ.get(1).getY(), pyramidViewYZ.get(1).getZ(), camNew.getY(), camNew.getZ(), c);
            b.drawLine(pyramidViewYZ.get(2).getY(), pyramidViewYZ.get(2).getZ(), camNew.getY(), camNew.getZ(), c);
            b.drawLine(pyramidViewYZ.get(3).getY(), pyramidViewYZ.get(3).getZ(), camNew.getY(), camNew.getZ(), c);
        }



    }

    private void renderWireframeXY(Model model)
    {
        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v2 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            b.drawLine(v0.getX(), v0.getY(), v1.getX(), v1.getY(), 0xFFFFFF);
            b.drawLine(v1.getX(), v1.getY(), v2.getX(), v2.getY(), 0xFFFFFF);
            b.drawLine(v2.getX(), v2.getY(), v0.getX(), v0.getY(), 0xFFFFFF);
        }
    }

    private void renderWireframeXZ(Model model)
    {

        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v2 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            b.drawLine(v0.getX(), v0.getZ(), v1.getX(), v1.getZ(), 0xFFFFFF);
            b.drawLine(v1.getX(), v1.getZ(), v2.getX(), v2.getZ(), 0xFFFFFF);
            b.drawLine(v2.getX(), v2.getZ(), v0.getX(), v0.getZ(), 0xFFFFFF);
        }
    }

    private void renderWireframeYZ(Model model)
    {
        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v2 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            b.drawLine(v0.getY(), v0.getZ(), v1.getY(), v1.getZ(), 0xFFFFFF);
            b.drawLine(v1.getY(), v1.getZ(), v2.getY(), v2.getZ(), 0xFFFFFF);
            b.drawLine(v2.getY(), v2.getZ(), v0.getY(), v0.getZ(), 0xFFFFFF);
        }
    }

    private void renderWireframeP(Model model)
    {
        for (Triangle t : model.getTriangles())
        {

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v2 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            double o0 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v0.getZ())*(cam.getZ()-v0.getZ())));
            double o1 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v1.getZ())*(cam.getZ()-v1.getZ())));
            double o2 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v2.getZ())*(cam.getZ()-v2.getZ())));
            b.drawLine((int)(v0.getX()*o0), (int)(v0.getY()*o0), (int)(v1.getX()*o1), (int)(v1.getY()*o1), 0xFFFFFF);
            b.drawLine((int)(v1.getX()*o1), (int)(v1.getY()*o1), (int)(v2.getX()*o2), (int)(v2.getY()*o2), 0xFFFFFF);
            b.drawLine((int)(v2.getX()*o2), (int)(v2.getY()*o2), (int)(v0.getX()*o0), (int)(v0.getY()*o0), 0xFFFFFF);
        }
    }

    private void renderShadedXY(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

            float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

            for (int y = minY; y <= maxY; y++)
            {
                if (y < 0 || y >= b.getHeight()) continue;
                int yIndex = y * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = yIndex + x;
                    float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
                        if (zBuffer[index] < depth)
                        {
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedXZ(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxZ = (int)Math.min(b.getHeight()-1, Math.max(v0.getZ(), Math.max(v1.getZ(), v3.getZ())));
            int minZ = (int)Math.max(0, Math.min(v0.getZ(), Math.min(v1.getZ(), v3.getZ())));

            float triangleArea = (v0.getZ() - v3.getZ()) * (v1.getX() - v3.getX()) + (v1.getZ() - v3.getZ()) * (v3.getX() - v0.getX());

            for (int z = minZ; z <= maxZ; z++)
            {
                if (z < 0 || z >= b.getHeight()) continue;
                int zIndex = z * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = zIndex + x;
                    float b1 = ((z - v3.getZ()) * (v1.getX() - v3.getX()) + (v1.getZ() - v3.getZ()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((z - v0.getZ()) * (v3.getX() - v0.getX()) + (v3.getZ() - v0.getZ()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((z - v1.getZ()) * (v0.getX() - v1.getX()) + (v0.getZ() - v1.getZ()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getY() + b2 * v1.getY() + b3 * v3.getY();
                        if (zBuffer[index] < depth)
                        {
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedYZ(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxY = (int)Math.min(b.getWidth()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));
            int maxZ = (int)Math.min(b.getHeight()-1, Math.max(v0.getZ(), Math.max(v1.getZ(), v3.getZ())));
            int minZ = (int)Math.max(0, Math.min(v0.getZ(), Math.min(v1.getZ(), v3.getZ())));

            float triangleArea = (v0.getZ() - v3.getZ()) * (v1.getY() - v3.getY()) + (v1.getZ() - v3.getZ()) * (v3.getY() - v0.getY());

            for (int z = minZ; z <= maxZ; z++)
            {
                if (z < 0 || z >= b.getHeight()) continue;
                int zIndex = z * b.getWidth();
                for (int y = minY; y <= maxY; y++)
                {
                    if (y < 0 || y >= b.getWidth()) continue;
                    int index = zIndex + y;
                    float b1 = ((z - v3.getZ()) * (v1.getY() - v3.getY()) + (v1.getZ() - v3.getZ()) * (v3.getY() - y)) / triangleArea;
                    float b2 = ((z - v0.getZ()) * (v3.getY() - v0.getY()) + (v3.getZ() - v0.getZ()) * (v0.getY() - y)) / triangleArea;
                    float b3 = ((z - v1.getZ()) * (v0.getY() - v1.getY()) + (v0.getZ() - v1.getZ()) * (v1.getY() - y)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getX() + b2 * v1.getX() + b3 * v3.getX();
                        if (zBuffer[index] < depth)
                        {
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedP(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            double o0 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v0.getZ())*(cam.getZ()-v0.getZ())));
            double o1 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v1.getZ())*(cam.getZ()-v1.getZ())));
            double o2 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v3.getZ())*(cam.getZ()-v3.getZ())));

            v0.setX((float)(v0.getX()*o0));
            v0.setY((float)(v0.getY()*o0));
            v0.setZ((float)(v0.getZ()*o0));
            v1.setX((float)(v1.getX()*o1));
            v1.setY((float)(v1.getY()*o1));
            v1.setZ((float)(v1.getZ()*o1));
            v3.setX((float)(v3.getX()*o2));
            v3.setY((float)(v3.getY()*o2));
            v3.setZ((float)(v3.getZ()*o2));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

            float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

            for (int y = minY; y <= maxY; y++)
            {
                if (y < 0 || y >= b.getHeight()) continue;
                int yIndex = y * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = yIndex + x;
                    float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
                        if (zBuffer[index] < depth)
                        {
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedGouraudXY(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {

            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            //float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

            float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

            for (int y = minY; y <= maxY; y++)
            {
                if (y < 0 || y >= b.getHeight()) continue;
                int yIndex = y * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {

                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = yIndex + x;
                    float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getX()-v0.getX(), v3.getY()-v0.getY(), 0);
                            Vector3f ap = new Vector3f(x-v0.getX(), y-v0.getY(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bp = new Vector3f(x-v1.getX(), y-v1.getY(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getX()-v3.getX(), v1.getY()-v3.getY(), 0);
                            Vector3f cp = new Vector3f(x-v3.getX(), y-v3.getY(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getX()-v1.getX(), v3.getY()-v1.getY(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            float interpolation = angleCos0*w + angleCos1*u + angleCos2*v;
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, interpolation).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedGouraudXZ(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxZ = (int)Math.min(b.getHeight()-1, Math.max(v0.getZ(), Math.max(v1.getZ(), v3.getZ())));
            int minZ = (int)Math.max(0, Math.min(v0.getZ(), Math.min(v1.getZ(), v3.getZ())));

            float triangleArea = (v0.getZ() - v3.getZ()) * (v1.getX() - v3.getX()) + (v1.getZ() - v3.getZ()) * (v3.getX() - v0.getX());

            for (int z = minZ; z <= maxZ; z++)
            {
                if (z < 0 || z >= b.getHeight()) continue;
                int zIndex = z * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = zIndex + x;
                    float b1 = ((z - v3.getZ()) * (v1.getX() - v3.getX()) + (v1.getZ() - v3.getZ()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((z - v0.getZ()) * (v3.getX() - v0.getX()) + (v3.getZ() - v0.getZ()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((z - v1.getZ()) * (v0.getX() - v1.getX()) + (v0.getZ() - v1.getZ()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getY() + b2 * v1.getY() + b3 * v3.getY();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getX()-v0.getX(), v3.getZ()-v0.getZ(), 0);
                            Vector3f ap = new Vector3f(x-v0.getX(), z-v0.getZ(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getZ()-v1.getZ(), 0);
                            Vector3f bp = new Vector3f(x-v1.getX(), z-v1.getZ(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getX()-v3.getX(), v1.getZ()-v3.getZ(), 0);
                            Vector3f cp = new Vector3f(x-v3.getX(), z-v3.getZ(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getX()-v1.getX(), v3.getZ()-v1.getZ(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            float interpolation = angleCos0*w + angleCos1*u + angleCos2*v;
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, interpolation).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedGouraudYZ(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxY = (int)Math.min(b.getWidth()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));
            int maxZ = (int)Math.min(b.getHeight()-1, Math.max(v0.getZ(), Math.max(v1.getZ(), v3.getZ())));
            int minZ = (int)Math.max(0, Math.min(v0.getZ(), Math.min(v1.getZ(), v3.getZ())));

            float triangleArea = (v0.getZ() - v3.getZ()) * (v1.getY() - v3.getY()) + (v1.getZ() - v3.getZ()) * (v3.getY() - v0.getY());

            for (int z = minZ; z <= maxZ; z++)
            {
                if (z < 0 || z >= b.getHeight()) continue;
                int zIndex = z * b.getWidth();
                for (int y = minY; y <= maxY; y++)
                {
                    if (y < 0 || y >= b.getWidth()) continue;
                    int index = zIndex + y;
                    float b1 = ((z - v3.getZ()) * (v1.getY() - v3.getY()) + (v1.getZ() - v3.getZ()) * (v3.getY() - y)) / triangleArea;
                    float b2 = ((z - v0.getZ()) * (v3.getY() - v0.getY()) + (v3.getZ() - v0.getZ()) * (v0.getY() - y)) / triangleArea;
                    float b3 = ((z - v1.getZ()) * (v0.getY() - v1.getY()) + (v0.getZ() - v1.getZ()) * (v1.getY() - y)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getX() + b2 * v1.getX() + b3 * v3.getX();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getY()-v0.getY(), v3.getZ()-v0.getZ(), 0);
                            Vector3f ap = new Vector3f(y-v0.getY(), z-v0.getZ(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getY()-v1.getY(), v0.getZ()-v1.getZ(), 0);
                            Vector3f bp = new Vector3f(y-v1.getY(), z-v1.getZ(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getY()-v3.getY(), v1.getZ()-v3.getZ(), 0);
                            Vector3f cp = new Vector3f(y-v3.getY(), z-v3.getZ(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getY()-v1.getY(), v3.getZ()-v1.getZ(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            float interpolation = angleCos0*w + angleCos1*u + angleCos2*v;
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, interpolation).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedGouraudP(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {

            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            double o0 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v0.getZ())*(cam.getZ()-v0.getZ())));
            double o1 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v1.getZ())*(cam.getZ()-v1.getZ())));
            double o2 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v3.getZ())*(cam.getZ()-v3.getZ())));

            v0.setX((float)(v0.getX()*o0));
            v0.setY((float)(v0.getY()*o0));
            v0.setZ((float)(v0.getZ()*o0));
            v1.setX((float)(v1.getX()*o1));
            v1.setY((float)(v1.getY()*o1));
            v1.setZ((float)(v1.getZ()*o1));
            v3.setX((float)(v3.getX()*o2));
            v3.setY((float)(v3.getY()*o2));
            v3.setZ((float)(v3.getZ()*o2));

            Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

            float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

            for (int y = minY; y <= maxY; y++)
            {
                if (y < 0 || y >= b.getHeight()) continue;
                int yIndex = y * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = yIndex + x;
                    float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getX()-v0.getX(), v3.getY()-v0.getY(), 0);
                            Vector3f ap = new Vector3f(x-v0.getX(), y-v0.getY(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bp = new Vector3f(x-v1.getX(), y-v1.getY(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getX()-v3.getX(), v1.getY()-v3.getY(), 0);
                            Vector3f cp = new Vector3f(x-v3.getX(), y-v3.getY(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getX()-v1.getX(), v3.getY()-v1.getY(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            float interpolation = angleCos0*w + angleCos1*u + angleCos2*v;
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, interpolation).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }


    private void renderShadedPhongXY(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {

            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            //float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

            float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

            for (int y = minY; y <= maxY; y++)
            {
                if (y < 0 || y >= b.getHeight()) continue;
                int yIndex = y * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {

                    if (x < 0 || x >= b.getWidth()) continue;

                    int index = yIndex + x;
                    float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie

                            Vector3f ac = new Vector3f(v3.getX()-v0.getX(), v3.getY()-v0.getY(), 0);
                            Vector3f ap = new Vector3f(x-v0.getX(), y-v0.getY(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bp = new Vector3f(x-v1.getX(), y-v1.getY(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getX()-v3.getX(), v1.getY()-v3.getY(), 0);
                            Vector3f cp = new Vector3f(x-v3.getX(), y-v3.getY(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getX()-v1.getX(), v3.getY()-v1.getY(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            Vector3f norm = norm0.scale(w).add(norm1.scale(u)).add(norm2.scale(v));
                            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());


                            /////////
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedPhongXZ(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            //Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            //float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxZ = (int)Math.min(b.getHeight()-1, Math.max(v0.getZ(), Math.max(v1.getZ(), v3.getZ())));
            int minZ = (int)Math.max(0, Math.min(v0.getZ(), Math.min(v1.getZ(), v3.getZ())));

            float triangleArea = (v0.getZ() - v3.getZ()) * (v1.getX() - v3.getX()) + (v1.getZ() - v3.getZ()) * (v3.getX() - v0.getX());

            for (int z = minZ; z <= maxZ; z++)
            {
                if (z < 0 || z >= b.getHeight()) continue;
                int zIndex = z * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = zIndex + x;
                    float b1 = ((z - v3.getZ()) * (v1.getX() - v3.getX()) + (v1.getZ() - v3.getZ()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((z - v0.getZ()) * (v3.getX() - v0.getX()) + (v3.getZ() - v0.getZ()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((z - v1.getZ()) * (v0.getX() - v1.getX()) + (v0.getZ() - v1.getZ()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getY() + b2 * v1.getY() + b3 * v3.getY();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getX()-v0.getX(), v3.getZ()-v0.getZ(), 0);
                            Vector3f ap = new Vector3f(x-v0.getX(), z-v0.getZ(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getZ()-v1.getZ(), 0);
                            Vector3f bp = new Vector3f(x-v1.getX(), z-v1.getZ(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getX()-v3.getX(), v1.getZ()-v3.getZ(), 0);
                            Vector3f cp = new Vector3f(x-v3.getX(), z-v3.getZ(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getX()-v1.getX(), v3.getZ()-v1.getZ(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            Vector3f norm = norm0.scale(w).add(norm1.scale(u)).add(norm2.scale(v));
                            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedPhongYZ(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {
            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            //Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            //float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxY = (int)Math.min(b.getWidth()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));
            int maxZ = (int)Math.min(b.getHeight()-1, Math.max(v0.getZ(), Math.max(v1.getZ(), v3.getZ())));
            int minZ = (int)Math.max(0, Math.min(v0.getZ(), Math.min(v1.getZ(), v3.getZ())));

            float triangleArea = (v0.getZ() - v3.getZ()) * (v1.getY() - v3.getY()) + (v1.getZ() - v3.getZ()) * (v3.getY() - v0.getY());

            for (int z = minZ; z <= maxZ; z++)
            {
                if (z < 0 || z >= b.getHeight()) continue;
                int zIndex = z * b.getWidth();
                for (int y = minY; y <= maxY; y++)
                {
                    if (y < 0 || y >= b.getWidth()) continue;
                    int index = zIndex + y;
                    float b1 = ((z - v3.getZ()) * (v1.getY() - v3.getY()) + (v1.getZ() - v3.getZ()) * (v3.getY() - y)) / triangleArea;
                    float b2 = ((z - v0.getZ()) * (v3.getY() - v0.getY()) + (v3.getZ() - v0.getZ()) * (v0.getY() - y)) / triangleArea;
                    float b3 = ((z - v1.getZ()) * (v0.getY() - v1.getY()) + (v0.getZ() - v1.getZ()) * (v1.getY() - y)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getX() + b2 * v1.getX() + b3 * v3.getX();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getY()-v0.getY(), v3.getZ()-v0.getZ(), 0);
                            Vector3f ap = new Vector3f(y-v0.getY(), z-v0.getZ(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getY()-v1.getY(), v0.getZ()-v1.getZ(), 0);
                            Vector3f bp = new Vector3f(y-v1.getY(), z-v1.getZ(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getY()-v3.getY(), v1.getZ()-v3.getZ(), 0);
                            Vector3f cp = new Vector3f(y-v3.getY(), z-v3.getZ(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getY()-v1.getY(), v3.getZ()-v1.getZ(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            Vector3f norm = norm0.scale(w).add(norm1.scale(u)).add(norm2.scale(v));
                            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private void renderShadedPhongP(Model model)
    {
        ArrayHelper.fill(zBuffer, Float.NEGATIVE_INFINITY);

        for (Triangle t : model.getTriangles())
        {

            ArrayList<Triangle> neighbors = new ArrayList<>();
            ArrayList<Vector3f> norms = new ArrayList<>();
            for (Triangle n : model.getTriangles()){
                if(!neighbors.contains(n)
                        && (n.v0 == t.v0 || n.v0 == t.v1 || n.v0 == t.v2
                        || n.v1 == t.v0 || n.v1 == t.v1 || n.v1 == t.v2
                        || n.v2 == t.v0 || n.v2 == t.v1 || n.v2 == t.v2)){
                    neighbors.add(n);
                }
            }

            //liczymy wektory normalne w wierzchołkach
            Vector3f norm0 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v0)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm0 = norm0.add(neighborNorm);

                }
            }
            Vector3f norm1 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v1)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm1 = norm1.add(neighborNorm);
                }
            }
            Vector3f norm2 = new Vector3f();
            for(Triangle neighbor : neighbors){
                if(neighbor.contains(t.v2)){
                    Vector3f v0 = new Vector3f(transform.transform(new Vector4f(neighbor.v0, 1)));
                    Vector3f v1 = new Vector3f(transform.transform(new Vector4f(neighbor.v1, 1)));
                    Vector3f v3 = new Vector3f(transform.transform(new Vector4f(neighbor.v2, 1)));
                    Vector3f neighborNorm = v1.sub(v0).cross(v3.sub(v0));
                    norm2 = norm2.add(neighborNorm);
                }
            }

            Vector3f v0 = new Vector3f(transform.transform(new Vector4f(t.v0, 1)));
            Vector3f v1 = new Vector3f(transform.transform(new Vector4f(t.v1, 1)));
            Vector3f v3 = new Vector3f(transform.transform(new Vector4f(t.v2, 1)));

            double o0 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v0.getZ())*(cam.getZ()-v0.getZ())));
            double o1 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v1.getZ())*(cam.getZ()-v1.getZ())));
            double o2 = Math.abs(cam.getZ())/(Math.sqrt((cam.getZ()-v3.getZ())*(cam.getZ()-v3.getZ())));

            v0.setX((float)(v0.getX()*o0));
            v0.setY((float)(v0.getY()*o0));
            v0.setZ((float)(v0.getZ()*o0));
            v1.setX((float)(v1.getX()*o1));
            v1.setY((float)(v1.getY()*o1));
            v1.setZ((float)(v1.getZ()*o1));
            v3.setX((float)(v3.getX()*o2));
            v3.setY((float)(v3.getY()*o2));
            v3.setZ((float)(v3.getZ()*o2));

            //Vector3f norm = v1.sub(v0).cross(v3.sub(v0));
            //float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());

            int maxX = (int)Math.min(b.getWidth()-1, Math.max(v0.getX(), Math.max(v1.getX(), v3.getX())));
            int minX = (int)Math.max(0, Math.min(v0.getX(), Math.min(v1.getX(), v3.getX())));
            int maxY = (int)Math.min(b.getHeight()-1, Math.max(v0.getY(), Math.max(v1.getY(), v3.getY())));
            int minY = (int)Math.max(0, Math.min(v0.getY(), Math.min(v1.getY(), v3.getY())));

            float triangleArea = (v0.getY() - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - v0.getX());

            for (int y = minY; y <= maxY; y++)
            {
                if (y < 0 || y >= b.getHeight()) continue;
                int yIndex = y * b.getWidth();
                for (int x = minX; x <= maxX; x++)
                {
                    if (x < 0 || x >= b.getWidth()) continue;
                    int index = yIndex + x;
                    float b1 = ((y - v3.getY()) * (v1.getX() - v3.getX()) + (v1.getY() - v3.getY()) * (v3.getX() - x)) / triangleArea;
                    float b2 = ((y - v0.getY()) * (v3.getX() - v0.getX()) + (v3.getY() - v0.getY()) * (v0.getX() - x)) / triangleArea;
                    float b3 = ((y - v1.getY()) * (v0.getX() - v1.getX()) + (v0.getY() - v1.getY()) * (v1.getX() - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1)
                    {
                        float depth = b1 * v0.getZ() + b2 * v1.getZ() + b3 * v3.getZ();
                        if (zBuffer[index] < depth)
                        {
                            /////////cieniowanie
                            float angleCos0 = Math.max(0, norm0.dot(light)) / (norm0.length() * light.length());
                            float angleCos1 = Math.max(0, norm1.dot(light)) / (norm1.length() * light.length());
                            float angleCos2 = Math.max(0, norm2.dot(light)) / (norm2.length() * light.length());

                            Vector3f ac = new Vector3f(v3.getX()-v0.getX(), v3.getY()-v0.getY(), 0);
                            Vector3f ap = new Vector3f(x-v0.getX(), y-v0.getY(), 0);
                            float cap = (ac.cross(ap)).length();
                            Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bp = new Vector3f(x-v1.getX(), y-v1.getY(), 0);
                            float abp = (ba.cross(bp)).length();
                            Vector3f cb = new Vector3f(v1.getX()-v3.getX(), v1.getY()-v3.getY(), 0);
                            Vector3f cp = new Vector3f(x-v3.getX(), y-v3.getY(), 0);
                            float bcp = (cb.cross(cp)).length();
                            //Vector3f ba = new Vector3f(v0.getX()-v1.getX(), v0.getY()-v1.getY(), 0);
                            Vector3f bc = new Vector3f(v3.getX()-v1.getX(), v3.getY()-v1.getY(), 0);
                            float abc = (ba.cross(bc)).length();

                            float u = cap/abc;
                            float v = abp/abc;
                            float w = bcp/abc;

                            Vector3f norm = norm0.scale(w).add(norm1.scale(u)).add(norm2.scale(v));
                            float angleCos = Math.max(0, norm.dot(light)) / (norm.length() * light.length());
                            /////////
                            b.setPixel(index, getShade(Color.WHITE, angleCos).getRGB());
                            zBuffer[index] = depth;
                        }
                    }
                }
            }
        }
    }

    private Color getShade(Color color, float shade)
    {
        int red = (int) ((float)color.getRed() * shade);
        int green = (int) ((float)color.getGreen() * shade);
        int blue = (int) ((float)color.getBlue() * shade);
        return new Color(red, green, blue);
    }

    public void setTransform(Matrix4f transform)
    {
        this.transform = transform;
    }

    public void setRenderMode(RenderMode renderMode)
    {
        this.renderMode = renderMode;
    }

    public RenderMode getRenderMode()
    {
        return renderMode;
    }

    public void setRenderType(RenderType renderType)
    {
        this.renderType = renderType;
    }

    public RenderType getRenderType()
    {
        return renderType;
    }

    public Vector3f getCam() {
        return camNew;
    }

    public void setCam(Vector3f cam) {
        this.camNew = cam;
    }

    public void setCam(float x, float y, float z) {
        this.camNew.setX(x);
        this.camNew.setY(y);
        this.camNew.setZ(z);
    }

    public Vector3f getTo() {
        return to;
    }

    public void setTo(Vector3f to) {
        this.to = to;
    }

    public Vector3f getLight() {
        return to;
    }

    public void setLight(Vector3f light) {
        this.light = light;
    }


}