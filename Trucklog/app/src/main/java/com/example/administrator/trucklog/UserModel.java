package com.example.administrator.trucklog;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by rock on 1/30/17.
 */

public class UserModel implements Serializable {
    private String name, email, image_url, id;
    private long created;
    public UserModel(JSONObject json_object){
        try{
            name = json_object.getString("name");
            created = json_object.getLong("created");
            email  =json_object.getString("email");
            image_url = json_object.getString("image_url");
            id = json_object.getString("id");
        }catch (JSONException e){

        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
