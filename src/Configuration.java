import java.util.Scanner;

class Configuration {
    private int totalTickets;
    private int ticketReleaseCount;
    private int customerRetrievalCount;
    private int maxTicketCapacity;

    private Configuration(int totalTickets, int ticketReleaseCount, int customerRetrievalCount, int maxTicketCapacity) {
        this.totalTickets = totalTickets;
        this.ticketReleaseCount = ticketReleaseCount;
        this.customerRetrievalCount = customerRetrievalCount;
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public static Configuration getConfigurationFromUser() {
        Scanner scanner = new Scanner(System.in);

        int totalTickets = getValidIntInput(scanner, "Enter total number of tickets: ");
        int ticketReleaseCount = getValidIntInput(scanner, "Enter tickets released per cycle: ");
        int ticketsRetrievedPerCycle = getValidIntInput(scanner, "Enter tickets retrieved per cycle: ");
        int maxTicketCapacity = getValidIntInput(scanner, "Enter maximum ticket pool capacity: ");

        return new Configuration(totalTickets, ticketReleaseCount, ticketsRetrievedPerCycle, maxTicketCapacity);
    }

    private static int getValidIntInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = scanner.nextInt();
                if (value <= 0) {
                    System.out.println("Please enter a positive integer.");
                    continue;
                }
                return value;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketReleaseCount() {
        return ticketReleaseCount;
    }

    public int getCustomerRetrievalCount() {
        return customerRetrievalCount;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
}

