/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mudar.backend.Json.Response;

import java.util.ArrayList;

/**
 *
 * @author Alvaro
 */
public class JsonResponse {
    
    
    
    private ArrayList<defaultMessage> errors = new ArrayList();

    public JsonResponse(String defaultMessage) {
        defaultMessage aqt = new defaultMessage( defaultMessage);
        errors.add(aqt);
    }

    public ArrayList<defaultMessage> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<defaultMessage> errors) {
        this.errors = errors;
    }
    
    

    
    
}
