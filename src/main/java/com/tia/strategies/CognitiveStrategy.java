package com.tia.strategies;

import com.tia.algorithms.BFS;
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
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
    }

    private void processMoveRequest(Agent agent, Mail request) {
        Agent sender = request.getSender();
        if (!agent.isStuck()) {
            moveToBoxOutsideSenderPath(agent, sender);
//            moveToRandomDirection(agent);
//            // Envoi un mail OK à l'expéditeur
//            sendResponseOK(agent, sender);
//            // Supprime la requête
//            MailBox.deleteMail(request, agent);
        } else {
//            Agent neighbour = agent.getMinPriorityNeighbourAgentExceptOne(sender);
            Agent neighbour = agent.getRandomNeighbourExceptOne(sender);
//            Agent neighbour = sender;

//            if (MailBox.isEmpty(agent, Type.SENT))
//                Agent neighbour = agent.getRandomNeighbour();

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
//            moveToRandomDirection(agent);
//            sendResponseOK(agent, sender);

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
            // TODO

//            moveOrAskToMove(agent);

//            Agent minPriorityAgent = agent.getMinPriorityNeighbourAgentExceptOne(neighbour);
//            if (minPriorityAgent != null)
//                sendRequestMove(agent, minPriorityAgent);

            if (neighbour.getValue().getCode() < agent.getValue().getCode()) {
                sendRequestMove(agent, neighbour);
            }

            // L'agent qui veut se déplacer lui envoie requête "MOVE"
//            sendRequestMove(agent, neighbour);

//            sendRequestMove(agent, randomNeighbour);
        }
        // Si la voie est libre, il parcourt le plus court chemin vers sa destination
        else {
            SimpleStrategy strategy = new SimpleStrategy();
            strategy.solve(agent);
        }
    }

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

    // =======================================================================================
    // =======================================================================================
    // =======================================================================================
    // =======================================================================================
    // =======================================================================================
    // =======================================================================================
    // =======================================================================================

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

//        if (MailBox.getMails(receiver, Type.REQUEST).size() == 0) {
            MailBox.sendMailTo(mail, receiver);
            // Ajouter le message dans la queue de mails envoyés
            MailBox.addSent(sender, mail);
//        }
    }

    private boolean isBlockedByOtherAgent(Agent agent) {
        // Si la case vers laquelle il se dirige est occupée et interblocage
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
            // Se déplace vers cette case
            move(agent, nextDirection);
        }
        // Si cette case n'est pas libre
        else {
            // On récupère l'agent bloqueur
            // L'agent qui veut se déplacer lui envoie requête "MOVE"
//            if (MailBox.isEmpty(agent, Type.SENT))
            sendRequestMove(agent, neighbour);

//            checkPathsIntersection(agent, neighbour);
        }
    }

    private synchronized void moveToRandomDirection(Agent agent) {
        Direction randDirection = agent.getRandomDirection();
        if (randDirection != null)
            move(agent, randDirection);
    }

    private boolean noExit(Agent agent) {
        List<Box> path = BFS.findPath(agent, true);
        return path.isEmpty();
    }

    private Direction getNextDirection(Agent agent) {
        // Calcule le plus court chemin sans obstacle
        List<Box> path = BFS.findPath(agent, false);
        List<Direction> directions = BFS.convertPathToDirections(path);
        agent.setPathDirections(directions);
        return directions.get(0);
    }

    public void checkPathsIntersection(Agent a1, Agent a2) {
        List<Box> path1 = BFS.findPath(a1, true);
        List<Box> path2 = BFS.findPath(a2, true);


        List<Box> common1 = new ArrayList<Box>(path1);
        List<Box> common2 = new ArrayList<Box>(path2);



    }

    public void moveToBoxOutsideSenderPath(Agent agent, Agent sender) {
        // Get sender path
        List<Box> senderPath = BFS.findPath(sender, true);
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

            Direction direction = BFS.convertPathToDirections(pathToDirections).get(0);

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