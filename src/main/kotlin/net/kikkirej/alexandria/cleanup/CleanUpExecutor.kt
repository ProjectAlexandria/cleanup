package net.kikkirej.alexandria.cleanup

import net.kikkirej.alexandria.cleanup.config.GeneralProperties
import net.kikkirej.alexandria.cleanup.db.AnalysisRepository
import net.kikkirej.alexandria.cleanup.db.VersionRepository
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription
import org.camunda.bpm.client.task.ExternalTask
import org.camunda.bpm.client.task.ExternalTaskHandler
import org.camunda.bpm.client.task.ExternalTaskService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

@Component
@ExternalTaskSubscription("cleanup")
class CleanUpExecutor (
    @Autowired val generalProperties: GeneralProperties,
    @Autowired val versionRepository: VersionRepository,
    @Autowired val analysisRepository: AnalysisRepository,
): ExternalTaskHandler{
    override fun execute(externalTask: ExternalTask?, externalTaskService: ExternalTaskService?) {
        deleteFileSystemFolder(externalTask!!.businessKey)
        updateCurrentAnalysis(externalTask.businessKey)
        externalTaskService!!.complete(externalTask)
    }

    private fun deleteFileSystemFolder(businessKey: String) {
        val path = Path.of(File(generalProperties.sharedfolder).absolutePath + File.separator + businessKey)
        Files.deleteIfExists(path)
    }

    private fun updateCurrentAnalysis(businessKey: String) {
        val analysisOptional = analysisRepository.findById(businessKey.toLong())
        val analysis = analysisOptional.get()
        val version = analysis.version
        version.latest_analysis = analysis
        versionRepository.save(version)
    }

}