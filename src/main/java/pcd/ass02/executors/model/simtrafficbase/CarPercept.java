package pcd.ass02.executors.model.simtrafficbase;

import java.util.Optional;

import pcd.ass02.executors.model.simengineseq.Percept;

/**
 * 
 * Percept for Car Agents
 * 
 * - position on the road
 * - nearest car, if present (distance)
 * - nearest semaphore, if presente (distance)
 * 
 */
public record CarPercept(double roadPos, Optional<CarAgentInfo> nearestCarInFront, Optional<TrafficLightInfo> nearestSem) implements Percept { }