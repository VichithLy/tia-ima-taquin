package com.tia.messages;

import java.util.Comparator;

public class MailComparator implements Comparator<Mail> {
    @Override
    public int compare(Mail m1, Mail m2) {
        if (m1.getPriority() < m2.getPriority())
            return 1;
        else if (m1.getPriority() > m2.getPriority())
            return -1;
        return 0;
    }
}
