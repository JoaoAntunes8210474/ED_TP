/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collections.implementation;

import collections.exceptions.EmptyCollectionException;
import collections.interfaces.QueueADT;

/**
 * @param <T> Generic type
 */
public class CircularArrayQueue<T> implements QueueADT<T> {
    /**
     * The default size of the queue
     */
    private final int SIZE = 100;
    /**
     * The array that holds the queue
     */
    private T[] queue;
    /**
     * The number of elements in the queue
     */
    private int currentSize;
    /**
     * The index of the front and rear of the queue
     */
    private int front, rear;

    /**
     * Creates an empty queue using the default size.
     */
    public CircularArrayQueue() {
        this.currentSize = 0;
        this.front = this.rear = -1;
        this.queue = (T[]) (new Object[SIZE]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enqueue(T element) {
        if (this.currentSize == this.queue.length) {
            expandCapacity();
        } else {
            if (this.rear == this.front && this.front == -1) {
                this.front = 0;
            }
            this.rear = (rear + 1) % SIZE;
            this.queue[rear] = element;
            this.currentSize++;
        }
    }

    /**
     * Expands the capacity of the queue
     */
    private void expandCapacity() {
        int tam = SIZE + 1;
        T[] temp = (T[]) (new Object[tam]);
        for (int i = 0; i < this.currentSize; i++) {
            temp[i] = this.queue[i];
        }
        this.queue = temp;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T dequeue() throws EmptyCollectionException {
        T result = null;
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        if (this.front == queue.length - 1) {
            this.front = 0;
        } else {
            result = queue[front];
            if (this.front == this.rear) {
                this.front = this.rear = -1;
            } else {
                front = (front + 1) % SIZE;
                this.currentSize--;
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) throw new EmptyCollectionException("A lista está vazia");
        return queue[front];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return (this.front == -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return this.currentSize;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < currentSize; i++) {
            s += queue[i] + " | ";
        }
        return s;
    }
}
