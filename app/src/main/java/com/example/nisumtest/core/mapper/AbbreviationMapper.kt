package com.example.nisumtest.core.mapper

import com.example.nisumtest.data.local.entity.AbbreviationEntity
import com.example.nisumtest.data.local.entity.LfEntity
import com.example.nisumtest.data.local.entity.VarEntity
import com.example.nisumtest.data.local.entity.relation.AbbreviationWithLf
import com.example.nisumtest.data.local.entity.relation.LfWithVar
import com.example.nisumtest.data.model.Abbreviation
import com.example.nisumtest.data.model.Lf
import com.example.nisumtest.data.model.Var
import javax.inject.Inject

class AbbreviationMapper
@Inject constructor() : EntityMapper<AbbreviationWithLf, Abbreviation> {
    override fun mapFromEntity(entity: AbbreviationWithLf): Abbreviation {
        val lfs = entity.lfs.map {
            Lf(
                it.lf.frequency,
                it.lf.lf,
                it.lf.since,
                it.vars.map { v -> Var(v.frequency, v.lf, v.since) })
        }

        return Abbreviation(
            entity.abb.sf,
            lfs
        )
    }

    override fun mapToEntity(domainModel: Abbreviation): AbbreviationWithLf {
        return AbbreviationWithLf(
            AbbreviationEntity(0, domainModel.sf),
            domainModel.lfs.map {
                LfWithVar(
                    LfEntity(0, it.freq, it.lf, it.since, 0),
                    it.vars.map { v -> VarEntity(0, v.freq, v.lf, v.since, 0) })
            })
    }

    override fun toList(entity: List<AbbreviationWithLf>): List<Abbreviation> {
        return entity.map { mapFromEntity(it) }
    }

    fun mapFromList(models: List<Abbreviation>): List<AbbreviationWithLf> {
        return models.map { mapToEntity(it) }
    }

    fun mapToLfsWithId(idFk: Long, model: LfEntity): LfEntity {
        return LfEntity(0, model.frequency, model.lf, model.since, idFk)
    }

    fun mapToVarWithLfsId(idFk: Long, model: VarEntity): VarEntity {
        return VarEntity(0, model.frequency, model.lf, model.since, idFk)
    }

    fun toMapEntity(entity: List<AbbreviationWithLf>): List<LfEntity> {
        val lfOnly = mutableListOf<LfEntity>()
        entity.forEach {
            it.lfs.forEach { lfE ->
                lfOnly.add(lfE.lf)
            }
        }
        return lfOnly
    }

}