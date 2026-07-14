/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.pengguna;

/**
 *
 * @author Acer
 */
import com.mycompany.dibuatincv.login;
import java.awt.Image;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class finalResult extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(finalResult.class.getName());

    /**
     * Creates new form finalResult
     */
    public finalResult() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    int pilihan = javax.swing.JOptionPane.showConfirmDialog(
                    null, 
                    "CV kamu belum diunduh, yakin mau keluar?",
                    "Konfirmasi Keluar",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE);
                
                if(pilihan == javax.swing.JOptionPane.YES_OPTION){
                    System.exit(0);
                } 
            }
        });
        
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                tampilkanPreviewPdf();
            }});
    }
    
    private void loadHasilCV() {
        int idData = SessionManager.getInstance().getIdData();
        if (idData <= 0) {
            lblStatus.setText("Tidak ada data CV.");
            return;
        }

        try {
            java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
            java.sql.PreparedStatement ps = conn.prepareStatement(
                "SELECT path_cv, nama, generate FROM data_diri WHERE id_data = ?"
            );
            ps.setInt(1, idData);
            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String pathCv  = rs.getString("path_cv");
                String nama    = rs.getString("nama");
                int statusGen  = rs.getInt("generate");

                if (statusGen == 1 && pathCv != null && !pathCv.isEmpty()) {
                    // CV sudah selesai, tampilkan preview
                    lblStatus.setText("CV " + nama + " sudah siap!");
                    tampilkanPreviewPdf();
                    btnDownload.setEnabled(true);
                } else {
                    // CV belum selesai diproses admin
                    lblStatus.setText("CV kamu sedang diproses oleh admin, mohon tunggu ya...");
                    btnDownload.setEnabled(false);
                }
            }

        } catch (Exception e) {
            lblStatus.setText("Gagal memuat CV: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void tampilkanPreviewPdf() {
    try {
        // 1. Ambil ID Data yang aktif dari SessionManager
        com.mycompany.dibuatincv.pengguna.SessionManager session = 
            com.mycompany.dibuatincv.pengguna.SessionManager.getInstance();
        int idData = session.getIdData();

        if (idData == 0) {
            lblPratinjau.setText("ID Data tidak ditemukan.");
            return;
        }

        // 2. Tarik nama file (path_cv) yang tersimpan di database
        String namaFilePdf = "";
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        String sql = "SELECT path_cv FROM data_diri WHERE id_data = ?";
        
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idData);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    namaFilePdf = rs.getString("path_cv");
                }
            }
        }

        // Validasi jika data di database kosong
        if (namaFilePdf == null || namaFilePdf.trim().isEmpty()) {
            lblPratinjau.setIcon(null);
            lblPratinjau.setText("Nama file CV belum terdaftar di database.");
            return;
        }

        // 3. Arahkan ke folder Documents laptop tempat file PDF disimpan
        String userHome = System.getProperty("user.home");
        File filePdf = new File(userHome + File.separator + "Documents" + File.separator + namaFilePdf);

        // 4. Jika file PDF fisik ditemukan, konversi ke Gambar
        if (filePdf.exists()) {
            // Load dokumen PDF menggunakan PDFBox
            PDDocument document = PDDocument.load(filePdf);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            
            // Render halaman pertama (indeks 0) dengan resolusi 100 DPI
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 100);
            
            // Tentukan ukuran gambar pratinjau (sesuaikan dengan ukuran JLabel Anda, misal 400x516)
            int labelWidth = lblPratinjau.getWidth() > 0 ? lblPratinjau.getWidth() : 350;
            int labelHeight = lblPratinjau.getHeight() > 0 ? lblPratinjau.getHeight() : 467;
            
            Image imgScale = bufferedImage.getScaledInstance(
                labelWidth, 
                labelHeight, 
                Image.SCALE_SMOOTH
            );
            
            // 5. Tampilkan gambar ke JLabel komponen pratinjau Anda
            lblPratinjau.setIcon(new ImageIcon(imgScale));
            lblPratinjau.setText(""); // Hapus teks loading/bawaan
            
            // Tutup dokumen berkas agar tidak mengunci file di sistem
            document.close();
            
        } else {
            // Penanganan jika file fisik tidak ada di folder Documents
            lblPratinjau.setIcon(null);
            lblPratinjau.setText("<html><center>Berkas PDF tidak ditemukan di folder Documents!<br>Nama: " + namaFilePdf + "</center></html>");
            lblPratinjau.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }

    } catch (Exception e) {
        e.printStackTrace();
        lblPratinjau.setText("Gagal memuat pratinjau gambar.");
    }
}
//    private void tampilkanPreviewPdf(String namaFile) {
//        try {
//            String fullPath = System.getProperty("user.home") + "/Documents/" + namaFile;
//            java.io.File filePdf = new java.io.File(fullPath);
//            
//            if (!filePdf.exists()) {
//                lblStatus.setText("File CV tidak ditemukan.");
//                return;
//            }
//
//            org.apache.pdfbox.pdmodel.PDDocument doc = 
//                org.apache.pdfbox.pdmodel.PDDocument.load(filePdf);
//            org.apache.pdfbox.rendering.PDFRenderer renderer = 
//                new org.apache.pdfbox.rendering.PDFRenderer(doc);
//            java.awt.image.BufferedImage img = renderer.renderImageWithDPI(0, 120);
//            doc.close();
//
//            java.awt.Image scaled = img.getScaledInstance(500, 650, java.awt.Image.SCALE_SMOOTH);
//            lblPreviewCV.setIcon(new javax.swing.ImageIcon(scaled));
//            lblPreviewCV.setText(null);
//
//        } catch (Exception e) {
//            lblPreviewCV.setText("Preview tidak tersedia.");
//            e.printStackTrace();
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnDownload = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        lblPratinjau = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        btnDownload.setText("export");
        btnDownload.addActionListener(this::btnDownloadActionPerformed);

        lblStatus.setFont(new java.awt.Font("Arial Black", 1, 36)); // NOI18N
        lblStatus.setText("CV SUDAH JADI");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 2, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dibuatin.CV");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(1033, Short.MAX_VALUE)
                .addComponent(btnDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(467, 467, 467)
                        .addComponent(lblPratinjau, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblStatus)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(lblStatus))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(60, 60, 60)
                .addComponent(lblPratinjau, javax.swing.GroupLayout.PREFERRED_SIZE, 467, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        // TODO add your handling code here:
        try {
        int idData = SessionManager.getInstance().getIdData();

        // ✅ Pakai try-with-resources agar otomatis ditutup setelah selesai
        try (java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
             java.sql.PreparedStatement ps = conn.prepareStatement(
                 "SELECT path_cv, nama FROM data_diri WHERE id_data = ?")) {

            ps.setInt(1, idData);

            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String namaFile = rs.getString("path_cv");
                    String nama     = rs.getString("nama");
                    String fullPath = System.getProperty("user.home") + "/Documents/" + namaFile;

                    javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                    fileChooser.setSelectedFile(new java.io.File("CV_" + nama + ".pdf"));
                    fileChooser.setFileFilter(
                        new javax.swing.filechooser.FileNameExtensionFilter("PDF Files", "pdf"));

                    int result = fileChooser.showSaveDialog(this);
                    if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        java.io.File dest = fileChooser.getSelectedFile();
                        java.nio.file.Files.copy(
                            java.nio.file.Paths.get(fullPath),
                            dest.toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING
                        );
                        javax.swing.JOptionPane.showMessageDialog(this,
                            "CV berhasil diunduh!\nTersimpan di: " + dest.getAbsolutePath(),
                            "Berhasil", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } // ✅ rs ditutup di sini

        } // ✅ conn & ps ditutup di sini
    } catch (Exception e) {
        javax.swing.JOptionPane.showMessageDialog(this,
            "Gagal mengunduh CV: " + e.getMessage());
        e.printStackTrace();
    }
        login halaman = new login();
        halaman.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDownloadActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new finalResult().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDownload;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPratinjau;
    private javax.swing.JLabel lblStatus;
    // End of variables declaration//GEN-END:variables
}
