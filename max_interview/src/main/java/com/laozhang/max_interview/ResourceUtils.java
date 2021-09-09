package com.laozhang.max_interview;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.*;

public abstract class ResourceUtils {

	/**
	 * 关闭打开的资源。
	 * 
	 * @param resouce
	 *            打开的资源。
	 */
	public static void close(Closeable resouce) {
		if (resouce != null) {
			try {
				resouce.close();
			} catch (IOException e) {
				// 忽略
			}
		}
	}

	/**
	 * 读取文件中的二进制数据，返回一个字节数组。
	 * 
	 * @param file
	 *            文件对象。
	 * 
	 * @return 一个字节数组。
	 * 
	 * @throws Exception
	 *             如果文件内容太大。
	 */
	public static byte[] getBytes(File file) throws Exception {
		if (file.isDirectory()) {
			throw new Exception();
		} else {
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				return getBytes(is, file.length());
			} catch (IOException ext) {
				throw new Exception();
			}
		}
	}

	// 从流中读取二进制数据
	private static byte[] getBytes(InputStream is, long len) {
		BufferedInputStream bis = null;
		try {
			if (len > Integer.MAX_VALUE) {
				throw new RuntimeException();
			}
			bis = new BufferedInputStream(is);
			byte[] ret = new byte[(int) len];
			bis.read(ret);
			return ret;
		} catch (IOException ext) {
			throw new RuntimeException();
		} finally {
			close(bis);
		}
	}

	/**
	 * 读取资源内容二进制数据，返回一个字节数组。
	 * 
	 * @param resource
	 *            资源内容。
	 * 
	 * @return 一个字节数组。
	 * 
	 * @throws Exception
	 *             如果资源对应的文件内容太大。
	 */
	public static byte[] getBytes(Resource resource) throws Exception {
		InputStream is = null;
		try {
			is = resource.getInputStream();
			return getBytes(is, resource.contentLength());
		} catch (IOException ext) {
			throw new Exception();
		} finally {
			close(is);
		}
	}

	/**
	 * 读取指定路径对应的文件。
	 * 
	 * @param path
	 *            资源文件的路径。
	 * 
	 * @return 一个资源对象。
	 */
	public static Resource getResource(String path) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource resource = resolver.getResource(path);
		return resource;
	}

}
