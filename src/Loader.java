import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Loader
{

    public Model loadModelFromObj(String name)
    {
        Model result = null;
        try
        {
            Main.modelName = name;
            if(name.substring(name.length()-4).equals(".obj")) {
                BufferedReader br = new BufferedReader(new FileReader("res/" + name));
                List<Vector3f> vertex = new ArrayList<Vector3f>();
                List<Vector3f> normal = new ArrayList<Vector3f>();
                List<Triangle> triangle = new ArrayList<Triangle>();
                String line = "";
                while ((line = br.readLine()) != null) {
                    if (line.equals("") || line.startsWith("#")) continue;
                    String[] tokens = line.split(" ");
                    if (tokens[0].equals("v")) {
                        vertex.add(new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
                    }
                    if (tokens[0].equals("vn")) {
                        normal.add(new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
                    }
                    if (tokens[0].equals("f")) {
                        String[] v1Tokens = tokens[1].split("/");
                        String[] v2Tokens = tokens[2].split("/");
                        String[] v3Tokens = tokens[3].split("/");
                        triangle.add(new Triangle(vertex.get(Integer.parseInt(v1Tokens[0]) - 1), vertex.get(Integer.parseInt(v2Tokens[0]) - 1), vertex.get(Integer.parseInt(v3Tokens[0]) - 1),
                                normal.get(Integer.parseInt(v1Tokens[2]) - 1), normal.get(Integer.parseInt(v2Tokens[2]) - 1), normal.get(Integer.parseInt(v3Tokens[2]) - 1)));
                    }
                }

                result = new Model(triangle.toArray(new Triangle[]{}), vertex.size());
                br.close();
            }else {
                BufferedReader br = new BufferedReader(new FileReader("res/" + name));
                List<Vector3f> points = new ArrayList<Vector3f>();
                //List<Vector3f> normal = new ArrayList<Vector3f>();
                List<Triangle> triangle = new ArrayList<Triangle>();
                String line = "";
                while (!(line = br.readLine()).equals("")) {
                    String[] tokens = line.split(" ");
                    System.out.println(tokens[0]);
                    points.add(new Vector3f(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));

                }
                while (!(line = br.readLine()).equals("")) {
                    String[] tokens = line.split(" ");
                    triangle.add(new Triangle(points.get(Integer.parseInt(tokens[0])), points.get(Integer.parseInt(tokens[1])), points.get(Integer.parseInt(tokens[2]))));
                }

                result = new Model(triangle.toArray(new Triangle[]{}), points.size());
                br.close();
            }

            File f = new File("res/" + name.substring(0, name.length()-4) + ".txt");
            if(f.isFile()){
                BufferedReader br = new BufferedReader(new FileReader("res/" + name.substring(0, name.length()-4) + ".txt"));
                String line = "";
                line = br.readLine();
                String[] tokens = line.split(" ");
                Main.cam.setX(Float.parseFloat(tokens[0]));
                Main.cam.setY(Float.parseFloat(tokens[1]));
                Main.cam.setZ(Float.parseFloat(tokens[2]));
                line = br.readLine();
                tokens = line.split(" ");
                Main.to.setX(Float.parseFloat(tokens[0]));
                Main.to.setY(Float.parseFloat(tokens[1]));
                Main.to.setZ(Float.parseFloat(tokens[2]));
                line = br.readLine();
                tokens = line.split(" ");
                Main.angleValue = Float.parseFloat(tokens[0]);
                br.close();
            }else{
                Main.cam.setX(200);
                Main.cam.setY(200);
                Main.cam.setZ(600);
                Main.to.setX(50);
                Main.to.setY(50);
                Main.to.setZ(100);
                Main.angleValue = 80;
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }

}