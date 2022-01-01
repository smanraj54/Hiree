package com.dal.hiree.client;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.sns.AmazonSNSClient;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;


public class AWSS3SERVICE {

    private final String ACCESS_KEY = "ASIA5DOFSKTTT5MTZQQS";
    private final String SECRET_KEY = "+f4Pt1Id4F0Y3yienw5yXP3A88622v/Nyph/pH4o";
    private final String TOKEN = "FwoGZXIvYXdzELr//////////wEaDH517v2YIAiInJSsYSK+ASKKqR9YF3ScVxZ2IN7Ta40/e8nkZ77MbLn97wPrObRnyNotPdAddj/Oz2un6lUAlhsxDmn5vvKmL0h+eqKbZabgw4lOiLHaBjwwOAbyiB5OeJh0tOb7G92gF6K6N0PwXovEklm/EPjw0l+IJCWozA1eF7xtWBL5IHqYbUKI1aS06AzfrBTD3xn8yCbUugXEmO9mvSann9iaogiwBzn537Vuq4dJ10cKm9vGty9csgFI5yV9fei5Kk+G1qzmAngo+q61jQYyLWhHd2aZQYoXOBG/+SHksUdeeuQ9uTLvXZtmlN9z8lFCU8xvCCWPkQOfH9ckDQ==";
    private AmazonS3 client = null;
    private String bucketName = "hiree-csci5409";
    private static AWSS3SERVICE awss3SERVICE = null;
    private AWSS3SERVICE(){
        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(ACCESS_KEY, SECRET_KEY, TOKEN);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(sessionCredentials);

        client = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion(Regions.US_EAST_1).build();
    }

    public static AWSS3SERVICE getInstance(){
        if(awss3SERVICE == null){
            awss3SERVICE = new AWSS3SERVICE();
        }
        return awss3SERVICE;
    }

    public void uploadObject(MultipartFile file ) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());
        client.putObject(bucketName, file.getOriginalFilename(), file.getInputStream(), objectMetadata);

    }

    public void getObject(String fileName) throws IOException {
        fileName = "Manraj_Singh_Resume_Dalhousie Final.pdf";
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);

        S3Object object = client.getObject(getObjectRequest);
        S3ObjectInputStream inputStream = object.getObjectContent();
        FileUtils.copyInputStreamToFile(inputStream, new File("/Users/manrajsingh/Documents/AWS/"+ fileName));

    }

}
