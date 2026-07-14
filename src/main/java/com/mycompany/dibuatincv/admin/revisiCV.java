/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.admin;


import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;


/**
 *
 * @author Lenovo
 */
public class revisiCV extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(revisiCV.class.getName());
    
    
    
   // private javax.swing.JTextArea txtNote;
    /**
     * Creates new form RevisiCV
     */
    
     
    public revisiCV() {
        initComponents();
//        try {
////        ImageIcon icon1 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/admin/image/desainbaru.jpeg");
////        
////        // Memeriksa apakah gambar berhasil dimuat
////        if (icon1.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
////            // Perbaikan: Tambahkan SPASI, bukan underscore (_)
////            Image imgScale1 = icon1.getImage().getScaledInstance(400, 516, Image.SCALE_SMOOTH);
////            gambarcv.setIcon(new ImageIcon(imgScale1));
////        } else { 
////            // Perbaikan: Blok else sekarang berada di dalam try, tepat setelah tutup kurung IF
////            System.out.println("Gambar desainbaru.jpeg tidak ditemukan!");
////        }
//    
//    
//} catch (Exception e) {
//    e.printStackTrace();
//}
    }
    
    revisiCV(int dataId) {
    initComponents();
    this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    
    // Simpan dataId ke session
    com.mycompany.dibuatincv.pengguna.SessionManager.getInstance().setIdData(dataId);
    
    // Load data CV dari database berdasarkan dataId
    loadDataCV(dataId);
}
    private void loadDataCV(int dataId) {
    try {
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        String sql = "SELECT * FROM data_diri WHERE id_data = ?";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, dataId);
        java.sql.ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String namaDesain = rs.getString("pilihan_desain");
            String namaUser   = rs.getString("nama");
            String noteRevisi = rs.getString("note_revisi");
            String pathCv     = rs.getString("path_cv");

            // Tampilkan note konsultan di textArea
            if (noteRevisi != null && !noteRevisi.isEmpty()) {
                jTextArea1.setText("Note Konsultan:\n" + noteRevisi);
            } else {
                jTextArea1.setText("(Tidak ada note dari konsultan)");
            }

            // Tampilkan gambar CV yang sudah digenerate
            if (pathCv != null && !pathCv.isEmpty()) {
                tampilkanPdfSebagaiGambar(pathCv);
            } else {
                String pathGambar = getPathGambarDesain(namaDesain);
                try {
                    java.awt.Image imgScale = new javax.swing.ImageIcon(pathGambar)
                        .getImage().getScaledInstance(400, 516, java.awt.Image.SCALE_SMOOTH);
                    gambarcv.setIcon(new javax.swing.ImageIcon(imgScale));
                } catch (Exception e) {
                    gambarcv.setText("Gambar tidak ditemukan");
                }
            }

            // ✅ TARUH DI SINI - setelah bagian gambar, sebelum setTitle
            StringBuilder sb = new StringBuilder();
            sb.append("NAMA: ").append(rs.getString("nama") != null ? rs.getString("nama") : "").append("\n");
            sb.append("ALAMAT: ").append(rs.getString("alamat") != null ? rs.getString("alamat") : "").append("\n");
            sb.append("NO WA: ").append(rs.getString("no_wa") != null ? rs.getString("no_wa") : "").append("\n");
            sb.append("EMAIL: ").append(rs.getString("email") != null ? rs.getString("email") : "").append("\n\n");
            sb.append("PENDIDIKAN:\n").append(rs.getString("pendidikan") != null ? rs.getString("pendidikan") : "").append("\n\n");
            sb.append("TENTANG SAYA:\n").append(rs.getString("tentang_saya") != null ? rs.getString("tentang_saya") : "").append("\n\n");
            sb.append("PENGALAMAN:\n").append(rs.getString("pengalaman") != null ? rs.getString("pengalaman") : "").append("\n\n");
            sb.append("KEPEMIMPINAN:\n").append(rs.getString("kepemimpinan") != null ? rs.getString("kepemimpinan") : "").append("\n\n");
            sb.append("SKILL:\n").append(rs.getString("keahlian") != null ? rs.getString("keahlian") : "").append("\n\n");
            sb.append("PROJEK:\n").append(rs.getString("projek") != null ? rs.getString("projek") : "").append("\n");
            txtEditData.setText(sb.toString());
            txtEditData.setCaretPosition(0);

            // setTitle tetap di bawah
            this.setTitle("Revisi CV - " + namaUser + " | Desain: " + namaDesain);
        }
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Gagal load data CV: " + e.getMessage());
        e.printStackTrace();
    }
}
//private void loadDataCV(int dataId) {
//    try {
//        java.sql.Connection conn = com.mycompany.dibuatincvnw.DBConnection.getKoneksi();
//        String sql = "SELECT * FROM data_diri WHERE id_data = ?";
//        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setInt(1, dataId);
//        java.sql.ResultSet rs = ps.executeQuery();
//
//        if (rs.next()) {
//            String namaDesain = rs.getString("pilihan_desain");
//            String namaUser   = rs.getString("nama");
//            String noteRevisi = rs.getString("note_revisi");
//            String pathCv     = rs.getString("path_cv");
//
//            // Tampilkan note konsultan di textArea
//            if (noteRevisi != null && !noteRevisi.isEmpty()) {
//                jTextArea1.setText("Note Konsultan:\n" + noteRevisi);
//            } else {
//                jTextArea1.setText("(Tidak ada note dari konsultan)");
//            }
//
//            // Tampilkan gambar CV yang sudah digenerate
//            if (pathCv != null && !pathCv.isEmpty()) {
//                // Coba render PDF sebagai gambar
//                tampilkanPdfSebagaiGambar(pathCv);
//            } else {
//                // Belum ada CV, tampilkan gambar template desain
//                String pathGambar = getPathGambarDesain(namaDesain);
//                try {
//                    java.awt.Image imgScale = new javax.swing.ImageIcon(pathGambar)
//                        .getImage().getScaledInstance(400, 516, java.awt.Image.SCALE_SMOOTH);
//                    gambarcv.setIcon(new javax.swing.ImageIcon(imgScale));
//                } catch (Exception e) {
//                    gambarcv.setText("Gambar tidak ditemukan");
//                }
//            }
//
//            this.setTitle("Revisi CV - " + namaUser + " | Desain: " + namaDesain);
//        }
//
//    } catch (Exception e) {
//        javax.swing.JOptionPane.showMessageDialog(this,
//            "Gagal load data CV: " + e.getMessage());
//        e.printStackTrace();
//    }
//}
private void tampilkanPdfSebagaiGambar(String namaFile) {
    try {
        // Path sesuai ChronologicalResume.generateFromDatabase()
        String fullPath = System.getProperty("user.home") + "/Documents/" + namaFile;
        
        java.io.File filePdf = new java.io.File(fullPath);
        if (!filePdf.exists()) {
            gambarcv.setText("File CV tidak ditemukan:\n" + fullPath);
            return;
        }

        org.apache.pdfbox.pdmodel.PDDocument doc = 
            org.apache.pdfbox.pdmodel.PDDocument.load(filePdf);
        org.apache.pdfbox.rendering.PDFRenderer renderer = 
            new org.apache.pdfbox.rendering.PDFRenderer(doc);
        
        java.awt.image.BufferedImage img = renderer.renderImageWithDPI(0, 120);
        doc.close();

        java.awt.Image scaled = img.getScaledInstance(400, 516, java.awt.Image.SCALE_SMOOTH);
        gambarcv.setIcon(new javax.swing.ImageIcon(scaled));
        gambarcv.setText(null);

    } catch (Exception e) {
        System.out.println("Gagal render PDF: " + e.getMessage());
        gambarcv.setText("Preview tidak tersedia");
    }
}
//private void loadDataCV(int dataId) {
//    try {
//        java.sql.Connection conn = com.mycompany.dibuatincvnw.DBConnection.getKoneksi();
//        String sql = "SELECT d.*, u.nama FROM data_diri d "
//                   + "JOIN user u ON d.id_user = u.id_user "
//                   + "WHERE d.id_data = ?";
//        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setInt(1, dataId);
//        java.sql.ResultSet rs = ps.executeQuery();
//
//        if (rs.next()) {
//            String namaDesain = rs.getString("pilihan_desain");
//            String namaUser   = rs.getString("nama");
//            String noteRevisi = rs.getString("note_revisi");
//
//            // Tampilkan note revisi dari konsultan di text area
//            if (noteRevisi != null && !noteRevisi.isEmpty()) {
//                jTextArea1.setText("Note Konsultan:\n" + noteRevisi);
//            } else {
//                jTextArea1.setText("");
//            }
//
//            // Load gambar preview CV sesuai desain yang dipilih user
//            String pathGambar = getPathGambarDesain(namaDesain);
//            try {
//                java.awt.Image imgScale = new javax.swing.ImageIcon(pathGambar)
//                    .getImage().getScaledInstance(400, 516, java.awt.Image.SCALE_SMOOTH);
//                gambarcv.setIcon(new javax.swing.ImageIcon(imgScale));
//            } catch (Exception e) {
//                System.out.println("Gambar desain tidak ditemukan: " + pathGambar);
//            }
//
//            // Update judul window
//            this.setTitle("Revisi CV - " + namaUser + " | Desain: " + namaDesain);
//        }
//
//    } catch (Exception e) {
//        javax.swing.JOptionPane.showMessageDialog(this, 
//            "Gagal load data CV: " + e.getMessage());
//        e.printStackTrace();
//    }
//}

private String getPathGambarDesain(String namaDesain) {
    String base = "src/main/java/com/mycompany/dibuatincv/images/";
    switch (namaDesain) {
        case "ChronologicalResume":  return base + "chronoUC.jpeg";
        case "project": return base + "projectUC.jpeg";
        case "Keisya":  return base + "keishaHV.jpeg";
        default:        return base + "";
    }
}

     
     
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanelUtama = new javax.swing.JPanel();
        gambarcv = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        kirimkepengguna = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtEditData = new javax.swing.JTextArea();
        btnGenerate = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        PanelUtama.setBackground(new java.awt.Color(255, 204, 204));
        PanelUtama.setPreferredSize(new java.awt.Dimension(1024, 773));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 3, 35)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Dibuatin.CV");

        kirimkepengguna.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        kirimkepengguna.setText("Kirim ke Pengguna");
        kirimkepengguna.addActionListener(this::kirimkepenggunaActionPerformed);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        txtEditData.setColumns(20);
        txtEditData.setRows(5);
        jScrollPane2.setViewportView(txtEditData);

        btnGenerate.setText("GENERATE");
        btnGenerate.addActionListener(this::btnGenerateActionPerformed);

        javax.swing.GroupLayout PanelUtamaLayout = new javax.swing.GroupLayout(PanelUtama);
        PanelUtama.setLayout(PanelUtamaLayout);
        PanelUtamaLayout.setHorizontalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(36, 36, 36)
                .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gambarcv, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(kirimkepengguna)
                .addGap(23, 23, 23))
            .addGroup(PanelUtamaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PanelUtamaLayout.setVerticalGroup(
            PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelUtamaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(56, 56, 56)
                .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelUtamaLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelUtamaLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(PanelUtamaLayout.createSequentialGroup()
                                .addGap(220, 220, 220)
                                .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(PanelUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(kirimkepengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(gambarcv, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(584, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, 1314, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, 1219, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void kirimkepenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kirimkepenggunaActionPerformed
        // TODO add your handling code here:
        int idData = com.mycompany.dibuatincv.pengguna.SessionManager.getInstance().getIdData();
    
    if (idData <= 0) {
        javax.swing.JOptionPane.showMessageDialog(this, "Tidak ada data yang dipilih!");
        return;
    }

    try {
        // Generate ulang CV
        String pathPdf = com.mycompany.dibuatincv.pengguna.ChronologicalResume
                            .generateFromDatabase(idData);
        String namaFile = new java.io.File(pathPdf).getName();

        // Update database: selesai (generate=1), simpan path baru
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        java.sql.PreparedStatement ps = conn.prepareStatement(
            "UPDATE data_diri SET generate = 1, path_cv = ? WHERE id_data = ?"
        );
        ps.setString(1, namaFile);
        ps.setInt(2, idData);
        ps.executeUpdate();

        javax.swing.JOptionPane.showMessageDialog(this,
            "CV berhasil di-generate ulang dan dikirim ke pengguna!\nTersimpan di:\n" + pathPdf,
            "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

        // Kembali ke dashboard
        DashboardAdmin dashboard = new DashboardAdmin();
        dashboard.setVisible(true);
        this.dispose();

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Gagal: " + e.getMessage());
        e.printStackTrace();
    }
//          try {
//        int idData = com.mycompany.dibuatincvnw.pengguna.SessionManager.getInstance().getIdData();
//        String catatanAdmin = jTextArea1.getText().trim();
//
//        java.sql.Connection conn = com.mycompany.dibuatincvnw.DBConnection.getKoneksi();
//
//        // Update status: generate=1 artinya sudah selesai direvisi & dikirim
//        String sql = "UPDATE data_diri SET generate = 1, note_revisi = ? WHERE id_data = ?";
//        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
//        ps.setString(1, catatanAdmin);
//        ps.setInt(2, idData);
//        ps.executeUpdate();
//
//        javax.swing.JOptionPane.showMessageDialog(this,
//            "CV berhasil dikirim ke pengguna!",
//            "Sukses",
//            javax.swing.JOptionPane.INFORMATION_MESSAGE);
//
//        // Kembali ke dashboard admin
//        DashboardAdmin dashboard = new DashboardAdmin();
//        dashboard.setVisible(true);
//        this.dispose();
//
//    } catch (Exception e) {
//        javax.swing.JOptionPane.showMessageDialog(this, 
//            "Gagal mengirim CV: " + e.getMessage());
//        e.printStackTrace();
//    }
//         try {
//        // ... (Kode kamu untuk menyimpan data atau mengirim cv ke pengguna) ...
//        
//        // 1. Tampilkan notifikasi sukses ke user
//        javax.swing.JOptionPane.showMessageDialog(this, 
//                "CV Sukses Dikirim ke pengguna!", 
//                "Sukses", 
//                javax.swing.JOptionPane.INFORMATION_MESSAGE);
//     
//        
//    } catch (Exception e) {
//        // Jika ada error saat proses kirim data
//        javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengirim cv: " + e.getMessage());
//    }

    }//GEN-LAST:event_kirimkepenggunaActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        // TODO add your handling code here:
        try {
        int idData = com.mycompany.dibuatincv.pengguna.SessionManager.getInstance().getIdData();
        String teks = txtEditData.getText();

        // Parse data dari textArea
        String nama         = ambilNilai(teks, "NAMA:");
        String alamat       = ambilNilai(teks, "ALAMAT:");
        String noWa         = ambilNilai(teks, "NO WA:");
        String email        = ambilNilai(teks, "EMAIL:");
        String pendidikan   = ambilBlok(teks, "PENDIDIKAN:", "TENTANG SAYA:");
        String tentangSaya  = ambilBlok(teks, "TENTANG SAYA:", "PENGALAMAN:");
        String pengalaman   = ambilBlok(teks, "PENGALAMAN:", "KEPEMIMPINAN:");
        String kepemimpinan = ambilBlok(teks, "KEPEMIMPINAN:", "SKILL:");
        String skill        = ambilBlok(teks, "SKILL:", "PROJEK:");
        String projek       = ambilBlok(teks, "PROJEK:", null);

        // Update database
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        java.sql.PreparedStatement ps = conn.prepareStatement(
            "UPDATE data_diri SET nama=?, alamat=?, no_wa=?, email=?, " +
            "pendidikan=?, tentang_saya=?, pengalaman=?, kepemimpinan=?, " +
            "keahlian=?, projek=? WHERE id_data=?"
        );
        ps.setString(1, nama);
        ps.setString(2, alamat);
        ps.setString(3, noWa);
        ps.setString(4, email);
        ps.setString(5, pendidikan);
        ps.setString(6, tentangSaya);
        ps.setString(7, pengalaman);
        ps.setString(8, kepemimpinan);
        ps.setString(9, skill);
        ps.setString(10, projek);
        ps.setInt(11, idData);
        ps.executeUpdate();

//        // Generate ulang PDF
//        String pathPdf = com.mycompany.dibuatincvnw.pengguna.ChronologicalResume
//                            .generateFromDatabase(idData);
//        String namaFile = new java.io.File(pathPdf).getName();
        // 1. Ambil pilihan desain awal dari database berdasarkan idData
String pilihanDesain = "";
String pathPdf = "";

try {
    // BENAR (Langsung gunakan variabel 'conn' yang sudah ada di atas)
    conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
    String sqlCekDesain = "SELECT pilihan_desain FROM data_diri WHERE id_data = ?";
    
    try (java.sql.PreparedStatement psCek = conn.prepareStatement(sqlCekDesain)) {
        psCek.setInt(1, idData);
        try (java.sql.ResultSet rsCek = psCek.executeQuery()) {
            if (rsCek.next()) {
                pilihanDesain = rsCek.getString("pilihan_desain");
            }
        }
    }
} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Gagal mengambil data desain: " + e.getMessage());
}

// 2. Generate ulang PDF berdasarkan desain yang didapatkan
if (pilihanDesain != null && !pilihanDesain.isEmpty()) {
    // Normalisasi teks (hapus spasi & jadikan huruf kecil) agar pengecekan akurat
    switch (pilihanDesain.toLowerCase().trim()) {
        case "chrono":
            pathPdf = com.mycompany.dibuatincv.pengguna.ChronologicalResume.generateFromDatabase(idData);
            break;
            
        case "keisya":
            // 💡 Sesuaikan dengan nama class generator desain Keisya Anda
            pathPdf = com.mycompany.dibuatincv.pengguna.keisya.generateFromDatabase(idData);
            break;
            
        case "project":
            // 💡 Sesuaikan dengan nama class generator desain Project Anda
            pathPdf = com.mycompany.dibuatincv.pengguna.project.generateFromDatabase(idData);
            break;
            
//        default:
//            // Jika tidak ada yang cocok, gunakan Chrono sebagai fallback default
//            pathPdf = com.mycompany.dibuatincv.pengguna.ChronologicalResume.generateFromDatabase(idData);
//            break;
    }
} else {
    // Jika data desain di DB kosong, Eror
//    pathPdf = com.mycompany.dibuatincv.pengguna.ChronologicalResume.generateFromDatabase(idData);
}

// 3. Ambil nama file dari path hasil generate terbaru
String namaFile = new java.io.File(pathPdf).getName();
        // Simpan path_cv (generate masih 0, belum dikirim ke user)
        java.sql.PreparedStatement ps2 = conn.prepareStatement(
            "UPDATE data_diri SET path_cv=? WHERE id_data=?"
        );
        ps2.setString(1, namaFile);
        ps2.setInt(2, idData);
        ps2.executeUpdate();

        // Refresh preview CV
        tampilkanPdfSebagaiGambar(namaFile);

        javax.swing.JOptionPane.showMessageDialog(this,
            "CV berhasil di-generate ulang!\nKlik 'Kirim ke Pengguna' jika sudah oke.",
            "Sukses", javax.swing.JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this, "Gagal generate: " + e.getMessage());
        e.printStackTrace();
    }
}

// Helper parse satu baris
private String ambilNilai(String teks, String key) {
    for (String line : teks.split("\n")) {
        if (line.startsWith(key)) {
            return line.substring(key.length()).trim();
        }
    }
    return "";
}

// Helper parse multi baris
private String ambilBlok(String teks, String keyMulai, String keySelesai) {
    String[] lines = teks.split("\n");
    StringBuilder result = new StringBuilder();
    boolean mulai = false;
    for (String line : lines) {
        if (line.startsWith(keyMulai)) { mulai = true; continue; }
        if (mulai) {
            if (keySelesai != null && line.startsWith(keySelesai)) break;
            result.append(line).append("\n");
        }
    }
    return result.toString().trim();
    }//GEN-LAST:event_btnGenerateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new revisiCV().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelUtama;
    private javax.swing.JButton btnGenerate;
    private javax.swing.JLabel gambarcv;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton kirimkepengguna;
    private javax.swing.JTextArea txtEditData;
    // End of variables declaration//GEN-END:variables
}
