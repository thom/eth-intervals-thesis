import java.util.concurrent.atomic.AtomicInteger;

public class WorkStealingDeque 
		implements WorkStealingQueue {
	private volatile WorkItem[] tasks = new WorkItem[1024];
	private volatile int bottom = 0;
	private AtomicInteger top = new AtomicInteger(0);

	public void put(WorkItem task) {
		int oldBottom = bottom;
		int oldTop = top.get();
		WorkItem[] currentTasks = tasks;
		int size = oldBottom - oldTop;
		if (size >= currentTasks.length - 1) {
			currentTasks = expand(
				currentTasks, oldBottom, oldTop
			);
			tasks = currentTasks;
		}
		currentTasks[oldBottom % currentTasks.length] = task;
		bottom = oldBottom + 1;
	}

	public WorkItem steal(Worker thiefWorker) {
		int oldTop, oldBottom;
		WorkItem task;
		
		while (true) {
			// important that top read before bottom
			oldTop = top.get();
			oldBottom = bottom;
			WorkItem[] currentTasks = tasks;
			int size = oldBottom - oldTop;
			
			if (size <= 0)
				return null; // empty
			
			task = currentTasks[oldTop % currentTasks.length];
			
			 // fetch and increment
			if (top.compareAndSet(oldTop, oldTop + 1))
				break;
		}
		
		return task;
	}

	public WorkItem take() {
		int oldBottom = this.bottom;
		WorkItem[] currentTasks = tasks;
		oldBottom = oldBottom - 1;
		this.bottom = oldBottom;
		int oldTop = top.get();
		int size = oldBottom - oldTop;

		if (size < 0) {
			bottom = oldTop;
			return null;
		}

		WorkItem task = currentTasks[
			bottom % currentTasks.length
		];

		if (size > 0) {
			return task;
		}

		// fetch and increment
		if (!top.compareAndSet(oldTop, oldTop + 1))
			task = null; // queue is empty

		bottom = oldTop + 1;
		return task;
	}

	private WorkItem[] expand(WorkItem[] currentTasks, 
			int bottom, int top) {
		WorkItem[] newTasks = new WorkItem[
			currentTasks.length * 2
		];

		for (int i = top; i < bottom; i++) {
			newTasks[i % newTasks.length] = currentTasks[i
					% currentTasks.length];
		}

		return newTasks;
	}
}
