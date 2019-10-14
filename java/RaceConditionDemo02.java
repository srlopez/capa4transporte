import java.util.concurrent.Semaphore;

class Counter extends Thread {
    private Semaphore sem;
    private int id;

    Counter(int id, Semaphore s) {
        this.id = id;
        this.sem = s;
    }

    public void run() {
        if (id == 1) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Hola, soy el thread " + id);
        if (id == 2) {
            sem.release();
        }
    }
}

public class RaceConditionDemo02 {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(0);
        Counter t1 = new Counter(1, semaphore);
        Counter t2 = new Counter(2, semaphore);
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo principal del proceso interrumpido.");
        }
        System.out.println("Proceso acabando.");
    }
}