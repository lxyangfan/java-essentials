package cn.fr4nk.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InterruptDemo {


  public static void main(String[] args) {
    blockingThreadThrowInterruptException(args);
    activelyCheckInterruptStatus(args);
  }

  public static void activelyCheckInterruptStatus(String[] args) {
    RunningThread runningThread = new RunningThread();
    runningThread.setDaemon(false);
    runningThread.setName("running-thread");

    new Thread(() -> {
      log.info("{} is running", Thread.currentThread().getName());
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
      log.info("interrupt running-thread");
      runningThread.interrupt();
    }).start();
    runningThread.start();
  }

  public static void blockingThreadThrowInterruptException(String[] args) {
    BlockingThread th  = new BlockingThread();
    th.setDaemon(false);
    th.setName("blocking-thread");

    new Thread(() -> {
      log.info("{} is running", Thread.currentThread().getName());
      try {
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        return;
      }
      log.info("interrupt blcoking-thread");
      th.interrupt();
    }).start();
    th.start();
  }

  static class BlockingThread extends Thread {
    @Override
    public void run() {
      while (true) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          log.info("blocking-thread interrupted, Exit normally");
          return;
        }
      }
    }
  }

  static class RunningThread extends Thread {
    @Override
    public void run() {
      while (true) {
        log.info("RunningThread running");
        if (Thread.currentThread().isInterrupted()) {
          log.info("RunningThread interrupted, Exit normally");
          return;
        }
      }
    }
  }


}
