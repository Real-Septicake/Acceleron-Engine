package game.renderEngine;

import java.io.*;
import java.util.*;

import org.joml.*;

import game.models.Mesh;

public class OBJLoader {
	
	public static Mesh loadObjModel(String fileName, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+fileName+".obj"));
		}
		catch(FileNotFoundException e) {
			System.err.println("Couldn't find model file!");
			e.printStackTrace();
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		List<Vector3d> vertices = new ArrayList<Vector3d>();
		List<Vector2d> textures = new ArrayList<Vector2d>();
		List<Vector3d> normals = new ArrayList<Vector3d>();
		List<Integer> indices = new ArrayList<Integer>();
		double[] verticesArray = null;
		double[] normalsArray = null;
		double[] textureArray = null;
		int[] indicesArray = null;
		
		try {
			while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if(line.startsWith("v ")) {
					Vector3d vertex = new Vector3d(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				} else if(line.startsWith("vt ")) {
					Vector2d textureCoord = new Vector2d(Float.parseFloat(currentLine[1]), 1 - Float.parseFloat(currentLine[2]));
					textures.add(textureCoord);
				} else if(line.startsWith("vn ")) {
					Vector3d normal = new Vector3d(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if(line.startsWith("f ")) {
					textureArray = new double[vertices.size() * 2];
					normalsArray = new double[vertices.size() * 3];
					break;
				}
			}
			
			while(line != null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			
		}
		
		verticesArray = new double[vertices.size() * 3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3d vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i=0; i<indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		return loader.loadToVAO(verticesArray, indicesArray, textureArray, normalsArray);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2d> textures, List<Vector3d> normals, double[] textureArray, double[] normalsArray)
	{
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2d currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = currentTex.y;
		Vector3d currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer*3 + 2] = currentNorm.z;
	}
}
