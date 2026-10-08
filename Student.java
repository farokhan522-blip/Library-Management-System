public class Student extends Member {
    private String universityId;

    public Student(String name, String contact, String address, String email, String universityId) {
        super(name, contact, address, email);
        this.universityId = universityId;
    }

    
    @Override
    public double getMembershipFee() {
        return 500.0;  
    }

    
    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    @Override
    public String toString() {
        return getName() + " | ID: " + getMemberId() + " | Student | Contact: " + getContact();
    }
}
