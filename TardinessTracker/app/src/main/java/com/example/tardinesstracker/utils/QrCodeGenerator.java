package com.example.tardinesstracker.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeGenerator {
    
    /**
     * Generates a QR code bitmap from student data
     * @param studentId The student ID
     * @param fullName The student's full name
     * @param width Width of the QR code
     * @param height Height of the QR code
     * @return Bitmap containing the QR code
     */
    public static Bitmap generateQrCode(int studentId, String fullName, int width, int height) {
        try {
            // Create data string to encode
            String qrData = "STUDENT:" + studentId + ":" + fullName;
            
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix bitMatrix = writer.encode(qrData, BarcodeFormat.QR_CODE, width, height);
            
            // Convert bit matrix to bitmap
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
            
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
