package pcd.ass01.simtrafficbase.version0;

import pcd.ass01.simengineseq.version0.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(double distance) implements Action {}
