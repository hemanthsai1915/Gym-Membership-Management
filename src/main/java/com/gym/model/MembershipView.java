package com.gym.model;

public class MembershipView {

    private Membership membership;
    private String displayStatus;
    private long daysRemaining;
    private int progressPercent;

    // Constructor
    public MembershipView(Membership membership, String displayStatus, long daysRemaining, int progressPercent) {
        this.membership = membership;
        this.displayStatus = displayStatus;
        this.daysRemaining = daysRemaining;
        this.progressPercent = progressPercent;
    }

    // Static fallback (VERY IMPORTANT)
    public static MembershipView noPlan() {
        return new MembershipView(null, "NO PLAN", 0, 0);
    }

    // Getters
    public Membership getMembership() {
        return membership;
    }

    public String getDisplayStatus() {
        return displayStatus;
    }

    public long getDaysRemaining() {
        return daysRemaining;
    }

    public int getProgressPercent() {
        return progressPercent;
    }

    // Helper methods
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(displayStatus);
    }

    public boolean hasMembership() {
        return membership != null;
    }
}