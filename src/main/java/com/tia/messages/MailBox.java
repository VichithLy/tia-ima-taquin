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
    public static Map<Agent, List<Mail>> sentMails = new HashMap<>();

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
        }
    }

    /**
     * @param agent
     * @return
     */
    public static PriorityQueue<Mail> getMails(Agent agent, Type type) {
        return mails.get(agent).get(type);
    }

    public static List<Mail> getSentMails(Agent agent) {
        return sentMails.get(agent);
    }

    public static Mail getSentMail(Agent agent, Mail mail) {
        Mail result = null;

        for (Mail m : sentMails.get(agent)) {
            if (m.equals(mail)) result = m;
        }

        return result;
    }

    /**
     * @param agent
     * @return
     */
    public static Mail getAndDeletePriorityMail(Agent agent, Type type) {
        return mails.get(agent).get(type).poll();
    }


    /**
     * @param agent
     * @return
     */
    public static Mail getPriorityMail(Agent agent, Type type) {
        return mails.get(agent).get(type).peek();
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
        return mails.get(agent).get(type).isEmpty();
    }
}
