package cn.fr4nk.thread;

import cn.fr4nk.tool.ThreadUtil;

public class TwoCrossPrintThread {

  public static void main(String[] args) {

    Object lock = new Object();

    Runnable printAplha = () -> {
      for (char alphabet = 'A'; alphabet <= 'Z'; alphabet++) {
        synchronized (lock) {
          lock.notify();
          System.out.print(alphabet);
          try {
            lock.wait();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
      }
    };

    Runnable printtNum = () -> {
      for (int i = 1; i <= 26; i++) {
        synchronized (lock) {
          try {
            lock.wait();
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
          System.out.print(i);
          lock.notify();
        }
      }
    };

    new Thread(printtNum).start();
    // 确保让打印数字的线程先启动，然后再 wait
    ThreadUtil.sleepCurrentThread(10);
    new Thread(printAplha).start();

  }

}
