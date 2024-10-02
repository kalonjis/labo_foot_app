package com.labospring.LaboFootApp.dl.enums;

public enum SubscriptionStatus {
    PENDING, ACCEPTED, REJECTED, CANCELED;

    public static boolean contains(SubscriptionStatus status) {
        for (SubscriptionStatus s : values()) {
            if (s == status) {
                return true;
            }
        }
        return false;
    }
}
