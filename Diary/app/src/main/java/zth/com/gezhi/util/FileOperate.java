package zth.com.gezhi.util;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.PermissionInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件操作工具类,操作sdcard和程序缓存目录,主要功能: 1.自动判断权限，智能初始化 2.建立，删除，获取文件目录和文件
 * 3.日志提示，删除目录，计算时间 4.自动去掉不能为文件名的常见特殊符号 (:,?,/,.,*,|,\) 5.除注释外，其他全部英文，防止编码报错
 * encoding:utf-8
 * 
 * @author pursuege 2014-4-5 Email:pursuege@gmail.com QQ:751190264
 * 
 */
public class FileOperate {
	/**
	 * 文件名称不能是以下这些字符
	 */
	public static final String fileNameRule = "[:?\\/\\*\\|]";
	/**
	 * 文件目录名称不能是以下这些字符
	 */
	public static final String fileDirRule = "[:?]";
	/**
	 * log日志信息标题,方便在DDMS打印,可自己更改
	 */
	public static final String LOG_TAG = "log_file";
	/**
	 * 目录
	 */
	private static String appDir = "mnt/sdcard/";
	/**
	 * sdcard下面的默认目录,注意如果替换，要符合命名规范
	 */
	private static String defaultDir = "CacheFile";
	private static boolean isCacheInit = false;
	/**
	 * 文件名称最大长度
	 */
	private static int MAX_LENGTH = 250;

	/**
	 * 在sdcard下面建立根目录,如果没有调用此方法,就为:mnt/sdcard/defaultDir,也无法验证权限
	 * 
	 * @param sdcardDir
	 *            在sdcard卡下面建立目录的名称
	 */
	public static void initSdcardFileDir(Context context, String sdcardDir) {
		sdcardDir = fileDirReplace(sdcardDir);
		defaultDir = sdcardDir;
		PackageManager mPackageMgr = context.getPackageManager();
		try {
			PackageInfo pack = mPackageMgr.getPackageInfo(
					context.getPackageName(), PackageManager.GET_PERMISSIONS);
			// PermissionInfo[] permissions = pack.permissions; // 这里为null
			String[] permissArray = pack.requestedPermissions;
			boolean hashPermission = false;
			for (String per : permissArray) {
				if (per.equals(permission.WRITE_EXTERNAL_STORAGE.toString())) {
					hashPermission = true;
				}
			}
			if (!hashPermission) {
				logFile("please write WRITE_EXTERNAL_STORAGE permission!");
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建缓存目录，程序启动必须调用一下,否则会出错 目录: data/data/packename/cache
	 */
	public static void initCacheFileDir(Context context) {
		// data/data/com..../cache
		File fileDir = context.getApplicationContext().getCacheDir();
		appDir = fileDir.getPath();
		PackageManager mPackageMgr = context.getPackageManager();
		try {
			PackageInfo pack = mPackageMgr.getPackageInfo(
					context.getPackageName(), PackageManager.GET_PERMISSIONS);
			PermissionInfo[] permissions = pack.permissions; // 这里为null
			String[] permissArray = pack.requestedPermissions;
			boolean hashPermission = false;
			for (String per : permissArray) {
				if (per.equals(permission.MOUNT_UNMOUNT_FILESYSTEMS.toString())) {
					hashPermission = true;
				}
			}
			if (!hashPermission) {
				logFile("please write MOUNT_UNMOUNT_FILESYSTEMS permission!");
			}
			isCacheInit = hashPermission;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 判断sdcard是否存在
	 * 
	 * @return return
	 */
	public static boolean sdcardExist() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
	}

	/**
	 * 得到sdcard根目录
	 * 
	 * @return
	 */
	public static String getSdcardDir() {
		if (sdcardExist()) {
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		} else {
			return "-1,sdcard not exist!";
		}
	}

	/**
	 * 在缓存下创建一个目录
	 * 
	 * @param subDir dir
	 * @return return
	 */
	public static File mkdirCacheDirs(String subDir) {
		subDir = fileDirReplace(subDir);
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return null;
		}
		File subFile = new File(appDir + "/" + subDir);
		if (!subFile.exists()) {
			subFile.mkdirs();
		}
		return subFile;
	}

	/**
	 * 得到缓存文件的根目录
	 * 
	 * @param cacheDir
	 *            (如果为null，那么统计的就是cache目录)
	 * @return 文件目录
	 */
	public static String getCacheDirsPath(String cacheDir) {
		if (cacheDir == null) {// 如果根目录为空，那么统计的就是cache目录
			File file = new File(appDir);
			if (file.exists()) {
				return file.getPath();
			} else {
				return "-1,this dir not exist!" + file.getPath();
			}
		}
		cacheDir = fileDirReplace(cacheDir);
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return "not call initCacheFileDir()";
		}
		File file = new File(appDir + "/" + cacheDir);
		if (file.exists()) {
			return file.getPath();
		} else {
			return "-1,this dir not exist!" + file.getPath();
		}
	}

	/**
	 * 得到缓存文件的根目录
	 * 
	 * @param cacheDir dir
	 * @return 文件目录
	 */
	public static String getCacheFilePath(String cacheDir, String fileName) {
		cacheDir = fileDirReplace(cacheDir);
		fileName = fileNameReplace(fileName);
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return "not call initCacheFileDir()";
		}
		File fileDir = new File(appDir + "/" + cacheDir);
		if (!fileDir.exists()) {
			return "-1,this dir not exist!" + fileDir.getPath();
		}
		File file = new File(appDir + "/" + cacheDir + "/" + fileName);
		if (file.exists()) {
			return file.getPath();
		} else {
			return "-1,this file not exist!" + file.getPath();
		}
	}

	/**
	 * 得到缓存文件的根目录
	 * 
	 * @param cacheDir dir
	 * @return 文件目录
	 */
	public static String getSdcardDirsPath(String cacheDir) {
		if (cacheDir == null) {
			File file = new File(getSdcardDir() + "/" + defaultDir);
			if (file.exists()) {
				return file.getPath();
			} else {
				return "-1,dir not exist!";
			}
		}
		cacheDir = fileDirReplace(cacheDir);
		File file = new File(getSdcardDir() + "/" + defaultDir + "/" + cacheDir);
		if (file.exists()) {
			return file.getPath();
		} else {
			return "-1,dir not exist!";
		}
	}

	/**
	 * 得到缓存文件的根目录
	 * 
	 * @param cacheDir dir
	 * @return 文件目录
	 */
	public static String getSdcardFilePath(String cacheDir, String fileName) {
		cacheDir = fileDirReplace(cacheDir);
		fileName = fileNameReplace(fileName);
		File file = new File(getSdcardDir() + "/" + defaultDir + "/" + cacheDir
				+ "/" + fileName);
		if (file.exists()) {
			return file.getPath();
		} else {
			return "-1,this dir not exist!";
		}
	}

	/**
	 * 在缓存下创建一个文件夹,若没有调用init方法初始化一般就为:/mnt/sdcard/defaultDir/
	 * 
	 * @param subDir
	 * @return
	 */
	public static File mkdirSdcardDirs(String subDir) {
		subDir = fileDirReplace(subDir);
		StringBuffer sb = new StringBuffer(getSdcardDir());
		sb.append("/").append(defaultDir).append("/").append(subDir);
		File subFile = new File(sb.toString());
		if (!subFile.exists()) {
			subFile.mkdirs();
		}
		return subFile;
	}

	/**
	 * 在缓存下创建文件夹
	 * 
	 * @param subDir
	 *            父目录
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static File mkdirCacheFile(String subDir, String fileName) {
		subDir = fileDirReplace(subDir);
		fileName = fileNameReplace(fileName);
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return null;
		}
		File subFile = mkdirCacheDirs(subDir);
		if (!subFile.exists()) {
			subFile.mkdirs();
		}
		File file = new File(subFile.getPath() + "/" + fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logFile("mkdir cache file failed," + file.getPath());
			}
		}
		return file;
	}

	/**
	 * 在sd卡下创建文件,若没有调用init方法初始化一般就为:/mnt/sdcard/defaultDir/
	 * 
	 * @param subDir
	 *            父目录
	 * @param fileName
	 *            文件名称
	 * @return
	 */
	public static File mkdirSdcardFile(String subDir, String fileName) {
		subDir = fileDirReplace(subDir);
		fileName = fileNameReplace(fileName);
		if (!sdcardExist()) {
			logFile("sdcard not exit");
			return null;
		}
		StringBuffer sb = new StringBuffer(getSdcardDir());
		sb.append("/").append(defaultDir).append("/").append(subDir);
		File subFile = new File(sb.toString());
		if (!subFile.exists()) {
			subFile.mkdirs();
		}
		sb.append("/").append(fileName);
		File file = new File(sb.toString());
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logFile("mkdir sdcard file failed:" + sb.toString());
			}
		} else {
			logFile("mkdir sdcard file success:" + file.getPath());
		}
		return file;
	}

	/**
	 * 清空sdcard下面的某个目录下面的所有文件(包括下面子目录中的文件,和目录本身),文件较多时建议开启线程删除
	 * 
	 * @param dir
	 * @param isDeleteDir
	 *            是否删除此目录本身(指的不是下面的子目录)
	 */
	public static void deleteSdcardDirectory(String dir, boolean isDeleteDir) {
		dir = fileDirReplace(dir);
		number = 0;
		long startTime = System.currentTimeMillis();
		int fileMumber = 0;
		File file = new File(getSdcardDir() + "/" + defaultDir + "/" + dir);
		if (!file.exists()) {
			logFile("dir is not exist:" + file.getPath());
			return;
		}
		if (!file.isDirectory()) {
			logFile("delete failed,this is not a airectory");
			return;
		}
		fileMumber = deleteFile(file, isDeleteDir);
		logFile("delete " + file.getPath() + "," + fileMumber
				+ "files and dir,time:"
				+ (System.currentTimeMillis() - startTime));
	}

	/**
	 * 删除sdcard下面的某个目录下面的某个文件
	 * 
	 * @param dir
	 * @param key
	 */
	public static void deleteSdcardFile(String dir, String key) {
		dir = fileDirReplace(dir);
		key = fileNameReplace(key);
		File file = new File(getSdcardDir() + "/" + defaultDir + "/" + dir
				+ "/" + key);
		file.delete();
		if (!file.exists()) {
			logFile(file.getPath() + ",this file is not exist");
			return;
		}
	}

	/**
	 * 删除sd卡下面建立的本程序的目录:mnt/sdcard/defaultDir
	 */
	public static void clearSdcardDir() {
		File file = new File(getSdcardDir() + "/" + defaultDir);
		if (!file.exists()) {
			logFile(file.getPath() + ",this dir is not exist");
			return;
		}
		long startTime = System.currentTimeMillis();
		int fileMumber = 0;
		number = 0;
		fileMumber = deleteFile(file, true);
		logFile("delete:" + file.getPath() + "," + fileMumber
				+ "files and dir,time:"
				+ (System.currentTimeMillis() - startTime));
	}

	/**
	 * 删除缓存下面的某个目录下面的某个文件
	 * 
	 * @param dir dir
	 * @param key key
	 */
	public static void deleteCacheFile(String dir, String key) {
		dir = fileDirReplace(dir);
		key = fileNameReplace(key);
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return;
		}
		File fileDir = new File(appDir + "/" + dir);
		if (!fileDir.exists()) {
			logFile("this dir is not exist!" + fileDir.getPath());
			return;
		}
		File file = new File(appDir + "/" + dir + "/" + key);
		if (file.exists()) {
			file.delete();
		} else {
			logFile("this file is not exist!" + file.getPath());
		}
	}

	/**
	 * 删除缓存下面的某个目录下面的所有文件
	 * 
	 * @param dir
	 *            (若未null,删除目录本身)
	 * @param isDeleteDir
	 *            是否删除目录本身
	 */
	public static void deleteCacheDir(String dir, boolean isDeleteDir) {
		dir = fileDirReplace(dir);
		number = 0;
		long startTime = System.currentTimeMillis();
		int fileMumber = 0;
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return;
		}
		File fileDir = null;
		if (null == dir) {
			fileDir = new File(appDir);
		} else {
			fileDir = new File(appDir + "/" + dir);
		}
		if (!fileDir.exists()) {
			logFile("this dir is not exist!" + fileDir.getPath());
			return;
		}
		fileMumber = deleteFile(fileDir, isDeleteDir);
		if (isDeleteDir) {
			fileDir.delete();
		}
		logFile("delete" + fileDir.getPath() + "," + fileMumber
				+ "files and dir,time:"
				+ (System.currentTimeMillis() - startTime));
	}

	/**
	 * 清除程序缓存下面的所有数据,data/data/packagename/cache,不包括cache目录本身
	 */
	public static void clearCacheDir() {
		long startTime = System.currentTimeMillis();
		int fileMumber = 0;
		number = 0;
		if (!isCacheInit) {
			logFile("you not call initCacheFileDir(),not init");
			return;
		}
		File fileDir = new File(appDir);
		if (!fileDir.exists()) {
			logFile("this dir is not exist!" + fileDir.getPath());
			return;
		}
		fileMumber = deleteFile(fileDir, true);
		logFile("delete" + fileDir.getPath() + "," + fileMumber
				+ "files and dir,time:"
				+ (System.currentTimeMillis() - startTime));
	}

	private static int number = 0;

	/**
	 * 删除目录下面的文件
	 * 
	 * @param file file
	 * @param isDeleteDir
	 *            是否删除目录本身
	 * @return return
	 */
	public static int deleteFile(File file, boolean isDeleteDir) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i], true); // 把每个文件 用这个方法进行迭代
					number++;
				}
				if (isDeleteDir) {
					file.delete();// 删除此目录
				}
			} else {
				file.delete();
			}
		}
		return number;
	}

	/**
	 * 统计目录下面有多少个文件,目录不算一个文件
	 * 
	 * @param dir dir
	 * @param isSdcard
	 *            是否是sdcard文件
	 * @return
	 */
	public static int countDircetoryNumber(String dir, boolean isSdcard) {
		dir = fileDirReplace(dir);
		File file = null;
		if (!defaultDir.equals(dir)) {
			if (isSdcard) {
				file = new File(getSdcardDirsPath(dir));
			} else {
				file = new File(getCacheDirsPath(dir));
			}
		} else {
			if (isSdcard) {
				file = new File(getSdcardDir() + "/" + defaultDir);
			} else {
				file = new File(appDir);
			}
		}
		if (null == file) {
			logFile("file:" + file);
			return -1;
		}
		if (!file.exists()) {
			logFile("count dir is not exist:" + file.getPath());
			return -1;
		}
		return (int) FileUtil.getCacheDataNumber(file);
	}

	/**
	 * 统计目录下面有文件总大小
	 * 
	 * @param dir
	 * @param isSdcard
	 *            是否是sdcard卡文件
	 * @return byte
	 */
	public static long countDircetoryTotalSize(String dir, boolean isSdcard) {
		dir = fileDirReplace(dir);
		File file = null;
		if (isSdcard) {
			file = new File(getSdcardDirsPath(dir));
		} else {
			file = new File(getCacheDirsPath(dir));
		}
		if (null == file) {
			logFile("file:" + file);
			return -1;
		}
		if (!file.exists()) {
			logFile("count dir is not exist:" + file.getPath());
			return -1;
		}
		return FileUtil.getCacheDataTotalSize(file);
	}

	/**
	 * 文件日志
	 * 
	 * @param msg
	 *            日志内容
	 */
	public static void logFile(String msg) {
		Log.i(LOG_TAG, msg);
	}

	/**
	 * 去掉文件名中的特殊符号
	 * 
	 * @param fileName
	 */
	public static String fileNameReplace(String fileName) {
		if (null == fileName) {
			return "";
		}
		if (fileName.length() >= MAX_LENGTH) {
			fileName = fileName.substring(fileName.length() - MAX_LENGTH,
					fileName.length());
		}
		fileName = fileName.replaceAll(fileNameRule, "");
		return fileName;
	}

	/**
	 * 去掉文件名中的特殊符号
	 * 
	 * @param dirName
	 */
	public static String fileDirReplace(String dirName) {
		if (null == dirName) {
			return "";
		}
		if (dirName.length() >= MAX_LENGTH) {
			dirName = dirName.substring(dirName.length() - MAX_LENGTH,
					dirName.length());
		}
		dirName = dirName.replaceAll(fileDirRule, "");
		return dirName;
	}

	/**
	 * 统计文件的工具类
	 * 
	 * @author Administrator
	 * 
	 */
	private static class FileUtil {
		private static final String countKey = "fileCount";
		private static final String contextKey = "contextLegnths";

		/**
		 * 得到此目录下面有多少个缓存文件
		 * 
		 * @param dir
		 * @return
		 */
		public static long getCacheDataNumber(File dir) {
			// 计算文件多少
			return getFilesDetail(dir).get(countKey);
		}

		/**
		 * 得到此目录下面缓存文件的总大小
		 * 
		 * @param dir
		 * @return 中大小,byte
		 */
		public static long getCacheDataTotalSize(File dir) {
			// 计算文件多少
			return getFilesDetail(dir).get(contextKey);
		}

		private static Map<String, Long> getFilesDetail(File dir) {
			dirAssert(dir);
			Map<String, Long> map = new HashMap<String, Long>();
			map.put(countKey, 0L);
			map.put(contextKey, dir.length());
			// map.put(contextKey, 0L);
			// System.out.println(dir.getName() + " = " + dir.length());
			filesCount(dir, map);
			return map;
		}

		private static void add(long i, String key, Map<String, Long> map) {
			Long count = map.get(key);
			map.put(key, count + i);
		}

		private static void dirAssert(File dir) {
			if (dir == null || !dir.isDirectory()) {
				throw new IllegalArgumentException("非法参数");
			}
		}

		private static void filesCount(File dir, Map<String, Long> map) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) {
					filesCount(file, map);
				} else {
					add(1, countKey, map);
					add(file.length(), contextKey, map);
				}
			}
		}

	}

	public static String getdefaultDirName() {
		return defaultDir;
	}

	/**
	 * 重命名
	 * 
	 * @param filePath file
	 * @param newPath path
	 */
	public static void renameFile(String filePath, String newPath) {
		File file = new File(filePath);
		if (!file.exists()) {
			logFile("file not exist:" + filePath);
			return;
		}
		File newFile = new File(newPath);
		if (!newFile.exists()) {
			logFile("new file not exist:" + newFile);
			return;
		}
		file.renameTo(newFile);
	}

	public static void saveInputToFile(InputStream in, String path)
			throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = new FileOutputStream(file);
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = in.read(b)) != -1) {
			out.write(b,0,len);
		}
		out.flush();
		in.close();
		out.close();
	}

	/**
	 * 获取文件夹大小
	 * @param file File实例
	 * @return long
	 */
	public static long getFolderSize(File file){

		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++)
			{
				if (fileList[i].isDirectory())
				{
					size = size + getFolderSize(fileList[i]);

				}else{
					size = size + fileList[i].length();

				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//return size/1048576;
		return size;
	}

	/**
	 * 删除指定目录下文件及目录
	 * @param deleteThisPath path
	 * @param filePath path
	 * @return
	 */
	public void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 处理目录
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 格式化单位
	 * @param size size
	 * @return return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size/1024;
		if(kiloByte < 1) {
			return size + "B";
		}

		double megaByte = kiloByte/1024;
		if(megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte/1024;
		if(gigaByte < 1) {
			BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte/1024;
		if(teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
}
