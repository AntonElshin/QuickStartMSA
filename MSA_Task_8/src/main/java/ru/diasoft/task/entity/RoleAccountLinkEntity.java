package ru.diasoft.task.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "CORE_ROLEACCOUNT")
public class RoleAccountLinkEntity {

    @Id
    @Column(name = "LINKID")
    @SequenceGenerator( name = "CORE_ROLEACCOUNT_SEQ", sequenceName = "CORE_ROLEACCOUNT_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CORE_ROLEACCOUNT_SEQ" )
    private Long id;

    @Column(name = "USERACCOUNTID")
    private Long useraccountid;

    @Column(name = "ROLEID")
    private Long roleid;

}
