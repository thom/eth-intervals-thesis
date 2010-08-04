public void put(WorkItem workItem) {
	// Order write in (1) before write in (2)
	int head, size, tag;
		
	while (true) {
		ArrayData arrayData = anchor.getReference();
		head = arrayData.head;
		size = arrayData.size;
		tag = anchor.getStamp();
		if (size == workItems.length)
			expand();
		else
			break;
		
	}
		
	// (1)
	workItems[(head + size) % workItems.length] = workItem;
		
	// (2)
	anchor.set(new ArrayData(head, size + 1), tag + 1);
}
