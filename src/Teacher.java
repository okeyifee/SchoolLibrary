import java.util.*;

public class Teacher extends User {

//  Teacher class constructor
    public Teacher(String name, String email, int Id) {
        super(name, email, Id, type.teacher);
    }
}