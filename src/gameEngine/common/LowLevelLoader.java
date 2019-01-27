package gameEngine.common;

import java.io.*;
import java.nio.*;
import java.util.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.PNGDecoder;

import gameEngine.rendering.data.meshData.MeshLowLevel;

public class LowLevelLoader {

	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();

	private List<Integer> textures = new ArrayList<Integer>();

	public MeshLowLevel loadToVAO(double[] positions, int[] indices, double[] textureCoords, double[] normals) {
		int vaoID = createVAO();
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new MeshLowLevel(vaoID, indices.length);
	}
	
	public MeshLowLevel loadToVAO(double[] positions) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, 3, positions);
		unbindVAO();
		return new MeshLowLevel(vaoID, positions.length/3);
	}
	
	public MeshLowLevel reloadToVAO(double[] positions, int[] indices, double[] textureCoords, double[] normals, int meshID) {
		loadVAO(meshID);
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, positions);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		return new MeshLowLevel(meshID, indices.length);
	}

	public int loadTexture(String filepath) {
		try {
		  File file = new File("res/"+filepath+".png");
		  InputStream in = new FileInputStream(file);
		  PNGDecoder decoder = new PNGDecoder(in);
		  ByteBuffer buffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		  decoder.decode(buffer, decoder.getWidth() * 4, PNGDecoder.RGBA);
		  buffer.flip();
		  
		  int textureID = GL11.glGenTextures();
		  GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
		  // WRAP OR NOT TO WRAP, THAT IS THE QUESTION
		  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
		  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
		  
		  // ME DOEST SMOOTH OR SHARP? SHARP!
		  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		  GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		  
		  // MIP MAP!
		  GL30.glGenerateMipmap(textureID);
		  
		  // MAKE!
		  GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		  
		  GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		  return textureID;
		}
		catch (IOException e){
			return 0;
		}
	}
	
	public void clean() {
		for (int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}

		for (int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}

		for (int texture : textures) {
			GL15.glDeleteTextures(texture);
		}
	}

	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	private void loadVAO(int vaoID) {
		GL30.glBindVertexArray(vaoID);
	}

	private void storeDataInAttributeList(int attributeNumber, int size, double[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		DoubleBuffer buffer = storeDataInDoubleBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, size, GL11.GL_DOUBLE, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}

	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private DoubleBuffer storeDataInDoubleBuffer(double[] data) {
		DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
