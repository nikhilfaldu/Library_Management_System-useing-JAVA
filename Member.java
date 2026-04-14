// Member.java - This class stores information about a library member

public class Member {

    // Variables to store member details
    int id;
    String name;
    String username;
    String password;
    String phone;
    String email;
    String joinDate;
    boolean isActive;

    // Constructor - used to create a new member
    Member(int id, String name, String username, String password, String phone, String email, String joinDate) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.joinDate = joinDate;
        this.isActive = true;  // new member is always active
    }
}
