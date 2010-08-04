public WorkItem take() {
	if (ownerHead == ownerTail) // Deque is empty
		return null;

	final int lastTail = ownerTail - 1;
	final int lastIndex = lastTail % workItems.length;
	WorkItem workItem = workItems.get(lastIndex);
	if (!workItems.compareAndSet(lastIndex, workItem, null)) //*\label{lst:work-stealing-lazy-deque-take-cas}
		workItem = null;

	if (workItem == null) {
		ownerHead = ownerTail; //*\label{lst:work-stealing-lazy-deque-take-update}
		return null;
	}

	ownerTail = lastTail;
	return workItem;
}
