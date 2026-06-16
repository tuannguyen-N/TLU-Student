package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.mapper.toUiModel
import org.example.project.data.remote.api.TuitionApi
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.TuitionDetailUiModel
import org.example.project.domain.model.TuitionUiModel

class TuitionRepository(
    private val tuitionApi: TuitionApi
) {
    private val _tuitionList = MutableStateFlow<List<TuitionUiModel>>(emptyList())
    val tuitionList = _tuitionList.asStateFlow()

    suspend fun getTuition(): AppResult<List<TuitionUiModel>> {
        try {
            val data = tuitionApi.getTuition().data ?: return AppResult.Failure("No data")
            val dataUiModel = data.toUiModel()
            _tuitionList.value = dataUiModel
            return AppResult.Success(dataUiModel)
        } catch (e: Exception) {
            return AppResult.Failure(e.message, cause = e)
        }
    }

    suspend fun getDetailTuition(invoiceId: Int): AppResult<TuitionDetailUiModel> {
        try {
            val data =
                tuitionApi.getDetailTuition(invoiceId).data ?: return AppResult.Failure("No data")
            return AppResult.Success(data.toUiModel())
        } catch (e: Exception) {
            return AppResult.Failure(e.message, cause = e)
        }
    }
}