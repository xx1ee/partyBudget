package ru.croc.kochetova.partyBudget.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @NotBlank(message = "Имя не должно быть пустым")
    String name;
    @NotBlank(message = "Фамилия не должна быть пустой")
    String lastname;
    @NotBlank(message = "Email не должен быть пустым")
    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Email не соответствует формату")
    String email;
    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 8, message = "Длина пароля - минимум 8 символов")
    String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    Role authority;
    BigDecimal wallet_amount;

    public User(@NotBlank String name, @NotBlank String lastname, @NotBlank @Pattern(regexp = "^\\S+@\\S+\\.\\S+$") String email, @NotNull String password, Role authority) {
        super();
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.authority = authority;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getEmail() {
        return this.email;
    }

    public Role getAuthority() {
        return authority;
    }

    public BigDecimal getWallet_amount() {
        return wallet_amount;
    }

    public void setWallet_amount(BigDecimal wallet_amount) {
        this.wallet_amount = wallet_amount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.authority);
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setName(@NotBlank String name) {
        this.name = name;
    }

    public void setLastname(@NotBlank String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(@NotBlank @Pattern(regexp = "^\\S+@\\S+\\.\\S+$") String email) {
        this.email = email;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    public void setAuthority(Role authority) {
        this.authority = authority;
    }
}
