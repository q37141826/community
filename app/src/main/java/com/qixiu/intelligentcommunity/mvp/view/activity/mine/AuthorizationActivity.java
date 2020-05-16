package com.qixiu.intelligentcommunity.mvp.view.activity.mine;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.IntelligentTextWatcher;
import com.qixiu.intelligentcommunity.listener.weakreferences.TextWatcherAdapterInterface;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.beans.identify.BuildingBean;
import com.qixiu.intelligentcommunity.mvp.beans.identify.EstateBean;
import com.qixiu.intelligentcommunity.mvp.beans.identify.PeriodsBean;
import com.qixiu.intelligentcommunity.mvp.beans.identify.UnitBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.main.MainActivity;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.my_alterdialog.ArshowDialog;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForSelect;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.PopoItemBean;
import com.qixiu.intelligentcommunity.utlis.AppManager;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.GetGson;
import com.qixiu.intelligentcommunity.utlis.IdcardUtils;
import com.qixiu.intelligentcommunity.utlis.MD5Util;
import com.qixiu.intelligentcommunity.utlis.PictureCut;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Call;

/**
 * Created by Administrator on 2017/6/15 0015.
 */

public class AuthorizationActivity extends NewTitleActivity implements OKHttpUIUpdataListener<BaseBean>, TextWatcherAdapterInterface {
    private ArrayList<String> selectPhotos = new ArrayList<>();
    private ImageView mIv_upload_picture;
    private static int MAX_IMAGE = 1;
    private static final String EMPTY_PATH = StringConstants.EMPTY_STRING;
    private ImageCaptureManager captureManager;
    private View mRl_user_identity;
    //用户身份edit
    private EditText mEt_user_identity;
    //用户名edit
    private EditText mEt_user_name;
    //所在小区deit
    private EditText mEt_place_plot;
    //所在期数edit
    private EditText mEt_place_periods;
    //所在楼栋edit
    private EditText mEt_place_building;
    //单元号码eidt
    private EditText mEt_element_number;
    //身份证号
    private EditText et_obligate_phone_number;
    private EditText mEt_identity_card_number;
    private EditText mEt_doorplate_number;

    private RelativeLayout relativelayout_phone_identify;
    private View mRl_place_plot;
    private View mRl_place_periods;
    private View mRl_place_building;
    private View mRl_element_number;

    //点即展开的图标
    ImageView iv_user_identity_icon;
    ImageView iv_place_plot_icon, iv_place_periods_icon, iv_place_building_icon, iv_element_number_icon;

    //弹出来的窗
    MyPopForSelect pop;

    //展开供选择的参数
    int estate_id = -1;//小区id
    int period_id = -1;//期数id
    int building_id = -1;//楼栋id
    int unit_id = -1;
    //展开供选择的数据集
    List<String> estates = new ArrayList<>();//小区名称
    Map<String, Integer> estatemap = new HashMap<>();//小区id
    List<PopoItemBean> periods = new ArrayList<>();//房子期数
    Map<String, Integer> periodmap = new HashMap<>();
    List<PopoItemBean> buildings = new ArrayList<>();//楼栋
    Map<String, Integer> buildingmap = new HashMap<>();
    List<PopoItemBean> units = new ArrayList<>();//单元号
    Map<String, Integer> unitsmap = new HashMap<>();

    //人脸照片路径
    String photoPath = "";

    //提交认证的按钮
    Button btn_start_identify;
    String password, phone;//传过来的密码
    String oldPassword;
    //第三方登录的key
    String appkey;
    Map<String, String> mapNotice;//参数未填写时候的提示语
    private IntelligentTextWatcher mUserNametTextWatcher;
    private IntelligentTextWatcher mDoorplateTextWatcher;
    private IntelligentTextWatcher mIdentityCardTextWatcher;
    private IntelligentTextWatcher mObligatePhoneTextWatcher;
    private OKHttpRequestModel okmodel;
    private ZProgressHUD zProgressHUD;

    @Override
    public void onClick(View view) {
        PopoItemBean bean;
        switch (view.getId()) {
            case R.id.iv_upload_picture:
                if (selectPhotos.size() < MAX_IMAGE) {
                    PhotoPicker.builder().setPhotoCount(MAX_IMAGE).setShowCamera(true).setSelected(
                            selectPhotos).start(this);
                } else {
                    PhotoPreview.builder().setPhotos(selectPhotos).setCurrentItem(0).start(
                            this);
                }
                break;
            case R.id.rl_user_identity:
            case R.id.et_user_identity:
//                if (TextUtils.isEmpty(mEt_user_identity.getText().toString())) {
//                    mEt_user_identity.setText("业主");
//
//                } else {
//                    mEt_user_identity.setText(StringConstants.EMPTY_STRING);
//                }
                List<PopoItemBean> list = new ArrayList<>();
                bean = new PopoItemBean();
                bean.setText("亲友");
                list.add(bean);
                bean = new PopoItemBean();
                bean.setText("租客");
                list.add(bean);
                bean = new PopoItemBean();
                bean.setText("业主");
                list.add(bean);
                pop = new MyPopForSelect(list, R.layout.layout_popupwindow_selected, this, mEt_user_identity, new MyPopForSelect.Pop_itemSelectListener() {
                    @Override
                    public void getSelectedString(PopoItemBean data) {
                        getSelected(data.getText(), mEt_user_identity);
                    }
                });
                break;
            case R.id.rl_place_plot:
            case R.id.et_place_plot:
                list = new ArrayList<>();
                for (int i = 0; i < estates.size(); i++) {
                    bean = new PopoItemBean();
                    bean.setText(estates.get(i));
                    list.add(bean);
                }
                if(estates.size()==0){
                    getEstatData();
                }
                pop = new MyPopForSelect(list, R.layout.layout_popupwindow_selected, this, mEt_place_plot, new MyPopForSelect.Pop_itemSelectListener() {
                    @Override
                    public void getSelectedString(PopoItemBean data) {
                        getSelected(data.getText(), mEt_place_plot);
                    }
                });
                break;
            case R.id.rl_place_periods:
            case R.id.et_place_periods:
                if (estate_id == -1) {
                    List<PopoItemBean> listString = new ArrayList<>();
                    bean = new PopoItemBean();
                    bean.setText("请选择小区");
                    listString.add(bean);
                    pop = new MyPopForSelect(listString, R.layout.layout_popupwindow_selected, this, mEt_place_periods, new MyPopForSelect.Pop_itemSelectListener() {
                        @Override
                        public void getSelectedString(PopoItemBean data) {

                        }
                    });
                } else {
                    //获取期数
                    getPriodsData();
                }
                break;
            case R.id.rl_place_building:
            case R.id.et_place_building:
                if (period_id == -1) {
                    List<PopoItemBean> listString = new ArrayList<>();
                    bean = new PopoItemBean();
                    bean.setText("请选择期数");
                    listString.add(bean);
                    pop = new MyPopForSelect(listString, R.layout.layout_popupwindow_selected, this, mEt_place_building, new MyPopForSelect.Pop_itemSelectListener() {
                        @Override
                        public void getSelectedString(PopoItemBean data) {
                            getSelected(data.getText(), mEt_place_building);

                        }
                    });
                } else {
                    //获取东别
                    getBuildingData();
                }
                break;
            case R.id.rl_element_number:
            case R.id.et_element_number:
                if (building_id == -1) {
                    List<PopoItemBean> listString = new ArrayList<>();
                    bean = new PopoItemBean();
                    bean.setText("请选择栋别");
                    listString.add(bean);
                    pop = new MyPopForSelect(listString, R.layout.layout_popupwindow_selected, this, mEt_element_number, new MyPopForSelect.Pop_itemSelectListener() {
                        @Override
                        public void getSelectedString(PopoItemBean data) {
                            getSelected(data.getText(), mEt_element_number);

                        }
                    });
                } else {
                    //获取单元号
                    getUnitData();
                }
                break;
            case R.id.btn_start_identify:
                //提示语装填
                mapNotice = new HashMap<>();
                mapNotice.put("period", "请选择期数");
                mapNotice.put("building", "请选择栋别");
                mapNotice.put("unit", "请选择单元");
                mapNotice.put("utype", "请选择身份");
                mapNotice.put("hnum", "请填写门牌号");
                mapNotice.put("reserv_phone", "请填写预留电话");
                mapNotice.put("phone", "请填写预留电话");
                mapNotice.put("true_name", "请填写真实姓名");
                mapNotice.put("idcard", "请填写身份证号码");
                mapNotice.put("face_img", "请传入正脸照");
                startIdentifyfy();
                break;
        }
    }

    private void startIdentifyfy() {
        oldPassword = password + "";
        String estate = estate_id + "";
        String utype = "";
        if (mEt_user_identity.getText().toString().equals("亲友")) {
            utype = 2 + "";
        } else if (mEt_user_identity.getText().toString().equals("租客")) {
            utype = 3 + "";
        } else if (mEt_user_identity.getText().toString().equals("业主")) {
            utype = 1 + "";
        }
        String idcard = mEt_identity_card_number.getText().toString();
        //业主选填
        String period = mEt_place_periods.getText().toString();
        String building = mEt_place_building.getText().toString();
        String unit = mEt_element_number.getText().toString();
        String hnum = mEt_doorplate_number.getText().toString();
        //亲友租客选填
        String reserv_phone = et_obligate_phone_number.getText().toString();
        //
        String true_name = mEt_user_name.getText().toString();
        //获取手机设备号
        String device = deviceId;
        final Map<String, String> map = new HashMap<>();
        try {
            if (putKeyValueToMap(map, "phone", phone, true)) {
                return;
            }
            if (putKeyValueToMap(map, "password", password, true)) {
                return;
            }
        } catch (Exception e) {
        }
        if (appkey != null) {
            map.remove("phone");
            map.remove("password");
            map.put("third_id", appkey);
        }
        if (putKeyValueToMap(map, "estate", estate_id + "", true)) {
            return;
        }
        if (putKeyValueToMap(map, "utype", utype, true)) {
            return;
        }
        if (putKeyValueToMap(map, "true_name", true_name, true)) {
            return;
        }
        if (putKeyValueToMap(map, "idcard", idcard, true)) {
            return;
        }
        if (!IdcardUtils.validateCard(idcard)) {
            ToastUtil.showToast(AuthorizationActivity.this, "请输入合法的身份证号");
            return;
        }
        putKeyValueToMap(map, "device", device, true);
        putKeyValueToMap(map, "device_type", 2 + "", true);
        if (utype.equals("1")) {
            putKeyValueToMap(map, "period", period_id + "", false);
            putKeyValueToMap(map, "building", building_id + "", false);
            putKeyValueToMap(map, "unit", unit_id + "", false);
            putKeyValueToMap(map, "hnum", hnum, false);
            if (putKeyValueToMap(map, "reserv_phone", reserv_phone, true)) {
                return;
            }
        } else {

            if (putKeyValueToMap(map, "period", period_id + "", true)) {
                return;
            }
            if (putKeyValueToMap(map, "building", building_id + "", true)) {
                return;
            }
            if (putKeyValueToMap(map, "unit", unit_id + "", true)) {
                return;
            }
            if (putKeyValueToMap(map, "hnum", hnum, true)) {
                return;
            }
            putKeyValueToMap(map, "reserv_phone", reserv_phone, false);
        }
        if (selectPhotos.size() == 0) {
            ToastUtil.showToast(this, "请上传正面照");
            return;
        } else {
            PictureCut.CompressImageWithThumb.callBase64(selectPhotos.get(0), new PictureCut.CompressImageWithThumb.CallBackBase64() {
                @Override
                public void callBack(String base64) {
                    putKeyValueToMap(map, "face_img", base64, true);
                    BaseBean mesage = new BaseBean();
                    //做个转圈圈特效
                    zProgressHUD.show();
                    okmodel.okhHttpPost(ConstantUrl.identifyUrl, map, mesage, false);
                }
            });
        }


    }

    //进行参数填写的判断
    private boolean putKeyValueToMap(Map<String, String> map, String key, String value, boolean IS_NECESSARY) {
        if (IS_NECESSARY && value.equals("")) {
            try {
                ToastUtil.toast(mapNotice.get(key));
            } catch (Exception e) {
            }
            return true;
        }
        if (!value.equals("")) {
            map.put(key, value.trim());
        }
        return false;
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

                    selectPhotos.addAll(photos);
                    /*}*/
                    if (selectPhotos.size() < MAX_IMAGE) {
                        mIv_upload_picture.setImageResource(R.mipmap.authorization_upload_photo);

                    } else {
                        Glide.with(this).load(selectPhotos.get(0)).crossFade().error(R.mipmap.error_image).into(mIv_upload_picture);
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

    @Override
    protected void onInitView() {
        zProgressHUD = new ZProgressHUD(this);
        super.onInitView();
//        if (ib_back != null) {
//            ib_back.setVisibility(View.GONE);
//
//        }
        mIv_upload_picture = (ImageView) findViewById(R.id.iv_upload_picture);


//        TextView tv_title = (TextView) findViewById(R.id.tv_title);
//        View titile_main = findViewById(R.id.titile_main);
//        titile_main.setBackgroundColor(getResources().getColor(R.color.theme_color));
//        tv_title.setText(R.string.mine_material_write);

//        if (mTv_more != null) {
//            mTv_more.setText(StringConstants.EMPTY_STRING);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                mTv_more.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.authorization_material_close, 0, 0, 0);
//            } else {
//                mTv_more.setCompoundDrawables(getResources().getDrawable(R.mipmap.authorization_material_close), null, null, null);
//            }
//        }
//        mTv_more.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        mTitleView.setTitle("资料填写");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setRightText("跳过");
        mTitleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "跳过之后将以游客身份访问，无法使用APP完整功能";
                setDialog(message);

            }
        });
        captureManager = new ImageCaptureManager(getApplicationContext());
        mRl_user_identity = findViewById(R.id.rl_user_identity);
        mRl_place_plot = findViewById(R.id.rl_place_plot);
        mRl_place_periods = findViewById(R.id.rl_place_periods);
        mRl_place_building = findViewById(R.id.rl_place_building);
        mRl_element_number = findViewById(R.id.rl_element_number);
        iv_user_identity_icon = (ImageView) findViewById(R.id.iv_user_identity_icon);
        iv_place_plot_icon = (ImageView) findViewById(R.id.iv_place_plot_icon);
        iv_place_periods_icon = (ImageView) findViewById(R.id.iv_place_periods_icon);
        iv_place_building_icon = (ImageView) findViewById(R.id.iv_place_building_icon);
        iv_element_number_icon = (ImageView) findViewById(R.id.iv_element_number_icon);
        relativelayout_phone_identify = (RelativeLayout) findViewById(R.id.relativelayout_phone_identify);
        btn_start_identify = (Button) findViewById(R.id.btn_start_identify);
        initEditText();
        initListener();

    }

    private void setDialog(String message) {
        ArshowDialog.Builder builder = new ArshowDialog.Builder(mContext);
        builder.setCanceledOnTouchOutside(false);
        builder.setCancelable(false);
        builder.setMessage(message);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gotoMain();
                try {
                    AppManager.getAppManager().finishActivity(LoginActivity.class);
                    AppManager.getAppManager().finishActivity(RegisterActivity.class);
                } catch (Exception e) {
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void gotoMain() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("password", MD5Util.MD5(password));
        map.put("device", deviceId);
        map.put("device_type", 2 + "");
        okmodel.okhHttpPost(ConstantUrl.touristUrl, map, new BaseBean());
    }

    @Override
    protected void onInitViewNew() {
        okmodel = new OKHttpRequestModel(this);
    }

    private void initEditText() {
        //选择的，不可输入的Edit
        mEt_user_identity = (EditText) findViewById(R.id.et_user_identity);
        mEt_user_identity.setCursorVisible(false);
        mEt_place_plot = (EditText) findViewById(R.id.et_place_plot);
        mEt_place_plot.setCursorVisible(false);
        mEt_place_periods = (EditText) findViewById(R.id.et_place_periods);
        mEt_place_periods.setCursorVisible(false);
        mEt_place_building = (EditText) findViewById(R.id.et_place_building);
        mEt_place_building.setCursorVisible(false);
        mEt_element_number = (EditText) findViewById(R.id.et_element_number);
        mEt_element_number.setCursorVisible(false);


        //可输入的Edit
        mEt_user_name = (EditText) findViewById(R.id.et_user_name);
        mEt_doorplate_number = (EditText) findViewById(R.id.et_doorplate_number);
        mEt_identity_card_number = (EditText) findViewById(R.id.et_identity_card_number);
        et_obligate_phone_number = (EditText) findViewById(R.id.et_obligate_phone_number);

    }

    private void initListener() {
        btn_start_identify.setOnClickListener(this);
        iv_user_identity_icon.setOnClickListener(this);
        mIv_upload_picture.setOnClickListener(this);
        mRl_user_identity.setOnClickListener(this);
        mRl_place_plot.setOnClickListener(this);
        mRl_place_periods.setOnClickListener(this);
        mRl_place_building.setOnClickListener(this);
        mRl_element_number.setOnClickListener(this);


        mEt_user_identity.setOnClickListener(this);
        mEt_place_plot.setOnClickListener(this);
        mEt_place_periods.setOnClickListener(this);
        mEt_place_building.setOnClickListener(this);
        mEt_element_number.setOnClickListener(this);

        mUserNametTextWatcher = new IntelligentTextWatcher(mEt_user_name.getId(), this);
        mUserNametTextWatcher.setEmojiDisabled(true, mEt_user_name);
        mEt_user_name.addTextChangedListener(mUserNametTextWatcher);

        mDoorplateTextWatcher = new IntelligentTextWatcher(mEt_doorplate_number.getId(), this);
        mDoorplateTextWatcher.setEmojiDisabled(true, mEt_doorplate_number);
        mEt_doorplate_number.addTextChangedListener(mDoorplateTextWatcher);

        mIdentityCardTextWatcher = new IntelligentTextWatcher(mEt_identity_card_number.getId(), this);
        mIdentityCardTextWatcher.setEmojiDisabled(true, mEt_identity_card_number);
        mEt_identity_card_number.addTextChangedListener(mIdentityCardTextWatcher);

        mObligatePhoneTextWatcher = new IntelligentTextWatcher(et_obligate_phone_number.getId(), this);
        mObligatePhoneTextWatcher.setEmojiDisabled(true, et_obligate_phone_number);
        et_obligate_phone_number.addTextChangedListener(mObligatePhoneTextWatcher);
    }


    @Override
    protected void onInitData() {
        //获取可以选择的数据
        getEstatData();//小区数据
        //获取传过来的数据
        try {
            appkey = getIntent().getStringExtra("key");
        } catch (Exception e) {
        }
        try {
            Bundle bundle = getIntent().getExtras();
            phone = bundle.getString("phone");
            password = bundle.getString("password");
            String from = getIntent().getStringExtra(ConstantString.FROM_WHERE);
            if (from.equals("mine")) {
                mTitleView.setRightTextVisible(View.GONE);
            }
        } catch (Exception e) {
        }

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_authorization;
    }

    //设置要显示的内容

    public void getSelected(String data, TextView editText) {
        if ("请选择小区".equals(data) || "请选择期数".equals(data) || "请选择栋别".equals(data) || TextUtils.isEmpty(data)) {
            return;
        }

        editText.setText(data);
        //判断选择的是什么
        if (mEt_user_identity.getText().toString().equals("业主")) {
            relativelayout_phone_identify.setVisibility(View.VISIBLE);
        } else {
            relativelayout_phone_identify.setVisibility(View.GONE);
        }
        switch (editText.getId()) {
            case R.id.et_place_plot://如果选择的是小区id，那么吧小区的id拿出来
                estate_id = estatemap.get(data);
                mEt_element_number.setText("");
                mEt_place_building.setText("");
                mEt_place_periods.setText("");
                unit_id = -1;
                building_id = -1;
                period_id = -1;
                break;
            case R.id.et_place_periods:
                period_id = periodmap.get(data);
                mEt_element_number.setText("");
                mEt_place_building.setText("");
                unit_id = -1;
                building_id = -1;
                break;
            case R.id.et_place_building:
                mEt_element_number.setText("");
                building_id = buildingmap.get(data);
                unit_id = -1;
                break;
            case R.id.et_element_number:
                unit_id = unitsmap.get(data);
                break;
        }
    }

    public void getEstatData() {
        OkHttpUtils.post().url(ConstantUrl.estateUrl).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int i) {

            }

            @Override
            public void onResponse(String s, int i) {
                try {
                    EstateBean estateBean = GetGson.init().fromJson(s, EstateBean.class);
                    estates.clear();
                    for (int j = 0; j < estateBean.getO().size(); j++) {
                        estates.add(estateBean.getO().get(j).getName());
                        estatemap.put(estateBean.getO().get(j).getName(), estateBean.getO().get(j).getId());
                    }
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mEt_user_name != null && mUserNametTextWatcher != null) {
            mEt_user_name.removeTextChangedListener(mUserNametTextWatcher);
        }
        if (mEt_doorplate_number != null && mDoorplateTextWatcher != null) {
            mEt_doorplate_number.removeTextChangedListener(mDoorplateTextWatcher);
        }
        if (mEt_identity_card_number != null && mIdentityCardTextWatcher != null) {
            mEt_identity_card_number.removeTextChangedListener(mIdentityCardTextWatcher);
        }
        if (et_obligate_phone_number != null && mObligatePhoneTextWatcher != null) {
            et_obligate_phone_number.removeTextChangedListener(mObligatePhoneTextWatcher);
        }
        super.onDestroy();
    }

    public void getPriodsData() {
        OkHttpUtils.post().url(ConstantUrl.periodUrl)
                .addParams("estate_id", estate_id + "").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {
                        try {
                            PeriodsBean periodsBean = GetGson.init().fromJson(s, PeriodsBean.class);
                            periods.clear();
                            for (int j = 0; j < periodsBean.getO().size(); j++) {
                                PopoItemBean bean = new PopoItemBean();
                                bean.setText(periodsBean.getO().get(j).getName());
                                periods.add(bean);
                                periodmap.put(periodsBean.getO().get(j).getName(), periodsBean.getO().get(j).getId());
                            }
                            pop = new MyPopForSelect(periods, R.layout.layout_popupwindow_selected, AuthorizationActivity.this, mEt_place_periods, new MyPopForSelect.Pop_itemSelectListener() {
                                @Override
                                public void getSelectedString(PopoItemBean data) {
                                    getSelected(data.getText(), mEt_place_periods);

                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
    }

    public void getBuildingData() {
        OkHttpUtils.post().url(ConstantUrl.buildingUrl)
                .addParams("period_id", period_id + "").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {
                        try {
                            BuildingBean buildingsBean = GetGson.init().fromJson(s, BuildingBean.class);
                            buildings.clear();
                            for (int j = 0; j < buildingsBean.getO().size(); j++) {
                                PopoItemBean bean = new PopoItemBean();
                                bean.setText(buildingsBean.getO().get(j).getName());
                                buildings.add(bean);
                                buildingmap.put(buildingsBean.getO().get(j).getName(), buildingsBean.getO().get(j).getId());
                            }
                            pop = new MyPopForSelect(buildings, R.layout.layout_popupwindow_selected, AuthorizationActivity.this, mEt_place_building, new MyPopForSelect.Pop_itemSelectListener() {
                                @Override
                                public void getSelectedString(PopoItemBean data) {
                                    getSelected(data.getText(), mEt_place_building);

                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
    }

    public void getUnitData() {
        OkHttpUtils.post().url(ConstantUrl.unitUrl)
                .addParams("building_id", building_id + "").build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int i) {

                    }

                    @Override
                    public void onResponse(String s, int i) {
                        try {
                            UnitBean unitBean = GetGson.init().fromJson(s, UnitBean.class);
                            units.clear();
                            for (int j = 0; j < unitBean.getO().size(); j++) {
                                PopoItemBean bean = new PopoItemBean();
                                bean.setText(unitBean.getO().get(j).getName());
                                units.add(bean);
                                unitsmap.put(unitBean.getO().get(j).getName(), unitBean.getO().get(j).getId());
                            }
                            pop = new MyPopForSelect(units, R.layout.layout_popupwindow_selected, AuthorizationActivity.this, mEt_element_number, new MyPopForSelect.Pop_itemSelectListener() {
                                @Override
                                public void getSelectedString(PopoItemBean data) {
                                    getSelected(data.getText(), mEt_element_number);

                                }
                            });
                        } catch (Exception e) {
                        }
                    }
                });
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        zProgressHUD.dismiss();
        if (data.getUrl().equals(ConstantUrl.touristUrl)) {
            //下一步
            Preference.put(ConstantString.USERID, data.getO().toString());
            Preference.put(ConstantString.UTYPE, 4 + "");
            CommonUtils.startIntent(this, MainActivity.class);
            finish();
        } else {
            //提交认证资料成功
            ToastUtil.showToast(this, data.getM());
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.putExtra("identify", "ok");
            ToastUtil.showToast(this, data.getM());
            setResult(100, intent);
            if (mEt_user_identity.getText().toString().equals("业主")) {
            } else {
                Preference.put(ConstantString.AUTH, 2 + "");
            }
            Intent intent1 = new Intent();
            intent1.setAction(ConstantString.IDENTIFY_OK);
            intent1.putExtra(ConstantString.PHONE, phone);
            intent1.putExtra(ConstantString.PASSWORD, oldPassword);
            sendBroadcast(intent1);
            finish();
        }
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        if (zProgressHUD != null) {
            zProgressHUD.dismiss();
        }
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        ToastUtil.showToast(this, c_codeBean.getM());
        if (zProgressHUD != null) {
            zProgressHUD.dismiss();
        }
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


}
