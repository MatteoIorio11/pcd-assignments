package pcd.ass03.part1.model.agent;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import pcd.ass03.part1.model.simengineseq.AbstractAgent;
import pcd.ass03.part1.model.simengineseq.AgentSynchronizer;

import java.time.Duration;
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
    private AkkaAgent(final ActorContext<AgentBehaviors> context, final AbstractAgent agent){
        super(context);
        this.agent = Objects.requireNonNull(agent);
        context.getLog().info("[Akka Agent] Agent created");
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
        try {
            this.agent.senseStep();
            this.getContext().getLog().info("[Akka Agent] Sense step");
        }catch (Exception e){
            //
        }
        return this;
    }

    private Behavior<AgentBehaviors> onAct(AgentBehaviors.Act msg){
        try {
            this.getContext().getLog().info("[Akka Agent] Act step");
            AgentSynchronizer.getInstance().executeCriticalSection(this.agent::actStep);
        }catch (Exception e){
            //
        }
        return this;
    }

    private Behavior<AgentBehaviors> onDeltaTime(AgentBehaviors.UpdateAgent msg){
        try {
            this.agent.decideStep(msg.getDt());
        }catch (Exception e){
            //
        }
        return this;
    }

    public static Behavior<AgentBehaviors> create(final AbstractAgent agent) {
        return Behaviors.setup(context -> new AkkaAgent(context, agent));
    }
}
