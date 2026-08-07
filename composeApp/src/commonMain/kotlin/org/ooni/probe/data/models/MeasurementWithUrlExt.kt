package org.ooni.probe.data.models

import androidx.compose.ui.text.intl.Locale
import io.ktor.http.URLBuilder
import io.ktor.http.appendPathSegments
import org.ooni.probe.config.OrganizationConfig
import org.ooni.probe.shared.languageRegionString

val MeasurementWithUrl.explorerUrl: String?
    get() {
        val urlBuilder = URLBuilder(OrganizationConfig.explorerUrl)
        val measurementUid = measurement.uid
        val measurementReportId = measurement.reportId
        if (measurementUid != null && measurementUid.value.isNotBlank()) {
            urlBuilder.appendPathSegments(listOf("m", measurementUid.value))
        } else if (measurementReportId != null) {
            urlBuilder.appendPathSegments(listOf("measurement", measurementReportId.value))
            url?.url?.let {
                urlBuilder.parameters.append("input", it)
            }
        } else {
            return null
        }
        return urlBuilder.build().toString()
    }

val MeasurementWithUrl.webViewUrl: String?
    get() {
        val explorerUrl = explorerUrl ?: return null
        val webViewUrl = URLBuilder(explorerUrl)
        webViewUrl.parameters.append("webview", "true")
        webViewUrl.parameters.append("language", Locale.current.languageRegionString)
        return webViewUrl.build().toString()
    }
