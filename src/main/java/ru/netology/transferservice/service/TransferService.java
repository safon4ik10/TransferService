package ru.netology.transferservice.service;


import org.springframework.stereotype.Service;
import ru.netology.transferservice.data.Operation;
import ru.netology.transferservice.data.Transfer;
import ru.netology.transferservice.repository.TransferRepository;

import java.time.LocalDate;
import java.util.Random;

@Service
public class TransferService {
    TransferRepository transferRepository;

    private final Random random = new Random();

    public TransferService(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    public String processTransfer(Transfer transfer) {
        validateCardDate(transfer.getCardFromValidTill());
        String operationId = String.valueOf(random.nextInt());
        String operationCode = String.valueOf(random.nextInt());

        transferRepository.addTransferToAccept(operationId, operationCode, transfer);
        return operationId;
    }

    private void validateCardDate(String cardFromValidTill) {
        LocalDate now = LocalDate.now();
        validateCardDate(cardFromValidTill, now.getMonthValue(), now.getYear());
    }

    void validateCardDate(String cardFromValidTill, int currentMonth, int currentYear) {
        String[] cardYearAndMonth = cardFromValidTill.split("/");
        int cardMonth = Integer.parseInt(cardYearAndMonth[0]);
        int cardYear = LocalDate.now().getYear() + Integer.parseInt(cardYearAndMonth[1]);

        if (currentYear > cardYear || currentYear == cardYear && currentMonth > cardMonth) {
            throw new RuntimeException("Card expired");
        }
    }

    public void confirmOperation(Operation operation) {
        transferRepository.confirmOperation(operation);
    }
}