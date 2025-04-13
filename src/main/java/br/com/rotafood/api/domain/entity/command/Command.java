package br.com.rotafood.api.domain.entity.command;


import java.util.List;
import java.util.UUID;

import br.com.rotafood.api.domain.entity.merchant.Merchant;
import br.com.rotafood.api.domain.entity.order.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
// Boa tarde! Me chamo Vinícius Costa, sou aqui de Limeira-SP, eu vi a postagem das vagas no isntagram e me inscrevi para a de Dev Web Junior! Queria ressaltar meu interesse em trabalhar na VR Software, já vi a logo de vocês em alguns lugares que eu frequento, e já ouvi falar muito bem da empresa. Enfim, já me candidatei no site de vocês, segue minhas redes sociais:





// Linkedin: https://www.linkedin.com/in/viniciuscostagandolfi/

// Github: https://github.com/ViniciusCostaGandolfi

// Site: https://rotafood.com.br e https://gradehorarios.com.br



// Tenho experiencia em Java Spring e Angular
@Entity
@Table(name = "commands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Command {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private Integer merchantSequence;

    @Column()
    private Integer tableIndex;

    @Column()
    private String name;

    @OneToMany(mappedBy = "command", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Enumerated(EnumType.STRING)
    private CommandStatus status;

    @ManyToOne(fetch = FetchType.LAZY)    
    @JoinColumn(name = "merchantId")
    private Merchant merchant;

    
}
