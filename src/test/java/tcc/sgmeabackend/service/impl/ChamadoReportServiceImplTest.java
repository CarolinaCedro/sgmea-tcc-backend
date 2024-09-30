package tcc.sgmeabackend.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.ChamadoReportResponse;
import tcc.sgmeabackend.model.dtos.ReportFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;

class ChamadoReportServiceImplTest {

    @Mock
    private ChamadoServiceImpl repository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private HttpServletResponse response;

    private ChamadoReportServiceImpl chamadoReportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chamadoReportService = new ChamadoReportServiceImpl(repository, modelMapper);
    }

    @Test
    void testExportReport_NoContent() throws JRException, IOException {
        // Given
        ReportFilter filter = new ReportFilter();
        when(repository.getChamadosConcluidosReport(filter)).thenReturn(Collections.emptyList());

        // When
        chamadoReportService.exportReport(response, filter);

        // Then
        verify(response).setStatus(HttpServletResponse.SC_NO_CONTENT);
        verify(response, never()).getOutputStream();
    }

    //Muito complexo de simular por se tratar de um lib externa para a geração de relatorio. torna o processo mt complexo

//    @Test
//    void testExportReport_Success() throws JRException, IOException {
//        // Given
//        ReportFilter filter = new ReportFilter();
//        ChamadoCriado chamadoCriado = new ChamadoCriado(); // Preencha com dados relevantes
//        chamadoCriado.setId("1");
//        ChamadoReportResponse chamadoResponse = new ChamadoReportResponse(); // Preencha com dados relevantes
//
//        when(repository.getChamadosConcluidosReport(filter)).thenReturn(List.of(chamadoCriado));
//        when(modelMapper.map(chamadoCriado, ChamadoReportResponse.class)).thenReturn(chamadoResponse);
//
//        // When
//        chamadoReportService.exportReport(response, filter);
//
//        // Then
//        verify(response).setContentType("application/pdf");
//        verify(response).setHeader("Content-Disposition", "inline; filename=relatorio.pdf");
//        // Verifica se o método de saída do fluxo foi chamado
//        // Adicione mais verificações conforme necessário, como verificar o preenchimento do relatório
//    }

}
