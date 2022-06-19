package com.tia.strategies;

import com.tia.algorithms.Path;
import com.tia.enums.Content;
import com.tia.enums.Direction;
import com.tia.enums.Type;
import com.tia.messages.Mail;
import com.tia.messages.MailBox;
import com.tia.models.Agent;
import com.tia.models.Box;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CognitiveStrategy implements Strategy {
    /**
     * Agent basic movement (NORTH, SOUTH, EAST, WEST).
     *
     * @param agent
     * @param direction
     */
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
    }

    /**
     * Apply strategy: compute shortest path to destination and send mails to reach destination.
     *
     * @param agent
     */
    @Override
    public void solve(Agent agent) {
        if (!MailBox.isEmpty(agent, Type.RESPONSE)) {
            processReponse(agent);
        } else if (!MailBox.isEmpty(agent, Type.REQUEST)) {
            processRequest(agent);
        } else {
            if (!agent.isArrived()) {
                goToDestination(agent);
            }
        }
    }

    private void processMoveRequest(Agent agent, Mail request) {
        Agent sender = request.getSender();
        if (!agent.isStuck()) {
            moveToBoxOutsideSenderPath(agent, sender);
        } else {
//            Agent neighbour = agent.getMinPriorityNeighbourAgentExceptOne(sender);
//            Agent neighbour = agent.getRandomNeighbourExceptOne(sender);
            Agent neighbour = agent.getRandomNeighbour();

            if (neighbour != null) {
                sendRequestMove(agent, neighbour);
            }

            sendResponseNOK(agent, sender);
        }
    }

    private void processRequest(Agent agent) {
        Mail request = MailBox.getAndDeletePriorityMail(agent, Type.REQUEST);

        Content content = request.getContent();
        Agent sender = request.getSender();

        if (content.equals(Content.MOVE)) {
            processMoveRequest(agent, request);
        } else {
            sendResponseOK(agent, sender);
        }
    }

    private void processOKResponse(Agent agent, Mail response) {
        Agent sender = response.getSender();

        if (!agent.isArrived()) {
            goToDestination(agent);
        } else {
            moveToBoxOutsideSenderPath(agent, sender);
        }
    }

    private void processNOKResponse(Agent agent, Mail response) {
        // WAIT
    }

    private void processReponse(Agent agent) {
        Mail response = MailBox.getAndDeletePriorityMail(agent, Type.RESPONSE);
        Content content = response.getContent();

        if (content.equals(Content.OK)) {
            processOKResponse(agent, response);
        }

        if (content.equals(Content.NOK)) {
            processNOKResponse(agent, response);
        }

        MailBox.deletePriorityMail(agent, Type.SENT);
    }

    private void goToDestination(Agent agent) {
        Direction nextDirection = getNextDirection(agent);
        Agent neighbour = agent.getNeighbour(nextDirection);

        if (noExit(agent)) {
            moveOrAskToMove(agent);
        } else if (isBlockedByOtherAgent(agent)) {
            if (neighbour != null) {
                if (neighbour.getPriority() < agent.getPriority()) {
                    sendRequestMove(agent, neighbour);
                }
            }
        } else {
            SimpleStrategy strategy = new SimpleStrategy();
            strategy.solve(agent);
        }
    }

    private void sendResponseNOK(Agent sender, Agent receiver) {
        Mail mail = new Mail(sender, receiver, Type.RESPONSE, Content.NOK);
        MailBox.sendMailTo(mail, receiver);
    }

    private void sendResponseOK(Agent sender, Agent receiver) {
        Mail mail = new Mail(sender, receiver, Type.RESPONSE, Content.OK);
        MailBox.sendMailTo(mail, receiver);
    }

    private void sendRequestMove(Agent sender, Agent receiver) {
        Mail mail = new Mail(sender, receiver, Type.REQUEST, Content.MOVE);
        MailBox.sendMailTo(mail, receiver);
        MailBox.addSent(sender, mail);
    }

    private boolean isBlockedByOtherAgent(Agent agent) {
        Direction nextDirection = getNextDirection(agent);
        Agent neighbour = agent.getNeighbour(nextDirection);

        if (neighbour != null)
            return neighbour.getDestination().equals(agent.getCurrent())
                    && agent.getDestination().equals(neighbour.getCurrent());

        return false;
    }

    private void moveOrAskToMove(Agent agent) {
        Direction nextDirection = getNextDirection(agent);
        Agent neighbour = agent.getNeighbour(nextDirection);

        if (neighbour == null) {
            move(agent, nextDirection);
        } else {
            sendRequestMove(agent, neighbour);
        }
    }

    private synchronized void moveToRandomDirection(Agent agent) {
        Direction randDirection = agent.getRandomFreeDirection();
        if (randDirection != null)
            move(agent, randDirection);
    }

    private boolean noExit(Agent agent) {
        List<Box> path = Path.find(agent, true);
        return path.isEmpty();
    }

    private Direction getNextDirection(Agent agent) {
        List<Box> path = Path.find(agent, false);
        List<Direction> directions = Path.convertToDirections(path);
        agent.setPathDirections(directions);
        return directions.get(0);
    }

    public void moveToBoxOutsideSenderPath(Agent agent, Agent sender) {
        // Get sender path
        List<Box> senderPath = Path.find(sender, true);
        // Go to box that is not in sender path
        List<Box> freeBoxesNeighbours = agent.getBoxNeighbours();
        List<Box> remainingBoxes = freeBoxesNeighbours;
        remainingBoxes.removeAll(senderPath);
        // Go to random remaining box
        Random rand = new Random();

        if (!remainingBoxes.isEmpty()) {
            Box randBox = remainingBoxes.get(rand.nextInt(remainingBoxes.size()));

            List<Box> pathToDirections = new ArrayList<>();
            pathToDirections.add(agent.getCurrent());
            pathToDirections.add(randBox);

            Direction direction = Path.convertToDirections(pathToDirections).get(0);

            if (randBox.getAgent() == null) {
                if (direction != null) {
                    move(agent, direction);
                    sendResponseOK(agent, sender);
                } else {
                    sendResponseNOK(agent, sender);
                }
            } else {
                sendRequestMove(agent, sender);
                sendResponseNOK(agent, sender);
            }
        } else {
            sendResponseNOK(agent, sender);
        }
    }
}