/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.admin;

/**
 *
 * @author Lenovo
 */
import com.mycompany.dibuatincv.pengguna.ChronologicalResume;
import com.mycompany.dibuatincv.pengguna.SessionManager;
import com.mycompany.dibuatincv.pengguna.keisya;
import com.mycompany.dibuatincv.pengguna.project;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class GenerateCV extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GenerateCV.class.getName());

    /**
     * Creates new form GenerateCV
     */
    public GenerateCV() {
         initComponents();
         this.setExtendedState(JFrame.MAXIMIZED_BOTH);
       loadGambarDesain();
        loadDataDiri();
//       try {
//            ImageIcon icon1 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/admin/image/desainbaru.jpeg");
//            if (icon1.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
//                Image imgScale1 = icon1.getImage().getScaledInstance(400, 516, Image.SCALE_SMOOTH);
//                desainbaru.setIcon(new ImageIcon(imgScale1));
//            } else {
//                System.out.println("Gambar chronoUC.jpeg tidak ditemukan!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
     private void loadGambarDesain() {
        try {
            int idData = SessionManager.getInstance().getIdData();
            if (idData <= 0) {
                desainbaru.setText("Belum ada data dipilih");
                return;
            }

            java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
            java.sql.PreparedStatement ps = conn.prepareStatement(
                    "SELECT pilihan_desain FROM data_diri WHERE id_data = ?"
            );
            ps.setInt(1, idData);
            java.sql.ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String namaDesain = rs.getString("pilihan_desain");
                String namaFileGambar = mapNamaDesainKeFile(namaDesain);

                String path = "src/main/java/com/mycompany/dibuatincv/images/" + namaFileGambar;
                ImageIcon icon = new ImageIcon(path);
                if (icon.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                    Image imgScale = icon.getImage().getScaledInstance(350, 454, Image.SCALE_SMOOTH);
                    desainbaru.setIcon(new ImageIcon(imgScale));
                    desainbaru.setText(null);
                } else {
                    desainbaru.setIcon(null);
                    desainbaru.setText("Gambar desain '" + namaDesain + "' tidak ditemukan");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            desainbaru.setText("Gagal memuat gambar desain");
        }
    }
     private String mapNamaDesainKeFile(String namaDesain) {
        if (namaDesain == null) return "";
        switch (namaDesain.toLowerCase()) {
            case "chrono":   return "chronoUC.jpeg";
            case "keisya":   return "keishaHV.jpeg";
            case "project":  return "projectUC.jpeg";
            default:         return "";
        }
    }
     private void loadDataDiri() {
        int idData = SessionManager.getInstance().getIdData();
        if (idData <= 0) {
            txtPreview.setText("Tidak ada data yang dipilih.");
            return;
        }
 
        try {
            java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
            String sql = "SELECT nama, alamat, email, no_wa, pendidikan, tentang_saya, keahlian, pengalaman, kepemimpinan, pilihan_desain, projek "
                   + "FROM data_diri WHERE id_data = ?";
            java.sql.PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idData);
            java.sql.ResultSet rs = ps.executeQuery();
 
            if (rs.next()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Desain Pilihan : ").append(rs.getString("pilihan_desain")).append("\n\n");
                sb.append("Nama           : ").append(rs.getString("nama")).append("\n");
                sb.append("Alamat         : ").append(rs.getString("alamat")).append("\n");
                sb.append("Email          : ").append(rs.getString("email")).append("\n");
                sb.append("No. WA         : ").append(rs.getString("no_wa")).append("\n\n");
                sb.append("Pendidikan     :\n").append(rs.getString("pendidikan")).append("\n\n");
                sb.append("Tentang Saya   :\n").append(rs.getString("tentang_saya")).append("\n\n");
                sb.append("Pengalaman     :\n").append(rs.getString("pengalaman")).append("\n\n");
               sb.append("Leadership     :\n").append(rs.getString("kepemimpinan")).append("\n\n"); // Menggunakan kolom 'kepemimpinan'
            sb.append("Skill          :\n").append(rs.getString("keahlian")).append("\n");       // Menggunakan kolom 'keahlian'
            sb.append("Projek         :\n").append(rs.getString("projek")).append("\n");
 
                txtPreview.setText(sb.toString());
                txtPreview.setCaretPosition(0);
            } else {
                txtPreview.setText("Data tidak ditemukan untuk id_data = " + idData);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
            txtPreview.setText("Gagal memuat data: " + e.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        gambardesain = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        desainbaru = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPreview = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1024, 773));

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 3, 35)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Dibuatin.CV");

        jButton1.setFont(new java.awt.Font("Arial", 0, 15)); // NOI18N
        jButton1.setText("Generate CV");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        txtPreview.setColumns(20);
        txtPreview.setRows(5);
        jScrollPane1.setViewportView(txtPreview);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(gambardesain)
                .addGap(18, 18, 18)
                .addComponent(desainbaru, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 174, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(187, 187, 187))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(gambardesain))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(desainbaru, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(926, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int idData = SessionManager.getInstance().getIdData();
        String pilihan = SessionManager.getInstance().getNamaDesain();

        if (idData <= 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang dipilih! Silakan klik notifikasi terlebih dahulu.");
            return;
        }
         

        try {
            java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        java.sql.PreparedStatement psCek = conn.prepareStatement(
            "SELECT pilihan_desain FROM data_diri WHERE id_data = ?"
        );
        psCek.setInt(1, idData);
        java.sql.ResultSet rsCek = psCek.executeQuery();
        
         pilihan = "";
        if (rsCek.next()) {
            pilihan = rsCek.getString("pilihan_desain");
        }
        
        if (pilihan == null || pilihan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Desain tidak ditemukan di database!");
            return;
        }
            
            String pathPdf = "";
            // 2. Generate PDF berdasarkan id_data tersebut
            if (pilihan.equalsIgnoreCase("chrono")) {
                pathPdf = ChronologicalResume.generateFromDatabase(idData);
            } else if (pilihan.equalsIgnoreCase("keisya")) {
                pathPdf = keisya.generateFromDatabase(idData);
            } else {
                pathPdf = project.generateFromDatabase(idData);
            }

// Ambil nama file saja dari path lengkap
            String namaFile = new java.io.File(pathPdf).getName();

// Update status generate DAN simpan path_cv ke database
            //java.sql.Connection conn = com.mycompany.dibuatincvnw.DBConnection.getKoneksi();
            java.sql.PreparedStatement ps = conn.prepareStatement(
                    "UPDATE data_diri SET generate = TRUE, path_cv = ? WHERE id_data = ?"
            );
            ps.setString(1, namaFile);
            ps.setInt(2, idData);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "CV berhasil di-generate!\nTersimpan di:\n" + pathPdf);
            KirimKonsultan kirimForm = new KirimKonsultan();
            kirimForm.setLocationRelativeTo(null);
            kirimForm.setVisible(true);
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal generate CV: " + e.getMessage());
            e.printStackTrace();
        }
//       int idData = SessionManager.getInstance().getIdData();
//
//    if (idData <= 0) {
//        JOptionPane.showMessageDialog(this, "Tidak ada data yang dipilih! Silakan klik notifikasi terlebih dahulu.");
//        return;
//    }
//
//    try {
//        // 2. Generate PDF berdasarkan id_data tersebut
//       String pathPdf = ChronologicalResume.generateFromDatabase(idData);
//
//// Ambil nama file saja dari path lengkap
//String namaFile = new java.io.File(pathPdf).getName();
//
//// Update status generate DAN simpan path_cv ke database
//java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
//java.sql.PreparedStatement ps = conn.prepareStatement(
//    "UPDATE data_diri SET generate = TRUE, path_cv = ? WHERE id_data = ?"
//);
//ps.setString(1, namaFile);
//ps.setInt(2, idData);
//ps.executeUpdate();
//
//        JOptionPane.showMessageDialog(this, "CV berhasil di-generate!\nTersimpan di:\n" + pathPdf);
//        KirimKonsultan kirimForm = new KirimKonsultan();
//        kirimForm.setLocationRelativeTo(null); 
//        kirimForm.setVisible(true);            
//        this.dispose();
//
//    } catch (Exception e) {
//        JOptionPane.showMessageDialog(this, "Gagal generate CV: " + e.getMessage());
//        e.printStackTrace();
//    }
    
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
        //</editor-fold>
 
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            GenerateCV halamanCV = new GenerateCV();
            halamanCV.setLocationRelativeTo(null);
            halamanCV.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel desainbaru;
    private javax.swing.JLabel gambardesain;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtPreview;
    // End of variables declaration//GEN-END:variables
}
