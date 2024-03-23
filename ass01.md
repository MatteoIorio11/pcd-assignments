# Assignment 01

## Analisi
Come descritto dal prof, bisogna intervenire sulla classe: ``AbstractSimulation``, dal momento in cui c'è una sezione all'interno di questo codice che è possibile parallelizzare. Qui di seguito è mostrato il codice di ``AbstractSimulation``, come possiamo vedere c'è una sezione chee viene eseguita sequenzialmente, la quale però **deve** essere resa parallelizzabile:
```java
	public void run(int numSteps) {		

		startWallTime = System.currentTimeMillis();

		/* initialize the env and the agents inside */
		int t = t0;

		env.init();
		for (var a: agents) {
			a.init(env);
		}

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;
		
		while (nSteps < numSteps) {

			currentWallTime = System.currentTimeMillis();
		
			/* make a step */
			
			env.step(dt);
			for (var agent: agents) {
				agent.step(dt);
			}
			t += dt;
			
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
		}	
		
		endWallTime = System.currentTimeMillis();
		this.averageTimePerStep = timePerStep / numSteps;
		
	}
```
Il vero problema risiede in questa piccola sezione del codice, in cui l'ambiente di simulazione si carica del fatto di dover eseguire i vari step di ogni singolo agente. Questa cosa ovviamente deve essere resa piu efficiente.
```java
for (var agent: agents) {
    agent.step(dt);
}
```
Il metodo ``agent.step(dt)`` non è altro che l'esecuzione di un insieme di operazioni che determina il comportamento dell'agente, in questo caso:
```java
	public void step(int dt) {

		/* sense */

		AbstractEnvironment env = this.getEnv();		
		currentPercept = (CarPercept) env.getCurrentPercepts(getId());			

		/* decide */
		
		selectedAction = Optional.empty();
		
		decide(dt);
		
		/* act */
		
		if (selectedAction.isPresent()) {
			env.doAction(getId(), selectedAction.get());
		}
	}
```
Come descritto all'interno della consegna, le fasi di **sense** e **decide** sono solamente operazioni di semplice lettura. La modifica dell'ambiente avviene solamente all'interno della fase di *act*, ovvero quando si decide di far eseguire ad ogni singolo agente il suo comportamento. Bisogna comunque tenere conto che le operazioni dovranno essere sempre eseguite nello stesso ordine. 

---
## Design
Una possibile implementazione è quella di creare una nuova implementazione dell'agente, o utilizzare il pattern strategy che prende in input il comportamento dell'agente e lo esegue, comunque poter realizzare il metodo ``agent.step`` in maniera del tutto autonoma, senza dover doverlo far fare al ``simulatore``, egli dovrà solo lanciare tutti gli agentie poi aspettari che essi abbiano finito prima di poter rieseguire un nuovo step, dal momento in cui ogni agente può dipendere dagli agenti che gli stanno intorno. 

Il nuovo simulatore dovra fare qualcosa di questo tipo: 
```java
	public void run(int numSteps) {		

		startWallTime = System.currentTimeMillis();

		/* initialize the env and the agents inside */
		int t = t0;

		env.init();
		for (var a: agents) {
			a.init(env);
		}

		this.notifyReset(t, agents, env);
		
		long timePerStep = 0;
		int nSteps = 0;
		
		while (nSteps < numSteps) {

			currentWallTime = System.currentTimeMillis();
		
			/* make a step */
			
			env.step(dt);
            // NEW PART
            for(var agent: agents){
                agent.start(dt)
            }
			for (var agent: agents) {
				agent.join();
			}
            // END OF THE NEW PART
			t += dt;
			notifyNewStep(t, agents, env);

			nSteps++;			
			timePerStep += System.currentTimeMillis() - currentWallTime;
			
			if (toBeInSyncWithWallTime) {
				syncWithWallTime();
			}
		}	
		
		endWallTime = System.currentTimeMillis();
		this.averageTimePerStep = timePerStep / numSteps;
		
	}
```
In questo modo il comportamento degli agenti verrà eseguito su un thread apposito in modo da non dover intaccare il simulatore