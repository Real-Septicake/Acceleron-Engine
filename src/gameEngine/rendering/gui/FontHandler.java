package gameEngine.rendering.gui;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.joml.*;

import gameEngine.debug.Debug;

public class FontHandler {
	
	public Font loadedFont;
	
	public FontHandler(String filePath) {
		InputStream inputStream;
		
		try {
			inputStream = new FileInputStream(filePath);
		} catch (Exception e) {
			Debug.logError(e);
			return;
		}
		
		try {
			loadedFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
		} catch (FontFormatException e) {
			Debug.logError(e.toString());
		} catch (IOException e) {
			Debug.logError(e.toString());
		}
	}
	
	public Vector3d[] getCharacterMesh(String character) {
		
		GlyphVector vector = loadedFont.createGlyphVector(new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF, RenderingHints.VALUE_FRACTIONALMETRICS_OFF), character);
		
		PathIterator iterator = vector.getGlyphOutline(0).getPathIterator(null);
		
		LinkedList<Vector3d> pointsQueue = new LinkedList<Vector3d>();
		
		
		while (!iterator.isDone()) {
			double[] coords = new double[6];
			int type = iterator.currentSegment(coords);
			if(type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO) {
				pointsQueue.add(new Vector3d(coords[0], coords[1], 0));
			}
			
			iterator.next();
		}
		
		int id = 0;
		Vector3d[] dataVector3ds = new Vector3d[pointsQueue.size()];
		for (Vector3d vector3d : pointsQueue) {
			dataVector3ds[id] = vector3d;
			id++;
		}
		
		return dataVector3ds;
	}
}
