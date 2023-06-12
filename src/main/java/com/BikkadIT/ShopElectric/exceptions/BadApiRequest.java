package com.BikkadIT.ShopElectric.exceptions;
public class BadApiRequest extends RuntimeException{

    public BadApiRequest(String message){

        super("Image Upload Successfully");
    }
    public BadApiRequest(){
        super("Bad Request !!");
    }
}
