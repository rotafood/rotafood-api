package br.com.rotafood.api.order.application.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.rotafood.api.order.domain.repository.CommandRepository;
import br.com.rotafood.api.order.domain.entity.Command;
import br.com.rotafood.api.order.domain.entity.CommandStatus;

@Component
public class CommandStatusSchedulerService {

    @Autowired
    private CommandRepository commandRepository;


    @Scheduled(fixedDelay = 3_600_000)
    public void closeStaleCommands() {
        Instant cutoff = Instant.now().minus(8, ChronoUnit.HOURS);

        List<Command> stale = commandRepository
            .findByCreatedAtBeforeAndStatusNot(cutoff, CommandStatus.CLOSED);

        stale.forEach(cmd -> {
            cmd.setStatus(CommandStatus.CLOSED);
            commandRepository.save(cmd);
        });
    }
}
