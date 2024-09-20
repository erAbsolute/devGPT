package com.erabsolute.discord.bots.listeners.actions.commands;

import static com.erabsolute.discord.bots.utils.GenericEmbeds.getDefaultEmbed;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public class Qrfy {

  public static EmbedBuilder qrfy(User user, Guild guild) {
    EmbedBuilder embed = getDefaultEmbed(user, guild);
    embed.setImage("attachment://qr.png");
    embed.setThumbnail(null);
    return embed;
  }

  public static ByteArrayOutputStream generateQRCodeImage(String barcodeText) throws Exception {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    QRCodeWriter barcodeWriter = new QRCodeWriter();
    File logoFile = new File("resources/logo.png");
    Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    BitMatrix bitMatrix =
        barcodeWriter.encode(barcodeText, BarcodeFormat.QR_CODE, 4096, 4096, hints);
    BufferedImage logo = ImageIO.read(logoFile);
    BufferedImage qr = MatrixToImageWriter.toBufferedImage(bitMatrix);
    BufferedImage combined = new BufferedImage(4096, 4096, BufferedImage.TYPE_INT_ARGB);
    // paint both images, preserving the alpha channels
    Graphics g = combined.getGraphics();
    g.drawImage(qr, 0, 0, null);
    g.drawImage(logo, 1488, 1488, null);
    g.fillRect(20, 20, 100, 100);
    g.dispose();

    ImageIO.write(combined, "png", baos);
    return baos;
  }
}
