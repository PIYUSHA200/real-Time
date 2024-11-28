public class Customer implements Runnable {
    private final TicketPool ticketPool;

    public Customer(TicketPool ticketPool) {
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        while (true) {
            ticketPool.removeTicket();
            try {
                Thread.sleep(1500); // Simulate time delay for purchasing tickets
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

