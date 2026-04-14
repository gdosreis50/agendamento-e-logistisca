/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import entidades.CheckList;
import entidades.Pedido;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.printing.PDFPageable;
import util.env;

/**
 *
 * @author work & college
 */
public class ImpressaoService {
    
    public void gerarChecklist(CheckList check, Pedido pedido) throws Exception {
        try (PDDocument doc = Loader.loadPDF(new File(env.CAMINHO_TEMPLATE))) {
        
            // 2. Você pega a página que já existe no layout (geralmente a primeira, índice 0)
            PDPage pagina = doc.getPage(0);

            // 3. O 'AppendMode.APPEND' diz ao PDFBox: "Mantenha o que está lá e adicione isto"
            try (PDPageContentStream canvas = new PDPageContentStream(doc, pagina, PDPageContentStream.AppendMode.APPEND, true, true)) {
                
                canvas.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 12);
                
                escrever(canvas, 123, 718, pedido.getNumPed());
                escrever(canvas, 123 , 692, pedido.getNomeCliente());
                escrever(canvas, 135, 665, check.getMotorista().getNomeMotorista());
                
                if(check.getTransportadora() != null){
                    escrever(canvas, 174, 636, check.getTransportadora().getNomeTransportadora());
                }
                
                String vagoes = check.getVeiculo().getVagoes().stream().map(Object::toString).collect(Collectors.joining(", "));
                escrever(canvas, 375, 718, vagoes);
                
                escrever(canvas, 339, 688, Integer.toString(check.getVeiculo().getTara()));
                
                SimpleDateFormat format = new SimpleDateFormat("HH:mm dd/MM/YYYY");
                escrever(canvas, 377, 660, format.format(check.getDataEmissao()));
                
                escrever(canvas, 358, 629, check.getFuncionario().getNomeFunc());
            }

            // 4. Salva como um NOVO arquivo para não sobrescrever seu template original
            doc.save(env.CAMINHO_PASTA_FINAL + "\\checklist" + pedido.getNumPed() + ".pdf");
            
            imprimir(doc);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void escrever(PDPageContentStream canvas, float x, float y, String texto) throws IOException {
        canvas.beginText();
        canvas.newLineAtOffset(x, y);
        canvas.showText(texto != null ? texto : "");
        canvas.endText();
    }
    
    private void imprimir(PDDocument checkPdf) throws PrinterException{
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPageable(new PDFPageable(checkPdf));
        
        if(job.printDialog()){
            job.print();
        }
    }
}
