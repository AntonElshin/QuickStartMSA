package ru.diasoft.task.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CORE_USERACCOUNT")
public class UserEntity {

    @Id
    @Column(name = "USERACCOUNTID")
    @SequenceGenerator(name = "CORE_USERACCOUNT_SEQ", sequenceName = "CORE_USERACCOUNT_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CORE_USERACCOUNT_SEQ" )
    private Long id;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    private String status;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "CORE_ROLEACCOUNT",
            joinColumns = @JoinColumn(name = "USERACCOUNTID"),
            inverseJoinColumns = @JoinColumn(name = "ROLEID")
    )
    private List<RoleEntity> roles;



}
