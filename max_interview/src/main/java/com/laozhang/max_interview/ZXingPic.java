package com.laozhang.max_interview;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ZXingPic {

    private final static Logger logger = LoggerFactory.getLogger(ZXingPic.class);

    public static void generateBarCodeFile(BarCodeInfo barCodeInfo, File file) throws IOException {
        logger.debug("generate QR File");
        // 定义条形码的内容
        StringBuilder sb = new StringBuilder();
        sb.append(barCodeInfo.getValue());
        logger.debug("start generate QR bitmatrix");
             BufferedImage bim = getBar_CODEBufferedImage(sb.toString(), barCodeInfo.getFormat(), barCodeInfo.getWidth(), barCodeInfo.getHeight(), getDecodeHintType());
        logger.debug("start generate QR File");
        addLogo_Bar_Code(bim, file);
    }

    public static void addLogo_Bar_Code(BufferedImage image, File qrSrc) throws IOException {
        try {
            Graphics2D graphics2D = image.createGraphics();
            graphics2D.dispose();
            ImageIO.write(image, "jpeg", qrSrc);
        }catch (IOException e){
            throw e;
        }finally {

        }
    }


    /**
     * 生成条形码
     *
     * @param content       图片内容
     * @param barcodeFormat 编码类型
     * @param width         图片宽度
     * @param height        图片高度
     * @param hints         设置参数
     * @return
     */
    public static BufferedImage getBar_CODEBufferedImage(String content, BarcodeFormat barcodeFormat, int width, int height, Map<EncodeHintType, ?> hints) {


        int codeWidth = 5+(7*6)+5+(7*6)+5;
        codeWidth = Math.max(codeWidth,width);

        MultiFormatWriter multiFormatWriter = null;
        BitMatrix bm = null;
        BufferedImage bufferedImage = null;

        try {
            multiFormatWriter = new MultiFormatWriter();

            // 设置参数顺序依次为内容，编码类型，图片宽度，图片高度，设置参数
            bm = multiFormatWriter.encode(content, barcodeFormat, codeWidth, height, hints);

            if(barcodeFormat != null && "QR_CODE".equals(barcodeFormat)) {
                bm = deleteWhite(bm);
            }

            int w = bm.getWidth();
            int h = bm.getHeight();
            bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    bufferedImage.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferedImage;
    }

    /**
     * 解析条形码
     * @param file
     * @return
     */
    public String decode(File file) {
        BufferedImage image = null;
        Result result = null;
        try {
            // 读取源文件
            image = ImageIO.read(file);
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            // 缓存亮度二维码图片
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            // 混合二进制生成位图
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType,String> hints = new HashMap<DecodeHintType,String>();
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 设置二维码的格式参数
     *
     * @return
     */
    @SuppressWarnings("deprecation")
	public static Map<EncodeHintType, Object> getDecodeHintType() {
        // 用于设置BarCode条形码参数
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 设置BarCode条形码参数的纠错级别(H为最高级别)具体级别信息
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 设置编码方式
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MAX_SIZE, 400);
        hints.put(EncodeHintType.MIN_SIZE, 100);
        hints.put(EncodeHintType.MARGIN, 1);
        return hints;
    }

    public static BitMatrix deleteWhite(BitMatrix matrix){
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }

}
