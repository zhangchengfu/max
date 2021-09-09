package com.laozhang.max_interview;

import com.google.zxing.BarcodeFormat;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.docx4j.Docx4J;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Doc2PdfUtil {

	private final static Logger logger = LoggerFactory.getLogger(Doc2PdfUtil.class);
	public final static String DOC_SUFFIX = ".doc";
	public final static String DOCX_SUFFIX = ".docx";
	public final static String PDF_SUFFIX = ".pdf";

	public static byte[] docx2pdf(File docx, byte[] watermark, BarCodeInfo... barCodeInfos) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		try {
			logger.debug("docx transfer into pdf");
			// 获得相关水印的路径
			String tempPath = docx.getAbsolutePath().substring(0, docx.getAbsolutePath().lastIndexOf(".")) + "_temp.pdf";
			FileInputStream inStream = new FileInputStream(docx);
			FileOutputStream outStream = new FileOutputStream(new File(tempPath));
			// docx转换成pdf -- 方案1

			CustomXWPFDocument_S_9 document = new CustomXWPFDocument_S_9(inStream);
			document.createNumbering();
			int initPageSize = document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();

			PdfOptions options = PdfOptions.create();
			PdfConverter.getInstance().convert(document, outStream, options);
			logger.debug("docx transfer into pdf finished");
			// 初始化pdf的容器
			logger.debug("start to render pdf");
			PdfReader reader = new PdfReader(tempPath, "PDF".getBytes());
			// 获得pdf的打磨器
			PdfStamper pdfStamper = new PdfStamper(reader, byteArrayOutputStream);
			// 获得pdf文档的页数
			int pageSize = reader.getNumberOfPages() + 1;

			// 获得水印的签章信息
			Image img = null;
			File qrCode = null;
			Image qrCodeImage = null;
			if (barCodeInfos != null && barCodeInfos.length > 0) {
				for (BarCodeInfo barCodeInfo : barCodeInfos) {
					if(barCodeInfo == null){
						continue;
					}
					// 加盖二维码
					qrCode = new File(docx.getAbsolutePath().substring(0, docx.getAbsolutePath().lastIndexOf(".")) + "_qr.jpg");
					logger.debug("QRCode file path" + qrCode.toString());
					ZXingPic.generateBarCodeFile(barCodeInfo, qrCode);
					qrCodeImage = Image.getInstance(qrCode.toString());
					// 设置二维码的位置
//                    qrCodeImage.setAbsolutePosition(408, 765);
//                    qrCodeImage.scaleAbsolute(100, 40);
					qrCodeImage.setAbsolutePosition(barCodeInfo.getX(), barCodeInfo.getY());// 前一个是左右距离，后一个是上下距离
					qrCodeImage.scaleAbsolute(barCodeInfo.getWidth(), barCodeInfo.getHeight());
					logger.debug("QRCode rendering finished!");

					// 给每一页pdf都添加上水印
					for (int i = 1; i <= initPageSize; i++) {
						PdfContentByte over = pdfStamper.getOverContent(i);
						if (qrCodeImage != null) {
							logger.debug("add QRCode...");
							over.addImage(qrCodeImage);
						}
						if (i == (pageSize - 1) && img != null) {
							logger.debug("add stamping...");
							over.addImage(img);
						}
					}
				}

			}
			// 获得水印的签章背景图
			Image logo = null;
			// 设置水印的透明度
			PdfGState state = new PdfGState();
			state.setFillOpacity(0.6f);
			state.setStrokeOpacity(0.2f);
			if (watermark != null && watermark.length > 0) {
				logger.debug("watermark existed");
				logo = Image.getInstance(watermark); // 插入logo
				// 设置水印的背景图所在的位置
				logo.setAbsolutePosition(45, 120);
			}
			for (int i = 1; i <= initPageSize; i++) {
				PdfContentByte over = pdfStamper.getOverContent(i);
				over.setGState(state);
				if (logo != null) {
					logger.debug("add watermark...");
					over.addImage(logo);
				}
			}

			pdfStamper.close();// 关闭
			reader.close();
			outStream.close();
			inStream.close();
			File tempfile = new File(tempPath);
			if (qrCode != null && qrCode.exists()) {
				if (!qrCode.delete())
					logger.warn("临时文件 " + qrCode.getName() + "未删除");
			}
			if (tempfile.exists()) {
				if (!tempfile.delete())
					logger.warn("临时文件 " + tempfile.getName() + "未删除");
			}
			if (docx.exists()) {
				if (!docx.delete())
					logger.warn("临时文件 " + docx.getName() + "未删除");
			}
			logger.debug("docx transfer into pdf finish");
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				byteArrayOutputStream.close();
			} catch (IOException e) {

			}
		}
		return null;
	}

	/**
	 * 将doc文档转换成docx文档
	 *
	 * @param sourcePath 源文件(doc)
	 * @param destPath   目标文件(docx)
	 */
	public static boolean doc2docx(String sourcePath, String destPath) {

		logger.debug("doc transfer into docx");
		PhysicalFonts.setRegex(null);
		try {
			File docFile = new File(sourcePath);
			// 加载doc文档
			WordprocessingMLPackage wordMLPackage = Docx4J.load(docFile);
			// 将doc文档转换成docx文档
			Docx4J.save(wordMLPackage, new File(destPath), Docx4J.FLAG_NONE); // (FLAG_NONE == default == zipped docx)
			if (docFile.exists()) {
				if (!docFile.delete())
					logger.warn("临时文件 " + docFile.getName() + "未删除");
			}
			logger.debug("doc transfer into docx successfully");
			return true;
		} catch (Docx4JException e) {
			e.printStackTrace();
		}
		logger.debug("doc transfer into docx failed");
		return false;
	}

	public static byte[] doc2pdf(String sourcePath, byte[] watermaker, BarCodeInfo... barCodeInfos) {
		String docPath = sourcePath.substring(0, sourcePath.lastIndexOf(".")) + DOCX_SUFFIX;

		if (doc2docx(sourcePath, docPath)) {
			return docx2pdf(new File(docPath), watermaker, barCodeInfos);
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("resultBean", new Object());
		String docPath = HelpUtil.createDoc(resultMap, "url");
		byte[] pdf = null;
		BarCodeInfo barCodeInfo = new BarCodeInfo();
		barCodeInfo.setValue("jsonStr");
		barCodeInfo.setX(480);
		barCodeInfo.setY(740);
		barCodeInfo.setWidth(100);
		barCodeInfo.setHeight(100);
		barCodeInfo.setFormat(BarcodeFormat.QR_CODE);
		// 带水印
		Resource pic = ResourceUtils.getResource("");
		pdf = Doc2PdfUtil.doc2pdf("docPath", ResourceUtils.getBytes(pic),barCodeInfo);
		// 不带水印
		pdf = Doc2PdfUtil.doc2pdf("docPath", null,barCodeInfo);

	}
}
