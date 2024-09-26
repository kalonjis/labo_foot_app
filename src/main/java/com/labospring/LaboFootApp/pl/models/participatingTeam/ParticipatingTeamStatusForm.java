package com.labospring.LaboFootApp.pl.models.participatingTeam;

import com.labospring.LaboFootApp.dl.enums.SubscriptionStatus;
import jakarta.validation.constraints.NotNull;

public record ParticipatingTeamStatusForm(@NotNull SubscriptionStatus subscriptionStatus) {}
