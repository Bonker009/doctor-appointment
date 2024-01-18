import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AppointmentScheduler scheduler = new AppointmentScheduler();

        while (true) {
            System.out.println("\nDoctor Appointment Scheduler System");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. Display Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Search Appointments by Patient");
            System.out.println("5. Search Appointments by Doctor");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();

                    System.out.print("Enter doctor name: ");
                    String doctorName = scanner.nextLine();

                    System.out.print("Enter appointment date: ");
                    String date = scanner.nextLine();

                    scheduler.scheduleAppointment(patientName, doctorName, date);
                    break;

                case 2:
                    scheduler.displayAppointments();
                    break;

                case 3:
                    System.out.print("Enter appointment date to cancel: ");
                    String cancelDate = scanner.nextLine();
                    scheduler.cancelAppointment(cancelDate);
                    break;

                case 4:
                    System.out.print("Enter patient name to search: ");
                    String searchPatient = scanner.nextLine();
                    scheduler.searchAppointmentsByPatient(searchPatient);
                    break;

                case 5:
                    System.out.print("Enter doctor name to search: ");
                    String searchDoctor = scanner.nextLine();
                    scheduler.searchAppointmentsByDoctor(searchDoctor);
                    break;

                case 6:
                    scheduler.closeConnection();
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
