package site.dittotrip.ditto_trip.utils;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class S3Service {

  private final AmazonS3 s3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucketName;

  public S3Service(@Value("${cloud.aws.credentials.accessKey}") String accessKey,
                   @Value("${cloud.aws.credentials.secretKey}") String secretKey,
                   @Value("${cloud.aws.region.static}") String region) {
    BasicAWSCredentials awsCredentials= new BasicAWSCredentials(accessKey, secretKey);

    this.s3Client = AmazonS3ClientBuilder.standard()
        .withRegion(region)
        .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
        .build();
  }

  public String uploadFile(MultipartFile file) throws IOException {
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null)
        .withCannedAcl(CannedAccessControlList.PublicRead));

    return s3Client.getUrl(bucketName, fileName).toString();
  }

  public void deleteFile(String fileName) {
    s3Client.deleteObject(bucketName, fileName);
  }
}