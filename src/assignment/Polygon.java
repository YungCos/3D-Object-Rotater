package assignment;

import java.util.LinkedHashSet;
import java.util.Objects;

public class Polygon extends GraphicalObject{
	
	
	LinkedHashSet<Vertex> vertices;
	
	public Polygon(LinkedHashSet<Vertex> vertices) {
		this.vertices = vertices;
	}

	@Override
	public void transform(double[][] matrix) {
		
		for (Vertex point : vertices) {
			point.transform(matrix);
		}
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(vertices);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Polygon other = (Polygon) obj;
		return vertices.equals(other.vertices);
	}

}
