package io.github.khietbt.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@SuperBuilder
@Table("profiles")
@ToString(
        callSuper = true
)
public class Profile extends Auditable {
    private String userId;

    private String email;

    private String phone;
}
