package br.com.rotafood.api.application.service.command;

import br.com.rotafood.api.application.dto.command.FullCommandDto;
import br.com.rotafood.api.application.dto.order.FullOrderDto;
import br.com.rotafood.api.application.service.order.OrderService;
import br.com.rotafood.api.domain.entity.command.Command;
import br.com.rotafood.api.domain.entity.command.CommandStatus;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.order.OrderStatus;
import br.com.rotafood.api.domain.repository.CommandRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommandService {

    @Autowired
    private CommandRepository commandRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private OrderService orderService;


    public List<Command> getAllByMerchantId(UUID merchantId) {
        return commandRepository.findAllByMerchantIdAndStatus(merchantId, CommandStatus.OPENED);
    }

    public Command getByIdAndMerchantId(UUID commandId, UUID merchantId) {
        return commandRepository.findByIdAndMerchantId(commandId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Command não encontrada."));
    }

    @Transactional
    public Command createOrUpdate(FullCommandDto commandDto, UUID merchantId) {
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Merchant não encontrado."));

        Command command = commandDto.id() != null
                ? commandRepository.findByIdAndMerchantId(commandDto.id(), merchantId)
                    .orElseThrow(() -> new EntityNotFoundException("Command não encontrada."))
                : new Command();

        if (command.getId() == null) {
            Long maxSequence = commandRepository.findMaxMerchantSequenceByMerchantId(merchantId);
            Long nextSequence = (maxSequence == null) ? 1L : maxSequence + 1L;
            command.setMerchant(merchant);
            command.setMerchantSequence(nextSequence.intValue());
        }
        command.setStatus(commandDto.status());
        command.setName(commandDto.name());
        command.setTableIndex(commandDto.tableIndex());

        return commandRepository.save(command);
    }

    @Transactional
    public Command closeCommand(FullCommandDto commandDto, UUID merchantId) {

        Command command = commandRepository.findByIdAndMerchantId(commandDto.id(), merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Command não encontrada."));

        this.validCommandOrderToClose(commandDto.orders());

        command.setStatus(CommandStatus.CLOSED);

        return commandRepository.save(command);
    }

    private void validCommandOrderToClose(List<FullOrderDto> orderDtos) {
        if (orderDtos != null) {
            for (FullOrderDto orderDto : orderDtos) {
                boolean allMethodsPaid = orderDto.payment() != null &&
                    orderDto.payment().methods() != null &&
                    !orderDto.payment().methods().isEmpty() &&
                    orderDto.payment().methods().stream().allMatch(m -> m.paid() == Boolean.TRUE);
                if (!allMethodsPaid) {
                    throw new ValidationException("Dados de pagamento não formatados");
                }
                if (allMethodsPaid) {
                    FullOrderDto updated = new FullOrderDto(
                                orderDto.id(),
                                orderDto.merchantSequence(),
                                orderDto.modifiedAt(),
                                orderDto.createdAt(),
                                orderDto.preparationStartDateTime(),
                                orderDto.type(),
                                OrderStatus.COMPLETED,
                                orderDto.salesChannel(),
                                orderDto.timing(),
                                orderDto.extraInfo(),
                                orderDto.merchantId(),
                                orderDto.total(),
                                orderDto.customer(),
                                orderDto.delivery(),
                                orderDto.schedule(),
                                orderDto.takeout(),
                                orderDto.command(),
                                orderDto.payment(),
                                orderDto.items(),
                                orderDto.benefits(),
                                orderDto.additionalFees()
                            );


                    orderService.createOrUpdate(updated, orderDto.merchantId());
                }
            }
        }
    } 


    @Transactional
    public void deleteByIdAndMerchantId(UUID commandId, UUID merchantId) {
        Command command = commandRepository.findByIdAndMerchantId(commandId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Command não encontrada."));
        commandRepository.delete(command);
    }
}
