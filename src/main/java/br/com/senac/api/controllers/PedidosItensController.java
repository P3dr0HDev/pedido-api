package br.com.senac.api.controllers;

import br.com.senac.api.dtos.PedidosItensRequestDto;
import br.com.senac.api.services.PedidosItensService;
import br.com.senac.api.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pedidosItens")
public class PedidosItensController {

    @Autowired
    private PedidosItensService pedidosItensService;

    @GetMapping("/listar")
    public ResponseEntity<List<?>> listarTodos() {
        return ResponseEntity.ok(pedidosItensService.listar());
    }

    @PostMapping("/criar")
    public ResponseEntity<?> criar(@RequestBody PedidosItensRequestDto item) {
        try {
            return ResponseEntity
                    .status(201)
                    .body(pedidosItensService.criar(item));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizar(
            @PathVariable Long id,
            @RequestBody PedidosItensRequestDto item
    ) {
        try {
            return ResponseEntity.ok(pedidosItensService.atualizar(
                    id,item
            ));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            pedidosItensService.deletar(id);
            return ResponseEntity.ok(null);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body(ResponseUtil.parserMensagem(e.getMessage()));
        }
    }
}
