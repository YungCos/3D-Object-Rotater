package assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;

import org.junit.jupiter.api.Test;

class AssignmentTests {

	@Test
	void testVertexTransform() {
		
		double[][] matrix1 = {
				{1, 0, 0},
				{0, 1, 0},
				{0, 0, 1}
		};
		
		double[][] matrix2 = {
				{1, 2, 3},
				{4, 5, 6},
				{7, 8, 9}
		};
		
		Vertex v1 = new Vertex(1, 1, 1);
		Vertex v2 = new Vertex(1, 1, 1);
		
		v1.transform(matrix1);
		
		assertEquals(v1, v2);
		
		v1.transform(matrix2);
		v2 = new Vertex(6, 15, 24);
		
		assertEquals(v1, v2);
		
	}
	
	@Test
	void testVertexToString() {
		Vertex v = new Vertex(1, 2, 3);	
		assertEquals(v.toString(), "1.0 2.0 3.0");
	}
	
	@Test
	void testVertexEquals() {
		Vertex v1 = new Vertex(1, 2, 3);
		Vertex v2 = new Vertex(3, 2, 1);
		Vertex v3 = new Vertex(1, 2, 3);
		
		assertEquals(v1, v1);
		assertNotEquals(v1, null);
		assertNotEquals(v1, 0);
		assertNotEquals(v1, v2);
		assertEquals(v1, v3);
	}
	
	@Test
	void testVertexHashCode() {
		Vertex v1 = new Vertex(1, 2, 3);
		Vertex v2 = new Vertex(1, 2, 3);
		
		assertEquals(v1.hashCode(), v2.hashCode());
	}
	
	@Test
	void testPolygon() {
		
		double[][] matrix = {
				{1, 0, 0},
				{0, 1, 0},
				{0, 0, 1}
		};
		
		Vertex a1 = new Vertex(1, 0, 0);
		Vertex a2 = new Vertex(0, 1, 0);
		Vertex a3 = new Vertex(0, 0, 1);
		
		LinkedHashSet<Vertex> a = new LinkedHashSet<Vertex>();
		
		a.add(a1);
		a.add(a2);
		a.add(a3);
		
		Vertex b1 = new Vertex(1, 0, 0);
		Vertex b2 = new Vertex(0, 1, 0);
		Vertex b3 = new Vertex(0, 0, 1);
		
		LinkedHashSet<Vertex> b = new LinkedHashSet<Vertex>();
		
		b.add(b1);
		b.add(b2);
		b.add(b3);
		
		Polygon aPoly = new Polygon(a);
		Polygon bPoly = new Polygon(b);
		
		aPoly.transform(matrix);
		
		
		assertEquals(aPoly, aPoly);
		assertNotEquals(aPoly, null);
		assertNotEquals(aPoly, 1);
				
		assertEquals(aPoly, bPoly);	
		
		
	}
	
	@Test
	void testGraphicalObject() {
		
		Vertex v1 = new Vertex(1, 2, 3);
		Vertex v2 = new Vertex(1, 2, 3);
		Vertex v3 = new Vertex(1, 2, 3);
		Vertex w = new Vertex(1, 2, 3);

		v1.rotateXAxis(0);
		v2.rotateYAxis(0);
		v3.rotateZAxis(0);
		
		assertEquals(v1, w);
		assertEquals(v2, w);
		assertEquals(v3, w);
		
		
	}
	
	@Test
	void testMesh() {
		
		double[][] matrix = {
				{1, 0, 0},
				{0, 1, 0},
				{0, 0, 1}
		};
		
		Vertex a1 = new Vertex(1, 2, 3);
		Vertex a2 = new Vertex(4, 5, 6);
		Vertex a3 = new Vertex(7, 8, 9);
		
		LinkedHashSet<Vertex> a = new LinkedHashSet<Vertex>();
		
		a.add(a1);
		a.add(a2);
		a.add(a3);
		
		Vertex b1 = new Vertex(1, 2, 3);
		Vertex b2 = new Vertex(4, 5, 6);
		Vertex b3 = new Vertex(7, 8, 9);
		
		LinkedHashSet<Vertex> b = new LinkedHashSet<Vertex>();
		
		b.add(b1);
		b.add(b2);
		b.add(b3);
		
		Polygon aPoly = new Polygon(a);
		Polygon bPoly = new Polygon(b);
		
		HashSet<Polygon> aMeshPolys = new HashSet<Polygon>();
		HashSet<Polygon> bMeshPolys = new HashSet<Polygon>();
		
		aMeshPolys.add(aPoly);
		bMeshPolys.add(bPoly);
		
		Mesh aMesh = new Mesh();
		Mesh bMesh = new Mesh();
		
		aMesh.polygons = aMeshPolys;
		bMesh.polygons = bMeshPolys;
		
		aMesh.setReader(new OBJMeshReader());
		aMesh.setWriter(new OBJMeshWriter());
		
		assertTrue(aMesh.writer instanceof OBJMeshWriter);
		assertTrue(aMesh.reader instanceof OBJMeshReader);
		
		assertEquals(aMesh, aMesh);
		assertNotEquals(aMesh, null);
		assertNotEquals(aMesh, 1);
		assertEquals(aMesh, bMesh);
		assertEquals(aMesh.hashCode(), bMesh.hashCode());
		
		aMesh.transform(matrix);
		
		assertEquals(aMesh, bMesh);
		
	}
	
	@Test
	void testOBJMeshReader () throws IOException {
		
		Mesh mesh = new Mesh(); 
		mesh.setReader(new OBJMeshReader()); 
		
		
		try {
			mesh.readFromFile("src/Objects/validOBJ.obj");
		}
		catch (WrongFileFormatException e) {
			fail(e.message);
		}	
		
		
		
		try {
			mesh.readFromFile("src/Objects/invalidHeader.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "First line is not a vertex");
		}		
		
		
		
		try {
			mesh.readFromFile("src/Objects/invalidIndex.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Index 7 out of bounds for length 4");
		}
		
		
		
		try {
			mesh.readFromFile("src/Objects/invalidVertex.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Vertex does not contain 3 numbers");
		}
		
		

		try {
			mesh.readFromFile("src/Objects/invalidNumbers.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "multiple points");
		}
		
		
		
		try {
			mesh.readFromFile("src/Objects/invalidVertexPlacement.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Face does not start with f");
		}
		
		try {
			mesh.readFromFile("src/Objects/noFaces.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "No Faces Given");
		}
		
		
		try {
			mesh.readFromFile("src/Objects/invalidFaceNum.obj");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"q\"");
		}
		
	}

	@Test
	void testOFFMeshReader () throws IOException {
		
		Mesh mesh = new Mesh(); 
		mesh.setReader(new OFFMeshReader()); 
		
		try {
			mesh.readFromFile("src/Objects/validOFF.off");
		}
		catch (WrongFileFormatException e) {
			fail(e.message);
		}
		
		
		try {
			mesh.readFromFile("src/Objects/invalidHeader.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Invalid Header");
		}
		
		
		try {
			mesh.readFromFile("src/Objects/invalidVertex.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"q\"");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidEntries.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Incorrect number of entries in face");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidSubheader.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Subheader does not have 3 entries");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidSubheader2.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"q\"");
		}
		
		
		try {
			mesh.readFromFile("src/Objects/invalidVertexEntries.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Vertex does not contain 3 values");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidNumberFace.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"q\"");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidNumberFace2.off");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"q\"");
		}
		
	}
	
	@Test
	void testPLYMeshReader () throws IOException {
			
		Mesh mesh = new Mesh(); 
		mesh.setReader(new PLYMeshReader()); 
			
		try {
			mesh.readFromFile("src/Objects/validPLY.ply");
		}
		catch (WrongFileFormatException e) {
			fail(e.message);
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidHeader.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Incorrect header");
		}
		
		try {
			mesh.readFromFile("src/Objects/tooManyVertex.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Vertex does not have 3 entries");
		}
		
		try {
			mesh.readFromFile("src/Objects/tooManyFaces.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Too many faces inputted");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidFaceEntries.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Invalid number of entries");
		}
		
		try {
			mesh.readFromFile("src/Objects/shortHeader.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Unexpected EOF");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidAmount.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Number of faces/vertices not declared or invalid");
		}
		
		
		try {
			mesh.readFromFile("src/Objects/invalidAmount2.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"q\"");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidVertex.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"qh\"");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidVertex2.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "Vertex does not have 3 entries");
		}
		
		
		try {
			mesh.readFromFile("src/Objects/invalidFace.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"p\"");
		}
		
		try {
			mesh.readFromFile("src/Objects/invalidFace2.ply");
			fail("No error was detected");
		}
		catch (WrongFileFormatException e) {
			assertEquals(e.message, "For input string: \"ph\"");
		}
		
	}
	
	@Test
	void testWriters() throws IOException, WrongFileFormatException {
		Mesh mesh = new Mesh();
		
		mesh.setReader(new OBJMeshReader()); 
		mesh.readFromFile("src/Objects/validOBJ.obj");
				
		mesh.setWriter(new OBJMeshWriter());
		mesh.writeToFile("src/TestOutputs/OBJOutput.obj");
		
		Mesh newOBJmesh = new Mesh();
		
		newOBJmesh.setReader(new OBJMeshReader()); 
		newOBJmesh.readFromFile("src/TestOutputs/OBJOutput.obj");
		
		assertEquals(mesh, newOBJmesh);
		
		mesh.setWriter(new OFFMeshWriter());
		mesh.writeToFile("src/TestOutputs/OFFOutput.off");
		
		Mesh newOFFmesh = new Mesh();
		
		newOFFmesh.setReader(new OFFMeshReader()); 
		newOFFmesh.readFromFile("src/TestOutputs/OFFOutput.off");
		
		assertEquals(mesh, newOFFmesh);
		
		mesh.setWriter(new PLYMeshWriter());
		mesh.writeToFile("src/TestOutputs/PLYOutput.ply");
		
		Mesh newPLYmesh = new Mesh();
		
		newPLYmesh.setReader(new PLYMeshReader()); 
		newPLYmesh.readFromFile("src/TestOutputs/PLYOutput.ply");
		
		assertEquals(mesh, newPLYmesh);
		
		
	}

}