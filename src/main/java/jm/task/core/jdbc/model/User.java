package jm.task.core.jdbc.model;

import javax.persistence.*;

@Table
public class User {
    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private Byte age;

    public User() {

    }

    public User(String name, String lastName, Byte age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long a_id) {
        this.id = a_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String b_name) {
        this.name = b_name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String c_lastName) {
        this.lastName = c_lastName;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return id.toString() + " " + name + " " + lastName + " " + age.toString();
    }
}
