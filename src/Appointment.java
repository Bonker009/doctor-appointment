

public class Appointment {
    private String patientName;
    private Doctor doctor;
    private String date;

    public Appointment(String patientName, Doctor doctor, String date) {
        this.patientName = patientName;
        this.doctor = doctor;
        this.date = date;
    }
    @Override
    public String toString() {
        return "Appointment Details: Patient: " + patientName + ", Doctor: " + doctor + ", Date: " + date;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
