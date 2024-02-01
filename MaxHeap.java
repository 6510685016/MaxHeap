package MaxHeap;

// MaxHeap.java
public class MaxHeap {
    private Node[] A;
    private int n;

    public MaxHeap(int capacity) {
        A = new Node[capacity];
        n = 0;
    }

    // เพิ่มโหนด i ลงในตำแหน่งสุดท้ายของ Heap แล้วสลับขึ้นบน O(log n)
    public void insert(Node i) {
        int p = n++;
        while ((p != 0) && (A[parent(p)].key < i.key)) {
            A[p] = A[parent(p)];
            p = parent(p);
        }
        A[p] = i;
    }

    // A จะเรียงจาก น้อย -> มาก ! จะไม่เป็น MaxHeap อีก
    public void heapSort() {
        buildMaxHeap();
        for (int i = n - 1; i >= 1; i--) {
            swap(0, i);
            maxHeapify(0, i);
        }
    }

    // ลบโหนดที่มีค่ามากที่สุด (root) และ minHeapify O(log n)
    public Node removeMax() throws HeapEmpty {
        if (n < 1) {
            throw new HeapEmpty("Heap is empty");
        }
        Node max = A[0];
        A[0] = A[--n];
        maxHeapify(0, n);
        return max;
    }

    // สร้าง Max Heap จากอาร์เรย์ AO(n)
    private void buildMaxHeap() {
        for (int i = (n / 2) - 1; i >= 0; i--) {
            maxHeapify(i, n);
        }
    }

    // ปรับค่ามากที่สุด(ในหมู่ i และลูกๆของ i) มาอยู่ที่ตำแหน่ง i และเรียง O(log n)
    private void maxHeapify(int i, int heapSize) {
        int l, r, largest;
        l = leftChild(i);
        r = rightChild(i);

        if ((l < heapSize) && (A[l].key > A[i].key)) {
            largest = l;
        } else {
            largest = i;
        }

        if ((r < heapSize) && (A[r].key > A[largest].key)) {
            largest = r;
        }

        if (largest != i) {
            swap(i, largest);
            maxHeapify(largest, heapSize);
        }
    }

    private void swap(int i, int j) {
        Node temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }

    public static void main(String[] args) {
        try {
            // สร้าง MaxHeap
            MaxHeap maxHeap = new MaxHeap(10);

            // เพิ่มโหนด
            maxHeap.insert(new Node(10));
            maxHeap.insert(new Node(8));
            maxHeap.insert(new Node(3));
            maxHeap.insert(new Node(5));

            // ทดสอบ Heap Sort
            System.out.println("Heap before sorting:");
            printHeap(maxHeap);
            maxHeap.heapSort();
            System.out.println("\nHeap after sorting:");
            printHeap(maxHeap);
            maxHeap.buildMaxHeap();
            System.out.println("\nHeap after building Min Heap:");
            printHeap(maxHeap);

            // ทดสอบการลบโหนดที่มีค่ามากที่สุด
            Node removedNode = maxHeap.removeMax();
            System.out.println("\nRemoved node with key: " + removedNode.key);
            System.out.println("Heap after removal:");
            printHeap(maxHeap);

        } catch (HeapEmpty e) {
            System.out.println("Heap is empty: " + e.getMessage());
        }
    }

    private static void printHeap(MaxHeap maxHeap) {
        for (int i = 0; i < maxHeap.n; i++) {
            System.out.print(maxHeap.A[i].key + " ");
        }
        System.out.println();
    }

}
