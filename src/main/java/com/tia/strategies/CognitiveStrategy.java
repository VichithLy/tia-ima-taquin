package com.tia.strategies;

import com.tia.algorithms.BFS;
import com.tia.enums.Content;
import com.tia.enums.Direction;
import com.tia.enums.Type;
import com.tia.messages.Mail;
import com.tia.messages.MailBox;
import com.tia.models.Agent;
import com.tia.models.Box;

import java.util.List;

public class CognitiveStrategy implements Strategy {
    @Override
    public void move(Agent agent, Direction direction) {
        NaiveStrategy strategy = new NaiveStrategy();
        strategy.move(agent, direction);
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

        // Ajouter le message dans la queue de mails envoyés
        MailBox.addSent(sender, mail);

//        if (sender.getValue().getText() == "A") {
//            System.out.println(sender.getValue().getText() + " sent a request to "
//                    + receiver.getValue().getText());
//
//            System.out.println("Sent mails=" + MailBox.getMails(sender, Type.SENT));
//        }

        /*if (sender.getValue().getText() == "A")
            System.out.println(MailBox.getMails(receiver, Type.REQUEST));*/
    }

    private void processReponses(Agent agent) {
        Mail response = MailBox.getPriorityMail(agent, Type.RESPONSE);

//        if (agent.getValue().getText() == "A") {
//            System.out.println("response=" + response);
//        }

        if (response != null) {
            Content content = response.getContent();
            Agent sender = response.getSender();

//            System.out.println(agent.getValue().getText() + " received a response from "
//                    + sender.getValue().getText());

            // Si c'est une réponse positive
            if (content.equals(Content.OK)) {
//                if (agent.getValue().getText() == "A") {
//                    System.out.println(agent.getValue().getText() + " received a OK response");
//                }

                // S'il n'est pas encore arrivé
                if (!agent.isArrived()) {
                    // Il continue d'aller vers sa destination
                    goToDestination(agent);
                }
                // S'il est déjà arrivé
                else {
                    // Il se déplace vers une case libre aléatoire
                    moveToRandomDirection(agent);
                    // sendResponseOK(agent, sender);
                }

                // Il "acquitte" sa requête envoyé en la supprimant
                // de ses mails envoyés
                MailBox.deletePriorityMail(agent, Type.SENT);
                // Il supprime la réponse
                MailBox.deletePriorityMail(agent, Type.RESPONSE);
            }
            // Si c'est une réponse négative
            if (content.equals(Content.NOK)) {
//                if (sender.getValue().getText() == "A") {
//                    System.out.println(sender.getValue().getText() + " received a NOK response");
//                }

                // Il supprime la réponse
                MailBox.deletePriorityMail(agent, Type.RESPONSE);
                // Il ne fait rien (attend que la voie se libère)
            }
        }
    }

    private void processRequests(Agent agent) {
        Mail request = MailBox.getPriorityMail(agent, Type.REQUEST);

        if (request != null) {
            Content content = request.getContent();
            Agent sender = request.getSender();

//            System.out.println(agent.getValue().getText() + " received a request from "
//                    + sender.getValue().getText());

            // Si on lui demande de bouger
            if (content.equals(Content.MOVE)) {
                // S'il n'est pas bloqué par d'autres agents
                if (!agent.isStuck()) {
                    if (agent.getValue().getText() == "A") {
                        System.out.println("A is not stuck");
                    }

                    // Il se déplace vers une case libre aléatoire
                    moveToRandomDirection(agent);
                    // Envoi un mail OK à l'expéditeur
                    sendResponseOK(agent, sender);
                    // Supprime la requête
                    MailBox.deleteMail(request, agent);
                }
                // S'il est bloqué
                else {
                    if (agent.getValue().getText() == "A") {
                        System.out.println("A is stuck");
                    }
                    // Envoie une requête "MOVE" à l'agent de plus petite priorité
                    // (il ne peut pas l'envoyé à l'expéditeur de la requête initiale)
                    Agent neighbour = agent.getMinPriorityNeighbourAgentExceptOne(sender);
//                    Agent neighbour = agent.getRandomNeighbour();
                    if (MailBox.isEmpty(agent, Type.SENT))
                        sendRequestMove(agent, neighbour);

                    // TODO

                    // Envoi un mail NOK à l'expéditeur
                    sendResponseNOK(agent, sender);
                }
            }

        }
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
        // Si la case vers laquelle il veut aller est libre
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
        }
    }

    private void goToDestination(Agent agent) {
        Direction nextDirection = getNextDirection(agent);
        Agent neighbour = agent.getNeighbour(nextDirection);

        // Si l'agent n'a aucune issue (4 autres agents bloquent le passage)
        if (noExit(agent)) {
            moveOrAskToMove(agent);
        } else if (isBlockedByOtherAgent(agent)) {
            System.out.println("======");
            System.out.println(agent.getValue().getText() + " - " + neighbour.getValue().getText());

            // TODO
//            moveOrAskToMove(agent);

//            Agent minPriorityAgent = agent.getMinPriorityNeighbourAgentExceptOne(neighbour);
//            if (minPriorityAgent != null)
//                sendRequestMove(agent, minPriorityAgent);


            // L'agent de plus grande priorité envoie une requête "MOVE"
            if (neighbour.getValue().getCode() < agent.getValue().getCode()) {
                sendRequestMove(agent, neighbour);

                System.out.println(agent.getValue().getText() + " sent a request to "
                        + neighbour.getValue().getText());
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
        // S'il a reçu une réponse
        if (!MailBox.isEmpty(agent, Type.RESPONSE)) {
            processReponses(agent);
        }
        // S'il a reçu une requête
        else if (!MailBox.isEmpty(agent, Type.REQUEST)) {
            processRequests(agent);
        }
        // S'il n'a rien reçu
        else {
            // S'il n'est pas encore arrivé à destination
            if (!agent.isArrived()) {
                // Il va vers sa destination
                goToDestination(agent);
            }
//            if (agent.getValue().getText() == "A")
//                System.out.println("Go to destination: " + agent.getValue().getText());
        }
    }
}