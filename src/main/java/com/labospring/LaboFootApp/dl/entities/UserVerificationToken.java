package com.labospring.LaboFootApp.dl.entities;


import jakarta.persistence.*;
import lombok.*;


@AllArgsConstructor
@Entity
@Getter
@EqualsAndHashCode(callSuper = true)
public class UserVerificationToken extends BaseToken{

}
