package cn.fr4nk.executors;

import cn.fr4nk.tool.ThreadUtil;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScheduleExecutorDemo {

  public static void scheduleAtFixRate(String[] args) {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    ses.scheduleAtFixedRate(() -> {
      log.info("scheduleAtFixedRate");
    }, 0, 1, TimeUnit.SECONDS);

    ThreadUtil.sleepCurrentThread(20000);
    ses.shutdownNow();
  }

  public static void scheduleAtFixRateWithLongDelay(String[] args) {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(2);
    ses.scheduleAtFixedRate(() -> {
      log.info("scheduleAtFixedRate");
      ThreadUtil.sleepCurrentThread(5000);
    }, 0, 1, TimeUnit.SECONDS);

    ses.scheduleAtFixedRate(() -> {
      log.info("scheduleAtFixedRate2");
      ThreadUtil.sleepCurrentThread(2000);
    }, 0, 1, TimeUnit.SECONDS);

    ses.scheduleAtFixedRate(() -> {
      log.info("scheduleAtFixedRate3");
      ThreadUtil.sleepCurrentThread(4000);
    }, 0, 1, TimeUnit.SECONDS);



    ThreadUtil.sleepCurrentThread(10000);
    ses.shutdownNow();
  }

  public static void main(String[] args) {
    log.info("cpu counts {}", Runtime.getRuntime().availableProcessors());
    scheduleAtFixRateWithLongDelay(args);
  }

}
