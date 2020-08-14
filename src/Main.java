import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IndexOutOfBoundsException {

        boolean exit = false;
        openLibrary();
        int answer;
        while(!exit) {
            promptInstructions();
            answer = scanner.nextInt();
            scanner.nextLine();
            switch (answer) {
                case 1 -> {
                    try {
                        UserLogin.loginUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    try {
                        createUser();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case 3 -> exit = true;
                default -> System.out.println("Please enter a valid number");
            }
        }
    }


//  Prompts user to sign up to use Library services or login(for existing user) or Quit
    public static void promptInstructions() {
        System.out.println("Press 1 to login" + "\nPress 2 to sign up (new user)" + "\nPress 3 to exit \n");
    }

//  Welcome message
    public static void openLibrary() {
        System.out.println("WELCOME TO SAMY'S LIBRARY\n" + "\n");
    }

//  Displays menu list for user
    public static void printInstructions() {
        System.out.println("\nPress ");
        System.out.println("\t 0 - Logout");
        System.out.println("\t 1 - See all available options");
        System.out.println("\t 2 - View all users");
        System.out.println("\t 3 - View all books");
        System.out.println("\t 4 - Borrow a book");
        System.out.println("\t 5 - Delete a user (specific to Librarians");
        System.out.println("\t 6 - Add a book (specific to Librarians");
        System.out.println("\t 7 - Remove a book (specific to Librarians");
        System.out.println("\t 8 - Approve a book request (specific to Librarians");
        System.out.println("\t 9 - return a borrowed book");
        System.out.println("\t 10 - lists book borrowed by user");
    }

//  Registers new Library user
    public static void createUser() throws Exception {
        int count = User.all_Library_Users.size() +1;
        System.out.println("Enter your email address\r");
        String email = scanner.nextLine();
        boolean isExist = User.all_Library_Users.containsKey(email);
        if (isExist) {
            System.out.println("Email already exists");
            return;
        } else {
            System.out.println("Enter your name\r");
            String name = scanner.nextLine();
            System.out.println("Type: \tL, if you are the Librarian," + "\n" + "\t" + "\tT, if you are a Teacher or" +"\n" + "\t" + "\tS, if you are a Student\r");
            String userType = scanner.nextLine().toLowerCase();
            if (userType.equals("s")) {
                System.out.println("Enter level: \n1 for Junior Student, \n2 for Senior student \r");
                int studentType = scanner.nextInt();
                Student student;
                if (studentType == 1) {
                    student = new Student(name, email, count, type.juniorStudent);
                } else {
                    student = new Student(name, email, count, type.seniorStudent);
                }
                User.createUser(email, student);
            } else if (userType.equals("t")) {
                Teacher teacher = new Teacher(name, email, count);
                User.createUser(email, teacher);
            } else {
                Librarian librarian = new Librarian(name, email, count);
                User.createUser(email, librarian);
            }
        }
        UserLogin.loginUser();
    }


//  Displays book Available in library
    public static int listBooks() {
        System.out.println("There are " + Book.allBooks.size() + " book(s) in the library");
        Iterator<Map.Entry<String, Book>> bookIterator = Book.allBooks.entrySet().iterator();
        System.out.println("\tS/N \t\tBook Title \t\t\tQuantity of books");
        while (bookIterator.hasNext()) {
            Map.Entry<String, Book> entry = bookIterator.next();
            System.out.println("\t" + entry.getValue().getId() + "\t\t" + entry.getValue().getTitle().toUpperCase() + "\t\t\t\t" + entry.getValue().getQuantity());
        }
        return Book.allBooks.size();
    }

//  adds library user to Borrow Queue
    private static boolean addMemberToQueue(String loggedInUser, String requestedBook) {
        int oldQty = Book.allBooks.get(requestedBook).getQuantity();
        Book.allBooks.get(requestedBook).setQuantity(oldQty);
        CustomQueue<String> newBorrower = new CustomQueue<>();
        newBorrower.add(loggedInUser);
        Library.booksToBeBorrowed.put(requestedBook, newBorrower);
        Library.booksToBeBorrowedWithPriority.put(requestedBook, newBorrower);
        System.out.println("You have been added to the waiting list");
        System.out.println(requestedBook +": " + Library.booksToBeBorrowed.get(requestedBook).printQueue());
        System.out.println(requestedBook +": " + Library.booksToBeBorrowedWithPriority.get(requestedBook).printQueue());
        return true;
    }

//  adds book to library (Restricted to Librarian only)
    public static void addBook() {
        int bookCount = Book.allBooks.size();
        String loggedInUser = UserLogin.getUserEmail();
        if (User.all_Library_Users.get(loggedInUser).getUserType().equals(type.librarian)) {
            System.out.println("What's the title of the book to be added to library\r");
            String bookTitle = scanner.nextLine().toLowerCase();
            Book existingBookName = Book.allBooks.get(bookTitle);
            if (existingBookName != null) {
                int qty = existingBookName.getQuantity();
                existingBookName.setQuantity(qty+1);
            } else {
                System.out.println("Who's the author of the book?\r");
                String bookAuthor = scanner.nextLine();
                System.out.println("Who's the publisher of the book?\r");
                String bookPublisher = scanner.nextLine();
                Book newBook = new Book(bookTitle, bookAuthor, bookPublisher, bookCount+1, 1);
                Book.addBook(bookTitle, newBook);
            }
            System.out.println("Book Added");
            listBooks();
        } else {
            System.out.println("Only a Librarian can perform this action");
        }
    }

//  Method for user to borrow book
    public static boolean borrowBook() {
        String loggedInUser = UserLogin.getUserEmail();
        System.out.println("Enter the title of the book you want to borrow\r");
        String requestedBook = scanner.nextLine().toLowerCase();
        if (Book.allBooks.containsKey(requestedBook)) {
            if (Library.booksToBeBorrowed.size() == 0) {
                return addMemberToQueue(loggedInUser, requestedBook);
            }
            if (Library.booksToBeBorrowedWithPriority.size() == 0) {
                return addMemberToQueue(loggedInUser, requestedBook);
            }
            if (Library.booksToBeBorrowed.containsKey(requestedBook)) {
                Object[] allQueueMembers = Library.booksToBeBorrowed.get(requestedBook).getAllQueueMembers();
                for (Object allQueueMember : allQueueMembers) {
                    if (allQueueMember == loggedInUser) {
                        System.out.println("You are already on the waiting list");
                        return false;
                    }
                }
                if (Book.allBooks.get(requestedBook).getQuantity() != 0) {
                    Library.booksToBeBorrowed.get(requestedBook).add(loggedInUser);
                    int oldQty = Book.allBooks.get(requestedBook).getQuantity();
                    Book.allBooks.get(requestedBook).setQuantity(oldQty);
                    System.out.println("You have been added to the waiting list");
                    System.out.println(requestedBook +": " + Library.booksToBeBorrowed.get(requestedBook).printQueue());
                    return true;
                } else {
                    System.out.println("Book taken");
                    return false;
                }
            } else {
                return addMemberToQueue(loggedInUser, requestedBook);
            }
        } else {
            System.out.println("This book is currently not available in our Library");
            return false;
        }
    }


//  Method for user to return book
    public static boolean returnBook() {
        String loggedInUser = UserLogin.getUserEmail();
        System.out.println("Enter the title of the book you want to return\r");
        String returnedBook = scanner.nextLine().toLowerCase();
        if (User.all_Library_Users.containsKey(loggedInUser)) {
            if (Book.allBooks.containsKey(returnedBook)) {
                int oldQty = Book.allBooks.get(returnedBook).getQuantity();
                Book.allBooks.get(returnedBook).setQuantity(oldQty + 1);
                System.out.println("Book returned");
            } else {
                System.out.println("No such book borrowed");
            }
            return true;
        }
        return false;
    }

//  deletes book from library (Restricted to Librarian only)
    public static boolean deleteBook() {
        String loggedInUser = UserLogin.getUserEmail();
        if (User.all_Library_Users.get(loggedInUser).getUserType().equals(type.librarian)) {
            System.out.println("What's the title of the book you want to delete?\r");
            String bookTitle = scanner.nextLine().toLowerCase();
            Book existingBookName = Book.allBooks.get(bookTitle);
            if (existingBookName != null) {
                if (existingBookName.getQuantity() == 0) {
                    System.out.println("There are no " + existingBookName.getTitle() + " books in the Library presently");
                    return false;
                }
                System.out.println("There are " + existingBookName.getQuantity() + " books in the library");
                System.out.println("Do you want to delete all the " + existingBookName.getTitle() + "books");
                System.out.println("\nIf YES press Y, else press the number of books you want to delete");
                String answer = scanner.nextLine();
                int qty = existingBookName.getQuantity();
                if (answer.equalsIgnoreCase("y")) {
                    existingBookName.setQuantity(0);
                    System.out.println("You have deleted all the " + existingBookName.getTitle() + " books from the Library");
                } else {
                    existingBookName.setQuantity(qty - Integer.parseInt(answer));
                    System.out.println("You have deleted " + Integer.parseInt(answer) + existingBookName.getTitle() + " books from the Library\n" +
                            "There are now " +existingBookName.getQuantity() + " books in the Library");
                    return true;
                }
            } else {
                System.out.println("There's no such book in the Library");
                return true;
            }
        } else {
            System.out.println("Only a Librarian can perform this action");
            return false;
        }
        return false;
    }

//  deletes user from library (Librarian only)
    public static void deleteUser() {
        String loggedInUser = UserLogin.getUserEmail();
        if (User.all_Library_Users.get(loggedInUser).getUserType().equals(type.librarian)) {
            System.out.println("Enter the email of the user to be deleted\r");
            String email = scanner.nextLine();
            User.deleteUser(email);
        } else {
            System.out.println("Only a Librarian can perform this action");
        }
    }


//  Approve book method based on priority(Teacher before senior student before junior student) or on a first come, first served basis
    public static boolean approveBook() throws Exception {
        String loggedInUser = UserLogin.getUserEmail();
        if (User.all_Library_Users.get(loggedInUser).getUserType().equals(type.librarian)) {
            System.out.println("Do you want to approve based on custom or priority queue?(C/P)\r");
            String answer = scanner.nextLine();
            System.out.println("\tS/N \t\tBook Title \t\t\tRequester");

        //  approves book on a first come, first served basis
            if (answer.equalsIgnoreCase("c")) {

                Iterator<Map.Entry<String, CustomQueue<String>>> bookRequestIterator = Library.booksToBeBorrowed.entrySet().iterator();
                while (bookRequestIterator.hasNext()) {
                    int count = 1;
                    Map.Entry<String, CustomQueue<String>> entry = bookRequestIterator.next();
                    System.out.println("\t" + count + "\t\t" + entry.getKey() + "\t\t\t\t" + entry.getValue().toString());
                }
                System.out.println("Which of the books do you want to approve request for?\r");
                String ans = scanner.nextLine();
                if(Library.booksToBeBorrowed.get(ans) != null) {
                    String bookCollector = Library.booksToBeBorrowed.get(ans).remove();
                    System.out.println("The book was given out to " + bookCollector);
                    int oldQty = Book.allBooks.get(ans).getQuantity();
                    Book.allBooks.get(ans).setQuantity(oldQty - 1);
                    return true;
                } else {
                    System.out.println("There is no such book in the queue");
                    return false;
                }

        //  approves book on a priority order
            } else if (answer.equalsIgnoreCase("p")){
                Iterator<Map.Entry<String, CustomQueue<String>>> bookRequestIterator = Library.booksToBeBorrowedWithPriority.entrySet().iterator();
                while (bookRequestIterator.hasNext()) {
                    int count = 1;
                    Map.Entry<String, CustomQueue<String>> entry = bookRequestIterator.next();
                    System.out.println("\t" + count + "\t\t" + entry.getKey() + "\t\t\t\t" + entry.getValue().toString());
                }
                System.out.println("Which of the books do you want to approve request for?\r");
                String ans = scanner.nextLine();
                if(Library.booksToBeBorrowedWithPriority.get(ans) != null) {
                    Library.priorityBooks.addAll((Collection<? extends User>) Library.booksToBeBorrowedWithPriority.get(ans));
                    User bookCollector = Objects.requireNonNull(Library.priorityBooks.poll());
                    System.out.println("The book was given out to " + bookCollector.getUserType() + " " + bookCollector.getEmail());
                    int oldQty = Book.allBooks.get(ans).getQuantity();
                    Book.allBooks.get(ans).setQuantity(oldQty - 1);
                    return true;
                } else {
                    System.out.println("There is no such book in the queue");
                    return false;
                }
        //  Returns error message to user for invalid input
            } else {
                System.out.println("You have not chosen an invalid key");
                return false;
            }
//  Error message when accessed by non-Librarian
    } else {
        System.out.println("Only a Librarian can perform this action");
        return false;
        }
    }
}
