package com.yiplau.main;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
	
	private final static String CMD_TXT = "[java -jar AXMLBuilder.jar [-tag|-attr] [-i|-r|-m] [tag name|attr name] [tag unique ID|attr value] [input name] [output name]";

	public static void main(String[] args){

		/**
		 * �����ʽ��
		 * -i ��Ӷ���
		 * -r ɾ������
		 * -m ���¶���
		 * -attr ����
		 * -tag ��ǩ
		 * ���Բ���ֱ������������ɣ���ǩ������Ҫ������Ϣ
		 */
		
		if(args.length < 3){
			System.out.println("Parameters Error...");
			System.out.println(CMD_TXT);
			return;
		}
		
		String inputfile = args[args.length-2];
		String outputfile = args[args.length-1];
		File inputFile = new File(inputfile);
		File outputFile = new File(outputfile);
		if(!inputFile.exists()){
			System.out.println("Input File Not Exist...");
			return;
		}
		
		//���ļ�
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try{
			fis = new FileInputStream(inputFile);
			bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len=fis.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
			ParserChunkUtils.xmlStruct.byteSrc = bos.toByteArray();
		}catch(Exception e){
			System.out.println("parse xml error:"+e.toString());
		}finally{
			try{
				fis.close();
				bos.close();
			}catch(Exception e){
			}
		}
		
		doCommand(args);
		
		//д�ļ�
		if(!outputFile.exists()){
			outputFile.delete();
		}
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(outputFile);
			fos.write(ParserChunkUtils.xmlStruct.byteSrc);
			fos.close();
		}catch(Exception e){
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public static void testDemo(){
		//ɾ��һ��tag��ɾ��tagʱ����ָ��tag���ƺ�nameֵ����������Ψһȷ��һ��tag��Ϣ
		//XmlEditor.removeTag("uses-permission", "android.permission.INTERNET");
		//XmlEditor.removeTag("activity", ".MainActivity");

		//ɾ�����ԣ�����Ҫָ�����Զ�Ӧ��tag���ƺ�nameֵ��Ȼ�������������
		//XmlEditor.removeAttr("activity", ".MainActivity", "name");
		//XmlEditor.removeAttr("uses-permission", "android.permission.INTERNET", "name");

		//��ӱ�ǩ��ֱ����xml�����ü��ɣ���Ҫע�����������Ϣ��manifest����ı�ǩ������application��ǩ�ĺ���
		//XmlEditor.addTag();

		//������ԣ�����ָ����ǩ����
		//XmlEditor.addAttr("activity", ".MainActivity", "jiangwei", "fourbrother");

		//�������ԣ�����ֱ�Ӳ�����ɾ��������Ӳ������
		//XmlEditor.modifyAttr("application", "package", "debuggable", "true");
	}

	public static void doCommand(String[] args){
		if("-tag".equals(args[0])){
			if(args.length < 2){
				System.out.println("Lack Of Parameters...");
				System.out.println(CMD_TXT);
				return;
			}
			//��ǩ
			if("-i".equals(args[1])){
				if(args.length < 3){
					System.out.println("Lack Of Parameters...");
					System.out.println(CMD_TXT);
					return;
				}
				//�������
				String insertXml = args[2];
				File file = new File(insertXml);
				if(!file.exists()){
					System.out.println("Insert XML Not Exists...");
					return;
				}
				XmlEditor.addTag(insertXml);
				System.out.println("Insert Tag Completed...");
				return;
			}else if("-r".equals(args[1])){
				if(args.length < 4){
					System.out.println("Lack Of Parameters...");
					System.out.println(CMD_TXT);
					return;
				}
				//ɾ������
				String tag = args[2];
				String tagName = args[3];
				XmlEditor.removeTag(tag, tagName);
				System.out.println("Remove Tag Completed...");
				return;
			}else{
				System.out.println("Tag Parameters Error...");
				System.out.println(CMD_TXT);
				return;
			}
		}else if("-attr".equals(args[0])){
			if(args.length < 2){
				System.out.println("Lack Of Parameters...");
				System.out.println(CMD_TXT);
				return;
			}
			//����
			if("-i".equals(args[1])){
				if(args.length < 6){
					System.out.println("Lack Of Parameters...");
					System.out.println(CMD_TXT);
					return;
				}
				//��������
				String tag = args[2];
				String tagName = args[3];
				String attr = args[4];
				String value = args[5];
				XmlEditor.addAttr(tag, tagName, attr, value);
				System.out.println("Insert Attribute Completed...");
				return;
			}else if("-r".equals(args[1])){
				if(args.length < 5){
					System.out.println("Lack Of Parameters...");
					System.out.println(CMD_TXT);
					return;
				}
				//ɾ������
				String tag = args[2];
				String tagName = args[3];
				String attr = args[4];
				XmlEditor.removeAttr(tag, tagName, attr);
				System.out.println("Remove Attribute Completed...");
				return;
			}else if("-m".equals(args[1])){
				if(args.length < 6){
					System.out.println("Lack Of Parameters...");
					System.out.println(CMD_TXT);
					return;
				}
				//�޸�����
				String tag = args[2];
				String tagName = args[3];
				String attr = args[4];
				String value = args[5];
				XmlEditor.modifyAttr(tag, tagName, attr, value);
				System.out.println("Modify Attribute Completed...");
			}else{
				System.out.println("Attribute Parameters Error...");
				System.out.println(CMD_TXT);
				return;
			}
		}
	}
	
}
