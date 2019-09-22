package com.qixiu.intelligentcommunity.mvp.view.activity.mine.myprofile;


import android.Manifest;
import android.content.Intent;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.beans.MessageBean;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.titleview.TitleView;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.PictureCut;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Call;

public class MyprofileActivity extends BaseActivity implements View.OnClickListener, TextWatcherAdapterInterface {
    private RelativeLayout relativeLayout_title_myprofile, relativeLayout_changehead, relativeLayout_changenickname;
    private TitleView titleview;
    private TextView textView_nickname_change;
    private PopupWindow popwindow;
    //图片选择器参数
    ArrayList<String> selectPhotos = new ArrayList<>();
    int MAX_IMAGE = 1;
    private ImageCaptureManager captureManager;
    //图片路径
    String photoPath;
    private static final String EMPTY_PATH = StringConstants.EMPTY_STRING;
    private CircleImageView circular_head_edit;
    //照相机权限
    String permissions[] = {Manifest.permission.CAMERA};
    private IntelligentTextWatcher mIntelligentTextWatcher;
    private ZProgressHUD zProgressHUD;
    @Override
    protected void onInitData() {
        zProgressHUD=new ZProgressHUD(this);
    }

    @Override
    protected void onInitView() {
        relativeLayout_changehead = (RelativeLayout) findViewById(R.id.relativeLayout_changehead);
        relativeLayout_changenickname = (RelativeLayout) findViewById(R.id.relativeLayout_changenickname);
        relativeLayout_title_myprofile = (RelativeLayout) findViewById(R.id.relativeLayout_title_myprofile);
        circular_head_edit = (CircleImageView) findViewById(R.id.circular_head_edit);
        textView_nickname_change = (TextView) findViewById(R.id.textView_nickname_change);
        initTitle();
        setClick();
    }

    private void setClick() {
        relativeLayout_changehead.setOnClickListener(this);
        relativeLayout_changenickname.setOnClickListener(this);
    }

    private void initTitle() {
        titleview = new TitleView(this);
        titleview.setTitle("编辑资料");
        relativeLayout_title_myprofile.addView(titleview.getView());
        titleview.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titleview.setTitle_textColor(getResources().getColor(R.color.white));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_myprofile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relativeLayout_changehead:
                if (!hasPermission(permissions)) {
                    hasRequse(1, permissions);
                    return;
                }

                    PhotoPicker.builder().setPhotoCount(MAX_IMAGE).setShowCamera(true).setSelected(
                            selectPhotos).start(this);

                break;

            case R.id.relativeLayout_changenickname:
                showEditPopwindow();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    if (selectPhotos.size() < MAX_IMAGE) {
                        circular_head_edit.setImageResource(R.mipmap.authorization_upload_photo);

                    } else {
                        Glide.with(this).load(selectPhotos.get(0)).crossFade().into(circular_head_edit);
                        //上传图片修改图像
                        startUploadHead(selectPhotos.get(0));
                    }
                }
            } else if (requestCode == ImageCaptureManager.REQUEST_TAKE_PHOTO) {
                captureManager.galleryAddPic();
                photoPath = captureManager.getCurrentPhotoPath();

                selectPhotos.remove(EMPTY_PATH);
                //@author gyh 限制如果达到最大张数拍照后的图片就不添加
                if (selectPhotos.size() < MAX_IMAGE) {
                    selectPhotos.add(photoPath);
                    //   selectedBitmap.add(revolvePicture(photoPath));//将拍照获得照片转成Bitmap。添加到集合中
                }

            }


        }
    }

    private void startUploadHead(final String path) {
        zProgressHUD.show();
        PictureCut.CompressImageWithThumb.callBase64(selectPhotos.get(0), new PictureCut.CompressImageWithThumb.CallBackBase64() {
            @Override
            public void callBack(String base64) {
                OkHttpUtils.post().url(ConstantUrl.changeHeaddUrl)
                        .addParams("uid", Preference.get(ConstantString.USERID, ""))
                        .addParams("head", base64)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {
                        zProgressHUD.dismiss();
                    }

                    @Override
                    public void onResponse(String s, int i) {
                        zProgressHUD.dismiss();
                        try {
                            MessageBean messageBean = GetGson.parseMessageBean(s);
                            Preference.put(ConstantString.HEAD, path);
                            Glide.with(MyprofileActivity.this).load(Preference.get(ConstantString.HEAD, "")).into(circular_head_edit);
                            ToastUtil.showToast(MyprofileActivity.this, messageBean.getM());
                        } catch (Exception e) {

                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this).load(Preference.get(ConstantString.HEAD, "")).into(circular_head_edit);
        textView_nickname_change.setText(Preference.get(ConstantString.NICK_NAME, "").equals("") ? Preference.get(ConstantString.PHONE, "") : Preference.get(ConstantString.NICK_NAME, ""));
    }

    EditText editText_edituser;

    private void showEditPopwindow() {
        View view = View.inflate(this, R.layout.popwindow_edituser2, null);
        try {
            popwindow = new PopupWindow(view);
            popwindow.setFocusable(true);
            popwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popwindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            popwindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        } catch (Exception e) {
        }
        RelativeLayout relativeLayout_pop_dismiss = (RelativeLayout) view.findViewById(R.id.relativeLayout_pop_dismiss);
        relativeLayout_pop_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow.dismiss();
            }
        });
        editText_edituser = (EditText) view.findViewById(R.id.editText_edituser);
        editText_edituser.setText(textView_nickname_change.getText().toString());
        editText_edituser.setSelection(textView_nickname_change.getText().toString().length());
        mIntelligentTextWatcher = new IntelligentTextWatcher(editText_edituser.getId(), this);
        mIntelligentTextWatcher.setEmojiDisabled(true, editText_edituser);
        editText_edituser.addTextChangedListener(mIntelligentTextWatcher);
        Button button_cancleEdit = (Button) view.findViewById(R.id.button_cancleEdit);
        Button button_confirmEdit = (Button) view.findViewById(R.id.button_confirmEdit);
        button_cancleEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popwindow.dismiss();
            }
        });
        button_confirmEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText_edituser.getText().toString().length() > 20) {
                    ToastUtil.showToast(MyprofileActivity.this, "输入字数不得超过20位");
                    return;
                }
                if (editText_edituser.getText().toString().trim().equals("")) {
                    ToastUtil.showToast(MyprofileActivity.this, "未传入昵称");
                    return;
                }
                startChangeNIckname(editText_edituser.getText().toString().trim());
            }
        });
    }

    private void startChangeNIckname(final String nickname) {
        OkHttpUtils.post().url(ConstantUrl.changeNickUrl)
                .addParams("uid", Preference.get(ConstantString.USERID, ""))
                .addParams("nickname", nickname)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {
                popwindow.dismiss();
            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    MessageBean messageBean = GetGson.parseMessageBean(s);
                    ToastUtil.showToast(MyprofileActivity.this, messageBean.getM());
                    if (messageBean.getC() == 1) {
                        textView_nickname_change.setText(nickname);
                        Preference.put(ConstantString.NICK_NAME, nickname);
                    }
                    popwindow.dismiss();
                } catch (Exception e) {
                }
            }
        });

    }

    @Override
    public void beforeTextChanged(int editTextId, CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(int editTextId, CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(int editTextId, Editable s) {

    }


    @Override
    protected void onDestroy() {
        if (editText_edituser != null && mIntelligentTextWatcher != null) {
            editText_edituser.removeTextChangedListener(mIntelligentTextWatcher);
        }
        super.onDestroy();
    }
}
