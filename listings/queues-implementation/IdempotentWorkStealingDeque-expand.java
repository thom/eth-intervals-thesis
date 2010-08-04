private void expand() {
	// Order writes in (1) and (2) before write in (3)
	// Order write in (3) before write in put:(2)
	ArrayData arrayData = anchor.getReference();
	int head = arrayData.head;
	int size = arrayData.size;
	
	// (1)
	WorkItem[] tempWorkItems = new WorkItem[2 * size];
	
	for (int i = 0; i < size; i++) {
		// (2)
		tempWorkItems[(head + i) % tempWorkItems.length] = 
			workItems[(head + i) % workItems.length];
	}
		
	// (3)
	workItems = tempWorkItems;
}
