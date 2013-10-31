package com.eshare_android_preview.activity.base.notes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.eshare_android_preview.R;
import com.eshare_android_preview.base.activity.EshareBaseActivity;
import com.eshare_android_preview.base.task.BaseAsyncTask;
import com.eshare_android_preview.base.utils.BaseUtils;
import com.eshare_android_preview.base.utils.CameraLogic;
import com.eshare_android_preview.base.utils.ImageTools;
import com.eshare_android_preview.base.utils.ValidateUtil;
import com.eshare_android_preview.logic.HttpApi;
import com.eshare_android_preview.model.Note;

public class AddNoteActivity extends EshareBaseActivity{
	public class RequestCode{
		public static final int FROM_ALBUM  = 0;
		public static final int FROM_CAMERA = 1;
	}
	EditText add_note_content;
	ImageView add_note_img;
	
	Object item;
	
	Uri uri; //头像
    File image_file;
    byte[] bytes = null;
	private void get_img(){
		new AlertDialog.Builder(this)
    	.setTitle("插入图片")
    	.setItems(new String[] { "选择本地图片", "拍照" }, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent;
				switch (which) {
				case 0:
					intent = new Intent(Intent.ACTION_PICK, null);  
					intent.setDataAndType(  
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  
                            "image/*");  
				    startActivityForResult(intent,AddNoteActivity.RequestCode.FROM_ALBUM);
					break;
				case 1:
                    CameraLogic.call_system_camera(AddNoteActivity.this,AddNoteActivity.RequestCode.FROM_CAMERA);        
                    break;
				default:
					break;
				}
			}
		})
    	.setNegativeButton("取消", null)
    	.show();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.n_add_note);
        hide_head_setting_button();
        set_head_text(R.string.add_note_title);
		
		Intent intent = getIntent();
		item = intent.getExtras().getSerializable("item");
		
		load_ui();

        super.onCreate(savedInstanceState);
	}
	private void load_ui() {
		add_note_content = (EditText)findViewById(R.id.add_note_content);
		add_note_img = (ImageView)findViewById(R.id.add_note_img);
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode != Activity.RESULT_OK){
			return;
		}
		switch(requestCode){
		  case RequestCode.FROM_ALBUM:
			    System.out.println(" userManagerActivity "+ data.getData().getPath());
			    startPhotoZoom(data.getData());  
			    break;
		  case RequestCode.FROM_CAMERA:
			  new BaseAsyncTask<Void, Void, Void>(AddNoteActivity.this,"请稍等"){
				@Override
				public Void do_in_background(Void... params) throws Exception {
					String file_path = CameraLogic.IMAGE_CAPTURE_TEMP_FILE.getAbsolutePath();
					try {
						uri= Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), file_path, null, null));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					return null;
				}
				@Override
				public void on_success(Void result) {
					startPhotoZoom(uri); 
				}
			  }.execute();     
            break;

		  case 9:  
              /**  
               * 非空判断大家一定要验证，如果不验证的话，  
               * 在剪裁之后如果发现不满意，要重新裁剪，丢弃  
               * 当前功能时，会报NullException，小马只  
               * 在这个地方加下，大家可以根据不同情况在合适的  
               * 地方做判断处理类似情况  
               *   
               */ 
              if(data != null){  
                  setPicToView(data);  
              }  
              break; 
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**  
     * 裁剪图片方法实现  
     * @param uri  
     */ 
    public void startPhotoZoom( Uri uri) {  
	    System.out.println(uri.getPath());
		Intent intent = new Intent("com.android.camera.action.CROP");  
        intent.setDataAndType(uri, "image/*");  
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪  
        intent.putExtra("crop", "true");  
        // aspectX aspectY 是宽高的比例  
        intent.putExtra("aspectX", 1);  
        intent.putExtra("aspectY", 1);  
        // outputX outputY 是裁剪图片宽高  
        intent.putExtra("outputX", 150);  
        intent.putExtra("outputY", 150);  
        intent.putExtra("return-data", true);  
        startActivityForResult(intent, 9);  
    }
    /**  
     * 保存裁剪之后的图片数据  
     * @param picdata  
     */  
    private void setPicToView(Intent picdata) {  
        Bundle extras = picdata.getExtras();  
        if (extras != null) {  
            Bitmap photo = extras.getParcelable("data");  
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            photo.compress(CompressFormat.PNG, 100,os);
            byte[] bytes = os.toByteArray();
            set_img(bytes);
        }
    }
    private void set_img(byte[] bytes){
    	if (bytes!= null) {
    		this.bytes = bytes;
    		Bitmap bitmap = ImageTools.bytesToBimap(bytes);
        	add_note_img.setBackgroundDrawable(ImageTools.bitmapToDrawableByBD(bitmap));
        	add_note_img.setVisibility(View.VISIBLE);
		}
    }
    
	public void click_add_img(View view){
		get_img();
	}
	
	public void click_on_submit_but(View view){
		String content_str = add_note_content.getText().toString();
		if (BaseUtils.is_str_blank(content_str) && bytes == null) {
//			BaseUtils.toast("笔记内容不可以为空");
			ValidateUtil.isEmpty(add_note_content, "内容");
			return;
		}
		String content = BaseUtils.is_str_blank(content_str)? "":content_str;
//		Note note = new Note(question.id,content,bytes);
		System.out.println(item.getClass().getName());
		Note note = new Note(item.getClass().getName(), content, bytes,item);
		if (HttpApi.HANote.create(note)) {
			this.finish();
		}
	}
}
