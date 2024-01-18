public class Doctor {
    private String name;
    private String specialty;

    public Doctor(String name, String specialty) {
        this.name = name;
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Doctor Information:\n" +
                "Name: " + name + "\n" +
                "Specialty: " + specialty;
    }


}
