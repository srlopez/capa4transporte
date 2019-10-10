class Counter  implements Runnable{
    private int c = 0;

    public void increment() {
        try {
            Thread.sleep(10);
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
    
    @Override
    public void run() {
        //++
        this.increment();
        System.out.println("Contador= "+ this.getValue() + " en "
        + Thread.currentThread().getName() + " tras INC" );
        //--
        this.decrement();
        System.out.println("VContador= "+ this.getValue() + " en "
        + Thread.currentThread().getName() + " tras DEC" );
    }
}

public class RaceConditionDemo01{
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter, "Thread-1");
        Thread t2 = new Thread(counter, "Thread-2");
        Thread t3 = new Thread(counter, "Thread-3");
        t1.start();
        t2.start();
        t3.start();
    }    
}
