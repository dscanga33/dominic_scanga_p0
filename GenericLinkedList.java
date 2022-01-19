package bankapp;


import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class GenericLinkedList <T> {

    public Node<T> head = null;
    public Node<T> tail = null;

    private int size = 0;

    public void add(T data) {

        Node<T> newNode = new Node<T>(data);

        if(head == null) {
            //If the list is empty, both the head and tail will point to the first Node
            head = newNode;
            tail = newNode;
        } else {
            //First update the current tail's next reference to this new element/Node.
            tail.next = newNode;
            //Then update the tail to reference this new Node.
            tail = newNode;
        }

        size++;
    }

    public int getSize() {
        return size;
    }

    public T get(int index) {

        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or above the List's size");
        }

        Node<T> iterator = head;

        for(int i = 0; i < index; i++) {
            iterator = iterator.next;
        }

        return iterator.data;
    }

    public T find(int id) {

        Node<T> iterator = head;

        for(int i = 0; i < size; i++) {
            T element = iterator.data;
            try {
                if ((int) element.getClass().getMethod("getId").invoke(element) == id) {
                    return element;
                }
            } catch (NoSuchMethodException e) {
                continue;
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            iterator = iterator.next;
        }

        return null;

    }

    public T remove(int index) {

        if(index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or above the List's size");
        }

        Node<T> iterator = head;

        //If the index of where to remove is at the start of the list
        //then update the head to the next node, then remove this node as having any references
        //i.e. set its next to null. Then return the removed element.
        if(index == 0) {
            head = iterator.next;
            iterator.next = null;
            size--;
            return iterator.data;
        }
        //Should get us to the Node right before the one we want to remove.
        for(int i = 0; i < index - 1; i++) {
            iterator = iterator.next;
        }
        //Holding onto the values of the 1) to-be-deleted Node for the sake of returning that value.
        //2) the node that the to-be-deleted Node references (using next)
        Node<T> returnNode = iterator.next;
        Node<T> newNext = returnNode.next;

        iterator.next.next = null;
        iterator.next = newNext;

        size--;
        return returnNode.data;
    }

    public String toString() {

        String result = "(";

        Node<T> current = head;

        while(current != null) {
            result += current.data;

            if(current != tail) {
                result += ", ";
            }

            current = current.next;
        }

        result += ")";
        return result;
    }
}
