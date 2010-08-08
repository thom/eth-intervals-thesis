public void put(WorkItem workItem) {
	int head, size, tag;
	while (true) {
		ArrayData arrayData = anchor.getReference(); //*\label{lst:idempotent-work-stealing-deque-put-read-1}
		head = arrayData.head;
		size = arrayData.size;
		tag = anchor.getStamp(); //*\label{lst:idempotent-work-stealing-deque-put-read-2}
		if (size == workItems.length) //*\label{lst:idempotent-work-stealing-deque-put-full}
			expand(); //*\label{lst:idempotent-work-stealing-deque-put-expand}
		else
			break;
	}
		
	workItems[(head + size) % workItems.length] = workItem; //*\label{lst:idempotent-work-stealing-deque-put-insert}
	anchor.set(new ArrayData(head, size + 1), tag + 1); //*\label{lst:idempotent-work-stealing-deque-put-update-anchor}
}
