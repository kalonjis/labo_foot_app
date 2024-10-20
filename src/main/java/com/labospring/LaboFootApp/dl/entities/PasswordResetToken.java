package com.labospring.LaboFootApp.dl.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PasswordResetToken extends BaseToken {

}
