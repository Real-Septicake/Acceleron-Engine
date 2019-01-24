package game.gameData;

public abstract class BlockInfo {
	
	public abstract String getName();
	
	public abstract String getInternalName();
	
	public abstract boolean getTransparent();
	
	public abstract int[] getTextureIDs();
}
