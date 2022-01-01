package com.dal.hiree.client;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

public class AWSSNSClient {
    private static AWSSNSClient awssnsHelper;
    private AmazonSNSClient amazonSNSClient;
    private final String AWS_ARN = "arn:aws:sns:us-east-1:900746859751:HireEmail";
    private final String ACCESS_KEY = "ASIA5DOFSKTTT5MTZQQS";
    private final String SECRET_KEY = "+f4Pt1Id4F0Y3yienw5yXP3A88622v/Nyph/pH4o";
    private final String TOKEN = "FwoGZXIvYXdzELr//////////wEaDH517v2YIAiInJSsYSK+ASKKqR9YF3ScVxZ2IN7Ta40/e8nkZ77MbLn97wPrObRnyNotPdAddj/Oz2un6lUAlhsxDmn5vvKmL0h+eqKbZabgw4lOiLHaBjwwOAbyiB5OeJh0tOb7G92gF6K6N0PwXovEklm/EPjw0l+IJCWozA1eF7xtWBL5IHqYbUKI1aS06AzfrBTD3xn8yCbUugXEmO9mvSann9iaogiwBzn537Vuq4dJ10cKm9vGty9csgFI5yV9fei5Kk+G1qzmAngo+q61jQYyLWhHd2aZQYoXOBG/+SHksUdeeuQ9uTLvXZtmlN9z8lFCU8xvCCWPkQOfH9ckDQ==";

    private AWSSNSClient() {
        BasicSessionCredentials sessionCredentials = new BasicSessionCredentials(ACCESS_KEY, SECRET_KEY, TOKEN);
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(sessionCredentials);

        amazonSNSClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard().withCredentials(credentialsProvider)
                .withRegion(Regions.US_EAST_1).build();
    }

    public static AWSSNSClient getInstance() {
        if(awssnsHelper == null) {
            awssnsHelper = new AWSSNSClient();
        }
        return awssnsHelper;
    }

    public void sendEmail(String subject, String emailBody) {
        amazonSNSClient.publish(AWS_ARN, emailBody, subject);
    }

    public void subscribe(String email) {
        amazonSNSClient.subscribe(AWS_ARN, "email",email);
    }

    public void uploadFileToS3(MultipartFile file) throws IOException {
        System.out.println(Arrays.toString(file.getBytes()));
    }

    public static void main(String[] args) {
        AWSSNSClient awssnsHelper = new AWSSNSClient();
        awssnsHelper.subscribe("devrajvit@gmail.com");
    }
}