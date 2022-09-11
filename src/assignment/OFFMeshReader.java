package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

public class OFFMeshReader implements MeshReader{

	@Override
	public HashSet<Polygon> read(String filename) throws IOException, WrongFileFormatException {
		
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		
		String currLine = br.readLine();
		
		List<Vertex> vertices = new ArrayList<Vertex>();
				
		if (!Pattern.compile("OFF\\s*").matcher(currLine).matches()) {
			br.close();
			throw new WrongFileFormatException("Invalid Header");
		}
		
		currLine = br.readLine();

		String[] split = currLine.split("\\s+");
		
		if (split.length != 3) {
			br.close();
			throw new WrongFileFormatException("Subheader does not have 3 entries");
		}
		
		int numVertex,numFaces;
		
		try {
			numVertex = Integer.parseInt(split[0]);
			numFaces = Integer.parseInt(split[1]);
		}
		catch(NumberFormatException e) {
			br.close();
			throw new WrongFileFormatException(e.getMessage());
		}		

		currLine = br.readLine();
		
		double x, y, z;
		
		for (int i = 0; i < numVertex; i++) {
			
			
			split = currLine.split("\\s+");
						
			if (split.length != 3) {
				br.close();
				throw new WrongFileFormatException("Vertex does not contain 3 values");
			}
			
			try {
			
				x = Double.parseDouble(split[0]);
				y = Double.parseDouble(split[1]);
				z = Double.parseDouble(split[2]);
				vertices.add(new Vertex(x, y, z));
			
			}
			
			catch(NumberFormatException e) {
				
				br.close();
				throw new WrongFileFormatException(e.getMessage());
			}
			
			currLine = br.readLine();
		}
		
		int numVertexInPolygon;
		Vertex v;
		LinkedHashSet<Vertex> faceVertices;
		
		for (int i = 0; i < numFaces; i++) {
			
			
			split = currLine.split("\\s+");
			
			try {
				numVertexInPolygon = Integer.parseInt(split[0]);
			}
			catch(NumberFormatException e) {
				
				br.close();
				throw new WrongFileFormatException(e.getMessage());
			}
			
			if (split.length != numVertexInPolygon + 4 && split.length != numVertexInPolygon + 1) {
				br.close();
				throw new WrongFileFormatException("Incorrect number of entries in face");
			}
			
 			faceVertices = new LinkedHashSet<Vertex>();
 			
 			
 			try {
 			
 			for (int j = 1; j <= numVertexInPolygon; j++) {
 				v = vertices.get(Integer.parseInt(split[j]));
 				faceVertices.add(new Vertex(v.x, v.y, v.z));
 			}
			
 			
			polygons.add(new Polygon(faceVertices));
			
 			}
			catch(NumberFormatException e) {
				
				br.close();
				throw new WrongFileFormatException(e.getMessage());
			}
			currLine = br.readLine();
			
			
			
		}
		
		br.close();
		fr.close();
				
		return polygons;
		
	}
	
}
