package gameEngine.components;

import java.util.HashMap;

import org.joml.Vector3d;

public class GameObject {
	private static int nextID = 0;
	
	public Transform transform;
	public String name = "New GameObject";
	private int id;
	
	public int getId() { return id; }
	
	public Boolean equal(GameObject other) {
		return (id == other.getId());
	}
	
	@SuppressWarnings("rawtypes")
	private ComponentArray components = new ComponentArray<>();
	
	public GameObject(Vector3d pos, Vector3d rot, Vector3d sca) {
		transform = new Transform(pos, rot, sca);
		id = nextID;
		nextID++;
	}
	
	public <T extends ComponentBase> ComponentBase getComponent(Class<T> type) {
		return (ComponentBase) components.components.get(type);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ComponentBase> ComponentBase addComponent(Class<T> type) {
		
		if(components.components.get(type) == null) {
			
			try {
				ComponentBase comp = (ComponentBase) type.newInstance();
				comp.gameObject = this;
				comp.setup();
				components.components.put(type, comp);
				return getComponent(type);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Tried to add component to GameObject which already had one! CHECK!");
		return null;
	}
	
	private class ComponentArray <T extends ComponentBase>{
		public HashMap<Class<T>, ComponentBase> components = new HashMap<Class<T>, ComponentBase>();;
	}
	
	public void destroy() {
		ComponentBase[] comp = (ComponentBase[]) components.components.values().toArray();
		for (ComponentBase component : comp) {
			component.destroy();
		}
	}
}
