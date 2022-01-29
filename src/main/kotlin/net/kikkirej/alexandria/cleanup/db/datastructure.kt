package net.kikkirej.alexandria.cleanup.db

import org.springframework.data.repository.CrudRepository
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "version")
class Version(@Id var id: Long,
              var name:String,
              @ManyToOne var latest_analysis: Analysis? = null,
)

@Entity(name = "analysis")
class Analysis(@Id var id: Long,
               @ManyToOne var version: Version,
)

interface AnalysisRepository : CrudRepository<Analysis, Long>

interface VersionRepository : CrudRepository<Version, Long>