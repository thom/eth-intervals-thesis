public abstract class Interval extends WorkItem {		
	/**
	 * State used to indicate whether interval has been initialized, 
	 * is running or is done already.
	 */
	enum RunningState {
		INIT, RUNNING, DONE
	}

	/**
	 * Indicates whether interval has been initialized, is running or 
	 * is done already.
	 */
	private final AtomicReference<RunningState> runningState = 
		new AtomicReference<RunningState>(RunningState.INIT);
	
	/**
	 * The "main" method for this interval: invoked when we are 
	 * scheduled. Simply invokes "exec()".
	 */
	void exec(Worker worker) {
		if (runningState.compareAndSet(RunningState.INIT, 
				RunningState.RUNNING)) {
			exec();  // execute the associated action
			runningState.set(RunningState.DONE);
		}
	}

	// ...
}
