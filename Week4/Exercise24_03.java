// Look for WRITE YOUR CODE to write your code
import java.util.*;

public class Exercise24_03 {
    public static void main(String[] args) {
        new Exercise24_03();
    }

    public Exercise24_03() {
        TwoWayLinkedList<Double> list = new TwoWayLinkedList<>();
        System.out.print("Enter five numbers: ");
        Scanner input = new Scanner(System.in);
        double[] v = new double[5];
        for (int i = 0; i < 5; i++)
            v[i] = input.nextDouble();

        list.add(v[1]);
        list.add(v[2]);
        list.add(v[3]);
        list.add(v[4]);
        list.add(0, v[0]);
        list.add(2, 10.55);
        list.remove(3);

        java.util.ListIterator<Double> iterator1 = list.listIterator();
        System.out.print("The list in forward order: ");
        while (iterator1.hasNext())
            System.out.print(iterator1.next() + " ");

        java.util.ListIterator<Double> iterator2 = list.listIterator(list.size() - 1);
        System.out.print("\nThe list in backward order: ");
        while (iterator2.hasPrevious())
            System.out.print(iterator2.previous() + " ");
    }
}

interface MyList<E> extends java.util.Collection<E> {
    /** Add a new element at the specified index in this list */
    public void add(int index, E e);

    /** Return the element from this list at the specified index */
    public E get(int index);

    /** Return the index of the first matching element in this list.
     *  Return -1 if no match. */
    public int indexOf(Object e);

    /** Return the index of the last matching element in this list
     *  Return -1 if no match. */
    public int lastIndexOf(E e);

    /** Remove the element at the specified position in this list
     *  Shift any subsequent elements to the left.
     *  Return the element that was removed from the list. */
    public E remove(int index);

    /** Replace the element at the specified position in this list
     *  with the specified element and returns the new set. */
    public E set(int index, E e);

    @Override /** Add a new element at the end of this list */
    public default boolean add(E e) {
        add(size(), e);
        return true;
    }

    @Override /** Return true if this list contains no elements */
    public default boolean isEmpty() {
        return size() == 0;
    }

    @Override /** Remove the first occurrence of the element e
     *  from this list. Shift any subsequent elements to the left.
     *  Return true if the element is removed. */
    public default boolean remove(Object e) {
        if (indexOf(e) >= 0) {
            remove(indexOf(e));
            return true;
        }
        else
            return false;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        // Left as an exercise
        return true;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
        // Left as an exercise
        return true;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        // Left as an exercise
        return true;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        // Left as an exercise
        return true;
    }

    @Override
    public default Object[] toArray() {
        // Left as an exercise
        return null;
    }

    @Override
    public default <T> T[] toArray(T[] array) {
        // Left as an exercise
        return null;
    }
}

//
class TwoWayLinkedList<E> implements MyList<E> {
    private Node<E> head, tail;
    private int size;

    /** Create a default list */
    public TwoWayLinkedList() {
    }

    /** Create a list from an array of objects */
    public TwoWayLinkedList(E[] objects) {
        for (E e : objects)
            add(e);
    }

    /** Return the head element in the list */
    public E getFirst() {
        if (size == 0) return null;
        return head.element;
    }

    /** Return the last element in the list */
    public E getLast() {
        if (size == 0) return null;
        return tail.element;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        Node<E> current = head;

        for (int i = 0; i < size; i++) {
            result.append(current.element);
            current = current.next;
            if (current != null) result.append(", ");
            else result.append("]");
        }

        return result.toString();
    }

    /** Clear the list */
    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    /** Return true if this list contains the element e */
    @Override
    public boolean contains(Object e) {
        return indexOf(e) != -1;
    }

    /** Return the element from this list at the specified index */
    @Override
    public E get(int index) {
        checkIndex(index);
        return getNode(index).element;
    }

    /** Return the index of the first matching element in this list. Return -1 if no match. */
    @Override
    public int indexOf(Object e) {
        int index = 0;
        for (Node<E> current = head; current != null; current = current.next) {
            if (Objects.equals(e, current.element)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /** Return the index of the last matching element in this list. Return -1 if no match. */
    public int lastIndexOf(Object e) {
        int index = size - 1;
        for (Node<E> current = tail; current != null; current = current.previous) {
            if (Objects.equals(e, current.element)) {
                return index;
            }
            index--;
        }
        return -1;
    }

    /** Replace the element at the specified position in this list with the specified element. */
    @Override
    public E set(int index, E e) {
        checkIndex(index);
        Node<E> node = getNode(index);
        E oldValue = node.element;
        node.element = e;
        return oldValue;
    }

    // iterator
    private class LinkedListIterator implements java.util.ListIterator<E> {
        private Node<E> current;
        private int currentIndex;

        public LinkedListIterator() {
            current = head;
            currentIndex = 0;
        }

        // sets cursor to the element at "index"
        public LinkedListIterator(int index) {
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

            current = getNode(index);
            currentIndex = index;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E value = current.element;
            current = current.next;
            currentIndex++;
            return value;
        }

        @Override
        public boolean hasPrevious() {
            return current != null;
        }

        @Override
        public E previous() {
            E value = current.element;
            current = current.previous;
            currentIndex--;
            return value;
        }

        @Override
        public int nextIndex() {
            return currentIndex;
        }

        @Override
        public int previousIndex() {
            return currentIndex - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() not implemented");
        }

        @Override
        public void add(E e) {
            throw new UnsupportedOperationException("add() not implemented");
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException("set() not implemented");
        }
    }

    private class Node<E> {
        E element;
        Node<E> next;
        Node<E> previous;

        public Node(E o) {
            element = o;
        }
    }

    @Override
    public int size() {
        return size;
    }

    public ListIterator<E> listIterator() {
        return new LinkedListIterator();
    }

    public ListIterator<E> listIterator(int index) {
        return new LinkedListIterator(index);
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public E next() {
                E value = current.element;
                current = current.next;
                return value;
            }
        };
    }

    // doubly linked operations

    /** Add an element to the beginning of the list */
    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);

        if (size == 0) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }

        size++;
    }

    /** Add an element to the end of the list */
    public void addLast(E e) {
        Node<E> newNode = new Node<>(e);

        if (size == 0) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.previous = tail;
            tail = newNode;
        }

        size++;
    }

    /** Add a new element at the specified index in this list */
    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        if (index == 0) {
            addFirst(e);
        } else if (index == size) {
            addLast(e);
        } else {
            Node<E> nextNode = getNode(index);
            Node<E> prevNode = nextNode.previous;

            Node<E> newNode = new Node<>(e);
            newNode.next = nextNode;
            newNode.previous = prevNode;

            prevNode.next = newNode;
            nextNode.previous = newNode;

            size++;
        }
    }

    /** Remove the head node and return the object that is contained in the removed node. */
    public E removeFirst() {
        if (size == 0) return null;

        E removedValue = head.element;

        if (size == 1) {
            head = tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }

        size--;
        return removedValue;
    }

    /** Remove the last node and return the object that is contained in the removed node. */
    public E removeLast() {
        if (size == 0) return null;

        E removedValue = tail.element;

        if (size == 1) {
            head = tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }

        size--;
        return removedValue;
    }

    /** Remove the element at the specified position in this list. */
    @Override
    public E remove(int index) {
        checkIndex(index);

        if (index == 0) return removeFirst();
        if (index == size - 1) return removeLast();

        Node<E> nodeToRemove = getNode(index);
        Node<E> prevNode = nodeToRemove.previous;
        Node<E> nextNode = nodeToRemove.next;

        prevNode.next = nextNode;
        nextNode.previous = prevNode;

        size--;
        return nodeToRemove.element;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    // gets the node at index (walk from head or tail depending on which is closer)
    private Node<E> getNode(int index) {
        Node<E> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) current = current.next;
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) current = current.previous;
        }
        return current;
    }
}
