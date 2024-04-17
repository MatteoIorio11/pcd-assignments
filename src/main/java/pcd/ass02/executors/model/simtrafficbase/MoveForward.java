package pcd.ass02.executors.model.simtrafficbase;

import pcd.ass02.executors.model.simengineseq.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(double distance) implements Action {}
