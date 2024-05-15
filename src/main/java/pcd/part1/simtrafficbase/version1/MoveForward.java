package pcd.part1.simtrafficbase.version1;

import pcd.part1.simengineseq.version1.Action;

/**
 * Car agent move forward action
 */
public record MoveForward(String agentId, double distance) implements Action {}
