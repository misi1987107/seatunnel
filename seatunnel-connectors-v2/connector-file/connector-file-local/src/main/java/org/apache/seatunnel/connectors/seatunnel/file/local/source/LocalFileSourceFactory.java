/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.connectors.seatunnel.file.local.source;

import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.options.ConnectorCommonOptions;
import org.apache.seatunnel.api.source.SeaTunnelSource;
import org.apache.seatunnel.api.source.SourceSplit;
import org.apache.seatunnel.api.table.connector.TableSource;
import org.apache.seatunnel.api.table.factory.Factory;
import org.apache.seatunnel.api.table.factory.TableSourceFactory;
import org.apache.seatunnel.api.table.factory.TableSourceFactoryContext;
import org.apache.seatunnel.connectors.seatunnel.file.config.FileFormat;

import com.google.auto.service.AutoService;
import org.apache.seatunnel.connectors.seatunnel.file.local.config.LocalFileSourceOptions;

import java.io.Serializable;
import java.util.Arrays;

import static org.apache.seatunnel.connectors.seatunnel.file.local.config.LocalFileBaseOptions.*;

@AutoService(Factory.class)
public class LocalFileSourceFactory implements TableSourceFactory {
    @Override
    public String factoryIdentifier() {
        return "LocalFile";
    }

    @Override
    public <T, SplitT extends SourceSplit, StateT extends Serializable>
            TableSource<T, SplitT, StateT> createSource(TableSourceFactoryContext context) {
        return () -> (SeaTunnelSource<T, SplitT, StateT>) new LocalFileSource(context.getOptions());
    }

    @Override
    public OptionRule optionRule() {
        return OptionRule.builder()
                .optional(ConnectorCommonOptions.TABLE_CONFIGS)
                .optional(FILE_PATH)
                .optional(FILE_FORMAT_TYPE)
                .optional(ENCODING)
                .conditional(
                        FILE_FORMAT_TYPE,
                        FileFormat.TEXT,
                        FIELD_DELIMITER)
                .conditional(
                        FILE_FORMAT_TYPE,
                        FileFormat.XML,
                        XML_ROW_TAG,
                        XML_USE_ATTR_FORMAT)
                .conditional(
                        FILE_FORMAT_TYPE,
                        Arrays.asList(
                                FileFormat.TEXT,
                                FileFormat.JSON,
                                FileFormat.EXCEL,
                                FileFormat.CSV,
                                FileFormat.XML),
                        ConnectorCommonOptions.SCHEMA)
                .optional(LocalFileSourceOptions.PARSE_PARTITION_FROM_PATH)
                .optional(DATE_FORMAT)
                .optional(DATETIME_FORMAT)
                .optional(TIME_FORMAT)
                .optional(LocalFileSourceOptions.SHEET_NAME)
                .optional(LocalFileSourceOptions.EXCEL_ENGINE)
                .optional(LocalFileSourceOptions.FILE_FILTER_PATTERN)
                .optional(LocalFileSourceOptions.COMPRESS_CODEC)
                .optional(LocalFileSourceOptions.ARCHIVE_COMPRESS_CODEC)
                .optional(LocalFileSourceOptions.NULL_FORMAT)
                .build();
    }

    @Override
    public Class<? extends SeaTunnelSource> getSourceClass() {
        return LocalFileSource.class;
    }
}
