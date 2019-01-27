package gameEngine.rendering;

import java.util.*;

import org.joml.Vector3d;

import gameEngine.rendering.gui.GuiRendererHandler;
import gameEngine.rendering.meshData.*;
import gameEngine.rendering.shaders.*;
import gameEngine.common.LowLevelLoader;
import gameEngine.components.Camera;
import gameEngine.components.Light;
import gameEngine.components.MeshRenderer;

public class MasterRenderer {
	
	//Rendering information
	private static StaticShader shader = new StaticShader();
	private static RendererHandler renderer = new RendererHandler(shader);
	private static GuiRendererHandler guiRenderer;

	
	//Objects to render
	private static Map<TexturedMeshLowLevel, List<RenderObjectInfo>> entities = new HashMap<TexturedMeshLowLevel, List<RenderObjectInfo>>();
	
	//Objects which are rendered every update
	private static List<MeshRenderer> meshes = new LinkedList<MeshRenderer>();
	
	
	//The lights used when doing lighting, first 8 are always used
	private static List<Light> lights = new LinkedList<Light>();
	
	//The camera that is used to render
	private static Camera cam;
	
	public MasterRenderer(LowLevelLoader loader) {
		
		guiRenderer = new GuiRendererHandler(loader);
		
	}
	
	
	//Method called to start the rendering process
	public static void render() {
		if(cam != null) {
			renderer.clear();
			shader.Start();
			shader.loadLight(lights);
			shader.loadViewMatrix(cam);
			for (MeshRenderer meshRenderer : meshes) {
				addEntity(meshRenderer);
			}
			renderer.render(entities, cam);
			shader.Stop();
			entities.clear();
		}
		else {
			System.out.println("No camera!");
		}
		
		guiRenderer.renderUI();
	}
	
	//Method called to prepare the known mesh renderers for rendering
	private static void addEntity(MeshRenderer rend) {
		
		TexturedMeshLowLevel entityMesh = rend.mesh.getTexturedMesh();
		
		List<RenderObjectInfo> batch = entities.get(entityMesh);
		
		if (batch != null) {
			
			batch.add(new RenderObjectInfo(rend.gameObject.transform, rend.mesh.getAtlasLocation()));
		}
		else {
			
			batch = new ArrayList<RenderObjectInfo>();
			
			batch.add(new RenderObjectInfo(rend.gameObject.transform, rend.mesh.getAtlasLocation()));
		}
		
		entities.put(entityMesh, batch);
	}
	
	
	//Method called by mesh renderers to register themselves
	public static void addRenderer(MeshRenderer rend) {
		meshes.add(rend);
	}
	
	
	//Method called by mesh renderers to unregister themselves
	public static void removeRenderer(MeshRenderer rend) {
		meshes.remove(rend);
	}
	
	
	//Method called by lights to register themselves
	public static void addLight(Light light) {
		lights.add(light);
	}
	
	
	//Method called by lights to unregister themselves
	public static void removeLight(Light light) {
		lights.remove(light);
	}
	
	
	//Method called by a camera to register itself
	public static void setCamera(Camera camera) {
		cam = camera;
	}
	
	
	//Method called by a camera to unregister itself
	public static void removeCamera() {
		cam = null;
	}
	
	
	//A method you can call to render a complete mesh on demand
	public static void drawMesh(CompleteMesh entityMesh, Vector3d position, Vector3d rotation, Vector3d scale) {
		
		List<RenderObjectInfo> batch = entities.get(entityMesh.getTexturedMesh());
		
		if (batch != null) {
			
			batch.add(new RenderObjectInfo(position, rotation, scale));
		}
		else {
			
			batch = new ArrayList<RenderObjectInfo>();
			
			batch.add(new RenderObjectInfo(position, rotation, scale, entityMesh.getAtlasLocation()));
		}
		
		entities.put(entityMesh.getTexturedMesh(), batch);
	}
	
	//A method you can call to render a complete mesh on demand
	public static void drawMesh(TexturedMeshLowLevel entityMesh, Vector3d position, Vector3d rotation, Vector3d scale) {
		
		List<RenderObjectInfo> batch = entities.get(entityMesh);
		
		if (batch != null) {
			
			batch.add(new RenderObjectInfo(position, rotation, scale));
		}
		else {
			
			batch = new ArrayList<RenderObjectInfo>();
			
			batch.add(new RenderObjectInfo(position, rotation, scale));
		}
		
		entities.put(entityMesh, batch);
	}
	
	//A method you can call to render a complete mesh on demand
	public static void drawMesh(TexturedMeshLowLevel entityMesh, Vector3d position, Vector3d rotation, Vector3d scale, int textureAtlasLocation) {
		
		List<RenderObjectInfo> batch = entities.get(entityMesh);
		
		if (batch != null) {
			
			batch.add(new RenderObjectInfo(position, rotation, scale, textureAtlasLocation));
		}
		else {
			
			batch = new ArrayList<RenderObjectInfo>();
			
			batch.add(new RenderObjectInfo(position, rotation, scale));
		}
		
		entities.put(entityMesh, batch);
	}

	
	//Method used to clean up the master renderer
	public static void clean() {
		entities.clear();
		shader.clean();
		guiRenderer.cleanUp();
	}

}
