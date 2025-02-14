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

package org.apache.seatunnel.connectors.seatunnel.file.local.sink;

import org.apache.seatunnel.api.configuration.ReadonlyConfig;
import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.options.SinkConnectorCommonOptions;
import org.apache.seatunnel.api.table.catalog.CatalogTable;
import org.apache.seatunnel.api.table.connector.TableSink;
import org.apache.seatunnel.api.table.factory.Factory;
import org.apache.seatunnel.api.table.factory.TableSinkFactoryContext;
import org.apache.seatunnel.api.table.type.SeaTunnelRow;
import org.apache.seatunnel.connectors.seatunnel.file.config.FileFormat;
import org.apache.seatunnel.connectors.seatunnel.file.factory.BaseMultipleTableFileSinkFactory;
import org.apache.seatunnel.connectors.seatunnel.file.local.config.LocalFileSinkOptions;
import org.apache.seatunnel.connectors.seatunnel.file.sink.commit.FileAggregatedCommitInfo;
import org.apache.seatunnel.connectors.seatunnel.file.sink.commit.FileCommitInfo;
import org.apache.seatunnel.connectors.seatunnel.file.sink.state.FileSinkState;

import com.google.auto.service.AutoService;

import static org.apache.seatunnel.connectors.seatunnel.file.local.config.LocalFileBaseOptions.*;

@AutoService(Factory.class)
public class LocalFileSinkFactory extends BaseMultipleTableFileSinkFactory {
    @Override
    public String factoryIdentifier() {
        return "LocalFile";
    }

    @Override
    public OptionRule optionRule() {
        return OptionRule.builder()
                .required(FILE_PATH)
                .required(FILE_FORMAT_TYPE)
                .optional(LocalFileSinkOptions.TMP_PATH)
                .optional(SCHEMA_SAVE_MODE)
                .optional(DATA_SAVE_MODE)
                .optional(SinkConnectorCommonOptions.MULTI_TABLE_SINK_REPLICA)
                .conditional(FILE_FORMAT_TYPE,
                        FileFormat.EXCEL,
                        LocalFileSinkOptions.MAX_ROWS_IN_MEMORY,
                        LocalFileSinkOptions.SHEET_NAME)
                .conditional(
                        FILE_FORMAT_TYPE,
                        FileFormat.XML,
                        XML_USE_ATTR_FORMAT)
                .optional(LocalFileSinkOptions.CUSTOM_FILENAME)
                .optional(LocalFileSinkOptions.FILE_NAME_EXPRESSION)
                .optional(LocalFileSinkOptions.FILENAME_TIME_FORMAT)
                .optional(LocalFileSinkOptions.HAVE_PARTITION)
                .conditional(
                        LocalFileSinkOptions.HAVE_PARTITION,
                        true,
                        LocalFileSinkOptions.PARTITION_BY,
                        LocalFileSinkOptions.PARTITION_DIR_EXPRESSION,
                        LocalFileSinkOptions.IS_PARTITION_FIELD_WRITE_IN_FILE)
                .optional(LocalFileSinkOptions.SINK_COLUMNS)
                .optional(LocalFileSinkOptions.IS_ENABLE_TRANSACTION)
                .optional(DATE_FORMAT)
                .optional(DATETIME_FORMAT)
                .optional(TIME_FORMAT)
                .optional(ENCODING)
                .optional(LocalFileSinkOptions.SINGLE_FILE_MODE)
                .optional(LocalFileSinkOptions.BATCH_SIZE)
                .optional(LocalFileSinkOptions.TXT_COMPRESS)
                .optional(LocalFileSinkOptions.XML_ROOT_TAG)
                .optional(XML_ROW_TAG)
                .optional(LocalFileSinkOptions.PARQUET_AVRO_WRITE_TIMESTAMP_AS_INT96)
                .optional(LocalFileSinkOptions.PARQUET_AVRO_WRITE_FIXED_AS_INT96)
                .optional(LocalFileSinkOptions.ENABLE_HEADER_WRITE)
                .optional(LocalFileSinkOptions.CREATE_EMPTY_FILE_WHEN_NO_DATA)
                .build();
    }

    @Override
    public TableSink<SeaTunnelRow, FileSinkState, FileCommitInfo, FileAggregatedCommitInfo>
            createSink(TableSinkFactoryContext context) {
        ReadonlyConfig readonlyConfig = context.getOptions();
        CatalogTable catalogTable = context.getCatalogTable();
        return () -> new LocalFileSink(readonlyConfig, catalogTable);
    }
}
