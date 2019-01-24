package game.gameData.blocks;

import game.gameData.BlockInfo;

public class Block_Grass extends BlockInfo {
	
	private final static String name = "Grass";
	private final static String internalName = "Base:Grass_Block";
	//private final static String[] textureLocations = new String[]{};
	private final static int[] textureIDs = new int[] { 2, 2, 2, 2, 1, 3 };
	
	
	public Block_Grass() {
		//textureIDs = ContentDatabase.LoadTextures(textureLocations);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInternalName() {
		return internalName;
	}

	@Override
	public boolean getTransparent() {
		return false;
	}
	
	@Override
	public int[] getTextureIDs() {
		return textureIDs;
	}

}
