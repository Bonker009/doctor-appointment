import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        try {
            // Add a delay of 2 seconds as a welcome/loading screen
            System.out.println("Welcome to the Doctor Appointment Scheduler System...");
            Thread.sleep(1000); // Delay in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LoadingAnimation();

        // Rest of your existing code...
        Scanner scanner = new Scanner(System.in);
        AppointmentScheduler scheduler = new AppointmentScheduler();
        CellStyle textAlign = new CellStyle(CellStyle.HorizontalAlign.center);
        int choice = 0;

        do{
            Table table = new Table(2, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);
            table.setColumnWidth(1, 25, 35);
            table.setColumnWidth(0, 9, 15);
            table.addCell("Doctor Appointment Scheduler System",textAlign,2);
            table.addCell("1",textAlign);
            table.addCell("Schedule Appointment");
            table.addCell("2",textAlign);
            table.addCell("Display Appointment");
            table.addCell("3",textAlign);
            table.addCell("Cancel Appointment");
            table.addCell("4",textAlign);
            table.addCell("Search Appointment by ID");
            table.addCell("5",textAlign);
            table.addCell("Search Appointment by Doctor");
            table.addCell("6",textAlign);
            table.addCell("Add Doctor's Information");
            table.addCell("7",textAlign);
            table.addCell("Display Doctors");
            table.addCell("8",textAlign);
            table.addCell("Exit",textAlign);
            System.out.println(table.render());
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();
                    scheduler.displayDoctors();
                    System.out.print("Choose doctor by ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter appointment date: ");
                    String date = scanner.nextLine();
                    scheduler.scheduleAppointment(patientName, doctorId, date);
                    break;
                case 2:
                    scheduler.displayAppointments();
                    scanner.nextLine();
                    break;
                case 3:
                    scheduler.displayAppointments();
                    System.out.print("Enter appointment id to cancel: ");
                    int cancelDate = Integer.parseInt(scanner.nextLine());
                    scheduler.cancelAppointment(cancelDate);
                    break;
                case 4:
                    scheduler.displayAppointments();
                    System.out.print("Enter appointment ID to search: ");
                    int searchPatient = scanner.nextInt();
                    scheduler.searchAppointmentById(searchPatient);
                    break;
                case 5:
                    scheduler.displayDoctors();
                    System.out.print("Enter doctor name to search: ");
                    String searchDoctor = scanner.nextLine();
                    scheduler.searchAppointmentsByDoctor(searchDoctor);
                    break;
                case 6:
                    System.out.print("Enter doctor name: ");
                    String newDoctorName = scanner.nextLine();
                    System.out.print("Enter doctor specialty: ");
                    String newDoctorSpecialty = scanner.nextLine();
                    scheduler.addDoctor(newDoctorName, newDoctorSpecialty);
                    break;
                case 7:
                    scheduler.displayDoctors();
                    scanner.nextLine();
                    break;
                case 8:
                    scheduler.closeConnection();
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }while (choice != 8);
    }
    public static void LoadingAnimation(){
        Thread animationThread = new Thread(() -> {
            try {
                String[] animationFrames = {
                        "\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A0",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1",
                        "\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1\u25A1"
                };
                int frameIndex = 0;

                while (!Thread.interrupted()) {
                    System.out.print("\r" + animationFrames[frameIndex]);
                    frameIndex = (frameIndex + 1) % animationFrames.length;
                    Thread.sleep(200); // Delay in milliseconds
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        System.out.print("Processing... ");
        animationThread.start();

        // Simulate some time-consuming task
        try {
            Thread.sleep(5000); // Delay in milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        animationThread.interrupt(); // Stop the animation
        System.out.println("\rProcessing... Done!");

    }

}