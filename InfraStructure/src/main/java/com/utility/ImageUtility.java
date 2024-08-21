package com.utility;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

/**
 * @Creator 8/17/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
public class ImageUtility {

    public static OutputAPIForm validateImage(byte[] img, int height,int width){

        OutputAPIForm retVal = new OutputAPIForm();
        try{
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(img));
            if(image.getHeight() > height || image.getWidth() > width){
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.INVALID_IMAGE);
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.INVALID_IMAGE);
            log.error("Error in convert of picture" + e.getMessage());
        }
        return retVal;

    }
}
