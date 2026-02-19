package ch.bbw.pr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 * A barcode reader and decoder using the laptop camera.
 * Uses bytedeco to use the camera.
 * Uses https://github.com/zxing/zxing to read the barcode.
 * see https://github.com/zxing/zxing about which barcode is supported.
 * see https://barcode4j.sourceforge.net/examples.html for barcode examples
 *
 * @author Peter Rutschmann
 * @version 1.4.2024
 */
public class CameraBarcodeReaderMain {

   public static void main(String[] args) throws FrameGrabber.Exception {

      // image grabber
      FrameGrabber grabber = FrameGrabber.createDefault(0); // 0 represents the default camera
      grabber.start();

      // bytedeco canvas
      CanvasFrame canvas = new CanvasFrame("barcode reader");
      canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

      // textfield for scanned barcode
      JLabel barcodeLabel = new JLabel("Barcode:");
      JTextField barcodeTextField = new JTextField(20);
      barcodeTextField.setEditable(false);
      JPanel barcodePanel = new JPanel();
      barcodePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      barcodePanel.add(barcodeLabel);
      barcodePanel.add(barcodeTextField);

      // textArea for decoded barcode
      JLabel areaLabel = new JLabel("Decoded barcode:");
      JTextArea textArea = new JTextArea(10, 40);
      textArea.setText("empty");
      JPanel areaPanel = new JPanel();
      areaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
      areaPanel.add(areaLabel);
      areaPanel.add(textArea);

      // button to re-enable barcode scan
      final boolean[] enableEncoding = {true};
      JButton stopButton = new JButton("re-enable scan");
      stopButton.setEnabled(false);
      stopButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            System.out.println("CameraBarcodeReader2.actionPerformed");
            if(!enableEncoding[0]){
               enableEncoding[0] = true;
               stopButton.setEnabled(false);
            }
         }
      });

      // the panel holding the elements
      JPanel controlPanel = new JPanel();
      controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS)); // Vertikales Layout
      controlPanel.add(barcodePanel);
      controlPanel.add(Box.createVerticalStrut(10));
      controlPanel.add(areaPanel);
      controlPanel.add(Box.createVerticalStrut(10));
      controlPanel.add(stopButton);
      controlPanel.add(Box.createVerticalStrut(10));
      canvas.add(controlPanel, BorderLayout.SOUTH);

      // the grabbing, barcode-reading and decoding
      while (true) {
         Frame frame = grabber.grab();
         if (frame != null) {
            if (enableEncoding[0]) {
               String barcode = BarcodeReader.readBarcode(frame);
               if (barcode != null) {
                  System.out.println("CameraBarcodeReader.main barcode: " + barcode);
                  enableEncoding[0] = false;
                  barcodeTextField.setText(barcode);
                  textArea.setText(Decoder.decode(barcode));
                  stopButton.setEnabled(true);
               }
            }
            canvas.showImage(frame);
         }
      }
   }
}