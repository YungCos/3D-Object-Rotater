package assignment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class PLYMeshWriter implements MeshWriter{

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
		 
		 newMesh.write("ply\n");
		 newMesh.write("format ascii 1.0\n");
		 newMesh.write("element vertex " + vertices.size() + "\n");
		 newMesh.write("property float32 x\n");
		 newMesh.write("property float32 y\n");
		 newMesh.write("property float32 z\n");
		 newMesh.write("element face " + polygons.size() + "\n");
		 newMesh.write("property list uint8 int32 vertex_indices\n");
		 newMesh.write("end_header\n" );
		 
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
			 
			 newMesh.write(currLine + "\n");
			 
		 }
		 
		 newMesh.close();
		 
		
	}

	
	

}
