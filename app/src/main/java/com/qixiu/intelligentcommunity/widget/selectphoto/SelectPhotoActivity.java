package com.qixiu.intelligentcommunity.widget.selectphoto;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.widget.PauseOnScrollListener;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * 相册图片选择
 *
 * @author xxy
 */
public class SelectPhotoActivity extends AppCompatActivity {
    private static final String TAG = "SelectPhotoActivity";

    public static final int REQUEST_CODE = 0x0352;
    public static final String SELECTED_PHOTOS = "Bigimage";
    private Button mBtnConfirm;//确定

    private GridView photoGrid;// 图片列表

    private TextView titleName;// 头部的标题

    private ImageView titleIcon;// 头部的三角图标

    private TextView mTvSelectNum;//底部选中数量

//    private Toolbar toolbar;

    private ProgressDialog mProgressDialog;

    private HashSet<String> mDirPaths = new HashSet<String>();// 临时的辅助类，用于防止同一个文件夹的多次扫描

    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();// 扫描拿到所有的图片文件夹

    int totalCount = 0;

    private File mImgDir = new File("");// 图片数量最多的文件夹

    private int mPicsSize;// 存储文件夹中的图片数量

    private List<String> mImgs = new ArrayList<String>();// 所有的图片

    private int mScreenHeight;

    private LinearLayout dirLayout;


    private ListView dirListView;


    private int mScreenWidth;

    private ImageFloderAdapter floderAdapter;

    private GirdItemAdapter girdItemAdapter;

    private PopupWindow popupWindow;

    private View dirview;

    private static final int TAKE_CAMERA_PICTURE = 1000;// 拍照

    private String path;

    private File dir;

    private String imagename;

    private FilenameFilter mFilter;

    private static final String MAX_SELECT_PIC_EXTRA = "MAX_SELECT_PIC_EXTRA";
    private static final String MAX_SELECTED_PIC_EXTRA = "MAX_SELECTED_PIC_EXTRA";
    private int mMaxNum;//最大图片数

    public static Intent getIntent(Context context, MaxSelectPhotoType maxType, int selectNum) {
        Intent intent = new Intent(context, SelectPhotoActivity.class);
        intent.putExtra(MAX_SELECT_PIC_EXTRA, maxType);
        intent.putExtra(MAX_SELECTED_PIC_EXTRA, selectNum);
        return intent;
    }

    public static void start(Context context, MaxSelectPhotoType maxType, int selectNum) {
        Intent intent = new Intent(context, SelectPhotoActivity.class);
        intent.putExtra(MAX_SELECT_PIC_EXTRA, maxType);
        intent.putExtra(MAX_SELECTED_PIC_EXTRA, selectNum);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE);
    }

    public static void start(Context context, MaxSelectPhotoType maxType, int selectNum, Fragment fragment) {
        Intent intent = new Intent(context, SelectPhotoActivity.class);
        intent.putExtra(MAX_SELECT_PIC_EXTRA, maxType);
        intent.putExtra(MAX_SELECTED_PIC_EXTRA, selectNum);
        fragment.startActivityForResult(intent, REQUEST_CODE);
    }

    /**
     * @param activity    活动类
     * @param maxType     最大图片选择个数
     * @param selectedNum 已选择的图片个数
     * @param requestCode 请求码
     */
    public static void start(Activity activity, MaxSelectPhotoType maxType, int selectedNum, int requestCode) {
        Intent intent = new Intent(activity, SelectPhotoActivity.class);
        intent.putExtra(MAX_SELECT_PIC_EXTRA, maxType);
        intent.putExtra(MAX_SELECTED_PIC_EXTRA, selectedNum);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.photo_select_view);
        GirdItemAdapter.mSelectedImage.clear();
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        mScreenWidth = outMetrics.widthPixels;

        MaxSelectPhotoType mMaxSelectPhotoType;
        int mSelectedNum;
        //获取最大选择图片数量
        if (getIntent().getExtras() != null) {
            mMaxSelectPhotoType = (MaxSelectPhotoType) getIntent().getExtras().get(MAX_SELECT_PIC_EXTRA);
            mSelectedNum = getIntent().getIntExtra(MAX_SELECTED_PIC_EXTRA, 0);
        } else {
            mMaxSelectPhotoType = MaxSelectPhotoType.BaseNum;
            mSelectedNum = 0;
        }
        switch (mMaxSelectPhotoType) {
            case OneNum:
                mMaxNum = 1 - mSelectedNum;
                break;
            case BaseNum:
                mMaxNum = 9 - mSelectedNum;
                break;
            case DoubleNum:
                mMaxNum = 18 - mSelectedNum;
                break;
            case TripleNum:
                mMaxNum = 27 - mSelectedNum;
                break;
            case FourFoldNum:
                mMaxNum = 36 - mSelectedNum;
                break;
            case TenFold:
                mMaxNum = 99 - mSelectedNum;
                break;
            case FOUR:
                mMaxNum = 4 - mSelectedNum;
                break;
            default:
                mMaxNum = 9;
                break;
        }

        mFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg")
                        || name.endsWith(".JPG") || name.endsWith(".PNG") || name.endsWith(".JPEG"))
                    return true;
                return false;
            }
        };

        setView();
        getImages();
        initEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 获取控件
     */
    private void setView() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        //toolbar.setNavigationIcon(R.mipmap.ic_drawer_home);//设置导航栏图标
//        toolbar.inflateMenu(R.menu.menu_select_photo);//设置右上角的填充菜单
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        mTvSelectNum = (TextView) findViewById(R.id.tv_select_num);
        photoGrid = (GridView) findViewById(R.id.gird_photo_list);
        titleName = (TextView) findViewById(R.id.selected_photo_name_text);
        titleIcon = (ImageView) findViewById(R.id.selected_photo_icon);
//        titleIcon.setBackgroundResource(R.drawable.navigationbar_arrow_down);
        photoGrid.setOnScrollListener(new com.qixiu.intelligentcommunity.widget.PauseOnScrollListener(Glide.with(SelectPhotoActivity.this), false, true));

        setSelectedNum(0);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String firstImage = null;
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = SelectPhotoActivity.this.getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?", new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED);
                //Log.e(TAG, mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    //Log.e(TAG, path);
                    // Log.e("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    if (parentFile.list() == null)
                        continue;
                    int picSize = parentFile.list(mFilter).length;
                    totalCount += picSize;
                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            setDataView();// 为View绑定数据
        }
    };

    /**
     * 为View绑定数据
     */
    public void setDataView() {
        path = Environment.getExternalStorageDirectory() + "/" + "test/photo/";
        dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), "一张图片没扫描到", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mImgDir.exists()) {
            mImgs = Arrays.asList(mImgDir.list(mFilter));
        }
        Collections.reverse(mImgs);
        girdItemAdapter = new GirdItemAdapter(this, mImgs, mImgDir.getAbsolutePath(), mMaxNum);
        photoGrid.setAdapter(girdItemAdapter);
        girdItemAdapter.setOnPhotoSelectedListener(new GirdItemAdapter.OnPhotoSelectedListener() {
            @Override
            public void takePhoto() {
                imagename = getTimeName(System.currentTimeMillis()) + ".jpg";
                String state = Environment.getExternalStorageState();
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(dir, imagename);// localTempImgDir和localTempImageFileName是自己定义的名字
                    Uri u = Uri.fromFile(f);
                    intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, u);
                    startActivityForResult(intent, TAKE_CAMERA_PICTURE);
                } else {
                    Toast.makeText(SelectPhotoActivity.this, "请插入SD卡", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void photoClick(List<String> number) {
                setSelectedNum(number.size());
            }
        });
    }

    private void setSelectedNum(int selectedNum) {
        mTvSelectNum.setText(selectedNum + "/" + mMaxNum);
    }

    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        if (popupWindow == null) {
            dirview = LayoutInflater.from(SelectPhotoActivity.this).inflate(R.layout.image_select_dirlist, null);
            dirListView = (ListView) dirview.findViewById(R.id.id_list_dirs);
            popupWindow = new PopupWindow(dirview, LayoutParams.MATCH_PARENT, mScreenHeight * 3 / 5);
            floderAdapter = new ImageFloderAdapter(SelectPhotoActivity.this, mImageFloders);
            dirListView.setAdapter(floderAdapter);
            dirListView.setOnScrollListener(new PauseOnScrollListener(Glide.with(SelectPhotoActivity.this), false, true));
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        titleIcon.setBackgroundResource(R.mipmap.navigationbar_arrow_up);
//        popupWindow.showAsDropDown(toolbar, 0, 0);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                titleIcon.setBackgroundResource(R.mipmap.navigationbar_arrow_down);
            }
        });
        dirListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                titleName.setText(mImageFloders.get(position).getName());
                mImgDir = new File(mImageFloders.get(position).getDir());
                mImgs = Arrays.asList(mImgDir.list(mFilter));
                for (int i = 0; i < mImageFloders.size(); i++) {
                    mImageFloders.get(i).setSelected(false);
                }
                mImageFloders.get(position).setSelected(true);
                floderAdapter.changeData(mImageFloders);
                girdItemAdapter.changeData(mImgs, mImageFloders.get(position).getDir());
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });

    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /*
     * @see android.app.Activity#onBackPressed()
     */
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
    }

    /**
     * 监听事件
     */
    private void initEvent() {
        /**
         * 为底部的布局设置点击事件，弹出popupWindow
         */
        titleName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 初始化展示文件夹的popupWindw
                initListDirPopupWindw();

            }
        });
        mBtnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String[] array = GirdItemAdapter.mSelectedImage.toArray(new String[GirdItemAdapter.mSelectedImage.size()]);
                intent.putExtra(SELECTED_PHOTOS, array);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if (GirdItemAdapter.mSelectedImage.size() == 0) {
//                    Toast.makeText(SelectPhotoActivity.this, R.string.toast_tips, Toast.LENGTH_LONG).show();
//                    return true;
//                }
//                Intent intent = new Intent();
//                String[] array = GirdItemAdapter.mSelectedImage.toArray(new String[GirdItemAdapter.mSelectedImage.size()]);
//                intent.putExtra(SELECTED_PHOTOS, array);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//                return true;
//            }
//        });

    }

    public static String getTimeName(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(time);
        return formatter.format(date);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA_PICTURE:
                if (Activity.RESULT_OK == resultCode) {
                    Intent intent = new Intent();
                    String[] picpath = {path + imagename};
                    intent.putExtra(SELECTED_PHOTOS, picpath);
                    setResult(RESULT_OK, intent);
                    finish();

                }
                break;
        }
    }

    /*
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Glide.get(SelectPhotoActivity.this).clearMemory();
    }
}
