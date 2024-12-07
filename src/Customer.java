import java.util.concurrent.atomic.AtomicBoolean;

public class Customer implements Runnable {
    private TicketPool ticketPool;
    private int retrievalRate;
    private int retrievalCount;
    private int customerId;
    private AtomicBoolean running;

    public Customer(TicketPool ticketPool, int retrievalRate, int retrievalCount, int customerId, AtomicBoolean running) {
        this.ticketPool = ticketPool;
        this.retrievalRate = retrievalRate;
        this.retrievalCount = retrievalCount;
        this.customerId = customerId;
        this.running = running;
    }

    @Override
    public void run() {
        while (running.get()) { // Check the running flag
            // Always try to purchase 'retrievalCount' tickets as entered by the user
            ticketPool.purchaseTickets(retrievalCount);

            try {
                Thread.sleep(retrievalRate); // Wait for the specified rate before purchasing again
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Handle thread interruption gracefully
                break; // Exit the loop if interrupted
            }
        }
    }
}


