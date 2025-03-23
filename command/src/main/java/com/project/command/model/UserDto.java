package com.project.command.model;

import java.util.Objects;

public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String userName;

    public UserDto(String firstName, String lastName, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
    }

    public UserDto() {

    }

    public Long getId() {
        return id;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

@Override
public String toString() {
    return "UserDao{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", userName='" + userName + '\'' +
            '}';
}

@Override
public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    UserDto userDto = (UserDto) o;
    return Objects.equals(id, userDto.id) && Objects.equals(firstName, userDto.firstName) && Objects.equals(lastName, userDto.lastName) && Objects.equals(userName, userDto.userName);
}

@Override
public int hashCode() {
    return Objects.hash(id, firstName, lastName, userName);
}
}
