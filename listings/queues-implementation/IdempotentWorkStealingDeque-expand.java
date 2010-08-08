private void expand() {
	ArrayData arrayData = anchor.getReference();
	int head = arrayData.head;
	int size = arrayData.size;
	WorkItem[] tempWorkItems = new WorkItem[2 * size]; //*\label{lst:idempotent-work-stealing-deque-expand-new-array}
	
	for (int i = 0; i < size; i++) //*\label{lst:idempotent-work-stealing-deque-expand-copy-1}
		tempWorkItems[(head + i) % tempWorkItems.length] = 
			workItems[(head + i) % workItems.length]; //*\label{lst:idempotent-work-stealing-deque-expand-copy-2}

	workItems = tempWorkItems; //*\label{lst:idempotent-work-stealing-deque-expand-assign}
}
