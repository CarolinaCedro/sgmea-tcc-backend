package tcc.sgmeabackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.service.impl.ChamadoReportServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("api/sgmea/v1/chamado-report")
public class ChamadoReportController {

    private final ChamadoReportServiceImpl chamadoReportService;

    public ChamadoReportController(ChamadoReportServiceImpl chamadoReportService) {
        this.chamadoReportService = chamadoReportService;
    }


    @PostMapping("/gerar-report-chamado")
    public void gerarReportChamado(@RequestBody ChamadoCriado chamado) throws IOException {
        try {
            this.chamadoReportService.gerar(chamado);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
