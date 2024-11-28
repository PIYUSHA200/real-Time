import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {
    private final List<Integer> tickets = Collections.synchronizedList(new ArrayList<>());
    private final int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    // Add tickets to the pool
    public synchronized void addTickets(int count) {
        while (tickets.size() + count > maxCapacity) {
            try {
                System.out.println("Vendor waiting: Pool is full.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < count; i++) {
            tickets.add(1); // Add a ticket
        }
        System.out.println("Added " + count + " tickets. Total tickets: " + tickets.size());
        notifyAll();
    }

    // Remove tickets from the pool
    public synchronized void removeTicket() {
        while (tickets.isEmpty()) {
            try {
                System.out.println("Customer waiting: No tickets available.");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tickets.remove(0); // Remove a ticket
        System.out.println("Ticket purchased. Remaining tickets: " + tickets.size());
        notifyAll();
    }
}
