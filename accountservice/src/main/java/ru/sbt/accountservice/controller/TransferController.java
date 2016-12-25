package ru.sbt.accountservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import ru.sbt.accountservice.service.TransferService;
import ru.sbt.accountservice.utils.JmsSender;
import ru.sbt.core.accountservice.Transfers;

public class TransferController {

    @Autowired
    JmsSender jmsSender;

    @Autowired
    TransferService transferService;

    private final String QUEUE = "account-in";
    private final String CORRECTION = "Operation = 'CORRECTION'";
    private final String CORRECTION_CANCEL = "Operation = 'CORRECTION'";

    @JmsListener(destination = QUEUE, selector = CORRECTION)
    public void correction(Transfers transfers) {
        try {
            transferService.makeCorrection(transfers.getFromAccount(), transfers.getToAccount(), transfers.getAmount());
            jmsSender.sendMessage(CORRECTION, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CORRECTION, ex.getMessage());
        }
    }

    @JmsListener(destination = QUEUE, selector = CORRECTION_CANCEL)
    public void cancelCorrection(Long id) {
        try {
            transferService.cancelCorrection(id);
            jmsSender.sendMessage(CORRECTION_CANCEL, "OK");
        } catch (Exception ex) {
            jmsSender.sendMessage(CORRECTION_CANCEL, ex.getMessage());
        }
    }
}
