package com.eshare_android_preview.controller.task;

import android.os.AsyncTask;
import android.util.Log;

import com.eshare_android_preview.R;
import com.eshare_android_preview.controller.activity.base.EshareBaseActivity;
import com.eshare_android_preview.http.base.EshareHttpRequest;
import com.eshare_android_preview.utils.BaseUtils;
import com.eshare_android_preview.view.dialog.EshareProgressDialog;

import org.apache.http.conn.HttpHostConnectException;

public abstract class BaseAsyncTask<TParams, TProgress, TResult>  {
	public static final int SUCCESS = 200;
    public static final int HTTP_HOST_CONNECT_EXCEPTION = 9002;
    public static final int AUTHENTICATE_EXCEPTION = 9003;
    public static final int RESPONSE_NOT_200_EXCEPTION = 9004;
    public static final int UNKNOWN_EXCEPTION = 9099;
    
//    三种泛型类型分别代表“启动任务执行的输入参数”、“后台任务执行的进度”、“后台计算结果的类型”。在特定场合下，并不是所有类型都被使用，如果没有被使用，可以用java.lang.Void类型代替。
//    一个异步任务的执行一般包括以下几个步骤：
//    1.execute(Params... params)，执行一个异步任务，需要我们在代码中调用此方法，触发异步任务的执行。
//    2.onPreExecute()，在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。
//    3.doInBackground(Params... params)，在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。
//    4.onProgressUpdate(Progress... values)，在调用publishProgress(Progress... values)时，此方法被执行，直接将进度信息更新到UI组件上。
//    5.onPostExecute(Result result)，当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
    private class InnerTask extends AsyncTask<TParams, TProgress, Integer>{
    	@Override
    	protected void onPreExecute() {
    		// 如果构造器传入了 progress_dialog_message 则显示一个提示框
            if (null != progress_dialog_message && null != progress_dialog_activity) {
                progress_dialog = EshareProgressDialog.show(progress_dialog_activity, progress_dialog_message);
            }
            on_start();
    	}
		@Override
		protected Integer doInBackground(TParams... params) {
			try {
				Log.d("TeamknAsyncTask", "开始执行");
				inner_task_result = do_in_background(params);
				return SUCCESS;
            } catch (HttpHostConnectException e) {
                Log.e("TeamknAsyncTask", "网络连接不上");
                e.printStackTrace();
                return HTTP_HOST_CONNECT_EXCEPTION;
			} catch (EshareHttpRequest.AuthenticateException e) {
                // 用户身份验证错误
                Log.e("TeamknAsyncTask", "用户身份验证错误");
                e.printStackTrace();
                return AUTHENTICATE_EXCEPTION;
            } catch (EshareHttpRequest.ResponseNot200Exception e){
                Log.e("TeamknAsyncTask", "http 请求返回非200 code");
                e.printStackTrace();
                return RESPONSE_NOT_200_EXCEPTION;
            }  catch (Exception e) {
            	// 程序执行错误
                Log.e("TeamknAsyncTask", "程序执行错误", e);
                return UNKNOWN_EXCEPTION;
			}
			
		}
		@Override
		protected void onPostExecute(Integer result) {
			try {
				switch (result) {
				case SUCCESS:
					//正确执行
				    on_success(inner_task_result);
					break;
                case HTTP_HOST_CONNECT_EXCEPTION:
                    ___http_host_connect_exception();
				case AUTHENTICATE_EXCEPTION:
					// 用户身份验证错误
				    ___authenticate_exception();
					break;
                case RESPONSE_NOT_200_EXCEPTION:
                    ___response_not_200_exception();
                    break;
				case UNKNOWN_EXCEPTION:
					// 程序执行错误
				    ___unknown_exception();
					break;
				default:
					// result 传入了无法被处理的值，也算程序执行错误
				    ___unknown_exception();
					break;
				}
			} catch (Exception e) {
				 e.printStackTrace();
                // 如果最终处理过程中出现任何异常，也捕获之
                ___unknown_exception();
			}finally{
				___final();
			}
		}
		protected  void onProgressUpdate(TProgress... values) {
			on_progress_update(values);
		}
		protected void publish_progress(TProgress... values) {
            publishProgress(values);
        }
		private void ___authenticate_exception() {
	        on_authenticate_exception();
	        // 2011.10.27 不再对用户身份验证错误的情况进行自动处理
	    }
        private void ___response_not_200_exception() {
            on_response_not_200_exception();
        }
        private void ___http_host_connect_exception() {
            on_http_host_connect_exception();
        }
		private void ___unknown_exception() {
            if (on_unknown_exception()) {
                BaseUtils.toast(R.string.app_unknown_exception);
            }
        }
		private void ___final() {
            on_final();
            try {
				if (null != progress_dialog) {
				    progress_dialog.dismiss();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
    }
    
	private EshareBaseActivity progress_dialog_activity =  null;
	private String progress_dialog_message =  null;
	private EshareProgressDialog progress_dialog =  null;
	
	private InnerTask inner_task = null;
	private TResult inner_task_result =  null;
	
	public BaseAsyncTask(){
		super();
	}
	public BaseAsyncTask(EshareBaseActivity progress_dialog_activity,String progress_dialog_message){
		super();
		this.progress_dialog_activity = progress_dialog_activity;
		this.progress_dialog_message = progress_dialog_message;
	}
	public BaseAsyncTask(EshareBaseActivity progress_dialog_activity,int progress_dialog_message_resource_id){
		super();
		this.progress_dialog_activity = progress_dialog_activity;
		this.progress_dialog_message = progress_dialog_activity.getResources().getString(progress_dialog_message_resource_id);
	}
	// 调用该方法以执行异步请求
    public final void execute(TParams... params) {
        this.inner_task = new InnerTask();
        this.inner_task.execute(params);
    }
    // 在do_in_background中调用该方法以调用 on_progress 方法
    public final void publish_progress(TProgress... values) {
        this.inner_task.publish_progress(values);
    }
    // 选择实现此方法，声明请求过程中随着进度变化的处理逻辑，包括界面的变化等
    // 例如更改进度条的进度
    public void on_progress_update(TProgress... values) {
    }
    // 选择实现此方法，声明请求开始时处理逻辑，包括界面的变化等
    // 例如显示进度条等
    public void on_start() {
    }
    // 必须实现此方法，声明异步请求中的方法逻辑
    public abstract TResult do_in_background(TParams... params) throws Exception;
    
    // 必须实现此方法，声明请求成功时的后续处理逻辑，包括界面的变化等
    public abstract void on_success(TResult result);
    
    // 选择实现此方法，声明请求完结时（不管正确还是出错时）的后续处理逻辑，包括界面的变化等
    // 例如关闭“正在登录…”对话框
    public void on_final() {
    }
    
    // 钩子方法，声明在登录认证错误时的一些特定处理逻辑
    public void on_authenticate_exception() {
    }
    // 钩子方法，声明在出现登录认证错误以外的非200请求时的一些特定处理逻辑
    public void on_response_not_200_exception() {

    }
    // 钩子方法，声明在出现网络连接不上时的一些特定处理逻辑
    public void on_http_host_connect_exception() {

    }
    // 钩子方法，声明在出现其他任何异常时的一些特定处理逻辑
    public boolean on_unknown_exception() {
        return true;
    }
    
}

