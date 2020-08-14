import java.util.*;

public class Library {

//  List of Library books to be borrowed
    public static Map<String, CustomQueue<String>> booksToBeBorrowed = new HashMap<>();
    public static PriorityQueue<User> priorityBooks = new PriorityQueue<>(new UserComparator());
    public static Map<String, CustomQueue<String>> booksToBeBorrowedWithPriority = new HashMap<String, CustomQueue<String>>();
}