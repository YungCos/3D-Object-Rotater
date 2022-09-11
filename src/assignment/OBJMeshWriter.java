package assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class OBJMeshWriter implements MeshWriter{

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
		 String currLine;
		 for (Vertex curr : vertices) {
			 currLine = "v " + curr.x + " " + curr.y + " " + curr.z;
			 newMesh.write(currLine + "\n");
		 }
		 
		 
		 for (Polygon curr : polygons) {
			 
			 currLine = "f";
			 
			 for (Vertex currVertex : curr.vertices) {
				 currLine += " " + (vertices.indexOf(currVertex) + 1);
			 }
			 
			 newMesh.write(currLine + "\n");
			 
		 }
		 
		 
		 newMesh.close();
		 
		
	}

}
