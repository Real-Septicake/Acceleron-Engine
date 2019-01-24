package game.common;

import java.util.HashMap;

import game.gameData.*;
import game.gameData.blocks.*;

public class ContentDatabase {
	private static HashMap<String, BlockInfo> blockData = new HashMap<String, BlockInfo>();
	private static final int textureCount = 16;
	public static BlockInfo AIR_BLOCK;
	
	
	public static void LoadContent() {
		AIR_BLOCK = new Block_Air();
		
		BlockInfo newBlock = new Block_Grass();
		blockData.put(newBlock.getInternalName(), newBlock);
		
		newBlock = new Block_Dirt();
		blockData.put(newBlock.getInternalName(), newBlock);
		
		newBlock = new Block_Stone();
		blockData.put(newBlock.getInternalName(), newBlock);
	}
	
	public static BlockInfo GetBlockInfo(String internalName) {
		return blockData.get(internalName);
	}
	
	//If there is 0 strings, there are no textures to load.
	//If there is 1 string, the whole block is 1 texture
	//If there is 2 textures, the sides are the first, and the second is the top.
	//If there is 3 textures, the sides are the first, the second is the top, and the third is the bottom.
	//If there is 6 textures, the textures are north, south, east, west, top, and bottom respectively.
	//Duplicates will be ignored and the original will be given back.
	public static int[] LoadTextures(String[] textureLocations) {
		return new int[6];
	}
	
	public static double[] GetTextureCoords(BlockInfo info, int side) {
		int column = info.getTextureIDs()[side] % textureCount;
		int row = textureCount -info.getTextureIDs()[side] / textureCount;
		
		double[] output = new double[8];
		output[0] = column / 16d;
		output[1] = row / 16d;
		
		output[2] = column / 16d;
		output[3] = (row + 1) / 16d;
		
		output[4] = (column + 1) / 16d;
		output[5] = (row + 1) / 16d;
		
		output[6] = (column + 1) / 16d;
		output[7] = row / 16d;
		
		return output;
	}
	
}
