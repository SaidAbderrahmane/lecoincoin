package fr.bihar.lecoincoin

import fr.bihar.lecoincoin.Illustration
import fr.bihar.lecoincoin.SaleAd

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional

import java.io.File
import java.security.MessageDigest
import java.util.List

import org.springframework.web.multipart.MultipartFile

@Transactional
class IllustraionService {

    GrailsApplication grailsApplication

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

}
