public void put(WorkItem workItem) {
	// Read bottom data
	Node currentNode = bottom.node;
	int currentIndex = bottom.index;

	// Write data in current bottom cell
	currentNode.workItems[currentIndex] = workItem;

	Node newNode;
	int newIndex;
	if (currentIndex != 0) {
		newNode = currentNode;
		newIndex = currentIndex - 1;
	} else {
		// Allocate and link a new node
		newNode = new Node();
		newNode.next = currentNode;
		currentNode.prev = newNode;
		newIndex = Node.SIZE - 1;
	}

	// Update bottom
	bottom.node = newNode;
	bottom.index = newIndex;
}
