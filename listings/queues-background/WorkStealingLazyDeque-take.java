// Only owner can take. Returns either null or a WorkItem 
// that should be executed.
public WorkItem take() {
	// Empty
	if (ownerHead == ownerTail)
		return null;

	// Pop the last work item from the deque.
	final int lastTail = ownerTail - 1;
	final int lastIndex = lastTail % workItems.length;

	// Read the work item popped.
	// Note: if we get back null, the work item must have been 
	// stolen, since otherwise we never store null into the 
	// array, and we know this location was initialized.
	WorkItem workItem = workItems.get(lastIndex);
	if (!workItems.compareAndSet(lastIndex, workItem, null))
		workItem = null;

	// Only updates the location of the head of the deque when 
	// it tries to take something and finds it gone
	if (workItem == null) {
		// The work item we put here was stolen!
		// If this work item was stolen, then all previous 
		// entries must have been stolen too. Update our 
		// notion of the head of the deque.
		ownerHead = ownerTail;

		// Deque is now empty.
		return null;
	}

	ownerTail = lastTail;
	return workItem;
}
