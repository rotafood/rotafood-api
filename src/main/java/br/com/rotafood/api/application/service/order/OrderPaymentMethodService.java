package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderPaymentMethodDto;
import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderPaymentMethodService {

    @Autowired
    private OrderPaymentMethodRepository orderPaymentMethodRepository;

    @Autowired
    private OrderPaymentRepository orderPaymentRepository;

    @Autowired
    private DigitalWalletInformationRepository walletRepository;

    @Autowired
    private CashInformationRepository cashRepository;

    @Autowired
    private CreditCardInformationRepository cardRepository;

    @Autowired
    private TransactionInformationRepository transactionRepository;

    @Transactional
    public OrderPaymentMethod createOrUpdate(OrderPaymentMethodDto dto, UUID paymentId) {
        OrderPayment payment = orderPaymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("OrderPayment not found."));

        OrderPaymentMethod method = dto.id() != null
                ? orderPaymentMethodRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."))
                : new OrderPaymentMethod();

        method.setDescription(dto.description());
        method.setMethod(dto.method());
        method.setValue(dto.value());
        method.setPayment(payment);

        if (dto.wallet() != null) {
            DigitalWalletInformation wallet = new DigitalWalletInformation();
            wallet.setWalletName(dto.wallet().walletName());
            wallet.setWalletId(dto.wallet().walletId());
            wallet = walletRepository.save(wallet);
            method.setWallet(wallet);
        }

        if (dto.cash() != null) {
            CashInformation cash = new CashInformation();
            cash.setChangeFor(dto.cash().changeFor());
            cash = cashRepository.save(cash);
            method.setCash(cash);
        }

        if (dto.card() != null) {
            CreditCardInformation card = new CreditCardInformation();
            card.setBrand(dto.card().brand());
            card = cardRepository.save(card);
            method.setCard(card);
        }

        if (dto.transaction() != null) {
            TransactionInformation transaction = new TransactionInformation();
            transaction.setAuthorizationCode(dto.transaction().authorizationCode());
            transaction.setAcquirerDocument(dto.transaction().acquirerDocument());
            transaction = transactionRepository.save(transaction);
            method.setTransaction(transaction);
        }

        return orderPaymentMethodRepository.save(method);
    }

    @Transactional
    public void deleteById(UUID methodId) {
        OrderPaymentMethod method = orderPaymentMethodRepository.findById(methodId)
                .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."));

        if (method.getWallet() != null) {
            walletRepository.delete(method.getWallet());
        }
        if (method.getCash() != null) {
            cashRepository.delete(method.getCash());
        }
        if (method.getCard() != null) {
            cardRepository.delete(method.getCard());
        }
        if (method.getTransaction() != null) {
            transactionRepository.delete(method.getTransaction());
        }

        orderPaymentMethodRepository.delete(method);
    }

    public OrderPaymentMethod getById(UUID methodId) {
        return orderPaymentMethodRepository.findById(methodId)
                .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."));
    }
}
