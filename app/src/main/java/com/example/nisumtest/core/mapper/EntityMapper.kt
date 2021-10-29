package com.example.nisumtest.core.mapper

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity

    fun toList(entity: List<Entity>): List<DomainModel>
}