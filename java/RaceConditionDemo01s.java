import java.util.Random;
import java.util.concurrent.Semaphore;

class Counter implements Runnable {

    Random rand = new Random();

    private Semaphore sem;
    private int c = 0;

    public Counter( int val ) {
        this.sem = new Semaphore(val);
    }

    public void increment() {
        try {
            Thread.sleep(100);// rand.nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        c++;
    }

    public void decrement() {
        c--;
    }

    public int getValue() {
        return c;
    }

    // @Override
    public void run() {
        // synchronized(this){

        //if (Thread.currentThread().getName() == "#1")
         {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
            // ++
            this.increment();
            System.out.println("Thread" + Thread.currentThread().getName() + "(+)= " + this.getValue());
            // --
            this.decrement();
            System.out.println("Thread" + Thread.currentThread().getName() + "(-)= " + this.getValue());
            // }
        //} else
            sem.release();
    }
}

public class RaceConditionDemo01s {

    public static void main(String[] args) {
        int val = args.length == 0?0:Integer.parseInt("0"+args[0]);
        System.out.println("Semaforo=" + val);

        Counter counter = new Counter(val);
        Thread t1 = new Thread(counter, "#1");
        Thread t2 = new Thread(counter, "#2");
        Thread t3 = new Thread(counter, "#3");
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo principal del proceso interrumpido.");
        }
        System.out.println("Proceso acabando.");
    }
}
