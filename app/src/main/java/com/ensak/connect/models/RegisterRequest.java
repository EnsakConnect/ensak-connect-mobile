package com.ensak.connect.models;

public class RegisterRequest {
    String firstname;
    String lastname;
    String email;
    String password;
    //String role;




    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

   /*public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
*/
    @Override
    public String toString() {
        return "RegisterRequest{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
