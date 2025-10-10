package cn.fr4nk.consumer;

public interface Consumer<T> {

  void consume(T item);
}
