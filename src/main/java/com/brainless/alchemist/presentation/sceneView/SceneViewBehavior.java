package com.brainless.alchemist.presentation.sceneView;

import java.util.function.BiFunction;

import com.brainless.alchemist.model.ECS.blueprint.Blueprint;

import util.geometry.geom2d.Point2D;

public class SceneViewBehavior {

	public static BiFunction<Blueprint, Point2D, Boolean> previewEntityFunction = null;
	public static BiFunction<Blueprint, Point2D, Boolean> createEntityFunction = null;
	public static BiFunction<Blueprint, Point2D, Boolean> abortEntityFunction = null;
	
}
