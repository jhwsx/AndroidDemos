package com.wzc.util;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by wzc on 2017/8/10.
 *
 */

public class HttpTool {

    private HttpTool() {
    }

    public static void doPost(String url, final List<CustomedNameValuePair> params, final HttpCallbackListener listener) {
        doPost(url, "", params, listener);
    }

    public static void doPost(final String url, final String cert, final List<CustomedNameValuePair> params, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (params != null) {
                    if (null != url) {
                        if (url.startsWith("https:")) { // 是https
                            doHttpsPost( url, params, listener, cert);
                        } else if (url.startsWith("http:")){ // 是http
                            doHttpPost(url, params, listener);
                        }
                    }
                }
            }
        }).start();
    }

    private static final int RQ_TIMEOUT = 15 * 1000;
    private static final int SO_TIMEOUT = 30 * 1000;
    private static void doHttpsPost( String url, List<CustomedNameValuePair> params, HttpCallbackListener listener, String cert) {
        if (params != null) {
            HttpsURLConnection connection = null;
            String result = "";
            try {
                initTrustManagers(cert);
                initSSL(cert);

                URL u = new URL(url);
                connection = (HttpsURLConnection) u.openConnection();
                if (!TextUtils.isEmpty(cert)) {
                    connection.setSSLSocketFactory(ssf);
                } else {
                    connection.setSSLSocketFactory(ssfForNoCert);
                    connection.setHostnameVerifier(new TrustAnyHostnameVerifier());

                }
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setConnectTimeout(RQ_TIMEOUT);
                connection.setReadTimeout(SO_TIMEOUT);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.connect();

                DataOutputStream out = new DataOutputStream(connection
                        .getOutputStream());

                String content = getContent(params);
                out.writeBytes(content);
                out.flush();
                out.close();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    result = inputStreamToString(connection.getInputStream());
                    if (listener != null) {
                        listener.onFinish(result);
                    }
                }
                connection.disconnect();
            } catch (Exception e) {
                if (listener != null) {
                    listener.onFail(e);
                }
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }

    private static TrustManager[] trustManagers;
    private static TrustManager[] trustManagersForNoCert;
    private static SSLSocketFactory ssf;
    private static SSLSocketFactory ssfForNoCert;
    private static void initSSL(String cert) throws NoSuchAlgorithmException, KeyManagementException {
        if (ssf == null && !TextUtils.isEmpty(cert)) {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, trustManagers, null);
            ssf = sslcontext.getSocketFactory();
        }
        if (ssfForNoCert == null && TextUtils.isEmpty(cert)) {
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, trustManagersForNoCert, null);
            ssfForNoCert = sslcontext.getSocketFactory();
        }
    }

    private static void initTrustManagers(String cert) throws CertificateException,
            NoSuchProviderException, IOException, KeyStoreException, NoSuchAlgorithmException {
        if (trustManagers == null && !TextUtils.isEmpty(cert)) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509", "BC");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            InputStream certificate = new ByteArrayInputStream(cert.getBytes());
            keyStore.setCertificateEntry("ca", certificateFactory.generateCertificate(certificate));

            certificate.close();

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            trustManagers = trustManagerFactory.getTrustManagers();
        }
        if (trustManagersForNoCert == null && TextUtils.isEmpty(cert)) {
            trustManagersForNoCert = new TrustManager[]{new MyX509TrustManager()};
        }

    }

    private static void doHttpPost(String url, List<CustomedNameValuePair> params, HttpCallbackListener listener) {
        if (params != null) {
            String result = "";
            HttpURLConnection connection = null;
            try {

                URL u = new URL(url);
                connection = (HttpURLConnection) u.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Charset", "UTF-8");
                connection.setConnectTimeout(RQ_TIMEOUT);
                connection.setReadTimeout(SO_TIMEOUT);
                connection.setDoOutput(true);
                connection.setChunkedStreamingMode(0);
                connection.setDoInput(true);
                connection.connect();
                // 将请求参数发送给服务器
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));

                String content = getContent(params);
                out.writeBytes(content);
                out.flush();
                out.close();

                if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                    result = inputStreamToString(connection.getInputStream());
                    if (listener != null) {
                        listener.onFinish(result);
                    }
                }
                connection.disconnect();
            } catch (Exception e) {
                if (listener != null) {
                    listener.onFail(e);
                }
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
    }


    private static String getContent(List<CustomedNameValuePair> params) {
        String content = "";
        for (CustomedNameValuePair pair : params) {
            content += "&" + pair.toString();
        }
        return content.length() > 0 ? content.substring(1, content.length()) : content;
    }

    private static String inputStreamToString(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len;
        byte[] arr = new byte[1024];
        try {
            while ((len = bis.read(arr)) != -1) {
                bos.write(arr, 0, len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 信任所有主机 对于任何证书都不做SSL检测
     * 安全验证机制，而Android采用的是X509验证
     */
    private static class MyX509TrustManager implements X509TrustManager {

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }

    /**
     * 不进行主机名确认
     */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    interface HttpCallbackListener {

        void onFinish(String result);

        void onFail(Exception e);
    }

    class CustomedNameValuePair {
        private String key;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            if (null == value){
                this.value = "";
            } else {
                this.value = value;
            }

        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            if (null == key){
                this.key = "";
            } else {
                this.key = key;
            }
        }

        private String value;

        public CustomedNameValuePair(String key, String value) {
            setKey(key);
            setValue(value);
        }

        @Override
        public String toString() {
            try {
                return key + "=" + URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
            }
            return null;
        }
    }
}
