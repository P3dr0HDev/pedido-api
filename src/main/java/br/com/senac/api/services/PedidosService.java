package br.com.senac.api.services;

import br.com.senac.api.dtos.PedidosItensRequestDto;
import br.com.senac.api.dtos.PedidosRequestDto;
import br.com.senac.api.entidades.Pedidos;
import br.com.senac.api.entidades.PedidosItens;
import br.com.senac.api.respositorios.PedidosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepositorio pedidosRepositorio;

    public List<Pedidos> listar() {
        return pedidosRepositorio.findAll();
    }

    public Pedidos criar(PedidosRequestDto pedido) {
        Pedidos pedidoSaida = this.pedidosRequestDtoToPedidos(pedido);

        return pedidosRepositorio.save(pedidoSaida);
    }

    public Pedidos atualizar(Long id, PedidosRequestDto pedido) {
        if(pedidosRepositorio.existsById(id)) {
            Pedidos pedidoSaida = this.pedidosRequestDtoToPedidos(pedido);
            pedidoSaida.setId(id);

            return pedidosRepositorio.save(pedidoSaida);
        }

        throw new RuntimeException("Pedido não encontrado!");
    }

    public void deletar(Long id) {
        if(pedidosRepositorio.existsById(id)) {
            pedidosRepositorio.deleteById(id);
            return;
        }

        throw new RuntimeException("Pedido não encontrado!");
    }

    public Pedidos listarPorId(Long id) {
        Optional<Pedidos> pedidoRetorno = pedidosRepositorio.findById(id);
        if(pedidoRetorno.isPresent()) {
            return pedidoRetorno.get();
        }

        throw new RuntimeException("Pedido não encontrado!");

    }

    private Pedidos pedidosRequestDtoToPedidos(PedidosRequestDto entrada) {
        Pedidos saida = new Pedidos();
        for (PedidosItensRequestDto itemDto : entrada.getItens()) {
            PedidosItens item = new PedidosItens();
            item.setQuantidade(itemDto.getQuantidade());
            item.setPedido(saida);
            saida.getItens().add(item);
        }

        saida.setNomeCliente(entrada.getNomeCliente());

        return saida;
    }
}
