package cn.fr4nk.thread;

import cn.fr4nk.tool.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeamonDemo {

  public static void main(String[] args) {
    Thread t = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(500);
          log.info("I am alive");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    t.setName("alive-deamon-thread");
    t.setDaemon(true);
    t.start();
    log.info("main thread will exit in 2s ");
    ThreadUtil.sleepCurrentThread(2000);
  }
}
