package app.proxime.lambda.src.domain.user;

public class User {

    private String id;
    private String name;
    private String phone;
    private String username;
    private String email;
    private String password;
    private String familyName;

    public User(
            String name,
            String familyName,
            String username,
            String email,
            String password,
            String phone
    ) {
        this.name = name;
        this.familyName = familyName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(
            String id,
            String name,
            String familyName,
            String username,
            String email,
            String password,
            String phone
    ) {
        this.id = id;
        this.name = name;
        this.familyName = familyName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getUsername(){
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }



}
