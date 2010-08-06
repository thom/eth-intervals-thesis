public void put(WorkItem workItem) {
	int oldBottom = bottom;
	int oldTop = top.get();
	WorkItem[] currentWorkItems = workItems;
	int size = oldBottom - oldTop;
	if (size >= currentWorkItems.length - 1) {
		currentWorkItems = expand(currentWorkItems, oldBottom, oldTop);
		workItems = currentWorkItems;
	}
	currentWorkItems[oldBottom % currentWorkItems.length] = workItem;
	bottom = oldBottom + 1; //*\label{lst:work-stealing-deque-put-update-bottom}
}
