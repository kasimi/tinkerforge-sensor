package berlin.yuna.tinkerforgesensor.model.type;

import java.util.function.Consumer;

import static berlin.yuna.tinkerforgesensor.util.TinkerForgeUtil.RefreshType.EACH_SECOND;
import static berlin.yuna.tinkerforgesensor.util.TinkerForgeUtil.RefreshType.POST_PROCESS;
import static java.lang.System.currentTimeMillis;

public class Loop extends AsyncRun {

    private final long refreshMs;

    public Loop(final String name, final long refreshMs, final Consumer<Long> consumer) {
        super(name, consumer);
        this.refreshMs = refreshMs;
        this.start();
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double numTicks = 60.0;
        final double nanoSeconds = 1000000000.0 / numTicks;
        double delta = 0;
        int frames = 0;
        int ticks = 0;
        long time = currentTimeMillis();

        while (running) {
            final long currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / nanoSeconds;
            lastTime = currentTime;

            if (delta >= 1) {
                if (refreshMs == POST_PROCESS.ms) {
                    consumer.accept(POST_PROCESS.ms);
                }
                ticks++;
                delta--;
            }
            frames++;
            sleep(15);
            if (currentTimeMillis() - time >= refreshMs) {
                consumer.accept(refreshMs);
                time += refreshMs;
                ticks = 0;
                frames = 0;
            } else if (currentTimeMillis() - time >= EACH_SECOND.ms) {
                consumer.accept(EACH_SECOND.ms);
                time += EACH_SECOND.ms;
                ticks = 0;
                frames = 0;
            }
        }
    }

    @Override
    public String toString() {
        return "Loop{" +
                "name='" + name + '\'' +
                '}';
    }
}
