public WorkItem steal(Worker thiefWorker) {
	int oldTop, oldBottom;
	WorkItem workItem;
		
	while (true) {
		// important that top read before bottom
		oldTop = top.get();
		oldBottom = bottom; //*\label{lst:work-stealing-deque-steal-bottom}
		WorkItem[] currentWorkItems = workItems;
		int size = oldBottom - oldTop;
			
		if (size <= 0)
			return null; // empty
			
		workItem = currentWorkItems[oldTop % currentWorkItems.length];
			
		 // fetch and increment
		if (top.compareAndSet(oldTop, oldTop + 1))
			break;
	}
		
	return workItem;
}
