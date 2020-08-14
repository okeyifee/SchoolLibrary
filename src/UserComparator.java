import java.util.Comparator;

public class UserComparator implements Comparator<User> {

/*  Assumption made:
    Secondary school ranges from JSS1 to SS3. In order to determine hierarchy,
    user are assigned id's according to their classes as follows:

    User                  id(No)
    Junior Students        1
    Senior Students        2
    Teachers               3
*/

    public int compare(User o1, User o2) {
        type user1 = o1.getUserType();
        type user2 = o2.getUserType();

//
        if (user1.compareTo(user2) > 0) {
            return -1;
        } else if (user1.compareTo(user2) < 0) {
            return 1;
        }
        return 0;
    }
}