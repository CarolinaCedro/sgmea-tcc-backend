package tcc.sgmeabackend.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.sgmeabackend.service.impl.ChamadoReportServiceImpl;
import tcc.sgmeabackend.service.impl.ChamadoServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("api/sgmea/v1/chamado-report")
public class ChamadoReportController {

    private final ChamadoReportServiceImpl chamadoReportService;
    private final ChamadoServiceImpl chamadoService;


    public ChamadoReportController(ChamadoReportServiceImpl chamadoReportService, ChamadoServiceImpl chamadoService) {
        this.chamadoReportService = chamadoReportService;

        this.chamadoService = chamadoService;
    }

    @GetMapping("/generated-report")
    public void generateReport(HttpServletResponse response) throws IOException, JRException {
        this.chamadoReportService.exportReport(response);
    }
}
