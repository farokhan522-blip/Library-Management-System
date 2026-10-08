public class Staff extends Member {
    private String universityId;

    public Staff(String name, String contact, String address, String email, String universityId) {
        super(name, contact, address, email);
        this.universityId = universityId;
    }

    
    @Override
    public double getMembershipFee() {
        return 1000.0;  
    }

    
    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    
    public String toString() {
        return getName() + " | ID: " + getMemberId() + " | Staff | Contact: " + getContact();
    }
}
