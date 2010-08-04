public void put(WorkItem workItem) {
	assert workItem != null;

	// If queue full or index overflow: expand
	if (!((tail < Math.min(tailMin, head) + size) 
			&& (tail < Integer.MAX_VALUE / 2)))
		expand();

	workItems[tail % size] = workItem;
	tail = tail + 1;
}
