package com.mycompany.dibuatincv.pengguna;

public class SessionManager {
    private static SessionManager instance;
    
    private int idUser;
    private int idDesain;
    private int idData;
    private String username;
    private String namaDesain; // ✅ Tambah ini
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    
    public int getIdDesain() { return idDesain; }
    public void setIdDesain(int idDesain) { this.idDesain = idDesain; }
    
    public int getIdData() { return idData; }
    public void setIdData(int idData) { this.idData = idData; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getNamaDesain() { return namaDesain; } // ✅ Tambah ini
    public void setNamaDesain(String namaDesain) { this.namaDesain = namaDesain; } // ✅ Tambah ini
}