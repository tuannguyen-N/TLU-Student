package org.example.project.data.mapper

import org.example.project.data.local.entity.FeatureEntity
import org.example.project.domain.model.FeatureUiModel

fun FeatureUiModel.toEntity(order: Int) = FeatureEntity(
    name = name,
    type = type,
    order = order
)

fun FeatureEntity.toUiModel(): FeatureUiModel = FeatureUiModel(
    name = name,
    type = type
)