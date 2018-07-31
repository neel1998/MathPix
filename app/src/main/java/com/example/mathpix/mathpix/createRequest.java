package com.example.mathpix.mathpix;

/**
 * Created by Admin on 22-05-2018.
 */

public class createRequest {
    private String url;
    public createRequest(String img){
        url="data:image/jpeg;base64,"+img;
    }
}
