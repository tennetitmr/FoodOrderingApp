package org.upgrad.models;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/* Controller  ------> Services Layer ---> Data Access Layer ( Model)
 * UserAuthToken model: Maps attributes --> fields in the user_auth_token table.
 * Also, it contains Annotations, getters and setters. Annotations map the fields to table columns.
 */
@Entity
@Table(name="user_auth_token")
public class UserAuthToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "access_token")
    @NotNull
    private String accessToken;

    @Column(name = "login_at")
    @NotNull
    private Date loginAt;

    @Column(name = "logout_at")
    private Date logoutAt;

    public UserAuthToken() {
    }

    public UserAuthToken(User user, @NotNull String accessToken, @NotNull Date loginAt) {
        this.user = user;
        this.accessToken = accessToken;
        this.loginAt = loginAt;
    }

    // Getters &
    // Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getLoginAt() {
        return loginAt;
    }

    public void setLoginAt(Date loginAt) {
        this.loginAt = loginAt;
    }

    public Date getLogoutAt() {
        return logoutAt;
    }

    public void setLogoutAt(Date logoutAt) {
        this.logoutAt = logoutAt;
    }

}
