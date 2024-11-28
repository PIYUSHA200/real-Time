import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Configuration Inputs
        System.out.println("Enter total tickets (initially available): ");
        int totalTickets = scanner.nextInt();

        System.out.println("Enter ticket release rate (tickets added per interval): ");
        int ticketReleaseRate = scanner.nextInt();

        System.out.println("Enter maximum ticket pool capacity: ");
        int maxCapacity = scanner.nextInt();

        System.out.println("Enter number of customers: ");
        int numCustomers = scanner.nextInt();

        // Initialize the Ticket Pool
        TicketPool ticketPool = new TicketPool(maxCapacity);

        // Create Vendor Threads
        int numVendors = 1; // Single vendor for simplicity
        for (int i = 0; i < numVendors; i++) {
            Thread vendorThread = new Thread(new Vendor(ticketPool, ticketReleaseRate));
            vendorThread.start();
        }

        // Create Customer Threads
        for (int i = 0; i < numCustomers; i++) {
            Thread customerThread = new Thread(new Customer(ticketPool));
            customerThread.start();
        }

        scanner.close();
    }
}
