interface WorkStealingQueue {
	public void put(WorkItem task);
	public WorkItem take();
	public WorkItem steal();
}
