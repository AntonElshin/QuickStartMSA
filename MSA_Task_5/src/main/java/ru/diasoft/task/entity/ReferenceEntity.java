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
@Table(name = "REF_REFERENCE")
public class ReferenceEntity {

    @Id
    @Column(name = "REFERENCEID")
    @SequenceGenerator( name = "REF_REFERENCE_SEQ", sequenceName = "REF_REFERENCE_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REF_REFERENCE_SEQ" )
    private Long id;

    @Column(name = "REFERENCENAME")
    private String name;

    @Column(name = "REFSYSNAME")
    private String sysname;

    @Column(name = "DESCRIPTION")
    private String description;

    public ReferenceEntity(String name, String sysname, String description) {
        this.name = name;
        this.sysname = sysname;
        this.description = description;
    }

}
