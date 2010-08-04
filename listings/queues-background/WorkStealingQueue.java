interface WorkStealingQueue {
	public void put(WorkItem workItem);
	public WorkItem take();
	public WorkItem steal();
}
