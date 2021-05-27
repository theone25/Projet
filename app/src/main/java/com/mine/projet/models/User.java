package com.mine.projet.models;

import java.io.Serializable;

public class User implements Serializable {
    public int id;
    public String nom;
    public String prenom;
    public String email;
    public String tel;
    public String password;

    public User(int id, String nom, String prenom, String email, String password, String tel) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public User() {

    }

}
