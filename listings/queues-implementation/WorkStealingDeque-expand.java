private WorkItem[] expand(WorkItem[] currentWorkItems, 
		int bottom, int top) {
	WorkItem[] newWorkItems = 
		new WorkItem[currentWorkItems.length * 2];

	for (int i = top; i < bottom; i++) {
		newWorkItems[i % newWorkItems.length] = 
			currentWorkItems[i % currentWorkItems.length];
	}

	return newWorkItems;
}
