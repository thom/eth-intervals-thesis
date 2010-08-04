private void expand() {
	synchronized (this) {
		if (head > tailMin)
			head = tailMin;

		tailMin = Integer.MAX_VALUE;

		// Adjust the indices to prevent overflow
		int count = Math.max(0, tail - head);
		head = head % size;
		tail = head + count;

		// Replace workItems array
		int newSize = 2 * size;
		WorkItem[] newWorkItems = new WorkItem[newSize];
		for (int i = head; i < tail; i++)
			newWorkItems[i % newSize] = workItems[i % size];
		size = newSize;
		workItems = newWorkItems;
	}
}
