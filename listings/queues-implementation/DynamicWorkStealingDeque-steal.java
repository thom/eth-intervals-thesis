public WorkItem steal() {
	WorkItem workItem;

	while (true) {
		// Read top
		AtomicStampedReference<Index> currentTopRef = top;
		Index currentTop = currentTopRef.getReference();
		Node currentTopNode = currentTop.node;
		int currentTopIndex = currentTop.index;
		int currentTopTag = currentTopRef.getStamp();

		if (currentTopNode == null)
			return null;

		// Read bottom
		Index currentBottom = bottom;

		if (isEmpty(currentBottom, currentTop)) {
			if (currentTopRef == top)
				return null;
			else
				continue; // ABORT
		}

		// New top values
		int newTopTag;
		Node newTopNode;
		int newTopIndex;

		// If deque isn't empty, calculate next top pointer
		if (currentTopIndex != 0) {
			// Stay at current node
			newTopTag = currentTopTag;
			newTopNode = currentTopNode;
			newTopIndex = currentTopIndex - 1;
		} else {
			// Move to next node and update tag
			newTopTag = currentTopTag + 1;
			newTopNode = currentTopNode.prev;
			newTopIndex = Node.SIZE - 1;
		}

		// Read value
		workItem = currentTopNode.workItems[currentTopIndex];

		// New top
		Index newTop = new Index(newTopNode, newTopIndex);

		// Try to update top using CAS
		if (top.compareAndSet(currentTop, newTop, currentTopTag, 
				newTopTag))
			break;
	}

	return workItem;
}
