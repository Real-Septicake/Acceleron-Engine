package game.topDownFighter.scripts;

public enum TileState {
	White(0, "White"),
	Black(1, "Black"),
	Wall(2, "Wall"),
	Empty(3, "Nothing");
	
	private int textureAtlasLocation;
	private String name;
	
	public int getTextureAtlasLocation() {
		return textureAtlasLocation;
	}
	
	public String getTileName() {
		return name;
	}
	
	private TileState(int textureAtlasLocation, String name) {
		this.textureAtlasLocation = textureAtlasLocation;
		this.name = name;
	}
}
