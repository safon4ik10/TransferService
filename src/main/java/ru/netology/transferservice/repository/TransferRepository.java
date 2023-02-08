package ru.netology.transferservice.repository;

import ru.netology.transferservice.data.Card;
import ru.netology.transferservice.data.Operation;
import ru.netology.transferservice.data.Transfer;
import ru.netology.transferservice.data.TransferToAccept;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TransferRepository {
    private final Map<String, Card> userCardsMap = createTestCardsMap();
    private final Map<String, TransferToAccept> transfersToAccept = new HashMap<>();

    public synchronized void addTransferToAccept(String operationId, String operationCode, Transfer transfer) {
        checkCards(transfer);
        transfersToAccept.put(operationId, new TransferToAccept(transfer, operationCode));
    }

    public synchronized void confirmOperation(Operation operation) {
        String operationId = operation.getOperationId();
        TransferToAccept transferToAccept = transfersToAccept.get(operationId);
        checkOperation(operation, transferToAccept);

        Transfer transfer = transferToAccept.getTransfer();
        Integer amount = transfer.getAmount().getValue();
        updateBalances(transfer, amount);

        transfersToAccept.remove(operationId);
    }

    /*Вспомогательные методы*/

    private Map<String, Card> createTestCardsMap() {
        Map<String, Card> cardsMap = new HashMap<>();
        cardsMap.put("1111111111111111", new Card("1111111111111111", "11/23", "111", 100_000_000));
        cardsMap.put("2222222222222222", new Card("2222222222222222", "12/26", "222", 100_000));
        cardsMap.put("3333333333333333", new Card("3333333333333333", "01/31", "333", 999));

        return cardsMap;
    }

    private void checkCards(Transfer transfer) {
        Card cardFrom = userCardsMap.get(transfer.getCardFromNumber());
        if (cardFrom == null) {
            throw new RuntimeException("Write-off card not found");
        }
        compareCardFromData(cardFrom, transfer);

        if (!userCardsMap.containsKey(transfer.getCardToNumber())) {
            throw new RuntimeException("Recipient's card not found");
        }
    }

    private void checkOperation(Operation operation, TransferToAccept transferToAccept) {
        if (transferToAccept == null) {
            throw new RuntimeException("Operation not found");
        }
        if (!operation.getCode().equals(transferToAccept.getOperationCode())) {
            throw new RuntimeException("Incorrect operation code");
        }
        checkCards(transferToAccept.getTransfer());
    }

    private void compareCardFromData(Card cardFrom, Transfer transfer) {
        if (!cardFrom.getCardFromCVV().equals(transfer.getCardFromCVV()) ||
                !cardFrom.getCardFromValidTill().equals(transfer.getCardFromValidTill())) {
            throw new RuntimeException("Incorrect data");
        }
        if (cardFrom.getAccountBalance() < transfer.getAmount().getValue()) {
            throw new RuntimeException("Insufficient funds");
        }
    }

    private void updateBalances(Transfer transfer, Integer amount) {
        setBalance(transfer.getCardFromNumber(), amount * (-1));
        setBalance(transfer.getCardToNumber(), amount);
    }

    private void setBalance(String cardNumber, Integer amount) {
        final Card card = userCardsMap.get(cardNumber);
        card.setAccountBalance(card.getAccountBalance() + amount);
    }
}