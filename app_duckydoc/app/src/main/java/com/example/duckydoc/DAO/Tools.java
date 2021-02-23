package com.example.duckydoc.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tools {
    public static final String userURL = "http://192.168.1.28:8080/api/";
    public static final String documentURL = "http://192.168.1.28:8081/api/";
    public static final String qaURL = "http://192.168.1.28:8082/api/";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String sUserKey = "user_data_unique_key";
    public static final String genericError = "è stato rilevato un errore, riprova";

    public static Account account;
    public static Query query;
    public static Document document;
    public static List<Document> lstDocuments;
    protected static String sqlErrorContainer;

    public static String getSqlErrorContainer() {
        return sqlErrorContainer;
    }

    //Save and get data User into local smartphone
    public static void saveSharedData(Context context, String key, String data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, data);
        editor.apply();
    }
    public static String getSharedData(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setErrorMessage(TextView label, String message){
        label.setText(message);
        label.setTextColor(Color.RED);
    }

    //Login
    public static Account loginUser(String email, String password){
        String parameters = userURL + "utenti/login/"+email+"/"+password;
        String result;
        Account account = null;
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();

            //FARE I CONTROLLI DEI RISULTATI DI RITORNO!!
            switch (result) {
                case genericError:
                    //problema connessione db
                    sqlErrorContainer = genericError;
                    break;
                case "EMAILERR":
                    sqlErrorContainer = "Email inserita inesistente";
                    break;
                case "FAILURE":
                    sqlErrorContainer = "Password errata";
                    break;
                default:
                    Gson gson = new Gson();
                    int id = gson.fromJson(result, int.class);
                    account = new Account(id, email, "aletemp", password);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return account;
        }
    }

    //Get all queries
    public static ArrayList<Query> getQueries(){
        String parameters = qaURL + "queries";
        String result;
        Query[] vQueries;
        ArrayList<Query> lstQueries = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
                Log.i("info", "Errore1");
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getQueries();
            }
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                Log.i("body", gson.toJson(result));
                vQueries = gson.fromJson(result, Query[].class);
                if (vQueries.length > 0) {
                    lstQueries.addAll(Arrays.asList(vQueries));
                }
                else{
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return lstQueries;
        }
    }

    //Get query from id
    public static Query getQueriesId(long id){
        String parameters = qaURL + "queries/" + id;
        String result;
        Query query = null;
        ArrayList<Query> lstQueries = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getQueriesId(id);
            }
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                Log.i("info", result);
                query = gson.fromJson(result, Query.class);
                if (query == null) {
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return query;
        }
    }

    //Get all user queries
    public static ArrayList<Query> getUserQueries(){
        String parameters = qaURL + "queries/user/" + account.getIdUser();
        String result;
        Query[] vQueries;
        ArrayList<Query> lstQueries = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){

                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getUserQueries();
            }
            else {
                Log.i("info", "corretto");
                Gson gson = new GsonBuilder().setLenient().create();
                vQueries = gson.fromJson(result, Query[].class);
                if (vQueries.length > 0) {
                    lstQueries.addAll(Arrays.asList(vQueries));
                }
                else{
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            Log.i("info", "eccezione");
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return lstQueries;
        }
    }

    //Get all user answers
    public static ArrayList<Answer> getUserAnswers(){
        String parameters = qaURL + "answers/user/" + account.getIdUser();
        String result;
        Answer[] vAnswers;
        ArrayList<Answer> lstAnswers = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getUserAnswers();
            }
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                vAnswers = gson.fromJson(result, Answer[].class);
                if (vAnswers.length > 0) {
                    lstAnswers.addAll(Arrays.asList(vAnswers));
                }
                else{
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return lstAnswers;
        }
    }

    public static boolean postQuery(Query q){
        String parameters = qaURL + "queries/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(q);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return postQuery(q);
            }
            else {
                Gson gson = new Gson();
                Query tmp = gson.fromJson(result, Query.class);
                if (tmp != null) {
                    return true;
                }
                else{
                    sqlErrorContainer = genericError;
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        return false;
    }

    public static boolean postAnswer(Answer a){
        String parameters = qaURL + "answers/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(a);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return postAnswer(a);
            }
            else {
                Gson gson = new Gson();
                Query tmp = gson.fromJson(result, Query.class);
                if (tmp != null) {
                    return true;
                }
                else{
                    sqlErrorContainer = genericError;
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        return false;
    }

    //Get all documents
    public static ArrayList<Document> getDocuments(){
        String parameters = documentURL + "documents";
        String result;
        Document[] vDocument;
        ArrayList<Document> lstDocuments = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getUserDocuments();
            }
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                vDocument = gson.fromJson(result, Document[].class);
                if (vDocument.length > 0) {
                    lstDocuments.addAll(Arrays.asList(vDocument));
                }
                else{
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return lstDocuments;
        }
    }

    //Get all user documents
    public static ArrayList<Document> getUserDocuments(){
        String parameters = documentURL + "documents/user/" + account.getIdUser();
        String result;
        Document[] vDocument;
        ArrayList<Document> lstDocuments = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getUserDocuments();
            }
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                vDocument = gson.fromJson(result, Document[].class);
                if (vDocument.length > 0) {
                    lstDocuments.addAll(Arrays.asList(vDocument));
                }
                else{
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return lstDocuments;
        }
    }

    public static boolean postDocument(Document d){
        String parameters = documentURL + "documents/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(d);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return postDocument(d);
            }
            else {
                Gson gson = new Gson();
                Document tmp = gson.fromJson(result, Document.class);
                if (tmp != null) {
                    return true;
                }
                else{
                    sqlErrorContainer = genericError;
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        return false;
    }

    //Get all user documents
    public static ArrayList<Review> getDocumentReviews(){
        String parameters = documentURL + "/documents/" + document.getId() + "/reviews";
        String result;
        Review[] vReview;
        ArrayList<Review> lstReviews = new ArrayList<>();
        HttpGetRequest getRequest = new HttpGetRequest();
        try {
            result = getRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return getDocumentReviews();
            }
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                vReview = gson.fromJson(result, Review[].class);
                if (vReview.length > 0) {
                    lstReviews.addAll(Arrays.asList(vReview));
                }
                else{
                    sqlErrorContainer = genericError;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        finally {
            return lstReviews;
        }
    }

    public static boolean postReview(Review r){
        String parameters = documentURL + "reviews/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(r);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else if(result.equals("LOGERR")){ //probabilmente inutile
                sqlErrorContainer = "sessione scaduta";
                loginUser(account.getEmail(), account.getPassword());
                return postReview(r);
            }
            else {
                Gson gson = new Gson();
                Review tmp = gson.fromJson(result, Review.class);
                if (tmp != null) {
                    return true;
                }
                else{
                    sqlErrorContainer = genericError;
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        return false;
    }
}
