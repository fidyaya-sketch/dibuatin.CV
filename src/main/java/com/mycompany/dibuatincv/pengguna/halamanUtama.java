/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.dibuatincv.pengguna;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/**
 *
 * @author Acer
 */
public class halamanUtama extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(halamanUtama.class.getName());

    /**
     * Creates new form login
     */
    public halamanUtama() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    int pilihan = javax.swing.JOptionPane.showConfirmDialog(
                    null, 
                    "Kamu belum buat CV, yakin mau keluar?",
                    "Konfirmasi Keluar",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE);
                
                if(pilihan == javax.swing.JOptionPane.YES_OPTION){
                    System.exit(0);
                } 
            }
        });
                
        
                // Kode untuk Gambar 1 (Chrono)
        try {
            ImageIcon icon1 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/images/chronoUC.jpeg");
            if (icon1.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                Image imgScale1 = icon1.getImage().getScaledInstance(350, 450, Image.SCALE_SMOOTH);
                chrono.setIcon(new ImageIcon(imgScale1));
            } else {
                System.out.println("Gambar chronoUC.png tidak ditemukan!");
            }
            
            int idUser = 1; // Sesuaikan dengan cara kamu mendapatkan ID user yang sedang aktif
    String pathPdf = com.mycompany.dibuatincv.pengguna.ChronologicalResume.generateFromDatabase(idUser);
    System.out.println("PDF Otomatis digenerate di: " + pathPdf);
    
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Kode untuk Gambar 2 (Hybrid)
        try {
            ImageIcon icon2 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/images/hybridUC.jpeg");
            if (icon2.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                Image imgScale2 = icon2.getImage().getScaledInstance(350, 454, Image.SCALE_SMOOTH);
                hybrid.setIcon(new ImageIcon(imgScale2));
            } else {
                System.out.println("Gambar hybridUC.jpeg tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Kode untuk Gambar 4 (Project)
        try {
            ImageIcon icon4 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/images/projectUC.jpeg");
            if (icon4.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                Image imgScale4 = icon4.getImage().getScaledInstance(350, 451, Image.SCALE_SMOOTH);
                project.setIcon(new ImageIcon(imgScale4));
            } else {
                System.out.println("Gambar projectUC.png tidak ditemukan!");
            }
            int idUser = 1;
            String pathPdf = com.mycompany.dibuatincv.pengguna.project.generateFromDatabase(idUser);
            System.out.println("PDF Otomatis digenerate di: " + pathPdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Kode untuk gambar 3 (Keisha)
        try {
            ImageIcon icon3 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/images/keishaHV.jpeg");
            if (icon3.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                Image imgScale3 = icon3.getImage().getScaledInstance(350, 454, Image.SCALE_SMOOTH);
                keisha.setIcon(new ImageIcon(imgScale3));
            } else {
                System.out.println("Gambar keishaHV.jpeg tidak ditemukan!");
            }
            int idUser = 1;
            String pathPdf = com.mycompany.dibuatincv.pengguna.keisya.generateFromDatabase(idUser);
            System.out.println("PDF Otomatis digenerate di: " + pathPdf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Kode untuk gambar 5 (Ellen)
        try {
            ImageIcon icon5 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/images/benHV.jpeg");
            if (icon5.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                Image imgScale5 = icon5.getImage().getScaledInstance(350, 454, Image.SCALE_SMOOTH);
                ben.setIcon(new ImageIcon(imgScale5));
            } else {
                System.out.println("Gambar benHV.jpeg tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Kode untuk gambar 6 (Magda)
        try {
            ImageIcon icon6 = new ImageIcon("src/main/java/com/mycompany/dibuatincv/images/viditaHV.jpeg");
            if (icon6.getImageLoadStatus() == java.awt.MediaTracker.COMPLETE) {
                Image imgScale6 = icon6.getImage().getScaledInstance(350, 454, Image.SCALE_SMOOTH);
                vidita.setIcon(new ImageIcon(imgScale6));
            } else {
                System.out.println("Gambar viditaHV.jpeg tidak ditemukan!");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        chrono = new javax.swing.JLabel();
        hybrid = new javax.swing.JLabel();
        keisha = new javax.swing.JLabel();
        project = new javax.swing.JLabel();
        ben = new javax.swing.JLabel();
        vidita = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 2, 40)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dibuatin.CV");

        jScrollPane1.setBackground(new java.awt.Color(255, 204, 204));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setRowHeaderView(null);

        jPanel2.setBackground(new java.awt.Color(255, 204, 204));
        jPanel2.setPreferredSize(new java.awt.Dimension(900, 1700));

        chrono.setMaximumSize(new java.awt.Dimension(350, 454));
        chrono.setMinimumSize(new java.awt.Dimension(204, 264));
        chrono.setPreferredSize(new java.awt.Dimension(204, 264));
        chrono.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chronoMouseClicked(evt);
            }
        });

        hybrid.setMaximumSize(new java.awt.Dimension(204, 264));
        hybrid.setMinimumSize(new java.awt.Dimension(204, 264));
        hybrid.setPreferredSize(new java.awt.Dimension(204, 264));
        hybrid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hybridMouseClicked(evt);
            }
        });

        keisha.setMaximumSize(new java.awt.Dimension(204, 263));
        keisha.setMinimumSize(new java.awt.Dimension(204, 263));
        keisha.setPreferredSize(new java.awt.Dimension(204, 263));
        keisha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keishaMouseClicked(evt);
            }
        });

        project.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectMouseClicked(evt);
            }
        });

        ben.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                benMouseClicked(evt);
            }
        });

        vidita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viditaMouseClicked(evt);
            }
        });

        jLabel2.setText("\"Mengenal CV ATS Friendly: Format dokumen yang dirancang khusus agar mudah dibaca oleh Applicant Tracking System, yaitu sistem perangkat lunak otomatis perusahaan");
        jLabel2.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Segoe UI Symbol", 3, 18)); // NOI18N
        jLabel3.setText("Pilihan template lainnya segera hadir, stay tune ya!<3");

        jLabel4.setText("Template ini bergaya korporat");

        jLabel5.setText("Desain ini fokus pada kejelasan teks dengan");

        jLabel6.setText("sentuhan tata letak yang natural dan profesional.");

        jLabel7.setText("Template ini cocok untuk fresh graduate");

        jLabel9.setText("Template ini memiliki variasi pada visualnya");

        jLabel10.setText("Pemanfaatan elemen grafis kecil untuk memisahkan");

        jLabel11.setText("setiap bagian agar dokumen terasa lebih dinamis dan elegan.");

        jLabel8.setText("Penggunaan estetika tradisional yang rapi");

        jLabel12.setText("menciptakan kesan konvensional dan berwibawa.");

        jLabel13.setText("yang berfungsi memindai, menyaring, dan mengurutkan dokumen lamaran secara cepat, praktis, dan efisien berdasarkan kata kunci yang paling sesuai dengan posisi lowongan.\"");
        jLabel13.setToolTipText("");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chrono, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(hybrid, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(project, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ben, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel11))
                                .addGap(94, 94, 94))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(keisha, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(vidita, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 323, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 942, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(project, javax.swing.GroupLayout.PREFERRED_SIZE, 451, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chrono, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(keisha, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)))
                .addGap(58, 58, 58)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vidita, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hybrid, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ben, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addContainerGap(522, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1302, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 992, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1004, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentResized

    private void viditaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viditaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_viditaMouseClicked

    private void benMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_benMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_benMouseClicked

    private void projectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectMouseClicked
        // TODO add your handling code here:
        SessionManager.getInstance().setNamaDesain("project");
        formulir form = new formulir();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_projectMouseClicked

    private void keishaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keishaMouseClicked
        // TODO add your handling code here:
        SessionManager.getInstance().setNamaDesain("keisya");
        formulir form = new formulir();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_keishaMouseClicked

    private void hybridMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hybridMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_hybridMouseClicked

    private void chronoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chronoMouseClicked
        // TODO add your handling code here:
        SessionManager.getInstance().setNamaDesain("Chrono");
        formulir form = new formulir();
        form.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_chronoMouseClicked

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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run(){
                new halamanUtama().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ben;
    private javax.swing.JLabel chrono;
    private javax.swing.JLabel hybrid;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel keisha;
    private javax.swing.JLabel project;
    private javax.swing.JLabel vidita;
    // End of variables declaration//GEN-END:variables

}