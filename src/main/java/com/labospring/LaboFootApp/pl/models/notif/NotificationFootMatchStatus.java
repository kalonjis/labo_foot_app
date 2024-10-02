package com.labospring.LaboFootApp.pl.models.notif;

import com.labospring.LaboFootApp.dl.enums.MatchStatus;

public record NotificationFootMatchStatus(String teamHome, String teamAway, MatchStatus matchStatus, String message) {
}
