/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.konsultan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Acer
 */
public class konsultan extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(konsultan.class.getName());

    konsultan(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private void loadNotifications() {
    // 1. Buat panel utama yang akan menampung semua notifikasi
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(new Color(255, 204, 204)); // Samakan dengan tema background

    try {
        // 2. Ambil data dari database yang status 'generate' sudah TRUE (berhasil dikirim)
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();
        String sql = "SELECT id_data, nama, pilihan_desain FROM data_diri WHERE generate = TRUE ORDER BY id_data DESC";
        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
        java.sql.ResultSet rs = ps.executeQuery();

        boolean adaData = false;

        // 3. Looping berdasarkan data riil dari database
        while (rs.next()) {
            adaData = true;
            final int idData = rs.getInt("id_data");
            String namaUser = rs.getString("nama");
            String desain = rs.getString("pilihan_desain");

            // Buat panel notifikasi baru tiap baris database ditemukan
            JPanel pnlNotification = new JPanel();
            pnlNotification.setLayout(new BorderLayout());
            pnlNotification.setBackground(new Color(240, 240, 240));
            pnlNotification.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            // Atur ukuran komponen agar rapi
            pnlNotification.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
            pnlNotification.setPreferredSize(new Dimension(300, 60));

            // Tambahkan konten dinamis berdasarkan database
            JLabel lblPesan = new JLabel("<html>&nbsp;&nbsp;&nbsp;<b>[ New CV ]</b><br>&nbsp;&nbsp;User: " + namaUser + " | Desain: " + desain + "</html>");
            lblPesan.setFont(new Font("Arial", Font.PLAIN, 12));
            pnlNotification.add(lblPesan, BorderLayout.CENTER);

            pnlNotification.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Tambahkan MouseListener untuk mendeteksi klik notifikasi
            pnlNotification.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Pindah ke halaman revisi dengan membawa idData yang diklik
                    bukaHalamanDetail(idData);
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    pnlNotification.setBackground(new Color(225, 225, 225)); // Efek hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    pnlNotification.setBackground(new Color(240, 240, 240)); // Balik normal
                }
            });

            // Masukkan ke panel utama
            mainPanel.add(pnlNotification);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        }

        // Jika ternyata database kosong / belum ada CV yang dikirim
        if (!adaData) {
            JLabel lblKosong = new JLabel("Belum ada CV yang dikirim oleh pengguna.");
            lblKosong.setFont(new Font("Arial", Font.ITALIC, 14));
            lblKosong.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            mainPanel.add(lblKosong);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JLabel lblError = new JLabel("Gagal memuat notifikasi: " + e.getMessage());
        mainPanel.add(lblError);
    }

    // 4. Masukkan panel utama ke dalam JScrollPane
    jScrollPane1.setViewportView(mainPanel);

    // 5. Refresh komponen grafik
    jScrollPane1.revalidate();
    jScrollPane1.repaint();
}
//private void loadNotifications() {
//        // 1. Buat panel utama yang akan menampung semua notifikasi
//        JPanel mainPanel = new JPanel();
//
//        // Menggunakan BoxLayout agar panel notifikasi tersusun vertikal (dari atas ke bawah)
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//
//        // 2. Looping variable untuk membuat 5 JPanel Notifikasi
//        for (int i = 1; i <= 100; i++) {
//            final int notificationId = i;
//            // Buat panel notifikasi baru setiap kali loop berjalan
//            JPanel pnlNotification = new JPanel();
//            pnlNotification.setLayout(new BorderLayout());
//            pnlNotification.setBackground(new Color(240, 240, 240));
//            pnlNotification.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY)); // Garis pembatas bawah
//
//            // Atur ukuran maksimum agar panel tidak melar secara vertikal
//            pnlNotification.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
//            pnlNotification.setPreferredSize(new Dimension(300, 60));
//
//            // Tambahkan konten ke dalam panel notifikasi (Contoh: Label Teks)
//            JLabel lblPesan = new JLabel("<html>&nbsp;&nbsp;&nbsp;[ New CV ]<br>&nbsp;&nbsp;Nama Desain, Berkeley</html>");
//            lblPesan.setFont(new Font("Arial", Font.PLAIN, 12));
//            pnlNotification.add(lblPesan, BorderLayout.CENTER);
//
//            pnlNotification.setCursor(new Cursor(Cursor.HAND_CURSOR));
//
//        // 2. Menambahkan MouseListener untuk mendeteksi klik
//            pnlNotification.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                // Panggil method untuk berpindah halaman
//                bukaHalamanDetail();
//            }
//
//            // Opsional: Memberikan efek warna saat mouse masuk/keluar (hover effect)
//            @Override
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                pnlNotification.setBackground(new Color(225, 225, 225)); // Warna agak gelap saat di-hover
//            }
//
//            @Override
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                pnlNotification.setBackground(new Color(240, 240, 240)); // Kembali ke warna semula
//            }
//        });
//        //pnlNotification.add(lblPesan, BorderLayout.CENTER);
//
//            // 3. Masukkan panel notifikasi ke dalam panel utama
//            mainPanel.add(pnlNotification);
//
//            // Beri sedikit jarak antar panel
//            mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
//        }
//
//        // 4. Masukkan panel utama ke dalam JScrollPane
//        // Catatan: Ganti 'jScrollPane1' sesuai dengan nama variabel JScrollPane Anda di NetBeans
//        jScrollPane1.setViewportView(mainPanel);
//
//        // 5. Refresh JScrollPane agar perubahan langsung muncul
//        jScrollPane1.revalidate();
//        jScrollPane1.repaint();
//    }
    /**
     * Creates new form konsultan
     */
    public konsultan() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loadNotifications();
    }
    private void bukaHalamanDetail(int id) {
    // Contoh Logika: Membuka Frame Baru bernama 'HalamanDetail'
    // Kamu bisa mengirimkan ID data/notifikasi ke frame baru tersebut jika diperlukan
    review halamanUtama = new review(id); 
    halamanUtama.setLocationRelativeTo(null);
    halamanUtama.setVisible(true);
    
    // Jika ingin menutup/menyembunyikan dashboard saat ini:
    this.dispose(); 
}

//    private void bukaHalamanDetail(int id) {
//    // Contoh Logika: Membuka Frame Baru bernama 'HalamanDetail'
//    // Kamu bisa mengirimkan ID data/notifikasi ke frame baru tersebut jika diperlukan
//    revisi halamanUtama = new revisi(id); 
//    halamanUtama.setVisible(true);
//    
//    // Jika ingin menutup/menyembunyikan dashboard saat ini:
//    this.dispose(); 
//}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 40)); // NOI18N
        jLabel1.setText("Dashboard Konsultan");

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 2, 35)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Dibuatin.CV");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(463, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(448, 448, 448))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(59, 59, 59))
        );

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 581, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.awt.EventQueue.invokeLater(() -> new konsultan().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
