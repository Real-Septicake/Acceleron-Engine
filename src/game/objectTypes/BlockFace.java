package game.objectTypes;

import game.common.ContentDatabase;
import game.gameData.BlockInfo;

public class BlockFace {

	public static enum Side {
		North (0, new double[] { 0f, 0f, -1f }), 
		South (1, new double[] { 0f, 0f, 1f }), 
		East  (2, new double[] { 1f, 0f, 0f }), 
		West  (3, new double[] { -1f, 0f, 0f }),
		Top   (4, new double[] { 0f, 1f, 0f }), 
		Bottom(5, new double[] { 0f, -1f, 0f });

		private final double[] normal;
		private final int index;

		private Side(final int ind, final double[] norm) {
			this.normal = norm;
			this.index = ind;
		}

		public double[] getNormal() {
			return normal;
		}
		public int getID() {
			return index;
		}
	}
	
	public static final Side[] SideIntRef = new Side[] {
			Side.North, Side.South, Side.East, Side.West, Side.Top, Side.Bottom
	};
	
	public double[] positionData;
	public double[] normals;
	public double[] textureCoords;
	
	public static final int[] indices = new int[] {
			0, 1, 2, 
			2, 3, 0
	};

	public BlockFace(Side side, int xPos, int yPos, int zPos, BlockInfo block) {
		positionData = createPositionData(xPos, yPos, zPos, side);
		normals = side.getNormal();
		textureCoords = ContentDatabase.GetTextureCoords(block, side.getID());
	}

	public double[] createPositionData(int xPos, int yPos, int zPos, Side side) {
		switch (side) {

		case North:
			return new double[] {
					1 + xPos, 1 + yPos, -1 + zPos,
					1 + xPos, 0 + yPos, -1 + zPos,
					0 + xPos, 0 + yPos, -1 + zPos,
					0 + xPos, 1 + yPos, -1 + zPos
			};
		case South:
			return new double[] {
					0 + xPos, 1 + yPos, 0 + zPos,	
					0 + xPos, 0 + yPos, 0 + zPos,	
					1 + xPos, 0 + yPos, 0 + zPos,	
					1 + xPos, 1 + yPos, 0 + zPos
			};
		case East:
			return new double[] {
					1 + xPos, 1 + yPos, 0 + zPos,
					1 + xPos, 0 + yPos, 0 + zPos,
					1 + xPos, 0 + yPos, -1 + zPos,
					1 + xPos, 1 + yPos, -1 + zPos
			};
		case West:
			return new double[] {
					0 + xPos, 1 + yPos, -1 + zPos,	
					0 + xPos, 0 + yPos, -1 + zPos,	
					0 + xPos, 0 + yPos, 0 + zPos,	
					0 + xPos, 1 + yPos, 0 + zPos
			};
		case Top:
			return new double[] {
					0 + xPos, 1 + yPos, -1 + zPos,	
					0 + xPos, 1 + yPos, 0 + zPos,	
					1 + xPos, 1 + yPos, 0 + zPos,	
					1 + xPos, 1 + yPos, -1 + zPos
			};
		case Bottom:
			return new double[] {
					1 + xPos, 0 + yPos, -1 + zPos,
					1 + xPos, 0 + yPos, 0 + zPos,
					0 + xPos, 0 + yPos, 0 + zPos,
					0 + xPos, 0 + yPos, -1 + zPos
			};
			
		default:
			return null;

		}
	}
}
