package game.renderEngine;

import java.util.*;

import game.models.*;
import game.shaders.*;
import gameEngine.components.Camera;
import gameEngine.components.Entity;
import gameEngine.components.Light;

public class MasterRenderer {
	private StaticShader shader = new StaticShader();
	private Renderer renderer = new Renderer(shader);

	private Map<TexturedMesh, List<Entity>> entities = new HashMap<TexturedMesh, List<Entity>>();

	public void render(Light sun, Camera cam) {
		renderer.Clear();
		shader.Start();
		shader.LoadLight(sun);
		shader.LoadViewMatrix(cam);
		renderer.render(entities);
		shader.Stop();
		entities.clear();
	}

	public void processEntity(Entity entity) {
		TexturedMesh entityMesh = entity.getMesh();
		List<Entity> batch = entities.get(entityMesh);
		if (batch != null) {
			batch.add(entity);
		}
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityMesh, newBatch);
		}
	}

	public void clean() {
		shader.Clean();
	}

}
