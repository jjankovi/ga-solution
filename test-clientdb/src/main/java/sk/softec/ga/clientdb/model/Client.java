package sk.softec.ga.clientdb.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jankovj on 12. 8. 2016.
 */
@Entity
public class Client {

    private static final String SEQUENCE_NAME = "CLIENT_SEQ";

    @Id
    @Column(name = "ID", unique = true, nullable = false, precision = 22, scale = 0)
    @GenericGenerator(name = SEQUENCE_NAME, strategy = "sequence", parameters = {@org.hibernate.annotations.Parameter(name = "sequence", value=SEQUENCE_NAME)})
    @GeneratedValue(generator = SEQUENCE_NAME, strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "BIRTHDAY")
    @Temporal(TemporalType.DATE)
    private Date birthDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }
}
