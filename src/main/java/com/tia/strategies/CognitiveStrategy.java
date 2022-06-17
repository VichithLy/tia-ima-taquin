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

    private void moveToRandomDirection(Agent agent) {
        Direction randDirection = agent.getRandomDirection();
        move(agent, randDirection);
    }

    private void processMails(Agent agent) {
        Mail request = MailBox.getPriorityMail(agent, Type.REQUEST);
        Mail response = MailBox.getPriorityMail(agent, Type.RESPONSE);

        // L'agent traite d'abord ses réponses
        if (response != null) {
            Content content = response.getContent();
            Agent sender = response.getSender();

            System.out.println(agent.getValue().getText() + " received a response from "
                    + sender.getValue().getText());
        }

        // Ensuite ses requêtes
        if (request != null) {
            Content content = request.getContent();
            Agent sender = request.getSender();

            System.out.println(agent.getValue().getText() + " received a request from "
                    + sender.getValue().getText());

            // Si on lui demande de bouger
            if (content.equals(Content.MOVE)) {

                // S'il n'est pas bloqué par d'autres agents
                if (!agent.isStuck()) {
                    // Il se déplace vers une case libre aléatoire
                    moveToRandomDirection(agent);

//                    MailBox.deleteMail(request, agent);

                    // TO FIX
                    // Once Mail is processed, delete it
                    // MailBox.deleteAgentMail(mail, agent);
                }
                // S'il est bloqué
                else {
                    // Envoie requête "MOVE" à l'agent de plus petite priorité
                    // (il ne peut pas l'envoyé à l'expéditeur de la requête initiale)
                    Agent neighbour = agent.getMinPriorityNeighbourAgentExceptOne(sender);
                    sendRequestMove(agent, neighbour);

//                    if (!agent.hasSendAMessage()) {
//                        // Send message to min priority Agent
//                        A
//                        sendAMailWithMoveContentToNeighbour(agent, neighbour);
//                    }
                }
            }

        }
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
    private void sendRequestMove(Agent sender, Agent receiver) {
        Mail mail = new Mail(sender, receiver, Type.REQUEST, Content.MOVE);
        MailBox.sendMailTo(mail, receiver);

//        sender.sentAMessage();

        System.out.println(sender.getValue().getText() + " sent a request to "
                + receiver.getValue().getText());
    }

    private void goToDestination(Agent agent) {
        // Si l'agent n'a aucune issue (4 autres agents bloquent le passage)
        if (noExit(agent)) {
            // Si la case vers laquelle il veut aller est libre
            Direction nextDirection = getNextDirection(agent);
            if (agent.getNeighbour(nextDirection) == null) {
                // Se déplace vers cette case
                move(agent, nextDirection);
                // agent.canSendAnotherMessage();
            }
            // Si cette case n'est pas libre
            else {
                // On récupère l'agent bloqueur
                Agent neighbour = agent.getNeighbour(nextDirection);

                // L'agent qui veut se déplacer lui envoie requête "MOVE"
                sendRequestMove(agent, neighbour);

//                if (!agent.hasSendAMessage()) {
//                    sendAMailWithMoveContentToNeighbour(agent, neighbour);
//                }
            }

        } else {
            // BFS with obstacle avoidance
            SimpleStrategy strategy = new SimpleStrategy();
            strategy.solve(agent);
        }
    }

    @Override
    public void solve(Agent agent) {
        // Si l'agent a reçu un mail

        if (!MailBox.isEmpty(agent, Type.REQUEST)
                || !MailBox.isEmpty(agent, Type.RESPONSE))  {
            processMails(agent);

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