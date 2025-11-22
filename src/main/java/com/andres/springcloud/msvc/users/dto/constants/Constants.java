package com.andres.springcloud.msvc.users.dto.constants;

import com.andres.springcloud.msvc.users.dto.AddressDto;
import com.andres.springcloud.msvc.users.dto.PersonDto;
import com.andres.springcloud.msvc.users.dto.UserDto;
import com.andres.springcloud.msvc.users.entities.Address;
import com.andres.springcloud.msvc.users.entities.User;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.aws.IBucketUtil;
import pe.com.gamacommerce.corelibraryservicegamacommerce.aggregates.aggregates.util.BucketParams;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.SecureRandom;
import java.sql.Timestamp;
@Slf4j
public class Constants {
    public static final Boolean STATUS_ACTIVE=true;
    public static final Boolean STATUS_INACTIVE=false;
    public static final String NUM_PAG_BY_DEFECT="0";
    public static final String SIZE_PAG_BY_DEFECT="10";
    public static final String ORDER_BY_DEFECT_ALL="createdAt";
    ////    public static final String ORDER_BY_DEFECT_PAGADOR_FLETE="creadoEn";
////    public static final String ORDER_BY_DEFECT_REMITENTE="creadoEn";
////    public static final String ORDER_BY_DEFECT_GUIATRANSPORTISTA="fechaEmision";
    public static final String ORDER_DIRECT_BY_DEFECT="0";
    public static void deleteOldImageFromS3(String imageUrl, IBucketUtil bucketUtil, String bucketName) {
        try {
            String filePath = extractFilePathFromUrl(imageUrl, bucketName);
            if (filePath != null) {
                BucketParams bucketParams = BucketParams.builder()
                        .bucketName(bucketName)
                        .filePath(filePath)
                        .build();
                bucketUtil.deleteFile(bucketParams);
                log.info("File deleted from S3: {}", filePath);
            }
        } catch (Exception e) {
            log.error("Error deleting file from S3: {}", imageUrl, e);
        }
    }
    public static String createDefaultPassword(PersonDto personDto) {
        SecureRandom random = new SecureRandom();
        String randomString = random.ints(48, 122) // ASCII range for alphanumeric and symbols
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // Exclude non-alphanumeric
                .limit(6) // Length of random string
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        String base = personDto.getDocumentNumber() != null ? personDto.getDocumentNumber() : "BASE";
        return base.substring(0, Math.min(base.length(), 4)) + "@" + randomString;
    }
    public static String extractFilePathFromUrl(String imageUrl, String bucketName) {
        try {
            if (imageUrl.contains(bucketName + ".s3.amazonaws.com/")) {
                return imageUrl.substring(imageUrl.indexOf(bucketName + ".s3.amazonaws.com/") + (bucketName + ".s3.amazonaws.com/").length());
            } else if (imageUrl.contains("s3.amazonaws.com/" + bucketName + "/")) {
                return imageUrl.substring(imageUrl.indexOf("s3.amazonaws.com/" + bucketName + "/") + ("s3.amazonaws.com/" + bucketName + "/").length());
            }
            return null;
        } catch (Exception e) {
            log.error("Error extracting file path from URL: {}", imageUrl, e);
            return null;
        }
    }
    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .keycloakId(user.getKeycloakId())
                .admin(user.isAdmin())
                .build();
    }
    public static String getUserInSession() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String username = request.getHeader("X-Username");
        String userId = request.getHeader("X-User-Id");
        return username + " - " + userId;
    }

}
