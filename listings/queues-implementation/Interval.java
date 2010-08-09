public abstract class Interval extends WorkItem {		
	enum RunningState { INIT, RUNNING, DONE } //*\label{lst:interval-state}

	private final AtomicReference<RunningState> runningState = 
		new AtomicReference<RunningState>(RunningState.INIT); //*\label{lst:interval-init}
	
	// The "main" method for this interval: invoked when we are 
	// scheduled. Simply invokes "exec()".
	void exec(Worker worker) {
		if (runningState.compareAndSet(RunningState.INIT, 
				RunningState.RUNNING)) { //*\label{lst:interval-cas}
			exec();  // execute the associated action
			runningState.set(RunningState.DONE); //*\label{lst:interval-done}
		}
	}
	// ...
}
