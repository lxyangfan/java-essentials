package cn.fr4nk;

/**
 * Hello world!
 */
public class App {

    static volatile int cnt = 0;
    final static int max_loop = 2;

    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            threadDemo();
        } catch (InterruptedException e) {
            System.out.println("gg");
        }
    }

    public static void threadDemo() throws InterruptedException {
        // show the usage of wait and notify
        final Object obj = new Object();
        Runnable r = () -> {
            try {
                while (cnt < max_loop) {
                    Thread.currentThread().sleep(1000);
                    synchronized (obj) {
                        System.out.println(Thread.currentThread().getName() + " sub try release by notify()");
                        obj.notify();
                        System.out.println("the sub thread notified....");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("sub gg");
            }
        };
        Thread th = new Thread(r);
        th.start();

        System.out.println("the main thread wait....");

        synchronized (obj) {
            while (cnt++ < max_loop) {
                System.out.println("\t >>>> sha diao loop begin." + cnt);
                obj.wait();
                System.out.println("\t >>>> sha diao loop end." + cnt);
            }
        }

    }
}
