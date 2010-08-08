public WorkItem steal(Worker thiefWorker) {
	while (true) {
		ArrayData arrayData = anchor.getReference(); //*\label{lst:idempotent-work-stealing-deque-steal-read-anchor-1}
		int head = arrayData.head;
		int size = arrayData.size; 
		int tag = anchor.getStamp(); //*\label{lst:idempotent-work-stealing-deque-steal-read-anchor-2}
		if (size == 0) //*\label{lst:idempotent-work-stealing-deque-steal-check-size}
			return null;

		WorkItem[] tempWorkItems = workItems;
		WorkItem workItem = tempWorkItems[head % tempWorkItems.length]; //*\label{lst:idempotent-work-stealing-deque-steal-read}
		int newHead = head + 1 % Integer.MAX_VALUE;

		if (anchor.compareAndSet(arrayData,
				new ArrayData(newHead, size - 1), tag, tag))  //*\label{lst:idempotent-work-stealing-steal-cas}
			return workItem;
	}
}
