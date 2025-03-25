package br.com.rotafood.api.application.service.command;

import br.com.rotafood.api.application.dto.command.FullCommandDto;
import br.com.rotafood.api.domain.entity.command.Command;
import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.repository.CommandRepository;
import br.com.rotafood.api.domain.repository.MerchantRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public List<Command> getAllByMerchantId(UUID merchantId) {
        return commandRepository.findAllByMerchantId(merchantId);
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
        command.setPending(commandDto.pending());
        command.setPrepaid(commandDto.prepaid());
        command.setName(commandDto.name());
        command.setTableIndex(commandDto.tableIndex());

        return commandRepository.save(command);
    }

    @Transactional
    public void deleteByIdAndMerchantId(UUID commandId, UUID merchantId) {
        Command command = commandRepository.findByIdAndMerchantId(commandId, merchantId)
                .orElseThrow(() -> new EntityNotFoundException("Command não encontrada."));
        commandRepository.delete(command);
    }
}
