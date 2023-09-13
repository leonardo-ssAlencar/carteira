package com.padroes.projetos.carteira.model.entidades.caixinha;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.padroes.projetos.carteira.model.entidades.Notificacoes;
import com.padroes.projetos.carteira.model.entidades.commands.LancamentoCommand;
import com.padroes.projetos.carteira.model.entidades.estrategiaEstorno.EstornoSimples;
import com.padroes.projetos.carteira.model.entidades.estrategiaEstorno.EstrategiaEstorno;
import com.padroes.projetos.carteira.model.entidades.estrategiaLancamento.LancamentoEstrategy;
import com.padroes.projetos.carteira.model.entidades.estrategiaLancamento.LancamentoSimples;
import com.padroes.projetos.carteira.model.entidades.estrategiaNotificacao.EstrategiaNotificacao;
import com.padroes.projetos.carteira.model.entidades.estrategiaNotificacao.NotificadorSimples;
import com.padroes.projetos.carteira.model.entidades.grupo.Grupo;
import com.padroes.projetos.carteira.model.entidades.lancamento.Lancamento;
import com.padroes.projetos.carteira.model.enums.estrategias.EstrategiaEstornoEnum;
import com.padroes.projetos.carteira.model.enums.estrategias.EstrategiaNotificacaoEnum;
import com.padroes.projetos.carteira.model.enums.estrategias.LancamentoEstrategyEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("SI")
public class Caixinha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    private LancamentoEstrategyEnum lancamentoEst;

    @Enumerated(EnumType.ORDINAL)
    private EstrategiaNotificacaoEnum notificador;

    @Enumerated(EnumType.ORDINAL)
    private EstrategiaEstornoEnum estornoEst;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal meta;
    private LocalDate fechamento;

    @OneToMany(mappedBy = "caixinha", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Lancamento> lancamentos = new ArrayList<>();

    @Transient
    private List<Notificacoes> ultimasNotificacoes = new ArrayList<>();

    private boolean mensal;

    @OneToOne(mappedBy = "caixinha", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Grupo grupo;

    protected Caixinha(EstrategiaNotificacaoEnum notificador, EstrategiaEstornoEnum estorno, BigDecimal valorTotal,
            BigDecimal meta, LocalDate fechamento, boolean mensal, LancamentoEstrategyEnum lancamentoEstrategy) {

        this.notificador = notificador;
        this.estornoEst = estorno;
        this.valorTotal = valorTotal;
        this.meta = meta;
        this.fechamento = fechamento;
        this.mensal = mensal;
        this.lancamentoEst = lancamentoEstrategy;
    }

    public Caixinha() {

    }

    public void executarLancamento(LancamentoCommand command) {
        Lancamento novoLancamento = getLancamentoEstrategy().executar(this, command);
        novoLancamento.setCaixinha(this);
        lancamentos.add(novoLancamento);
    }

    public void notificar(String mensagem) {
        ultimasNotificacoes.addAll(getNotificador().notificar(mensagem, this.grupo.getParticipantes()));

    }

    public void realizarEstorno() {
        getEstorno().calcularExtorno(this);

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public LancamentoEstrategy getLancamentoEstrategy() {

        switch (this.lancamentoEst) {
            case LANCAMENTO_SIMPLES:
                return new LancamentoSimples();

        }

        return new LancamentoSimples();

    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public EstrategiaNotificacao getNotificador() {
        // TODO switch para o enum
        return new NotificadorSimples();
    }

    public EstrategiaEstorno getEstorno() {
        // TODO Switch para o enum
        return new EstornoSimples();
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public BigDecimal getMeta() {
        return meta;
    }

    public LocalDate getFechamento() {
        return fechamento;
    }

    public boolean isMensal() {
        return mensal;
    }

    public List<Notificacoes> getUltimasNotificacoes() {
        List<Notificacoes> notificacoes = new ArrayList<>();
        ultimasNotificacoes.forEach(x -> notificacoes.add(x));

        ultimasNotificacoes.clear();

        return notificacoes;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public void setLancamentoEst(LancamentoEstrategyEnum lancamentoEst) {
        this.lancamentoEst = lancamentoEst;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }

    public void setNotificador(EstrategiaNotificacaoEnum notificador) {
        this.notificador = notificador;
    }

    public void setEstornoEst(EstrategiaEstornoEnum estornoEst) {
        this.estornoEst = estornoEst;
    }

    public void setMeta(BigDecimal meta) {
        this.meta = meta;
    }

    public void setFechamento(LocalDate fechamento) {
        this.fechamento = fechamento;
    }

    public void setMensal(boolean mensal) {
        this.mensal = mensal;
    }

    public String dataDeFechamento() {
        return fechamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

    }
}
