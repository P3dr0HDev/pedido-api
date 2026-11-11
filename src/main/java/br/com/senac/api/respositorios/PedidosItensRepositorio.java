package br.com.senac.api.respositorios;

import br.com.senac.api.entidades.PedidosItens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosItensRepositorio extends JpaRepository<PedidosItens, Long> {
}
