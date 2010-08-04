public WorkItem steal() {
	synchronized (thief) {
		final int head = thief.head;
		final int index = head % workItems.length;
		WorkItem workItem = workItems.get(index);
		if (!workItems.compareAndSet(index, workItem, null))
			workItem = null;

		if (workItem == null)
			return null;

		thief.head++;
		return workItem;
	}
}
