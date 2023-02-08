package ru.netology.transferservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.transferservice.data.Operation;
import ru.netology.transferservice.data.Transfer;
import ru.netology.transferservice.data.response.ResponseSuccess;
import ru.netology.transferservice.service.TransferService;

import javax.validation.Valid;

@CrossOrigin
@RestController
public class TransferController {
    private final Logger logger = LoggerFactory.getLogger(TransferController.class);

    TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseSuccess transfer(@Valid @RequestBody Transfer transfer) {
        final String operationId = transferService.processTransfer(transfer);
        logger.info(transfer + "; OperationId: " + operationId);
        return new ResponseSuccess(operationId);
    }

    @PostMapping("/confirmOperation")
    public ResponseSuccess confirmOperation(@RequestBody Operation operation) {
        transferService.confirmOperation(operation);
        logger.info(operation.toString());
        return new ResponseSuccess("Success");
    }
}