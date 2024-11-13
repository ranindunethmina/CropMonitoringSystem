package lk.ijse.crop_monitoring_system.util;

import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.UUID;

public class AppUtil {
    public static String createFieldId(){
        return "FIELD-"+ UUID.randomUUID();
    }

    public static String createCropCode(){
        return "CROP-"+ UUID.randomUUID();
    }

    public static String createStaffId(){
        return "STAFF-"+ UUID.randomUUID();
    }

    public static String createLogID(){
        return "LOG-"+ UUID.randomUUID();
    }

    public static String createVehicleCode(){
        return "VEHICLE-"+ UUID.randomUUID();
    }

    public static String createEquipmentId(){
        return "EQUIPMENT-"+ UUID.randomUUID();
    }

    public static String toBase64(MultipartFile file){
        String imageBase64 = null;
        try {
            imageBase64 =  Base64.getEncoder().encodeToString(file.getBytes());
        }catch (Exception e){
            e.printStackTrace();
        }
        return imageBase64;
    }

}