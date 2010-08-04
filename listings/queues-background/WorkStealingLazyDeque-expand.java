private void expand() {
	// Only owner can expand, no thieves are active
	synchronized (thief) {
		assert ownerHead <= thief.head && 
			thief.head <= ownerTail;
		ownerHead = thief.head;
		final int length = workItems.length();
		final int thold = length >> 4;
		final int size = (ownerTail - ownerHead);

		// Less than 1/16 is free.
		if ((length - size) <= thold) {
			replaceTaskArray(length * 2);
		}
		// About to roll-over.
		else if (ownerTail == Integer.MAX_VALUE) {
			replaceTaskArray(length);
		}
	}
}

private void replaceTaskArray(int size) {
	AtomicReferenceArray<WorkItem> newWorkItems = 
		new AtomicReferenceArray<WorkItem>(size);
	final int length = workItems.length();
	int newTail = 0;

	for (int i = ownerHead; i < ownerTail; i++) {
		newWorkItems.set(newTail % size, 
			workItems.get(i % length));
		newTail++;
	}

	ownerTail = newTail;
	ownerHead = thief.head = 0;
	workItems = newWorkItems;
}
