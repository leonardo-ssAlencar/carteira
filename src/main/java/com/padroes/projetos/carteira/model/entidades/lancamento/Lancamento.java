package com.padroes.projetos.carteira.model.entidades.lancamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.padroes.projetos.carteira.model.entidades.caixinha.Caixinha;
import com.padroes.projetos.carteira.model.entidades.grupo.Usuario;
import com.padroes.projetos.carteira.model.enums.OperacoesEnum;
import com.padroes.projetos.carteira.model.excecoes.OperacaoNaoPermitidaException;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("LANC")
public class Lancamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(length = 60)
    protected String mensagem;
    @Column(precision = 10, scale = 2)
    protected BigDecimal valor;
    @Enumerated(EnumType.ORDINAL)
    protected OperacoesEnum operacao;
    @Enumerated(EnumType.ORDINAL)
    private EstadoEnum estado;
    protected LocalDateTime dataHoraLancamento;
    @OneToOne(fetch = FetchType.LAZY)
    protected Usuario usuario;
    @ManyToOne
    private Caixinha caixinha;

    public Lancamento() {
    }

    public Lancamento(Usuario usuario, String mensagem, OperacoesEnum operacao,
            LocalDateTime dataHoraLancamento) {
        this.dataHoraLancamento = dataHoraLancamento;
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.operacao = operacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Operacao getOperacao() {

        if (this.operacao == OperacoesEnum.RECEITA) {
            return new Credito(valor);

        } else if (this.operacao == OperacoesEnum.DESPESA) {
            return new Debito(valor);
        }

        throw new OperacaoNaoPermitidaException("A operação não é possivel nesse tipo de lancamento");

    }

    public LocalDateTime getDataHoraLancamento() {
        return dataHoraLancamento;
    }

    public String dataHora() {
        String formatador = "dd/MM/yyyy - HH:mm:ss";

        return dataHoraLancamento.format(DateTimeFormatter.ofPattern(formatador));

    }

    public void setDataHoraLancamento(LocalDateTime dataHoraLancamento) {
        this.dataHoraLancamento = dataHoraLancamento;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Caixinha getCaixinha() {
        return caixinha;
    }

    public void setCaixinha(Caixinha caixinha) {
        this.caixinha = caixinha;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Lancamento [id=" + id + ", mensagem=" + mensagem + ", valor=" + valor + ", operacao=" + operacao
                + ", dataHoraLancamento=" + dataHoraLancamento + ", usuario=" + usuario.getNome() + ", caixinha="
                + caixinha.getId() + "status=" + estado + "]";
    }

}
