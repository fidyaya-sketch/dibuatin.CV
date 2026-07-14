package com.mycompany.dibuatincv.pengguna;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

///**
// * AcademicResume.java
// * Menghasilkan CV format Akademik/Riset sesuai gambar menggunakan iText 5.
// */
public class keisya {

    // ── Warna (Minimalis Hitam-Putih Sesuai Gambar) ───────
    private static final BaseColor BLACK      = BaseColor.BLACK;
    private static final BaseColor GRAY_LINE  = new BaseColor(150, 150, 150);

    // ── Font ───────────────────────────────────────────────
    private static final Font NAME_FONT     = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD,   BLACK);
    private static final Font CONTACT_FONT  = new Font(Font.FontFamily.HELVETICA,  9, Font.NORMAL, BLACK);
    private static final Font SECTION_FONT  = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   BLACK);
    private static final Font BOLD_FONT     = new Font(Font.FontFamily.HELVETICA,  9, Font.BOLD,   BLACK);
    private static final Font NORMAL_FONT   = new Font(Font.FontFamily.HELVETICA,  9, Font.NORMAL, BLACK);
    private static final Font ITALIC_FONT   = new Font(Font.FontFamily.HELVETICA,  9, Font.ITALIC, BLACK);

    // ── Data holder ──────────────────────────────────────────
    public static class DataDiri {
        String nama, alamat, email, noWa, pendidikan, tentangSaya, skill, pengalaman, leadership, projek;
    }

    public static String generateFromDatabase(int idData) throws Exception {
        DataDiri data = fetchData(idData);
        if (data == null) {
            throw new Exception("Data dengan id_data=" + idData + " tidak ditemukan!");
        }

        String outputPath = System.getProperty("user.home") + "/Documents/CV_Academic_" + data.nama.replaceAll("\\s+", "_") + ".pdf";
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
            d.projek      = rs.getString("projek");
            
            return d;
        }
        return null;
    }

    private static void generatePdf(DataDiri data, String outputPath) throws Exception {
        // Margin 36 (0.5 inch) agar muat banyak konten riset sesuai gambar
        Document document = new Document(PageSize.LETTER, 36, 36, 36, 36);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // ── KEPALA / HEADER (Nama & Kontak) ─────────────────
        addHeaderBox(document, data);

        // ── TENTANG SAYA ───────────────────────────────────
        addSectionHeader(document, "RESEARCH INTERESTS / PROFILE");
        addBodyParagraph(document, safe(data.tentangSaya));

        // ── PENDIDIKAN ─────────────────────────────────────
        addSectionHeader(document, "EDUCATION");
        addAcademicSplitRows(document, safe(data.pendidikan));

        // ── PENGALAMAN ─────────────────────────────────────
        addSectionHeader(document, "RESEARCH EXPERIENCE");
        addAcademicSplitRows(document, safe(data.pengalaman));

        // ── LEADERSHIP ─────────────────────────────────────
        addSectionHeader(document, "LEADERSHIP & SERVICE");
        addAcademicSplitRows(document, safe(data.leadership));

        // ── PROJEK ─────────────────────────────────────────
        addSectionHeader(document, "PROJECTS");
        addAcademicSplitRows(document, safe(data.projek));

        // ── SKILL ──────────────────────────────────────────
        addSectionHeader(document, "SKILLS & TECHNIQUES");
        addMultilineAsBullets(document, safe(data.skill), 10);

        document.close();
        System.out.println("✅ PDF Akademik berhasil dibuat: " + outputPath);
    }

    private static String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
    }

    // ── FORMAT HEADER REVISI (Tanpa Kotak, Garis Bawah Tipis) ──
    private static void addHeaderBox(Document doc, DataDiri data) throws DocumentException {
        Paragraph name = new Paragraph(safe(data.nama), NAME_FONT);
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingAfter(2);
        doc.add(name);

        Paragraph email = new Paragraph(safe(data.email), CONTACT_FONT);
        email.setAlignment(Element.ALIGN_CENTER);
        email.setSpacingAfter(2);
        doc.add(email);

        String detailKontak = safe(data.alamat) + "  •  " + safe(data.noWa);
        Paragraph contact = new Paragraph(detailKontak, CONTACT_FONT);
        contact.setAlignment(Element.ALIGN_CENTER);
        contact.setSpacingAfter(10);
        doc.add(contact);

        // Garis horizontal pembatas tipis di bawah kontak awal
        doc.add(createHorizontalLine());
        doc.add(new Paragraph(" "));
    }

    // ── FORMAT SECTION HEADER (Teks Tengah + Garis Bawah Tipis) ──
    private static void addSectionHeader(Document doc, String title) throws DocumentException {
        doc.add(new Paragraph(" ")); // Jarak sebelum section
        
        Paragraph p = new Paragraph(title, SECTION_FONT);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(4);
        doc.add(p);
        
        doc.add(createHorizontalLine());
    }

    // Line helper menggunakan PdfPTable dengan border bawah saja
    private static PdfPTable createHorizontalLine() {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.BOTTOM);
        cell.setBorderColor(GRAY_LINE);
        cell.setBorderWidth(0.8f);
        cell.setPadding(0);
        table.addCell(cell);
        return table;
    }

    private static void addBodyParagraph(Document doc, String text) throws DocumentException {
        Paragraph p = new Paragraph(text, NORMAL_FONT);
        p.setIndentationLeft(10);
        p.setSpacingBefore(4);
        doc.add(p);
    }

//    /**
//     * ── SPLIT BARIS OTOMATIS GAYA AKADEMIK ──
//     * Berguna memisahkan teks multi-baris dari JTextArea.
//     * Jika baris mengandung tanda koma (,), teks sebelum koma dicetak Tebal (Bold) di kiri,
//     * sisanya dicetak normal. Baris berikutnya otomatis menjadi sub-bullet di bawahnya.
//     */
    private static void addAcademicSplitRows(Document doc, String text) throws DocumentException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Jika baris diawali tanda minus atau bullet, jadikan bullet menjorok dalam
            if (line.trim().startsWith("-") || line.trim().startsWith("•")) {
                String cleanLine = line.replaceFirst("^[-•]", "").trim();
                addMultilineAsBullets(doc, cleanLine, 25);
                continue;
            }

            // Split menggunakan koma pertama untuk membagi Judul Instansi vs Detail Lokasi/Tanggal
            int commaIndex = line.indexOf(",");
            
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{4.5f, 1.5f});
            table.setSpacingBefore(4);

            PdfPCell leftCell = new PdfPCell();
            leftCell.setBorder(Rectangle.NO_BORDER);
            leftCell.setPaddingLeft(10);

            PdfPCell rightCell = new PdfPCell();
            rightCell.setBorder(Rectangle.NO_BORDER);
            rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

            if (commaIndex != -1) {
                String boldPart = line.substring(0, commaIndex).trim();
                String normalPart = line.substring(commaIndex).trim(); // Termasuk komanya

                Paragraph leftP = new Paragraph();
                leftP.add(new Chunk(boldPart, BOLD_FONT));
                leftP.add(new Chunk(normalPart, NORMAL_FONT));
                leftCell.addElement(leftP);
            } else {
                leftCell.addElement(new Paragraph(line.trim(), BOLD_FONT));
            }

            // Bagian kanan dikosongkan agar user bisa mengaturnya lewat bullet atau manual di text area
            rightCell.addElement(new Paragraph("", NORMAL_FONT));

            table.addCell(leftCell);
            table.addCell(rightCell);
            doc.add(table);
        }
    }

    private static void addMultilineAsBullets(Document doc, String text, float indent) throws DocumentException {
        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;
            Paragraph p = new Paragraph();
            p.setIndentationLeft(indent);
            p.setSpacingBefore(2);
            p.add(new Chunk("• ", NORMAL_FONT));
            p.add(new Chunk(line.trim(), NORMAL_FONT));
            doc.add(p);
        }
    }
}