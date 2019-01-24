package game.objectTypes;

import java.util.*;

import game.common.ContentDatabase;
import game.gameData.BlockInfo;
import game.models.*;
import game.objectTypes.BlockFace.Side;
import game.renderEngine.*;

public class Chunk {

	public static final int X_SIZE = 16;
	public static final int Y_SIZE = 256;
	public static final int Z_SIZE = 16;

	public BlockInfo[] chunkInfo;
	public Mesh mesh;

	public void GenerateMesh(Loader loader) {
		
		ArrayList<Double> positions = new ArrayList<Double>();
		ArrayList<Double> textureCoords = new ArrayList<Double>();
		ArrayList<Double> normals = new ArrayList<Double>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		long millis = System.currentTimeMillis();
		
		chunkInfo = new BlockInfo[X_SIZE * Y_SIZE * Z_SIZE];
		BlockInfo grass = ContentDatabase.GetBlockInfo("Base:Grass_Block");
		for(int i = 0; i < chunkInfo.length; i++) {
			chunkInfo[i] = grass;
		}
		
		int facesGenerated = 0;

		for (int x = 0; x < X_SIZE; x++) {
			for (int y = 0; y < Y_SIZE; y++) {
				for (int z = 0; z < Z_SIZE; z++) {
					BlockInfo currentBlockData = chunkInfo[x + z * Z_SIZE + y * Y_SIZE];
					
					if (currentBlockData != ContentDatabase.AIR_BLOCK) {
						
						for(Side side : BlockFace.SideIntRef) {
							BlockFace face = GenerateFaceIfVisible(side, x, y, z, currentBlockData);
							if(face!=null) {
								AddFace(side, positions, indices, textureCoords, normals, face, facesGenerated);
								facesGenerated++;
							}
						}
					}
				}
			}
		}
		
		double[] posArray = positions.stream().mapToDouble(d -> d).toArray();
		int[] indicesArray = indices.stream().mapToInt(d -> d).toArray();
		double[] textureArray = textureCoords.stream().mapToDouble(d -> d).toArray();
		double[] normalsArray = normals.stream().mapToDouble(d -> d).toArray();
		
		if(mesh != null)
			mesh = loader.reloadToVAO(posArray, indicesArray, textureArray, normalsArray, mesh.getVaoID());
		else
			mesh = loader.loadToVAO(posArray, indicesArray, textureArray, normalsArray);
		System.out.println("Took " + (System.currentTimeMillis() - millis) + " milliseconds to generate the chunk mesh.");
	}
	
	private void AddFace(BlockFace.Side side, ArrayList<Double> positions, ArrayList<Integer> indices, ArrayList<Double> textureCoords, ArrayList<Double> normals, BlockFace face, int facesGenerated ) {
		double[] norm = side.getNormal();
		
		for (double f : face.positionData)
			positions.add(f);
		
		for(double f : norm)
			normals.add(f);
		for(double f : norm)
			normals.add(f);
		for(double f : norm)
			normals.add(f);
		for(double f : norm)
			normals.add(f);

		int indicesStartSize = facesGenerated * 4;
		
		for (int i : BlockFace.indices)
			indices.add(indicesStartSize + i);
		
		for (double f : face.textureCoords)
			textureCoords.add(f);
	}

	private BlockFace GenerateFaceIfVisible(BlockFace.Side side, int xPos, int yPos, int zPos, BlockInfo info) {
		if (
				(xPos == 0 && side == BlockFace.Side.West) || (xPos == X_SIZE -1 && side == BlockFace.Side.East) || 
				(zPos == 0 && side == BlockFace.Side.North) || (zPos == Z_SIZE - 1 && side == BlockFace.Side.South) ||
				(yPos == 0 && side == BlockFace.Side.Bottom) || (yPos == Y_SIZE - 1 && side == BlockFace.Side.Top) ) {
			return new BlockFace(side, xPos, yPos, zPos, info);
		} else {
			return null;
		}
	}

}
