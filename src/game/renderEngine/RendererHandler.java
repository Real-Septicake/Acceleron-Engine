package game.renderEngine;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

import java.util.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import game.models.*;
import gameEngine.rendering.shaders.*;
import gameEngine.common.Maths;
import gameEngine.components.Camera;

public class RendererHandler {
	
	private Matrix4f projectionMatrix;
	private StaticShader shader;
	
	public RendererHandler(StaticShader shader) {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		this.shader = shader;
	}
	
	public void clear() {
		GL11.glClearColor(.25f, 0, 0.5f, 1);
		GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void render(Map<TexturedMesh, List<RenderObjectInfo>> entities, Camera camera) {
		createProjectionMatrix(camera);
		shader.loadProjectionMatrix(projectionMatrix);
		for(TexturedMesh mesh:entities.keySet()) {
			if(mesh != null) {
				prepareTexturedMesh(mesh);
				List<RenderObjectInfo> batch = entities.get(mesh);
				for(RenderObjectInfo entity:batch) {
					prepareInstance(entity);
					GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
				unbindTexturedModel();
			}
		}
	}
	
	private void prepareTexturedMesh(TexturedMesh mesh) {
		GL30.glBindVertexArray(mesh.getMesh().getVaoID());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(RenderObjectInfo entity) {
		shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.pos, entity.rot, entity.scale));
	}
	
	private void createProjectionMatrix(Camera cam) {
		float aspectRatio = (float) WindowManager.manager.getCurrentWidth() / WindowManager.manager.getCurrentHeight();

		projectionMatrix = new Matrix4f().perspective((float) Math.toRadians(cam.fov), aspectRatio, cam.nearPlane, cam.farPlane);
	}
}
