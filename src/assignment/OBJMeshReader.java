package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class OBJMeshReader implements MeshReader{
	

	@Override
	public HashSet<Polygon> read(String filename) throws WrongFileFormatException, IOException {
		
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		
		FileReader fr;
		fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		
		String currLine;
		
		currLine = br.readLine();
		
		if (currLine == null || currLine.charAt(0) != 'v') {
			br.close();
			throw new WrongFileFormatException("First line is not a vertex");
		}
		
		List<Vertex> vertices = new ArrayList<Vertex>();
		
		String[] split;
		
		while (currLine.charAt(0) == 'v') {
			
			
			split = currLine.split("\\s+");
			
			if (split.length != 4) {
				br.close();
				throw new WrongFileFormatException("Vertex does not contain 3 numbers");
			}
			
			try {
			
				double x = Double.parseDouble(split[1]);
				double y = Double.parseDouble(split[2]);
				double z = Double.parseDouble(split[3]);
				vertices.add(new Vertex(x, y, z));
			
			}
			
			catch(NumberFormatException e) {
				
				br.close();
				throw new WrongFileFormatException(e.getMessage());
			}
			
			currLine = br.readLine();
			
			if (currLine == null) {
				br.close();
				throw new WrongFileFormatException("No Faces Given");
			}
		}
		
		LinkedHashSet<Vertex> faceVertices;
		Vertex v;
		
		while (currLine != null) {
			
			split = currLine.split("\\s+");
			
			if (split == null || split[0].charAt(0) != 'f') {
				br.close();
				throw new WrongFileFormatException("Face does not start with f");
			}
			
 			faceVertices = new LinkedHashSet<Vertex>();
 			 	 		
 			for (int i = 1; i < split.length; i++) {
 				
 				
 				
 				try {
 					
 	 				v = vertices.get(Integer.parseInt(split[i]) - 1);
 	 				faceVertices.add(new Vertex(v.x, v.y, v.z));
 	 				
 					
 				}
 				catch(NumberFormatException e) {
 					br.close();
 					throw new WrongFileFormatException(e.getMessage()); 					
 				}
 				catch(IndexOutOfBoundsException e) {
 					br.close();
 					throw new WrongFileFormatException(e.getMessage()); 	
 				}

 			}
 			
 			polygons.add(new Polygon(faceVertices));
			
			currLine = br.readLine();
			
		}
		
		br.close();
		fr.close();
				
		return polygons;
		
	}

}
