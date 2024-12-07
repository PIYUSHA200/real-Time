import java.util.LinkedList;
import java.util.Queue;

public class TicketPool {
    private int maxTicketCapacity;
    private Queue<Integer> tickets; // A queue to represent the tickets in the pool

    public TicketPool(int maxTicketCapacity, int totalTickets) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.tickets = new LinkedList<>();

        // Initialize the tickets in the pool
        for (int i = 1; i <= totalTickets; i++) {
            tickets.add(i);
        }
    }

    public synchronized void addTickets(int ticketsToAdd) {
        int added = 0;
        while (added < ticketsToAdd && tickets.size() < maxTicketCapacity) {
            tickets.add(tickets.size() + 1); // Add new ticket to the pool
            added++;
        }

        System.out.println("Vendor" + " added " + added + " tickets. Total tickets: " + tickets.size());
        notifyAll(); // Notify waiting threads
    }

    public synchronized void purchaseTickets(int ticketsToPurchase) {
        // Ensure that the customer only purchases the exact number of tickets requested
        while (tickets.size() < ticketsToPurchase) {
            try {
                wait(); // Wait for tickets to be added
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Keep track of the tickets the customer buys
        StringBuilder purchasedTickets = new StringBuilder();
        for (int i = 0; i < ticketsToPurchase; i++) {
            int ticket = tickets.poll(); // Remove a ticket from the pool
            purchasedTickets.append(ticket).append(" ");
        }

        System.out.println(Thread.currentThread().getName() + " purchased " + ticketsToPurchase + " tickets. Purchased tickets id: " + purchasedTickets.toString().trim() + ". Remaining tickets: " + tickets.size());
    }
}
