package pcd.ass03.part1.view.agent;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import pcd.ass03.part1.model.agent.AkkaAgent;
import pcd.ass03.part1.model.simengineseq.AbstractAgent;
import pcd.ass03.part1.model.simengineseq.AbstractSimulation;

public class JavaGuiAgent extends AbstractBehavior<JavaGuiAgent.GuiBehaviors> {
    private final AbstractSimulation simulation;
    private final int steps;

    public static abstract class GuiBehaviors {
        private GuiBehaviors() {}

        public static final class StartBehavior extends GuiBehaviors {
        }
        public static final class StopBehavior extends GuiBehaviors {
        }
    }

    @Override
    public Receive<GuiBehaviors> createReceive() {
        return newReceiveBuilder()
                .onMessage(GuiBehaviors.StartBehavior.class, this::onStart)
                .onMessage(GuiBehaviors.StopBehavior.class, this::onStop)
                .build();
    }

    public Behavior<GuiBehaviors> onStart(GuiBehaviors.StartBehavior b) {
        try {
            this.simulation.run(this.steps);
        } catch (final Exception e) {
            //
        }
        return this;
    }

    public Behavior<GuiBehaviors> onStop(GuiBehaviors.StopBehavior b) {
        try {
            this.simulation.stopSimulation();
        } catch (final Exception e) {
            //
        }
        return this;
    }

    public JavaGuiAgent(final ActorContext<GuiBehaviors> context, final AbstractSimulation simulation, final int steps) {
        super(context);
        this.simulation = simulation;
        this.steps = steps;
    }

    public static Behavior<GuiBehaviors> create(final AbstractSimulation simulation, final int steps) {
        return Behaviors.setup(ctx -> new JavaGuiAgent(ctx, simulation, steps));
    }
}
