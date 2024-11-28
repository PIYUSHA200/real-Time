public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseRate;

    public Vendor(TicketPool ticketPool, int releaseRate) {
        this.ticketPool = ticketPool;
        this.releaseRate = releaseRate;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.addTickets(releaseRate);
            try {
                Thread.sleep(1000); // Simulate time delay for releasing tickets
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

