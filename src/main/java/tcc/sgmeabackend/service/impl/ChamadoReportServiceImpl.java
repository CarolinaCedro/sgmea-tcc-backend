package tcc.sgmeabackend.service.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSwapFile;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import tcc.sgmeabackend.model.ChamadoCriado;

import javax.sql.DataSource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfStream;

@Service
public class ChamadoReportServiceImpl {

    public static final String CHAMADOS = "classpath:jasper/chamados/";
    public static final String IMAGEBG = "classpath:jasper/img/logo.png";
    public static final String ARQUIVOJRXML = "chamado.jrxml";
    public static final Logger LOGGER = LoggerFactory.getLogger(ChamadoReportServiceImpl.class);

    public static final String DESTINOPDF = "C:\\chamados-report\\";

    @Autowired
    private DataSource dataSource;  // Injetar o DataSource

    public void gerar(ChamadoCriado chamado) throws IOException {
        System.out.println("Chamado recebido: " + chamado);

        byte[] imagebg = this.loadImage(IMAGEBG);

        Map<String, Object> params = new HashMap<>();
        params.put("titulo", chamado.getTitulo());
        params.put("observacoes", chamado.getObservacoes());
        params.put("descricao_equipamento", chamado.getEquipamento());
        params.put("data_abertura", chamado.getDataAbertura());
        params.put("data_fechamento", chamado.getDataFechamento());
        params.put("logo", imagebg);

        System.out.println("Parâmetros passados: " + params);

        String pathAbsoluto = getAbsultePath();
        try {
            String folderDiretorio = getDiretorioSave("chamados-salvos");
            JasperReport report = JasperCompileManager.compileReport(pathAbsoluto);
            LOGGER.info("Relatório compilado: {} ", pathAbsoluto);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());

            // Chame o método print passando o JasperPrint
            try (OutputStream outputStream = new FileOutputStream(folderDiretorio)) {
                this.print(print, outputStream);
            }

            LOGGER.info("JasperPrint criado com sucesso.");
            JasperExportManager.exportReportToPdfFile(print, folderDiretorio);
            LOGGER.info("PDF exportado para: {}", folderDiretorio);

        } catch (JRException e) {
            LOGGER.error("Erro ao gerar relatório: ", e);
            throw new RuntimeException(e);
        }
    }

    public void print(final JasperPrint print, final OutputStream outputStream) {
        // Criando o virtualizador de cache para impressão
        final JRSwapFileVirtualizer virtualizer = new JRSwapFileVirtualizer(
                1,
                new JRSwapFile(System.getProperty("java.io.tmpdir"), 1, 100),
                true
        );
        try {
            final Map<String, Object> params = this.getParams();
            params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            exportReportToPdfStream(print, outputStream);
            LOGGER.info("Relatório enviado para o OutputStream com sucesso.");
        } catch (final JRException e) {
            LOGGER.error("Erro ao imprimir relatório: ", e);
            throw new RuntimeException(e);
        } finally {
            virtualizer.cleanup();
        }
    }

    public JasperReport loadReport() {
        try {
            return (JasperReport) JRLoader.loadObject(getSourceReport());
        } catch (final JRException ex) {
            LOGGER.error("Erro ao carregar relatório: ", ex);
            throw new RuntimeException(ex);
        }
    }

    private byte[] loadImage(String imagebg) throws IOException {
        String image = ResourceUtils.getFile(imagebg).getAbsolutePath();
        File file = new File(image);
        try (InputStream inputStream = new FileInputStream(file)) {
            return IOUtils.toByteArray(inputStream);
        }
    }

    private String getDiretorioSave(String name) {
        this.createDiretorio(DESTINOPDF);
        return DESTINOPDF + name.concat(".pdf");
    }

    private void createDiretorio(String name) {
        File dir = new File(name);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    private String getAbsultePath() throws FileNotFoundException {
        return ResourceUtils.getFile(CHAMADOS + ARQUIVOJRXML).getAbsolutePath();
    }

    private Map<String, Object> getParams() {
        // Retornar parâmetros dummy para simplificar, ajustar conforme necessário
        Map<String, Object> params = new HashMap<>();
        params.put("example", "value");
        return params;
    }

    private File getSourceReport() {
        return new File(CHAMADOS + ARQUIVOJRXML);
    }
}
