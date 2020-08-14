import java.util.*;

/*  Assumptions made:
    Names are not unique(two users can bear the same name) but emails are.
    Logins and book requests are made using emails cos of the uniqueness.
    removing a user by the librarian is achieved using user's registered email address
*/


//  initializing list of constants for priority
    enum type {
        juniorStudent,
        seniorStudent,
        teacher,
        librarian
    }

    public abstract class User {
        private String name;
        private String email;
        private int id;
        private type userType;
        public static Map<String, User> all_Library_Users = new HashMap<>();


    public User(String name, String email, int id, type userType) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.userType = userType;
        }


    public String getName() {
        return name;
        }

    public String getEmail() {
        return email;
        }

    public int getId() {
        return id;
        }

    public type getUserType() {
        return userType;
        }


//  Creates a user and stores user details in all_library_users map.
    public static User createUser(String email, User user) {
        all_Library_Users.put(email, user);
        return null;
        }

//  Checks if a user details(Email) exist in map and if found, deletes user.....(Only accessed by Librarian)
    public static User deleteUser(String email) {
        if (!all_Library_Users.containsKey(email)) {
            System.out.println("User does not exist");
            all_Library_Users.remove(email);
            System.out.println("User successfully deleted");
            }
            return null;
        }

//  Logs in a registered member to use the library
    public static boolean loginUser(String email) {
        if (!all_Library_Users.containsKey(email)) {
            System.out.println("User not found");
            return false;
        } else {
            System.out.println("Welcome " + all_Library_Users.get(email).getName());
            UserLogin.setUserEmail(email);
            return true;
        }
    }

//  Lists out all library users(members)
    public static void listOfUsers() {
        System.out.println("There are " + all_Library_Users.size() + " members who use the Library");
        Iterator<Map.Entry<String, User>> iterator = all_Library_Users.entrySet().iterator();
        System.out.println("\tS/N \t\tName \t\t\tUser_Type");
        while (iterator.hasNext()) { Map.Entry<String, User> entry = iterator.next();
            System.out.println("\t" + entry.getValue().getId() + " \t\t" + entry.getValue().getName().toUpperCase() + "\t\t\t " + entry.getValue().getUserType().toString().toUpperCase());
            }
        }
    }

