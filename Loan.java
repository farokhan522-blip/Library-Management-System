import java.time.LocalDateTime;

class Loan {
    private String loanId;
    private Book book;
    private Member member;
    private LocalDateTime issueDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private static int loanCounter = 1;

    public Loan() {
        this.loanId = "L" + loanCounter++; // e.g., L1, L2, ...
        this.book = null;
        this.member = null;
        this.issueDate = null;
        this.dueDate = null;
        this.returnDate = null;
    }

    public Loan(Book book, Member member, LocalDateTime issueDate, LocalDateTime dueDate, LocalDateTime returnDate) {
        this.loanId = "L" + loanCounter++;
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public boolean isOverdue() {
        return returnDate == null && LocalDateTime.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        String returnStatus = (returnDate != null) ? returnDate.toString() : "Not Returned";
        return "LoanID: " + loanId +
               ", Book: " + book.getTitle() +
               ", Member: " + member.getName() +
               ", Issue: " + issueDate +
               ", Due: " + dueDate +
               ", Returned: " + returnStatus;
    }
}
