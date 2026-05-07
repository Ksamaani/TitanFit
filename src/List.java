public class List<V> {
    private Node<V> firstNode;
    private Node<V> lastNode;
    private String name;

    public List() {
        this("list");
    }

    public List(String listName) {
        name = listName;
        firstNode = lastNode = null;
    }

    public boolean isEmpty() {
        return firstNode == null;
    }

    public void insertAtFront(V insertItem) {
        if (isEmpty()) {
            firstNode = lastNode = new Node<V>(insertItem);
        } else {
            firstNode = new Node<V>(insertItem, firstNode);
        }
    }

    public void insertAtBack(V insertItem) {
        if (isEmpty()) {
            firstNode = lastNode = new Node<V>(insertItem);
        } else {
            lastNode = lastNode.nextNode = new Node<V>(insertItem);
        }
    }

    public V removeFromFront() {
        if (isEmpty()) {
            return null; // Added safety check
        }
        V removedItem = firstNode.data;
        if (firstNode == lastNode) {
            firstNode = lastNode = null;
        } else {
            firstNode = firstNode.nextNode;
        }
        return removedItem;
    }

    public V removeFromBack() {
        if (isEmpty()) {
            return null; // Added safety check
        }
        V removedItem = lastNode.data;
        if (firstNode == lastNode) {
            firstNode = lastNode = null;
        } else {
            Node<V> current = firstNode;
            while (current.nextNode != lastNode) {
                current = current.nextNode;
            }
            lastNode = current;
            current.nextNode = null;
        }
        return removedItem;
    }

    public Node<V> getFirstNode() {
        return firstNode;
    }

    public void print() {
        if (isEmpty()) {
            System.out.printf("Empty %s\n", name);
            return;
        }
        System.out.printf("The %s is: ", name);
        Node<V> current = firstNode;
        while (current != null) {
            System.out.printf("%s\n", current.data);
            current = current.nextNode;
        }
        System.out.println("\n");
    }
}