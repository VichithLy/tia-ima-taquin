package com.tia.messages;

import com.tia.enums.Type;
import com.tia.models.Agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Resources:
 * - PriorityQueue: https://www.geeksforgeeks.org/priority-queue-class-in-java/
 * - Comparator: https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/
 */
public final class MailBox {
    public static Map<Agent, Map<Type, PriorityQueue<Mail>>> mails = new HashMap<>();
    public static Map<Agent, PriorityQueue<Mail>> sent = new HashMap<>();

    /**
     * Initialize the mailbox of each agent.
     * @param agents
     */
    public static void init(List<Agent> agents) {
        for (Agent agent : agents) {
            Map<Type, PriorityQueue<Mail>> subjects = new HashMap<>();
            subjects.put(Type.REQUEST, new PriorityQueue<>(new MailComparator()));
            subjects.put(Type.RESPONSE, new PriorityQueue<>(new MailComparator()));
            subjects.put(Type.SENT, new PriorityQueue<>(new MailComparator()));

            mails.put(agent, subjects);
            sent.put(agent, new PriorityQueue<>(new MailComparator()));
        }
    }

    /**
     * Get mails of an agent, by type.
     * @param agent
     * @return a priority queue of mails
     */
    public static PriorityQueue<Mail> getMails(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent);
        return mails.get(agent).get(type);
    }

    /**
     * Get a mail by agent and type.
     * @param agent
     * @return a mail
     */
    public static Mail getAndDeletePriorityMail(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent).poll();
        return mails.get(agent).get(type).poll();
    }

    /**
     * Get the priority mail by agent and type.
     * @param agent
     * @return a mail
     */
    public static Mail getPriorityMail(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent).peek();
        return mails.get(agent).get(type).peek();
    }

    /**
     * Add a mail of an agent to sent mailbox.
     * @param agent
     * @param mail
     */
    public static void addSent(Agent agent, Mail mail) {
        sent.get(agent).add(mail);
    }

    /**
     * Delete a request, response or sent mail of an agent.
     * @param mail
     * @param agent
     */
    public static void deleteMail(Mail mail, Agent agent) {
        if (!isEmpty(agent, Type.RESPONSE) &&mail.getType().equals(Type.RESPONSE))
            mails.get(agent).get(Type.RESPONSE).remove(mail);

        if (!isEmpty(agent, Type.REQUEST) && mail.getType().equals(Type.REQUEST))
            mails.get(agent).get(Type.REQUEST).remove(mail);

        if (!isEmpty(agent, Type.SENT) && mail.getType().equals(Type.SENT))
            sent.get(agent).remove(mail);
    }

    /**
     * Delete the request, response or sent priority mail of an agent.
     * @param agent
     * @param type
     */
    public static void deletePriorityMail(Agent agent, Type type) {
        if (!isEmpty(agent, Type.SENT) && type.equals(Type.SENT)) sent.get(agent).poll();

        if (!isEmpty(agent, Type.RESPONSE) && type.equals(Type.RESPONSE))
            mails.get(agent).get(Type.RESPONSE).poll();

        if (!isEmpty(agent, Type.REQUEST) && type.equals(Type.REQUEST))
            mails.get(agent).get(Type.REQUEST).poll();
    }

    /**
     * Send mail from agent to another agent.
     * @param mail
     * @param agent
     */
    public static void sendMailTo(Mail mail, Agent agent) {
        if (mail.getType().equals(Type.RESPONSE)) {
            mails.get(agent).get(Type.RESPONSE).add(mail);
        }

        if (mail.getType().equals(Type.REQUEST)) {
            mails.get(agent).get(Type.REQUEST).add(mail);
        }
    }

    /**
     * Check if requests or responses or sent mailbox is empty.
     * @param agent
     * @return true if mailbox is empty, else false
     */
    public static boolean isEmpty(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent).isEmpty();
        return mails.get(agent).get(type).isEmpty();
    }
}
