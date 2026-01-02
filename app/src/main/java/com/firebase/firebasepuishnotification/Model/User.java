package com.firebase.firebasepuishnotification.Model;

// This is a simple Java class that defines a User object.
// It's a "model" class, which means it represents a data structure.
public class User {

    // A private field to store the user's ID.
    private String id;
    // A private field to store the user's username.
    private String username;

    // A default constructor. This is required for some libraries (like Firebase Realtime Database) to be able to create instances of this class.
    public User() {
    }

    // A constructor that allows you to create a new User object with an ID and username.
    public User(String id, String username) {
        this.id = id;
        this.username = username;
    }

    // A "getter" method to retrieve the user's ID.
    public String getId() {
        return id;
    }

    // A "setter" method to set the user's ID.
    public void setId(String id) {
        this.id = id;
    }

    // A "getter" method to retrieve the user's username.
    public String getUsername() {
        return username;
    }

    // A "setter" method to set the user's username.
    public void setUsername(String username) {
        this.username = username;
    }
}
