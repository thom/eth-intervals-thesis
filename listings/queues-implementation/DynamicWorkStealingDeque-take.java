public WorkItem take() {
	// Read bottom data
	Node oldBottomNode = bottom.node;
	int oldBottomIndex = bottom.index;

	Node newBottomNode;
	int newBottomIndex;
	if (oldBottomIndex != Node.SIZE - 1) {
		newBottomNode = oldBottomNode;
		newBottomIndex = oldBottomIndex + 1;
	} else {
		newBottomNode = oldBottomNode.next;
		newBottomIndex = 0;
	}

	if (newBottomNode == null)
		return null;

	// Read data to be taken
	WorkItem workItem = 
		newBottomNode.workItems[newBottomIndex];

	// Update bottom
	bottom.node = newBottomNode;
	bottom.index = newBottomIndex;

	// Read top
	AtomicStampedReference<Index> currentTopRef = top;
	Index currentTop = currentTopRef.getReference();
	Node currentTopNode = currentTop.node;
	int currentTopIndex = currentTop.index;
	int currentTopTag = currentTopRef.getStamp();

	// Case 1: If top has crossed bottom
	if ((oldBottomNode == currentTopNode)
			&& (newBottomIndex == currentTopIndex)) {
		// Return bottom to its old position
		bottom.node = oldBottomNode;
		bottom.index = oldBottomIndex;
		return null;
	}
	// Case 2: When taking the last entry in the deque (i.e. 
	// deque is empty after the update of bottom)
	else if ((newBottomNode == currentTopNode)
			&& (newBottomIndex == currentTopIndex)) {
		// Try to update top's tag so no concurrent steal 
		// operation will also take the same entry
		Index newTop = new Index(currentTopNode, 
			currentTopIndex);
		if (top.compareAndSet(currentTop, newTop, 
				currentTopTag, currentTopTag + 1)) {
			return workItem;
		}
		// If CAS failed (i.e. a concurrent steal operation 
		// already took the last entry)
		else {
			// Return bottom to its old position
			bottom.node = oldBottomNode;
			bottom.index = oldBottomIndex;
			return null;
		}
	}
	// Case 3: Regular case (i.e. there was at least one 
	// entry in the deque after bottom's update)
	else {
		return workItem;
	}
}
