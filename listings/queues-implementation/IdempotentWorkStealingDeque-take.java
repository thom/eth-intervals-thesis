public WorkItem take() {
	ArrayData arrayData = anchor.getReference(); //*\label{lst:idempotent-work-stealing-deque-take-read-anchor-1}
	int head = arrayData.head;
	int size = arrayData.size; 
	int tag = anchor.getStamp(); //*\label{lst:idempotent-work-stealing-deque-take-read-anchor-2}
	
	if (size == 0) { //*\label{lst:idempotent-work-stealing-deque-take-check-size}
		return null;
	}
		
	WorkItem workItem = 
		workItems[(head + size - 1) % workItems.length]; //*\label{lst:idempotent-work-stealing-deque-take-get}

	anchor.set(new ArrayData(head, size - 1), tag); //*\label{lst:idempotent-work-stealing-deque-take-update-anchor}

	return workItem;
}
