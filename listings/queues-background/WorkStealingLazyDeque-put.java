public void put(WorkItem workItem) {
	assert workItem != null;

	while (true) {
		final int length = workItems.length();
		final int tail = ownerTail;

		// Would be full or would roll-over
		if (tail - ownerHead >= length || 
				tail == Integer.MAX_VALUE) {
			expand();
			continue;
		}

		workItems.set(tail % length, workItem);
		ownerTail = tail + 1;
		return;
	}
}
