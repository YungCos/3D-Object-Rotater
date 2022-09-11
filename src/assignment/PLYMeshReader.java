package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

public class PLYMeshReader implements MeshReader{

	@Override
	public HashSet<Polygon> read(String filename) throws IOException, WrongFileFormatException {
		
		HashSet<Polygon> polygons = new HashSet<Polygon>();
		
		FileReader fr = new FileReader(filename);
		BufferedReader br = new BufferedReader(fr);
		
		String currLine = br.readLine();
		
		List<Vertex> vertices = new ArrayList<Vertex>();
				
		int numVertex = -1;
		int numFaces = -1;
		String[] headerFormat = {
				"ply\\s*",
				"format\\s+ascii\\s+1.0\\s*",
				"",
				"property\\s+float32\\s+x\\s*",
				"property\\s+float32\\s+y\\s*",
				"property\\s+float32\\s+z\\s*",
				"",
				"property\\s+list\\s+uint8\\s+int32\\s+vertex_indices\\s*",
				"end_header\\s*"
		};
		
		try {
		
		for(int lineNum = 0; lineNum < 9; lineNum++) {
			
			if (lineNum == 2) {
				numVertex = Integer.parseInt(currLine.split("\\s+")[2]);
			}
			else if(lineNum == 6){
				numFaces = Integer.parseInt(currLine.split("\\s+")[2]);
			}
			
			else if (!Pattern.compile(headerFormat[lineNum]).matcher(currLine).matches()){
				br.close();
				throw new WrongFileFormatException("Incorrect header");
			}
			
			currLine = br.readLine();
			
			if (currLine == null) {
				br.close();
				throw new WrongFileFormatException("Unexpected EOF");
			}
		}
		
		}
		
		catch(NumberFormatException e) {
			
			br.close();
			throw new WrongFileFormatException(e.getMessage());
		}
		
		if (numVertex < 0 || numFaces < 0) {
			br.close();
			throw new WrongFileFormatException("Number of faces/vertices not declared or invalid");
		}
		
		String[] split;
				
		double x, y, z;
		
		for (int i = 0; i < numVertex; i++) {			
			
			split = currLine.split("\\s+");
			
			if (split.length != 3) {
				br.close();
				throw new WrongFileFormatException("Vertex does not have 3 entries");
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
			
			if (currLine == null) {
				br.close();
				throw new WrongFileFormatException("Too many faces inputted");
			}
			
			split = currLine.split("\\s+");
			
			try {
				numVertexInPolygon = Integer.parseInt(split[0]);
			}
			catch(NumberFormatException e) {
				
				br.close();
				throw new WrongFileFormatException(e.getMessage());
			}
			
			if (split.length != numVertexInPolygon + 1) {
				br.close();
				throw new WrongFileFormatException("Invalid number of entries");
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