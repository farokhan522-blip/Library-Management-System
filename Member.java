public abstract class Member {
    private String memberId;
    private String name;
    private String contact;
    private String address;
    private String email;
    private static int memberCounter = 1;



    public Member() {
        this.memberId = "Mem-" + memberCounter++;
        this.name = "";
        this.contact = "";
        this.address = "";
        this.email = "";
    }

    public Member(String name, String contact, String address, String email) {
        this.memberId ="Mem-" + memberCounter++;
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.email = email;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {//for emergency optional use , otherwise id is dependent on static count aut increemneted variable
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  
    public abstract double getMembershipFee();


    public abstract String toString();
}
