package edu.upc.dsa.models;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = {"id", "name", "surname", "email", "birthday, history"})
public class User {

    Integer id;
    String name;
    String surname;
    String email;
    String birthday;
    List<PointofInterest> history;

    public User() {
    }

    public User(Integer id, String name, String surname, String email, String birthday) {
        this.setId(id);
        this.setName(name);
        this.setSurname(surname);
        this.setEmail(email);
        this.setBirthday(birthday);
    }

    @XmlElement(name = "id")
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @XmlElement(name = "email")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    @XmlElement(name = "birthday")
    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    @XmlElement(name = "history")
    public List<PointofInterest> getHistory()
    {
        return history;
    }

    public void setHistory(PointofInterest history)
    {
        this.history.add(history);
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", hours=" + email + ", email=" + email + ", birthday=" + birthday + " history=" + history + "]";
    }

}