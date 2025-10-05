package cn.fr4nk.thread;

import cn.fr4nk.tool.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JoinDemo {

  public static void main(String[] args) {
    heavyJobWaitAndNotify(args);
  }


  public static void heavyJobWaitAndNotify(String[] args) {
    HeavyJobWaitForLock hj = new HeavyJobWaitForLock();
    hj.setName("heavy-job");
    hj.start();
    new Thread(() -> {
      ThreadUtil.sleepCurrentThread(5000);
      synchronized (lock) {
        log.info("got lock and notify");
        lock.notify();
      }
    }).start();
    try {
      log.info("{} join for heavy job", Thread.currentThread().getName());
      hj.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

  }
  private static final Object lock = new Object();
  static class HeavyJobWaitForLock extends Thread {
    @Override
    public void run() {
      log.info("start heavy job");

      synchronized (lock) {
        try {
          log.info("got lock and wait ...");
          lock.wait();
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
      log.info("end heavy job");
    }
  }
  
  static class HeavyJob extends Thread {
    @Override
    public void run() {
      log.info("start heavy job");
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        log.info("interrupt heavy job");
      }
      log.info("end heavy job");
    }
  }

  public static void mainThreadJoinHeavyJob(String[] args) {
    HeavyJob hj = new HeavyJob();

    log.info("start main thread, and wait for heavy job");
    hj.start();
    try {
      hj.join();
    } catch (InterruptedException e) {
      log.info("main thread interrupted");
    }
  }
}
