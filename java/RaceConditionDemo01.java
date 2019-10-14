import java.util.Random;



class Counter  implements Runnable{
    private int c = 0;
    Random rand = new Random();

    public void increment() {
        try {
            Thread.sleep(100);//rand.nextInt(10));
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
    
    //@Override
    public void run() {
        //synchronized(this){
            //++
            this.increment();
            System.out.println("Thread" + Thread.currentThread().getName() + "(+)= " + this.getValue());
            //--
            this.decrement();
            System.out.println("Thread" + Thread.currentThread().getName() + "(-)= " + this.getValue());
        //}
    }
}

public class RaceConditionDemo01{
    public static void main(String[] args) {
        Counter counter = new Counter();
        Thread t1 = new Thread(counter, "#1");
        Thread t2 = new Thread(counter, "#2");
        Thread t3 = new Thread(counter, "#3");
        t1.start();
        t2.start();
        t3.start();
    }    
}
