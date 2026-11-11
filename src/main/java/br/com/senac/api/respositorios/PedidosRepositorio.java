package br.com.senac.api.respositorios;

import br.com.senac.api.entidades.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidosRepositorio extends JpaRepository<Pedidos, Long> {

}
