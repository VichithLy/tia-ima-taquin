package com.tia.messages;

import com.tia.enums.Content;
import com.tia.enums.Status;
import com.tia.enums.Type;
import com.tia.models.Agent;

public class Mail {
    private Agent sender;
    private Agent receiver;
    private Type type;
    private Content content;
    private Status status;
    private int priority;

    public Mail(Agent sender, Agent receiver, Type type, Content content) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
        this.content = content;
        this.priority = sender.getValue().getCode();
    }

    public Agent getSender() {
        return sender;
    }

    public Agent getReceiver() {
        return receiver;
    }

    public Type getType() {
        return type;
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
                "sender=" + sender + "\n" +
                ", receiver=" + receiver + "\n" +
                ", type=" + type + "\n" +
                ", content=" + content + "\n" +
                ", priority=" + priority + "\n" +
                '}';
    }
}
