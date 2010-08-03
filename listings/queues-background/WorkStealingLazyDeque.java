public class WorkStealingLazyDeque 
		implements WorkStealingQueue {
	static class ThiefData {
		int head = 0;
	}

	private AtomicReferenceArray<WorkItem> tasks = 
		new AtomicReferenceArray<WorkItem>(1024);
	int ownerHead = 0, ownerTail = 0;
	private final ThiefData thief = new ThiefData();
	// ...
}
