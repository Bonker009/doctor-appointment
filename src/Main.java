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
        WelcomeAnimation();
        LoadingAnimation();

        // Rest of your existing code...
        Scanner scanner = new Scanner(System.in);
        AppointmentScheduler scheduler = new AppointmentScheduler();
        while (true) {
            System.out.println("\nDoctor Appointment Scheduler System");
            System.out.println("\u001B[36m+----+--------------------------------+");
            System.out.println("| \u001B[33mNo \u001B[36m|          \u001B[33mMenu Option           \u001B[36m|");
            System.out.println("+----+--------------------------------+");
            System.out.println("| \u001B[33m 1  \u001B[36m| \u001B[37mSchedule Appointment           \u001B[36m|");
            System.out.println("| \u001B[33m 2  \u001B[36m| \u001B[37mDisplay Appointments           \u001B[36m|");
            System.out.println("| \u001B[33m 3  \u001B[36m| \u001B[37mCancel Appointment             \u001B[36m|");
            System.out.println("| \u001B[33m 4  \u001B[36m| \u001B[37mSearch Appointments by Patient \u001B[36m|");
            System.out.println("| \u001B[33m 5  \u001B[36m| \u001B[37mSearch Appointments by Doctor  \u001B[36m|");
            System.out.println("| \u001B[33m 6  \u001B[36m| \u001B[37mAdd Doctor's Information       \u001B[36m|");
            System.out.println("| \u001B[33m 7  \u001B[36m| \u001B[37mDisplay Doctors       \u001B[36m|");
            System.out.println("| \u001B[33m 8  \u001B[36m| \u001B[37mExit                           \u001B[36m|");
            System.out.println("+----+--------------------------------+");
            System.out.print("\u001B[37mEnter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    clearScreen();
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();
                    scheduler.displayDoctors();

                    System.out.print("Choose doctor by ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    System.out.print("Enter appointment date: ");
                    String date = scanner.nextLine();

                    scheduler.scheduleAppointment(patientName, doctorId, date);
                    break;

                case 2:
                    clearScreen();
                    scheduler.displayAppointments();
                    break;

                case 3:
                    clearScreen();
                    System.out.print("Enter appointment id to cancel: ");
                    int cancelDate = Integer.parseInt(scanner.nextLine());
                    scheduler.cancelAppointment(cancelDate);
                    break;

                case 4:
                    clearScreen();
                    System.out.print("Enter patient name to search: ");
                    String searchPatient = scanner.nextLine();
                    scheduler.searchAppointmentsByPatient(searchPatient);
                    break;

                case 5:
                    clearScreen();
                    System.out.print("Enter doctor name to search: ");
                    String searchDoctor = scanner.nextLine();
                    scheduler.searchAppointmentsByDoctor(searchDoctor);
                    break;


                case 6:
                    clearScreen();
                    System.out.print("Enter doctor name: ");
                    String newDoctorName = scanner.nextLine();

                    System.out.print("Enter doctor specialty: ");
                    String newDoctorSpecialty = scanner.nextLine();

                    scheduler.addDoctor(newDoctorName, newDoctorSpecialty);
                    break;
                case 7:
                    clearScreen();
                    scheduler.displayDoctors();
                    break;
                case 8:
                    clearScreen();
                    scheduler.closeConnection();
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
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


    public static void WelcomeAnimation() throws InterruptedException{
        String[] frames = {
                "\u001B[33m  W       W  EEEEEEE  L            CCCCCC       OOOOOOO  MMM         MMM  EEEEEEE\u001B[0m",
                "\u001B[32m  W       W  E        L          C               O     O  M   M     M   M  E      \u001B[0m",
                "\u001B[36m  W   W   W  E        L        C                   O O O  M    M   M    M  E      \u001B[0m",
                "\u001B[35m  W  W W  W  EEEEE    L        C                   O   O  M     M M     M  EEEEE  \u001B[0m",
                "\u001B[31m  W W   W W  E        L        C                   O O O  M      M      M  E      \u001B[0m",
                "\u001B[34m  W       W  E        L          C               O     O  M              M  E      \u001B[0m",
                "\u001B[33m  W       W  EEEEEEE  LLLLLLL     CCCCCC       OOOOOOO  M              M  EEEEEEE\u001B[0m"
        };

        // Display frames in a loop
        for (String frame : frames) {
            clearScreen(); // Clear console screen
            System.out.println(frame);
            Thread.sleep(500); // Delay between frames (in milliseconds)
        }
    }
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Handle exceptions as needed
        }
    }

}