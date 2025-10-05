package cn.fr4nk;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallableFutureTaskDemo {

  public static void main(String[] args) {
    startFutureTaskByThread();
  }

  public static void startFutureTaskByThread() {
    FutureTask<Integer> task = new FutureTask<>(new Task());
    new Thread(task).start();
    try {
      log.info("result: {}", task.get());
    } catch (InterruptedException | ExecutionException e) {
      log.info("任务执行异常", e);
    }
  }

  public static void submmitFutureTaskByExecutor() {

    IntegerFutureTask task = new IntegerFutureTask(new Task());
    ExecutorService es = Executors.newFixedThreadPool(2);

    Future<?> res =  es.submit(task);
    try {
      var result = res.get(2, TimeUnit.SECONDS);
      if (result != null) {
        log.info("result: {}", result);
      }
    } catch ( Exception e) {
      log.info("任务执行异常", e);
      res.cancel( true);
      log.info("任务被取消");
    }
    es.shutdown();
  }

  static class Task implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
      try {
        Thread.sleep(1000);
        return 1;
      } catch (InterruptedException e) {
        log.info("响应外部中断");
        return null;
      }
    }
  }

  static class IntegerFutureTask extends FutureTask<Integer> {
    public IntegerFutureTask(Callable<Integer> callable) {
      super(callable);
    }
  }

}
