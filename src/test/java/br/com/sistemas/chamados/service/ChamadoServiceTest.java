package br.com.sistemas.chamados.service;

import br.com.sistemas.chamados.dto.ChamadoAtribuirRequest;
import br.com.sistemas.chamados.dto.ChamadoRequest;
import br.com.sistemas.chamados.dto.ChamadoResponse;
import br.com.sistemas.chamados.dto.ChamadoStatusRequest;
import br.com.sistemas.chamados.entity.Categoria;
import br.com.sistemas.chamados.entity.Chamado;
import br.com.sistemas.chamados.entity.Cliente;
import br.com.sistemas.chamados.entity.Tecnico;
import br.com.sistemas.chamados.enums.Perfil;
import br.com.sistemas.chamados.enums.Prioridade;
import br.com.sistemas.chamados.enums.StatusChamado;
import br.com.sistemas.chamados.exception.RecursoNaoEncontradoException;
import br.com.sistemas.chamados.exception.RegraNegocioException;
import br.com.sistemas.chamados.repository.ChamadoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChamadoServiceTest {

    @Mock private ChamadoRepository repository;
    @Mock private TecnicoService tecnicoService;
    @Mock private CategoriaService categoriaService;
    @Mock private ClienteService clienteService;

    private ChamadoService service;
    private Chamado chamado;

    @BeforeEach
    void preparar() {
        service = new ChamadoService(repository, tecnicoService, categoriaService, clienteService);
        chamado = new Chamado("Sem internet", "Rede caiu", Prioridade.ALTA,
                new Cliente("Ana", "ana@exemplo.com", "47999999999"),
                new Categoria("Rede", "Problemas de rede"));
    }

    private void chamadoExiste() {
        when(repository.findById(1L)).thenReturn(Optional.of(chamado));
    }

    // ---------- atribuir técnico ----------

    @Test
    void atribuirTecnico_chamadoAberto_atribui() {
        chamadoExiste();
        when(tecnicoService.buscarEntity(2L)).thenReturn(new Tecnico("Carlos", "carlos@empresa.com", Perfil.TECNICO));

        ChamadoResponse resposta = service.atribuirTecnico(1L, new ChamadoAtribuirRequest(2L));

        assertEquals("Carlos", resposta.tecnicoNome());
    }

    @Test
    void atribuirTecnico_chamadoFechado_lancaRegraNegocio() {
        chamado.setStatus(StatusChamado.FECHADO);
        chamadoExiste();

        assertThrows(RegraNegocioException.class,
                () -> service.atribuirTecnico(1L, new ChamadoAtribuirRequest(2L)));
        verifyNoInteractions(tecnicoService);
    }

    @Test
    void atribuirTecnico_chamadoInexistente_lancaNaoEncontrado() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.atribuirTecnico(99L, new ChamadoAtribuirRequest(2L)));
    }

    // ---------- alterar status ----------

    @Test
    void alterarStatus_valido_atualizaStatus() {
        chamadoExiste();

        ChamadoResponse resposta = service.alterarStatus(1L, new ChamadoStatusRequest(StatusChamado.EM_ANDAMENTO));

        assertEquals(StatusChamado.EM_ANDAMENTO, resposta.status());
    }

    @Test
    void alterarStatus_paraFechado_lancaRegraNegocio() {
        chamadoExiste();

        assertThrows(RegraNegocioException.class,
                () -> service.alterarStatus(1L, new ChamadoStatusRequest(StatusChamado.FECHADO)));
        assertEquals(StatusChamado.ABERTO, chamado.getStatus());
    }

    @Test
    void alterarStatus_chamadoFechado_naoReabre() {
        chamado.setStatus(StatusChamado.FECHADO);
        chamadoExiste();

        assertThrows(RegraNegocioException.class,
                () -> service.alterarStatus(1L, new ChamadoStatusRequest(StatusChamado.ABERTO)));
        assertEquals(StatusChamado.FECHADO, chamado.getStatus());
    }

    // ---------- fechar ----------

    @Test
    void fechar_chamadoAberto_preencheDataFechamento() {
        chamadoExiste();

        ChamadoResponse resposta = service.fechar(1L);

        assertEquals(StatusChamado.FECHADO, resposta.status());
        assertNotNull(resposta.dataFechamento());
    }

    @Test
    void fechar_chamadoJaFechado_lancaRegraNegocio() {
        chamado.setStatus(StatusChamado.FECHADO);
        chamadoExiste();

        assertThrows(RegraNegocioException.class, () -> service.fechar(1L));
    }

    // ---------- atualizar ----------

    @Test
    void atualizar_chamadoFechado_lancaRegraNegocio() {
        chamado.setStatus(StatusChamado.FECHADO);
        chamadoExiste();
        ChamadoRequest dto = new ChamadoRequest("Novo título", "Nova descrição", Prioridade.BAIXA, 1L, 1L);

        assertThrows(RegraNegocioException.class, () -> service.atualizar(1L, dto));
        assertEquals("Sem internet", chamado.getTitulo());
    }
}
