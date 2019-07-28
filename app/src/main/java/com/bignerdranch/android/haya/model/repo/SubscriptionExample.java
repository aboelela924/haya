package com.bignerdranch.android.haya.model.repo;

public class SubscriptionExample {

    private Subscription subscription;

    public Subscription getSubscription ()
    {
        return subscription;
    }

    public void setSubscription (Subscription subscription)
    {
        this.subscription = subscription;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [subscription = "+subscription+"]";
    }

}
