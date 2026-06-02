package ADT.Exercise1;

public class Main {
    public static void main(String[] args) {
        QueueADT queue = new LinkedQueue();  // or LinkedQueue later
        queue.enqueue(10);
        queue.enqueue(20);
        System.out.println(queue.dequeue());  // 10
    }
}