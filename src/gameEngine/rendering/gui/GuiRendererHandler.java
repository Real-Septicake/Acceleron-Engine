package gameEngine.rendering.gui;

import java.util.HashSet;

import org.joml.Matrix4f;
import org.lwjgl.opengl.*;

import gameEngine.common.*;
import gameEngine.components.rendering.Camera;
import gameEngine.rendering.WindowManager;
import gameEngine.rendering.data.meshData.MeshLowLevel;
import gameEngine.rendering.gui.shaders.GuiShader;

public class GuiRendererHandler {
	
	private final MeshLowLevel guiQuad;
	private final GuiShader shader;
	
	private static HashSet<GuiTexture> guiElements = new HashSet<GuiTexture>();
	
	public GuiRendererHandler(LowLevelLoader loader) {
		double[] quadVertPositions = new double[] { -1, 1, 0, -1, -1, 0, 1, 1, 0, 1, -1, 0 };
		
		guiQuad = loader.loadToVAO(quadVertPositions);
		shader = new GuiShader();
	}
	
	public void renderUI(Camera cam) {
		shader.Start();
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL30.glBindVertexArray(guiQuad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		
		createProjectionMatrix(cam);
		
		double width = WindowManager.manager.getCurrentWidth(), height = WindowManager.manager.getCurrentHeight();
		
		for (GuiTexture guiTexture : guiElements) {
			
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, guiTexture.getTexture());
			
			Matrix4f matrix4f = Maths.createTransformationMatrixGui(guiTexture.getScaledPosition(width, height), guiTexture.transform.rotation, guiTexture.getScaledSize(width, height), width, height);
			
			shader.loadTransformation(matrix4f);
			
			GL20.glDrawArrays(GL20.GL_TRIANGLE_STRIP, 0, guiQuad.getVertexCount());
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		guiElements.clear();
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shader.Stop();
	}
	
	public static void addUIElement(GuiTexture toRender) {
		guiElements.add(toRender);
	}
	
	public void cleanUp() {
		shader.clean();
	}
}
