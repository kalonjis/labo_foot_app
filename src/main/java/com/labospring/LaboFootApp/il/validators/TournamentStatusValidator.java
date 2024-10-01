package com.labospring.LaboFootApp.il.validators;

import com.labospring.LaboFootApp.dl.enums.TournamentStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.labospring.LaboFootApp.il.annotation.ValidTournamentStatus;



public class TournamentStatusValidator implements ConstraintValidator<ValidTournamentStatus, TournamentStatus> {

    public boolean isValid(TournamentStatus value, ConstraintValidatorContext context) {
        // Vérifiez si la valeur est bien présente dans l'enum TournamentStatus
        return TournamentStatus.contains(value); // Utilisation d'une méthode utilitaire
    }

}
