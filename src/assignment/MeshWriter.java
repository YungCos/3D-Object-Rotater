package assignment;

import java.io.IOException;
import java.util.HashSet;

public interface MeshWriter {
	
	void write(String filename, HashSet<Polygon> polygons) throws IOException;

}
