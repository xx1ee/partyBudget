package ru.croc.kochetova.partyBudget.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Table(name = "membership_application")
public class MembershipApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
    @Enumerated(EnumType.STRING)
    Status status;

    public MembershipApplication(User user) {
        this.user = user;
        this.status = Status.CREATED;
    }

    public MembershipApplication() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
