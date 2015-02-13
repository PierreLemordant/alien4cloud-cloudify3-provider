package alien4cloud.paas.cloudify3.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import alien4cloud.git.RepositoryManager;
import alien4cloud.tosca.ArchiveUploadService;
import alien4cloud.utils.FileUtil;

@Component
@Slf4j
public class CSARUtil {

    public static final String TOSCA_NORMATIVE_TYPES_NAME = "tosca-normative-types";

    public static final String URL_FOR_SAMPLES = "https://github.com/alien4cloud/samples.git";
    public static final String BRANCH_FOR_SAMPLES = "cloudify3";
    public static final String SAMPLES_TYPES_NAME = "samples";
    public static final String APACHE_TYPE_PATH = SAMPLES_TYPES_NAME + "/apache";
    public static final String MYSQL_TYPE_PATH = SAMPLES_TYPES_NAME + "/mysql";
    public static final String PHP_TYPE_PATH = SAMPLES_TYPES_NAME + "/php";
    public static final String WORDPRESS_TYPE_PATH = SAMPLES_TYPES_NAME + "/wordpress";

    public static final Path ARTIFACTS_DIRECTORY = Paths.get("./target/csars");

    public static final Path TOSCA_NORMATIVE_TYPES = ARTIFACTS_DIRECTORY.resolve(TOSCA_NORMATIVE_TYPES_NAME);

    @Resource
    private ArchiveUploadService archiveUploadService;

    private RepositoryManager repositoryManager = new RepositoryManager();

    public void uploadCSAR(Path path) throws Exception {
        Path zipPath = Files.createTempFile("csar", ".zip");
        FileUtil.zip(path, zipPath);
        archiveUploadService.upload(zipPath);
    }

    public void uploadNormativeTypes() throws Exception {
        repositoryManager.cloneOrCheckout(ARTIFACTS_DIRECTORY, "https://github.com/alien4cloud/tosca-normative-types.git", "1.0.0.wd03",
                TOSCA_NORMATIVE_TYPES_NAME);
        uploadCSAR(TOSCA_NORMATIVE_TYPES);
    }

    public void uploadApacheTypes() throws Exception {
        repositoryManager.cloneOrCheckout(ARTIFACTS_DIRECTORY, URL_FOR_SAMPLES, BRANCH_FOR_SAMPLES, SAMPLES_TYPES_NAME);
        uploadCSAR(ARTIFACTS_DIRECTORY.resolve(APACHE_TYPE_PATH));
    }

    public void uploadMySqlTypes() throws Exception {
        repositoryManager.cloneOrCheckout(ARTIFACTS_DIRECTORY, URL_FOR_SAMPLES, BRANCH_FOR_SAMPLES, SAMPLES_TYPES_NAME);
        uploadCSAR(ARTIFACTS_DIRECTORY.resolve(MYSQL_TYPE_PATH));
    }

    public void uploadPHPTypes() throws Exception {
        repositoryManager.cloneOrCheckout(ARTIFACTS_DIRECTORY, URL_FOR_SAMPLES, BRANCH_FOR_SAMPLES, SAMPLES_TYPES_NAME);
        uploadCSAR(ARTIFACTS_DIRECTORY.resolve(PHP_TYPE_PATH));
    }

    public void uploadWordpress() throws Exception {
        repositoryManager.cloneOrCheckout(ARTIFACTS_DIRECTORY, URL_FOR_SAMPLES, BRANCH_FOR_SAMPLES, SAMPLES_TYPES_NAME);
        uploadCSAR(ARTIFACTS_DIRECTORY.resolve(WORDPRESS_TYPE_PATH));
    }

    public void uploadAll() throws Exception {
        uploadNormativeTypes();
        uploadApacheTypes();
        uploadMySqlTypes();
        uploadPHPTypes();
        uploadWordpress();
    }
}
