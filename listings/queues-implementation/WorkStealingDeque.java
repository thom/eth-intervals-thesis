import java.util.concurrent.atomic.AtomicInteger;

public class WorkStealingDeque 
		implements WorkStealingQueue {
	private volatile WorkItem[] workItems = 
		new WorkItem[1024];
	private volatile int bottom = 0;
	private AtomicInteger top = new AtomicInteger(0);

	public void put(WorkItem workItem) {
		int oldBottom = bottom;
		int oldTop = top.get();
		WorkItem[] currentWorkItems = workItems;
		int size = oldBottom - oldTop;
		if (size >= currentWorkItems.length - 1) {
			currentWorkItems = expand(
				currentWorkItems, oldBottom, oldTop
			);
			workItems = currentWorkItems;
		}
		currentWorkItems[oldBottom % currentWorkItems.length] = 
			workItem;
		bottom = oldBottom + 1;
	}

	public WorkItem steal(Worker thiefWorker) {
		int oldTop, oldBottom;
		WorkItem workItem;
		
		while (true) {
			// important that top read before bottom
			oldTop = top.get();
			oldBottom = bottom;
			WorkItem[] currentWorkItems = workItems;
			int size = oldBottom - oldTop;
			
			if (size <= 0)
				return null; // empty
			
			workItem = currentWorkItems[oldTop % 
				currentWorkItems.length];
			
			 // fetch and increment
			if (top.compareAndSet(oldTop, oldTop + 1))
				break;
		}
		
		return workItem;
	}

	public WorkItem take() {
		int oldBottom = this.bottom;
		WorkItem[] currentWorkItems = workItems;
		oldBottom = oldBottom - 1;
		this.bottom = oldBottom;
		int oldTop = top.get();
		int size = oldBottom - oldTop;

		if (size < 0) {
			bottom = oldTop;
			return null;
		}

		WorkItem workItem = currentWorkItems[
			bottom % currentWorkItems.length
		];

		if (size > 0) {
			return workItem;
		}

		// fetch and increment
		if (!top.compareAndSet(oldTop, oldTop + 1))
			workItem = null; // queue is empty

		bottom = oldTop + 1;
		return workItem;
	}

	private WorkItem[] expand(WorkItem[] currentWorkItems, 
			int bottom, int top) {
		WorkItem[] newWorkItems = new WorkItem[
			currentWorkItems.length * 2
		];

		for (int i = top; i < bottom; i++) {
			newWorkItems[i % newWorkItems.length] = 
				currentWorkItems[i % currentWorkItems.length];
		}

		return newWorkItems;
	}
}
