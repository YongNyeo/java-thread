import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int MAX_PASSWORD = 999;

    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(random.nextInt(MAX_PASSWORD)));

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }

    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrect(int password) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            return this.password == password;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setPriority(MAX_PRIORITY);
            this.setName(this.getClass().getSimpleName());
        }

        @Override
        public synchronized void start() {
            super.start();
            System.out.println("start HackerThread " + this.getName());
        }
    }

    private static class AscendingHackerThread extends HackerThread {


        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {

            for (int i = 0; i < MAX_PASSWORD; i++) {

                if (vault.isCorrect(i)) {
                    System.out.println("success solve password " + this.getName()+" "+i);
                    System.exit(0);
                }
            }

        }
    }

    private static class DescendingHackerThread extends HackerThread {


        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {

            for (int i = MAX_PASSWORD; i >= 0; i--) {

                if (vault.isCorrect(i)) {
                    System.out.println("success solve password " + this.getName()+" "+i);
                    System.exit(0);
                }
            }

        }
    }

    private static class PoliceThread extends Thread {

        @Override
        public void run() {

            for (int i = 10; i >= 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(i);
            }
            System.out.println("Game Over, police win");
        }
    }


}

