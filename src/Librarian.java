import java.util.*;

public class Librarian extends User {

//  Librarian class constructor
    public Librarian(String name, String email, int id) {
        super(name, email, id, type.librarian);
    }
}