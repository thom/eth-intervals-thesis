public WorkItem take() {
	ArrayData arrayData = anchor.getReference();
	int head = arrayData.head;
	int size = arrayData.size;
	int tag = anchor.getStamp();
	
	if (size == 0)
		return null;
		
	WorkItem workItem = 
		workItems[(head + size - 1) % workItems.length];
	anchor.set(new ArrayData(head, size - 1), tag);
	return workItem;
}
