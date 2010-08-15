public WorkItem take() {
	int oldBottom = this.bottom;
	WorkItem[] currentWorkItems = workItems;
	oldBottom = oldBottom - 1;
	this.bottom = oldBottom;
	int oldTop = top.get();
	int size = oldBottom - oldTop;

	if (size < 0) { //*\label{lst:work-stealing-deque-take-empty-1}
		bottom = oldTop;
		return null; //*\label{lst:work-stealing-deque-take-empty-2}
	}

	WorkItem workItem = 
		currentWorkItems[bottom % currentWorkItems.length];

	if (size > 0) //*\label{lst:work-stealing-deque-take-non-empty-1}
		return workItem; //*\label{lst:work-stealing-deque-take-non-empty-2}

	if (!top.compareAndSet(oldTop, oldTop + 1)) //*\label{lst:work-stealing-deque-take-cas}
		workItem = null; // queue is empty

	bottom = oldTop + 1; //*\label{lst:work-stealing-deque-take-update}
	return workItem;
}
