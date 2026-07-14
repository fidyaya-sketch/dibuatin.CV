package com.mycompany.dibuatincv.pengguna;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChronologicalResume {

    // ── Warna ──────────────────────────────────────────────
    private static final BaseColor NAVY       = new BaseColor(30,  45,  90);
    private static final BaseColor LIGHT_BLUE = new BaseColor(100, 140, 200);
    private static final BaseColor SECTION_BG = new BaseColor(220, 228, 245);
    private static final BaseColor BORDER_CLR = new BaseColor(180, 195, 230);
    private static final BaseColor BLACK      = BaseColor.BLACK;

    // ── Font ───────────────────────────────────────────────
    private static final Font TITLE_FONT    = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD,   NAVY);
    private static final Font NAME_FONT     = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD,   BLACK);
    private static final Font CONTACT_FONT  = new Font(Font.FontFamily.HELVETICA,  9, Font.NORMAL, BLACK);
    private static final Font SECTION_FONT  = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   NAVY);
    private static final Font BOLD_FONT     = new Font(Font.FontFamily.HELVETICA,  9, Font.BOLD,   BLACK);
    private static final Font NORMAL_FONT   = new Font(Font.FontFamily.HELVETICA,  9, Font.NORMAL, BLACK);

    // ── Data holder ──────────────────────────────────────────
    public static class DataDiri {
        // PERBAIKAN: Menambahkan variabel 'projek' ke dalam kelas penampung
        String nama, alamat, email, noWa, pendidikan, tentangSaya, skill, pengalaman, leadership, projek;
    }

    public static String generateFromDatabase(int idData) throws Exception {
        DataDiri data = fetchData(idData);
        if (data == null) {
            throw new Exception("Data dengan id_data=" + idData + " tidak ditemukan!");
        }

        String outputPath = System.getProperty("user.home") + "/Documents/CV_" + data.nama.replaceAll("\\s+", "_") + ".pdf";
        generatePdf(data, outputPath);
        return outputPath;
    }

    private static DataDiri fetchData(int idData) throws Exception {
        Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        String sql = "SELECT nama, alamat, email, no_wa, pendidikan, tentang_saya, keahlian, pengalaman, kepemimpinan, projek "
                   + "FROM data_diri WHERE id_data = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, idData);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            DataDiri d = new DataDiri();
            d.nama        = rs.getString("nama");
            d.alamat      = rs.getString("alamat");
            d.email       = rs.getString("email");
            d.noWa        = rs.getString("no_wa");
            d.pendidikan  = rs.getString("pendidikan");
            d.tentangSaya = rs.getString("tentang_saya");
            d.skill       = rs.getString("keahlian");      
            d.pengalaman  = rs.getString("pengalaman");
            d.leadership  = rs.getString("kepemimpinan");  
            // PERBAIKAN: Memasukkan data dari database kolom 'projek' ke variabel object
            d.projek      = rs.getString("projek");
            
            return d;
        }
        return null;
    }

    private static void generatePdf(DataDiri data, String outputPath) throws Exception {
        Document document = new Document(PageSize.LETTER, 36, 36, 36, 36);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        addTitle(document);
        addNameContactBox(document, data);

        addSectionHeader(document, "TENTANG SAYA");
        addBodyParagraph(document, safe(data.tentangSaya));

        addSectionHeader(document, "PENDIDIKAN");
        addBodyParagraph(document, safe(data.pendidikan));

        addSectionHeader(document, "PENGALAMAN");
        addMultilineAsBullets(document, safe(data.pengalaman));

        addSectionHeader(document, "LEADERSHIP");
        addMultilineAsBullets(document, safe(data.leadership));        
        
        // PERBAIKAN: Menggambar section PROJEK ke dalam PDF layout
        addSectionHeader(document, "PROJEK");
        addMultilineAsBullets(document, safe(data.projek));
        
        addSectionHeader(document, "SKILL");
        addBodyParagraph(document, safe(data.skill));

        document.close();
        System.out.println("✅ PDF berhasil dibuat: " + outputPath);
    }

    private static String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
    }

    // ── TITLE BAR ─────────────────────────────────────────
    private static void addTitle(Document doc) throws DocumentException {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{1.5f, 5f, 1.5f});

        PdfPCell leftDots = dotsCell(true);
        PdfPCell titleCell = new PdfPCell(new Phrase("", TITLE_FONT));
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleCell.setPadding(10);
        PdfPCell rightDots = dotsCell(false);

        table.addCell(leftDots);
        table.addCell(titleCell);
        table.addCell(rightDots);
        doc.add(table);
        doc.add(new Paragraph(" "));
    }

    private static PdfPCell dotsCell(boolean isLeft) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        cell.setPadding(8);

        Paragraph p = new Paragraph();
        Font dotNavy  = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, NAVY);
        Font dotLight = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, LIGHT_BLUE);
        String[] colors = isLeft ? new String[]{"N","N","L","L"} : new String[]{"L","L","N","N"};
        for (String c : colors) {
            p.add(new Chunk("● ", "N".equals(c) ? dotNavy : dotLight));
        }
        p.setAlignment(isLeft ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT);
        cell.addElement(p);
        return cell;
    }

    // ── NAMA & KONTAK ─────────────────────────────────────
    private static void addNameContactBox(Document doc, DataDiri data) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        PdfPCell cell = new PdfPCell();
        cell.setBorderColor(BORDER_CLR);
        cell.setBorderWidth(1.2f);
        cell.setPadding(8);

        Paragraph name = new Paragraph(safe(data.nama), NAME_FONT);
        name.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(name);

        String kontak = safe(data.alamat) + " | " + safe(data.noWa) + " | " + safe(data.email);
        Paragraph contact = new Paragraph(kontak, CONTACT_FONT);
        contact.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(contact);

        table.addCell(cell);
        doc.add(table);
        doc.add(new Paragraph(" "));
    }

    // ── SECTION HEADER ────────────────────────────────────
    private static void addSectionHeader(Document doc, String title) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(6);

        PdfPCell cell = new PdfPCell(new Phrase(title, SECTION_FONT));
        cell.setBackgroundColor(SECTION_BG);
        cell.setBorderColor(BORDER_CLR);
        cell.setBorderWidth(0.8f);
        cell.setPadding(4);
        table.addCell(cell);
        doc.add(table);
    }

    // ── BODY PARAGRAPH ────────────────────────────────────
    private static void addBodyParagraph(Document doc, String text) throws DocumentException {
        Paragraph p = new Paragraph(text, NORMAL_FONT);
        p.setIndentationLeft(10);
        p.setSpacingBefore(3);
        doc.add(p);
    }

    // ── MULTILINE AS BULLETS ──────────────────────────────
    private static void addMultilineAsBullets(Document doc, String text) throws DocumentException {
        if (text == null) return;
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            Paragraph p = new Paragraph();
            p.setIndentationLeft(15);
            p.setSpacingBefore(1);
//    if (line != null && !line.isEmpty()) {
    // 1. Pisahkan teks berdasarkan tombol Enter (\n)
//    String[] barisTeks = line.split("\\r?\\n");
    
//    for (String baris : lines) {
        if (line.matches("^[A-Z\\s\\p{Punct}]+$") && line.matches(".*[A-Z].*")) {
            p.add(new Chunk("• ", NORMAL_FONT));
            p.add(new Chunk(line.trim(), NORMAL_FONT));
//            com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
//            list.add(new com.itextpdf.text.ListItem(line));
//            p.add(list);
            
        } else {
            // TAMPILKAN SEBAGAI PARAGRAF BIASA (Jika ada huruf kecil)
            p.add(new Chunk(line.trim(), NORMAL_FONT));
        }
        doc.add(p);
    }
}
}