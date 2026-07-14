package com.mycompany.dibuatincv.pengguna;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

///**
// * ProjectHighlightResume.java
// * Format CV Chronological dengan sorotan proyek (Design Projects) dua kolom lateral.
// */
public class project {

    // ── Warna (Kembali ke tema Navy-Blue dari template utama) ──
    private static final BaseColor NAVY       = new BaseColor(30,  45,  90);
    private static final BaseColor LIGHT_BLUE = new BaseColor(100, 140, 200);
    private static final BaseColor BORDER_CLR = new BaseColor(180, 195, 230);
    private static final BaseColor BLACK      = BaseColor.BLACK;

    // ── Font ───────────────────────────────────────────────
    private static final Font TITLE_FONT    = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD,   NAVY);
    private static final Font NAME_FONT     = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD,   BLACK);
    private static final Font CONTACT_FONT  = new Font(Font.FontFamily.HELVETICA,  9, Font.NORMAL, BLACK);
    private static final Font SECTION_FONT  = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD,   NAVY);
    private static final Font BOLD_FONT     = new Font(Font.FontFamily.HELVETICA,  9, Font.BOLD,   BLACK);
    private static final Font NORMAL_FONT   = new Font(Font.FontFamily.HELVETICA,  9, Font.NORMAL, BLACK);

    // ── Data holder ──────────────────────────────────────────
    public static class DataDiri {
        String nama, alamat, email, noWa, pendidikan, tentangSaya, skill, pengalaman, leadership, projek;
    }

    public static String generateFromDatabase(int idData) throws Exception {
        DataDiri data = fetchData(idData);
        if (data == null) {
            throw new Exception("Data dengan id_data=" + idData + " tidak ditemukan!");
        }

        String outputPath = System.getProperty("user.home") + "/Documents/CV_Project_" + data.nama.replaceAll("\\s+", "_") + ".pdf";
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
        // Menggunakan margin standar agar border luar tampak seimbang
        Document document = new Document(PageSize.LETTER, 36, 36, 36, 36);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();

        // 1. Title Bar Dots Atas
//        addTitle(document);

        // 2. Box Info Utama (Nama di tengah, Alamat kiri, Kontak kanan)
        addNameContactBox(document, data);

        // 3. Section Profil / Tentang Saya
        addTwoColumnSection(document, "PROFILE", data.tentangSaya, false);

        // 4. Section Pendidikan
        addTwoColumnSection(document, "EDUCATION", data.pendidikan, false);

        // 5. Section Keahlian (Skills)
        addTwoColumnSection(document, "SKILLS", data.skill, false);

        // 6. Section Projek Utama (Design Projects)
        addTwoColumnSection(document, "PROJECTS", data.projek, true);

        // 7. Section Pengalaman Kerja
        addTwoColumnSection(document, "EXPERIENCE", data.pengalaman, true);

        // 8. Section Kepemimpinan (Leadership)
        addTwoColumnSection(document, "LEADERSHIP", data.leadership, true);

        document.close();
        System.out.println("✅ PDF Project Highlights berhasil dibuat: " + outputPath);
    }

    private static String safe(String s) {
        return (s == null || s.trim().isEmpty()) ? "-" : s.trim();
    }

    private static PdfPCell dotsCell(boolean isLeft) {
        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        Paragraph p = new Paragraph();
        Font dotNavy  = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, NAVY);
        Font dotLight = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, LIGHT_BLUE);
        String[] colors = isLeft ? new String[]{"N","N","L","L"} : new String[]{"L","L","N","N"};
        for (String c : colors) {
            p.add(new Chunk("● ", "N".equals(c) ? dotNavy : dotLight));
        }
        p.setAlignment(isLeft ? Element.ALIGN_RIGHT : Element.ALIGN_LEFT);
        cell.addElement(p);
        return cell;
    }

    // ── 2. BOX NAMA & KONTAK (Gaya 3 Kolom di dalam Border) ──
    private static void addNameContactBox(Document doc, DataDiri data) throws DocumentException {
        PdfPTable containerTable = new PdfPTable(1);
        containerTable.setWidthPercentage(100);

        PdfPCell boxCell = new PdfPCell();
        boxCell.setBorderColor(BORDER_CLR);
        boxCell.setBorderWidth(1f);
        boxCell.setPadding(8);

        // Sub table untuk membagi Alamat (Kiri), Nama (Tengah), Kontak (Kanan)
        PdfPTable infoTable = new PdfPTable(3);
        infoTable.setWidthPercentage(100);
        infoTable.setWidths(new float[]{2.2f, 2.6f, 2.2f});

        // Alamat Kiri
        PdfPCell leftCell = new PdfPCell(new Paragraph(safe(data.alamat), CONTACT_FONT));
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setHorizontalAlignment(Element.ALIGN_LEFT);

        // Nama Tengah
        PdfPCell centerCell = new PdfPCell(new Paragraph(safe(data.nama), NAME_FONT));
        centerCell.setBorder(Rectangle.NO_BORDER);
        centerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        centerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // Kontak Kanan
        String kananTeks = safe(data.email) + "\n" + safe(data.noWa);
        PdfPCell rightCell = new PdfPCell(new Paragraph(kananTeks, CONTACT_FONT));
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setHorizontalAlignment(Element.ALIGN_RIGHT);

        infoTable.addCell(leftCell);
        infoTable.addCell(centerCell);
        infoTable.addCell(rightCell);

        boxCell.addElement(infoTable);
        containerTable.addCell(boxCell);
        
        doc.add(containerTable);
        doc.add(spacer(5));
    }

    // ── 3. TEMPLATE DUA KOLOM LATERAL (Kiri: Judul Section, Kanan: Isi Teks) ──
    private static void addTwoColumnSection(Document doc, String sectionTitle, String contentText, boolean useBullets) throws DocumentException {
        String cleanText = safe(contentText);
        if (cleanText.equals("-")) return; // Lewati jika data kosong

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);
        mainTable.setWidths(new float[]{1.8f, 5.2f}); // Kolom kiri lebih ramping
        mainTable.setSpacingBefore(8);

        // Kolom Kiri: Judul Section
        PdfPCell leftCell = new PdfPCell(new Paragraph(sectionTitle, SECTION_FONT));
        leftCell.setBorder(Rectangle.NO_BORDER);
        leftCell.setPaddingTop(2);

        // Kolom Kanan: Isi Data Konten
        PdfPCell rightCell = new PdfPCell();
        rightCell.setBorder(Rectangle.NO_BORDER);
        rightCell.setPaddingLeft(10);

        String[] lines = cleanText.split("\n");
        for (String line : lines) {
            if (line.trim().isEmpty()) continue;

            // Logika pemisahan koma untuk Baris Utama (Cetak Tebal nama instansi/projek)
            if (!line.trim().startsWith("-") && !line.trim().startsWith("•")) {
                int commaIndex = line.indexOf(",");
                Paragraph pHeader = new Paragraph();
                pHeader.setSpacingBefore(3);

                if (commaIndex != -1) {
                    String boldPart = line.substring(0, commaIndex).trim();
                    String normalPart = line.substring(commaIndex).trim();
                    pHeader.add(new Chunk(boldPart, BOLD_FONT));
                    pHeader.add(new Chunk(normalPart, NORMAL_FONT));
                } else {
                    pHeader.add(new Chunk(line.trim(), BOLD_FONT));
                }
                rightCell.addElement(pHeader);
            } 
            // Logika jika baris merupakan sub-bullet deskripsi
            else {
                String cleanLine = line.replaceFirst("^[-•]", "").trim();
                Paragraph pBullet = new Paragraph();
                pBullet.setIndentationLeft(10);
                pBullet.setSpacingBefore(1);
                // Gunakan format bullet jika diinstruksikan atau bawaan teks area
                if (useBullets) {
                    pBullet.add(new Chunk("• ", NORMAL_FONT));
                }
                pBullet.add(new Chunk(cleanLine, NORMAL_FONT));
                rightCell.addElement(pBullet);
            }
        }

        mainTable.addCell(leftCell);
        mainTable.addCell(rightCell);
        doc.add(mainTable);
    }

    private static Paragraph spacer(float size) {
        Paragraph p = new Paragraph(" ");
        p.setSpacingBefore(size);
        return p;
    }
}