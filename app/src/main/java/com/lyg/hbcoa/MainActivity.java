package com.lyg.hbcoa;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private WebView mWebView;
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageArray;
    private final static int FILECHOOSER_RESULTCODE=1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==FILECHOOSER_RESULTCODE) {
            if (null != mUploadMessage) {


                Uri result = data == null || resultCode != RESULT_OK ? null
                        : data.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
            else if(null != mUploadMessageArray){
                Uri result = data == null || resultCode != RESULT_OK ? null
                        : data.getData();
                mUploadMessageArray.onReceiveValue(new Uri[] {result});
                mUploadMessageArray = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webView.setOnTouchListener(this);

        webView.setWebViewClient(new MyWebViewClient());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                //CLog.i("UPFILE", "in openFile Uri Callback");
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            // For Android 3.0+
            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                //CLog.i("UPFILE", "in openFile Uri Callback has accept Type" + acceptType);
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                String type = TextUtils.isEmpty(acceptType) ? "application/*" : acceptType;
                i.setType(type);
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"),
                        FILECHOOSER_RESULTCODE);
            }

            // For Android 4.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                //CLog.i("UPFILE", "in openFile Uri Callback has accept Type" + acceptType + "has capture" + capture);
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                mUploadMessage = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                String type = TextUtils.isEmpty(acceptType) ? "application/*" : acceptType;
                i.setType(type);
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
            }

            //Android 5.0+
            @Override
            @SuppressLint("NewApi")
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                if (mUploadMessage != null) {
                    mUploadMessage.onReceiveValue(null);
                }
                //CLog.i("UPFILE", "file chooser params：" + fileChooserParams.toString());
                mUploadMessageArray = filePathCallback;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //i.addCategory(Intent.CATEGORY_OPENABLE);
                if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                        && fileChooserParams.getAcceptTypes().length > 0) {
                    i.setType(fileChooserParams.getAcceptTypes()[0]);
                } else {
                    i.setType("*/*");
                }
                //i.setData(Uri.parse("file://"));
                i.setType("image/*;pdf/*;doc/*;docx/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
                return true;
            }
            //设置响应js 的Confirm()函数
//            @Override
//            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
//                b.setTitle("Confirm");
//                b.setMessage(message);
//                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                });
//                b.create().show();
//                return true;
//            }

            //设置响应js 的Prompt()函数
//            @Override
//            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
//                final View v = View.inflate(MainActivity.this, R.layout.prompt_dialog, null);
//                ((TextView) v.findViewById(R.id.prompt_message_text)).setText(message);
//                ((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
//                AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
//                b.setTitle("Prompt");
//                b.setView(v);
//                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();
//                        result.confirm(value);
//                    }
//                });
//                b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                });
//                b.create().show();
//                return true;
//            }
        });
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.loadUrl("http://218.92.22.138:8088/portal/m/");
    }

    private class MyWebViewDownLoadListener implements DownloadListener {

        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            if(mimetype.startsWith("image"))
//                intent.setType("image/*");
//            else if(mimetype.startsWith("video"))
//                intent.setType("video/*");
//            else if(mimetype.startsWith("audio"))
//                intent.setType("audio/*");
//            else if(mimetype.equals("application/pdf"))
//                intent.setType("application/pdf");
//            else
//                intent.setType("*/*");
            startActivity(intent);
        }

    }
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
