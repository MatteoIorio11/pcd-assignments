package pcd.ass01.model.simtrafficbase;

import pcd.ass01.model.simengineseq.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(double distance) implements Action {}
