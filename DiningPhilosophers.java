class Philosopher implements Runnable {

    private final Object leftFork;
    private final Object rightFork;

    Philosopher(Object leftFork, Object rightFork) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void work(String work) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " " + work);
        Thread.sleep(((int) (Math.random() * 100)));
    }

    @Override
    public void run() {
        try {
            while (true) {
                work(System.nanoTime() + ": Thinking"); // thinking
                synchronized (leftFork) {
                    work(System.nanoTime() + ": Picked up left fork");
                    synchronized (rightFork) {
                        work(System.nanoTime() + ": Picked up right fork - eating"); // eating
                        work(System.nanoTime() + ": Put down right fork");
                    }
                    work(System.nanoTime() + ": Put down left fork. Returning to thinking");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
public class DiningPhilosophers {

    public static void main(String[] args) throws Exception {

        Philosopher[] philosophers = new Philosopher[5];
        Object[] forks = new Object[philosophers.length];

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i++) {

            Object leftFork = forks[i];
            Object rightFork = forks[(i + 1) % forks.length];

            if (i == philosophers.length - 1) {
                philosophers[i] = new Philosopher(rightFork, leftFork); // The last philosopher picks up the right fork first
            } else {
                philosophers[i] = new Philosopher(leftFork, rightFork);
            }

            Thread t = new Thread(philosophers[i], "Philosopher " + (i + 1));
            t.start();
        }
    }

}
