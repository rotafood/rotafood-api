package br.com.rotafood.api.application.service.order;

import br.com.rotafood.api.application.dto.order.OrderCashInformationDto;
import br.com.rotafood.api.application.dto.order.OrderCreditCardInformationDto;
import br.com.rotafood.api.application.dto.order.OrderDigitalWalletInformationDto;
import br.com.rotafood.api.application.dto.order.OrderPaymentMethodDto;
import br.com.rotafood.api.application.dto.order.OrderTransactionInformationDto;
import br.com.rotafood.api.domain.entity.order.*;
import br.com.rotafood.api.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPaymentMethodService {

    @Autowired
    private OrderPaymentMethodRepository orderPaymentMethodRepository;

    @Autowired
    private OrderDigitalWalletInformationRepository walletRepository;

    @Autowired
    private OrderCashInformationRepository cashRepository;

    @Autowired
    private OrderCreditCardInformationRepository cardRepository;

    @Autowired
    private OrderTransactionInformationRepository transactionRepository;

    @Transactional
    public OrderPaymentMethod createOrUpdate(OrderPaymentMethodDto dto) {

        OrderPaymentMethod method = dto.id() != null
                ? orderPaymentMethodRepository.findById(dto.id())
                        .orElseThrow(() -> new EntityNotFoundException("OrderPaymentMethod not found."))
                : new OrderPaymentMethod();

        method.setDescription(dto.description());
        method.setMethod(dto.method());
        method.setPrepaid(dto.prepaid());
        method.setCurrency(dto.currency());
        method.setType(dto.type());
        method.setValue(dto.value());
        

        method.setCurrency(dto.currency());
        method.setWallet(this.createOrUpdateWallet(dto.wallet()));
        method.setCash(this.createOrUpdateCash(dto.cash()));
        method.setCard(this.createOrUpdateCard(dto.card()));
        method.setTransaction(this.createOrUpdateTransaction(dto.transaction()));

        return orderPaymentMethodRepository.save(method);
    }

    private OrderDigitalWalletInformation createOrUpdateWallet(OrderDigitalWalletInformationDto walletDto) {
        if (walletDto == null) return null;

        OrderDigitalWalletInformation wallet = walletDto.id() != null
                ? walletRepository.findById(walletDto.id()).orElse(new OrderDigitalWalletInformation())
                : new OrderDigitalWalletInformation();

        wallet.setWalletName(walletDto.walletName());
        wallet.setWalletId(walletDto.walletId());

        return walletRepository.save(wallet);
    }

    private OrderCashInformation createOrUpdateCash(OrderCashInformationDto cashDto) {
        if (cashDto == null) return null;

        OrderCashInformation cash = cashDto.id() != null
                ? cashRepository.findById(cashDto.id()).orElse(new OrderCashInformation())
                : new OrderCashInformation();

        cash.setChangeFor(cashDto.changeFor());
        cash.setDescription(cashDto.description());

        return cashRepository.save(cash);
    }

    private OrderCreditCardInformation createOrUpdateCard(OrderCreditCardInformationDto cardDto) {
        if (cardDto == null) return null;

        OrderCreditCardInformation card = cardDto.id() != null
                ? cardRepository.findById(cardDto.id()).orElse(new OrderCreditCardInformation())
                : new OrderCreditCardInformation();

        card.setBrand(cardDto.brand());

        return cardRepository.save(card);
    }

    private OrderTransactionInformation createOrUpdateTransaction(OrderTransactionInformationDto transactionDto) {
        if (transactionDto == null) return null;

        OrderTransactionInformation transaction = transactionDto.id() != null
                ? transactionRepository.findById(transactionDto.id()).orElse(new OrderTransactionInformation())
                : new OrderTransactionInformation();

        transaction.setAuthorizationCode(transactionDto.authorizationCode());
        transaction.setAcquirerDocument(transactionDto.acquirerDocument());

        return transactionRepository.save(transaction);
    }
}
