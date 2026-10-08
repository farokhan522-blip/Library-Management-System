import java.util.Scanner;
import java.time.LocalDateTime;
import java.io.*;

public class Main{
    public static void main (String[] args) throws Exception{

        LibrarySystem system = new LibrarySystem();
        Scanner sc = new Scanner(System.in);


        while (true) {
           System.out.println("==== LIBRARY SYSTEM LOGIN ====");
            
            System.out.print("Username: ");
            String user = sc.nextLine();

            System.out.print("Password: ");
            String pass = sc.nextLine();

            if (system.getAdmin().login(user, pass)) {
                System.out.println("Login successful! Welcome, " + user);
                break;  // Exit the loop
            } 

            else {
                System.out.println("Invalid credentials. Please try again.\n");
            }
        }


        // Load data at start after loggin in
        system.loadBooksFromFile("books.txt");
        system.loadMembersFromFile("members.txt");
        system.loadLoansFromFile("loans.txt");

        int choice = -1;

        while (choice != 0) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Register Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. View All Books");
            System.out.println("8. Search Book by Isbn");
            System.out.println("9. Search Book by Title");
            System.out.println("10. View Available Books");
            System.out.println("11. View Issued Books");
            System.out.println("12. View Members");
            System.out.println("13. Search Member by ID");
            System.out.println("14. List Overdue Books");
            System.out.println("15. Calculate Fine");
            System.out.println("16. Get Loan Details of Members");
            System.out.println("0. Exit\n");
            System.out.print("Select an option : ");

            choice = sc.nextInt();
            sc.nextLine();

            System.out.println();

            if (choice == 1) {

                System.out.println("Add book details to add book");

                System.out.println();
                
                System.out.print("Title : ");
                String title = sc.nextLine();
                
                System.out.print("Author : ");
                String author = sc.nextLine();
                
                System.out.print("ISBN : ");
                String isbn = sc.nextLine();
                
                System.out.print("Publisher: ");
                String publisher = sc.nextLine();
                
                System.out.print("Publication Year: ");
                int pubYear = sc.nextInt();

                sc.nextLine();
                
                System.out.print("Enter Book Category name :");
                String categoryName = sc.next();

                System.out.print("Enter Book Category Description :");
                String categoryDesc = sc.next();

                sc.nextLine();
                                
                system.addBook( title,author,isbn,publisher,pubYear,false,categoryName,categoryDesc);
            }
            
            else if (choice == 2) {
                
                System.out.println("Enter Book's ISBN to remove book from system ");
                System.out.println();

                System.out.print("Enter ISBN to remove: ");
                String removeIsbn = sc.nextLine();
                
                system.removeBook(removeIsbn);
            }
            
            else if (choice == 3) {
                
                System.out.println("====Member Registration====");
                System.out.println();

                System.out.print("Member Type (1 for Student, 2 for Staff): ");
                int type = sc.nextInt();

                while(type < 1 || type > 2){
                    System.out.println("\nInvalid input , Enter a valid number again : ");
                    
                    System.out.print("Member Type (1 for Student, 2 for Staff): ");
                    type = sc.nextInt();
    
                }
                
                sc.nextLine(); 
                
                System.out.println();

                System.out.print("Name: ");
                String name = sc.nextLine();
                
                System.out.print("Contact : ");
                String contact = sc.nextLine();
                
                System.out.print("Address : ");
                String address = sc.nextLine();
                
                System.out.print("Email : ");
                String email = sc.nextLine();
    
                System.out.print("University Id : ");
                String uniId = sc.nextLine();

                system.registerMember(name,contact,address,email,uniId,type);
            }

            else if (choice == 4) {
                
                System.out.println("Eliminate membership of member by Entering Member-ID Below : ");
                System.out.println();

                System.out.print("Enter Member ID here to remove Member : ");
                String removeId = sc.nextLine();
                
                System.out.println();
                system.removeMember(removeId);
            }
            
            else if (choice == 5) {
                
                System.out.println("Issuing Book To a Member : ");
                System.out.println();

                System.out.print("Enter ISBN to issue book to Member : ");
                String issueIsbn = sc.nextLine();
                
                System.out.print("Member ID : ");
                String issueMemId = sc.nextLine();
                
                System.out.println();
                system.issueBook(issueIsbn, issueMemId);
            }
            
            else if (choice == 6) {
                
                System.out.println("Returning A borrowed(Loaned) Book Menu :");
                System.out.println();
                
                System.out.print("ISBN to return: ");
                String returnIsbn = sc.nextLine();
                
                System.out.println();
                system.returnBook(returnIsbn);  
            }
            
            else if (choice == 7) {
                System.out.println("All Book's Data :");
                System.out.println();

                system.viewAllBooks();
            }
            
            else if (choice == 8){

                System.out.println("Search A specific BOOK by It's ISBN : ");
                System.out.println();
                
                System.out.print("Enter ISBN Of Book to search: ");
                String i = sc.nextLine();
                
                System.out.println();
                
                Book b = system.SearchBookByIsbn(i);

                if (b != null) {
                    System.out.print(b);
                } else {
                    System.out.println("No Book of such ISBN is Available at Library!");
                }
                
                System.out.println();
            }
            

            else if (choice == 9){

                System.out.println("Search A specific BOOK by It's Title : ");
                System.out.println();
                
                System.out.print("Enter Title Of Book to search: ");
                String a = sc.nextLine();
                
                System.out.println();
                
                Book b = system.SearchBookByTitle(a);

                if (b != null) {
                    System.out.print(b);
                } else {
                     System.out.println("No Book of such title is Available at Library!");
                }
                
                System.out.println();
            }

            else if (choice == 10) {

                System.out.println("All Available Book's  : ");

                System.out.println();
                
                system.viewAvailableBooks();
                System.out.println();
            }
            
            else if (choice == 11) {

                System.out.println("All Issued books : ");

                System.out.println();
                system.viewIssuedBooks();
                System.out.println();
            }
            
            else if (choice == 12) {

                System.out.println("All Member's Data : ");

                System.out.println();
                system.viewMembers();
                System.out.println();
            }
            else if(choice == 13){
                
                System.out.println("Search a specific Member By Id: ");
                System.out.println();

                System.out.print("Enter the Member ID to Search member: ");
                String mID = sc.nextLine();
                
                System.out.println();

                Member m =  system.SearchMemberById(mID);

                if (m != null) {
                    System.out.print(m);
                } else {
                    System.out.println("No Member of such ID is Available at Library!");
                }

                
                System.out.println();
            }

            
            else if (choice == 14) {

                System.out.println("List of books whom dues aren't paid yet : ");
                System.out.println();

                system.listOverdueBooks();
            }
            
            else if (choice == 15) {
                
                System.out.println("Fine Calculation : ");
                System.out.println();

                System.out.print("Enter Loan ID to calculate fine: ");
                String loanId = sc.nextLine();
                
                System.out.println();
                
                double fine = system.calculateFine(loanId);
                System.out.println("Fine: Rs. " + fine);
            }

            else if (choice == 16){
                
                System.out.println("List of Member's who Borrowed (took Loan) Books From Library : ");
                System.out.println();

                system.listLoansByMember();
            }
            
            else if (choice == 0) {
                System.out.println("Thanks for using the Library System!");
            }
            
            else {
                System.out.println("Invalid option. Try again.");
            }
        }

        sc.close();
        
    }
}
