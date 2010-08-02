public class DuplicatingWorkStealingDeque 
		implements WorkStealingQueue {
	private int size = 1024;
	private WorkItem[] tasks = new WorkItem[size];
	private int tailMin = Integer.MAX_VALUE;
	private volatile int tail = 0;
	private volatile int head = 0;

	public void put(WorkItem task) {
		assert task != null;

		// If queue full or index overflow: expand
		if (!((tail < Math.min(tailMin, head) + size) 
				&& (tail < Integer.MAX_VALUE / 2)))
			expand();

		tasks[tail % size] = task;
		tail = tail + 1;
	}

	public WorkItem steal() {
		synchronized (this) {
			if (head < tail) {
				WorkItem task = tasks[head % size];
				head = head + 1;
				return task;
			} else {
				return null;
			}
		}
	}

	public WorkItem take() {
		tail = tail - 1;
		WorkItem task = null;

		// can we pop safely?
		if (head <= Math.min(tailMin, tail)) {
			if (tailMin > tail)
				tailMin = tail;
			task = tasks[tail % size];
			tasks[tail % size] = null;
		} else {
			synchronized (this) {
				// adjust head and reset tailMin
				if (head > tailMin)
					head = tailMin;
				tailMin = Integer.MAX_VALUE;

				// try to pop again
				if (head <= tail) {
					task = tasks[tail % size];
					tasks[tail % size] = null;
				} else {
					tail = tail + 1; // restore tail when empty
				}
			}
		}

		return task;
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

			// Replace tasks array
			int newSize = 2 * size;
			WorkItem[] newTasks = new WorkItem[newSize];
			for (int i = head; i < tail; i++)
				newTasks[i % newSize] = tasks[i % size];
			size = newSize;
			tasks = newTasks;
		}
	}
}
