package cn.fr4nk.tool;

public class ThreadUtil {

  public static void sleepCurrentThread(int mils) {
    try {
      Thread.sleep(mils);
    } catch (InterruptedException e) {
    }
  }
}
