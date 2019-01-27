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
	private static StaticShader shader = new StaticShader();
	private static RendererHandler renderer = new RendererHandler(shader);
	private static GuiRendererHandler guiRenderer;

	private static Map<TexturedMeshLowLevel, List<RenderObjectInfo>> entities = new HashMap<TexturedMeshLowLevel, List<RenderObjectInfo>>();
	private static List<MeshRenderer> meshes = new LinkedList<MeshRenderer>();
	
	private static List<Light> lights = new LinkedList<Light>();
	private static Camera cam;
	
	public MasterRenderer(LowLevelLoader loader) {
		guiRenderer = new GuiRendererHandler(loader);
	}
	
	public static void render() {
		if(cam != null) {
			renderer.clear();
			shader.Start();
			shader.LoadLight(lights);
			shader.LoadViewMatrix(cam);
			for (MeshRenderer meshRenderer : meshes) {
				addEntity(meshRenderer);
			}
			renderer.render(entities, cam);
			shader.Stop();
			entities.clear();
		}
		else {
			//System.out.println("No camera!");
		}
		
		guiRenderer.renderUI();
	}
	
	private static void addEntity(MeshRenderer rend) {
		TexturedMeshLowLevel entityMesh = rend.mesh;
		List<RenderObjectInfo> batch = entities.get(entityMesh);
		if (batch != null) {
			batch.add(new RenderObjectInfo(rend.gameObject.transform));
		}
		else {
			batch = new ArrayList<RenderObjectInfo>();
			batch.add(new RenderObjectInfo(rend.gameObject.transform));
		}
		
		entities.put(entityMesh, batch);
	}
	
	public static void addRenderer(MeshRenderer rend) {
		meshes.add(rend);
	}
	
	public static void removeRenderer(MeshRenderer rend) {
		meshes.remove(rend);
	}
	
	public static void addLight(Light light) {
		lights.add(light);
	}
	
	public static void removeLight(Light light) {
		lights.remove(light);
	}
	
	public static void setCamera(Camera camera) {
		cam = camera;
	}
	
	public static void removeCamera() {
		cam = null;
	}
	
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

	public static void clean() {
		entities.clear();
		shader.clean();
		guiRenderer.cleanUp();
	}

}
