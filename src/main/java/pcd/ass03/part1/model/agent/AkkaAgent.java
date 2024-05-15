package pcd.ass03.part1.model.agent;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import pcd.ass03.part1.model.simengineseq.AbstractAgent;
import pcd.ass03.part1.model.simengineseq.AgentSynchronizer;

import java.util.Objects;

public class AkkaAgent extends AbstractBehavior<AkkaAgent.AgentBehaviors> {

    public static abstract class AgentBehaviors{
        private AgentBehaviors() {}
        public static final class SenseDecide extends AgentBehaviors{
            public SenseDecide(){

            }
        }

        public static final class Act extends AgentBehaviors{
            public Act(){

            }
        }

        public static final class UpdateAgent extends AgentBehaviors {
            private final int dt;
            public UpdateAgent(final int dt){
                this.dt = dt;
            }

            public int getDt(){
                return this.dt;
            }
        }
    }


    private final AbstractAgent agent;
    public AkkaAgent(final ActorContext<AgentBehaviors> context, final AbstractAgent agent){
        super(context);
        this.agent = Objects.requireNonNull(agent);
    }

    @Override
    public Receive<AgentBehaviors> createReceive() {
        return newReceiveBuilder()
                .onMessage(AgentBehaviors.SenseDecide.class, this::onSenseDecide)
                .onMessage(AgentBehaviors.Act.class, this::onAct)
                .onMessage(AgentBehaviors.UpdateAgent.class, this::onDeltaTime)
                .build();

    }

    private Behavior<AgentBehaviors> onSenseDecide(AgentBehaviors.SenseDecide msg){
        this.getContext().getLog().info("[Akka Agent] Sense step");
        this.agent.senseStep();
        return this;
    }

    private Behavior<AgentBehaviors> onAct(AgentBehaviors.Act msg){
        this.getContext().getLog().info("[Akka Agent] Act step");
        AgentSynchronizer.getInstance().executeCriticalSection(this.agent::actStep);
        return this;
    }

    private Behavior<AgentBehaviors> onDeltaTime(AgentBehaviors.UpdateAgent msg){
        this.getContext().getLog().info("[Akka Agent] Update delta time");
        this.agent.decideStep(msg.getDt());
        return this;
    }

    public static Behavior<AgentBehaviors> create(final AbstractAgent agent) {
        return Behaviors.setup(context -> new AkkaAgent(context, agent));
    }
}
