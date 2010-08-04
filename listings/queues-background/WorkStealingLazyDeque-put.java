public void put(WorkItem workItem) {
	assert workItem != null;
	while (true) {
		final int length = workItems.length();
		final int tail = ownerTail;

		if (tail - ownerHead >= length || //*\label{lst:work-stealing-lazy-deque-put-expand-1}
				tail == Integer.MAX_VALUE) { //*\label{lst:work-stealing-lazy-deque-put-expand-2}
			expand();
			continue;
		}

		workItems.set(tail % length, workItem);
		ownerTail = tail + 1;
		return;
	}
}
