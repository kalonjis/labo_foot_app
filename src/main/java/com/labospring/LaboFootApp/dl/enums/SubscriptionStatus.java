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

    public boolean isValidStatusTransition(SubscriptionStatus newStatus) {
        return switch (this) {
            case PENDING -> newStatus == ACCEPTED || newStatus == REJECTED || newStatus == CANCELED;
            case ACCEPTED -> newStatus == CANCELED || newStatus == REJECTED;
            case REJECTED -> newStatus == PENDING;
            case CANCELED -> newStatus == PENDING;
        };
    }
}
