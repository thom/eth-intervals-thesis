public class DynamicWorkStealingDeque implements WorkStealingQueue {
	class Node {
		static final int SIZE = 1024;
		WorkItem[] workItems = new WorkItem[SIZE];
		Node next, prev;
	}

	class Index {
		Node node;
		int index;

		Index(Node node, int index) {
			this.node = node;
			this.index = index;
		}
	}

	private Index bottom;
	private AtomicStampedReference<Index> top;

	public DynamicWorkStealingDeque() {
		Node node1 = new Node();
		Node node2 = new Node();
		node1.next = node2;
		node2.prev = node1;

		bottom = new Index(node1, Node.SIZE - 1);
		top = new AtomicStampedReference<Index>(
				new Index(node1, Node.SIZE - 1), 0);
	}

	// ...
}
