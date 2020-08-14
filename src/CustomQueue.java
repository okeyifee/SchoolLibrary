import java.util.Arrays;
import java.util.NoSuchElementException;

public class CustomQueue<T> {
    private int front = 0;
    private int rear = -1;
    private int size = 0;
    private static final int INITIAL_CAPACITY = 2;
    private Object[] elements;

    public CustomQueue() {
        elements = new Object[INITIAL_CAPACITY];
    }

    public void add(T t) {
        if (size == elements.length) {
            increaseCapacity();
        }
        elements[++rear] = t;
        ++size;
    }

    public Object[] getAllQueueMembers() {
        return elements;
    }

    public String printQueue() {
        StringBuilder sb = new StringBuilder();

        sb.append('[');
        for(int i = 0; i < size; i++) {
            sb.append(elements[i].toString());
            if(i != size - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    public int size() {
        return size;
    }

    public T remove() throws Exception {
        if (size == 0) {
            throw new NoSuchElementException("There is no one waiting to borrow a book");
        } else {
            if (front == elements.length) {
                System.out.println("All members of the queue have been settled");
                return null;
            }
            T removedElement = (T) elements[front];
            front++;
            size--;
            return removedElement;
        }
    }

    private void increaseCapacity() {
        elements = Arrays.copyOf(elements, INITIAL_CAPACITY * 2);
    }
}
