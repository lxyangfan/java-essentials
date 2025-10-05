package cn.fr4nk.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadStatusDemo {

  public static void main(String[] args) {
    Thread th = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(500);
          log.info("I am alive");
        } catch (InterruptedException e) {
          log.info("I am interrupted");
          break;
        }
      }
    });
  }
}
