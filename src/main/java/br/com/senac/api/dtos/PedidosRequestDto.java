package br.com.senac.api.dtos;

import java.util.ArrayList;
import java.util.List;

public class PedidosRequestDto {
    private String nomeCliente;
    private List<PedidosItensRequestDto> itens = new ArrayList<>();

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public List<PedidosItensRequestDto> getItens() {
        return itens;
    }

    public void setItens(List<PedidosItensRequestDto> itens) {
        this.itens = itens;
    }
}
