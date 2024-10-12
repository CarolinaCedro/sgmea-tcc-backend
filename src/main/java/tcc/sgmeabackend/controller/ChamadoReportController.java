package tcc.sgmeabackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.model.dtos.ReportFilter;
import tcc.sgmeabackend.service.impl.ChamadoReportServiceImpl;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("api/sgmea/v1/chamado-report")
public class ChamadoReportController {

    private final ChamadoReportServiceImpl chamadoReportService;
    private final ChamadoServiceImpl chamadoService;


    public ChamadoReportController(ChamadoReportServiceImpl chamadoReportService, ChamadoServiceImpl chamadoService) {
        this.chamadoReportService = chamadoReportService;

        this.chamadoService = chamadoService;
    }

//    @GetMapping("/generated-report")
//    public void generateReport(
//            @RequestParam(required = false) LocalDate dataAbertura,
//            @RequestParam(required = false) LocalDate dataFechamento,
//            @RequestParam(required = false) String nomeEquipamento,
//            HttpServletResponse response) throws IOException, JRException {
//        ReportFilter filter = new ReportFilter(dataAbertura, dataFechamento, nomeEquipamento);
//        this.chamadoReportService.exportReport(response, filter);
//    }


    @GetMapping("/generated-report")
    public void generateReport(
            @RequestParam(required = false) LocalDate dataAbertura,
            @RequestParam(required = false) LocalDate dataFechamento,
            @RequestParam(required = false) String nomeEquipamento,
            HttpServletResponse response) throws IOException, JRException {

        ReportFilter filter = new ReportFilter(dataAbertura, dataFechamento, nomeEquipamento);

        // Chama o serviço para gerar o relatório
        this.chamadoReportService.exportReport(response, filter);
    }


}
