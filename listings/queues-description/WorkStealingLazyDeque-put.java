public void put(WorkItem task) {
	assert task != null;

	while (true) {
		final int length = tasks.length();
		final int tail = ownerTail;

		// Would be full or would roll-over
		if (tail - ownerHead >= length || 
				tail == Integer.MAX_VALUE) {
			expand();
			continue;
		}

		tasks.set(tail % length, task);
		ownerTail = tail + 1;
		return;
	}
}
