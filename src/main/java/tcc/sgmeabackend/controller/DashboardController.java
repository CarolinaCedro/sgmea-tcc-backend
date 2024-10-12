package tcc.sgmeabackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.enums.Status;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;
import tcc.sgmeabackend.service.impl.EquipamentoServiceImpl;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/sgmea/v1/dashboard")
public class DashboardController {

    private final ChamadoServiceImpl chamadoService;
    private final EquipamentoServiceImpl equipamentoService;

    public DashboardController(ChamadoServiceImpl chamadoService, EquipamentoServiceImpl equipamentoService) {
        this.chamadoService = chamadoService;
        this.equipamentoService = equipamentoService;
    }


//    @GetMapping()
//    public ResponseEntity<Map<String, Object>> getChamadoDistribution() {
//        Map<String, Object> chartData = chamadoService.getChamadoChartData();
//        return ResponseEntity.ok(chartData);
//    }


    @GetMapping()
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        // Obter dados do serviço de chamados
        int chamadosAbertos = chamadoService.countChamadosByStatus(Status.ABERTO);
        int chamadosPendentes = chamadoService.countChamadosByStatus(Status.ANDAMENTO);
        int chamadosConcluidos = chamadoService.countChamadosByStatus(Status.CONCLUIDO);
        int chamadosCriticos = chamadoService.countChamadosCriticos();
        long equipamentos = equipamentoService.countEquipamentos();

        // Obter dados para o gráfico de distribuição de chamados
        Map<String, Object> chartData = chamadoService.getChamadoChartData();

        // Construir a resposta JSON
        Map<String, Object> response = new HashMap<>();
        response.put("chamadosAbertos", chamadosAbertos);
        response.put("chamadosPendentes", chamadosPendentes);
        response.put("chamadosCriticos", chamadosCriticos);
        response.put("chamadosConcluidos", chamadosConcluidos);
        response.put("equipamentos", equipamentos);
        response.put("chartData", chartData);

        return ResponseEntity.ok(response);
    }

}
