package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.example.project.data.local.dao.FeatureDao
import org.example.project.data.mapper.toEntity
import org.example.project.data.mapper.toUiModel
import org.example.project.domain.model.FeatureUiModel

class FeatureRepository(
    private val dao: FeatureDao
) {
    fun getQuickAccessList(): Flow<List<FeatureUiModel>> =
        dao.getQuickAccessList().map { list -> list.map { it.toUiModel() } }

    suspend fun seedDefaultsIfNeeded() {
        if (dao.getQuickAccessCount() == 0) {
            val defaults = FeatureUiModel.getQuickAccessList()
            dao.insertAll(defaults.mapIndexed { index, item -> item.toEntity(index) })
        }
    }

    suspend fun addToQuickAccess(feature: FeatureUiModel, order: Int) =
        dao.insert(feature.toEntity(order))

    suspend fun removeFromQuickAccess(feature: FeatureUiModel) =
        dao.delete(feature.toEntity(0))

    suspend fun reorderQuickAccess(reorderedList: List<FeatureUiModel>) {
        val entities = reorderedList.mapIndexed { index, item -> item.toEntity(index) }
        dao.insertAll(entities)
    }
}