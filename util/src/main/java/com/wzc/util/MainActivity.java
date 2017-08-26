package com.wzc.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String CER = "-----BEGIN CERTIFICATE-----\n" +
            "MIIEKjCCAxKgAwIBAgIDCoFLMA0GCSqGSIb3DQEBCwUAMEcxCzAJBgNVBAYTAlVT\n" +
            "MRYwFAYDVQQKEw1HZW9UcnVzdCBJbmMuMSAwHgYDVQQDExdSYXBpZFNTTCBTSEEy\n" +
            "NTYgQ0EgLSBHMzAeFw0xNjAyMjQwODQ4MjBaFw0xOTAyMjUyMTQ0NDhaMBYxFDAS\n" +
            "BgNVBAMMCyouYWR1cHMuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKC\n" +
            "AQEAwv7gQSjvv5NCHQQb6SUtj/1fAX9/fD/ikJjAdye57dmjFAwkN6bmwTU7XlMc\n" +
            "BXsJS6qqJQ2/349OWLzoNSDm7MhZTxDZ/iPK9zPVOT8b1NaUjWJBD+QRPGCMzubR\n" +
            "SS+TiDNHbAIj73A4rUJLZ5PX6/h1bESlPc58Vv40s5CXXoj3t3X/6GPZUW3urFPB\n" +
            "bey72x7VYSAl92LhRMsQDN4zpxIntX/ApMXe2eElQhPiJ52NDV02eBQSlcCrWrEV\n" +
            "nD8Yjz6fGx9LtaW5GuFV0nI9ylSWEOEQe8cbJ1u+WCNW2/wQ7r+x0n7hB9BCmFaq\n" +
            "RcifoBs/8SwiPMzc6ltv/oZiowIDAQABo4IBTjCCAUowHwYDVR0jBBgwFoAUw5zz\n" +
            "/NNGCDS7zkZ/oHxb8+IIy1kwVwYIKwYBBQUHAQEESzBJMB8GCCsGAQUFBzABhhNo\n" +
            "dHRwOi8vZ3Yuc3ltY2QuY29tMCYGCCsGAQUFBzAChhpodHRwOi8vZ3Yuc3ltY2Iu\n" +
            "Y29tL2d2LmNydDAOBgNVHQ8BAf8EBAMCBaAwHQYDVR0lBBYwFAYIKwYBBQUHAwEG\n" +
            "CCsGAQUFBwMCMCEGA1UdEQQaMBiCCyouYWR1cHMuY29tgglhZHVwcy5jb20wKwYD\n" +
            "VR0fBCQwIjAgoB6gHIYaaHR0cDovL2d2LnN5bWNiLmNvbS9ndi5jcmwwDAYDVR0T\n" +
            "AQH/BAIwADBBBgNVHSAEOjA4MDYGBmeBDAECATAsMCoGCCsGAQUFBwIBFh5odHRw\n" +
            "czovL3d3dy5yYXBpZHNzbC5jb20vbGVnYWwwDQYJKoZIhvcNAQELBQADggEBADuw\n" +
            "fE0p4KRY3+NVFQdABZP41UEsZ1lgu+Q6imbUPiNiJ8OCwBxAP2u8O7Yzz2Gy3PvR\n" +
            "tN2YoaKhOufwMJyFQeCys80RU8qv3VtSejSGk69zWQOehnt0gfbrKTbBaIRaLefc\n" +
            "xT0n1v/tBixmdgsgtFOlBhGkecW9KwYDftDdRCXA0cEYYwNuFsappkD4QsMPZFwy\n" +
            "HXCW5S7LIhmXRqRb78WwbRMdh5JkmLJRtG7yo8VS7vXfxQDuKf8iVhl9YwZRbVsJ\n" +
            "6CQACUnnaVJmCr75/epNfKN5nRVsRjKz1gzzI6EdC8KxH+IXBtICLyvnr7yYmAR8\n" +
            "0pPMIhYAnbCnO5tsgqQ=\n" +
            "-----END CERTIFICATE-----";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById (R.id.button).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 HttpTool.doPost("http://www.minotopic.com/?channel=10314", new ArrayList<HttpTool.CustomedNameValuePair>(), new HttpTool.HttpCallbackListener() {
                     @Override
                     public void onFinish(final String result) {
                         findViewById (R.id.button).post(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(MainActivity.this, "http result : " + result, Toast.LENGTH_SHORT).show();
                             }
                         });
                         Log.e("tagtag", "http result : " + result);

                     }

                     @Override
                     public void onFail(final Exception e) {
                         findViewById (R.id.button).post(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(MainActivity.this, "http e : " + e, Toast.LENGTH_SHORT).show();
                             }
                         });
                         Log.e("tagtag", "http e : " + e);

                     }
                 });
             }
         });
         findViewById (R.id.button2).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               String url =  "https://clean.adups.com/logserver/reportScene.do";

                 HttpTool.doPost(url, CER, new ArrayList<HttpTool.CustomedNameValuePair>(), new HttpTool.HttpCallbackListener() {
                     @Override
                     public void onFinish(final String result) {
                         findViewById (R.id.button2).post(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(MainActivity.this, "https result : " + result, Toast.LENGTH_SHORT).show();
                             }
                         });
                         Log.e("tagtag", "https result : " + result);
                     }

                     @Override
                     public void onFail(final Exception e) {
                         findViewById (R.id.button).post(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(MainActivity.this, "https e : " + e, Toast.LENGTH_SHORT).show();
                             }
                         });
                         Log.e("tagtag", "https e : " + e);
                     }
                 });
             }
         });
         findViewById (R.id.button3).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
               String url =  "https://www.baidu.com/";

                 HttpTool.doPost(url,  new ArrayList<HttpTool.CustomedNameValuePair>(), new HttpTool.HttpCallbackListener() {
                     @Override
                     public void onFinish(final String result) {
                         findViewById (R.id.button3).post(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(MainActivity.this, "https no cert result : " + result, Toast.LENGTH_SHORT).show();
                             }
                         });
                         Log.e("tagtag", "https no cert result : " + result);
                     }

                     @Override
                     public void onFail(final Exception e) {
                         findViewById (R.id.button3).post(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(MainActivity.this, "https no cert e : " + e, Toast.LENGTH_SHORT).show();
                             }
                         });
                         Log.e("tagtag", "https no cert  e : " + e);
                     }
                 });
             }
         });
    }
}
