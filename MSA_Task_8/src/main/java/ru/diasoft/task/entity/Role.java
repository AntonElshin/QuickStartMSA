package ru.diasoft.task.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
