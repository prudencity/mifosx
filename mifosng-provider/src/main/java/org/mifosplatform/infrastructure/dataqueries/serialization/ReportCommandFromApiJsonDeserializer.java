/**
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mifosplatform.infrastructure.dataqueries.serialization;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.mifosplatform.infrastructure.core.data.ApiParameterError;
import org.mifosplatform.infrastructure.core.data.DataValidatorBuilder;
import org.mifosplatform.infrastructure.core.exception.InvalidJsonException;
import org.mifosplatform.infrastructure.core.exception.PlatformApiDataValidationException;
import org.mifosplatform.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

@Component
public final class ReportCommandFromApiJsonDeserializer {

	/**
	 * The parameters supported for this command.
	 */
	private final Set<String> supportedParameters = new HashSet<String>(
			Arrays.asList("reportName", "reportType", "reportSubType",
					"reportCategory", "reportSql", "useReport"));

	private final FromJsonHelper fromApiJsonHelper;

	@Autowired
	public ReportCommandFromApiJsonDeserializer(
			final FromJsonHelper fromApiJsonHelper) {
		this.fromApiJsonHelper = fromApiJsonHelper;
	}

	public void validateForCreate(final String json) {
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
				supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(
				dataValidationErrors).resource("report");

		final JsonElement element = fromApiJsonHelper.parse(json);
/* WIP JPW
		Arrays.asList("reportName", "reportType", "reportSubType",
				"reportCategory", "reportSql", "useReport"));
		final String name = fromApiJsonHelper.extractStringNamed("name",
				element);
		baseDataValidator.reset().parameter("name").value(name).notBlank()
				.notExceedingLengthOf(100);

		final String externalId = fromApiJsonHelper.extractStringNamed(
				"externalId", element);
		baseDataValidator.reset().parameter("externalId").value(externalId)
				.notExceedingLengthOf(100);
*/
		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}

	public void validateForUpdate(final String json) {
		if (StringUtils.isBlank(json)) {
			throw new InvalidJsonException();
		}

		final Type typeOfMap = new TypeToken<Map<String, Object>>() {
		}.getType();
		fromApiJsonHelper.checkForUnsupportedParameters(typeOfMap, json,
				supportedParameters);

		final List<ApiParameterError> dataValidationErrors = new ArrayList<ApiParameterError>();
		final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(
				dataValidationErrors).resource("report");

		final JsonElement element = fromApiJsonHelper.parse(json);
		
		/* wip jpw
		if (fromApiJsonHelper.parameterExists("name", element)) {
			final String name = fromApiJsonHelper.extractStringNamed("name",
					element);
			baseDataValidator.reset().parameter("name").value(name).notBlank()
					.notExceedingLengthOf(100);
		}

		if (fromApiJsonHelper.parameterExists("externalId", element)) {
			final String externalId = fromApiJsonHelper.extractStringNamed(
					"externalId", element);
			baseDataValidator.reset().parameter("externalId").value(externalId)
					.notExceedingLengthOf(100);
		}
*/
		throwExceptionIfValidationWarningsExist(dataValidationErrors);
	}

	private void throwExceptionIfValidationWarningsExist(
			final List<ApiParameterError> dataValidationErrors) {
		if (!dataValidationErrors.isEmpty()) {
			throw new PlatformApiDataValidationException(
					"validation.msg.validation.errors.exist",
					"Validation errors exist.", dataValidationErrors);
		}
	}
}