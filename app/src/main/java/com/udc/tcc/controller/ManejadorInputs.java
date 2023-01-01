package com.udc.tcc.controller;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ManejadorInputs {
    public static void limpiarCampos(List<TextInputEditText> inputs){
        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).setText("");
        }
    }

    public static void disable(List<TextInputEditText> inputs){
        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).setEnabled(false);
        }
    }

    public static void enable(List<TextInputEditText> inputs){
        for (int i = 0; i < inputs.size(); i++) {
            inputs.get(i).setEnabled(true);
        }
    }
}
