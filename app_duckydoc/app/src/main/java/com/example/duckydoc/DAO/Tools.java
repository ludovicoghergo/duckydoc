package com.example.duckydoc.DAO;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tools {
    public static final String requestURL = "http://192.168.1.28:8085/api/";
    public static final String genericError = "è stato rilevato un errore, riprova";

    public static Account account;
    public static Query query;
    public static Document document;
    public static List<Document> lstDocuments;
    protected static String sqlErrorContainer;

    public static String getSqlErrorContainer() {
        return sqlErrorContainer;
    }

    public static void setErrorMessage(TextView label, String message){
        label.setText(message);
        label.setTextColor(Color.RED);
    }

    //Login
    public static Account loginUser(String id){
        String parameters = requestURL + "utenti/" + id;
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
                    account = gson.fromJson(result, Account.class);
                    //account = new Account(id, email, "aletemp", password);
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
        }
        return account;
    }

    public static boolean postUser(Account u){
        String parameters = requestURL + "utenti/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(u);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else {
                Gson gson = new Gson();
                Account tmp = gson.fromJson(result, Account.class);
                if (tmp != null) {
                    account.setIdUser(tmp.getIdUser());
                    Log.i("INFO", String.valueOf(tmp.getIdUser()));
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

    //Get all queries
    public static ArrayList<Query> getQueries(){
        String parameters = requestURL + "queries";
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
        return lstQueries;
    }

    //Get query from id
    public static Query getQueriesId(long id){
        String parameters = requestURL + "queries/" + id;
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
        return query;
    }

    //Get query from id
    public static List<Answer> getQueryAnswers(long id){
        String parameters = requestURL + "queries/" + id + "/answers";
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
            else {
                Gson gson = new GsonBuilder().setLenient().create();
                //Log.i("info", result);
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
        return lstAnswers;
    }

    //Get all user queries
    public static ArrayList<Query> getUserQueries(){
        String parameters = requestURL + "queries/user/" + account.getIdUser();
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
        return lstQueries;
    }

    //Get all user answers
    public static ArrayList<Answer> getUserAnswers(){
        String parameters = requestURL + "answers/user/" + account.getIdUser();
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
        return lstAnswers;
    }

    public static boolean postQuery(Query q){
        String parameters = requestURL + "queries/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(q);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
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
        String parameters = requestURL + "answers/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(a);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else {
                Gson gson = new Gson();
                Answer tmp = gson.fromJson(result, Answer.class);
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

    public static boolean putCorrect(long id){
        String parameters = requestURL + "answers/correct/" + id;
        String result;

        HttpPutRequest putRequest = new HttpPutRequest(null);
        try {
            result = putRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
            }
            else {
                Gson gson = new Gson();
                Answer tmp = gson.fromJson(result, Answer.class);
                if (tmp != null) {
                    //Log.i("INFO", String.valueOf(tmp.getIdUser()));
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
        String parameters = requestURL + "documents";
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
        return lstDocuments;
    }

    //Get all user documents
    public static ArrayList<Document> getUserDocuments(){
        String parameters = requestURL + "documents/user/" + account.getIdUser();
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
        return lstDocuments;
    }

    public static boolean postDocument(Document d){
        String parameters = requestURL + "documents/createapp";
        String result;

        Log.i("doc", "start");

        HttpPostRequest postRequest = new HttpPostRequest(d);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
                Log.i("doc", "generic error");
            }
            else {
                Gson gson = new Gson();
                boolean tmp = gson.fromJson(result, boolean.class);
                if (tmp) {
                    Log.i("doc", "ok");
                    return true;
                }
                else{
                    sqlErrorContainer = genericError;
                    Log.i("doc", "altro errore");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            sqlErrorContainer = genericError;
            Log.i("doc", "catch error");
        }
        return false;
    }

    //Get all user documents
    public static ArrayList<Review> getDocumentReviews(){
        String parameters = requestURL + "/documents/" + document.getId() + "/reviews";
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
        return lstReviews;
    }

    public static boolean postReview(Review r){
        String parameters = requestURL + "reviews/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(r);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
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

    public static boolean postReport(Report r){
        String parameters = requestURL + "reports/" + account.getIdUser() + "/create";
        String result;

        HttpPostRequest postRequest = new HttpPostRequest(r);
        try {
            result = postRequest.execute(parameters).get();
            if(result.equals(genericError)){
                //problema connessione db
                sqlErrorContainer = genericError;
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
