package assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OFFMeshWriter implements MeshWriter{

	@Override
	public void write(String filename, HashSet<Polygon> polygons) throws IOException {
		 File newOBJ = new File(filename);
		 newOBJ.createNewFile();
		 
		 FileWriter newMesh = new FileWriter(filename);
		 
		 List<Vertex> vertices = new ArrayList<Vertex>();
		 
		 for (Polygon currPoly : polygons) {
			 for (Vertex currVertex : currPoly.vertices) {
				 if (!vertices.contains(currVertex)) {
					 vertices.add(currVertex);
				 }
			 }
		 }
		 
		 newMesh.write("OFF\n");
		 newMesh.write((vertices.size()) + " " + (polygons.size()) + " 0\n");
		 
		 String currLine;
		 for (Vertex curr : vertices) {
			 currLine = curr.x + " " + curr.y + " " + curr.z;
			 newMesh.write(currLine + "\n");
		 }
		 for (Polygon curr : polygons) {
			 
			 currLine = String.valueOf(curr.vertices.size());
			 
			 for (Vertex currVertex : curr.vertices) {
				 currLine += " " + (vertices.indexOf(currVertex));
			 }
			 
			 newMesh.write(currLine + " 220 220 220\n");
			 
		 }
		 
		 newMesh.close();
		 
		
	}

	
	

}
