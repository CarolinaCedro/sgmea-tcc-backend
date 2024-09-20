package tcc.sgmeabackend.service.impl;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import tcc.sgmeabackend.model.ChamadoCriado;
import tcc.sgmeabackend.model.ChamadoReportResponse;
import tcc.sgmeabackend.model.dtos.ReportFilter;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChamadoReportServiceImpl {

    private final ChamadoServiceImpl repository;
    public final ModelMapper modelMapper;

    public ChamadoReportServiceImpl(ChamadoServiceImpl repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }


    public void exportReport(HttpServletResponse response, ReportFilter filter) throws JRException, IOException {
        List<ChamadoCriado> chamados = repository.getChamadosConcluidosReport(filter);

        if (chamados.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        List<ChamadoReportResponse> chamadosResponse = chamados.stream()
                .map(chamado -> modelMapper.map(chamado, ChamadoReportResponse.class))
                .collect(Collectors.toList());

        File file = ResourceUtils.getFile("classpath:chamado.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(chamadosResponse);
        Map<String, Object> parameters = new HashMap<>();

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Configura a resposta para enviar o PDF diretamente
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=relatorio.pdf");

        OutputStream outStream = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        outStream.flush();
        outStream.close();
    }



//    public void exportReport(HttpServletResponse response, ReportFilter filter) throws JRException, IOException {
//        // Define o caminho para salvar o relatório na pasta "Downloads" do usuário no Windows
//
//        String PATH = "C:\\chamados-report\\relatorio.pdf";
//
//
//        // Busca os chamados encerrados
//        List<ChamadoCriado> chamados = repository.getChamadosConcluidosReport(filter);
//
//        if (chamados.isEmpty()) {
//            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
//            return;
//        }
//
//        // Mapeando cada ChamadoCriado para ChamadoReportResponse
//        List<ChamadoReportResponse> chamadosResponse = chamados.stream()
//                .map(chamado -> modelMapper.map(chamado, ChamadoReportResponse.class))
//                .collect(Collectors.toList());
//
//        // Localiza o arquivo de template do relatório
//        File file = ResourceUtils.getFile("classpath:chamado.jrxml");
//        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
//
//        // Preenche os dados do relatório
//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(chamadosResponse);
//        Map<String, Object> parameters = new HashMap<>();
////        parameters.put("createdBy", "Carolina Cedro");
//
//        // Gera o relatório
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
//
//        // Exporta o relatório para um arquivo PDF na pasta "Downloads"
//        JasperExportManager.exportReportToPdfFile(jasperPrint, PATH);
//
//        // Configura a resposta para baixar o PDF diretamente
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
//
//        // Envia o relatório diretamente para o navegador
//        final OutputStream outStream = response.getOutputStream();
//        JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
//    }

}
