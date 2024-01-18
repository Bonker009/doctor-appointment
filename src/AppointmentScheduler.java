import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public void scheduleAppointment(String patientName, String doctorName, String date) {
        try {
            if (checkDoctorAvailability(doctorName, date)) {
                String sql = "INSERT INTO appointments (patient_name, doctor_name, appointment_date) VALUES (?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, patientName);
                    statement.setString(2, doctorName);
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
    public void addDoctor(String doctorName, String specialty) {
        try {
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

    public void displayAppointments() {
        try {
            String sql = "SELECT * FROM appointments";
            try (PreparedStatement statement = connection.prepareStatement(sql);
                 ResultSet resultSet = statement.executeQuery()) {

                System.out.println("Scheduled Appointments:");
                while (resultSet.next()) {
                    String patientName = resultSet.getString("patient_name");
                    String doctorName = resultSet.getString("doctor_name");
                    String date = resultSet.getString("appointment_date");
                    System.out.println("Appointment Details: Patient: " + patientName + ", Doctor: " + doctorName + ", Date: " + date);
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }
    public boolean checkDoctorAvailability(String doctorName, String date) {
        try {
            String sql = "SELECT * FROM appointments WHERE doctor_name = ? AND appointment_date = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, doctorName);
                statement.setString(2, date);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return !resultSet.next(); // Doctor is available if no appointment is found for the specified date
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
            return false; // Assume doctor is not available in case of an error
        }
    }

    public void cancelAppointment(String cancelDate) {
        try {
            String sql = "DELETE FROM appointments WHERE appointment_date = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, cancelDate);
                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    System.out.println("Appointment canceled successfully.");
                } else {
                    System.out.println("No appointment found on the specified date.");
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
            String sql = "SELECT * FROM appointments WHERE doctor_name LIKE ?";
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
