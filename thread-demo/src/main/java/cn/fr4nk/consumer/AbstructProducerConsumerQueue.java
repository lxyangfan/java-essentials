package cn.fr4nk.consumer;

import java.util.LinkedList;
import java.util.List;

public class AbstructProducerConsumerQueue <T> {

  private int capacity;
  private LinkedList<T> queue;

  private List<Consumer<T>> consumers;

  public AbstructProducerConsumerQueue(int capacity) {
    this.capacity = capacity;
    queue = new LinkedList<>();
  }

  public void addConsumer(Consumer<T> consumer) {
    consumers.add(consumer);
  }

  public void produce(T t) {
    synchronized (queue) {
      while (queue.size() >= capacity) {
        try {
          queue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      queue.add(t);
      queue.notifyAll();
    }
  }

  public T consume() {
    synchronized (queue) {
      while (queue.isEmpty()) {
        try {
          queue.wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      T t = queue.remove();
      queue.notifyAll();
      return t;
    }
  }
}
