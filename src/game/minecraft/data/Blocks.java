package game.minecraft.data;

public enum Blocks {
	Air("Air", "Base", true, BlockType.Empty, -1),
	Grass("Grass", "Base", false, BlockType.Cube, 0),
	Dirt("Dirt", "Base", false, BlockType.Cube, 1),
	Stone("Stone", "Base", false, BlockType.Cube, 2);
	
	private String name;
	private String mod;
	private boolean transparent;
	private BlockType blockType;
	private int textureAtlasLocation;
	
	public String getName() {
		return name;
	}
	
	public String getMod() {
		return mod;
	}
	
	public boolean isTransparent() {
		return transparent;
	}
	
	public BlockType getBlockType() {
		return blockType;
	}
	
	public int getTextureAtlasLocation() {
		return textureAtlasLocation;
	}
	
	private Blocks(String name, String mod, boolean transparent, BlockType blockType, int textureAtlasLocation) {
		this.name = name;
		this.mod = mod;
		this.transparent = transparent;
		this.blockType = blockType;
		this.textureAtlasLocation = textureAtlasLocation;
	}
}
