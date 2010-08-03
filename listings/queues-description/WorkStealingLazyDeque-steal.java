public WorkItem steal() {
	// At most one thief at a time
	synchronized (thief) {
		final int head = thief.head;
		final int index = head % tasks.length;

		WorkItem item = tasks.get(index);
		if (!tasks.compareAndSet(index, item, null))
			item = null;

		// If null, was either already taken by owner or 
		// never there.
		if (item == null)
			return null;

		// Successfully stolen!
		thief.head++;
		return item;
	}
}
