// Only owner can take. Returns either null or a WorkItem 
// that should be executed.
public WorkItem take() {
	// Empty
	if (ownerHead == ownerTail)
		return null;

	// Pop the last item from the deque.
	final int lastTail = ownerTail - 1;
	final int lastIndex = lastTail % tasks.length;

	// Read the item popped.
	// Note: if we get back null, the item must have been 
	// stolen, since otherwise we never store null into 
	// the array, and we know this location was initialized.
	WorkItem item = tasks.get(lastIndex);
	if (!tasks.compareAndSet(lastIndex, item, null))
		item = null;

	// Only updates the location of the head of the deque 
	// when it tries to take something and finds it gone
	if (item == null) {
		// The item we put here was stolen!
		// If this item was stolen, then all previous entries 
		// must have been stolen too. Update our notion of the 
		// head of the deque.
		ownerHead = ownerTail;

		// Deque is now empty.
		return null;
	}

	ownerTail = lastTail;
	return item;
}
