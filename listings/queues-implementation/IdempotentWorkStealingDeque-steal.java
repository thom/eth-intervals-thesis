public WorkItem steal(Worker thiefWorker) {
	// Order read in (1) before read in (2)
	// Order read in (3) before CAS in (4)
	ArrayData arrayData;
	int head, size, tag;
	WorkItem workItem;
		
	while (true) {
		// (1)
		arrayData = anchor.getReference();
		head = arrayData.head;
		size = arrayData.size;
		tag = anchor.getStamp();
		if (size == 0)
			return null;
			
		// (2)
		WorkItem[] tempWorkItems = workItems;
			
		// (3)
		workItem = tempWorkItems[head % tempWorkItems.length];
		int newHead = head + 1 % Integer.MAX_VALUE;
			
		// (4)
		if (anchor.compareAndSet(arrayData,
				new ArrayData(newHead, size - 1), tag, tag))
			break;
	}
	
	return workItem;
}
