package project.campshare.domain.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class AWSS3Service {

    private final FileService fileService;

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;


    /*
     * bean이 초기화 될 때 한 번 AmazonS3Client 객체를 빌드한다.
     */
    @PostConstruct
    private void setS3Client(){
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey,this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region)
                .build();
    }


    public String upload(MultipartFile file, String dir) {
        String filename = file.getOriginalFilename();
        String convertedFileName = fileService.fileNameConvert(filename);
        String fileNameWithPath = fileService.addDirToSave(convertedFileName,dir);
        try{
            String mimeType = new Tika().detect(file.getInputStream());
            ObjectMetadata metadata = new ObjectMetadata();

            fileService.checkImageMimeType(mimeType);
            metadata.setContentType(mimeType);
            s3Client.putObject(
                    new PutObjectRequest(bucket,fileNameWithPath,file.getInputStream(),metadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch (IOException ex){
            ex.printStackTrace();
        }

        return s3Client.getUrl(bucket,fileNameWithPath).toString();
    }
}
