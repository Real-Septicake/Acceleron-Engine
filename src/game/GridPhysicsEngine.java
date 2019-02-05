package game;

import java.util.HashSet;

import org.joml.Vector3d;

import gameEngine.components.scripts.Script;

public class GridPhysicsEngine {

    private HashSet<Player> players = new HashSet<Player>();
    private Grid grid;
    private HashSet<Bullet> bullets = new HashSet<Bullet>();
    
    /* TODO LIST:
     * Remove above variables, replace with arrays
     * Do checks for possible tile collisions (9 tiles for player, 2 for bullets (current tile, next tile)) instead of checking all tiles
     * Allow physics rewinding by saving states
     * Have all players store a grid location to simplify checks if it is within a tile for things like fast collision checking
     * ? Ignore grid entirely and do collidable to collidable collisions?
foreach bullet
	Get surrounding tiles
		If you can collide with them (Black tile, white tile, grey tile)
			Check if they are able to collide with you (Overlapping)
				Resolve Collision
			
foreach player
	if the tile you are on was changed by a bullet this frame die
	else Get surrounding tiles
		If you can collide with them (Black tile, white tile, grey tile)
			Check if they are able to collide with you (Overlapping)
				Resolve Collision
     */
	
    private static Player[] playerArray;
    private static Bullet[] bulletArray;
    public void runUpdate() {
    	for (Bullet bullet : bullets) {
			bullet.position = bullet.position.add(bullet.direction.getDirection().mul(bullet.movementSpeed));
		}
    	
    	playerArray = new Player[players.size()];
    	bulletArray = new Bullet[bullets.size()];
    	
    	int index = 0;
    	for (Player player : players) {
    		playerArray[index] = player;
			index++;
		}
    	
    	index = 0;
    	for (Bullet bullet : bullets) {
			bulletArray[index] = bullet;
		}
    	
    	
    }
    
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	public void removeBullet(Bullet bullet) {
		bullets.remove(bullet);
	}
	
	public void setup(Grid grid) {
		this.grid = grid;
	}
	
	//Is a player and a tile overlapping?
    private static boolean overlapPlayerTile(Player player, Vector3d tile)
    {
        if (player.position.x + 0.25 < tile.x - 0.5) return false;
        if (player.position.x - 0.25 > tile.x + 0.5) return false;
        if (player.position.y + 0.25 < tile.y - 0.5) return false;
        if (player.position.y - 0.25 > tile.y + 0.5) return false;

        return true;
    }

    //Is a player and a bullet overlapping?
	private static boolean overlapPlayerBullet(Player player, Vector3d bullet)
    {
        if (bullet.x > player.position.x + 0.25) return false;
        if (bullet.x < player.position.x - 0.25) return false;
        if (bullet.y > player.position.y + 0.25) return false;
        if (bullet.y < player.position.y - 0.25) return false;

        return true;
    }

    //Is a tile and a bullet overlapping?
	private static boolean overlapTileBullet(Vector3d tile, Vector3d bullet)
    {
        if (bullet.x > tile.x + 0.5) return false;
        if (bullet.x < tile.x - 0.5) return false;
        if (bullet.y > tile.y + 0.5) return false;
        if (bullet.y < tile.y - 0.5) return false;

        return true;
    }

    private static double resolveX(Player player, Vector3d tile)
    {
        double xOverlap1 = (player.position.x - 0.25) - (tile.x + 0.5);
        double xOverlap2 = (player.position.x + 0.25) - (tile.x - 0.5);

        double xOverlap = Math.abs(xOverlap1) < Math.abs(xOverlap2) ? xOverlap1 : xOverlap2;

        double yOverlap1 = (player.position.y - 0.25) - (tile.y + 0.5);
        double yOverlap2 = (player.position.y + 0.25) - (tile.y - 0.5);

        double yOverlap = Math.abs(yOverlap1) < Math.abs(yOverlap2) ? yOverlap1 : yOverlap2;

        return Math.abs(xOverlap) < Math.abs(yOverlap) ? xOverlap : 0;
    }

    private static double resolveY(Player player, Vector3d tile)
    {
        double xOverlap1 = (player.position.x - 0.25) - (tile.x + 0.5);
        double xOverlap2 = (player.position.x + 0.25) - (tile.x - 0.5);

        double xOverlap = Math.abs(xOverlap1) < Math.abs(xOverlap2) ? xOverlap1 : xOverlap2;

        double yOverlap1 = (player.position.y - 0.25) - (tile.y + 0.5);
        double yOverlap2 = (player.position.y + 0.25) - (tile.y - 0.5);

        double yOverlap = Math.abs(yOverlap1) < Math.abs(yOverlap2) ? yOverlap1 : yOverlap2;

        return Math.abs(xOverlap) > Math.abs(yOverlap) ? yOverlap : 0;
    }
}
