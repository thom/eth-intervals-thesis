public WorkItem steal() {
	// At most one thief at a time
	synchronized (thief) {
		final int head = thief.head;
		final int index = head % workItems.length;

		WorkItem workItem = workItems.get(index);
		if (!workItems.compareAndSet(index, workItem, null))
			workItem = null;

		// If null, was either already taken by owner or 
		// never there.
		if (workItem == null)
			return null;

		// Successfully stolen!
		thief.head++;
		return workItem;
	}
}
