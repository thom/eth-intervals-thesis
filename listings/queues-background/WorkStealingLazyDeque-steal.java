public WorkItem steal() {
	synchronized (thief) { //*\label{lst:work-stealing-lazy-deque-steal-sync-1}
		final int head = thief.head;
		final int index = head % workItems.length;
		WorkItem workItem = workItems.get(index);

		if (!workItems.compareAndSet(index, workItem, null)) {
			workItem = null;
		}

		if (workItem == null) {
			return null; //*\label{lst:work-stealing-lazy-deque-steal-null}
		}

		thief.head++;
		return workItem; //*\label{lst:work-stealing-lazy-deque-steal-item}
	} //*\label{lst:work-stealing-lazy-deque-steal-sync-2}
}
