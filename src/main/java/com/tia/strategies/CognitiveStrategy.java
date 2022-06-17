package com.tia.strategies;

import com.tia.algorithms.BFS;
import com.tia.enums.Content;
import com.tia.enums.Direction;
import com.tia.enums.Subject;
import com.tia.messages.Mail;
import com.tia.messages.MailBox;
import com.tia.models.Agent;
import com.tia.models.Box;

import java.util.List;
import java.util.PriorityQueue;

public class CognitiveStrategy implements Strategy {
    @Override
    public synchronized void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
    }

    private synchronized void processMails(Agent agent) {
//        System.out.println("-----> Agent is processing its mails");

        PriorityQueue<Mail> mailBox = MailBox.getAgentMails(agent);
//        GameUtils.print("mailBox", mailBox);

        Mail mail = MailBox.getAgentPriorityMail(agent);
//        GameUtils.print("mailToProcess", mailToProcess);

//        System.out.println("-----> Agent is performing the mail's content");
        Content content = mail.getContent();
        Agent sender = mail.getSender();

        System.out.println(agent.getValue().getText() + " received a mail of "
                + sender.getValue().getText());

        if (content.equals(Content.MOVE)) {
//            System.out.println("-----> Agent is moving");
//            move(agent, Direction.EAST);

            // If not stuck
            if (!agent.isStuck()) {
                // Go to free random box
                Direction randDirection = agent.getRandomDirection();
                move(agent, randDirection);

                if (agent.getValue().getText() == "G")
                    System.out.println("randDirection= " + randDirection);

                // TO FIX
                // Once Mail is processed, delete it
                MailBox.deleteAgentMail(mail, agent);
            } else {
                if (!agent.hasSendAMessage()) {
                    // Send message to min priority Agent
                    Agent neighbour = agent.getMinPriorityNeighbourAgentExceptOne(sender);
                    sendAMailWithMoveContentToNeighbour(agent, neighbour);
                }
            }
        } else if (content.equals(Content.OK)) {



        } else if (content.equals(Content.NOK)) {

        }

        // TODO notify mail has been processed
    }

    public void sendAMailWithMoveContentToNeighbour(Agent sender, Agent receiver) {
        // Send message to min priority Agent
        Mail mail = new Mail(sender, receiver, Subject.REQUEST, Content.MOVE);
        MailBox.sendMailTo(mail, receiver);
        sender.sentAMessage();

        System.out.println(sender.getValue().getText() + " sent a mail to "
                + receiver.getValue().getText());
    }

    private synchronized void reachDestination(Agent agent) {
        // If no path found
        List<Box> pathWithoutObstacles = BFS.findPath(agent, true);

        if (pathWithoutObstacles.isEmpty()) {
            // BFS without obstacle avoidance
            List<Box> path = BFS.findPath(agent, false);
            List<Direction> directions = BFS.convertPathToDirections(path);
            agent.setPathDirections(directions);

            Direction direction = directions.get(0);

            // If the next box is empty
            if (agent.getNeighbour(direction) == null) {
                move(agent, directions.get(0));
                agent.canSendAnotherMessage();
            } else {
                // Get the blocker
                Agent neighbour = agent.getNeighbour(direction);
                // Send Mail to the blocker
                if (!agent.hasSendAMessage()) {
                    sendAMailWithMoveContentToNeighbour(agent, neighbour);
                }
            }

        } else {
            // BFS with obstacle avoidance
            SimpleStrategy strategy = new SimpleStrategy();
            strategy.solve(agent);

        }
    }

    @Override
    public synchronized void solve(Agent agent) {
//        System.out.println("Agent " + agent.getValue().getText() + " is running...");
//        System.out.println(agent);

//        System.out.println(agent.getFreeNeighboursBox());

        if (!MailBox.getAgentMails(agent).isEmpty()) {
            processMails(agent);
        } else {
            if (!agent.isArrived()) reachDestination(agent);
//            if (agent.getValue().getText() == "A")
//                System.out.println("Go to destination: " + agent.getValue().getText());
        }
    }
}