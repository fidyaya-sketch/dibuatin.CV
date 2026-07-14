///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mycompany.dibuatincv;
//
///**
// *
// * @author Acer
// */
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//public class DBConnection {
//   private static Connection koneksi;
//
//    public static Connection getKoneksi() {
//        // cek apakah koneksi
//        if (koneksi == null) {
//
//            try {
//                String url = "jdbc:mysql://127.0.0.1:3306/dibuatincv";
//                String user = "root";
//                String password = "";
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                koneksi = DriverManager.getConnection(url, user, password);
//            } catch (SQLException t) {
//                System.out.println("Error Membuat Koneksi");
//            } catch (ClassNotFoundException ex) {
//                System.getLogger(DBConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//            }
//        }
//        return koneksi;
//    }
//
//    public static Connection getKoneksiBaru() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//}
///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mycompany.dibuatincvnw;
//
///**
// *
// * @author Acer
// */
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//public class DBConnection {
//   private static Connection koneksi;
//
//    public static Connection getKoneksi() {
//        // cek apakah koneksi
//        if (koneksi == null) {
//
//            try {
//                String url = "jdbc:mysql://127.0.0.1:3307/dibuatincvnww";
//                String user = "root";
//                String password = "";
//                Class.forName("com.mysql.cj.jdbc.Driver");
//                koneksi = DriverManager.getConnection(url, user, password);
//            } catch (SQLException t) {
//                System.out.println("Error Membuat Koneksi");
//            } catch (ClassNotFoundException ex) {
//                System.getLogger(DBConnection.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
//            }
//        }
//        return koneksi;
//    }
//}
package com.mycompany.dibuatincv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection koneksi;
    
    public static Connection getKoneksi() {
        try {
            // ✅ Cek apakah koneksi masih hidup, kalau mati buat baru
            if (koneksi == null || koneksi.isClosed() || !koneksi.isValid(2)) {
                String url = "jdbc:mysql://127.0.0.1:3306/dibuatincv"
                           + "?autoReconnect=true&useSSL=false";
                String user = "root";
                String password = "";
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(url, user, password);
                koneksi.setAutoCommit(true); // ✅ Pastikan auto commit aktif
            }
        } catch (SQLException t) {
            System.out.println("Error Membuat Koneksi: " + t.getMessage());
            koneksi = null; // reset agar bisa coba lagi
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver tidak ditemukan: " + ex.getMessage());
        }
        return koneksi;
    }
    
    // ✅ Tambah method untuk paksa koneksi baru (dipakai saat cek status di proses.java)
    public static Connection getKoneksiBaru() {
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/dibuatincv"
                       + "?autoReconnect=true&useSSL=false";
            String user = "root";
            String password = "";
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection newConn = DriverManager.getConnection(url, user, password);
            newConn.setAutoCommit(true);
            return newConn;
        } catch (Exception e) {
            System.out.println("Gagal buat koneksi baru: " + e.getMessage());
            return null;
        }
    }
}