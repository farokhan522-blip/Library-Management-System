import java.io.*;
import java.time.LocalDateTime;
import java.util.Scanner;

class LibrarySystem {
    private Book[] books;
    private Member[] members;
    private Loan[] loans;
    private Category category;    
    private Librarian admin;
    private boolean isLoading = false;

    public LibrarySystem() {
        this.books = new Book[100];
        this.members = new Member[100];
        this.loans = new Loan[100];
        this.category = new Category();
        this.admin = new Librarian("admin", "admin123");
    }

   

    public void addBook(String title, String author, String isbn, String publisher, int publicationYear, boolean isIssued, String catName, String desc) throws Exception {
         
        category = new Category(catName,desc);

        for (int i = 0; i < books.length; i++) {
            
            if (books[i] == null){
            
                books[i]  = new Book(title, author, isbn, publisher, publicationYear, false, category);

                FileWriter fw = new FileWriter("books.txt", true); // true = append mode
                fw.write(title + "," + author + "," + isbn + "," + publisher + "," + publicationYear + "," + false + "," + catName + "," + desc + "\n");
                
                fw.close();
                
                if(!isLoading){
                System.out.println("Book Added Successfully");
                
                }
                return;
            }
        }
        if(!isLoading){
        System.out.println("no space to add more books");
        }
    }

    public void removeBook(String isbn) throws Exception {
        
        for (int i = 0; i < books.length; i++) {
            
            if (books[i] != null && books[i].getIsbn().equals(isbn)) {
                books[i] = null;
                category = null;

                // overwrite file with updated books[]
                FileWriter fw = new FileWriter("books.txt", false); //  overwrite mode
                
                for (int j = 0; j < books.length; j++) {
                    if (books[j] != null) {
                    
                        fw.write(books[j].getTitle() + "," +
                             books[j].getAuthor() + "," +
                             books[j].getIsbn() + "," +
                             books[j].getPublisher() + "," +
                             books[j].getPublicationYear() + "," +
                             books[j].isIssued() + "," +
                             category.getName() + "," +
                             category.getDescription() + "\n");
                    }
                }

                fw.close();

                System.out.println("Book removed successfully.");
                return;
            }
        }

            System.out.println("Book not found.");
    }

    public void registerMember(String name, String contact, String address, String email, String universityId, int type) throws Exception {
        
        for (int i = 0; i < members.length; i++) {
            if (members[i] == null) {
                if (type == 1) {
                    members[i] = new Student(name, contact, address, email, universityId);
                    System.out.println("Student Registered. Membership Fee: " + members[i].getMembershipFee());
                } else {
                    members[i] = new Staff(name, contact, address, email, universityId);
                    System.out.println("Staff Registered. Membership Fee: " + members[i].getMembershipFee());
                }

                // File I/O: Append new member to "members.txt"
                FileWriter fw = new FileWriter("members.txt", true);
                    
                    fw.write(name + "," + contact + "," + address + "," + email + "," + universityId + "," + 
                    (type == 1 ? "Student" : "Staff") + "\n");
                    
                    fw.close();

                    return;
            }
        }

            System.out.println("No more space to add more members");
    }

    public boolean removeMember(String id) throws Exception {
        
        for (int i = 0; i < members.length; i++) {
            if (members[i] != null && members[i].getMemberId().equals(id)) {
                members[i] = null;

                // File I/O: Overwrite "members.txt" with current members[]
                FileWriter fw = new FileWriter("members.txt",false); // overwrite mode
                    
                    for (int j = 0; j < members.length; j++) {
                        if (members[j] != null) {
                            fw.write(members[j].getName() + "," +
                                members[j].getContact() + "," +
                                members[j].getAddress() + "," +
                                members[j].getEmail() + "," +
                                members[j].getMemberId() + "," +
                                (members[j] instanceof Student ? "Student" : "Staff") + "\n");
                        }
                    }

                        fw.close();

                        System.out.println("Member removed");
                        return true;
            }
        }

            System.out.println("Member not Found");
            return false;
    }

    public void issueBook(String isbn, String memberId) throws Exception {
        
        Book book = SearchBookByIsbn(isbn);
        Member member = SearchMemberById(memberId);

    if (book == null || member == null) {
        System.out.println("Book or Member not found.");
        return;
    }

    if (book.isIssued()) {
        System.out.println("Book is already issued.");
        return;
    }

    LocalDateTime today = LocalDateTime.now();
    LocalDateTime due = today.plusSeconds(60);

    Loan loan = new Loan(book, member, today, due, null);

    for (int i = 0; i < loans.length; i++) {
        if (loans[i] == null) {
            loans[i] = loan;
            book.setIssued(true);
            book.setIssuedTo(member);

            FileWriter fw = new FileWriter("loans.txt", true); // append
                fw.write(loan.getLoanId() + "," +          
                book.getIsbn() + "," +           
                member.getMemberId() + "," +      
                loan.getIssueDate() + "," +       
                loan.getDueDate() + "," +         
                member.getName() + "\n");
                
                fw.close();

            System.out.println("Book issued successfully to : "+member.getName());
            return;
        }
    }

    System.out.println("No space to issue more loans.");
}

    public void returnBook(String isbn) throws Exception {
        
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < loans.length; i++) {
            Loan loan = loans[i];

            if (loan != null && loan.getBook().getIsbn().equals(isbn)) {

                if (loan.getDueDate().isBefore(LocalDateTime.now())) {
                    double fine = FineCalculator.calculateFine(loan.getDueDate());
                    System.out.println("This book is overdue. Fine: Rs. " + fine);

                    System.out.print("Do you want to pay and return the book? (yes/no): ");
                    String response = sc.nextLine().trim().toLowerCase();

                    if (!response.equals("yes")) {
                        System.out.println("Book not returned. It remains on loan with accumulating fine.");
                        return;
                    }
                }

                // return process
                loan.getBook().setIssued(false);
                loan.getBook().setIssuedTo(null);
                loan.setReturnDate(LocalDateTime.now());
                loans[i] = null;

                // Now rewrite entire loans.txt file with remaining loans
                FileWriter fw = new FileWriter("loans.txt",false); // overwrite
                
                for (int j = 0; j < loans.length; j++) {
                    
                    Loan l = loans[j];
                    
                    if (l != null) {
                        fw.write(l.getLoanId() + "," +
                        l.getBook().getIsbn() + "," +
                        l.getMember().getMemberId() + "," +
                        l.getIssueDate() + "," +
                        l.getDueDate() + "," +
                        l.getMember().getName() + "\n");
                    }
                }
                    fw.close();

                    System.out.println("Book returned successfully.");
                    return;
            }
        }

                    System.out.println("Loan record not found.");
    }

    public double calculateFine(String loanId) {
        for (Loan l : loans) {
            if (l != null && l.getLoanId().equals(loanId)) {
                return FineCalculator.calculateFine(l.getDueDate());
            }
        }
        
        System.out.println("Loan not found");
        return 0;
    }

    public void listOverdueBooks() {
        
        LocalDateTime today = LocalDateTime.now();
        boolean hasOverdue = false;

        for (Loan loan : loans) {
            
            if (loan != null && loan.getDueDate().isBefore(today)) {
                System.out.println("Overdue: " + loan.getBook().getTitle() + " (Due: " + loan.getDueDate() + ")");
                hasOverdue = true;
            }
        }

        if (!hasOverdue) {
            System.out.println("No book is in overdue right now.");
        }
    }

    public void viewAllBooks() {
        
        boolean hasBooks = false;

        for (Book book : books) {
            if (book != null) {
                System.out.println(book);
                hasBooks = true;
            }
        }

        
        if (!hasBooks) {
        System.out.println("No books available.");
        }
    }

    public void viewAvailableBooks() {
        
        boolean found = false;
        
        for (Book book : books) {
            if (book != null && !book.isIssued()) {
                System.out.println(book);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("No Available Books");
        }
    }

    public void viewIssuedBooks() {
        
        boolean hasIssuedBooks = false;

        for (Book book : books) {
            if (book != null && book.isIssued()) {
                System.out.println(book);
                hasIssuedBooks = true;
            }
        }

        if (!hasIssuedBooks) {
            System.out.println("No books are currently issued.");
        }
    }
    
    public void viewMembers() {
        
        boolean found = false;

        for (Member m : members) {
            if (m != null) {
                System.out.println(m);
                found = true;
            }
        }

    
        if (!found) {
            System.out.println("No member Found!");
        }
    }

    public Book SearchBookByTitle(String title) {
        
        for (Book book : books) {
            if (book != null && book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null;
    }

    public Book SearchBookByIsbn(String isbn) {
        
        for (Book book : books) {
            if (book != null && book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null;
    }

    public Member SearchMemberById(String id) {
        for (Member m : members) {
            if (m != null && m.getMemberId().equals(id)) {
                return m;
            }
        }

        return null;
    }

    public Librarian getAdmin() {
        return admin;
    }

    public void listLoansByMember() {
        
        boolean anyMember = false;
        
        for (Member m : members) {
            
            if (m != null) {
                
                anyMember = true;
                
                System.out.println("\n=== Member: " + m.getName() + " (ID: " + m.getMemberId() + ") ===");
                
                boolean foundLoan = false;
                
                for (Loan l : loans) {
                    if (l != null && l.getMember().getMemberId().equals(m.getMemberId())) {
                        System.out.println("Loan ID : " + l.getLoanId() + ", Book ISBN : " + l.getBook().getIsbn());
                        foundLoan = true;
                    }
                }
                
                if (!foundLoan) {
                    System.out.println("No loans found for this member.");
                }
            }
        }
        
        if (!anyMember) {
            System.out.println("No member got membership in library till now.");
        }
    }

    // ================= FILE I/O =================

    public void loadBooksFromFile(String filename) throws Exception {
        
        File file = new File(filename);
    
        if (!file.exists()) {
            return; // if file doesn't exist, no books to load
        }

    
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        
        int index = 0;

        isLoading = true; // to suppress success messages

        while ((line = reader.readLine()) != null && index < books.length) {
        
            String[] parts = line.split(",", -1); // include empty values too

            if (parts.length >= 8) {
                String title = parts[0];
                String author = parts[1];
                String isbn = parts[2];
                String publisher = parts[3];
                
                int year = Integer.parseInt(parts[4]);
                boolean issued = Boolean.parseBoolean(parts[5]);
                String catName = parts[6];
                String catDesc = parts[7];

                category = new Category(catName, catDesc);
                books[index] = new Book(title, author, isbn, publisher, year, issued, category);

                index++;
            }
        }

        reader.close();
        isLoading = false;
    }





    public void loadMembersFromFile(String filename) throws Exception {
    
        File f = new File(filename);

        if (!f.exists()) {
            return;  
        } 

        
        BufferedReader br = new BufferedReader(new FileReader(f));

        String line;
        int index = 0;
        isLoading = true; // Suppress messages

        while ((line = br.readLine()) != null && index < members.length) {
            
            String[] parts = line.split(",");

            if (parts.length >= 6) {
                String name = parts[0];
                String contact = parts[1];
                String address = parts[2];
                String email = parts[3];
                String id = parts[4];
                String type = parts[5];

                if (type.equals("Student")) {
                    members[index] = new Student(name, contact, address, email, id);
                } else {
                    members[index] = new Staff(name, contact, address, email, id);
                }

                index++;
            }
        }

            br.close();
            isLoading = false;
    }


    public void loadLoansFromFile(String filename) throws Exception {
    
        File f = new File(filename);

        if (!f.exists()) {
            return;
        }

        FileReader fr = new FileReader(f);
    
        BufferedReader br = new BufferedReader(fr);

        String line;

        while ((line = br.readLine()) != null) {
            
            String[] parts = line.split(",");

            if (parts.length >= 6) {
                
                String loanId = parts[0];
                String isbn = parts[1];
                String memberId = parts[2];
                LocalDateTime issueDate = LocalDateTime.parse(parts[3]);
                LocalDateTime dueDate = LocalDateTime.parse(parts[4]);
                String memberName = parts[5]; // optional, useful for record but not needed to construct

                Book book = SearchBookByIsbn(isbn);
                Member member = SearchMemberById(memberId);

                if (book != null && member != null) {
                    Loan loan = new Loan(book, member, issueDate, dueDate, null);
                    loan.setLoanId(loanId); // optional, if you're assigning IDs

                    for (int i = 0; i < loans.length; i++) {
                        
                        if (loans[i] == null) {
                            loans[i] = loan;
                            book.setIssued(true);
                            book.setIssuedTo(member);
                            break;
                        }
                    }
                }
            }
        }

                br.close();
    }

}

    

