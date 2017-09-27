package com.jrdevelop.fodasescore.services;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by renan.b on 27/09/2017.
 */

public class SessionManager {

    private static final String PREF_NAME = "BeduinoNovacap";
    private static final String KEY_COD_USUARIO = "CD_USU";
    private static final String KEY_STATUS_ENVIADO = "STATUS_TRANSMISSAO";
    private static final String KEY_REGISTRO_ATIVO = "REGISTRO_ATIVO";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context _context){
        this.context = _context;
        sharedPreferences = this.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void addRegistroSession(String key, String value){
        editor.putString(key, value);
        editor.commit();
    }

    public String getRegistroSesssion(String key){
        return sharedPreferences.getString(key, null);
    }

    public void limparRegistroSession(String ... key){
        for(String k : key) {
            editor.remove(k);
            editor.commit();
        }
    }

    public void addCodigoUsuario(String codUsuario){
        editor.putString(KEY_COD_USUARIO, codUsuario);
        editor.commit();
    }

    public void addStatusTransmissao(String statusTransmissao){
        editor.putString(KEY_STATUS_ENVIADO, statusTransmissao);
        editor.commit();
    }

    public void addRegistroAtivo(String tipoRegistro){
        editor.putString(KEY_REGISTRO_ATIVO, tipoRegistro);
        editor.commit();
    }

    public String getCodigoUsuario(){
        return sharedPreferences.getString(KEY_COD_USUARIO, null);
    }

    public String getStatusTransmissao(){
        return sharedPreferences.getString(KEY_STATUS_ENVIADO, null);
    }

    public String getRegistroAtivo(){
        return sharedPreferences.getString(KEY_REGISTRO_ATIVO, null);
    }

    public void limparRegistroAtivo(){
        editor.remove(KEY_REGISTRO_ATIVO);
        editor.commit();
    }

    public void limparSessionTransmissao(){
        //remove todas as configuracoes
        editor.remove(KEY_STATUS_ENVIADO);
        editor.remove(KEY_COD_USUARIO);

        //salva as alterações
        editor.commit();
    }
}
