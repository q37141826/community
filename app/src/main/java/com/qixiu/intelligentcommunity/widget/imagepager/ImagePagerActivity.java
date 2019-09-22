package com.qixiu.intelligentcommunity.widget.imagepager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qixiu.intelligentcommunity.R;
import com.qixiu.intelligentcommunity.config.ToolBarOptions;
import com.qixiu.intelligentcommunity.mvp.view.activity.base.BaseActivity;
import com.qixiu.intelligentcommunity.utlis.AndroidUtils;
import com.qixiu.intelligentcommunity.utlis.FileUtils;
import com.qixiu.intelligentcommunity.utlis.ImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Description：
 * Author：XieXianyong
 * Date： 2017/6/20 20:18
 */
public class ImagePagerActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "ImagePagerActivity";

    public static final int ONLY_SHOW_IMAGE = 1;
    public static final int ALL_SHOW_IMAGE = 2; //旋转
    public static final int DYNAMIC_UPLOAD_SHOW = 3; //删除
    public static final int CHANGE_UPLOAD_SHOW = 4; //更换

    @BindView(R.id.image_view_page)
    ViewPager mViewPager;
    @BindView(R.id.tv_current_total)
    TextView mTvCurrentTotal;

    private ViewPagerAdapter mAdapter;

    private List<String> mUrls;
    private int mCurrentPos;
    private int mCurrentPage;

    private ImageUtils mImageUtil;
    int currntposition;

    private Dialog mDialog;
    private Matrix mMatrix;
    private Bitmap bitmap;
    private Bitmap rotatedBitmap;
    private ArrayList<Integer> mDeletePos;

    public static void start(Context context, ArrayList<String> urls, int position, int showType) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra("picList", urls);
        intent.putExtra("position", position);
        intent.putExtra("currentPage", showType);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    protected void onInitData() {
        setImageIndex(mCurrentPos + 1);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mCurrentPos);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentPos = position;
                setImageIndex(position + 1);
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    protected void onInitView() {
        ToolBarOptions tbOptions = new ToolBarOptions();
        setToolBar(tbOptions);
        if (getIntent().getExtras() != null) {
            String pics = (String) getIntent().getExtras().get("pics");
            mCurrentPos = (Integer) getIntent().getExtras().get("position");
            currntposition = mCurrentPos;
            mCurrentPage = getIntent().getExtras().getInt("currentPage");
            if (TextUtils.isEmpty(pics)) {
                mUrls = getIntent().getStringArrayListExtra("picList");
            } else {
                mUrls = new ArrayList<String>(Arrays.asList(pics.split(",")));
            }
        }

        mAdapter = new ViewPagerAdapter(mUrls, this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                mUrls.remove(mCurrentPos);
                if (mDeletePos == null) {
                    mDeletePos = new ArrayList<>();
                }
                mDeletePos.add(mCurrentPos);
                EventBus.getDefault().post(new PhotoDelete(currntposition));
                if (mUrls.size() == 0) {
                    finish();
                }
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 设置浏览下标
     *
     * @param position
     */
    private void setImageIndex(int position) {
        mTvCurrentTotal.setText(position + " / " + mUrls.size());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_image_pager;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Glide.get(ImagePagerActivity.this).clearMemory();
    }

    public void showRotatePop() {
        mDialog = new Dialog(this, R.style.upomp_bypay_MyDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_photo_rotate, null);
        mDialog.setContentView(view);

        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_anti_rotate = (Button) view.findViewById(R.id.btn_anti_rotate);
        Button btn_rotate = (Button) view.findViewById(R.id.btn_rotate);
        Button btn_save = (Button) view.findViewById(R.id.btn_save);

        btn_cancel.setOnClickListener(this);
        btn_rotate.setOnClickListener(this);
        btn_anti_rotate.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        android.view.WindowManager.LayoutParams layoutParams = mDialog.getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = (int) AndroidUtils.getDeviceWidth(this);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        mMatrix = new Matrix();
        new Thread() {
            @Override
            public void run() {
                super.run();
                bitmap = getNetBitmap(mUrls.get(mCurrentPos));
            }

        }.start();
        mDialog.show();

    }

    private Bitmap rotaingImageView(int angle2) {
        //旋转图片 动作
        mMatrix.postRotate(angle2);
        System.out.println("angle2=" + angle2);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), mMatrix, true);
        //Drawable drawable = ImageDispose.bitmapToDrawable(resizedBitmap);
        ((PhotoView) mAdapter.getPrimaryItem()).setImageBitmap(resizedBitmap);
        //((PhotoView)imageviewpage.findViewById(currentPosition)).setImageBitmap(resizedBitmap);
        //imageView.setBackgroundDrawable(drawable);
        return resizedBitmap;
    }

    public static Bitmap getNetBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        BufferedOutputStream out = null;
        int buffersize = 2 * 1024;
        try {
            in = new BufferedInputStream(new URL(url).openStream(), buffersize);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, buffersize);
            byte[] b = new byte[buffersize];
            int read;
            while ((read = in.read(b)) != -1) {
                out.write(b, 0, read);
            }
            out.flush();
            byte[] data = dataStream.toByteArray();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inSampleSize = 2;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            data = null;
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存文件
     */
    public void saveFile(Bitmap bm, String path) {
        try {
            File myCaptureFile = new File(path);
            if (!myCaptureFile.exists()) {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        if (bitmap != null && !bitmap.isRecycled()) {

            bitmap.recycle();

            bitmap = null;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel://取消
                mDialog.dismiss();
                break;
            case R.id.btn_anti_rotate://逆时针旋转
                if (bitmap == null) {
                    return;
                }
                rotatedBitmap = rotaingImageView(-90);
                break;
            case R.id.btn_rotate://顺时针旋转
                if (bitmap == null) {
                    return;
                }
                rotatedBitmap = rotaingImageView(90);
                break;
            case R.id.btn_save://保存
                if (rotatedBitmap != null) {
                    new Thread() {
                        @Override
                        public void run() {
                            super.run();
                            String fileName = System.currentTimeMillis() + ".jpg";
                            String path = FileUtils.getImagePath() + "/" + fileName;
                            saveFile(rotatedBitmap, path);
                            EventBus.getDefault().post(new PhotoSaveBean(mCurrentPos, path));
                            mDialog.dismiss();
                            finish();
                        }

                    }.start();
                }
                break;
        }
    }
}
