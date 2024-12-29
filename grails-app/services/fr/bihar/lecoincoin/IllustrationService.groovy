package fr.bihar.lecoincoin


import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional

import java.security.MessageDigest

import org.springframework.web.multipart.MultipartFile

@Transactional
class IllustrationService {

    GrailsApplication grailsApplication
    /**
     * Handles the upload and creation of multiple illustrations from a list of multipart files.
     *
     * This method processes a list of uploaded files, saves them to a specified directory,
     * and returns a list of `Illustration` objects representing the saved files. If the upload
     * directory does not exist, it is created. Each file is hashed to avoid overwriting existing files.
     * Any errors during file saving are logged and handled gracefully.
     * @param uploadedFiles a list of `MultipartFile` objects representing the uploaded files.
     *                      Each file should include its original filename for hashing purposes.
     * @return a list of `Illustration` objects, where each object contains the hashed filename
     *         of a successfully saved file. If no files are successfully saved, an empty list is returned.
     * @throws IllegalStateException if there are issues with file creation or saving,
     *                                which are logged for debugging purposes.
     * @see MultipartFile
     */
    List<Illustration> createMany(List<MultipartFile> uploadedFiles) {

        String uploadDir = grailsApplication.config.illustrations.basePath
        File dir = new File(uploadDir)

        // Check and create the upload directory if it doesn't exist
        if (!dir.exists()) {
            log.debug("Creating upload directory: ${dir}")
            if (!dir.mkdirs()) {
                log.error("Failed to create upload directory: ${dir}")
            }
        }

        List<Illustration> illustrations = []

        uploadedFiles.each { uploadedFile ->
            String hashedFilename = hashFilename(uploadedFile.originalFilename)

            File file = new File(dir, hashedFilename)

            // if file exists, skip it, if not, save it
            if (!file.exists() && !file.createNewFile()) {
                log.error("Failed to create file: ${file.absolutePath}")
                return
            }

            try {
                uploadedFile.transferTo(file.toPath())
                log.debug("File saved: ${file.absolutePath}")

                Illustration illustration = new Illustration()
                illustration.fileName = hashedFilename

                illustrations.add(illustration)
            } catch (Exception e) {
                log.error("Error saving file ${hashedFilename}: ${e.message}", e)
                flash.message = "Error saving file ${hashedFilename}."
                render(view: 'create', model: [saleAd: saleAd])
                return
            }
        }

        return illustrations
    }

    protected String hashFilename(String originalFilename) {
        MessageDigest md5Digest = MessageDigest.getInstance('MD5')
        byte[] originalFilenameBytes = originalFilename.bytes
        String hashedFilename = md5Digest.digest(originalFilenameBytes).encodeHex().toString()

        String fileExtension = originalFilename.tokenize('.').last()
        hashedFilename = "${hashedFilename}.${fileExtension}"
        log.debug("Hashed filename: ${hashedFilename}")
        return hashedFilename
    }

    String getIllustrationUrl(Illustration illustration) {
        String uploadDir = grailsApplication.config.illustrations.baseUrl
        return "${uploadDir}${illustration.fileName}"
    }

    String getDefaultIllustrationUrl() {
        String uploadDir = grailsApplication.config.illustrations.baseUrl
        String defaultImage = grailsApplication.config.illustrations.defaultImage
        return "${uploadDir}${defaultImage}"
    }
}
