package org.apache.seatunnel.connectors.seatunnel.file.local.config;

import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.Options;
import org.apache.seatunnel.api.options.SinkConnectorCommonOptions;
import org.apache.seatunnel.api.sink.DataSaveMode;
import org.apache.seatunnel.api.sink.SchemaSaveMode;
import org.apache.seatunnel.common.utils.DateTimeUtils;
import org.apache.seatunnel.common.utils.DateUtils;
import org.apache.seatunnel.common.utils.TimeUtils;
import org.apache.seatunnel.connectors.seatunnel.file.config.FileFormat;
import org.apache.seatunnel.format.text.constant.TextFormatConstant;

import java.util.Arrays;

import static org.apache.seatunnel.api.sink.DataSaveMode.*;
import static org.apache.seatunnel.api.sink.DataSaveMode.APPEND_DATA;

public class LocalFileBaseOptions {

    public static final String DEFAULT_FIELD_DELIMITER = TextFormatConstant.SEPARATOR[0];

    public static final Option<String> ENCODING =
            Options.key("encoding")
                    .stringType()
                    .defaultValue("UTF-8")
                    .withDescription(
                            "The encoding of the file, e.g. UTF-8, ISO-8859-1....");

    public static final Option<String> FILE_PATH =
            Options.key("path")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The file path of target files");

    public static final Option<FileFormat> FILE_FORMAT_TYPE =
            Options.key("file_format_type")
                    .enumType(FileFormat.class)
                    .defaultValue(FileFormat.CSV)
                    .withDescription("File format type, e.g. csv, orc, parquet, text");

    public static final Option<String> FIELD_DELIMITER =
            Options.key("field_delimiter")
                    .stringType()
                    .defaultValue(DEFAULT_FIELD_DELIMITER)
                    .withDescription(
                            "The separator between columns in a row of data. Only needed by `text` and `csv` file format");

    public static final Option<Boolean> XML_USE_ATTR_FORMAT =
            Options.key("xml_use_attr_format")
                    .booleanType()
                    .noDefaultValue()
                    .withDescription(
                            "Specifies whether to process data using the tag attribute format, only valid for XML files.");

    public static final Option<String> XML_ROW_TAG =
            Options.key("xml_row_tag")
                    .stringType()
                    .defaultValue("RECORD")
                    .withDescription(
                            "Specifies the tag name of the data rows within the XML file, only valid for xml files, default value is 'RECORD'");

    public static final Option<DateUtils.Formatter> DATE_FORMAT =
            Options.key("date_format")
                    .enumType(DateUtils.Formatter.class)
                    .defaultValue(DateUtils.Formatter.YYYY_MM_DD)
                    .withDescription("Date format");

    public static final Option<DateTimeUtils.Formatter> DATETIME_FORMAT =
            Options.key("datetime_format")
                    .enumType(DateTimeUtils.Formatter.class)
                    .defaultValue(DateTimeUtils.Formatter.YYYY_MM_DD_HH_MM_SS)
                    .withDescription("Datetime format");

    public static final Option<TimeUtils.Formatter> TIME_FORMAT =
            Options.key("time_format")
                    .enumType(TimeUtils.Formatter.class)
                    .defaultValue(TimeUtils.Formatter.HH_MM_SS)
                    .withDescription("Time format");

    public static Option<Integer> MULTI_TABLE_SINK_REPLICA =
            SinkConnectorCommonOptions.MULTI_TABLE_SINK_REPLICA;

    public static final Option<SchemaSaveMode> SCHEMA_SAVE_MODE =
            Options.key("schema_save_mode")
                    .enumType(SchemaSaveMode.class)
                    .defaultValue(SchemaSaveMode.CREATE_SCHEMA_WHEN_NOT_EXIST)
                    .withDescription(
                            "Before the synchronization task begins, process the existing path");

    public static final Option<DataSaveMode> DATA_SAVE_MODE =
            Options.key("data_save_mode")
                    .singleChoice(
                            DataSaveMode.class,
                            Arrays.asList(DROP_DATA, APPEND_DATA, ERROR_WHEN_DATA_EXISTS))
                    .defaultValue(APPEND_DATA)
                    .withDescription(
                            "Before the synchronization task begins, different processing of data files that already exist in the directory");
}
