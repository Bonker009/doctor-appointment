import java.sql.*;

public class AppointmentScheduler {
    private Connection connection;
    public AppointmentScheduler() {
        String url = "jdbc:mysql://localhost:3306/";
        String databaseName = "doctorappointment";
        String username = "root";
        String password = "seyha123";

        System.out.println("Connecting to the database ...");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected!");

            if (!databaseExists(databaseName)) {
                createDatabase(databaseName);
            }

            // Connect to the specified database
            url += databaseName;
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found!", e);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect to the database!", e);
        }
    }
    private void createDatabase(String databaseName) {
        try {
            String sql = "CREATE DATABASE " + databaseName;
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.executeUpdate();
                System.out.println("Database created: " + databaseName);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    private boolean databaseExists(String databaseName) {
        try {
            String sql = "SHOW DATABASES LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, databaseName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }


    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void scheduleAppointment(String patientName, int doctorId, String date) {
        try {
            createAppointmentsTableIfNotExist(); // Ensure that table exists

            // Check doctor availability
            if (checkDoctorAvailability(doctorId, date)) {
                String sql = "INSERT INTO appointments (patient_name, doctor_id, appointment_date) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, patientName);
                    statement.setInt(2, doctorId);
                    statement.setString(3, date);
                    statement.executeUpdate();
                    System.out.println("Appointment scheduled successfully.");
                }
            } else {
                System.out.println("Doctor is not available on the specified date.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public void createAppointmentsTableIfNotExist() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS appointments (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "patient_name VARCHAR(45) NOT NULL," +
                    "doctor_id INT NOT NULL," +
                    "appointment_date DATE NOT NULL," +
                    "FOREIGN KEY (doctor_id) REFERENCES doctors(id)" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                System.out.println("Appointments table created (if not exist).");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void createDoctorTableIfNotExist() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS doctors ("+
                    "id INT PRIMARY KEY AUTO_INCREMENT,"+
                    "doctor_name VARCHAR(45) NOT NULL,"+
                    "specialty VARCHAR(225) NOT NULL"+")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(sql);
                System.out.println("Doctors table created (if not exist).");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public void addDoctor(String doctorName, String specialty) {
        try {
            createDoctorTableIfNotExist();
            String sql = "INSERT INTO doctors (doctor_name, specialty) VALUES (?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, doctorName);
                statement.setString(2, specialty);
                statement.executeUpdate();
                System.out.println("Doctor added successfully.");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public void displayDoctors() {
        try {
            String sql = "SELECT id, doctor_name, specialty FROM doctors";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                // ANSI color codes
                String ANSI_RESET = "\u001B[0m";
                String ANSI_CYAN = "\u001B[36m";

                System.out.println(ANSI_CYAN + "Available Doctors:" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "+----+-----------------+-----------------+" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "| ID |    Doctor Name  |   Specialty     |" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "+----+-----------------+-----------------+" + ANSI_RESET);

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String doctorName = resultSet.getString("doctor_name");
                    String specialty = resultSet.getString("specialty");

                    System.out.printf(ANSI_CYAN + "| %-2d | %-15s | %-15s |" + ANSI_RESET + "\n", id, doctorName, specialty);
                }

                System.out.println(ANSI_CYAN + "+----+-----------------+-----------------+" + ANSI_RESET);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void displayAppointments() {
        try {
            String sql = "SELECT a.id, a.patient_name, d.doctor_name, a.appointment_date, d.specialty " +
                    "FROM appointments a " +
                    "JOIN doctors d ON a.doctor_id = d.id";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                System.out.println("Scheduled Appointments:");
                System.out.println("+----+-----------------+-----------------+-----------------+---------------------+");
                System.out.println("| ID |    Patient      |     Doctor      |      Date       | Doctor's Specialty  |");
                System.out.println("+----+-----------------+-----------------+-----------------+---------------------+");

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String patientName = resultSet.getString("patient_name");
                    String doctorName = resultSet.getString("doctor_name");
                    String date = resultSet.getString("appointment_date");
                    String doctorSpecialty = resultSet.getString("specialty");

                    System.out.printf("| %-2d | %-15s | %-15s | %-15s | %-19s |\n", id, patientName, doctorName, date, doctorSpecialty);
                }

                System.out.println("+----+-----------------+-----------------+-----------------+---------------------+");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public boolean checkDoctorAvailability(int doctorId, String date) {
        try {
            String sql = "SELECT * FROM appointments WHERE doctor_id = ? AND appointment_date = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, doctorId);
                statement.setString(2, date);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return !resultSet.next();
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return false;
        }
    }
    public void cancelAppointment(int id) {
        try {
            String sql = "DELETE FROM appointments WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, id);  // Use setInt for id
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Appointment canceled successfully.");
                } else {
                    System.out.println("No appointment found with the specified ID.");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }




    public void searchAppointmentsByPatient(String searchPatient) {
        try {
            String sql = "SELECT * FROM appointments WHERE patient_name LIKE ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + searchPatient + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    displayAppointmentsResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    public void searchAppointmentsByDoctor(String searchDoctor) {
        try {
            String sql = "SELECT a.*, d.doctor_name FROM appointments a " +
                    "JOIN doctors d ON a.doctor_id = d.id " +
                    "WHERE d.doctor_name LIKE ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, "%" + searchDoctor + "%");

                try (ResultSet resultSet = statement.executeQuery()) {
                    displayAppointmentsResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }


    private void displayAppointmentsResultSet(ResultSet resultSet) throws SQLException {
        System.out.println("Search Results:");
        while (resultSet.next()) {
            String patientName = resultSet.getString("patient_name");
            String doctorName = resultSet.getString("doctor_name");
            String date = resultSet.getString("appointment_date");
            System.out.println("Appointment Details: Patient: " + patientName + ", Doctor: " + doctorName + ", Date: " + date);
        }
    }


    private void handleSQLException(SQLException e) {
        e.printStackTrace();
        System.err.println("SQL Error: " + e.getMessage());
    }
}