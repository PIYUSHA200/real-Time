import java.util.concurrent.atomic.AtomicBoolean;

public class Vendor implements Runnable {
    private TicketPool ticketPool;
    private int releaseRate;
    private int releaseCount;
    private int vendorId;
    private AtomicBoolean running;

    public Vendor(TicketPool ticketPool, int releaseRate, int releaseCount, int vendorId, AtomicBoolean running) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
        this.releaseCount = releaseCount;
        this.vendorId = vendorId;
        this.running = running;
    }

    @Override
    public void run() {
        while (running.get()) { // Check the running flag
            ticketPool.addTickets(releaseCount);
            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle thread interruption gracefully
                break; // Exit the loop if interrupted
            }
        }
    }
}
