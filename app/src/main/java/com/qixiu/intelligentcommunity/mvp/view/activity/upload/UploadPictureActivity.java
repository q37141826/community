package com.qixiu.intelligentcommunity.mvp.view.activity.upload;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.TitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.upload.UpLoadPictureAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.utlis.AsyncTaskFactory;
import com.qixiu.intelligentcommunity.utlis.PictureCut;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Call;

/**
 * Created by gyh on 2017/6/30 0030.
 * TODO 注意！由于上传图片采用Base64方式，那么压缩图片到byte64的字节量很大，一定要放到异步里面去做，而后面的后网络请求也放到异步里面
 * TODO，从而回调就需要在主线程里面做了。
 */

public abstract class UploadPictureActivity extends TitleActivity implements OnRecyclerItemListener, OKHttpUIUpdataListener, AsyncTaskFactory.AsyncTaskInterface<Object, Object, String> {

    private ImageCaptureManager captureManager;
    private UpLoadPictureAdapter mRcAdapter;
    private int maxPictureCount = 9;
    protected ArrayList<String> selectPhotos = new ArrayList<>();
    private RecyclerView mRecyclerView;


    private OKHttpRequestModel mOkHttpRequestModel;
    protected ZProgressHUD mZProgressHUD;
    private Map<String, String> mMap;
    private String mUrl;

    /**
     * 作为该类的子类该方法就不要重写了，请在initUpLoadView来做初始化view的操作
     */
    @Override
    protected void onInitView() {
        super.onInitView();
        mZProgressHUD = new ZProgressHUD(this);
        mZProgressHUD.setMessage("发布中...");
        initUpLoadView();
        mRecyclerView = getRecyclerView();
        if (mRecyclerView == null) return;
        mOkHttpRequestModel = new OKHttpRequestModel(this);
        captureManager = new ImageCaptureManager(this);
        mRcAdapter = new UpLoadPictureAdapter();
        mRcAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mRcAdapter);
    }


    protected void requestUpLoad(String url, Map<String, String> map) {
        this.mUrl = url;

        if (map == null) {

            mMap = new HashMap();
        } else {
            mMap = map;
        }
        String uid = Preference.get(IntentDataKeyConstant.ID, StringConstants.EMPTY_STRING);
        if (!TextUtils.isEmpty(uid)) {
            mMap.put(IntentDataKeyConstant.UID_KEY, uid);
        }

        final StringBuffer images = new StringBuffer();
        try {
            PictureCut.CompressImage.callBase64s(selectPhotos, this, new PictureCut.CompressImage.ImageCallBackBase64s() {
                @Override
                public void callBackbitmapBase64s(List<String> base64s) {
                    for (int i = 0; i < base64s.size(); i++) {
                        images.append(base64s.get(i));
                        if (i < selectPhotos.size() - 1) {
                            images.append("|");
                        }
                    }
                    String s = images.toString();
                    if (!TextUtils.isEmpty(s)) {
                        mMap.put(StringConstants.IMGS_STRING, s);
                    }
                    AsyncTask asyncTask = AsyncTaskFactory.CreateDefaultAsyncTask(UploadPictureActivity.this);
                    asyncTask.execute();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void setMaxPictureCount(int maxPictureCount) {
        if (maxPictureCount <= 0) {
            this.maxPictureCount = 9;
        } else {
            this.maxPictureCount = maxPictureCount;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE) {
                //获取图片选择器的图片路径们
                if (data != null) {

                    selectPhotos.clear();
//                    selectedBitmap.clear();
                    List<String> photos =
                            data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                   /* if (photos != null && photos.size() > 0)
                    {

                        for (int i = 0; i < photos.size() ; i++)
                        {
                            Bitmap bitmap = revolvePicture(photos.get(i));
                            selectedBitmap.add(bitmap);
                        }*/
                    selectPhotos.addAll(photos);
                    /*}*/
                }
            } else if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO) {
                captureManager.galleryAddPic();
                String photoPath = captureManager.getCurrentPhotoPath();

                selectPhotos.remove(StringConstants.EMPTY_STRING);
                //@author gyh 限制如果达到最大张数拍照后的图片就不添加
                if (selectPhotos.size() < maxPictureCount) {
                    selectPhotos.add(photoPath);
                    //   selectedBitmap.add(revolvePicture(photoPath));//将拍照获得照片转成Bitmap。添加到集合中
                } else {
                    ToastUtil.toast("已经添加了" + maxPictureCount + "张图片");
                }

            }

            mRcAdapter.refreshData(selectPhotos);

        }
    }

    protected abstract void onUpLoad();

    public abstract void initUpLoadView();

    public abstract RecyclerView getRecyclerView();

    @Override
    public void onItemClick(View v, Object data) {
        int position = mRecyclerView.getChildLayoutPosition(v);
        if (position == mRcAdapter.getDatas().size()) {
            if (selectPhotos.size() < maxPictureCount) {
                PhotoPicker.builder().setPhotoCount(maxPictureCount).setShowCamera(true).setSelected(
                        selectPhotos).start(this);
            }
        } else {
            PhotoPreview.builder().setPhotos(selectPhotos).setCurrentItem(position).start(
                    this);
        }
    }

    @Override
    public void onSuccess(Object data, int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mZProgressHUD.isShowing()) {
                    mZProgressHUD.dismissWithSuccess("发布成功");
                }
                /**
                 * TODO 注意！这里由于dismissWithSuccess内部是利用AsyncTask 设置消息后延迟500毫秒才关闭掉，直接finish会异常
                 */
                AsyncTask<String, Integer, Long> task = new AsyncTask<String, Integer, Long>() {

                    @Override
                    protected Long doInBackground(String... params) {
                        SystemClock.sleep(501);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Long result) {
                        super.onPostExecute(result);
                        String simpleName = getClass().getSimpleName();
                        finish();
                    }
                };
                task.execute();
            }
        });
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mZProgressHUD.isShowing()) {
                    mZProgressHUD.dismissWithFailure("发布失败");
                }

            }
        });
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mZProgressHUD.isShowing()) {
                    mZProgressHUD.dismissWithFailure("发布失败");
                }
            }
        });
    }

    @Override
    public String doInBackground(Object... params) {
//        final StringBuffer images = new StringBuffer();
//        for (int i = 0; i < selectPhotos.size(); i++) {
//            try {
//                final int finalI = i;
//                PictureCut.CompressImage.callBitmap(selectPhotos.get(i), this, new PictureCut.CompressImage.ImageCallBackBitmap() {
//                    @Override
//                    public void callBackbitmap(Bitmap bitmap) {
//                        if (bitmap != null) {
//                            images.append(ImgHelper.bitmap2StrByBase64(bitmap));
//                            if (finalI < selectPhotos.size() - 1) {
//                                images.append("|");
//                            }
//                        }
//                    }
//                });
////                Bitmap bitmap = ImgHelper.revolvePicture(selectPhotos.get(i));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String s = images.toString();
//        if (!TextUtils.isEmpty(s)) {
//            mMap.put(StringConstants.IMGS_STRING, s);
//        }
        mOkHttpRequestModel.okhHttpPost(mUrl, mMap, new BaseBean(), false);
        return "";
    }

    @Override
    public void onPreExecute() {

    }

    @Override
    public void onPostExecute(String s) {
//        if (!TextUtils.isEmpty(s)) {
//            mMap.put(StringConstants.IMGS_STRING, s);
//        }
//        mOkHttpRequestModel.okhHttpPost(mUrl, mMap, new BaseBean(), false);
        mMap.clear();
        mMap = null;
    }

    @Override
    public void onProgressUpdate(Object... values) {

    }
}
