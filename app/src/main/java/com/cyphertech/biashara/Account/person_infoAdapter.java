package com.cyphertech.biashara.Account;

public class person_infoAdapter {

    private String firstName;
    private String lastName;
    private String phone;
    private String gender;

//    public person_infoAdapter() {
//        this.firstName = firstName;
//        this.phone = phone;
//       // this.gender = gender;
//    }


    public person_infoAdapter(String phone) {
        this.phone = phone;
     }

    public person_infoAdapter(String phone, String gender, String firstName, String lastName) {
        this.phone=phone;
        this.gender=gender;
        this.firstName=firstName;
        this.lastName=lastName;
    }


    public String getFirstName() {
        return firstName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
