
import java.util.*;

public class UserLogin {

    public static String userEmail;
    private static final Scanner scanner = new Scanner(System.in);


//   Setter for Initialized variable(userEmail)
    public static String getUserEmail() {
        return userEmail;
    }

//  getter for Initialized variable(userEmail)
    public static void setUserEmail(String userEmail) {
        UserLogin.userEmail = userEmail;
    }

//  Prompts user to login using Email
    public static void loginUser() throws Exception {
        System.out.println("Enter your email to login\r");
        String email = scanner.nextLine();
        boolean exit = false;
        boolean existing_User = User.loginUser(email);

        while (!exit) {
            if (!existing_User) {
                System.out.println("Incorrect email address");
                return;
            } else {
                exit = true;
            }
        }
        printInstructionToUser();
    }

//  Prints instruction page for user on how to use Library
    public static void printInstructionToUser() throws Exception {
        int selection = 0;
        boolean exit = false;

        while(!exit) {
            System.out.println("Enter your selection: (select 1 to show menu)");
            selection = scanner.nextInt();
            scanner.nextLine();
            switch (selection) {
                case 0 -> {
                    UserLogin.setUserEmail(null);
                    exit = true;
                }
                case 1 -> Main.printInstructions();
                case 2 -> User.listOfUsers();
                case 3 -> Main.listBooks();
                case 4 -> Main.borrowBook();
                case 5 -> Main.deleteUser();
                case 6 -> Main.addBook();
                case 7 -> Main.deleteBook();
                case 8 -> Main.approveBook();
                case 9 -> Main.returnBook();
                default -> { System.out.println("INVALID SELECTION");
                             Main.printInstructions();
                        }
                }
            }
        }

    }
