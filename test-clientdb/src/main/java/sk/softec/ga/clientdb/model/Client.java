package sk.softec.ga.clientdb.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

    private String login;

    private String password;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
