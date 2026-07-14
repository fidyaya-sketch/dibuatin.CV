/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.admin;

/**
 *
 * @author Lenovo
 */
import com.mycompany.dibuatincv.konsultan.konsultan;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class KirimKonsultan extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(KirimKonsultan.class.getName());
//    private String pathFileCV = "";
    /**
     * Creates new form GenerateCV
     */
    public KirimKonsultan() {
    initComponents();
    this.setExtendedState(JFrame.MAXIMIZED_BOTH);

    
    // 🔥 TAMBAHAN: Panggil pratinjau setelah JFrame benar-benar terbuka di layar
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowOpened(java.awt.event.WindowEvent e) {
                tampilkanPratinjauCV();
            }
        });
    
}
  
    private void tampilkanPratinjauCV() {
    try {
        // 1. Ambil ID Data yang aktif dari session
        com.mycompany.dibuatincv.pengguna.SessionManager session = 
            com.mycompany.dibuatincv.pengguna.SessionManager.getInstance();
        int idData = session.getIdData();

        if (idData == 0) {
            hasilgenerate.setText("ID Data tidak ditemukan.");
            return;
        }

        // 2. Ambil nama file asli dari database
        String namaFilePdf = "";
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        String sqlCek = "SELECT path_cv FROM data_diri WHERE id_data = ?";
        
        try (java.sql.PreparedStatement psCek = conn.prepareStatement(sqlCek)) {
            psCek.setInt(1, idData);
            try (java.sql.ResultSet rsCek = psCek.executeQuery()) {
                if (rsCek.next()) {
                    namaFilePdf = rsCek.getString("path_cv");
                }
            }
        }

        if (namaFilePdf == null || namaFilePdf.trim().isEmpty()) {
            hasilgenerate.setIcon(null);
            hasilgenerate.setText("Nama file PDF di database masih kosong.");
            return; 
        }

        // 3. 🔥 PERBAIKAN UTAMA: Arahkan langsung ke folder Documents Laptop
        String userHome = System.getProperty("user.home"); // Mengambil "C:\Users\Lenovo"
        File filePdf = new File(userHome + File.separator + "Documents" + File.separator + namaFilePdf);

        // 4. Cek apakah file fisik PDF benar-benar ada di folder Documents
        if (filePdf.exists()) {
            
            // 5. Render halaman pertama PDF menjadi gambar (Apache PDFBox)
            PDDocument document = PDDocument.load(filePdf);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(0, 100);
            
            // Tentukan ukuran pratinjau panel (Gunakan ukuran mutlak komponen Anda: 400x516)
            int labelWidth = hasilgenerate.getWidth() > 0 ? hasilgenerate.getWidth() : 400;
            int labelHeight = hasilgenerate.getHeight() > 0 ? hasilgenerate.getHeight() : 516;
            
            Image imgScale = bufferedImage.getScaledInstance(
                labelWidth, 
                labelHeight, 
                Image.SCALE_SMOOTH
            );
            
            // 6. Tampilkan ke JLabel 'hasilgenerate'
            hasilgenerate.setIcon(new ImageIcon(imgScale));
            hasilgenerate.setText(""); 
            
            document.close(); // Tutup dokumen agar tidak mengunci file
            
        } else {
            // Jika file tidak ditemukan, tampilkan lokasi pencariannya agar mudah di-crosscheck
            hasilgenerate.setIcon(null);
            hasilgenerate.setText("<html><center>File tidak ditemukan di Documents!<br>Nama File: <b>" 
                    + namaFilePdf + "</b><br>Dicari di: " + filePdf.getAbsolutePath() + "</center></html>");
            hasilgenerate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
        
    } catch (Exception e) {
        logger.log(java.util.logging.Level.SEVERE, "Gagal memuat pratinjau", e);
        hasilgenerate.setText("Gagal memuat pratinjau: " + e.getMessage());
    }
}

//private void tampilkanPratinjauCV() {
//        try {
//            // Mengambil instance session (sesuaikan package jika diperlukan)
//            com.mycompany.dibuatincvnw.pengguna.SessionManager session = com.mycompany.dibuatincvnw.pengguna.SessionManager.getInstance();
//            
//            // Contoh jika kamu menyimpan path pratinjau gambar di SessionManager setelah generate
//            // Jika belum ada, kita pakai fallback file default pratinjau kamu
//            String fileGambar = "src/main/java/com/mycompany/dibuatincv/pengguna/image/''.jpeg"; 
//            
//            File f = new File(fileGambar);
//            if (f.exists()) {
//                ImageIcon icon1 = new ImageIcon(fileGambar);
//                Image imgScale1 = icon1.getImage().getScaledInstance(hasilgenerate.getWidth(), hasilgenerate.getHeight(), Image.SCALE_SMOOTH);
//                hasilgenerate.setIcon(new ImageIcon(imgScale1));
//            } else {
//                hasilgenerate.setText("Pratinjau CV (PDF/Gambar) Berhasil Dibuat!");
//                hasilgenerate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//            }
//        } catch (Exception e) {
//            logger.log(java.util.logging.Level.SEVERE, "Gagal memuat pratinjau", e);
//        }}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        hasilgenerate = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1302, 897));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 3, 35)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Dibuatin.CV");

        jButton1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jButton1.setText("Kirim Konsultan");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 465, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(132, 132, 132))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(hasilgenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(431, 431, 431))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(hasilgenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, 778, 778, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
           // TODO add your handling code here:
           try {
        
        com.mycompany.dibuatincv.pengguna.SessionManager session = com.mycompany.dibuatincv.pengguna.SessionManager.getInstance();
            int idData = session.getIdData();

            if (idData == 0) {
                JOptionPane.showMessageDialog(this, "ID Data kosong! Silakan isi formulir kembali.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 2. Hubungkan ke Database dan update kolom 'generate' menjadi true (1)
            java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
            String sqlUpdate = "UPDATE data_diri SET generate = ? WHERE id_data = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sqlUpdate);
            
            ps.setBoolean(1, true); // Menandakan berkas dikirim/selesai digenerate
            ps.setInt(2, idData);
            
            int statusSukses = ps.executeUpdate();

            if (statusSukses > 0) {
                // 3. Notifikasi sukses kirim berkas
                JOptionPane.showMessageDialog(this, 
                        "CV Berhasil dikirim ke Konsultan!", 
                        "Pengiriman Sukses", 
                        JOptionPane.INFORMATION_MESSAGE);
                
                // 4. Buka Dashboard Konsultan secara langsung
                // Silakan ganti 'DashboardKonsultan' di bawah ini dengan nama JFrame halaman konsultanmu yang sebenarnya
                DashboardAdmin halamanKonsultan = new DashboardAdmin();
                halamanKonsultan.setVisible(true);
                
                // 5. Menutup frame KirimKonsultan yang aktif saat ini
                this.dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Gagal memperbarui berkas. Data tidak ditemukan.", "Error", JOptionPane.WARNING_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Koneksi bermasalah: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

    }//GEN-LAST:event_jButton1ActionPerformed

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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new KirimKonsultan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hasilgenerate;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
