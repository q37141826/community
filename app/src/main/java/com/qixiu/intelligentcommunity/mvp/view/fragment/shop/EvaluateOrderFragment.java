package com.qixiu.intelligentcommunity.mvp.view.fragment.shop;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.application.BaseApplication;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.IntentDataKeyConstant;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.store.order.OrderBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.fragment.base.BaseFragment;
import com.qixiu.intelligentcommunity.mvp.view.widget.NoScrollGridView;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.my_interface.shop.WeakReferenceInterface;
import com.qixiu.intelligentcommunity.utlis.PictureCut;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/5/4 0004.
 */

public class EvaluateOrderFragment extends BaseFragment implements AdapterView.OnItemClickListener, WeakReferenceInterface, View.OnClickListener, OKHttpUIUpdataListener<BaseBean> {
    private String permissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    private static final String EMPTY_PATH = "";
    private NoScrollGridView mGv_evluateorder;
    private EditText mEt_content;
    private static int MAX_IMAGE = 4;
    private ArrayList<String> selectPhotos = new ArrayList<>();
    private ImageSelectAdapter mImageSelectAdapter;
    private ImageCaptureManager captureManager;
    private Button mBt_submit_comment;
    private OKHttpRequestModel mOkHttpRequestModel;
    private String mGoods_id;
    private String order_id;
    private ZProgressHUD zprogress;
    OrderBean.OBean.ListBean orderBean;
    @Override
    protected void onInitData() {
        Bundle arguments = getArguments();
        mGoods_id = arguments.getString(IntentDataKeyConstant.GOODS_ID);
        order_id = arguments.getString(IntentDataKeyConstant.ORDER_ID);
        orderBean = arguments.getParcelable("data");

        captureManager = new ImageCaptureManager(getContext());
        mOkHttpRequestModel = new OKHttpRequestModel(this);

    }

    @Override
    protected void onInitView(View view) {
        mImageSelectAdapter = new ImageSelectAdapter(this);
        mGv_evluateorder = findViewById(R.id.gv_evluateorder);
        mBt_submit_comment = findViewById(R.id.bt_submit_comment);
        mBt_submit_comment.setOnClickListener(this);
        mEt_content = findViewById(R.id.et_content);
        mGv_evluateorder.setOnItemClickListener(this);
        mGv_evluateorder.setAdapter(mImageSelectAdapter);
        zprogress = new ZProgressHUD(getContext());

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_evluateorder;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String path = (String) parent.getItemAtPosition(position);
        if (TextUtils.isEmpty(path)) {
            if (selectPhotos.size() < MAX_IMAGE) {
                PhotoPicker.builder().setPhotoCount(MAX_IMAGE).setShowCamera(true).setSelected(
                        selectPhotos).start(getContext(), this);
            }
        } else {
            PhotoPreview.builder().setPhotos(selectPhotos).setCurrentItem(position).start(
                    getContext(), this);
        }
    }


    @Override
    public Object callBack() {
        return selectPhotos;
    }

    @Override
    public void onClick(View v) {
        //存储权限
        if (!hasPermission(getContext(),permissions)) {
            hasRequse(getActivity(),0, permissions);
            return;
        }
        //提交评论
        if (mEt_content.getText() != null && !TextUtils.isEmpty(mEt_content.getText().toString())) {
            mEt_content.getText().toString();
            final Map<String, String> requestMap = new HashMap<>();
            final Map<String, File> requestFileMap = new HashMap<>();
            requestMap.put(StringConstants.STRING_USERID, Preference.get(IntentDataKeyConstant.ID, StringConstants.STRING_EMPTY));
            if (mGoods_id != null) {
                requestMap.put(IntentDataKeyConstant.GOODS_ID, mGoods_id);
            }
            if (order_id != null) {
                requestMap.put(IntentDataKeyConstant.ORDER_ID, order_id);
            }
            zprogress.show();
            requestMap.put(StringConstants.STRING_USERNAME, Preference.get(IntentDataKeyConstant.NICKNAME, StringConstants.STRING_EMPTY));
            requestMap.put("content", mEt_content.getText().toString());
            if (selectPhotos.size() == 0) {
                mOkHttpRequestModel.okhHttpPost(ConstantUrl.GOODS_EVALUATE, requestMap, new BaseBean(), requestFileMap, true);
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i <selectPhotos.size() ; i++) {
//                            Bitmap smallBitmap = BitmapFactory.decodeFile(selectPhotos.get(i));
                            String path = PictureCut.getThubImage(selectPhotos.get(i));
                            File file = new File(path);
                            if (file.exists()) {
                                requestFileMap.put(StringConstants.STRING_IMG + (i + 1),file);
                            }
                        }
                        mOkHttpRequestModel.okhHttpPost(ConstantUrl.GOODS_EVALUATE, requestMap, new BaseBean(), requestFileMap, true);
                    }
                }).start();

            }
        } else {
            ToastUtil.showToast(BaseApplication.getContext(), R.string.input_content);
        }


    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        ToastUtil.showToast(BaseApplication.getContext(), "提交评论成功");
        zprogress.dismiss();
        Intent intent = new Intent(IntentDataKeyConstant.BROADCAST_COMMENTS_OK);
        intent.putExtra(IntentDataKeyConstant.ID,order_id);
        getContext().sendBroadcast(intent);
        orderBean.setIs_common("2");
        getActivity().finish();
    }

    @Override
    public void onError(Call call, Exception e, int i) {

    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {

    }

    private static class ImageSelectAdapter extends BaseAdapter {

        private final WeakReference<WeakReferenceInterface> mWeakReferenceInterfaceWeakReference;

        public ImageSelectAdapter(WeakReferenceInterface weakReference) {

            mWeakReferenceInterfaceWeakReference = new WeakReference<WeakReferenceInterface>(weakReference);

        }

        List<String> mImageStrings = new ArrayList<>();

        public void setData(List<String> data) {
            if (mImageStrings.size() > 0) {
                mImageStrings.clear();
            }
            mImageStrings.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mImageStrings.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            if (mImageStrings != null && position < mImageStrings.size()) {
                return mImageStrings.get(position);
            }
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageSelectHolder holder;
            if (convertView == null) {
                convertView = View.inflate(BaseApplication.getContext(), R.layout.items_goodsdetail_pictrue, null);
                holder = new ImageSelectHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ImageSelectHolder) convertView.getTag();
            }
            // 如果是最后一个则直接返回
            if (position == mImageStrings.size()) {
                Glide.with(BaseApplication.getContext()).load(R.mipmap.upload_pic2x).into(holder.getImageView());
                //@author gyh限制最大张数时不显示gridView的添加图片的图标

                if (((ArrayList<String>) mWeakReferenceInterfaceWeakReference.get().callBack()).size() >= MAX_IMAGE) {
                    holder.getSquareView().setVisibility(View.GONE);

                } else {
                    holder.getSquareView().setVisibility(View.VISIBLE);

                }

            } else {

                Glide.with(BaseApplication.getContext()).load(mImageStrings.get(position)).into(holder.getImageView());
            }


            return convertView;
        }


        private static class ImageSelectHolder {

            private final ImageView mIv_picture;
            private final View mCcl_item;

            public ImageSelectHolder(View contentView) {
                mIv_picture = (ImageView) contentView.findViewById(R.id.iv_picture);
                mCcl_item = contentView.findViewById(R.id.ccl_item);

            }

            public ImageView getImageView() {

                return mIv_picture;
            }

            public View getSquareView() {

                return mCcl_item;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

                selectPhotos.remove(EMPTY_PATH);
                //@author gyh 限制如果达到最大张数拍照后的图片就不添加
                if (selectPhotos.size() < MAX_IMAGE) {
                    selectPhotos.add(photoPath);
                    //   selectedBitmap.add(revolvePicture(photoPath));//将拍照获得照片转成Bitmap。添加到集合中
                } else {
                    ToastUtil.showToast(BaseApplication.getContext(), "已经添加了4张图片");
                }

            }

            mImageSelectAdapter.setData(selectPhotos);

        }

//        else if (requestCode == REQUEST_CODE) {
//            //以后可能自己做的操作
//            if (data != null) {
//
//            }
//        }

    }
}
