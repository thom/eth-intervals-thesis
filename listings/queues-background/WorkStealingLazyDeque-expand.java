private void expand() {
	synchronized (thief) { //*\label{lst:work-stealing-lazy-deque-expand-only-owner}
		int oldSize = workItems.length();
		int newSize = 2 * oldSize;
		int newTail = 0;
		AtomicReferenceArray<WorkItem> newWorkItems = 
			new AtomicReferenceArray<WorkItem>(newSize);

		for (int i = ownerHead; i < ownerTail; i++) {
			newWorkItems.set(newTail % newSize,
				workItems.get(i % oldSize));
			newTail++;
		}

		ownerTail = newTail; //*\label{lst:work-stealing-lazy-deque-expand-reset-1}
		ownerHead = thief.head = 0;
		workItems = newWorkItems; //*\label{lst:work-stealing-lazy-deque-expand-reset-2}
	}
}
