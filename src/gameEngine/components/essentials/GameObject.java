package gameEngine.components.essentials;

import java.util.*;

import org.joml.Vector3d;

import gameEngine.components.ComponentBase;
import gameEngine.debug.Debug;

public class GameObject {
	
	private static HashMap<Integer, GameObject> gameObjects = new HashMap<Integer, GameObject>();
	private static HashSet<GameObject> gmsToRemove = new HashSet<GameObject>();
	private static int nextID = 0;
	
	public Transform transform;
	public String name = "New GameObject";
	private int id;
	
	public int getId() { return id; }
	
	public Boolean equal(GameObject other) {
		return (id == other.getId());
	}
	
	public static GameObject find(int id) {
		return gameObjects.get(id);
	}
	
	public static GameObject find(String name) {
		for (GameObject gameObject : gameObjects.values()) {
			if(gameObject.name.equals(name))
				return gameObject;
		}
		
		return null;
	}
	
	public static void clearScene() {
		Collection<GameObject> gms = gameObjects.values();
		for (GameObject gameObject : gms) {
			gameObject.destroy();
		}
		clearOld();
	}
	
	public static void clearOld() {
		for (GameObject gameObject : gmsToRemove) {
			gameObjects.remove(gameObject.id);
			
		}
		gmsToRemove.clear();
	}
	
	public GameObject(Vector3d pos, Vector3d rot, Vector3d sca) {
		transform = new Transform(pos, rot, sca);
		id = nextID++;
		gameObjects.put(id, this);
	}
	
	private class ComponentArray <T extends ComponentBase>{
		public HashMap<Class<T>, ComponentBase> components = new HashMap<Class<T>, ComponentBase>();;
	}
	
	@SuppressWarnings("rawtypes")
	private ComponentArray components = new ComponentArray<>();
	
	public <T extends ComponentBase> ComponentBase getComponent(Class<T> type) {
		return (ComponentBase) components.components.get(type);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ComponentBase> ComponentBase addComponent(Class<T> type) {
		
		if(components.components.get(type) == null) {
			
			try {
				ComponentBase comp = type.getConstructor().newInstance();
				comp.gameObject = this;
				comp.setup();
				components.components.put(type, comp);
				return getComponent(type);
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		System.out.println("Tried to add component to GameObject which already had one! CHECK!");
		return null;
	}
	
	public void destroy() {
		@SuppressWarnings("unchecked")
		Collection<ComponentBase> comp = components.components.values();
		for (ComponentBase component : comp) {
			component.destroy();
		}
		gmsToRemove.add(this);
	}
}
