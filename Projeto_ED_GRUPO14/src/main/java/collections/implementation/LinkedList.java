
package collections.implementation;


import collections.exceptions.NullException;
import java.util.Objects;

/**
 *
 */
public class LinkedList<T> {
    int count;
    LinkedNode<T> head, tail;

    public LinkedList() {
        this.count = 0;
        this.head = null;
        this.tail = null;
    }
    
    public void add(T element) {
        //1º Definir LinkedNode
        LinkedNode<T> newNode = new LinkedNode<T>(element);
        newNode.setElement(element);
        if(this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        }
        else {
            newNode.setNext(head);
            head = newNode;   
        }
        this.count++;
    }
    
    
    
    public void remove(T element) throws NullException {
        // Armazenar início da lista ligada
        LinkedNode<T> temp = this.head;
        LinkedNode<T> prev = null;
        
        if(this.count == 0) throw new NullException("Não existem elementos na lista ligada");
        
        if (temp != null && temp.getElement() == element) {
            head = temp.getNext();
        }
        
        //Procura o elemento e guarda o previous
        while (temp != null && temp.getElement() != element) {
            prev = temp;
            temp = temp.getNext();
        }
        
        //1º Caso - Só existe um elemento
        if(this.head == this.tail) {
            this.head = null;
            this.tail = null;
        }
        //2º Caso - Verificar se o anterior é o head  
        if (prev == null) {
            this.head = temp.getNext();
        }
        //3º Caso - Verificar se o anterior é o tail
        //4º Caso - Verificar se o anterior está no meio
        else {
            prev.setNext(temp.getNext());
            if(prev.getNext() == null) {
                tail = prev;
            }
        }
        
        this.count--;
    }

    @Override
    public String toString() {
        LinkedNode<T> current = head;
        String s = "LinkedList:\n";
        while(current != null) {
            s += current.getElement().toString() + "\n";
            current = current.getNext();
        }
        return s;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LinkedList<?> other = (LinkedList<?>) obj;
        if (this.count != other.count) {
            return false;
        }
        if (!Objects.equals(this.head, other.head)) {
            return false;
        }
        if (!Objects.equals(this.tail, other.tail)) {
            return false;
        }
        return true;
    }
    
    
}

