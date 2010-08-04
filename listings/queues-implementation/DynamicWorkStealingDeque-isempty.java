private boolean isEmpty(Index currentBottom, Index currentTop) {
	// Same node
	if (currentBottom.node == currentTop.node) {
		// Same cell
		if (currentBottom.index == currentTop.index)
			return true;

		// Simple crossing
		if (currentTop.index - currentBottom.index == 1)
			return true;
	}
	// Neighboring nodes
	else if (currentBottom.node.prev == currentTop.node) {
		// Simple crossing
		if ((currentTop.index == 0)
				&& (currentBottom.index == Node.SIZE - 1))
			return true;
	}

	// Not empty
	return false;
}
