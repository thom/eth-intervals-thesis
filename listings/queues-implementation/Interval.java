public abstract class Interval extends WorkItem {		
	// Interval states
	enum RunningState {
		INIT, RUNNING, DONE //*\label{lst:interval-state}
	}

	// Indicates whether interval has been initialized, is running or 
	// is done already.
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
