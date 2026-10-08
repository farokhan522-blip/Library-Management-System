public class Book {
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private int publicationYear;
    private boolean isIssued;
    private Category category;
    private Member issuedTo;
    

    public Book() {
        
        this.title = "";
        this.author = "";
        this.isbn = "";
        this.publisher = "";
        this.publicationYear = 0;
        this.isIssued = false;
        this.category = null;
    }

    public Book(String title, String author, String isbn, String publisher, int publicationYear, boolean isIssued, Category category) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isIssued = isIssued;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean isIssued) {
        this.isIssued = isIssued;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

   public Member getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(Member issuedTo) {
        this.issuedTo = issuedTo;
    }

    @Override
    public String toString() {
        String issuedInfo;
        if (isIssued) {
            issuedInfo = (issuedTo != null)
                ? "Issued to: " + issuedTo.getName()
                : "Issued, but member info not found";
        } else {
            issuedInfo = "Not Issued to any member yet";
        }

            return title + " by " + author +
               " | ISBN: " + isbn +
               " | " + publisher + " (" + publicationYear + ")" +
               " | " + issuedInfo +
               " | Category: " + (category != null ? category.getName() : "None");
    }
}
