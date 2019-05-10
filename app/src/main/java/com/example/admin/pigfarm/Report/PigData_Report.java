package com.example.admin.pigfarm.Report;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.example.admin.R;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.MessageFormat;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class PigData_Report extends AppCompatActivity {

    String farm_id;
    private WebView webview;
    ProgressDialog pDialog;
    String pdffile,getstart_date,getend_date;
    SwipeRefreshLayout mySwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pigdata_report);


        SharedPreferences farm = getSharedPreferences("Farm", Context.MODE_PRIVATE);
        farm_id = farm.getString("farm_id", "");

        Intent intent = getIntent();
        pdffile = intent.getStringExtra("url");
        getstart_date = intent.getStringExtra("start_date");
        getend_date = intent.getStringExtra("end_date");


        StringBuffer buffer=new StringBuffer("http://drive.google.com/viewerng/viewer?url=");
        buffer.append(URLEncoder.encode(pdffile)+"?");
        buffer.append(URLEncoder.encode("farm_id=")+farm_id);
        buffer.append(URLEncoder.encode("&start_date=")+getstart_date);
        buffer.append(URLEncoder.encode("&end_date=")+getend_date);

        Log.d("show url" ,buffer.toString());
        
        webview = findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new MyWebViewClient());
        webview.loadUrl(buffer.toString());

    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
                pDialog = new ProgressDialog(PigData_Report.this);
                pDialog.setTitle("กำลังออกรายงาน");
                pDialog.setMessage("โปรดรอสักครู่...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (pDialog!=null){
                pDialog.dismiss();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }




}

