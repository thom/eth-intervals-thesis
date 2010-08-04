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
