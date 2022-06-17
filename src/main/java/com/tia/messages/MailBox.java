package com.tia.messages;

import com.tia.enums.Subject;
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
    public static Map<Agent, Map<Subject, PriorityQueue<Mail>>> mails = new HashMap<>();

    /**
     * @param agents
     */
    public static void init(List<Agent> agents) {
        for (Agent agent : agents) {
            Map<Subject, PriorityQueue<Mail>> subjects = new HashMap<>();
            subjects.put(Subject.REQUEST, new PriorityQueue<>(new MailComparator()));
            subjects.put(Subject.RESPONSE, new PriorityQueue<>(new MailComparator()));

            mails.put(agent, subjects);
        }
    }

    /**
     * @param agent
     * @return
     */
    public static PriorityQueue<Mail> getMails(Agent agent, Subject subject) {
        return mails.get(agent).get(subject);
    }

    /**
     * @param agent
     * @return
     */
    public static Mail getAndDeletePriorityMail(Agent agent, Subject subject) {
        return mails.get(agent).get(subject).poll();
    }


    /**
     * @param agent
     * @return
     */
    public static Mail getPriorityMail(Agent agent, Subject subject) {
        return mails.get(agent).get(subject).peek();
    }

    /**
     * @param mail
     * @param agent
     */
    public static void deleteMail(Mail mail, Agent agent) {
        if (mail.equals(Subject.RESPONSE))
            mails.get(agent).get(Subject.RESPONSE).remove(mail);

        if (mail.equals(Subject.REQUEST))
            mails.get(agent).get(Subject.REQUEST).remove(mail);
    }

    /**
     * @param mail
     * @param agent
     */
    public static void sendMailTo(Mail mail, Agent agent) {
        if (mail.getSubject().equals(Subject.RESPONSE))
            mails.get(agent).get(Subject.RESPONSE).add(mail);

        if (mail.getSubject().equals(Subject.REQUEST))
            mails.get(agent).get(Subject.REQUEST).add(mail);
    }

    /**
     * @param agent
     * @return
     */
    public static boolean isEmpty(Agent agent, Subject subject) {
        return mails.get(agent).get(subject).isEmpty();
    }
}
