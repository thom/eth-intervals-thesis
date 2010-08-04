import java.util.concurrent.atomic.AtomicStampedReference;

public class IdempotentWorkStealingDeque 
		implements WorkStealingQueue {
	class ArrayData {
		final int head, size;

		public ArrayData(int head, int size) {
			this.head = head;
			this.size = size;
		}
	}

	private AtomicStampedReference<ArrayData> anchor;
	private WorkItem[] workItems;

	public IdempotentWorkStealingDeque() {
		anchor = new AtomicStampedReference<ArrayData>(
			new ArrayData(0, 0), 0);
		workItems = new WorkItem[1024];
	}

	public void put(WorkItem workItem) {
		// Order write in (1) before write in (2)
		int head, size, tag;
		
		while (true) {
			ArrayData arrayData = anchor.getReference();
			head = arrayData.head;
			size = arrayData.size;
			tag = anchor.getStamp();
			if (size == workItems.length) {
				expand();
			} else {
				break;
			}
		}
		
		// (1)
		workItems[(head + size) % workItems.length] = workItem;
		
		// (2)
		anchor.set(new ArrayData(head, size + 1), tag + 1);
	}

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
			if (size == 0) {
				return null;
			}
			
			// (2)
			WorkItem[] tempWorkItems = workItems;
			
			// (3)
			workItem = tempWorkItems[head % tempWorkItems.length];
			int newHead = head + 1 % Integer.MAX_VALUE;
			
			// (4)
			if (anchor.compareAndSet(arrayData,
					new ArrayData(newHead, size - 1), tag, tag)) {
				break;
			}
		}
		
		return workItem;
	}

	public WorkItem take() {
		ArrayData arrayData = anchor.getReference();
		int head = arrayData.head;
		int size = arrayData.size;
		int tag = anchor.getStamp();
		
		if (size == 0) {
			return null;
		}
		
		WorkItem workItem = 
			workItems[(head + size - 1) % workItems.length];
		anchor.set(new ArrayData(head, size - 1), tag);
		return workItem;
	}

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
}
