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
