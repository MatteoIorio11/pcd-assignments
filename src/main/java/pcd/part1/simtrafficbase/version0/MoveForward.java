package pcd.part1.simtrafficbase.version0;

import pcd.part1.simengineseq.version0.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(double distance) implements Action {}
