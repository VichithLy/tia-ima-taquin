package com.tia.models;

import com.tia.enums.Direction;
import com.tia.enums.Letter;
import com.tia.strategies.Context;
import com.tia.strategies.NaiveStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Agent implements Runnable {
    private Letter value;
    private Box source;
    private Box destination;
    private Box current;
    private List<Direction> pathDirections;
    private Context context;

    private CountDownLatch latch;

    public Agent(Letter value, Box current, Box destination, Context context) {
        this.value = value;
        this.destination = destination;
        this.source = current;
        this.current = current;
        this.pathDirections = new ArrayList<>();
        this.context = context;
    }

    // Methods

    public boolean isArrived() {
        return (current.equals(destination));
    }

    public void solve() {
        context.executeStrategy(this);
    }

    // Getters & Setters

    public Letter getValue() {
        return value;
    }

    public void setValue(Letter value) {
        this.value = value;
    }

    public Box getSource() {
        return source;
    }

    public void setSource(Box source) {
        this.source = source;
    }

    public Box getDestination() {
        return destination;
    }

    public void setDestination(Box destination) {
        this.destination = destination;
    }

    public Box getCurrent() {
        return current;
    }

    public void setCurrent(Box current) {
        this.current = current;
    }

    public List<Direction> getPathDirections() {
        return pathDirections;
    }

    public void setPathDirections(List<Direction> pathDirections) {
        this.pathDirections = pathDirections;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    // Functions

    @Override
    public String toString() {
        return "Agent{" +
                "value=" + value +
                ", current=" + current.toString() +
                ", destination=" + destination.toString() +
                '}';
    }

    @Override
    public void run() {
        System.out.println("Agent running...");

        solve();

        this.latch.countDown();
    }
}
