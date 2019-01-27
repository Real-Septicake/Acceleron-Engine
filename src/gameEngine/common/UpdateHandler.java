package gameEngine.common;

import java.util.HashSet;

import gameEngine.components.*;

public class UpdateHandler {
	private static HashSet<Script> scripts = new HashSet<Script>();
	private static HashSet<Script> scriptsToStart = new HashSet<Script>();
	
	private static HashSet<StaticScript> staticScripts = new HashSet<StaticScript>();
	private static HashSet<StaticScript> staticScriptsToStart = new HashSet<StaticScript>();
	
	public static void RunUpdate() {
		for (Script script : scriptsToStart) {
			scriptsToStart.remove(script);
			script.start();
		}
		
		for (Script script : scripts) {
			script.update();
		}
		
		for (StaticScript script : staticScriptsToStart) {
			staticScriptsToStart.remove(script);
			script.start();
		}
		
		for (StaticScript script : staticScripts) {
			script.update();
		}
	}
	
	public static void RegisterScript(Script script) {
		scripts.add(script);
		scriptsToStart.add(script);
	}
	
	public static void UnregisterScript(Script script) {
		scripts.remove(script);
		scriptsToStart.remove(script);
	}
	
	public static void RegisterScript(StaticScript script) {
		staticScripts.add(script);
		staticScriptsToStart.add(script);
	}
	
	public static void UnregisterScript(StaticScript script) {
		staticScripts.remove(script);
		staticScriptsToStart.remove(script);
	}
}
