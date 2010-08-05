public class WorkStealingDeque implements WorkStealingQueue {
	private volatile WorkItem[] workItems = new WorkItem[1024];
	private volatile int bottom = 0;
	private AtomicInteger top = new AtomicInteger(0);
	// ...
}
