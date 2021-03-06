package berlin.yuna.tinkerforgesensor.model.type;

import java.util.function.Consumer;

public class Async extends AsyncRun {

    public Async(final String name, final Consumer<Long> consumer) {
        super(name, consumer);
        this.start();
    }

//    private Thread thread;
//    private boolean running = false;
//
//    private final String name;
//    private final Consumer<Long> consumer;

//    public Async(final String name, final long refreshMs, final Consumer<Long> consumer) {
//        this.name = name;
//        this.consumer = consumer;
//        this.start();
//    }

    @Override
    public void run() {
        consumer.accept(System.currentTimeMillis());
        running = false;
    }

//    private synchronized void start() {
//        if (running)
//            return;
//        running = true;
//        thread = new Thread(this, name);
//        thread.start();
//    }
//
//    public synchronized void stop() {
//        Object result = TimeoutExecutor.execute(1000, () -> {
//            if (running) {
//                running = false;
//                thread.join();
//            }
//            return !running;
//        });
//        if (result instanceof Throwable) {
//            thread.interrupt();
//        }
//    }

    @Override
    public String toString() {
        return "Async{" +
                "running=" + running +
                ", name='" + name + '\'' +
                '}';
    }
}
