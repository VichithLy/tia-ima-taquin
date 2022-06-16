package com.tia.messages;

import com.tia.enums.Content;
import com.tia.enums.Subject;
import com.tia.models.Agent;

public class Mail {
    private Agent sender;
    private Agent receiver;
    private Subject subject;
    private Content content;
    private int priority;

    public Mail(Agent sender, Agent receiver, Subject subject, Content content) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.content = content;
        this.priority = sender.getValue().getCode();
    }

    public Agent getSender() {
        return sender;
    }

    public Agent getReceiver() {
        return receiver;
    }

    public Subject getSubject() {
        return subject;
    }

    public Content getContent() {
        return content;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", subject=" + subject +
                ", content=" + content +
                ", priority=" + priority +
                '}';
    }
}
