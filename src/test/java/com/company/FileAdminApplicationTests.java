package com.company;

import com.company.controller.UploadController;
import com.company.domain.FileItem;
import com.company.service.FileFinderService;
import com.company.service.FileService;
import com.company.service.enumPackage.SortType;
import com.company.service.impl.FileMenuServiceImpl;
import com.company.service.impl.FileOperationServiceImpl;
import com.company.service.impl.FileServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileAdminApplicationTests {


	@Autowired
	UploadController uploadController;
	@Test
	public void contextLoads() {
		File file = new File("F://images");
		String filePath[];
		String[] files = file.list();
		for (String s : files) {
			s = "F://images//"+s;
			System.out.println(s);
		}

//		for (File file1 : files) {
//			String absolutePath = file1.getAbsolutePath();
//			System.out.println(absolutePath);
//		}
//		boolean b = uploadController.mergeFiles(files, "F://images//img.gif");
//		System.out.println(b);
	}

	/**
	 * 递归操作
	 * @param path
	 * @return
	 */
	public boolean delete(String path){
		File file = new File(path);
		if(!file.exists()){
			return false;
		}
		if(file.isFile()){
			return file.delete();
		}
		File[] files = file.listFiles();
		for (File f : files) {
			if(f.isFile()){
				if(!f.delete()){
					System.out.println(f.getAbsolutePath()+" delete error!");
					return false;
				}
			}else{
				if(!this.delete(f.getAbsolutePath())){
					return false;
				}
			}
		}
		return file.delete();
	}


	/**
	 * 删除文件夹
	 */
	@Test
	public  void  deleteFolder(){
		System.out.println(delete("F://images//static"));
	}


	@Test
	public void listFile(){
		File  file = new File("E:\\HuangChiXin\\images");
		File[] list = file.listFiles();
		for (File s : list) {
			System.out.println(s.getAbsolutePath());
		}
	}

	private String basePath = "F://images" ;
	@Test
	public  void  path(){
		File  file = new File("F:/images/static");
//		String absolutePath = file.getAbsolutePath();
		int length = basePath.length();
		String uri = file.toURI().getPath();
		System.out.println(length);
		uri = uri.substring(length+1,uri.length());
		System.out.println(uri);
//		String[] split = uri.split("/");
//		for (String s : split) {
//			System.out.println(s);
//		}
	}

	@Test
	public void sortByFileType(){
		File[] files = new File(basePath).listFiles();
		List<File> files1 = Arrays.asList(files);
		Collections.sort(files1, (o1,o2)-> {
				if (o1.isDirectory() && o2.isFile()){
					return -1;
				}
				if (o2.isDirectory() && o1.isFile()){
					return 1;
				}
				return o1.getName().compareTo(o2.getName());
			}
		);
		files1.forEach((file)->{
			System.out.println(file.toURI().getPath());
		});
	}

	@Autowired
	FileOperationServiceImpl fileOperationService;
	@Test
	public void sortByFileSize(){
		List<FileItem> fileItemList = fileOperationService.sortBySortType(basePath, SortType.FILESIZEUP);
//
//		File[] files = new File(basePath).listFiles();
//		List<File> files1 = Arrays.asList(files);
//		Collections.sort(files1, new Comparator<File>() {
//			@Override
//			public int compare(File o1, File o2) {
//				if (o1.length() > o2.length()){
//					return 1;
//				}else if  (o1.length() < o2.length()){
//					return -1;
//				}else{
//					return 0;
//				}
//			}
//
//			public boolean equals(Object obj){
//				return true;
//			}
//		}
//		);
		fileItemList.forEach((fileItem)->{
			System.out.println(fileItem);
		});
	}
	@Test
	public void sortFileByFileDate(){
//		File[] files = new File(basePath).listFiles();
//		List<File> files1 = Arrays.asList(files);
//		Collections.sort(files1, (o1, o2)-> {
//						if (o1.lastModified() > o2.lastModified()){
//							return 1;
//						}else if  (o1.lastModified() < o2.lastModified()){
//							return -1;
//						}else{
//							return 0;
//						}
//
//				}
//		);
//		files1.forEach((file)->{
//			System.out.println(file.toURI().getPath());
//		});
		List<FileItem> fileItemList = fileOperationService.sortBySortType(basePath, SortType.FILEDATEDOWN);
		fileItemList.forEach((fileItem)->{
			System.out.println(fileItem);
		});
	}

	@Autowired
	FileMenuServiceImpl fileMenuService;

	@Autowired
	FileFinderService finderService;

	List<FileItem> fileItemList = new CopyOnWriteArrayList<>();

	public List<FileItem> findFile(File file,String keyword){
//		List<FileItem> fileItemList = new ArrayList<>();
		File[] files = file.listFiles();
//		System.out.println(files.length);
		for(File f : files ){
			if (f.getName().contains(keyword)) {
				fileItemList.add(fileMenuService.fileItem(f));
			}
			if(f.isDirectory()){
				this.findFile(f,keyword);
			}
		}
		return fileItemList;
	}

	public List<FileItem> findFileByImgType(File file){
		File[] files = file.listFiles();
//		System.out.println(files.length);
		for(File f : files ){
			if(f != null){
				if(f.isDirectory()){
					this.findFileByImgType(f);
				}else{
					String[] split = f.getName().split("\\.");
//					String regex = "gif|jpg|peg|bmp|png";
//					String regex = "mp4|flv|avi|rm|rmvb|mpeng1-4|mov|mtv|dat|wmv|3gb|amv|dmv|wmv";
//					String  regex = "zip|jar|rar|cab|iso|ace|7z|tar|gz|arj|lzh|uue|bz2|z";
//					String regex = "torrent";
//					String regex = "(?i)(mp3|midi|wma|vqf|amr)";
//					String regex = "(^mp3|midi|wma|vqf|amr|zip|jar|rar|cab|iso|ace|7z|tar|gz|arj|lzh|uue|bz2|z|torrent|gif|jpg|peg|bmp|png|mp4|flv|avi|rm|rmvb|mpeng1-4|mov|mtv|dat|wmv|3gb|amv|dmv|wmv)";

					String regex = "((?!mp3|midi|wma|vqf|amr|zip|jar|rar|cab|iso|ace|7z|tar|gz|arj|lzh|uue|bz2|z|torrent|gif|jpg|peg|bmp|png|mp4|flv|avi|rm|rmvb|mpeng1-4|mov|mtv|dat|wmv|3gb|amv|dmv|wmv).)*";
					if(split.length > 1){
						System.out.println(split[0]+"  "+split[split.length-1]);
						boolean matches = Pattern.matches(regex, split[split.length-1]);
						if (matches) {
							fileItemList.add(fileMenuService.fileItem(f));
						}
					}
				}
			}
		}
//		System.out.println(fileItemList.size());
		return fileItemList;
	}



	@Test
	public void findFileByKeyword(){
		File file = new File("E:\\HuangChiXin");
		List<FileItem> fileItemList = this.findFile(file, "s");
		for (FileItem fileItem:fileItemList){
			System.out.println(fileItem);
		}
	}

	@Autowired
	FileFinderService fileFinder;

	@Value("${upload.path}")
	String path;
	@Test
	public void findFileByType(){
		List<FileItem> fileByImg = fileFinder.findFileByImg(path);
		fileByImg.forEach((fileItem -> System.out.println(fileItem)));
		System.out.println("---------------------------------------------");
		List<FileItem> fileByZip = fileFinder.findFileByZip(path);
		fileByZip.forEach((fileItem -> System.out.println(fileItem)));
		System.out.println("---------------------------------------------");
		List<FileItem> fileByTorrent = fileFinder.findFileByTorrent(path);
		fileByTorrent.forEach((fileItem -> System.out.println(fileItem)));
		System.out.println("---------------------------------------------");
		List<FileItem> fileByVedio = fileFinder.findFileByVedio(path);
		fileByVedio.forEach((fileItem -> System.out.println(fileItem)));
		System.out.println("---------------------------------------------");
		List<FileItem> fileByMusic = fileFinder.findFileByMusic(path);
		fileByMusic.forEach((fileItem -> System.out.println(fileItem)));
		System.out.println("---------------------------------------------");
		List<FileItem> fileByDoc = fileFinder.findFileByDoc(path);
		fileByDoc.forEach((fileItem -> System.out.println(fileItem)));
		System.out.println("---------------------------------------------");
		List<FileItem> fileByOthers= fileFinder.findFileByOthers(path);
		fileByOthers.forEach((fileItem -> System.out.println(fileItem)));


	}

	@Test
	public void findByKey(){
		List<FileItem> filesBykeyWord = finderService.findFilesByKeyword("F:/images", "y");
		filesBykeyWord.forEach((fileItem -> System.out.println(fileItem)));
	}

	@Autowired
	FileServiceImpl fileService;
	@Test
	public void checkIsExist(){
		boolean b = fileService.checkMd5("40de4fe1cfe5ab639e0d7a438a248");
		System.out.println(b);
	}

	@Test
	public  void  test(){
		long i = 1000L;
		long j = 156L;
		System.out.println(j*1.0/i);
	}

}
