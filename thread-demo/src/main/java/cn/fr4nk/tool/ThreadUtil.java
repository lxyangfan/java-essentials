package cn.fr4nk.tool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;


public class ThreadUtil {

  public static void sleepCurrentThread(int mils) {
    try {
      Thread.sleep(mils);
    } catch (InterruptedException e) {
    }
  }

  @Slf4j
  public static class ThreadPoolWithStartTime {
    private static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();
    public static final ExecutorService threadPool = new ThreadPoolExecutor(
        10, 10, 1L, TimeUnit.MINUTES
        , new LinkedBlockingQueue<>(128),
        new ThreadPoolExecutor.DiscardPolicy() ) {

      /**
       * Method invoked prior to executing the given Runnable in the
       * given thread.  This method is invoked by thread {@code t} that
       * will execute task {@code r}, and may be used to re-initialize
       * ThreadLocals, or to perform logging.
       *
       * <p>This implementation does nothing, but may be customized in
       * subclasses. Note: To properly nest multiple overridings, subclasses
       * should generally invoke {@code super.beforeExecute} at the end of
       * this method.
       *
       * @param t the thread that will run task {@code r}
       * @param r the task that will be executed
       */
      @Override
      protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        if (START_TIME.get() == null) {
          START_TIME.set(System.currentTimeMillis());
        }
      }

      /**
       * Method invoked upon completion of execution of the given Runnable.
       * This method is invoked by the thread that executed the task. If
       * non-null, the Throwable is the uncaught {@code RuntimeException}
       * or {@code Error} that caused execution to terminate abruptly.
       *
       * <p>This implementation does nothing, but may be customized in
       * subclasses. Note: To properly nest multiple overridings, subclasses
       * should generally invoke {@code super.afterExecute} at the
       * beginning of this method.
       *
       * <p><b>Note:</b> When actions are enclosed in tasks (such as
       * {@link FutureTask}) either explicitly or via methods such as
       * {@code submit}, these task objects catch and maintain
       * computational exceptions, and so they do not cause abrupt
       * termination, and the internal exceptions are <em>not</em>
       * passed to this method. If you would like to trap both kinds of
       * failures in this method, you can further probe for such cases,
       * as in this sample subclass that prints either the direct cause
       * or the underlying exception if a task has been aborted:
       *
       * <pre> {@code
       * class ExtendedExecutor extends ThreadPoolExecutor {
       *   // ...
       *   protected void afterExecute(Runnable r, Throwable t) {
       *     super.afterExecute(r, t);
       *     if (t == null
       *         && r instanceof Future<?>
       *         && ((Future<?>)r).isDone()) {
       *       try {
       *         Object result = ((Future<?>) r).get();
       *       } catch (CancellationException ce) {
       *         t = ce;
       *       } catch (ExecutionException ee) {
       *         t = ee.getCause();
       *       } catch (InterruptedException ie) {
       *         // ignore/reset
       *         Thread.currentThread().interrupt();
       *       }
       *     }
       *     if (t != null)
       *       System.out.println(t);
       *   }
       * }}</pre>
       *
       * @param r the runnable that has completed
       * @param t the exception that caused termination, or null if
       *          execution completed normally
       */
      @Override
      protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        Long startTime = START_TIME.get();
        if (startTime != null) {
          log.info("task execute time: {}", System.currentTimeMillis() - startTime);
        }
        START_TIME.remove();
      }
    };

    static {
      ((ThreadPoolExecutor) threadPool).allowCoreThreadTimeOut(true);
    }
  }
}
