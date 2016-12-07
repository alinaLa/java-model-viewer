package game.render;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import game.data.Vector3f;

public class Loader
{

	public Model loadModelFromObj(String name)
	{
		Model result = null;
		try
		{
			BufferedReader br = new BufferedReader(new FileReader("res/" + name + ".obj"));
			List<Vector3f> vertex = new ArrayList<Vector3f>();
			List<Vector3f> normal = new ArrayList<Vector3f>();
			List<Triangle> triangle = new ArrayList<Triangle>();
			String line = "";
			while ((line = br.readLine()) != null)
			{
				if (line.equals("") || line.startsWith("#")) continue;
				String[] tokens = line.split(" ");
				if (tokens[0].equals("v"))
				{
					vertex.add(new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
				}
				if (tokens[0].equals("vn"))
				{
					normal.add(new Vector3f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
				}
				if (tokens[0].equals("f"))
				{
					String[] v1Tokens = tokens[1].split("/");
					String[] v2Tokens = tokens[2].split("/");
					String[] v3Tokens = tokens[3].split("/");
					triangle.add(new Triangle(vertex.get(Integer.parseInt(v1Tokens[0])-1), vertex.get(Integer.parseInt(v2Tokens[0])-1), vertex.get(Integer.parseInt(v3Tokens[0])-1),
											normal.get(Integer.parseInt(v1Tokens[2])-1), normal.get(Integer.parseInt(v2Tokens[2])-1), normal.get(Integer.parseInt(v3Tokens[2])-1)));
				}
			}

			result = new Model(triangle.toArray(new Triangle[] {}), vertex.size());
			br.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return result;
	}

}
