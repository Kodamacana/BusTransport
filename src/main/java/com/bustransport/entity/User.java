package com.bustransport.entity;

import com.bustransport.permissions.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nameSurname;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;

    //Yetki belirtme metodu
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    //Yetki süresi dolup dolmadığını kontrol eder
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //Kullanıcı hesabı kilitli mi değil mi
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //kullanıcı aktif mi pasif mi kontrolü
    @Override
    public boolean isEnabled() {
        return true;
    }

}
