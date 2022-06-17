package com.tia.strategies;

import com.tia.GameUtils;
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

        // If direction D
        // If in bounds
        // If no other Agent
        // Move
        // Else
        // Send message to this Agent
        // Else
        // Don't move
    }

    private synchronized void processMails(Agent agent) {
//        System.out.println("-----> Agent is processing its mails");

        PriorityQueue<Mail> mailBox = MailBox.getAgentMails(agent);
        System.out.println("=====\n");
        GameUtils.print("mailBox", mailBox);

        Mail mailToProcess = MailBox.getAgentPriorityMail(agent);
//        GameUtils.print("mailToProcess", mailToProcess);

        Agent sender = mailToProcess.getSender();
        Subject subject = mailToProcess.getSubject();
        Content content = mailToProcess.getContent();

//        System.out.println("-----> Agent is performing the mail's content");
        performContent(agent, content);

        // Once Mail is processed, delete it
        MailBox.deleteAgentMail(mailToProcess, agent);

        // TODO notify mail has been processed
    }

    private synchronized void performContent(Agent agent, Content content) {
        // TODO
        if (content.equals(Content.MOVE)) {
//            System.out.println("-----> Agent is moving");
            move(agent, Direction.EAST);

        }
    }

    private synchronized void reachDestination(Agent agent) {
        // If agent is stuck (all neighbours are other agents)
        if (agent.isStuck()) {
//            System.out.println("-----> Agent is stuck");
            // BFS without obstacle avoidance
            List<Box> path = BFS.findPath(agent, false);
            List<Direction> directions = BFS.convertPathToDirections(path);
            agent.setPathDirections(directions);

//            GameUtils.print("pathDirections", agent.getPathDirections());

            if (!directions.isEmpty()) {
//                System.out.println("-----> Agent send a Mail to its neighbour");
                Direction direction = directions.get(0);
                Agent neighbour = agent.getNeighbour(direction);
//                GameUtils.print("neighbour", neighbour);

                // Send Mail to neighbour

                if (!agent.hasSendAMessage()) {
                    Mail mail = new Mail(agent, neighbour, Subject.REQUEST, Content.MOVE);
                    MailBox.sendMailTo(mail, neighbour);
                    agent.sentAMessage();
                }
            }

        } else {
//            System.out.println("-----> Agent is not stuck");
            // BFS with obstacle avoidance
            SimpleStrategy strategy = new SimpleStrategy();
            strategy.solve(agent);
        }
    }

    @Override
    public synchronized void solve(Agent agent) {
        System.out.println("Agent " + agent.getValue().getText() + " is running...");
        System.out.println(agent);


        if (!MailBox.getAgentMails(agent).isEmpty()) {
            processMails(agent);
        } else {
            if (!agent.isArrived())
                reachDestination(agent);
        }
    }
}