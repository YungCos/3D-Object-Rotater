package assignment;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;

public class Mesh extends GraphicalObject{
	
	HashSet<Polygon> polygons;
	MeshReader reader;
	MeshWriter writer;
	

	@Override
	public int hashCode() {
		return Objects.hash(polygons);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mesh other = (Mesh) obj;
		return polygons.equals(other.polygons);
	}
	 
	void setReader (MeshReader mr) {
		reader = mr;
	}
	
	void setWriter (MeshWriter mw) {
		writer = mw;
	}
	
	void readFromFile(String fileName) throws IOException, WrongFileFormatException {
		
		polygons = reader.read(fileName);
		
	}
	
	void writeToFile(String fileName) throws IOException{
		
		writer.write(fileName, polygons);
	}

	@Override
	public void transform(double[][] matrix) {
		
		for (Polygon poly: polygons) {
			poly.transform(matrix);
		}
		
	}
	
}
