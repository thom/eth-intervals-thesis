public class DuplicatingWorkStealingDeque 
		implements WorkStealingQueue {
	private int size = 1024;
	private WorkItem[] workItems = new WorkItem[size];
	private int tailMin = Integer.MAX_VALUE;
	private volatile int tail = 0;
	private volatile int head = 0;
	// ...
}
