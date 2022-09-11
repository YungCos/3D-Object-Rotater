package assignment;

import java.util.Objects;

public class Vertex extends GraphicalObject{
	
	double x;
	double y;
	double z;
	
	public Vertex(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	

	@Override
	public void transform(double[][] matrix) {
				
		double newX = (matrix[0][0] * this.x) + (matrix[0][1] * this.y) + (matrix[0][2] * this.z);
		double newY = (matrix[1][0] * this.x) + (matrix[1][1] * this.y) + (matrix[1][2] * this.z);
		double newZ = (matrix[2][0] * this.x) + (matrix[2][1] * this.y) + (matrix[2][2] * this.z);
		
		this.x = newX;
		this.y = newY;
		this.z = newZ;
				
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		return other.x == this.x && other.y == this.y && other.z == this.z;
	}

	@Override
	public String toString() {
		return x + " " + y + " " + z;
	}
	
	

}
