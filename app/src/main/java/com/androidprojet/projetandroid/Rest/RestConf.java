package com.androidprojet.projetandroid.Rest;

public interface RestConf {

    public final String BASE_URL="http://192.168.1.12:8000";
    public final String LOGIN_PATH=BASE_URL+"/users/login";
    public final String CREATE_ACCOUNT_PATH=BASE_URL+"/users/createAccount";
    public final String EDIT_ACCOUNT_PATH=BASE_URL+"/users/editAccount";
    public final String LIST_DEMANDE_PATH=BASE_URL+"/demandes/list/";
    public final String LIST_CLIENT_DEMANDE_PATH=BASE_URL+"/demandes/client/";
    public final String ADD_DEMANDE_PATH=BASE_URL+"/demandes/add/";
    public final String EDIT_DEMANDE_PATH=BASE_URL+"/demandes/edit/";
    public final String DELETE_DEMANDE_PATH=BASE_URL+"/demandes/delete/";
    public final String LIST_COM_PATH=BASE_URL+"/demandes/com/";

    public final String ADD_COM_PATH=BASE_URL+"/demandes/Addcom/";



    public final String LOGIN_REQUEST_TYPE="login.withObjectResult";
    public final String CREATE_OR_EDIT_ACCOUNT_REQUEST_TYPE="edit_create_account.withStringResult";
    public final String LIST_DEMANDE_REQUEST_TYPE="list_demande.withArrayResult";
    public final String LIST_DEMANDE_CLIENT_REQUEST_TYPE="list_client_demande.withArrayResult";
    public final String ADD_DEMANDE_REQUEST_TYPE="add_demande.withSTringResult";
    public final String EDIT_DEMANDE_REQUEST_TYPE="edit_demande.withSTringResult";
    public final String DELETE_DEMANDE_REQUEST_TYPE="delete_demande.withSTringResult";
    public final String LIST_COM_REQUEST_TYPE="list_com.withArrayResult";
    public final String ADD_COM_REQUEST_TYPE=BASE_URL+"/add_com.withStringResult";

}
