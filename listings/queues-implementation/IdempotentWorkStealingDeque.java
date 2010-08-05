public class IdempotentWorkStealingDeque 
		implements WorkStealingQueue {
	class ArrayData {
		final int head, size;

		public ArrayData(int head, int size) {
			this.head = head;
			this.size = size;
		}
	}

	private AtomicStampedReference<ArrayData> anchor = 
		new AtomicStampedReference<ArrayData>(new ArrayData(0, 0), 0);
	private WorkItem[] workItems = new WorkItem[1024];

	// ...
}
