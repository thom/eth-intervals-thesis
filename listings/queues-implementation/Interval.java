import java.util.concurrent.atomic.AtomicReference;

public abstract class Interval extends WorkItem {		
	/**
	 * State used to indicate whether interval has been 
	 * initialized, is running or is done already.
	 */
	enum RunningState {
		INIT, RUNNING, DONE
	}

	/**
	 * Indicates whether interval has been initialized, 
	 * is running or is done already.
	 */
	private final AtomicReference<RunningState> runningState;

	public Interval(Dependency dep) {
		runningState = 
			new AtomicReference<RunningState>(RunningState.INIT);

		// Rest of the method not shown...
	}

	protected abstract void run();
	
	/**
	 * The "main" method for this interval: invoked when we 
	 * are scheduled. Simply invokes "exec()".
	 */
	@Override
	void exec(Worker worker) {
		if (runningState.compareAndSet(RunningState.INIT, 
				RunningState.RUNNING)) {
			exec();
			runningState.set(RunningState.DONE);
		}
	}
	
	/**
	 * Executes the interval's task and - once it is finished 
	 * - signals the end of the interval that it can occur 
	 * (assuming all of its other dependencies are satisfied).
	 */
	final void exec() {
		// Implementation not shown...
	}
}
