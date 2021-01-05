package ru.diasoft.task.entity;

import lombok.*;
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
@Table(name = "CORE_USERROLE")
public class RoleEntity {

    @Id
    @Column(name = "ROLEID")
    @SequenceGenerator( name = "CORE_USERROLE_SEQ", sequenceName = "CORE_USERROLE_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CORE_USERROLE_SEQ" )
    private Long id;

    @Column(name = "ROLENAME")
    private String name;

    @Column(name = "ROLESYSNAME")
    private String sysname;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "CORE_ROLEACCOUNT",
            joinColumns = @JoinColumn(name = "ROLEID"),
            inverseJoinColumns = @JoinColumn(name = "USERACCOUNTID")
    )
    List<UserEntity> users;

}
