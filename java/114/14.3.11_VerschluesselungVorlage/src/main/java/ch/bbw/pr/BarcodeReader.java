package ch.bbw.pr;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * BarcodeReder
 *  Based on https://github.com/zxing/zxing
 *  Will convert 1D and 2D Codes to string.
 *  see https://github.com/zxing/zxing about which barcode is supported.
 */
public class BarcodeReader {

   public static BufferedImage toBufferedImage(Frame frame) {
      /*  Make sure that FrameConverters and JavaCV Frame are properly closed
       *   An object that may hold resources (such as file or socket handles) until it is closed.
       *   The close() method of an AutoCloseable object is called automatically when exiting a
       *   try-with-resources block for which the object has been declared in the resource specification header.
       *   This construction ensures prompt release, avoiding resource exhaustion exceptions and errors that
       *   may otherwise occur.
       */
      try (Java2DFrameConverter java2DConverter = new Java2DFrameConverter()) {
         return java2DConverter.convert(frame);
      }
   }

   public static String readBarcode(Frame frame) {
      try {
         BufferedImage bufferedImage = toBufferedImage(frame);
         BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
               new BufferedImageLuminanceSource(bufferedImage)));
         Result result = new MultiFormatReader().decode(binaryBitmap);
         return result.getText();
      } catch (Exception e) {
         e.printStackTrace();
         System.out.println("BarcodeReader.readBarcode: not found yet.");
         return null;
      }
   }

}