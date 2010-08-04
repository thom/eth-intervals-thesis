public class DuplicatingWorkStealingDeque 
		implements WorkStealingQueue {
	private int size = 1024;
	private WorkItem[] workItems = new WorkItem[size];
	private int tailMin = Integer.MAX_VALUE;
	private volatile int tail = 0;
	private volatile int head = 0;

	public void put(WorkItem workItem) {
		assert workItem != null;

		// If queue full or index overflow: expand
		if (!((tail < Math.min(tailMin, head) + size) 
				&& (tail < Integer.MAX_VALUE / 2)))
			expand();

		workItems[tail % size] = workItem;
		tail = tail + 1;
	}

	public WorkItem steal() {
		synchronized (this) {
			if (head < tail) {
				WorkItem workItem = workItems[head % size];
				head = head + 1;
				return workItem;
			} else {
				return null;
			}
		}
	}

	public WorkItem take() {
		tail = tail - 1;
		WorkItem workItem = null;

		// can we pop safely?
		if (head <= Math.min(tailMin, tail)) {
			if (tailMin > tail)
				tailMin = tail;
			workItem = workItems[tail % size];
			workItems[tail % size] = null;
		} else {
			synchronized (this) {
				// adjust head and reset tailMin
				if (head > tailMin)
					head = tailMin;
				tailMin = Integer.MAX_VALUE;

				// try to pop again
				if (head <= tail) {
					workItem = workItems[tail % size];
					workItems[tail % size] = null;
				} else {
					tail = tail + 1; // restore tail when empty
				}
			}
		}

		return workItem;
	}

	private void expand() {
		synchronized (this) {
			if (head > tailMin)
				head = tailMin;

			tailMin = Integer.MAX_VALUE;

			// Adjust the indices to prevent overflow
			int count = Math.max(0, tail - head);
			head = head % size;
			tail = head + count;

			// Replace workItems array
			int newSize = 2 * size;
			WorkItem[] newWorkItems = new WorkItem[newSize];
			for (int i = head; i < tail; i++)
				newWorkItems[i % newSize] = workItems[i % size];
			size = newSize;
			workItems = newWorkItems;
		}
	}
}
