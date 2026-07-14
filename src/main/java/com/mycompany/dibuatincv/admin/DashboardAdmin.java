/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.admin;

import com.mycompany.dibuatincv.pengguna.SessionManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 *
 * @author Lenovo
 */
public class DashboardAdmin extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(DashboardAdmin.class.getName());
    
private void loadNotifications() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBackground(Color.WHITE);

    try {
        java.sql.Connection conn = com.mycompany.dibuatincv.DBConnection.getKoneksi();

        // ── SECTION HEADER: REVISI CV ──
        JLabel lblRevisiHeader = new JLabel(" 🔄  REVISI CV");
        lblRevisiHeader.setFont(new Font("Arial", Font.BOLD, 11));
        lblRevisiHeader.setForeground(new Color(120, 0, 0));
        lblRevisiHeader.setOpaque(true);
        lblRevisiHeader.setBackground(new Color(232, 232, 232));
        lblRevisiHeader.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(187, 187, 187)));
        lblRevisiHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        lblRevisiHeader.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(lblRevisiHeader);

        // Query khusus REVISI (generate == 2)
        String sqlRevisi = "SELECT id_data, nama, pilihan_desain, generate, note_revisi "
                         + "FROM data_diri WHERE generate = 2 ORDER BY id_data DESC";
        java.sql.PreparedStatement psRevisi = conn.prepareStatement(sqlRevisi);
        java.sql.ResultSet rsRevisi = psRevisi.executeQuery();

        boolean adaRevisi = false;
        while (rsRevisi.next()) {
            adaRevisi = true;
            buatPanelNotif(mainPanel, rsRevisi, 2);
        }
        if (!adaRevisi) {
            JLabel lbl = new JLabel("  Tidak ada revisi.");
            lbl.setFont(new Font("Arial", Font.ITALIC, 12));
            lbl.setForeground(Color.GRAY);
            lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            mainPanel.add(lbl);
        }

        // ── SPACER ──
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // ── SECTION HEADER: CV BARU ──
        JLabel lblBaruHeader = new JLabel(" ✨  CV BARU");
        lblBaruHeader.setFont(new Font("Arial", Font.BOLD, 11));
        lblBaruHeader.setForeground(new Color(0, 100, 0));
        lblBaruHeader.setOpaque(true);
        lblBaruHeader.setBackground(new Color(232, 232, 232));
        lblBaruHeader.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(187, 187, 187)));
        lblBaruHeader.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        lblBaruHeader.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(lblBaruHeader);

        // Query khusus CV BARU (generate == 0)
        String sqlBaru = "SELECT id_data, nama, pilihan_desain, generate, note_revisi "
                       + "FROM data_diri WHERE generate = 0 ORDER BY id_data DESC";
        java.sql.PreparedStatement psBaru = conn.prepareStatement(sqlBaru);
        java.sql.ResultSet rsBaru = psBaru.executeQuery();

        boolean adaBaru = false;
        while (rsBaru.next()) {
            adaBaru = true;
            buatPanelNotif(mainPanel, rsBaru, 0);
        }
        if (!adaBaru) {
            JLabel lbl = new JLabel("  Tidak ada CV baru.");
            lbl.setFont(new Font("Arial", Font.ITALIC, 12));
            lbl.setForeground(Color.GRAY);
            lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            mainPanel.add(lbl);
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal load notifikasi: " + e.getMessage());
    }

    jScrollPanel.setViewportView(null);
    jScrollPanel.setViewportView(mainPanel);
    jScrollPanel.getViewport().revalidate();
    jScrollPanel.getViewport().repaint();
}
    private void buatPanelNotif(JPanel mainPanel, java.sql.ResultSet rs, int statusGen) throws Exception {
    int idData = rs.getInt("id_data");
    String namaUser = rs.getString("nama");
    String namaDesain = rs.getString("pilihan_desain");
    String catatanRevisi = rs.getString("note_revisi");
    final int statusGenFinal = statusGen;

    JPanel pnlNotif = new JPanel(new java.awt.BorderLayout(10, 5));
    pnlNotif.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));
    pnlNotif.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
    pnlNotif.setPreferredSize(new Dimension(300, 90));
    pnlNotif.setBackground(Color.WHITE);

    String judulNotif = (statusGenFinal == 2) ? "[ Revisi CV ]" : "[ CV Baru ]";
    String warnaTeks  = (statusGenFinal == 2) ? "#c0392b"       : "#1a6b2e";

    String htmlText = "<html><b><font color='" + warnaTeks + "'>" + judulNotif + "</font></b>"
                    + " &nbsp;<font color='gray'>" + namaDesain + "</font><br>"
                    + "<font size='3' color='#444444'>User: " + namaUser + "</font>";

    if (statusGenFinal == 2 && catatanRevisi != null && !catatanRevisi.isEmpty()) {
        String cuplikan = catatanRevisi.length() > 40
                        ? catatanRevisi.substring(0, 40) + "..." : catatanRevisi;
        htmlText += "<br><font size='2' color='gray'><i>Note Konsultan: \"" + cuplikan + "\"</i></font>";
    }
    htmlText += "</html>";

    javax.swing.JLabel lblTeks = new javax.swing.JLabel(htmlText);
    lblTeks.setFont(new Font("Arial", Font.PLAIN, 13));
    pnlNotif.add(lblTeks, java.awt.BorderLayout.CENTER);

    pnlNotif.putClientProperty("idData", idData);

    pnlNotif.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int dataId = (int) pnlNotif.getClientProperty("idData");
            try {
                java.sql.Connection c = com.mycompany.dibuatincv.DBConnection.getKoneksi();
                java.sql.PreparedStatement upd = c.prepareStatement(
                    "UPDATE data_diri SET baca = TRUE WHERE id_data = ?");
                upd.setInt(1, dataId);
                upd.executeUpdate();
            } catch (Exception ex) { ex.printStackTrace(); }

            com.mycompany.dibuatincv.pengguna.SessionManager.getInstance().setIdData(dataId);

            if (statusGenFinal == 2) {
                revisiCV detailRevisi = new revisiCV(dataId);
                detailRevisi.setLocationRelativeTo(null);
                detailRevisi.setVisible(true);
            } else {
                GenerateCV generatePage = new GenerateCV();
                generatePage.setLocationRelativeTo(null);
                generatePage.setVisible(true);
            }
        }
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            pnlNotif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            pnlNotif.setBackground(new Color(245, 245, 245));
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            pnlNotif.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            pnlNotif.setBackground(Color.WHITE);
        }
    });

    mainPanel.add(pnlNotif);
    mainPanel.add(Box.createRigidArea(new Dimension(0, 2)));
}

    

    /**
     * Creates new form DashboardAdmin
     */
    public DashboardAdmin() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    loadNotifications(); // load pertama kali

    // Auto-refresh setiap 10 detik
    javax.swing.Timer timerRefresh = new javax.swing.Timer(10000, e -> loadNotifications());
    timerRefresh.start();

    }
    
//    private void loadNotifications() {
//    JPanel mainPanel = new JPanel();
//    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//    
//    // Mengubah background dasar mainPanel menjadi putih agar seragam
//    mainPanel.setBackground(Color.WHITE); 
//
//    try {
//        java.sql.Connection conn = com.mycompany.dibuatincvnw.DBConnection.getKoneksi();
//
//        // Query mengambil data yang diperlukan termasuk note_revisi dan pilihan_desain
//        String sql = "SELECT id_data, nama, pilihan_desain, baca, generate, note_revisi "
//                   + "FROM data_diri "
//                   + "WHERE generate != 1 " // Hanya menampilkan yang BELUM selesai dibuat
//                   + "ORDER BY id_data DESC";
//
//        java.sql.PreparedStatement ps = conn.prepareStatement(sql);
//        java.sql.ResultSet rs = ps.executeQuery();
//
//        // DISINI LETAK PERUBAHANNYA: Mengganti seluruh isi blok while lama
//        while (rs.next()) {
//            int idData = rs.getInt("id_data");
//            String namaUser = rs.getString("nama");
//            String namaDesain = rs.getString("pilihan_desain");
//            String catatanRevisi = rs.getString("note_revisi");
//            
//            // Buat variabel final agar aman diakses di dalam MouseAdapter
//            final int statusGenFinal = rs.getInt("generate"); 
//
//            // 1. MEMBUAT PANEL NOTIFIKASI SATUAN
//            JPanel pnlNotif = new JPanel();
//            pnlNotif.setLayout(new java.awt.BorderLayout(10, 10));
//            pnlNotif.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
//            pnlNotif.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 90)); // Ukuran pas untuk 3 baris teks
//            pnlNotif.setPreferredSize(new java.awt.Dimension(300, 90));
//            
//            // SEMUA WARNA BACKGROUND SAMA (PUTIH)
//            pnlNotif.setBackground(Color.WHITE); 
//
//            // LOGIKA PENENTU TEKS JUDUL SAJA
//            String judulNotif = "";
//            if (statusGenFinal == 2) { 
//                judulNotif = "[ Revisi CV ]";
//            } else {
//                judulNotif = "[ CV Baru ]";
//            }
//
//            // 2. MEMBUAT TEKS NOTIFIKASI (Menggunakan HTML agar rapi dan mendukung Note Konsultan)
//            String htmlText = "<html><b>" + judulNotif + "</b> &nbsp;&nbsp;<font color='gray'>" + namaDesain + "</font><br>"
//                            + "<font size='3' color='#444444'>User: " + namaUser + "</font>";
//            
//            // Jika statusnya revisi, sisipkan note konsultan di bawah nama user
//            if (statusGenFinal == 2 && catatanRevisi != null && !catatanRevisi.isEmpty()) {
//                String cuplikanNote = catatanRevisi.length() > 40 ? catatanRevisi.substring(0, 40) + "..." : catatanRevisi;
//                htmlText += "<br><font size='2' color='gray'><i>Note Konsultan: \"" + cuplikanNote + "\"</i></font>";
//            }
//            
//            htmlText += "</html>";
//            
//            javax.swing.JLabel lblTeks = new javax.swing.JLabel(htmlText);
//            lblTeks.setFont(new Font("Arial", Font.PLAIN, 13));
//            pnlNotif.add(lblTeks, java.awt.BorderLayout.CENTER);
//
//            // Garis pembatas bawah antar baris notifikasi
//            pnlNotif.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
//
//            // Simpan ID Data ke properti panel agar bisa dibaca saat diklik
//            pnlNotif.putClientProperty("idData", idData);
//
//            // 3. MENAMBAHKAN AKSI KLIK & HOVER MOUSE
//            pnlNotif.addMouseListener(new java.awt.event.MouseAdapter() {
//                @Override
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    int dataId = (int) pnlNotif.getClientProperty("idData");
//
//                    // 1. Update status 'baca' di database secara background
//                    try {
//                        java.sql.Connection c = com.mycompany.dibuatincvnw.DBConnection.getKoneksi();
//                        java.sql.PreparedStatement upd = c.prepareStatement(
//                            "UPDATE data_diri SET baca = TRUE WHERE id_data = ?"
//                        );
//                        upd.setInt(1, dataId);
//                        upd.executeUpdate();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//
//                    // 2. Simpan id_data ke session manager
//                    com.mycompany.dibuatincvnw.pengguna.SessionManager.getInstance().setIdData(dataId);
//
//                    // 3. Logika Pengalihan Halaman Berdasarkan Status
//                    if (statusGenFinal == 2) {
//                        // Jika revisi, masuk ke halaman ketik manual milik admin
//                        revisiCV detailRevisi = new revisiCV(dataId);
//                        detailRevisi.setLocationRelativeTo(null);
//                        detailRevisi.setVisible(true);
//                    } else {
//                        // Jika data baru, langsung ke halaman cetak biasa
//                        GenerateCV generatePage = new GenerateCV();
//                        generatePage.setLocationRelativeTo(null);
//                        generatePage.setVisible(true);
//                    }
//                    
//                    // Opsional: Tutup DashboardAdmin jika tidak ingin menumpuk frame
//                    // DashboardAdmin.this.dispose();
//                }
//
//                @Override
//                public void mouseEntered(java.awt.event.MouseEvent evt) {
//                    pnlNotif.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
//                    pnlNotif.setBackground(new Color(245, 245, 245)); // Efek sorot abu-abu tipis saat didekati
//                }
//
//                @Override
//                public void mouseExited(java.awt.event.MouseEvent evt) {
//                    pnlNotif.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
//                    pnlNotif.setBackground(Color.WHITE); // Kembali ke putih bersih saat mouse pergi
//                }
//            });
//
//            // Masukkan panel satuan ke panel utama pembungkus
//            mainPanel.add(pnlNotif);
//            mainPanel.add(Box.createRigidArea(new Dimension(0, 2))); // Jarak tipis antar panel
//        }
//
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(this, "Gagal load notifikasi: " + e.getMessage());
//    }
//
//    // Mengosongkan viewport terlebih dahulu baru pasang mainPanel baru
//    jScrollPanel.setViewportView(null);
//    jScrollPanel.setViewportView(mainPanel);
//    jScrollPanel.getViewport().revalidate();
//    jScrollPanel.getViewport().repaint();
//}

//INI TAMPILKAN BAR NOTIFIKASI BARU 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPanel = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        notification = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        notif2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        notif3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        notif4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 40)); // NOI18N
        jLabel1.setText("Dashboard Admin");

        jLabel14.setFont(new java.awt.Font("Segoe UI Black", 2, 35)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Dibuatin.CV");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(399, 399, 399)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(29, 29, 29))
        );

        jPanel3.setBackground(new java.awt.Color(255, 204, 255));

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(1302, 773));

        jSeparator2.setBackground(new java.awt.Color(255, 204, 204));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        notification.setBackground(new java.awt.Color(255, 255, 255));
        notification.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notificationMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                notificationMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                notificationMouseExited(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel2.setText("[  CV Generate ]");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Nama user");

        jLabel11.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel11.setText("Desain 1 , Harvard");

        javax.swing.GroupLayout notificationLayout = new javax.swing.GroupLayout(notification);
        notification.setLayout(notificationLayout);
        notificationLayout.setHorizontalGroup(
            notificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(notificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(notificationLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11)))
                .addContainerGap(1008, Short.MAX_VALUE))
        );
        notificationLayout.setVerticalGroup(
            notificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notificationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notificationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        jSeparator3.setBackground(new java.awt.Color(255, 204, 204));
        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Nama user");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Desain 3 , Berkeley");

        jLabel15.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel15.setText("[  CV Generate ]");

        javax.swing.GroupLayout notif2Layout = new javax.swing.GroupLayout(notif2);
        notif2.setLayout(notif2Layout);
        notif2Layout.setHorizontalGroup(
            notif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notif2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(notif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(notif2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addComponent(jLabel7))
                .addContainerGap(1000, Short.MAX_VALUE))
        );
        notif2Layout.setVerticalGroup(
            notif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notif2Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(notif2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addContainerGap())
        );

        jSeparator6.setBackground(new java.awt.Color(255, 204, 204));
        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel6.setText("[  Revisi CV ]");

        jLabel12.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel12.setText("Desain 2 , Harvard");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Nama user");

        javax.swing.GroupLayout notif3Layout = new javax.swing.GroupLayout(notif3);
        notif3.setLayout(notif3Layout);
        notif3Layout.setHorizontalGroup(
            notif3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notif3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(notif3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(notif3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel12)))
                .addContainerGap(1010, Short.MAX_VALUE))
        );
        notif3Layout.setVerticalGroup(
            notif3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notif3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(notif3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12))
                .addGap(18, 18, 18)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSeparator7.setBackground(new java.awt.Color(255, 204, 204));
        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));

        jLabel5.setFont(new java.awt.Font("Arial", 1, 15)); // NOI18N
        jLabel5.setText("[  CV Generate ]");

        jLabel16.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel16.setText("Nama user");

        jLabel17.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel17.setText("Desain 2 , Harvard");

        javax.swing.GroupLayout notif4Layout = new javax.swing.GroupLayout(notif4);
        notif4.setLayout(notif4Layout);
        notif4Layout.setHorizontalGroup(
            notif4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notif4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(notif4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(notif4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(27, 27, 27)
                        .addComponent(jLabel17)))
                .addContainerGap(992, Short.MAX_VALUE))
        );
        notif4Layout.setVerticalGroup(
            notif4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(notif4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(notif4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator3)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1296, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(notification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(notif4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(notif3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(notif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addComponent(jSeparator6, javax.swing.GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(notification, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(notif2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(notif3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(notif4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(318, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(305, 305, 305)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(684, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(130, 130, 130))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(111, 111, 111))
        );

        jScrollPanel.setViewportView(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 594, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void notificationMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationMouseClicked
        // TODO add your handling code here:
       notification.setBackground(new java.awt.Color(255, 204, 204));
    
    GenerateCV halamanGenerate = new GenerateCV();
    
   
    halamanGenerate.setVisible(true);
    }//GEN-LAST:event_notificationMouseClicked

    private void notificationMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationMouseEntered
        // TODO add your handling code here:
        
    }//GEN-LAST:event_notificationMouseEntered

    private void notificationMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notificationMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_notificationMouseExited

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
        java.awt.EventQueue.invokeLater(() -> new DashboardAdmin().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPanel;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JPanel notif2;
    private javax.swing.JPanel notif3;
    private javax.swing.JPanel notif4;
    private javax.swing.JPanel notification;
    // End of variables declaration//GEN-END:variables

    private void tampilkanNotifikasiDinamis() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
} 
