package app.proxime.lambda.src.domain.user;

public class User {

    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String phone;
    private String birthdate;

    public User(
            String id,
            String name,
            String username,
            String email,
            String password,
            String phone,
            String birthdate
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    public boolean passwordIsIncorrect(String password){
        return !this.password.equals(password);
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
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

    public String getBirthdate() {
        return birthdate;
    }


}
