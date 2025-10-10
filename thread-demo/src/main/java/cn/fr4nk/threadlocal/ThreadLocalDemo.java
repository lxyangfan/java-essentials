package cn.fr4nk.threadlocal;

import cn.fr4nk.tool.ThreadUtil;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalDemo {

  private static final AtomicLong tidAtomic = new AtomicLong(1);
  private static final ThreadLocal<String> tid = ThreadLocal.withInitial(() -> "tid-" + tidAtomic.getAndIncrement());

  private static final ThreadLocal<String> user = new InheritableThreadLocal<>();
  public static void main(String[] args) {
    threadlocalUsed( args);
    for (int i = 0; i < 10; i++) {
      ThreadUtil.ThreadPoolWithStartTime.threadPool.execute(() -> log.info("do nothing but init"));
    }

    user.set("user-frank");
    var th1 = new Thread(ThreadLocalDemo::crossFunctionPassParams);
    th1.start();
    try {
      th1.join();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    log.info("threadLocal in main user: {}", user.get());

    ThreadUtil.ThreadPoolWithStartTime.threadPool.execute(() -> {
      // inherit threadlocal not work in thread pool
      if (user == null) {
        log.info("threadLocal in thread pool user is: null");
      } else {
        log.info("threadLocal in thread pool user: {}", user.get());
        ThreadUtil.sleepCurrentThread(300);
      }
    });
  }

  public static void threadlocalUsed(String[] args) {

    var t1 = new Thread(ThreadLocalDemo::someFunction);
    var t2 = new Thread(ThreadLocalDemo::someFunction);

    t1.start();
    t2.start();

    try {
      t1.join();
      t2.join();
    } catch (InterruptedException e) {
    }

  }

  public static void crossFunctionPassParams() {
    if (user.get() == null) {
      log.error("user not in context");
    } else {
      log.info("in cross function user: {}", user.get());
      user.remove();
    }
  }

  static void someFunction() {
    log.info("tid: {}", tid.get());
    tid.remove();
  }


}
