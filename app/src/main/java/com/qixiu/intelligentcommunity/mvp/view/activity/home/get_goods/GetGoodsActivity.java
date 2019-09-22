package com.qixiu.intelligentcommunity.mvp.view.activity.home.get_goods;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.constants.ConstantString;
import com.qixiu.intelligentcommunity.constants.ConstantUrl;
import com.qixiu.intelligentcommunity.constants.StringConstants;
import com.qixiu.intelligentcommunity.listener.rv_adapter.OnRecyclerItemListener;
import com.qixiu.intelligentcommunity.mvp.beans.BaseBean;
import com.qixiu.intelligentcommunity.mvp.beans.C_CodeBean;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpRequestModel;
import com.qixiu.intelligentcommunity.mvp.model.request.OKHttpUIUpdataListener;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.NewTitleActivity;
import com.qixiu.intelligentcommunity.mvp.view.activity.home.web.GoToActivity;
import com.qixiu.intelligentcommunity.mvp.view.adapter.upload.UpLoadPictureAdapter;
import com.qixiu.intelligentcommunity.mvp.view.widget.loading.ZProgressHUD;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopForSelect;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.MyPopTimePicker;
import com.qixiu.intelligentcommunity.mvp.view.widget.mypopselect.PopoItemBean;
import com.qixiu.intelligentcommunity.utlis.CommonUtils;
import com.qixiu.intelligentcommunity.utlis.PictureCut;
import com.qixiu.intelligentcommunity.utlis.Preference;
import com.qixiu.intelligentcommunity.utlis.TimeDataUtil;
import com.qixiu.intelligentcommunity.utlis.ToastUtil;
import com.qixiu.intelligentcommunity.utlis.picker.MyDataPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import me.iwf.photopicker.utils.ImageCaptureManager;
import okhttp3.Call;

public class GetGoodsActivity extends NewTitleActivity implements OKHttpUIUpdataListener<BaseBean> {
    private EditText edittext_elevator_useful, edittext_floor_select, edittext_time_select, edittext_goodsName, edittext_phoneGetGoods, edittext_goodsDescribe;
    private TextView textView_getGoods_rules, textView_givePayMoney;
    private MyPopForSelect popElevator, popFloor;
    private List<PopoItemBean> listEleVator, listFloor;
    private RecyclerView recyclerview_imageselected_publish;
    private UpLoadPictureAdapter adapter;
    private ArrayList<String> selectPhotos = new ArrayList<>();
    //选择图片相关
    private int maxPictureCount = 3;
    private ImageCaptureManager captureManager;
    //没有选择之前默认展示数据
    private List<String> images = new ArrayList<>();
    //时间选择弹窗
    private MyPopTimePicker timePicker;

    private OKHttpRequestModel okHttpRequestModel;
    ZProgressHUD zProgressHUD;
    //网络请求的数据
    private String name, phone, type = "1", floor, gtime, describe, imgs, payid = "";
    private Map<String, String> map;

    //接受打赏成功的广播
    private BroadcastReceiver receiver;
//是否勾选了免责条款
    private  boolean is_selected=true;
    private ImageView imageView_isSelected;
    @Override
    protected void onInitData() {
        CommonUtils.closeInputSoft(this,edittext_goodsName);
        mTitleView.setTitle("快递代收送上门");
        mTitleView.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitleView.setRightText("记录");
        mTitleView.setRightTextVisible(View.VISIBLE);
        mTitleView.setRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.startIntent(GetGoodsActivity.this, GetGoodsRecordActivity.class);
            }
        });
        //装填选择数据
        initListData();
        adapter = new UpLoadPictureAdapter();
        recyclerview_imageselected_publish.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerview_imageselected_publish.setAdapter(adapter);
        //在没有选择图片之前先放一个默认图标
        adapter.setOnItemClickListener(new OnRecyclerItemListener<String>() {
            @Override
            public void onItemClick(View v, String data) {
                int position = recyclerview_imageselected_publish.getChildLayoutPosition(v);
                if (position == adapter.getDatas().size()) {
                    if (selectPhotos.size() < maxPictureCount) {
                        PhotoPicker.builder().setPhotoCount(maxPictureCount).setShowCamera(true).setSelected(
                                selectPhotos).start(GetGoodsActivity.this, PhotoPicker.REQUEST_CODE);
                    }
                } else {
                    PhotoPreview.builder().setPhotos(selectPhotos).setCurrentItem(position).start(GetGoodsActivity.this, PhotoPreview.REQUEST_CODE);
                }
            }
        });
        if (TimeDataUtil.getTimeSection(9, 20) == TimeDataUtil.BEFORE_SECTION) {
            edittext_time_select.setText(TimeDataUtil.getDate(0) + "    " + "9:00-10:00");
        } else if (TimeDataUtil.getTimeSection(9, 20) == TimeDataUtil.AFTER_SECTION) {
            edittext_time_select.setText(TimeDataUtil.getDate(1 * 3600 * 24 * 1000) + "    " + "9:00-10:00");
        } else {
            edittext_time_select.setText(TimeDataUtil.getDate(0) + "    " + TimeDataUtil.getTime(0) + "-" + TimeDataUtil.getTime(1 * 3600 * 1000));
        }

        IntentFilter filter = new IntentFilter(ConstantString.broadCastFinishPay);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                adapter.refreshData(null);
                edittext_goodsName.requestFocus();
                edittext_elevator_useful.setText("能使用电梯");
                edittext_floor_select.setText("1楼");
                edittext_phoneGetGoods.setText("");
                edittext_goodsDescribe.setText("");
                edittext_goodsName.setText("");
                edittext_time_select.setText("");
                if (TimeDataUtil.getTimeSection(9, 20) == TimeDataUtil.BEFORE_SECTION) {
                    edittext_time_select.setText(TimeDataUtil.getDate(0) + "    " + "9:00-10:00");
                } else if (TimeDataUtil.getTimeSection(9, 20) == TimeDataUtil.AFTER_SECTION) {
                    edittext_time_select.setText(TimeDataUtil.getDate(1 * 3600 * 24 * 1000) + "    " + "9:00-10:00");
                } else {
                    edittext_time_select.setText(TimeDataUtil.getDate(0) + "    " + TimeDataUtil.getTime(0) + "-" + TimeDataUtil.getTime(1 * 3600 * 1000));
                }
            }
        };
        registerReceiver(receiver, filter);

    }

    private void initListData() {
        listEleVator = new ArrayList<>();
        listFloor = new ArrayList<>();
        PopoItemBean bean;
        bean = new PopoItemBean();
        bean.setText("能使用电梯");
        listEleVator.add(bean);
        bean = new PopoItemBean();
        bean.setText("不能使用电梯");
        listEleVator.add(bean);
        for (int i = 1; i <= 35; i++) {
            bean = new PopoItemBean();
            bean.setText(i + "楼");
            listFloor.add(bean);
        }
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_get_goods;
    }

    @Override
    protected void onInitViewNew() {
        zProgressHUD = new ZProgressHUD(this);
        edittext_goodsName = (EditText) findViewById(R.id.edittext_goodsName);
        edittext_phoneGetGoods = (EditText) findViewById(R.id.edittext_phoneGetGoods);
        okHttpRequestModel = new OKHttpRequestModel(this);
        edittext_elevator_useful = (EditText) findViewById(R.id.edittext_elevator_useful);
        edittext_floor_select = (EditText) findViewById(R.id.edittext_floor_select);
        edittext_time_select = (EditText) findViewById(R.id.edittext_time_select);
        recyclerview_imageselected_publish = (RecyclerView) findViewById(R.id.recyclerview_imageselected_publish);
        textView_getGoods_rules = (TextView) findViewById(R.id.textView_getGoods_rules);
        textView_givePayMoney = (TextView) findViewById(R.id.textView_givePayMoney);
        edittext_goodsDescribe = (EditText) findViewById(R.id.edittext_goodsDescribe);
        imageView_isSelected= (ImageView) findViewById(R.id.imageView_isSelected);
        setClick();

    }

    private void setClick() {
        edittext_elevator_useful.setOnClickListener(this);
        edittext_floor_select.setOnClickListener(this);
        edittext_time_select.setOnClickListener(this);
        textView_getGoods_rules.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        textView_getGoods_rules.setOnClickListener(this);
        textView_givePayMoney.setOnClickListener(this);
        imageView_isSelected.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edittext_elevator_useful:
                if (popElevator != null) {
                    popElevator.show();
                } else {
                    popElevator = new MyPopForSelect(listEleVator, R.layout.layout_popupwindow_selected, this, edittext_elevator_useful, new MyPopForSelect.Pop_itemSelectListener() {
                        @Override
                        public void getSelectedString(PopoItemBean data) {
                            edittext_elevator_useful.setText(data.getText());
                        }
                    });
                }
                break;
            case R.id.edittext_floor_select:
                if (popFloor != null) {
                    popFloor.show();
                } else {
                    popFloor = new MyPopForSelect(listFloor, R.layout.layout_popupwindow_selected, this, edittext_floor_select, new MyPopForSelect.Pop_itemSelectListener() {
                        @Override
                        public void getSelectedString(PopoItemBean data) {
                            edittext_floor_select.setText(data.getText());
                        }
                    });
                }
                break;
            case R.id.edittext_time_select:
//                if (timePicker != null) {
//                    timePicker.show();
//                } else {
                //为了满足产品的变态需求，本来的循环选择，将开始的日期时间打为空白，默认选择第一天，就要求按照后面的数据集合定制前面的空白数据集合长度
//                    List<String>  finalDate=new ArrayList<>();
//                    List<String> finalTime=new ArrayList<>();
//                    List<String> date = TimeDataUtil.getDataList(30);
//                    //注意初始化的时候是当天，要看看当前几点了,如果过了
//                    List<String> time = TimeDataUtil.getTimtArea(TimeDataUtil.getTime());
//                    if (TimeDataUtil.getTimeSection(9,20)==TimeDataUtil.AFTER_SECTION) {
//                        time = TimeDataUtil.getTimtArea(0);
//                        date.remove(0);
//                    }
//                    List<String>  listNothingTime=new ArrayList<>();
//                    for (int i = 0; i < time.size(); i++) {
//                        listNothingTime.add("");
//                    }
//                    List<String>  listNothingdate=new ArrayList<>();
//                    for (int i = 0; i < date.size(); i++) {
//                        listNothingdate.add("");
//                    }
//                    finalDate.addAll(listNothingdate);
//                    finalDate.addAll(date);
//                    finalTime.addAll(listNothingTime);
//                    finalTime.addAll(time);
//                    timePicker = new MyPopTimePicker(this, finalDate, finalTime, new MyPopTimePicker.Pop_itemSelectListener() {
//                        @Override
//                        public void getRightSelectData(String str) {
//                            if(TextUtils.isEmpty(str)){
//                                List<String>  list=new ArrayList<>();
//                                list.add("");
//                                timePicker.setRightData(list);
//                                return;
//                            }
//                            //如果是今天，判断现在几点了，如果是将来的某一天就不用判断了
//                            Log.e("is_todey", TimeDataUtil.isToday(str) + "");
//                            if (TimeDataUtil.isToday(str)) {
//                                List<String> timtArea = TimeDataUtil.getTimtArea(TimeDataUtil.getTime());
//                                timtArea.addAll(timtArea);
//                                timePicker.setRightData(timtArea);
//                            } else {
//                                List<String> timtArea = TimeDataUtil.getTimtArea(0);
//                                timtArea.addAll(timtArea);
//                                timePicker.setRightData(timtArea);
//                            }
//                        }
//                    }, new MyPopTimePicker.Pop_itemSelectListener() {
//                        @Override
//                        public void getRightSelectData(String str) {
//                            if(TextUtils.isEmpty(str)){
//                                return;
//                            }
//                            edittext_time_select.setText(str);
//                        }
//                    });
                MyDataPicker picker = new MyDataPicker(this);
                picker.setTextNormolColor(getResources().getColor(R.color.text_color));
                picker.setTextFocusedColor(getResources().getColor(R.color.font_grey));
                picker.setLineColor(getResources().getColor(R.color.lineColor));
                picker.setOnDatePickListener(new MyDataPicker.OnDateTimePickListener() {
                    @Override
                    public void onDatePicked(String day, String time) {
                        edittext_time_select.setText(day+"    "+time);
                    }
                });
                picker.show();
//                }
                break;
            case R.id.textView_getGoods_rules:
                Intent intent = new Intent(this, GoToActivity.class);
                intent.putExtra(ConstantString.URL, ConstantUrl.getGoodsRuleUrl);
                intent.putExtra(ConstantString.TITLE_NAME, "免责条款");
                startActivity(intent);
                break;
            case R.id.textView_givePayMoney:
                if(!is_selected){
                    ToastUtil.toast("请勾选免责条款");
                }
                zProgressHUD.show();
                if (!getData()) {
                    zProgressHUD.dismiss();
                }
                break;
            case R.id.imageView_isSelected:
                is_selected=!is_selected;
                setImageSelectedState(is_selected);
                break;
        }
    }

    private void setImageSelectedState(boolean is_selected) {
            imageView_isSelected.setImageResource(is_selected?R.mipmap.is_selected_grey2x:R.mipmap.not_selected_grey2x);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            adapter.refreshData(selectPhotos);
        }
    }

    @Override
    public void onSuccess(BaseBean data, int i) {
        zProgressHUD.dismiss();
        if (data.getUrl().equals(ConstantUrl.getGoodsApply)) {
            Intent intent = new Intent(this, GiveTipActvity.class);
            payid = data.getO().toString();
            intent.putExtra("payid", payid);
            startActivity(intent);
        }
//        ToastUtil.toast(data.getM());
    }

    @Override
    public void onError(Call call, Exception e, int i) {
        ToastUtil.toast(R.string.link_error);
        zProgressHUD.dismiss();
    }

    @Override
    public void onFailure(C_CodeBean c_codeBean) {
        zProgressHUD.dismiss();
        ToastUtil.toast(c_codeBean.getM());
    }

    public boolean getData() {
        map = new HashMap<>();
        name = edittext_goodsName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast("请输入物品名称");
            return false;
        }
        phone = edittext_phoneGetGoods.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.toast("请输入联系电话");
            return false;
        }
        if (!CommonUtils.isMobileNO(phone)) {
            ToastUtil.toast("请输入正确的联系电话");
            return false;
        }
        if (edittext_elevator_useful.getText().toString().contains("不能")) {
            type = 2 + "";
        } else {
            type = 1 + "";
        }
        floor = edittext_floor_select.getText().toString().charAt(0) + "";
        gtime = edittext_time_select.getText().toString();
        if (TextUtils.isEmpty(gtime)) {
            ToastUtil.toast("请选择上门时间");
            return false;
        }
        describe = edittext_goodsDescribe.getText().toString();
        if (selectPhotos.size() == 0) {
            ToastUtil.toast("请上传说明图片");
            return false;
        }
        map.put("uid", Preference.get(ConstantString.USERID, ""));
        map.put("name", name);
        map.put("phone", phone);
        map.put("type", type);
        map.put("floor", floor);
        map.put("gtime", gtime);
        if (!TextUtils.isEmpty(describe)) {
            map.put("describe", describe);
        }
        PictureCut.CompressImageWithThumb.callBase64s(selectPhotos, new PictureCut.CompressImageWithThumb.CallBackBase64s() {
            @Override
            public void callBack(List<String> base64s) {
                StringBuffer buffer = new StringBuffer("");
                for (int i = 0; i < base64s.size(); i++) {
                    buffer.append(base64s.get(i));
                    if (i < selectPhotos.size() - 1) {
                        buffer.append("|");
                    }
                }
                imgs = buffer.toString();
                if (!TextUtils.isEmpty(imgs)) {
                    map.put(StringConstants.IMGS_STRING, imgs);
                }
                okHttpRequestModel.okhHttpPost(ConstantUrl.getGoodsApply, map, new BaseBean());
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

}
