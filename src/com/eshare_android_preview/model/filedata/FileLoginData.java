package com.eshare_android_preview.model.filedata;

import com.eshare_android_preview.EshareApplication;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

public class FileLoginData {
	final static String FILE_NAME =   "account.obj";
	
	public ArrayList<String> login_data;

	@SuppressWarnings("unchecked")
	public ArrayList<String> getLogin_data() {
		this.login_data = new ArrayList<String>();
		ObjectInputStream in = null;
		try {
			InputStream is = EshareApplication.context.openFileInput(FILE_NAME);
			in = new ObjectInputStream(is);
			this.login_data = (ArrayList<String>) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return login_data;
	}

	public void setLogin_data(ArrayList<String> login_data) {
		this.login_data = login_data;
	}
	
	public void setLogin_data(String login_input) {
		this.login_data.remove(login_input);
		this.login_data.add(login_input);
		if (this.login_data.size() > 5 ){
			this.login_data.remove(0);
		}
		ObjectOutputStream out = null;
		try {
			@SuppressWarnings("static-access")
			FileOutputStream os = EshareApplication.context.openFileOutput(FILE_NAME, EshareApplication.context.MODE_PRIVATE);
			out = new ObjectOutputStream(os);
			out.writeObject(this.login_data);
		} catch (Exception e) {

		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public String get_last_data(){
		if (login_data == null) {
			this.login_data = getLogin_data();
		}
		return login_data.size() > 0 ? login_data.get(login_data.size() - 1)  : "";
	}
}
