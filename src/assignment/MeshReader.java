package assignment;

import java.io.IOException;
import java.util.HashSet;

public interface MeshReader{

	HashSet<Polygon> read (String filename) throws WrongFileFormatException, IOException;
	
}
