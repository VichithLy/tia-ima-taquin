package com.tia.messages;

import com.tia.enums.Type;
import com.tia.models.Agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * PriorityQueue: https://www.geeksforgeeks.org/priority-queue-class-in-java/
 * Comparator: https://www.geeksforgeeks.org/implement-priorityqueue-comparator-java/
 */
public final class MailBox {
    public static Map<Agent, Map<Type, PriorityQueue<Mail>>> mails = new HashMap<>();
    public static Map<Agent, PriorityQueue<Mail>> sent = new HashMap<>();

    /**
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
     * @param agent
     * @return
     */
    public static PriorityQueue<Mail> getMails(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent);
        return mails.get(agent).get(type);
    }

    /**
     * @param agent
     * @return
     */
    public static Mail getAndDeletePriorityMail(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent).poll();
        return mails.get(agent).get(type).poll();
    }

    /**
     * @param agent
     * @return
     */
    public static Mail getPriorityMail(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent).peek();
        return mails.get(agent).get(type).peek();
    }

    public static void addSent(Agent agent, Mail mail) {
        sent.get(agent).add(mail);
    }

    /**
     * @param mail
     * @param agent
     */
    public static void deleteMail(Mail mail, Agent agent) {
        if (mail.getType().equals(Type.RESPONSE))
            mails.get(agent).get(Type.RESPONSE).remove(mail);

        if (mail.getType().equals(Type.REQUEST))
            mails.get(agent).get(Type.REQUEST).remove(mail);

        if (mail.getType().equals(Type.SENT))
            sent.get(agent).remove(mail);
    }

    /**
     * @param agent
     * @param type
     */
    public static void deletePriorityMail(Agent agent, Type type) {
        if (type.equals(Type.SENT)) sent.get(agent).poll();

        if (type.equals(Type.RESPONSE))
            mails.get(agent).get(Type.RESPONSE).poll();

        if (type.equals(Type.REQUEST))
            mails.get(agent).get(Type.REQUEST).poll();
    }

    /**
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
     * @param agent
     * @return
     */
    public static boolean isEmpty(Agent agent, Type type) {
        if (type.equals(Type.SENT)) return sent.get(agent).isEmpty();
        return mails.get(agent).get(type).isEmpty();
    }
}
