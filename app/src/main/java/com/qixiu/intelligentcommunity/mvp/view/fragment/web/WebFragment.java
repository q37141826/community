package com.qixiu.intelligentcommunity.mvp.view.fragment.web;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.PopupWindow;

import com.google.gson.Gson;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.mvp.beans.home.jsinterface.JsInterfaceInfo;
import com.qixiu.intelligentcommunity.mvp.view.activity.web.WebActivity;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.fragment.home.jsinterface.JsInterface;
import com.qixiu.intelligentcommunity.my_interface.web.OnWebShowlistener;
import com.qixiu.intelligentcommunity.utlis.PopuWindowFactory;
import com.qixiu.intelligentcommunity.utlis.UriTranslate;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.api.widget.Widget;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gyh on 2017/6/26 0026.
 */

public abstract class WebFragment extends BaseFragment implements WebActivity.OnWebPageBackListener, PopuWindowFactory.OnClickCameraOrSelectPhotoListener {

    private WebView mChildWebView;
    protected OnWebShowlistener mOnWebShowListener;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    private int PHOTO_SELECT = 111;
    private int CAMERA_SELECT = 110;

    String FILE_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath();

    protected abstract WebView getWebView();

    private PopupWindow mPopupWindow;
    protected WebChromeClient mWebChromeClient = new WebChromeClient() {


        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mUploadMessageForAndroid5 = filePathCallback;
            mPopupWindow = PopuWindowFactory.showCameraOrSelectPhoto(getActivity(), getActivity().getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0, WebFragment.this);

            return true;
        }
    };

    private void openFileChooserImplForAndroid5() {
        Album.image(getActivity())
                .multipleChoice()
                .widget(Widget.newDarkBuilder(getActivity())
                        .title("图库") // Title.
                        .statusBarColor(getResources().getColor(R.color.theme_color)) // StatusBar color.
                        .toolBarColor(getResources().getColor(R.color.theme_color)) // Toolbar color.
                        .navigationBarColor(getResources().getColor(R.color.theme_color)) // Virtual NavigationBar color of Android5.0+.
                        .mediaItemCheckSelector(getResources().getColor(R.color.theme_color), getResources().getColor(R.color.theme_color)) // Image or video selection box.
                        .bucketItemCheckSelector(Color.WHITE, Color.WHITE) // Select the folder selection box.
                        .buttonStyle( // Used to configure the style of button when the image/video is not found.
                                Widget.ButtonStyle.newLightBuilder(getActivity()) // With Widget's Builder model.
                                        .setButtonSelector(Color.WHITE, Color.WHITE)// Button selector.
                                        .build()
                        )
                        .build())
                .requestCode(PHOTO_SELECT)
                .camera(true)
                .columnCount(3)
                .selectCount(9).onResult(new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                if (null == mUploadMessageForAndroid5)
                    return;
                if (result.size() != 0) {
                    Uri[] urls = new Uri[result.size()];
                    for (int i = 0; i < result.size(); i++) {
                        String path = result.get(i).getPath();
                        Uri imageContentUri = UriTranslate.getImageContentUri(getContext(), new File(path));
                        urls[i] = imageContentUri;
                    }
                    mUploadMessageForAndroid5.onReceiveValue(urls);
                } else {
                    mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
                }
                mUploadMessageForAndroid5 = null;
            }
        }).start();


//        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
//        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        contentSelectionIntent.setType("image/*");
//        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
//        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
//        chooserIntent.putExtra(Intent.EXTRA_TITLE, "选择图片");
//        startActivityForResult(chooserIntent, PHOTO_SELECT);


//        Intent intent = new Intent(Intent.ACTION_PICK, null);
//        //此处调用了图片选择器
//        //如果直接写intent.setDataAndType("image/*");
//        //调用的是系统图库
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        // intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(Intent.createChooser(intent, "请选择一个文件"), FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
    }

    private String photoPath;
    private Uri mUri;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        destroyWebView();
    }

    protected void setOnWebShowListener(OnWebShowlistener onWebShowListener) {

        mOnWebShowListener = onWebShowListener;
    }

    @Override
    public void onClose() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();

        }
        if (mUploadMessageForAndroid5 != null) {
            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //"content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FPictures%2F%E9%94%81%E5%B1%8F%E5%A3%81%E7%BA%B8%2FI01050388.jpg"
        if (null == mUploadMessageForAndroid5)
            return;
        if (requestCode == PHOTO_SELECT) {

            Uri result = (data == null || resultCode != RESULT_OK) ? null : data.getData();
            if (result != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }

        } else if (requestCode == CAMERA_SELECT) {
            if (resultCode == RESULT_OK && mUri != null) {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{mUri});
            } else {
                mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
            }
        }
        mUploadMessageForAndroid5 = null;
    }

    @Override
    public void onClickCamera() {
        Intent intent = new Intent();
        // 指定开启系统相机的Action
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        // 根据文件地址创建文件
        File file = new File(FILE_PATH, "huaxia_upload.jpg");
        photoPath = file.getPath();
        if (photoPath == null) {
            return;
        }
        // 把文件地址转换成Uri格式
        mUri = Uri.fromFile(file);
        // 设置系统相机拍摄照片完成后图片文件的存放地址
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        startActivityForResult(intent, CAMERA_SELECT);
    }

    @Override
    public void onSelectPhoto() {
        openFileChooserImplForAndroid5();
    }


    public static class BaseWebJsInterface implements JsInterface {

        private final WeakReference<WebFragment> mFragmentWeakReference;
        private final Gson mGson;


        public BaseWebJsInterface(WebFragment WebFragment) {
            mFragmentWeakReference = new WeakReference<>(WebFragment);
            mGson = new Gson();
        }

        @JavascriptInterface
        @Override
        public void pushBtn(String jsInterfaceInfo) {
            final JsInterfaceInfo mInterfaceInfo = mGson.fromJson(jsInterfaceInfo, JsInterfaceInfo.class);
            final WebFragment webFragment = mFragmentWeakReference.get();
            if (mInterfaceInfo != null && webFragment != null) {
                webFragment.runToUI(new Runnable() {
                    @Override
                    public void run() {

                        webFragment.pushBtn(mInterfaceInfo);
                    }
                });

            }
        }

        @JavascriptInterface
        @Override
        public void imageBtn(String jsInterfaceInfo) {
            final JsInterfaceInfo mInterfaceInfo = mGson.fromJson(jsInterfaceInfo, JsInterfaceInfo.class);
            final WebFragment webFragment = mFragmentWeakReference.get();
            if (mInterfaceInfo != null && webFragment != null) {
                webFragment.runToUI(new Runnable() {
                    @Override
                    public void run() {

                        webFragment.imageBtn(mInterfaceInfo);
                    }
                });

            }
        }

        @JavascriptInterface
        @Override
        public void titleName(String jsInterfaceInfo) {
            final JsInterfaceInfo mInterfaceInfo = mGson.fromJson(jsInterfaceInfo, JsInterfaceInfo.class);
            final WebFragment webFragment = mFragmentWeakReference.get();
            if (mInterfaceInfo != null && webFragment != null && webFragment.mOnWebShowListener != null) {
                webFragment.runToUI(new Runnable() {
                    @Override
                    public void run() {
                        webFragment.mOnWebShowListener.onWebShow(mInterfaceInfo);
                        webFragment.titleName(mInterfaceInfo);
                    }
                });

            }
        }

        @JavascriptInterface
        @Override
        public void popBtn(String jsInterfaceInfo) {

            final WebFragment webFragment = mFragmentWeakReference.get();
            if (webFragment != null) {
                webFragment.runToUI(new Runnable() {
                    @Override
                    public void run() {
                        webFragment.popBtn(null);
                    }
                });

            }
        }

        @JavascriptInterface
        @Override
        public void phoneNumber(String jsInterfaceInfo) {
            final JsInterfaceInfo mInterfaceInfo = mGson.fromJson(jsInterfaceInfo, JsInterfaceInfo.class);
            final WebFragment webFragment = mFragmentWeakReference.get();
            if (webFragment != null) {
                webFragment.runToUI(new Runnable() {
                    @Override
                    public void run() {
                        webFragment.phoneNumber(mInterfaceInfo);
                    }
                });

            }
        }

        @JavascriptInterface
        public void textBtn(String jsInterfaceInfo) {
            final JsInterfaceInfo mInterfaceInfo = mGson.fromJson(jsInterfaceInfo, JsInterfaceInfo.class);
            final WebFragment webFragment = mFragmentWeakReference.get();
            if (webFragment != null) {
                webFragment.runToUI(new Runnable() {
                    @Override
                    public void run() {
                        webFragment.textBtn(mInterfaceInfo);
                    }
                });

            }
        }

    }

    protected void textBtn(JsInterfaceInfo jsInterfaceInfo) {


    }

    protected void phoneNumber(JsInterfaceInfo jsInterfaceInfo) {

    }


    public void runToUI(Runnable runnable)

    {
        getActivity().runOnUiThread(runnable);

    }

    /**
     * 返回界面刷新回调
     *
     * @param jsInterfaceInfo
     */
    protected void popBtn(JsInterfaceInfo jsInterfaceInfo) {


    }

    /**
     * 查看大图js调用方法
     *
     * @param jsInterfaceInfo
     */
    protected void imageBtn(JsInterfaceInfo jsInterfaceInfo) {


    }

    /**
     * 跳转到下个js调用携带参数，可自行选择是否重写
     *
     * @param jsInterfaceInfo
     */
    protected void pushBtn(JsInterfaceInfo jsInterfaceInfo) {


    }

    /**
     * 空的方法需要的子类自行实现
     *
     * @param jsInterfaceInfo
     */
    protected void titleName(JsInterfaceInfo jsInterfaceInfo) {

    }

    @JavascriptInterface
    @Override
    protected void onInitData() {

        mChildWebView = getWebView();
        FragmentActivity activity = getActivity();
        if (mChildWebView == null) return;

        if (activity instanceof WebActivity) {
            WebActivity webActivity = (WebActivity) activity;
            webActivity.setOnWebPageBackListener(this);
            setOnWebShowListener(webActivity);
        }
        mChildWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mChildWebView.requestFocusFromTouch();
        mChildWebView.getSettings().setJavaScriptEnabled(true);

        mChildWebView.getSettings().setDefaultTextEncodingName("UTF-8");

//        if (Build.VERSION.SDK_INT >= 19) {
//            mChildWebView.getSettings().setLoadsImagesAutomatically(true);
//        } else {
//            mChildWebView.getSettings().setLoadsImagesAutomatically(false);
//        }
        //设置滚动条的风格0代表不占用空间在网页的上面
        mChildWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        mChildWebView.getSettings().setUseWideViewPort(true);
        mChildWebView.getSettings().setLoadWithOverviewMode(true);
//
        mChildWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mChildWebView.getSettings().setAllowFileAccess(true);  //设置可以访问文件

        mChildWebView.getSettings().setNeedInitialFocus(true);

    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected int getLayoutResource() {
        return 0;
    }


    private void destroyWebView() {

        if (getActivity() instanceof WebActivity) {
            WebActivity webActivity = (WebActivity) getActivity();
            webActivity.setOnWebPageBackListener(null);
            setOnWebShowListener(null);
        }
        if (mChildWebView != null) {
            ViewParent parent = mChildWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mChildWebView);
            }
            mChildWebView.stopLoading();
            mChildWebView.getSettings().setJavaScriptEnabled(false);
            mChildWebView.clearHistory();
            mChildWebView.clearView();
            mChildWebView.removeAllViews();


//            CookieSyncManager.createInstance(getContext());  //Create a singleton CookieSyncManager within a context
//            CookieManager cookieManager = CookieManager.getInstance(); // the singleton CookieManager instance
//            cookieManager.removeAllCookie();// Removes all cookies.
//            CookieSyncManager.getInstance().sync(); // forces sync manager to sync now

            mChildWebView.setWebChromeClient(null);
            mChildWebView.setWebViewClient(null);

            mChildWebView.clearCache(true);

            try {
                mChildWebView.destroy();
            } catch (Throwable throwable) {

            }
            mChildWebView = null;

        }

    }

    @Override
    public void onWebPageBack(boolean isWebGoBack) {
        if (mChildWebView != null && mChildWebView.canGoBack() && isWebGoBack) {
            mChildWebView.goBack();
        } else {
            finish();
        }
    }
}
