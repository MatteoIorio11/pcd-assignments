package pcd.ass02.part1.model.simtrafficbase;

import pcd.ass02.part1.model.simengineseq.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(double distance) implements Action {}
