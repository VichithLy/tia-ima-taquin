package com.tia.messages;

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
    public static Map<Agent, PriorityQueue> mails = new HashMap<>();

    public static void init(List<Agent> agents) {
        for (Agent agent : agents) {
            mails.put(agent, new PriorityQueue<Mail>(new MailComparator()));
        }
    }

    public static PriorityQueue<Mail> getAgentMails(Agent agent) {
        return mails.get(agent);
    }

    public static Mail getAndDeleteAgentPriorityMail(Agent agent) {
        return (Mail) mails.get(agent).poll();
    }

    public static Mail getAgentPriorityMail(Agent agent) {
        return (Mail) mails.get(agent).peek();
    }

    public static void deleteAgentMail(Mail mail, Agent agent) {
        mails.get(agent).remove(mail);
    }

    public static void sendMailTo(Mail mail, Agent agent) {
        mails.get(agent).add(mail);
    }


}
