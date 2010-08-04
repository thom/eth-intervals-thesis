public WorkItem take() {
	int oldBottom = this.bottom;
	WorkItem[] currentWorkItems = workItems;
	oldBottom = oldBottom - 1;
	this.bottom = oldBottom;
	int oldTop = top.get();
	int size = oldBottom - oldTop;

	if (size < 0) {
		bottom = oldTop;
		return null;
	}

	WorkItem workItem = currentWorkItems[bottom % 
		currentWorkItems.length];

	if (size > 0)
		return workItem;

	// fetch and increment
	if (!top.compareAndSet(oldTop, oldTop + 1))
		workItem = null; // queue is empty

	bottom = oldTop + 1;
	return workItem;
}
